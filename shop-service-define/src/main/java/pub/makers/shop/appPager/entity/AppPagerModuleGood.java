package pub.makers.shop.appPager.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class AppPagerModuleGood implements Serializable {
    private Long id;

    private Long goodId;

    private Long moduleId;

    private String isValid;

    private String delFlag;

    private String goodsImage;

    private Integer sort;//排序

    private String name;

    private String goodDesc;

    private String goodLink;

    private BigDecimal retailPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        if (retailPrice == null){
            retailPrice = BigDecimal.ZERO;
        }
        this.retailPrice = retailPrice;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid == null ? null : isValid.trim();
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage == null ? null : goodsImage.trim();
    }
}