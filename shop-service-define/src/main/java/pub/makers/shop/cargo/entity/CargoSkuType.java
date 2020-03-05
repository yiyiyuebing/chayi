package pub.makers.shop.cargo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2017/5/23.
 */
public class CargoSkuType implements Serializable {

    private Long id;
    private Long cargoId;
    private Long cargoBaseSkuTypeId;
    private Integer type;
    private String name;
    private Date createTime;
    private Date updateTime;
    private Long createBy;
    private Long updateBy;

    private List<CargoSkuItem> cargoSkuItemList;

    public List<CargoSkuItem> getCargoSkuItemList() {
        return cargoSkuItemList;
    }

    public void setCargoSkuItemList(List<CargoSkuItem> cargoSkuItemList) {
        this.cargoSkuItemList = cargoSkuItemList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCargoId() {
        return cargoId;
    }

    public void setCargoId(Long cargoId) {
        this.cargoId = cargoId;
    }

    public Long getCargoBaseSkuTypeId() {
        return cargoBaseSkuTypeId;
    }

    public void setCargoBaseSkuTypeId(Long cargoBaseSkuTypeId) {
        this.cargoBaseSkuTypeId = cargoBaseSkuTypeId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }
}
