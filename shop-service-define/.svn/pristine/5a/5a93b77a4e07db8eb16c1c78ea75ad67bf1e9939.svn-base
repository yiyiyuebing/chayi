package pub.makers.shop.promotion.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2017/6/16.
 */
public class PromotionInfoDetailVo implements Serializable {

    private List<PromotionActivityVo> activityList;


    private BigDecimal startPrice;

    private BigDecimal endPrice;

    private BigDecimal startFirstAmount;

    private BigDecimal endFirstAmount;

    private String activeName;

    private String activityType;

    private Date activityEnd;

    private Date activityStart;

    private String agreement; //协议

    private Date paymentStart; //尾款开始支付时间

    private Date paymentEnd; //尾款结束支付时间

    private Date shipTime; //发货时间

    private String presellType; //预售类型

    private String goodsSkuId;

    private String presellAmount;

    /** 预售量 */
    private Integer presellNum;

    /** 销量 */
    private Integer saleNum;

    private Integer vmcount;

    private String tag;

    private String tag1; //大促标签

    private String presellDesc; //活动说明


    private String activityDesc; //活动说明

    private Boolean activityStartFlag; //活动开始标识

    public Boolean getActivityStartFlag() {
        return activityStartFlag;
    }

    public void setActivityStartFlag(Boolean activityStartFlag) {
        this.activityStartFlag = activityStartFlag;
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }

    public String getPresellDesc() {
        return presellDesc;
    }

    public void setPresellDesc(String presellDesc) {
        this.presellDesc = presellDesc;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getVmcount() {
        return vmcount;
    }

    public void setVmcount(Integer vmcount) {
        this.vmcount = vmcount;
    }

    public Integer getPresellNum() {
        return presellNum;
    }

    public void setPresellNum(Integer presellNum) {
        this.presellNum = presellNum;
    }

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    public String getPresellAmount() {
        return presellAmount;
    }

    public void setPresellAmount(String presellAmount) {
        this.presellAmount = presellAmount;
    }

    public String getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(String goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public String getPresellType() {
        return presellType;
    }

    public void setPresellType(String presellType) {
        this.presellType = presellType;
    }

    public List<PromotionActivityVo> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<PromotionActivityVo> activityList) {
        this.activityList = activityList;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public BigDecimal getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(BigDecimal endPrice) {
        this.endPrice = endPrice;
    }

    public BigDecimal getStartFirstAmount() {
        return startFirstAmount;
    }

    public void setStartFirstAmount(BigDecimal startFirstAmount) {
        this.startFirstAmount = startFirstAmount;
    }

    public BigDecimal getEndFirstAmount() {
        return endFirstAmount;
    }

    public void setEndFirstAmount(BigDecimal endFirstAmount) {
        this.endFirstAmount = endFirstAmount;
    }

    public String getActiveName() {
        return activeName;
    }

    public void setActiveName(String activeName) {
        this.activeName = activeName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public Date getActivityEnd() {
        return activityEnd;
    }

    public void setActivityEnd(Date activityEnd) {
        this.activityEnd = activityEnd;
    }

    public Date getActivityStart() {
        return activityStart;
    }

    public void setActivityStart(Date activityStart) {
        this.activityStart = activityStart;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public Date getPaymentStart() {
        return paymentStart;
    }

    public void setPaymentStart(Date paymentStart) {
        this.paymentStart = paymentStart;
    }

    public Date getPaymentEnd() {
        return paymentEnd;
    }

    public void setPaymentEnd(Date paymentEnd) {
        this.paymentEnd = paymentEnd;
    }

    public Date getShipTime() {
        return shipTime;
    }

    public void setShipTime(Date shipTime) {
        this.shipTime = shipTime;
    }


    public static PromotionInfoDetailVo  getPresellInfo(PresellPromotionActivityVo presellPromotionActivityVo) {
        PromotionInfoDetailVo goodPromotionalInfoVo = new PromotionInfoDetailVo();
        goodPromotionalInfoVo.setActiveName(presellPromotionActivityVo.getActivityFullName());
        goodPromotionalInfoVo.setActivityType(presellPromotionActivityVo.getActivityType());
        goodPromotionalInfoVo.setTag(presellPromotionActivityVo.getTag2());
        goodPromotionalInfoVo.setTag1(presellPromotionActivityVo.getTag1());
        goodPromotionalInfoVo.setAgreement(presellPromotionActivityVo.getPresellAgreement());
        goodPromotionalInfoVo.setActivityStart(presellPromotionActivityVo.getActivityStart());
        goodPromotionalInfoVo.setActivityEnd(presellPromotionActivityVo.getActivityEnd());
        goodPromotionalInfoVo.setPaymentStart(presellPromotionActivityVo.getPaymentStart());
        goodPromotionalInfoVo.setPaymentEnd(presellPromotionActivityVo.getPaymentEnd());
        goodPromotionalInfoVo.setActivityStartFlag(comparteDate(new Date(), presellPromotionActivityVo.getActivityStart()));
        goodPromotionalInfoVo.setShipTime(presellPromotionActivityVo.getShipTime());
        goodPromotionalInfoVo.setPresellDesc(presellPromotionActivityVo.getPresellDesc());
        goodPromotionalInfoVo.setPresellType(presellPromotionActivityVo.getPresellType());
        goodPromotionalInfoVo.setSaleNum(presellPromotionActivityVo.getSaleNum() == null ? 0 : presellPromotionActivityVo.getSaleNum());
        goodPromotionalInfoVo.setPresellNum(presellPromotionActivityVo.getPresellNum() == null ? 0 : presellPromotionActivityVo.getPresellNum());
        goodPromotionalInfoVo.setVmcount(presellPromotionActivityVo.getVmCount() == null ? 0 : presellPromotionActivityVo.getVmCount());
        goodPromotionalInfoVo.setPresellAmount(presellPromotionActivityVo.getPresellAmount().toString());
        return goodPromotionalInfoVo;
    }

    public static PromotionInfoDetailVo  getSaleInfo(SalePromotionActivityVo promotionActivityVo) {
        PromotionInfoDetailVo goodPromotionalInfoVo = new PromotionInfoDetailVo();
        goodPromotionalInfoVo.setActiveName(promotionActivityVo.getActivityFullName());
        goodPromotionalInfoVo.setActivityType(promotionActivityVo.getActivityType());
        goodPromotionalInfoVo.setTag(promotionActivityVo.getTag2());
        goodPromotionalInfoVo.setTag1(promotionActivityVo.getTag1());
        goodPromotionalInfoVo.setActivityStart(promotionActivityVo.getActivityStart());
        goodPromotionalInfoVo.setActivityEnd(promotionActivityVo.getActivityEnd());
        goodPromotionalInfoVo.setActivityStartFlag(comparteDate(new Date(), promotionActivityVo.getActivityStart()));
        goodPromotionalInfoVo.setSaleNum(promotionActivityVo.getBuyNum());
        goodPromotionalInfoVo.setVmcount(promotionActivityVo.getVmNum());
        goodPromotionalInfoVo.setActivityDesc(promotionActivityVo.getActivityDesc());
        return goodPromotionalInfoVo;
    }

    private static Boolean comparteDate(Date date1, Date date2) {
        Long dateLong1 = date1.getTime();
        Long dateLong2 = date2.getTime();
        if (dateLong1 > dateLong2) {
            return true;
        }
        return false;
    }
}
