package pub.makers.shop.marketing.entity;

import java.io.Serializable;
import java.util.Date;

public class OnlineStudy implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private Long ID;		
	
	/** å­¦ä¹ æ ‡é¢˜ */
	private String title;		
	
	/** å­¦ä¹ åˆ†ç±» */
	private Long studyType;		
	
	/** å­¦ä¹ å­åˆ†ç±» */
	private String studyChildType;
	
	/** ç±»åž‹(1:æ–‡ç« ,2:è§†é¢‘) */
	private Long type;		
	
	/** é˜…è¯»é‡ */
	private Long readNum;		
	
	/** ä½œè€… */
	private String author;		
	
	/** è§†é¢‘åœ°å€ */
	private String videoUrl;		
	
	/** æ–‡ç« å†…å®¹ */
	private String content;		
	
	/** å°é¢å›¾ */
	private String covePic;		
	
	/** åˆ›å»ºæ—¶é—´ */
	private Date createTime;		
	
	/** åˆ›å»ºè€… */
	private Long createBy;		
	
	/** æ›´æ–°æ—¶é—´ */
	private Date updateTime;		
	
	/** æ›´æ–°è€… */
	private Long updateBy;		
	
	/** é™„ä»¶ */
	private String file;		
	
	/** æ ‡ç­¾ */
	private String label;		
	
	/** 0å¯æ’­ 1ç¦æ’­ */
	private Integer videoShow;		
	
	private String studyTypeName;

	private String studyTag;

	private Integer isShare;

	private String material;

	public Integer getIsShare() {
		return isShare;
	}

	public void setIsShare(Integer isShare) {
		this.isShare = isShare;
	}

	public String getStudyTag() {
		return studyTag;
	}

	public void setStudyTag(String studyTag) {
		this.studyTag = studyTag;
	}

	public void setID(Long ID){
		this.ID = ID;
	}
	
	public Long getID(){
		return ID;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setStudyType(Long studyType){
		this.studyType = studyType;
	}
	
	public Long getStudyType(){
		return studyType;
	}

	public String getStudyChildType() {
		return studyChildType;
	}

	public void setStudyChildType(String studyChildType) {
		this.studyChildType = studyChildType;
	}

	public void setType(Long type){
		this.type = type;
	}
	
	public Long getType(){
		return type;
	}
	
	public void setReadNum(Long readNum){
		this.readNum = readNum;
	}
	
	public Long getReadNum(){
		return readNum;
	}
	
	public void setAuthor(String author){
		this.author = author;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public void setVideoUrl(String videoUrl){
		this.videoUrl = videoUrl;
	}
	
	public String getVideoUrl(){
		return videoUrl;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setCovePic(String covePic){
		this.covePic = covePic;
	}
	
	public String getCovePic(){
		return covePic;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateBy(Long createBy){
		this.createBy = createBy;
	}
	
	public Long getCreateBy(){
		return createBy;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateBy(Long updateBy){
		this.updateBy = updateBy;
	}
	
	public Long getUpdateBy(){
		return updateBy;
	}
	
	public void setFile(String file){
		this.file = file;
	}
	
	public String getFile(){
		return file;
	}
	
	public void setLabel(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return label;
	}
	
	public void setVideoShow(Integer videoShow){
		this.videoShow = videoShow;
	}
	
	public Integer getVideoShow(){
		return videoShow;
	}

	public String getStudyTypeName() {
		return studyTypeName;
	}

	public void setStudyTypeName(String studyTypeName) {
		this.studyTypeName = studyTypeName;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
}
