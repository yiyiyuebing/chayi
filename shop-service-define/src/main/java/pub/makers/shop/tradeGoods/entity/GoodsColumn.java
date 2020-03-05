package pub.makers.shop.tradeGoods.entity;


import java.io.Serializable;
import java.util.Date;

public class GoodsColumn implements Serializable {
    private Long id;

    private Long shopId;

    private String columnName;

    private Long ruleSourceId;

    private Integer orderBy;

    private Integer status;

    private String showyn;

    private String showpicture;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    private Long introduceImage;//展示图id

    private Long slideshowImages;//轮播图id

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Long getRuleSourceId() {
        return ruleSourceId;
    }

    public void setRuleSourceId(Long ruleSourceId) {
        this.ruleSourceId = ruleSourceId;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getShowyn() {
        return showyn;
    }

    public void setShowyn(String showyn) {
        this.showyn = showyn;
    }

    public String getShowpicture() {
        return showpicture;
    }

    public void setShowpicture(String showpicture) {
        this.showpicture = showpicture;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getIntroduceImage() {
        return introduceImage;
    }

    public void setIntroduceImage(Long introduceImage) {
        this.introduceImage = introduceImage;
    }

    public Long getSlideshowImages() {
        return slideshowImages;
    }

    public void setSlideshowImages(Long slideshowImages) {
        this.slideshowImages = slideshowImages;
    }
}