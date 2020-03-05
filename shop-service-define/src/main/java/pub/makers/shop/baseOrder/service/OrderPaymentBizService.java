package pub.makers.shop.baseOrder.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import pub.makers.shop.baseOrder.entity.OrderListPayment;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.PaymentQuery;
import pub.makers.shop.promotion.entity.PresellActivity;
import pub.makers.shop.tradeOrder.vo.OrderPayInfo;

public interface OrderPaymentBizService {
	
	void createPayment(OrderBizType orderBizType, List<OrderListPayment> paymentList);
	
	List<OrderListPayment> createPresellPayment(PresellActivity activity, String orderId, List<BigDecimal> paymentAmounts, OrderBizType orderBizType);

	OrderListPayment getByBaseOrder(PaymentQuery query);
	
	OrderListPayment getById(String id);
	
	/**
	 * 将支付单更新为已支付
	 * @param info
	 */
	void updateToPayed(OrderPayInfo info);


	OrderListPayment getOrderListPaymentByOrderId(PaymentQuery paymentQuery);

	OrderListPayment getFinalOrderListByBaseOrder(PaymentQuery paymentQuery);

	Map<String,List<OrderListPayment>> getPresellOrderListPaymentByOrderIds(List<String> orderIdList, OrderBizType purchase, OrderType presell);
	
	/**
	 * 调整待支付金额
	 * @param fixAmount
	 */
	void fixPaymentAmount(String orderId, BigDecimal amount, OrderBizType orderBizType);

	List<OrderListPayment> getPaymentList(String orderNo, OrderBizType purchase);
}
