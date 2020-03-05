package pub.makers.shop.cargo.entity.vo;

import java.io.Serializable;

/**
 * Created by dy on 2017/5/23.
 */
public class CargoVo implements Serializable {

    private String id;
    private String cargoNo;
    private String name;
    private String classifyName;
    private String smallImage;
    private String brandName;
    private String supplierName;
    private String skuName;
    private String skuValue;
    private String skuCode;
    private String skuMemo;
    private String specs;
    private String pcAlbumId;

    public String getPcAlbumId() {
        return pcAlbumId;
    }

    public void setPcAlbumId(String pcAlbumId) {
        this.pcAlbumId = pcAlbumId;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuValue() {
        return skuValue;
    }

    public void setSkuValue(String skuValue) {
        this.skuValue = skuValue;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuMemo() {
        return skuMemo;
    }

    public void setSkuMemo(String skuMemo) {
        this.skuMemo = skuMemo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCargoNo() {
        return cargoNo;
    }

    public void setCargoNo(String cargoNo) {
        this.cargoNo = cargoNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
