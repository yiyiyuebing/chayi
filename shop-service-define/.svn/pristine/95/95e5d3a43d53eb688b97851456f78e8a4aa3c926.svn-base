package pub.makers.shop.baseOrder.service;

import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.baseOrder.pojo.PromotionOrderQuery;
import pub.makers.shop.baseOrder.pojo.TradeContext;

/**
 * 订单促销服务
 * @author apple
 *
 */
public interface OrderPromotionBizService {

	/**
	 * 应用赠品规则
	 * 对订单的商品列表会产生影响的规则
	 * 1.赠品
	 * 2.配件
	 * @param query
	 * @return
	 */
	BaseOrder applyGiftRule(PromotionOrderQuery query, TradeContext tc);
	
	/**
	 * 应用价格规则
	 * 对订单的商品明细价格或者是最终价格会产生影响的规则
	 * 1.限时打折
	 * 2.满减
	 * @param query
	 * @param tc
	 */
	void applyPriceRule(PromotionOrderQuery query, TradeContext tc);
	
	
	/**
	 * 应用订单总金额促销插件
	 * @param query
	 * @param tc
	 */
	void applyTotalPriceRule(PromotionOrderQuery query, TradeContext tc);
}
