package pub.makers.shop.finance.vo;

import java.io.Serializable;

public class FinanceAccountsPayParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String u8orderId;
	private String u8AccountId;
	private String orderType;
	private Integer status;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getU8orderId() {
		return u8orderId;
	}
	public void setU8orderId(String u8orderId) {
		this.u8orderId = u8orderId;
	}
	public String getU8AccountId() {
		return u8AccountId;
	}
	public void setU8AccountId(String u8AccountId) {
		this.u8AccountId = u8AccountId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
