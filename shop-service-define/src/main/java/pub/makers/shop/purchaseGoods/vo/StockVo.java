package pub.makers.shop.purchaseGoods.vo;

import java.io.Serializable;

/**
 * Created by daiwenfa on 2017/5/30.
 */
public class StockVo implements Serializable {
    private String id;
    private String code;
    private String skuName;
    private String onSalesNo;
    private String outShelvesNo;
    private String onPayNo;
    private String leftNums;
    private String cargoSkuId;
    private String goodId;
    private String isValid;

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

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getOnSalesNo() {
        return onSalesNo;
    }

    public void setOnSalesNo(String onSalesNo) {
        this.onSalesNo = onSalesNo;
    }

    public String getOutShelvesNo() {
        return outShelvesNo;
    }

    public void setOutShelvesNo(String outShelvesNo) {
        this.outShelvesNo = outShelvesNo;
    }

    public String getOnPayNo() {
        return onPayNo;
    }

    public void setOnPayNo(String onPayNo) {
        this.onPayNo = onPayNo;
    }

    public String getLeftNums() {
        return leftNums;
    }

    public void setLeftNums(String leftNums) {
        this.leftNums = leftNums;
    }

    public String getCargoSkuId() {
        return cargoSkuId;
    }

    public void setCargoSkuId(String cargoSkuId) {
        this.cargoSkuId = cargoSkuId;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
}
