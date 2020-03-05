package pub.makers.shop.cargo.vo;

import pub.makers.shop.cargo.entity.CargoBasePropertys;

import java.io.Serializable;

/**
 * Created by kok on 2017/7/6.
 */
public class CargoBasePropertysVo implements Serializable {
    private String id;
    private String cargoId;
    private String pname;
    private String pvalue;
    private Integer sort;

    public CargoBasePropertysVo() {
    }

    public CargoBasePropertysVo(CargoBasePropertys cargoBasePropertys) {
        this.id = cargoBasePropertys.getId() == null ? null : cargoBasePropertys.getId().toString();
        this.cargoId = cargoBasePropertys.getCargoId() == null ? null : cargoBasePropertys.getCargoId().toString();
        this.pname = cargoBasePropertys.getPname();
        this.pvalue = cargoBasePropertys.getPvalue();
        this.sort = cargoBasePropertys.getSort();
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

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPvalue() {
        return pvalue;
    }

    public void setPvalue(String pvalue) {
        this.pvalue = pvalue;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
