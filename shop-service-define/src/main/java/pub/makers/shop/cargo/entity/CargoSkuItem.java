package pub.makers.shop.cargo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dy on 2017/5/24.
 */
public class CargoSkuItem implements Serializable {

    private Long id;
    private Long cargoSkuTypeId;
    private Long cargoId;
    private Long cargoBaseSkuItemId;
    private String value;
    private String name;
    private Date createTime;
    private Long createBy;
    private Date updateTime;
    private Long updateBy;

    public Long getCargoId() {
        return cargoId;
    }

    public void setCargoId(Long cargoId) {
        this.cargoId = cargoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCargoSkuTypeId() {
        return cargoSkuTypeId;
    }

    public void setCargoSkuTypeId(Long cargoSkuTypeId) {
        this.cargoSkuTypeId = cargoSkuTypeId;
    }

    public Long getCargoBaseSkuItemId() {
        return cargoBaseSkuItemId;
    }

    public void setCargoBaseSkuItemId(Long cargoBaseSkuItemId) {
        this.cargoBaseSkuItemId = cargoBaseSkuItemId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }
}
