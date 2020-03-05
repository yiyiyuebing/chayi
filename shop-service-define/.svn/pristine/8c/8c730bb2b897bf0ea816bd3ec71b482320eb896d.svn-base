package pub.makers.shop.tradeGoods.vo;

import pub.makers.shop.tradeGoods.entity.GoodsBaseLabel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dy on 2017/5/25.
 */
public class GoodsBaseLabelVo implements Serializable {
    private String id;
    private String shopID;
    private String labelName;
    private Integer orderBy;
    private Integer status;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;

    public GoodsBaseLabelVo() {
    }

    public GoodsBaseLabelVo(GoodsBaseLabel goodsBaseLabel) {
        this.id = goodsBaseLabel.getId() == null ? null :goodsBaseLabel.getId().toString();
        this.shopID = goodsBaseLabel.getShopID() == null ? null : goodsBaseLabel.getShopID().toString();
        this.labelName = goodsBaseLabel.getLabelName();
        this.orderBy = goodsBaseLabel.getOrderBy();
        this.status = goodsBaseLabel.getStatus();
        this.createBy = goodsBaseLabel.getCreateBy() == null ? null : goodsBaseLabel.getCreateBy().toString();
        this.createTime = goodsBaseLabel.getCreateTime();
        this.updateBy = goodsBaseLabel.getUpdateBy() == null ? null : goodsBaseLabel.getUpdateBy().toString();
        this.updateTime = goodsBaseLabel.getUpdateTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
