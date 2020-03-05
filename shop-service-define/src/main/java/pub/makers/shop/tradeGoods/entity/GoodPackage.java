package pub.makers.shop.tradeGoods.entity;

import java.io.Serializable;
import java.util.Date;

public class GoodPackage implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Long id;		
	
	/** boom内码 */
	private String boomId;		
	
	/** 父项编码 */
	private String cinvcodeParent;		
	
	/** 库存 */
	private Integer baseQtyN;		
	
	/** 子项商品编码 */
	private String cinvcodeChild;		
	
	/** 套餐规格 */
	private String packSpec;		
	
	/** 套餐名称 */
	private String packName;		
	
	/** 上架状态 1上架 */
	private Integer status;		
	
	/** 原价 */
	private Double marketPrice;		
	
	/** 销售价 */
	private Double salePrice;		
	
	/** 基础销量 */
	private Integer baseSaleNum;		
	
	/** 销量 */
	private Integer saleNum;		
	
	/** 缩略图 */
	private String minPhoto;		
	
	/** 展示图 */
	private String showPhoto;		
	
	/** 详情图 逗号分割 */
	private String detailPhoto;		
	
	/**  */
	private Date createTime;		
	
	/**  */
	private Date updateTime;		
	
	/** 邮费规则 */
	private Long postid;		
	
	/** 库存 */
	private Long stock;		
	
	/** 上架未出售 */
	private Integer onSalesNo;		
	
	/** 已售未付款 */
	private Integer onPayNo;		
	
	/** 已售待发货 */
	private Integer onSendNo;		
	

	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setBoomId(String boomId){
		this.boomId = boomId;
	}
	
	public String getBoomId(){
		return boomId;
	}
	
	public void setCinvcodeParent(String cinvcodeParent){
		this.cinvcodeParent = cinvcodeParent;
	}
	
	public String getCinvcodeParent(){
		return cinvcodeParent;
	}
	
	public void setBaseQtyN(Integer baseQtyN){
		this.baseQtyN = baseQtyN;
	}
	
	public Integer getBaseQtyN(){
		return baseQtyN;
	}
	
	public void setCinvcodeChild(String cinvcodeChild){
		this.cinvcodeChild = cinvcodeChild;
	}
	
	public String getCinvcodeChild(){
		return cinvcodeChild;
	}
	
	public void setPackSpec(String packSpec){
		this.packSpec = packSpec;
	}
	
	public String getPackSpec(){
		return packSpec;
	}
	
	public void setPackName(String packName){
		this.packName = packName;
	}
	
	public String getPackName(){
		return packName;
	}
	
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public Integer getStatus(){
		return status;
	}
	
	public void setMarketPrice(Double marketPrice){
		this.marketPrice = marketPrice;
	}
	
	public Double getMarketPrice(){
		return marketPrice;
	}
	
	public void setSalePrice(Double salePrice){
		this.salePrice = salePrice;
	}
	
	public Double getSalePrice(){
		return salePrice;
	}
	
	public void setBaseSaleNum(Integer baseSaleNum){
		this.baseSaleNum = baseSaleNum;
	}
	
	public Integer getBaseSaleNum(){
		return baseSaleNum;
	}
	
	public void setSaleNum(Integer saleNum){
		this.saleNum = saleNum;
	}
	
	public Integer getSaleNum(){
		return saleNum;
	}
	
	public void setMinPhoto(String minPhoto){
		this.minPhoto = minPhoto;
	}
	
	public String getMinPhoto(){
		return minPhoto;
	}
	
	public void setShowPhoto(String showPhoto){
		this.showPhoto = showPhoto;
	}
	
	public String getShowPhoto(){
		return showPhoto;
	}
	
	public void setDetailPhoto(String detailPhoto){
		this.detailPhoto = detailPhoto;
	}
	
	public String getDetailPhoto(){
		return detailPhoto;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setPostid(Long postid){
		this.postid = postid;
	}
	
	public Long getPostid(){
		return postid;
	}
	
	public void setStock(Long stock){
		this.stock = stock;
	}
	
	public Long getStock(){
		return stock;
	}
	
	public void setOnSalesNo(Integer onSalesNo){
		this.onSalesNo = onSalesNo;
	}
	
	public Integer getOnSalesNo(){
		return onSalesNo;
	}
	
	public void setOnPayNo(Integer onPayNo){
		this.onPayNo = onPayNo;
	}
	
	public Integer getOnPayNo(){
		return onPayNo;
	}
	
	public void setOnSendNo(Integer onSendNo){
		this.onSendNo = onSendNo;
	}
	
	public Integer getOnSendNo(){
		return onSendNo;
	}
	
}
