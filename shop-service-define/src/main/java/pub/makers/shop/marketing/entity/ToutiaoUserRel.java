package pub.makers.shop.marketing.entity;

import java.io.Serializable;
import java.util.Date;

public class ToutiaoUserRel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private String relId;		
	
	/** 头条类别(b端还是c端) */
	private String toutiaoId;		
	
	/** 店铺ID */
	private String userId;		
	
	/** 是否删除 */
	private String delFlag;		
	
	/** 是否已读 */
	private String readFlag;	
	
	private String classify;
	
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
	
	public void setToutiaoId(String toutiaoId){
		this.toutiaoId = toutiaoId;
	}
	
	public String getToutiaoId(){
		return toutiaoId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
		return userId;
	}
	
	public void setDelFlag(String delFlag){
		this.delFlag = delFlag;
	}
	
	public String getDelFlag(){
		return delFlag;
	}
	
	public void setReadFlag(String readFlag){
		this.readFlag = readFlag;
	}
	
	public String getReadFlag(){
		return readFlag;
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

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	
}
