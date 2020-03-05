package pub.makers.shop.tradeGoods.vo;


import pub.makers.shop.cargo.entity.vo.ImageGroupVo;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.tradeGoods.entity.GoodsColumn;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**   
* @Title: GoodsColumnVo.java
* @Package com.club.web.store.vo 
* @Description: 商品基础栏目VO 
* @author hqLin   
* @date 2016/03/30
* @version V1.0   
*/
public class GoodsColumnVo implements Serializable{
    private String id;

    private String shopId;

    private String columnName;

    private String ruleSourceId;

    private Integer orderBy;

    private Integer status;

    private String showyn;

    private String showpicture;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;
    
    private String ShopFlag;
    
    private String ruleId;
    
    private String valueId;
    
    private String ruleVal;
    
    private String ruleId2;
    
    private Date ruleStarttime;

    private Date ruleEndtime;
    
    private String sourceId;
    
    private Integer ruleNumber;
    
    private String ruleName;
    
    private String showpictureId;

    private String introduceImage;//展示图id

    private String slideshowImages;//轮播图id

    private List<String> slideshowImagesUrl;//轮播图

    private ImageGroupVo imageGroupVo;             //存储轮播图

    private ImageVo imageVo;                //展示图

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getRuleSourceId() {
        return ruleSourceId;
    }

    public void setRuleSourceId(String ruleSourceId) {
        this.ruleSourceId = ruleSourceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
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

    public String getShopFlag() {
        return ShopFlag;
    }

    public void setShopFlag(String shopFlag) {
        ShopFlag = shopFlag;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public String getRuleVal() {
        return ruleVal;
    }

    public void setRuleVal(String ruleVal) {
        this.ruleVal = ruleVal;
    }

    public String getRuleId2() {
        return ruleId2;
    }

    public void setRuleId2(String ruleId2) {
        this.ruleId2 = ruleId2;
    }

    public Date getRuleStarttime() {
        return ruleStarttime;
    }

    public void setRuleStarttime(Date ruleStarttime) {
        this.ruleStarttime = ruleStarttime;
    }

    public Date getRuleEndtime() {
        return ruleEndtime;
    }

    public void setRuleEndtime(Date ruleEndtime) {
        this.ruleEndtime = ruleEndtime;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Integer getRuleNumber() {
        return ruleNumber;
    }

    public void setRuleNumber(Integer ruleNumber) {
        this.ruleNumber = ruleNumber;
    }

    public String getShowpictureId() {
        return showpictureId;
    }

    public void setShowpictureId(String showpictureId) {
        this.showpictureId = showpictureId;
    }

    public String getIntroduceImage() {
        return introduceImage;
    }

    public void setIntroduceImage(String introduceImage) {
        this.introduceImage = introduceImage;
    }

    public String getSlideshowImages() {
        return slideshowImages;
    }

    public void setSlideshowImages(String slideshowImages) {
        this.slideshowImages = slideshowImages;
    }

    public List<String> getSlideshowImagesUrl() {
        return slideshowImagesUrl;
    }

    public void setSlideshowImagesUrl(List<String> slideshowImagesUrl) {
        this.slideshowImagesUrl = slideshowImagesUrl;
    }

    public ImageGroupVo getImageGroupVo() {
        return imageGroupVo;
    }

    public void setImageGroupVo(ImageGroupVo imageGroupVo) {
        this.imageGroupVo = imageGroupVo;
    }

    public ImageVo getImageVo() {
        return imageVo;
    }

    public void setImageVo(ImageVo imageVo) {
        this.imageVo = imageVo;
    }

    public GoodsColumnVo(GoodsColumn column) {
        this.id = column.getId() == null ? null : column.getId().toString();
        this.shopId = column.getShopId() == null ? null : column.getShopId().toString();
        this.columnName = column.getColumnName();
        this.ruleSourceId = column.getRuleSourceId() == null ? null : column.getRuleSourceId().toString();
        this.orderBy = column.getOrderBy();
        this.showyn = column.getShowyn();
        this.showpicture = column.getShowpicture();
    }

    public GoodsColumnVo() {
        super();
    }
}