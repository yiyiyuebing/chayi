package pub.makers.shop.purchaseGoods.entity;

import java.io.Serializable;

/**
 * Created by dy on 2017/4/14.
 */
public class PurchaseGoodsSample implements Serializable {

    private String id;
    private String purGoodsId;
    private String sampleCode;
    private String sampleSku;
    private Double samplePrice;
    private String unit;
    private Integer startNum;
    private Integer freight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurGoodsId() {
        return purGoodsId;
    }

    public void setPurGoodsId(String purGoodsId) {
        this.purGoodsId = purGoodsId;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getSampleSku() {
        return sampleSku;
    }

    public void setSampleSku(String sampleSku) {
        this.sampleSku = sampleSku;
    }

    public Double getSamplePrice() {
        return samplePrice;
    }

    public void setSamplePrice(Double samplePrice) {
        this.samplePrice = samplePrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getStartNum() {
        return startNum;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }
}
