package pub.makers.shop.baseOrder.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.makers.shop.baseGood.pojo.BaseGood;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.BaseOrderItem;
import pub.makers.shop.baseOrder.pojo.OrderVerificationResult;
import pub.makers.shop.baseOrder.pojo.PromotionGoodQuery;
import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.baseOrder.service.OrderPreValidator;
import pub.makers.shop.promotion.enums.PromotionActivityType;
import pub.makers.shop.promotion.service.PromotionBizService;
import pub.makers.shop.promotion.vo.GoodPromotionalInfoVo;
import pub.makers.shop.promotion.vo.PresellPromotionActivityVo;
import pub.makers.shop.promotion.vo.SalePromotionActivityVo;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoods;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsService;
import pub.makers.shop.stock.pojo.StockQuery;
import pub.makers.shop.stock.service.StockBizService;
import pub.makers.shop.tradeGoods.entity.TradeGood;
import pub.makers.shop.tradeGoods.service.TradeGoodService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 库存前置验证器
 * 
 * @author apple
 *
 */
@Service("stockPreValidator")
public class StockPreValidator implements OrderPreValidator {

	@Autowired
	private StockBizService stockBizService;
	@Autowired
	private TradeGoodService tradeGoodService;
	@Autowired
	private PurchaseGoodsService purchaseGoodService;
	@Autowired
	private PromotionBizService promotionBizService;
	
	@Override
	public OrderVerificationResult validate(List<? extends BaseOrderItem> itemList, OrderBizType bizType,
			OrderType orderType, TradeContext tradeContext) {

		OrderVerificationResult result = null;
		
		// 合并库存查询
		ListMultimap<String, BaseOrderItem> goodMap = ArrayListMultimap.create();
		for (BaseOrderItem item : itemList) {
			goodMap.put(item.getGoodSkuId(), item);
		}

		// 查询促销信息
		PromotionGoodQuery promotionGoodQuery = new PromotionGoodQuery();
		promotionGoodQuery.setOrderBizType(bizType);
		List<BaseGood> goodList = Lists.newArrayList();
		for (String goodSkuId : goodMap.keySet()) {
			BaseGood baseGood = new BaseGood();
			baseGood.setGoodSkuId(goodSkuId);
			baseGood.setAmount(goodMap.get(goodSkuId).get(0).getGoodPrice());
			goodList.add(baseGood);
		}
		promotionGoodQuery.setGoodList(goodList);
		promotionGoodQuery.setOrderType(orderType);
		Map<String, GoodPromotionalInfoVo> infoVoMap = promotionBizService.applyPromotionRule(promotionGoodQuery);

		boolean enoughStock = true;
		List<String> errorMsg = Lists.newArrayList();
		for (String goodSkuId : goodMap.keySet()) {

			Collection<BaseOrderItem> skuItems = goodMap.get(goodSkuId);
			int totalNum = 0;
			for (BaseOrderItem skuItem : skuItems) {
				totalNum += skuItem.getBuyNum();
			}
			GoodPromotionalInfoVo infoVo = infoVoMap.get(goodSkuId);
			if (infoVo != null && infoVo.getBestActivity() != null && Lists.newArrayList(PromotionActivityType.presell.name(), PromotionActivityType.sale.name()).contains(infoVo.getBestActivity().getActivityType())) {
				if (PromotionActivityType.presell.name().equals(infoVo.getBestActivity().getActivityType())) {
					// 预售商品比较活动预售量
					PresellPromotionActivityVo activityVo = (PresellPromotionActivityVo) infoVo.getBestActivity();
					enoughStock = totalNum <= activityVo.getPresellNum();
				} else {
					// 打折商品比较活动可售卖数量
					SalePromotionActivityVo activityVo = (SalePromotionActivityVo) infoVo.getBestActivity();
					if (activityVo.getMaxNum() != null) {
						enoughStock = totalNum <= activityVo.getMaxNum();
					} else {
						// 没有设置可售卖数量时比较库存
						StockQuery query = new StockQuery(Long.valueOf(goodSkuId), bizType, totalNum, 0L);
						enoughStock = stockBizService.hasEnoughStock(query);
					}
				}
			} else {
				// 普通商品比较库存
				StockQuery query = new StockQuery(Long.valueOf(goodSkuId), bizType, totalNum, 0L);
				enoughStock = stockBizService.hasEnoughStock(query);
			}
			// 如果订单中有一个商品库存不足，则停止检查
			if (!enoughStock) {
				
				String goodName = goodSkuId;
				if (OrderBizType.trade.equals(bizType)){
					TradeGood good = tradeGoodService.getBySkuId(goodSkuId);
					if (good != null){
						goodName = good.getName();
					}
				}
				else {
					PurchaseGoods good = purchaseGoodService.getByPurGoodsSkuId(goodSkuId);
					if (good != null){
						goodName = good.getPurGoodsName();
					}
					
				}
				errorMsg.add(String.format("%s", goodName));
			}
		}

		if (errorMsg.isEmpty()) {
			result = OrderVerificationResult.createSuccess();
		}
		else {
			result = OrderVerificationResult.createError(Joiner.on("、").join(errorMsg) + "，库存不足，请重新选择数量");
		}

		return result;

	}

}
