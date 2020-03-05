package pub.makers.shop.baseGood.pojo;

import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.baseGood.enums.ChangeGoodNumOperation;
import pub.makers.shop.baseOrder.enums.OrderBizType;

import java.io.Serializable;

/**
 * Created by kok on 2017/8/9.
 */
public class ChangeGoodNumQuery implements Serializable {
    private String skuId; //商品skuid
    private String isSample; //是否样品
    private ChangeGoodNumOperation operation; //操作类型
    private OrderBizType orderBizType; //业务类型
    private Integer nowNum; //当前数量
    private String storeLevelId; //店铺等级
    private String userId; //用户id
    private String shopId; //店铺id
    private ClientType clientType; //客户端类型

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public ChangeGoodNumOperation getOperation() {
        return operation;
    }

    public void setOperation(ChangeGoodNumOperation operation) {
        this.operation = operation;
    }

    public OrderBizType getOrderBizType() {
        return orderBizType;
    }

    public void setOrderBizType(OrderBizType orderBizType) {
        this.orderBizType = orderBizType;
    }

    public Integer getNowNum() {
        return nowNum;
    }

    public void setNowNum(Integer nowNum) {
        this.nowNum = nowNum;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getStoreLevelId() {
        return storeLevelId;
    }

    public void setStoreLevelId(String storeLevelId) {
        this.storeLevelId = storeLevelId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getIsSample() {
        return isSample;
    }

    public void setIsSample(String isSample) {
        this.isSample = isSample;
    }
}
