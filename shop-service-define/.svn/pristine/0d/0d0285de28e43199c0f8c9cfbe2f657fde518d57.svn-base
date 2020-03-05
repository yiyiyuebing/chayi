package pub.makers.shop.baseOrder.pojo;

import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderConfirmType;
import pub.makers.shop.baseOrder.enums.OrderType;

import java.io.Serializable;

public class OrderConfirmInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userId; 
	private String orderId;
	private OrderConfirmType confirmType;
	private OrderBizType orderBizType;
	private OrderType orderType;

	public OrderConfirmInfo() {
	}

	public OrderConfirmInfo(String userId, String orderId, OrderConfirmType confirmType, OrderBizType orderBizType, OrderType orderType) {
		this.userId = userId;
		this.orderId = orderId;
		this.confirmType = confirmType;
		this.orderBizType = orderBizType;
		this.orderType = orderType;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public OrderConfirmType getConfirmType() {
		return confirmType;
	}
	public void setConfirmType(OrderConfirmType confirmType) {
		this.confirmType = confirmType;
	}
	public OrderBizType getOrderBizType() {
		return orderBizType;
	}
	public void setOrderBizType(OrderBizType orderBizType) {
		this.orderBizType = orderBizType;
	}
	public OrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	
}
