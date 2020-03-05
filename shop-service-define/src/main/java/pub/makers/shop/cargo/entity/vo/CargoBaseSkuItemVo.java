package pub.makers.shop.cargo.entity.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dy on 2017/5/22.
 */
public class CargoBaseSkuItemVo implements Serializable {

    private String id;

    private String code;

    private String baseSkuTypeId;

    private String value;

    private String name;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private String skuId;

    private CargoBaseSkuTypeVo skuType;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBaseSkuTypeId() {
        return baseSkuTypeId;
    }

    public void setBaseSkuTypeId(String baseSkuTypeId) {
        this.baseSkuTypeId = baseSkuTypeId;
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

    public CargoBaseSkuTypeVo getSkuType() {
        return skuType;
    }

    public void setSkuType(CargoBaseSkuTypeVo skuType) {
        this.skuType = skuType;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
}
