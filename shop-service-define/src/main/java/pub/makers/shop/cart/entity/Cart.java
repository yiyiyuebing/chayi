package pub.makers.shop.cart.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Cart implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Long id;		
	
	/** 商品id */
	private Long goodsId;		
	
	/** 店铺id */
	private Long shopId;		
	
	/** 用户id */
	private Long userId;		
	
	/** 商品数量 */
	private Integer goodsCount;		
	
	/** 商品价格 */
	private BigDecimal goodsPrize;
	
	/** 创建时间 */
	private Date createDate;		
	
	/** 更新时间 */
	private Date updateDate;		
	
	/**  */
	private String delFlag;
	

	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setGoodsId(Long goodsId){
		this.goodsId = goodsId;
	}
	
	public Long getGoodsId(){
		return goodsId;
	}
	
	public void setShopId(Long shopId){
		this.shopId = shopId;
	}
	
	public Long getShopId(){
		return shopId;
	}
	
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	public Long getUserId(){
		return userId;
	}
	
	public void setGoodsCount(Integer goodsCount){
		this.goodsCount = goodsCount;
	}
	
	public Integer getGoodsCount(){
		return goodsCount;
	}
	
	public void setGoodsPrize(BigDecimal goodsPrize){
		this.goodsPrize = goodsPrize;
	}
	
	public BigDecimal getGoodsPrize(){
		return goodsPrize;
	}
	
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	
	public Date getCreateDate(){
		return createDate;
	}
	
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}
	
	public Date getUpdateDate(){
		return updateDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}
