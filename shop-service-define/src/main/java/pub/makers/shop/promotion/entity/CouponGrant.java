package pub.makers.shop.promotion.entity;

import java.io.Serializable;
import java.util.Date;

public class CouponGrant implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 记录ID */
	private String recordId;		
	
	/** 优惠券ID */
	private String couponId;		
	
	/** 用户ID */
	private String userId;		
	
	/** 订单ID */
	private String orderId;		
	
	/** 使用数量 */
	private Integer num;		
	
	/** 发放时间 */
	private Date grantTime;		
	
	/** 是否有效 */
	private String isValid;		
	
	/** 删除状态 */
	private String delFlag;		
	
	/** 创建时间 */
	private Date dateCreated;		
	
	/** 更新时间 */
	private Date lastUpdated;		
	
	/** 备注 */
	private String remaek;
	

	public void setRecordId(String recordId){
		this.recordId = recordId;
	}
	
	public String getRecordId(){
		return recordId;
	}
	
	public void setCouponId(String couponId){
		this.couponId = couponId;
	}
	
	public String getCouponId(){
		return couponId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
		return userId;
	}
	
	public void setOrderId(String orderId){
		this.orderId = orderId;
	}
	
	public String getOrderId(){
		return orderId;
	}
	
	public void setNum(Integer num){
		this.num = num;
	}
	
	public Integer getNum(){
		return num;
	}
	
	public void setGrantTime(Date grantTime){
		this.grantTime = grantTime;
	}
	
	public Date getGrantTime(){
		return grantTime;
	}
	
	public void setIsValid(String isValid){
		this.isValid = isValid;
	}
	
	public String getIsValid(){
		return isValid;
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

	public String getRemaek() {
		return remaek;
	}

	public void setRemaek(String remaek) {
		this.remaek = remaek;
	}
	
}
