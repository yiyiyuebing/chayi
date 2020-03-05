package pub.makers.shop.promotion.entity;

import java.io.Serializable;
import java.util.Date;

public class CouponDef implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 优惠券定义ID */
	private String defId;		
	
	/** 优惠券规则ID */
	private String ruleId;		
	
	/** 优惠券名称 */
	private String couponName;		
	
	private Date startTime;
	
	private Date endTime;
	
	/** 是否有效 */
	private String isValid;		
	
	/** 删除状态 */
	private String delFlag;		
	
	/** 创建时间 */
	private Date dateCreated;		
	
	/** 更新时间 */
	private Date lastUpdated;		
	

	public void setDefId(String defId){
		this.defId = defId;
	}
	
	public String getDefId(){
		return defId;
	}
	
	public void setRuleId(String ruleId){
		this.ruleId = ruleId;
	}
	
	public String getRuleId(){
		return ruleId;
	}
	
	public void setCouponName(String couponName){
		this.couponName = couponName;
	}
	
	public String getCouponName(){
		return couponName;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
