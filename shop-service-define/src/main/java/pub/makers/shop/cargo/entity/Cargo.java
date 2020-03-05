package pub.makers.shop.cargo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Cargo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Long id;		
	
	/** 供应商 */
	private Long supplierId;		
	
	/** 品牌 */
	private Long brandId;		
	
	/** 所属分类 */
	private Long classifyId;

	/**
	 * 一级类目
	 */
	private Long parentClassifyId;
	
	/** 名称 */
	private String name;		
	
	/** 货品号 */
	private String cargoNo;		
	
	/** 描述 */
	private String description;		
	
	/** 缩略图 */
	private Long smallImageId;		
	
	/** 展示图 */
	private Long showImageGroupId;		
	
	/** 货品详情 */
	private Long detailImageGroupId;		
	
	/** 创建时间 */
	private Date createTime;		
	
	/** 创建者 */
	private Long createBy;		
	
	/** 更新时间 */
	private Date updateTime;		
	
	/** 更新者 */
	private Long updateBy;


	/** 移动端轮播 */
	private Long mobileAlbumId;

	/** 移动端轮播 */
	private Long pcAlbumId;

	private String mobileDetailInfo;

	private String pcDetailInfo;

	private String afterSell;

	private String weight;

	private String volume;

	private String delFlag;

	private String isSancha;

	private String cargoType;

	private BigDecimal fixedPrice;

	private String weightUnit;

	private String volumeUnit;

	public String getWeightUnit(){
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public String getVolumeUnit() {
		return volumeUnit;
	}

	public void setVolumeUnit(String volumeUnit) {
		this.volumeUnit = volumeUnit;
	}

	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	public String getIsSancha() {
		return isSancha;
	}

	public void setIsSancha(String isSancha) {
		this.isSancha = isSancha;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Long getParentClassifyId() {
		return parentClassifyId;
	}

	public void setParentClassifyId(Long parentClassifyId) {
		this.parentClassifyId = parentClassifyId;
	}

	public String getAfterSell() {
		return afterSell;
	}

	public void setAfterSell(String afterSell) {
		this.afterSell = afterSell;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public Long getMobileAlbumId() {
		return mobileAlbumId;
	}

	public void setMobileAlbumId(Long mobileAlbumId) {
		this.mobileAlbumId = mobileAlbumId;
	}

	public Long getPcAlbumId() {
		return pcAlbumId;
	}

	public void setPcAlbumId(Long pcAlbumId) {
		this.pcAlbumId = pcAlbumId;
	}

	public String getMobileDetailInfo() {
		return mobileDetailInfo;
	}

	public void setMobileDetailInfo(String mobileDetailInfo) {
		this.mobileDetailInfo = mobileDetailInfo;
	}

	public String getPcDetailInfo() {
		return pcDetailInfo;
	}

	public void setPcDetailInfo(String pcDetailInfo) {
		this.pcDetailInfo = pcDetailInfo;
	}

	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setSupplierId(Long supplierId){
		this.supplierId = supplierId;
	}
	
	public Long getSupplierId(){
		return supplierId;
	}
	
	public void setBrandId(Long brandId){
		this.brandId = brandId;
	}
	
	public Long getBrandId(){
		return brandId;
	}
	
	public void setClassifyId(Long classifyId){
		this.classifyId = classifyId;
	}
	
	public Long getClassifyId(){
		return classifyId;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setCargoNo(String cargoNo){
		this.cargoNo = cargoNo;
	}
	
	public String getCargoNo(){
		return cargoNo;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setSmallImageId(Long smallImageId){
		this.smallImageId = smallImageId;
	}
	
	public Long getSmallImageId(){
		return smallImageId;
	}
	
	public void setShowImageGroupId(Long showImageGroupId){
		this.showImageGroupId = showImageGroupId;
	}
	
	public Long getShowImageGroupId(){
		return showImageGroupId;
	}
	
	public void setDetailImageGroupId(Long detailImageGroupId){
		this.detailImageGroupId = detailImageGroupId;
	}
	
	public Long getDetailImageGroupId(){
		return detailImageGroupId;
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

	public BigDecimal getFixedPrice() {
		return fixedPrice;
	}

	public void setFixedPrice(BigDecimal fixedPrice) {
		this.fixedPrice = fixedPrice;
	}
}
