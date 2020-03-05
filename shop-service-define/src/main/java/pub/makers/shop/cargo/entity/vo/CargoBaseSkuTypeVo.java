package pub.makers.shop.cargo.entity.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2017/5/22.
 */
public class CargoBaseSkuTypeVo implements Serializable {

    private String id;

    private String type;

    private String name;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private List<CargoBaseSkuItemVo> skuItemList = new ArrayList<CargoBaseSkuItemVo>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<CargoBaseSkuItemVo> getSkuItemList() {
        return skuItemList;
    }

    public void setSkuItemList(List<CargoBaseSkuItemVo> skuItemList) {
        this.skuItemList = skuItemList;
    }

    public void addSkuItem(CargoBaseSkuItemVo itemVo) {
        this.skuItemList.add(itemVo);
    }
}
