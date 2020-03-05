package pub.makers.shop.marketing.entity;

import java.io.Serializable;
import java.util.Date;

public class Toutiao implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private String toutiaoId;		
	
	/** 目标对象(b端还是c端) */
	private String target;		
	
	/** 标签 */
	private String tag;	
	
	/** 分类 */
	private String classify;	
	
	/** 标题 */
	private String title;		
	
	/** 链接地址 */
	private String url;		
	
	/** 图文内容 */
	private String content;		
	
	/** 排序行 */
	private Long sort;
	
	/** 有效期开始 */
	private Date validStart;		
	
	/** 有效期结束 */
	private Date validEnd;		
	
	/** 是否有效 */
	private String isValid;		
	
	/** 创建时间 */
	private Date dateCreated;		
	
	/** 更新时间 */
	private Date lastUpdated;	
	
	private ToutiaoUserRel userRel;
	
	private Date createTime;

	public Date getCreateTime() {
		if (this.dateCreated != null) {
			return dateCreated;
		}
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setToutiaoId(String toutiaoId){
		this.toutiaoId = toutiaoId;
	}
	
	public String getToutiaoId(){
		return toutiaoId;
	}
	
	public void setTarget(String target){
		this.target = target;
	}
	
	public String getTarget(){
		return target;
	}
	
	public void setTag(String tag){
		this.tag = tag;
	}
	
	public String getTag(){
		return tag;
	}
	
	public void setClassify(String classify){
		this.classify = classify;
	}
	
	public String getClassify(){
		return classify;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return content;
	}

	public void setValidStart(Date validStart){
		this.validStart = validStart;
	}
	
	public Date getValidStart(){
		return validStart;
	}
	
	public void setValidEnd(Date validEnd){
		this.validEnd = validEnd;
	}
	
	public Date getValidEnd(){
		return validEnd;
	}
	
	public void setIsValid(String isValid){
		this.isValid = isValid;
	}
	
	public String getIsValid(){
		return isValid;
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

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public ToutiaoUserRel getUserRel() {
		return userRel;
	}

	public void setUserRel(ToutiaoUserRel userRel) {
		this.userRel = userRel;
	}
	
}
