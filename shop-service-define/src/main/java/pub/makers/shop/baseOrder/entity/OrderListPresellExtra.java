package pub.makers.shop.baseOrder.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderListPresellExtra implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private String id;		
	
	/** 订单ID */
	private String orderId;		
	
	/**  */
	private String orderListId;		
	
	private String goodSkuId;
	
	/**  */
	private BigDecimal presellFirst;		
	
	/**  */
	private BigDecimal presellEnd;

	/** 预售价 */
	private BigDecimal presellAmount;
	
	private int buyNumber;
	
	/** 删除状态 */
	private String delFlag;		
	
	/** 创建时间 */
	private Date dateCreated;		
	
	/** 更新时间 */
	private Date lastUpdated;	
	
	

	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setOrderId(String orderId){
		this.orderId = orderId;
	}
	
	public String getOrderId(){
		return orderId;
	}
	
	public void setOrderListId(String orderListId){
		this.orderListId = orderListId;
	}
	
	public String getOrderListId(){
		return orderListId;
	}
	
	public void setPresellFirst(BigDecimal presellFirst){
		this.presellFirst = presellFirst;
	}
	
	public BigDecimal getPresellFirst(){
		return presellFirst;
	}
	
	public void setPresellEnd(BigDecimal presellEnd){
		this.presellEnd = presellEnd;
	}
	
	public BigDecimal getPresellEnd(){
		return presellEnd;
	}
	
	public void setDelFlag(String delFlag){
		this.delFlag = delFlag;
	}
	
	public String getDelFlag(){
		return delFlag;
	}
	
	public void setDateCreated(Date dateCreated){
		this.dateCreated = dateCreated;
	}
	
	public Date getDateCreated(){
		return dateCreated;
	}
	
	public void setLastUpdated(Date lastUpdated){
		this.lastUpdated = lastUpdated;
	}
	
	public Date getLastUpdated(){
		return lastUpdated;
	}

	public String getGoodSkuId() {
		return goodSkuId;
	}

	public void setGoodSkuId(String goodSkuId) {
		this.goodSkuId = goodSkuId;
	}

	public BigDecimal getPresellAmount() {
		return presellAmount;
	}

	public void setPresellAmount(BigDecimal presellAmount) {
		this.presellAmount = presellAmount;
	}

	public int getBuyNumber() {
		return buyNumber;
	}

	public void setBuyNumber(int buyNumber) {
		this.buyNumber = buyNumber;
	}
	
}
