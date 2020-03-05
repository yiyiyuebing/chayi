package pub.makers.shop.baseOrder.service;

import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.baseOrder.pojo.OrderCancelInfo;
import pub.makers.shop.baseOrder.pojo.OrderConfirmInfo;
import pub.makers.shop.baseOrder.pojo.OrderDeleteInfo;
import pub.makers.shop.baseOrder.pojo.OrderSubmitInfo;
import pub.makers.shop.baseOrder.pojo.PayParam;
import pub.makers.shop.baseOrder.pojo.ShipNoticeInfo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.tradeOrder.vo.OrderPayInfo;
import pub.makers.shop.tradeOrder.vo.ShippingInfo;

/**
 * 订单管理器
 * 对订单进行业务操作的入口
 * @author apple
 *
 */
public interface BaseOrderManager {

	/**
	 * 创建订单
	 * @param t
	 * @return
	 */
	BaseOrder createOrder(OrderSubmitInfo info);
	
	
	void cancelOrder(OrderCancelInfo cancelInfo);
	
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
	void confirmReceipt(OrderConfirmInfo info);
	
	
	/**
	 * 订单免运费
	 * @param orderId
	 */
	void freeShipping(ShippingInfo si);
	
	/**
	 * 去支付
	 * @param param
	 * @return
	 */
	String toPay(PayParam param);

	/**
	 * 删除订单
	 */
	void deleteOrder(OrderDeleteInfo info);

	/**
	 * 提醒发货
	 */
	void shipNotice(ShipNoticeInfo info);
	
	/**
	 * 预览订单概况
	 * @param order
	 * @return
	 */
	BaseOrder preview(BaseOrder order);
}
