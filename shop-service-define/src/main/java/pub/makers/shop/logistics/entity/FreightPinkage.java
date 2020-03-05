package pub.makers.shop.logistics.entity;

import java.io.Serializable;
import java.util.Date;

public class FreightPinkage implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** id */
	private String pinkageId;		
	
	/** id */
	private String tplId;		
	
	/** 排序号 */
	private Integer sort;		
	
	/** 地区ID */
	private String areaIds;		
	
	/** 运送方式ID */
	private String methodId;

	/** 运送方式名称 */
	private String methodName;
	
	/** 物流服务商ID */
	private String servicerId;		
	
	/** 规则类型 */
	private String condType;
	
	/** 物流服务商名称 */
	private String servicerName;
	
	/** 包邮条件 */
	private String freeCondName;		
	
	/** 包邮规则 */
	private String freeCondVal;		
	
	/** 是否有效 */
	private String isValid;		
	
	/** 删除状态 */
	private String delFlag;		
	
	/** 创建时间 */
	private Date dateCreated;		
	
	/** 更新时间 */
	private Date lastUpdated;

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setPinkageId(String pinkageId){
		this.pinkageId = pinkageId;
	}
	
	public String getPinkageId(){
		return pinkageId;
	}
	
	public void setTplId(String tplId){
		this.tplId = tplId;
	}
	
	public String getTplId(){
		return tplId;
	}
	
	public void setSort(Integer sort){
		this.sort = sort;
	}
	
	public Integer getSort(){
		return sort;
	}
	
	public void setAreaIds(String areaIds){
		this.areaIds = areaIds;
	}
	
	public String getAreaIds(){
		return areaIds;
	}
	
	public void setMethodId(String methodId){
		this.methodId = methodId;
	}
	
	public String getMethodId(){
		return methodId;
	}
	
	public void setServicerId(String servicerId){
		this.servicerId = servicerId;
	}
	
	public String getServicerId(){
		return servicerId;
	}
	
	public void setFreeCondName(String freeCondName){
		this.freeCondName = freeCondName;
	}
	
	public String getFreeCondName(){
		return freeCondName;
	}
	
	public void setFreeCondVal(String freeCondVal){
		this.freeCondVal = freeCondVal;
	}
	
	public String getFreeCondVal(){
		return freeCondVal;
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

	public String getServicerName() {
		return servicerName;
	}

	public void setServicerName(String servicerName) {
		this.servicerName = servicerName;
	}

	public String getCondType() {
		return condType;
	}

	public void setCondType(String condType) {
		this.condType = condType;
	}

	
}
