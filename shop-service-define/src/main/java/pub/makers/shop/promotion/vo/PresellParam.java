package pub.makers.shop.promotion.vo;

import pub.makers.shop.promotion.entity.PresellGood;
import pub.makers.shop.promotion.entity.SaleActivityGood;

import java.io.Serializable;
import java.util.List;

/**
 * Created by daiwenfa on 2017/6/19.
 */
public class PresellParam implements Serializable {
    private String id;
    private String presellName;//活动名称or商品名称、sku编码、货品编码
    private String presellType;//预售模式(定金模式/全款模式)
    private String orderBizType;//订单业务类型(商城/采购)
    private String status;//预售状态
    private String classifyId;//分类id
    private String cargoNo;//货品编号
    private String isCheck;//是否已添加
    private String skuIds;//关联的skuId
    private String activityId;
    private String applyType;

    private List<PresellGood> presellGoodList;

    //商品集合
    private List<SaleActivityGood> saleActivityGoodList;

    public List<SaleActivityGood> getSaleActivityGoodList() {
        return saleActivityGoodList;
    }

    public void setSaleActivityGoodList(List<SaleActivityGood> saleActivityGoodList) {
        this.saleActivityGoodList = saleActivityGoodList;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getPresellType() {
        return presellType;
    }

    public void setPresellType(String presellType) {
        this.presellType = presellType;
    }

    public String getOrderBizType() {
        return orderBizType;
    }

    public void setOrderBizType(String orderBizType) {
        this.orderBizType = orderBizType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getCargoNo() {
        return cargoNo;
    }

    public void setCargoNo(String cargoNo) {
        this.cargoNo = cargoNo;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PresellGood> getPresellGoodList() {
        return presellGoodList;
    }

    public void setPresellGoodList(List<PresellGood> presellGoodList) {
        this.presellGoodList = presellGoodList;
    }

    public String getSkuIds() {
        return skuIds;
    }

    public void setSkuIds(String skuIds) {
        this.skuIds = skuIds;
    }

    public String getPresellName() {
        return presellName;
    }

    public void setPresellName(String presellName) {
        this.presellName = presellName;
    }
}
