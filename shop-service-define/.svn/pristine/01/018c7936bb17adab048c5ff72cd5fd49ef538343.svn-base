package pub.makers.shop.cart.pojo;

import org.apache.commons.lang.StringUtils;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.cart.entity.Cart;

import java.io.Serializable;

/**
 * Created by kok on 2017/6/12.
 */
public class CartQuery implements Serializable {
    private String id;
    /** 商品id */
    private String goodsId;

    /** 店铺id */
    private String shopId;

    /** 用户id */
    private String userId;

    /** 商品数量 */
    private Integer goodsCount;

    private OrderBizType orderBizType;

    /** 客户端类型 */
    private ClientType clientType;

    private String storeLevelId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public OrderBizType getOrderBizType() {
        return orderBizType;
    }

    public void setOrderBizType(OrderBizType orderBizType) {
        this.orderBizType = orderBizType;
    }

    public String getStoreLevelId() {
        return storeLevelId;
    }

    public void setStoreLevelId(String storeLevelId) {
        this.storeLevelId = storeLevelId;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public Cart toCart() {
        Cart cart = new Cart();
        cart.setGoodsId(StringUtils.isEmpty(this.getGoodsId()) ? null : Long.valueOf(this.getGoodsId()));
        cart.setShopId(StringUtils.isEmpty(this.getShopId()) ? null : Long.valueOf(this.getShopId()));
        cart.setUserId(StringUtils.isEmpty(this.getUserId()) ? null : Long.valueOf(this.getUserId()));
        cart.setGoodsCount(this.getGoodsCount());
        return cart;
    }
}
