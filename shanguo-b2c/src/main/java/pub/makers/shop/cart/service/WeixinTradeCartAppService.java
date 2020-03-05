package pub.makers.shop.cart.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.baseGood.pojo.ChangeGoodNumQuery;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.cart.pojo.CartQuery;
import pub.makers.shop.cart.vo.CartVo;
import pub.makers.shop.cart.vo.ChangeGoodNumVo;

import java.util.Arrays;
import java.util.List;

/**
 * Created by kok on 2017/6/14.
 */
@Service
public class WeixinTradeCartAppService {
    @Reference(version = "1.0.0")
    private CartBizService cartBizService;

    /**
     * 添加购物车
     */
    public void addToCart(CartQuery query) {
        ValidateUtils.notNull(query, "商品信息不能为空");
        query.setOrderBizType(OrderBizType.trade);
        cartBizService.addToCart(query);
    }

    /**
     * 添加购物车
     */
    public void addToCartList(List<CartQuery> querylist) {
        for (CartQuery query : querylist) {
            ValidateUtils.notNull(query, "商品信息不能为空");
            query.setOrderBizType(OrderBizType.trade);
            cartBizService.addToCart(query);
        }
    }

    /**
     * 从购物车删除
     */
    public void delFromCart(CartQuery query) {
        ValidateUtils.notNull(query, "商品信息不能为空");
        ValidateUtils.notNull(query.getId(), "商品信息不能为空");
        List<String> idList = Arrays.asList(StringUtils.split(query.getId(), ","));
        cartBizService.delFromCart(idList, query.getUserId(), OrderBizType.trade);
    }

    /**
     * 购物车列表
     */
    public List<CartVo> getCartList(CartQuery query) {
        ValidateUtils.notNull(query, "商品信息不能为空");
        query.setOrderBizType(OrderBizType.trade);
        query.setClientType(ClientType.mobile);
        return cartBizService.getCartList(query);
    }

    /**
     * 购物车数量
     */
    public Long countCartList(CartQuery query) {
        ValidateUtils.notNull(query, "商品信息不能为空");
        query.setOrderBizType(OrderBizType.trade);
        query.setClientType(ClientType.mobile);
        return cartBizService.countCartList(query);
    }

    /**
     * 清空购物车
     */
    public void clearCart(CartQuery query) {
        ValidateUtils.notNull(query, "商品信息不能为空");
        query.setOrderBizType(OrderBizType.trade);
        cartBizService.clearCart(query);
    }

    /**
     * 修改购物车商品数量
     */
    public void updateCartNum(CartQuery query) {
        ValidateUtils.notNull(query, "商品信息不能为空");
        query.setOrderBizType(OrderBizType.trade);
        cartBizService.updateCartNum(query);
    }

    /**
     * 增加购物车商品数量
     */
    public void addCartNum(CartQuery query) {
        ValidateUtils.notNull(query, "商品信息不能为空");
        query.setOrderBizType(OrderBizType.trade);
        cartBizService.addCartNum(query);
    }

    /**
     * 计算商品变化数量
     */
    public ChangeGoodNumVo changeGoodNum(ChangeGoodNumQuery query) {
        query.setOrderBizType(OrderBizType.trade);
        ChangeGoodNumVo changeGoodNumVo = cartBizService.changeGoodNum(query);
        // 更新购物车
        CartQuery cartQuery = new CartQuery();
        cartQuery.setGoodsId(query.getSkuId());
        cartQuery.setUserId(query.getUserId());
        cartQuery.setShopId(query.getShopId());
        cartQuery.setGoodsCount(changeGoodNumVo.getNum());
        cartQuery.setOrderBizType(OrderBizType.trade);
        cartBizService.updateCartNumByGoodsId(cartQuery);
        return changeGoodNumVo;
    }
}
