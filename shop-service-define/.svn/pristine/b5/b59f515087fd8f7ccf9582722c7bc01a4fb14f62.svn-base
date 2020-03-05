package pub.makers.shop.baseOrder.service;

import pub.makers.shop.baseOrder.enums.OrderCancelType;
import pub.makers.shop.baseOrder.enums.OrderConfirmType;
import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.baseOrder.pojo.OrderDeleteInfo;
import pub.makers.shop.baseOrder.pojo.PayParam;
import pub.makers.shop.baseOrder.pojo.ShipNoticeInfo;
import pub.makers.shop.tradeOrder.vo.OrderPayInfo;
import pub.makers.shop.tradeOrder.vo.ShippingInfo;

/**
 * 基础的订单支持
 * @author apple
 *
 */
public interface BaseOrderSupport {
	
	/**
	 * 预创建订单，用于计算订单金额
	 * @param t
	 * @return
	 */
	BaseOrder preCreate(BaseOrder t);
	
	/**
	 * 创建订单
	 * @param t
	 * @return
	 */
	BaseOrder createOrder(BaseOrder t);
	
	
	void cancelOrder(String userId, String orderId, OrderCancelType cancelType);
	
	
	/**
	 * 去支付
	 * @param param
	 * @return
	 */
	String toPay(PayParam param);
	
	/**
	 * 订单付款
	 * @param payInfo
	 */
	void payOrder(OrderPayInfo payInfo);
	
	
	/**
	 * 订单发货
	 * @param si
	 */
	void shipmentOrder(ShippingInfo si);


	/**
	 * 订单确认收货
	 * @param userId 订单所属用户ID
	 * @param orderId 订单ID
	 * @param confirmType 确认类型：主动确认还是超时自动确认
	 * 
	 */
	void confirmReceipt(String userId, String orderId, OrderConfirmType confirmType);
	
	
	/**
	 * 订单免运费
	 * @param orderId
	 */
	void freeShipping(String orderId);

	/**
	 * 删除订单
	 */
	void deleteOrder(OrderDeleteInfo info);

	/**
	 * 提醒发货
	 */
	void shipNotice(ShipNoticeInfo info);
}
