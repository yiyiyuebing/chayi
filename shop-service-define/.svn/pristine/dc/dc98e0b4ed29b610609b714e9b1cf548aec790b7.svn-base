package pub.makers.shop.promotion.entity;

import pub.makers.shop.promotion.enums.SaleActivitySaleType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SaleActivityGood implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private String id;		
	
	/**  */
	private String activityId;		
	
	/**  */
	private String goodId;		
	
	/**  */
	private String goodSkuId;	
	
	/** 打折方式(打折/优惠) */
	private String saleType;		
	
	/**  */
	private BigDecimal discount;		
	
	/**  */
	private BigDecimal privilege;

	/** 活动价 */
	private BigDecimal activityPrice;
	
	/** 虚拟抢定人数 */
	private Integer vmNum;
	
	/** 虚拟抢定人数 */
	private Integer buyNum;
	
	/** 最大抢定人数 */
	private Integer maxNum;
	
	/** 是否有效 */
	private String isValid;		
	
	/** 删除状态 */
	private String delFlag;		
	
	/** 创建时间 */
	private Date dateCreated;		
	
	/** 更新时间 */
	private Date lastUpdated;
	
	private SaleActivity activity;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getPrivilege() {
		return privilege;
	}

	public void setPrivilege(BigDecimal privilege) {
		this.privilege = privilege;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getGoodSkuId() {
		return goodSkuId;
	}

	public void setGoodSkuId(String goodSkuId) {
		this.goodSkuId = goodSkuId;
	}

	public SaleActivity getActivity() {
		return activity;
	}

	public void setActivity(SaleActivity activity) {
		this.activity = activity;
	}		
	
	public BigDecimal calcDIscount(BigDecimal iptAmount){
		
		BigDecimal discountAmount = BigDecimal.ZERO;
		if (SaleActivitySaleType.discount.name().equals(saleType)){
			discountAmount = iptAmount.multiply(discount);
		}
		else if (SaleActivitySaleType.privilege.equals(saleType)){
			discountAmount = privilege;
		}
		
		return discountAmount;
	}

	public Integer getVmNum() {
		return vmNum;
	}

	public void setVmNum(Integer vmNum) {
		this.vmNum = vmNum;
	}

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public BigDecimal getActivityPrice() {
		return activityPrice;
	}

	public void setActivityPrice(BigDecimal activityPrice) {
		this.activityPrice = activityPrice;
	}
}
