package pub.makers.shop.promotion.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RuleDef implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private String ruleId;		
	
	/** 无限制（全站会员）、指定会员、会员组、会员级别 和 自定义的查询条件等。 */
	private String qualityType;		
	
	/**  */
	private Long qualityCfg;		
	
	/** jfdk 积分抵扣 manjian 满减金额 mianze 满减金额 赠送积分 赠品 优惠券 */
	private String intType;		
	
	/** 利益累加计算 */
	private String reuse;		
	
	/** 促销对象的类型spu,classify,all,custom */
	private String objType;		
	
	/** 促销对象的配置 */
	private String objCfg;		
	
	/** 计算金额处理方式(1向下取整 2四舍五入) */
	private String moneyFixMethod;		
	
	/** order 下单时 pay 支付时 */
	private String ruleTrigger;		
	
	/** 是否有效 */
	private String isValid;		
	
	/** 删除状态 */
	private String delFlag;		
	
	/** 创建时间 */
	private Date dateCreated;		
	
	/** 更新时间 */
	private Date lastUpdated;
	
	private transient List<RuleInt> ruleIntList;
	

	public void setRuleId(String ruleId){
		this.ruleId = ruleId;
	}
	
	public String getRuleId(){
		return ruleId;
	}
	
	public void setQualityType(String qualityType){
		this.qualityType = qualityType;
	}
	
	public String getQualityType(){
		return qualityType;
	}
	
	public void setQualityCfg(Long qualityCfg){
		this.qualityCfg = qualityCfg;
	}
	
	public Long getQualityCfg(){
		return qualityCfg;
	}
	
	public void setIntType(String intType){
		this.intType = intType;
	}
	
	public String getIntType(){
		return intType;
	}
	
	public void setReuse(String reuse){
		this.reuse = reuse;
	}
	
	public String getReuse(){
		return reuse;
	}
	
	public void setObjType(String objType){
		this.objType = objType;
	}
	
	public String getObjType(){
		return objType;
	}
	
	public void setObjCfg(String objCfg){
		this.objCfg = objCfg;
	}
	
	public String getObjCfg(){
		return objCfg;
	}
	
	public void setMoneyFixMethod(String moneyFixMethod){
		this.moneyFixMethod = moneyFixMethod;
	}
	
	public String getMoneyFixMethod(){
		return moneyFixMethod;
	}
	
	public void setRuleTrigger(String ruleTrigger){
		this.ruleTrigger = ruleTrigger;
	}
	
	public String getRuleTrigger(){
		return ruleTrigger;
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

	public List<RuleInt> getRuleIntList() {
		return ruleIntList;
	}

	public void setRuleIntList(List<RuleInt> ruleIntList) {
		this.ruleIntList = ruleIntList;
	}
	
}
