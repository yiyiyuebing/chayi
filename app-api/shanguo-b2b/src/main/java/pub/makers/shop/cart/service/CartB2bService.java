package pub.makers.shop.cart.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lantu.base.common.entity.BoolType;
import org.springframework.stereotype.Service;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseGood.pojo.ChangeGoodNumQuery;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.cart.pojo.CartQuery;
import pub.makers.shop.cart.vo.CartVo;
import pub.makers.shop.cart.vo.ChangeGoodNumVo;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuBizService;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsSkuVo;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderManager;
import pub.makers.shop.user.utils.AccountUtils;

import java.util.List;

/**
 * Created by dy on 2017/6/13.
 */
@Service
public class CartB2bService {

    @Reference(version = "1.0.0")
    private CartBizService cartBizService;
    @Reference(version = "1.0.0")
    private PurchaseOrderManager purchaseOrderManager;
    @Reference(version = "1.0.0")
    private PurchaseGoodsSkuBizService purchaseGoodsSkuBizService;

    /**
     * 新增购物车
     *
     * @param cartQueries
     * @param userId
     * @return
     */
    public ResultData addToCart(List<CartQuery> cartQueries, String userId) {
        for (CartQuery cartQuery : cartQueries) {
            cartQuery.setUserId(userId);
            cartQuery.setOrderBizType(OrderBizType.purchase);
            cartBizService.addToCart(cartQuery);
        }
        return ResultData.createSuccess();
    }

    public ResultData batchAddToCart(List<CartQuery> cartQueries, String userId) {
        for (CartQuery cartQuery : cartQueries) {
            cartQuery.setUserId(userId);
            cartQuery.setClientType(ClientType.mobile);
            cartQuery.setOrderBizType(OrderBizType.purchase);
            cartBizService.addToCart(cartQuery);
        }
        return ResultData.createSuccess();
    }

    /**
     * 计算商品价格信息
     * @param order
     * @return
     */
    public BaseOrder calGoodsPriceInfo(BaseOrder order) {
        return purchaseOrderManager.preview(order);
    }

    /**
     * 从购物车删除
     */
    public void delFromCart(List<String> idList, String userId) {
        OrderBizType orderBizType = OrderBizType.purchase;
        cartBizService.delFromCart(idList, userId, orderBizType);
    }

    /**
     * 购物车列表
     */
    public List<CartVo> getCartList(CartQuery query) {
        query.setOrderBizType(OrderBizType.purchase);
        return cartBizService.getCartList(query);
    }

    /**
     * 清空购物车
     */
    public void clearCart(CartQuery query) {
        cartBizService.clearCart(query);
    }

    /**
     * 修改购物车商品数量
     */
    public void updateCartNum(CartQuery query) {
        query.setOrderBizType(OrderBizType.purchase);
        cartBizService.updateCartNum(query);
    }

    /**
     * 增加购物车商品数量
     */
    public void addCartNum(CartQuery query) {
        cartBizService.addCartNum(query);
    }

    /**
     * 购物车数量
     */
    public Long countCartList(CartQuery query) {
        query.setOrderBizType(OrderBizType.purchase);
        return cartBizService.countCartList(query);
    }

    public ChangeGoodNumVo changeGoodNum(ChangeGoodNumQuery query) {
        query.setOrderBizType(OrderBizType.purchase);
        ChangeGoodNumVo changeGoodNumVo = cartBizService.changeGoodNum(query);
        // 更新购物车
        String userId = AccountUtils.getCurrShopId();
        CartQuery cartQuery = new CartQuery();
        cartQuery.setGoodsId(query.getSkuId());
        cartQuery.setUserId(userId);
        cartQuery.setGoodsCount(changeGoodNumVo.getNum());
        cartQuery.setOrderBizType(OrderBizType.purchase);
        cartBizService.updateCartNumByGoodsId(cartQuery);
        return changeGoodNumVo;
    }

    public ResultData addGoodsToCart(List<CartQuery> cartQueries, OrderBizType orderBizType,
                                     ClientType clientType, String userId, String storeLevelId) {
        for (CartQuery cartQuery : cartQueries) {
            List<PurchaseGoodsSkuVo> purchaseGoodsSkuVos = purchaseGoodsSkuBizService.getGoodsSkuList(cartQuery.getGoodsId(), storeLevelId);
            for (PurchaseGoodsSkuVo purchaseGoodsSkuVo : purchaseGoodsSkuVos) {
                CartQuery addCartQuery = new CartQuery();
                if (BoolType.T.name().equals(purchaseGoodsSkuVo.getLimitFlg())) {
                    addCartQuery.setGoodsCount(purchaseGoodsSkuVo.getStartNum());
                } else {
                    addCartQuery.setGoodsCount(1);
                }
                addCartQuery.setOrderBizType(orderBizType);
                addCartQuery.setClientType(clientType);
                addCartQuery.setUserId(userId);
                addCartQuery.setGoodsId(purchaseGoodsSkuVo.getId());
                cartBizService.addToCart(addCartQuery);
            }
        }
        return ResultData.createSuccess();
    }
}
