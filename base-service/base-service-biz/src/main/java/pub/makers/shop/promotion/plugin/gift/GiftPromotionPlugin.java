package pub.makers.shop.promotion.plugin.gift;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.common.entity.BoolType;

import pub.makers.shop.baseGood.pojo.BaseGood;
import pub.makers.shop.baseGood.service.GiftBizService;
import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.baseOrder.pojo.BaseOrderItem;
import pub.makers.shop.baseOrder.pojo.PromotionGoodQuery;
import pub.makers.shop.baseOrder.pojo.PromotionOrderQuery;
import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.baseOrder.utils.BaseOrderHelps;
import pub.makers.shop.promotion.enums.PromotionActivityType;
import pub.makers.shop.promotion.plugin.PromotionPlugin;
import pub.makers.shop.promotion.vo.PromotionActivityVo;
import pub.makers.shop.stock.service.StockBizService;
import pub.makers.shop.tradeGoods.entity.TradeGiftRule;


/**
 * 赠品促销插件
 * @author apple
 *
 */
@Service
public class GiftPromotionPlugin implements PromotionPlugin{

	@Autowired
	private StockBizService stockBizService;
	@Autowired
	private GiftBizService giftBizService;
	
	@Override
	public BaseOrder applyForCreateOrer(PromotionOrderQuery query, TradeContext tc) {
		
		BaseOrder order = query.getOrderInfo();
		List oriItemList = order.getItemList();
		// 商品列表至少需要有一条记录
		Class<? extends BaseOrderItem> itemClass = order.getItemList().get(0).getClass();
		
		// 合并一下商品,统一计算赠品
		List<? extends BaseOrderItem> itemList = BaseOrderHelps.groupItems(order.getItemList());
		Map<String, BaseOrderItem> itemMap = Maps.newHashMap();
		List<TradeGiftRule> ruleList = Lists.newArrayList();
		for (BaseOrderItem item : itemList){
			itemMap.put(item.getGoodSkuId(), item);
			// 查询可赠送的赠品列表
			ruleList.addAll(giftBizService.queryGiftRules(query.getOrderBizType(), item.getGoodSkuId()));
		}
		
        for (TradeGiftRule rule : ruleList){
        	
        	BaseOrderItem item = itemMap.get(rule.getGoodSkuId() + "");
        	// 每份多少个
        	int giftNum = calcGiftNum(rule, item.getBuyNum());
        	int stockNum = stockBizService.queryCurrStork(rule.getGiftSkuId() + "", query.getOrderBizType().name());
        	// 如果赠品数量不够，则赠送最大可赠送数量
        	giftNum = giftNum > stockNum ? stockNum : giftNum;
        	if (giftNum > 0){
        		BaseOrderItem gift = null;
				try {
					gift = itemClass.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
        		gift.setBuyNum(giftNum);
        		gift.setGiftFlag(BoolType.T.name());
        		gift.setWaitPayAmont(BigDecimal.ZERO);
        		gift.setGoodSkuId(rule.getGiftSkuId() + "");
        		oriItemList.add(gift);
        	}
        }
        
        order.setItemList(oriItemList);
		
		return order;
	}
	
	private int calcGiftNum(TradeGiftRule rule, int buyNum){
		
		int giftNum = 0;
		/**
		 * 1.按倍数送：
		 * 根据已定赠品规则，赠品数量随购买商品的整数倍数增长而增长
		 * 例，赠品规则为买2送1，当购买数量为4和6等时，赠品数量才为2和3等
		 */
		if ("mulriple".equals(rule.getCondType())){
			int bs = buyNum / rule.getCondNum();
			giftNum = bs * rule.getNum();
		}
		/**
		 * 2.按区间送：
		 * 根据已定赠品规则，赠品数量随购买商品的整数倍数增长而增长，且当购买数量处于整数倍数的中间值时，赠品数量为较大倍数对应的赠品数量
		 * 例，赠品规则为买4送2，倍数为买8送4，买12送6。当购买数量为1-4时，赠品数量为2；当购买数量为5-8时，赠品数量为4；当购买数量为9-12时，赠品数量为6
		 */
		else if ("range".equals(rule.getCondType())){
			int bs = buyNum / rule.getCondNum();
			int fix = buyNum % rule.getCondNum() > 0 ? 1 : 0;
			giftNum = (bs + fix) * rule.getNum();
		}
		/**
		 * 3.按叠加送：
		 * 根据已定赠品规则，赠品数量随购买商品的增长而增长
		 * 例，赠品规则为买2送1，当购买数量达到2时开始赠送对应数量的赠品数量1；当购买数量为3，4，5等时，赠品数量为2，3，4叠加上去
		 */
		else if ("overlay".equals(rule.getCondType())){
			int fixNum = buyNum - rule.getCondNum();
			if (fixNum >= 0){
				giftNum = rule.getNum() + fixNum;
			}
			else {
				giftNum = 0;
			}
		}
		/**
		 * 根据已定赠品规则，赠品数量始终为1
		 */
		else{
			giftNum = 1;
		}
		
		return giftNum;
	}

	@Override
	public BaseOrder applyForPreviewOrder(PromotionOrderQuery query, TradeContext tc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void applyForGoodDetail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PromotionActivityVo> applyForGoodList(PromotionGoodQuery query) {
		
		List<PromotionActivityVo> resultList = Lists.newArrayList();
		
		// 根据传入的SKU_ID查询是否有赠品
		List<TradeGiftRule> ruleList = Lists.newArrayList();
		for (BaseGood good : query.getGoodList()){
			ruleList.addAll(giftBizService.queryGiftRules(query.getOrderBizType(), good.getGoodSkuId()));
		}
		
		for (TradeGiftRule rule : ruleList){
			PromotionActivityVo pvo = new PromotionActivityVo();
			pvo.setActivityType(PromotionActivityType.zengpin.name());
			pvo.setDiscountAmount(BigDecimal.ZERO);
			pvo.setActivityName(PromotionActivityType.zengpin.getDisplayName());
			pvo.setGoodSkuId(rule.getGoodSkuId() + "");
			
			resultList.add(pvo);
		}
		
		return resultList;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return PromotionActivityType.zengpin.name();
	}

}
