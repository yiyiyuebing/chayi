package pub.makers.shop.cargo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by daiwenfa on 2017/6/4.
 */
public class CargoMaterialLibraryRel implements Serializable {

    private Long id;
    private Long meterialLibraryId;
    private Long cargoId;
    private String sellingPoint;
    private Integer sort;
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMeterialLibraryId() {
        return meterialLibraryId;
    }

    public void setMeterialLibraryId(Long meterialLibraryId) {
        this.meterialLibraryId = meterialLibraryId;
    }

    public Long getCargoId() {
        return cargoId;
    }

    public void setCargoId(Long cargoId) {
        this.cargoId = cargoId;
    }

    public String getSellingPoint() {
        return sellingPoint;
    }

    public void setSellingPoint(String sellingPoint) {
        this.sellingPoint = sellingPoint;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
