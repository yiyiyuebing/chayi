package pub.makers.shop.promotion.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dy on 2017/8/25.
 */
public class ActivityGoodVo implements Serializable {

    private String id;
    private String pcAlbumId;
    private String image;
    private String name;
    private Integer u8Stock;
    private String skuId; //skuId
    private String goodId; //goodId
    private BigDecimal retailPrice; //零售价
    private BigDecimal referencePrice; //参考价
    private String isJoin; //是否参加
    private String ruleId;
    private List<ManZenAndPresellVo> manZenAndPresellVoList;

    public List<ManZenAndPresellVo> getManZenAndPresellVoList() {
        return manZenAndPresellVoList;
    }

    public void setManZenAndPresellVoList(List<ManZenAndPresellVo> manZenAndPresellVoList) {
        this.manZenAndPresellVoList = manZenAndPresellVoList;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPcAlbumId() {
        return pcAlbumId;
    }

    public void setPcAlbumId(String pcAlbumId) {
        this.pcAlbumId = pcAlbumId;
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

    public Integer getU8Stock() {
        return u8Stock;
    }

    public void setU8Stock(Integer u8Stock) {
        this.u8Stock = u8Stock;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
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

    public String getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(String isJoin) {
        this.isJoin = isJoin;
    }
}
