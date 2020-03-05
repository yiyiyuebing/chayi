package pub.makers.shop.promotion.vo;

import pub.makers.shop.promotion.entity.SaleActivityGood;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by daiwenfa on 2017/6/21.
 */
public class PresellGoodVo implements Serializable {
    private String id;
    private String pcAlbumId;
    private String image;
    private String name;
    private String skuId;//skuId
    private String goodId;//goodId
    private BigDecimal retailPrice;//
    private BigDecimal referencePrice;//参考价
    private BigDecimal presellAmount;//预售价
    private BigDecimal firstAmount;//定金
    private Integer vmCount;//虚拟预售数量
    private String isJoin;//是否参加
    private Integer presellNum;//预售数量
    private BigDecimal purchaseOnePrice;//零售价

    private String activityId;
    private BigDecimal saleNum;//
    private BigDecimal lessMoney;//减几元
    private BigDecimal activityMoney;//活动价格
    private String cargoSkuName; //  8.35g*24泡,200g,盒  sku名字
    private Integer vmNum; //虚拟抢订人数
    private Integer maxNum; //可售卖数量
    private String saleType;//是 打折 还 是 优惠
    private BigDecimal disCount; //打几折
    private List<SaleActivityGood> saleActivityGoodList;//已参加的打折活动
    private Boolean isValid; //是否参见过活动
    private String goodDesc;//商品描述
    private String groupId;
    private String goodLink;//商品链接

    public String getGoodDesc() {
        return goodDesc;
    }

    public void setGoodDesc(String goodDesc) {
        this.goodDesc = goodDesc;
    }

    public String getGoodLink() {
        return goodLink;
    }

    public void setGoodLink(String goodLink) {
        this.goodLink = goodLink;
    }

    public BigDecimal getPurchaseOnePrice() {
        return purchaseOnePrice;
    }

    public void setPurchaseOnePrice(BigDecimal purchaseOnePrice) {
        this.purchaseOnePrice = purchaseOnePrice;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    private List<ManZenAndPresellVo> list;//已参加的所有活动

    public List<ManZenAndPresellVo> getList() {
        return list;
    }

    public void setList(List<ManZenAndPresellVo> list) {
        this.list = list;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public BigDecimal getDisCount() {
        return disCount;
    }

    public void setDisCount(BigDecimal disCount) {
        this.disCount = disCount;
    }

    public String getCargoSkuName() {
        return cargoSkuName;
    }

    public void setCargoSkuName(String cargoSkuName) {
        this.cargoSkuName = cargoSkuName;
    }

    public Integer getVmNum() {
        return vmNum;
    }

    public void setVmNum(Integer vmNum) {
        this.vmNum = vmNum;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public BigDecimal getLessMoney() {
        return lessMoney;
    }

    public void setLessMoney(BigDecimal lessMoney) {
        this.lessMoney = lessMoney;
    }

    public BigDecimal getActivityMoney() {
        return activityMoney;
    }

    public void setActivityMoney(BigDecimal activityMoney) {
        this.activityMoney = activityMoney;
    }

    public List<SaleActivityGood> getSaleActivityGoodList() {
        return saleActivityGoodList;
    }

    public void setSaleActivityGoodList(List<SaleActivityGood> saleActivityGoodList) {
        this.saleActivityGoodList = saleActivityGoodList;
    }

    public BigDecimal getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(BigDecimal saleNum) {
        this.saleNum = saleNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(BigDecimal referencePrice) {
        this.referencePrice = referencePrice;
    }

    public BigDecimal getPresellAmount() {
        return presellAmount;
    }

    public void setPresellAmount(BigDecimal presellAmount) {
        this.presellAmount = presellAmount;
    }

    public BigDecimal getFirstAmount() {
        return firstAmount;
    }

    public void setFirstAmount(BigDecimal firstAmount) {
        this.firstAmount = firstAmount;
    }

    public Integer getVmCount() {
        return vmCount;
    }

    public void setVmCount(Integer vmCount) {
        this.vmCount = vmCount;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(String isJoin) {
        this.isJoin = isJoin;
    }

    public Integer getPresellNum() {
        return presellNum;
    }

    public void setPresellNum(Integer presellNum) {
        this.presellNum = presellNum;
    }

    public String getPcAlbumId() {
        return pcAlbumId;
    }

    public void setPcAlbumId(String pcAlbumId) {
        this.pcAlbumId = pcAlbumId;
    }
}
