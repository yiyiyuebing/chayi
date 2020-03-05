package pub.makers.shop.tradeGoods.vo;


import pub.makers.shop.tradeGoods.entity.TradeGoodsClassify;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2017/5/25.
 */
public class TradeGoodsClassifyVo implements Serializable {

    private String id;

    private String parentId;

    private Integer status;

    private Integer orderIndex;

    private String name;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String imgUrl;

    private Integer sort;

    private Boolean checked;

    private List<TradeGoodsClassifyVo> children = new ArrayList<TradeGoodsClassifyVo>();

    public TradeGoodsClassifyVo() {
    }

    public TradeGoodsClassifyVo(TradeGoodsClassify classify) {
        this.id = classify.getId() + "";
        this.parentId = classify.getParentId() + "";
        this.status = classify.getStatus();
        this.orderIndex = classify.getOrderIndex();
        this.name = classify.getName();
        this.createTime = classify.getCreateTime();
        this.createBy = classify.getCreateBy() + "";
        this.updateTime = classify.getUpdateTime();
        this.updateBy = classify.getUpdateBy() + "";
        this.imgUrl = classify.getImgUrl();
        this.sort = classify.getSort();
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public List<TradeGoodsClassifyVo> getChildren() {
        return children;
    }

    public void setChildren(List<TradeGoodsClassifyVo> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
