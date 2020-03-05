package pub.makers.shop.baseOrder.service;

import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.BaseOrderItem;
import pub.makers.shop.baseOrder.pojo.OrderVerificationResult;
import pub.makers.shop.baseOrder.pojo.TradeContext;

import java.util.List;

/**
 * 订单前置验证器
 * @author apple
 *
 */
public interface OrderPreVerifyBizService {

	/**
	 * 验证提交的订单列表是否满足要求
	 * @param itemList
	 * @return
	 */
	OrderVerificationResult validate(List<? extends BaseOrderItem> itemList, OrderBizType orderBizType, OrderType orderType, TradeContext tradeContext);
}
