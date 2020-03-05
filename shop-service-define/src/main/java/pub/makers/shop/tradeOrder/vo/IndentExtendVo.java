package pub.makers.shop.tradeOrder.vo;

import pub.makers.shop.store.entity.Subbranch;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IndentExtendVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String shopId;
	private BigDecimal totalAmount;
	private BigDecimal paymentAmount;
	private BigDecimal carriage = new BigDecimal(0.00);
	private Date createTime;
	private int count;
	private String type;
	private String receiver;
	private String status;
	private String indentStatus;
	private String dealStatus;
	private String expressNumber;
	private String expressCompany;
	private String picUrl;
	private String buyerCarriage;
	private Subbranch subAccount;
	private Integer billStatus;
	private String flowStatus;
	private String flowStatusStr;

	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public BigDecimal getCarriage() {
		return carriage;
	}
	public void setCarriage(BigDecimal carriage) {
		this.carriage = carriage;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIndentStatus() {
		return indentStatus;
	}
	public void setIndentStatus(String indentStatus) {
		this.indentStatus = indentStatus;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getBuyerCarriage() {
		return buyerCarriage;
	}
	public void setBuyerCarriage(String buyerCarriage) {
		this.buyerCarriage = buyerCarriage;
	}
	public Subbranch getSubAccount() {
		return subAccount;
	}
	public void setSubAccount(Subbranch subAccount) {
		this.subAccount = subAccount;
	}

	public Integer getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(Integer billStatus) {
		this.billStatus = billStatus;
	}

	public String getFlowStatus() {
		return flowStatus;
	}

	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}

	public String getFlowStatusStr() {
		return flowStatusStr;
	}

	public void setFlowStatusStr(String flowStatusStr) {
		this.flowStatusStr = flowStatusStr;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
}
