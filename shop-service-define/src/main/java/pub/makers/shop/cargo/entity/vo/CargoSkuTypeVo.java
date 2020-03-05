package pub.makers.shop.cargo.entity.vo;

import pub.makers.shop.cargo.entity.CargoSkuType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2017/5/24.
 */
public class CargoSkuTypeVo implements Serializable {

    private String id;
    private String cargoId;
    private String cargoBaseSkuTypeId;
    private String type;
    private String name;
    private Date createTime;
    private Date updateTime;
    private String createBy;
    private String updateBy;

    private List<CargoSkuItemVo> cargoSkuItemList = new ArrayList<CargoSkuItemVo>();

    public List<CargoSkuItemVo> getCargoSkuItemList() {
        return cargoSkuItemList;
    }

    public void setCargoSkuItemList(List<CargoSkuItemVo> cargoSkuItemList) {
        this.cargoSkuItemList = cargoSkuItemList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCargoId() {
        return cargoId;
    }

    public void setCargoId(String cargoId) {
        this.cargoId = cargoId;
    }

    public String getCargoBaseSkuTypeId() {
        return cargoBaseSkuTypeId;
    }

    public void setCargoBaseSkuTypeId(String cargoBaseSkuTypeId) {
        this.cargoBaseSkuTypeId = cargoBaseSkuTypeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }


    public void addSkuItem(CargoSkuItemVo itemVo) {
        this.cargoSkuItemList.add(itemVo);
    }

    public static CargoSkuTypeVo fromCargoSkuType(CargoSkuType type) {
        CargoSkuTypeVo vo = new CargoSkuTypeVo();
        vo.setId(type.getId() == null ? null : type.getId().toString());
        vo.setCargoId(type.getCargoId() == null ? null : type.getCargoId().toString());
        vo.setCargoBaseSkuTypeId(type.getCargoBaseSkuTypeId() == null ? null : type.getCargoBaseSkuTypeId().toString());
        vo.setType(type.getType() == null ? null : type.getType().toString());
        vo.setName(type.getName());
        vo.setCreateTime(type.getCreateTime());
        vo.setUpdateTime(type.getUpdateTime());
        vo.setCreateBy(type.getCreateBy() == null ? null : type.getCreateBy().toString());
        vo.setUpdateBy(type.getUpdateBy() == null ? null : type.getUpdateBy().toString());
        return vo;
    }
}
