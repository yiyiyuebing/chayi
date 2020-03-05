package pub.makers.shop.baseOrder.pojo;

import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderDeleteType;
import pub.makers.shop.baseOrder.enums.OrderType;

import java.io.Serializable;

/**
 * Created by kok on 2017/6/18.
 */
public class OrderDeleteInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;

    private String orderId;

    private OrderDeleteType deleteType;

    private OrderBizType orderBizType;

    private OrderType orderType;

    public OrderDeleteInfo() {
    }

    public OrderDeleteInfo(String userId, String orderId, OrderDeleteType deleteType, OrderBizType orderBizType, OrderType orderType) {
        this.userId = userId;
        this.orderId = orderId;
        this.deleteType = deleteType;
        this.orderBizType = orderBizType;
        this.orderType = orderType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderDeleteType getDeleteType() {
        return deleteType;
    }

    public void setDeleteType(OrderDeleteType deleteType) {
        this.deleteType = deleteType;
    }

    public OrderBizType getOrderBizType() {
        return orderBizType;
    }

    public void setOrderBizType(OrderBizType orderBizType) {
        this.orderBizType = orderBizType;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
}
