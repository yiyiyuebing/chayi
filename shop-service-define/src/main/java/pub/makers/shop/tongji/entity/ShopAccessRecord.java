package pub.makers.shop.tongji.entity;

import java.io.Serializable;
import java.util.Date;

public class ShopAccessRecord implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** id */
	private String id;		
	
	private String requestId;
	
	/** 用户ID */
	private String userId;		
	
	/** 店铺ID */
	private String shopId;		
	
	/** 访问地址 */
	private String uri;		
	
	/** 页面参数 */
	private String query;		
	
	/** 页面类型 */
	private String pageType;		
	
	/** 页面标识 */
	private String pageId;		
	
	/** 进入时间 */
	private Date enterTime;		
	
	/** 离开时间 */
	private Date leaveTime;		
	
	/** 访问时长(秒) */
	private Integer stayTime;		
	
	/** 访问渠道 */
	private String channel;		
	
	/** 设备类型(IOS/ANDROID/BROWSE) */
	private String deviceType;		
	
	/** 浏览器的agent */
	private String browseAgent;		
	
	/** 是否有效 */
	private String isValid;		
	
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
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
		return userId;
	}
	
	public void setShopId(String shopId){
		this.shopId = shopId;
	}
	
	public String getShopId(){
		return shopId;
	}
	
	public void setUri(String uri){
		this.uri = uri;
	}
	
	public String getUri(){
		return uri;
	}
	
	public void setQuery(String query){
		this.query = query;
	}
	
	public String getQuery(){
		return query;
	}
	
	public void setPageType(String pageType){
		this.pageType = pageType;
	}
	
	public String getPageType(){
		return pageType;
	}
	
	public void setPageId(String pageId){
		this.pageId = pageId;
	}
	
	public String getPageId(){
		return pageId;
	}
	
	public void setEnterTime(Date enterTime){
		this.enterTime = enterTime;
	}
	
	public Date getEnterTime(){
		return enterTime;
	}
	
	public void setLeaveTime(Date leaveTime){
		this.leaveTime = leaveTime;
	}
	
	public Date getLeaveTime(){
		return leaveTime;
	}
	
	public void setStayTime(Integer stayTime){
		this.stayTime = stayTime;
	}
	
	public Integer getStayTime(){
		return stayTime;
	}
	
	public void setChannel(String channel){
		this.channel = channel;
	}
	
	public String getChannel(){
		return channel;
	}
	
	public void setDeviceType(String deviceType){
		this.deviceType = deviceType;
	}
	
	public String getDeviceType(){
		return deviceType;
	}
	
	public String getBrowseAgent() {
		return browseAgent;
	}

	public void setBrowseAgent(String browseAgent) {
		this.browseAgent = browseAgent;
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

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
}
