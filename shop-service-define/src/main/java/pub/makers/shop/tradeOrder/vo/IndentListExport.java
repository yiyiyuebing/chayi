package pub.makers.shop.tradeOrder.vo;

import java.io.Serializable;

import pub.makers.shop.tradeOrder.entity.Indent;

/**
 * Created by dy on 2017/5/11.
 */
public class IndentListExport implements Serializable {

    private String cargoNo; //sku编码
    private String tradeGoodName;   //商品名称
    private String skuName;         //商品规格
    private String finalAmount; //商品总金额
    private String supplyPrice; //零售价
    private Integer number; //商品数量
    private Integer goodNum; //数量
    private Integer status; //商品状态
    private String cargoSkuName;

    private String retailPrice;

    private String giftFlag;//是否是配件
    private String goodType;

    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType;
    }

    public String getGiftFlag() {
        return giftFlag;
    }

    public void setGiftFlag(String giftFlag) {
        this.giftFlag = giftFlag;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getCargoSkuName() {
        return cargoSkuName;
    }

    public void setCargoSkuName(String cargoSkuName) {
        this.cargoSkuName = cargoSkuName;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }

    public String getCargoNo() {
        return cargoNo;
    }

    public void setCargoNo(String cargoNo) {
        this.cargoNo = cargoNo;
    }

    public String getTradeGoodName() {
        return tradeGoodName;
    }

    public void setTradeGoodName(String tradeGoodName) {
        this.tradeGoodName = tradeGoodName;
    }

    public String getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(String finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(String supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
