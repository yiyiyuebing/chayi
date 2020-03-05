package pub.makers.shop.cargo.entity.vo;

import pub.makers.shop.base.utils.DateParseUtil;
import pub.makers.shop.cargo.entity.CargoClassify;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kok on 2017/5/25.
 */
public class CargoClassifyVo implements Serializable {
    private String id;

    private String parentId;

    private String status;

    private String orderIndex;

    private String name;

    private String createTime;

    private String createBy;

    private String updateTime;

    private String updateBy;

    private String imgUrl;

    private String sort;

    private List<CargoClassifyVo> children;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(String orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<CargoClassifyVo> getChildren() {
        return children;
    }

    public void setChildren(List<CargoClassifyVo> children) {
        this.children = children;
    }

    public static CargoClassifyVo fromCargoClassify(CargoClassify cargoClassify) {
        CargoClassifyVo vo = new CargoClassifyVo();
        vo.setId(cargoClassify.getId().toString());
        vo.setParentId(cargoClassify.getParentId() == null ? null : cargoClassify.getParentId().toString());
        vo.setStatus(cargoClassify.getStatus().toString());
        vo.setOrderIndex(cargoClassify.getOrderIndex().toString());
        vo.setName(cargoClassify.getName());
        vo.setCreateTime(DateParseUtil.formatDate(cargoClassify.getCreateTime()));
        vo.setCreateBy(cargoClassify.getCreateBy().toString());
        vo.setUpdateTime(cargoClassify.getUpdateTime() == null ? null : DateParseUtil.formatDate(cargoClassify.getUpdateTime()));
        vo.setUpdateBy(cargoClassify.getUpdateBy() == null ? null : cargoClassify.getUpdateBy().toString());
        vo.setImgUrl(cargoClassify.getImgUrl());
        vo.setSort(cargoClassify.getSort() == null ? null : cargoClassify.getSort().toString());
        return vo;
    }
}
