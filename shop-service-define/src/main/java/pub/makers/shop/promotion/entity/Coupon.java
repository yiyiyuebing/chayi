package pub.makers.shop.promotion.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Coupon implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 优惠券ID */
	private String couponId;		
	
	/** 优惠券定义ID */
	private String defId;		
	
	/** 用户ID */
	private String userId;		
	
	/** 优惠券数量 */
	private BigDecimal num;		
	
	/** 是否有效 */
	private String isValid;		
	
	/** 删除状态 */
	private String delFlag;		
	
	/** 创建时间 */
	private Date dateCreated;		
	
	/** 更新时间 */
	private Date lastUpdated;		
	
	/* 优惠券类型 **/
	private String itemType;
	
	private transient RuleDef ruleDef;
	private transient String ruleId;
	

	public void setCouponId(String couponId){
		this.couponId = couponId;
	}
	
	public String getCouponId(){
		return couponId;
	}
	
	public void setDefId(String defId){
		this.defId = defId;
	}
	
	public String getDefId(){
		return defId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
		return userId;
	}
	
	public void setNum(BigDecimal num){
		this.num = num;
	}
	
	public BigDecimal getNum(){
		return num;
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

	public RuleDef getRuleDef() {
		return ruleDef;
	}

	public void setRuleDef(RuleDef ruleDef) {
		this.ruleDef = ruleDef;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	
}
