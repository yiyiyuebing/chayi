package pub.makers.shop.logistics.entity;

import java.io.Serializable;
import java.util.Date;

public class FreightTplGoodRel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** id */
	private String relId;		
	
	/** 商品SKUID */
	private String goodSkuId;		
	
	/** 订单类型 */
	private String tplId;		
	
	/** 商品类型(trade 商城商品 purchase 进货商品) */
	private String relType;		
	
	private String isSample;
	
	/** 是否有效 */
	private String isValid;		
	
	/** 删除状态 */
	private String delFlag;		
	
	/** 创建时间 */
	private Date dateCreated;		
	
	/** 更新时间 */
	private Date lastUpdated;		
	

	public void setRelId(String relId){
		this.relId = relId;
	}
	
	public String getRelId(){
		return relId;
	}
	
	
	public String getGoodSkuId() {
		return goodSkuId;
	}

	public void setGoodSkuId(String goodSkuId) {
		this.goodSkuId = goodSkuId;
	}

	public void setTplId(String tplId){
		this.tplId = tplId;
	}
	
	public String getTplId(){
		return tplId;
	}
	
	public void setRelType(String relType){
		this.relType = relType;
	}
	
	public String getRelType(){
		return relType;
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

	public String getIsSample() {
		return isSample;
	}

	public void setIsSample(String isSample) {
		this.isSample = isSample;
	}
	
}
