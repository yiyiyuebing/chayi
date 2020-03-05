package pub.makers.shop.cargo.entity.vo;

import pub.makers.shop.cargo.entity.CargoSkuItem;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsSkuVo;
import pub.makers.shop.tradeGoods.vo.TradeGoodSkuVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2017/5/24.
 */
public class CargoSkuItemVo implements Serializable {
    private String id;
    private String cargoSkuTypeId;
    private String cargoBaseSkuItemId;
    private String value;
    private String name;
    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;
    private String cargoId;
    private String skuId;
    private String isValid;
    private List<PurchaseGoodsSkuVo> purchaseGoodsSkuList;
    private List<TradeGoodSkuVo> tradeGoodSkuList;

    private CargoSkuTypeVo skuType;

    public String getCargoId() {
        return cargoId;
    }

    public void setCargoId(String cargoId) {
        this.cargoId = cargoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCargoSkuTypeId() {
        return cargoSkuTypeId;
    }

    public void setCargoSkuTypeId(String cargoSkuTypeId) {
        this.cargoSkuTypeId = cargoSkuTypeId;
    }

    public String getCargoBaseSkuItemId() {
        return cargoBaseSkuItemId;
    }

    public void setCargoBaseSkuItemId(String cargoBaseSkuItemId) {
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

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public CargoSkuTypeVo getSkuType() {
        return skuType;
    }

    public void setSkuType(CargoSkuTypeVo skuType) {
        this.skuType = skuType;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public List<PurchaseGoodsSkuVo> getPurchaseGoodsSkuList() {
        return purchaseGoodsSkuList;
    }

    public void setPurchaseGoodsSkuList(List<PurchaseGoodsSkuVo> purchaseGoodsSkuList) {
        this.purchaseGoodsSkuList = purchaseGoodsSkuList;
    }

    public List<TradeGoodSkuVo> getTradeGoodSkuList() {
        return tradeGoodSkuList;
    }

    public void setTradeGoodSkuList(List<TradeGoodSkuVo> tradeGoodSkuList) {
        this.tradeGoodSkuList = tradeGoodSkuList;
    }

    public static CargoSkuItemVo fromCargoSkuItem(CargoSkuItem item) {
        CargoSkuItemVo vo = new CargoSkuItemVo();
        vo.setId(item.getId() == null ? null : item.getId().toString());
        vo.setCargoSkuTypeId(item.getCargoSkuTypeId() == null ? null : item.getCargoSkuTypeId().toString());
        vo.setCargoBaseSkuItemId(item.getCargoBaseSkuItemId() == null ? null : item.getCargoBaseSkuItemId().toString());
        vo.setValue(item.getValue());
        vo.setName(item.getName());
        vo.setCreateTime(item.getCreateTime());
        vo.setCreateBy(item.getCreateBy() == null ? null : item.getCreateBy().toString());
        vo.setUpdateTime(item.getUpdateTime());
        vo.setUpdateBy(item.getUpdateBy() == null ? null : item.getUpdateBy().toString());
        vo.setCargoId(item.getCargoId() == null ? null : item.getCargoId().toString());
        return vo;
    }
}
