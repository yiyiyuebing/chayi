package pub.makers.shop.stock.pojo;

import java.io.Serializable;

import pub.makers.shop.baseOrder.enums.OrderBizType;

/**
 * Created by kok on 2017/6/1.
 */
public class StockQuery implements Serializable{
    private Long skuId;
    private OrderBizType orderType;
    private Integer num;
    private Long userId;

    public StockQuery() {
    }

    public StockQuery(Long skuId, OrderBizType orderType, Integer num, Long userId) {
        this.skuId = skuId;
        this.orderType = orderType;
        this.num = num;
        this.userId = userId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public OrderBizType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderBizType orderType) {
        this.orderType = orderType;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
