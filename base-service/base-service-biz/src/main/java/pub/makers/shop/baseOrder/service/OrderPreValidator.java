package pub.makers.shop.baseOrder.service;

import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.BaseOrderItem;
import pub.makers.shop.baseOrder.pojo.OrderVerificationResult;
import pub.makers.shop.baseOrder.pojo.TradeContext;

import java.util.List;

public interface OrderPreValidator {

	OrderVerificationResult validate(List<? extends BaseOrderItem> itemList, OrderBizType bizType, OrderType orderType, TradeContext tradeContext);
}
