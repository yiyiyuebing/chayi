package pub.makers.shop.baseOrder.pojo;

import java.io.Serializable;

import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;

public class PayParam implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6356668964466891915L;

	private String payWay;
	
	private String tradeType;
	
	private BaseOrder order;
	
	private OrderType orderType;
	
	private OrderBizType orderBizType;
	
	private String orderId;
	
	private String clientIp;
	
	private String openId;

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public BaseOrder getOrder() {
		return order;
	}

	public void setOrder(BaseOrder order) {
		this.order = order;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public OrderBizType getOrderBizType() {
		return orderBizType;
	}

	public void setOrderBizType(OrderBizType orderBizType) {
		this.orderBizType = orderBizType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
}
