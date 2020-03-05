package pub.makers.shop.promotion.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.jedis.JedisCallback;
import pub.makers.jedis.JedisTemplate;
import pub.makers.shop.base.util.SerializeUtils;
import pub.makers.shop.baseGood.pojo.BaseGood;
import pub.makers.shop.baseOrder.pojo.PromotionGoodQuery;
import pub.makers.shop.baseOrder.service.OrderPromotionPluginManager;
import pub.makers.shop.promotion.enums.PromotionActivityType;
import pub.makers.shop.promotion.plugin.PromotionPlugin;
import pub.makers.shop.promotion.vo.GoodPromotionalInfoVo;
import pub.makers.shop.promotion.vo.ManzengPromotionActivityVo;
import pub.makers.shop.promotion.vo.PromotionActivityVo;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service(version = "1.0.0")
public class PromotionBizServiceImpl implements PromotionBizService{

	@Autowired
	private OrderPromotionPluginManager promotionMgr;
	@Autowired
	private JedisTemplate jedisTemplate;

	@Override
	public Map<String, GoodPromotionalInfoVo> applyPromotionRule(PromotionGoodQuery query) {

		List<BaseGood> goodList = query.getGoodList();
		Set<String> skuIds = ListUtils.getIdSet(goodList, "goodSkuId");
		Map<String, GoodPromotionalInfoVo> resultMap = getCacheResult(skuIds, query.getOrderBizType().name());
		for (String skuId : resultMap.keySet()){
			GoodPromotionalInfoVo gvo = resultMap.get(skuId);
			if (gvo.getFinallyAmount() != null && gvo.getFinallyAmount().compareTo(BigDecimal.ZERO) > 0){
				gvo.setPrice(gvo.getFinallyAmount());
			}
			else {
				BaseGood good = goodList.stream().filter(g -> g.getGoodSkuId().equals(skuId)).findFirst().get();
				BigDecimal discountAmount = gvo.getDiscountAmount() == null ? BigDecimal.ZERO : gvo.getDiscountAmount();
				gvo.setPrice(good.getAmount() == null ? BigDecimal.ZERO : good.getAmount().subtract(discountAmount));
			}
			if (!Boolean.TRUE.equals(query.getIsDetail())) {
				for (PromotionActivityVo activityVo : gvo.getActivityList()) {
					if (PromotionActivityType.manjian.name().equals(activityVo.getActivityType()) || PromotionActivityType.manzeng.name().equals(activityVo.getActivityType())) {
						((ManzengPromotionActivityVo) activityVo).setRuleList(null);
					}
				}
			}
		}
		
		return resultMap;
	}

	/**
	 * 将缓存数据保存到redis中
	 * @param resultMap
	 */
	private void cacheResult(final Map<String, GoodPromotionalInfoVo> resultMap, String orderBizType){
		
		jedisTemplate.execute(new JedisCallback<Object>() {

			@Override
			public Object doInJedis(Jedis jedis) {
				
				String hashKey = "hash_" + orderBizType;
				// 删除原有缓存
				Transaction multi = jedis.multi();
				multi.del(hashKey);
				for (String skuId : resultMap.keySet()){
					
					String val = SerializeUtils.hessianSerialize(resultMap.get(skuId));
					multi.hset(hashKey, skuId, val);
				}
				multi.exec();
				return null;
			}
		});
	}
	
	
	private Map<String, GoodPromotionalInfoVo> getCacheResult(final Set<String> skuIds, String orderBizType){
		
		return jedisTemplate.execute(new JedisCallback<Map<String, GoodPromotionalInfoVo>>() {

			@Override
			public Map<String, GoodPromotionalInfoVo> doInJedis(Jedis jedis) {
				
				Map<String, GoodPromotionalInfoVo> resultMap = Maps.newHashMap();

				if (skuIds.isEmpty()) {
					return resultMap;
				}
				String hashKey = "hash_" + orderBizType;
				List<String> resultStrs = jedis.hmget(hashKey, skuIds.toArray(new String[0]));
				for (String resultStr : resultStrs){
					if (StringUtils.isBlank(resultStr)){ continue; }
					GoodPromotionalInfoVo result = SerializeUtils.hessianDeSerialize(resultStr);
					if (result != null){
						resultMap.put(result.getGoodSkuId(), result);
					}
				}
				return resultMap;
			}
		});
	}
	
	@Override
	@Deprecated
	public void updatePromotionRule(PromotionGoodQuery query) {
	
	}

	@Override
	public Map<String, GoodPromotionalInfoVo> buildCache(PromotionGoodQuery query) {
		
		Map<String, GoodPromotionalInfoVo> tmpMap = Maps.newHashMap();

		List<PromotionActivityVo> voList = Lists.newArrayList();
		Collection<PromotionPlugin> plugins = promotionMgr.getAlllplungins(query.getOrderBizType(), query.getOrderType());
		for (PromotionPlugin plugin : plugins){
			voList.addAll(plugin.applyForGoodList(query));
		}

		Multimap<String, PromotionActivityVo> pvoMMap = ArrayListMultimap.create();
		for (PromotionActivityVo pvo : voList){
			pvoMMap.put(pvo.getGoodSkuId(), pvo);
		}

		for (String skuId : pvoMMap.keySet()){
			GoodPromotionalInfoVo gpvo = new GoodPromotionalInfoVo();
			Collection<PromotionActivityVo> pvoC = pvoMMap.get(skuId);
			List<PromotionActivityVo> activityVoList = Lists.newArrayList();
			gpvo.setGoodSkuId(skuId);
			// TODO 考虑选举最佳活动的逻辑
			PromotionActivityVo bestActivity = pvoC.iterator().next();
			for (PromotionActivityVo activityVo : pvoC) {
				if (bestActivity == null || PromotionActivityType.valueOf(bestActivity.getActivityType()).getLevel() < PromotionActivityType.valueOf(activityVo.getActivityType()).getLevel()) {
					bestActivity = activityVo;
				}
				if (!PromotionActivityType.manjian.name().equals(activityVo.getActivityType())) {
					activityVoList.add(activityVo);
				}
			}
			if (!PromotionActivityType.presell.name().equals(bestActivity.getActivityType())) {
				activityVoList = Lists.newArrayList(pvoC);
			}
			activityVoList.remove(bestActivity);
			activityVoList.add(0, bestActivity);
			gpvo.setActivityList(activityVoList);
			gpvo.setFinallyAmount(bestActivity.getFinalAmount());
			gpvo.setDiscountAmount(bestActivity.getDiscountAmount());
			gpvo.setBestActivity(bestActivity);
			tmpMap.put(skuId, gpvo);
			
		}
		
		// 筛选掉没有活动的返回值
		Map<String, GoodPromotionalInfoVo> resultMap = Maps.newHashMap();
		for (String skuId : tmpMap.keySet()){
			GoodPromotionalInfoVo info = tmpMap.get(skuId);
			if (info.getBestActivity() != null){
				resultMap.put(skuId, info);
			}
		}
		
		cacheResult(resultMap, query.getOrderBizType().name());
		
		return resultMap;
	}

}
