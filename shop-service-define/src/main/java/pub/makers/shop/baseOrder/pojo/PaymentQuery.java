package pub.makers.shop.baseOrder.pojo;

import java.io.Serializable;

import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;

public class PaymentQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BaseOrder order;
	
	private OrderBizType orderBizType;
	
	private OrderType orderType;

	private Integer stageNum;

	public Integer getStageNum() {
		return stageNum;
	}

	public void setStageNum(Integer stageNum) {
		this.stageNum = stageNum;
	}

	public BaseOrder getOrder() {
		return order;
	}

	public void setOrder(BaseOrder order) {
		this.order = order;
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
