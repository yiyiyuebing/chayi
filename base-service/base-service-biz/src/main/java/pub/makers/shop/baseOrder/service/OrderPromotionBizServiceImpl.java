package pub.makers.shop.baseOrder.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.baseOrder.pojo.BaseOrderItem;
import pub.makers.shop.baseOrder.pojo.PromotionOrderQuery;
import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.promotion.plugin.PromotionPlugin;

import java.math.BigDecimal;
import java.util.Collection;

@Service(version = "1.0.0")
public class OrderPromotionBizServiceImpl implements OrderPromotionBizService{

	@Autowired
	private OrderPromotionPluginManager promotionMgr;
	
	@Override
	public BaseOrder applyGiftRule(PromotionOrderQuery query, TradeContext tc) {
		
		Collection<PromotionPlugin> plugins = promotionMgr.getGiftPlungins(query.getOrderBizType(), query.getOrderType());
		BaseOrder order = query.getOrderInfo();
		for (PromotionPlugin plugin : plugins){
			
			query.setOrderInfo(order);
			// TODO 后续考虑对于整个订单生效的促销规则
			order = plugin.applyForCreateOrer(query, tc);
		}
		
		// 计算优惠金额
		BigDecimal discountAmount = BigDecimal.ZERO;
		for (BaseOrderItem item : order.getItemList()){
			BigDecimal prevtDiscountAmount = item.getDiscountAmount();
			if (prevtDiscountAmount == null){ prevtDiscountAmount = BigDecimal.ZERO; }
			discountAmount = discountAmount.add(prevtDiscountAmount);
		}
		
		order.setDiscountAmount(discountAmount);
		
		return order;
	}

	@Override
	public void applyPriceRule(PromotionOrderQuery query, TradeContext tc) {
		
		Collection<PromotionPlugin> plugins = promotionMgr.getPricePlungins(query.getOrderBizType(), query.getOrderType());
		for (PromotionPlugin plugin : plugins){
			
			plugin.applyForCreateOrer(query, tc);
		}
		
	}

	@Override
	public void applyTotalPriceRule(PromotionOrderQuery query, TradeContext tc) {
		
		Collection<PromotionPlugin> plugins = promotionMgr.getTotalPricePlungins(query.getOrderBizType(), query.getOrderType());
		for (PromotionPlugin plugin : plugins){

			plugin.applyForCreateOrer(query, tc);
		}
		
	}

}
