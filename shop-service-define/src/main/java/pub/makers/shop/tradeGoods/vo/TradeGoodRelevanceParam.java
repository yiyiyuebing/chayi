package pub.makers.shop.tradeGoods.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by daiwenfa on 2017/6/7.
 */
public class TradeGoodRelevanceParam implements Serializable {
    private String relevanceName;
    private String classifyId;
    private String id;
    private String type;
    private String goodIds;
    private String isChecked;
    private String operation;
    private BigDecimal startPrice;
    private BigDecimal endPrice;
    private String goodType;
    private String skuIds;
    private String classifyName;
    private String applyType;
   // private String isValid;//是否上架   T

  /*  public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }*/

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

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

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public String getGoodIds() {
        return goodIds;
    }

    public void setGoodIds(String goodIds) {
        this.goodIds = goodIds;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public BigDecimal getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(BigDecimal endPrice) {
        this.endPrice = endPrice;
    }

    public String getRelevanceName() {
        return relevanceName;
    }

    public void setRelevanceName(String relevanceName) {
        this.relevanceName = relevanceName;
    }

    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType;
    }

    public String getSkuIds() {
        return skuIds;
    }

    public void setSkuIds(String skuIds) {
        this.skuIds = skuIds;
    }
}
