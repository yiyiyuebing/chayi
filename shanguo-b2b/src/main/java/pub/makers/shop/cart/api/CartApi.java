package pub.makers.shop.cart.api;

import com.dev.base.json.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseGood.pojo.ChangeGoodNumQuery;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.cart.pojo.CartQuery;
import pub.makers.shop.cart.service.CartB2bService;
import pub.makers.shop.cart.vo.CartVo;
import pub.makers.shop.cart.vo.ChangeGoodNumVo;
import pub.makers.shop.user.utils.AccountUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kok on 2017/6/30.
 */
@Controller
@RequestMapping("weixin/cart")
public class CartApi {
    @Autowired
    private CartB2bService cartB2bService;

    /**
     * {cart/addToCart}
     * 添加购物车商品
     *
     * @return
     */
    @RequestMapping("addToCart")
    @ResponseBody
    public ResultData addToCart(String queryJson) {
        ValidateUtils.notNull(queryJson, "参数不能为空");

        CartQuery query = JsonUtils.toObject(queryJson, CartQuery.class);
        String userId = AccountUtils.getCurrShopId();
        query.setClientType(ClientType.mobile);
        query.setOrderBizType(OrderBizType.purchase);
        cartB2bService.addToCart(Lists.newArrayList(query), userId);
        return ResultData.createSuccess();
    }

    /**
     * {cart/batchAddToCart}
     * 批量加入购物车商品
     *
     * @return
     */
    @RequestMapping("batchAddToCart")
    @ResponseBody
    public ResultData batchAddToCart(String queryJson) {
        ValidateUtils.notNull(queryJson, "参数不能为空");

        List<CartQuery> cartQueryList = Lists.newArrayList();
        String userId = AccountUtils.getCurrShopId();
        cartQueryList = JsonUtils.toObject(queryJson, ListUtils.getCollectionType(List.class, CartQuery.class));
        cartB2bService.batchAddToCart(cartQueryList, userId);
        return ResultData.createSuccess();

    }

    /**
     * {cart/delFromCart}
     * 删除购物车商品
     *
     * @return
     */
    @RequestMapping("delFromCart")
    @ResponseBody
    public ResultData delFromCart(String idList) {
        ValidateUtils.notNull(idList, "参数不能为空");

        List<String> ids = Arrays.asList(StringUtils.split(idList, ","));
        String userId = AccountUtils.getCurrShopId();
        cartB2bService.delFromCart(ids, userId);
        return ResultData.createSuccess();
    }

    /**
     * {cart/clearCart}
     * 清空购物车
     *
     * @return
     */
    @RequestMapping("clearCart")
    @ResponseBody
    public ResultData clearCart() {
        CartQuery query = new CartQuery();
        String userId = AccountUtils.getCurrShopId();
        query.setUserId(userId);
        query.setOrderBizType(OrderBizType.purchase);
        cartB2bService.clearCart(query);
        return ResultData.createSuccess();
    }

    /**
     * {cart/getCartList}
     * 获取购物车列表
     *
     * @return
     */
    @RequestMapping("getCartList")
    @ResponseBody
    public ResultData getCartList() {
        CartQuery query = new CartQuery();
        String userId = AccountUtils.getCurrShopId();
        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        query.setUserId(userId);
        query.setStoreLevelId(storeLevelId);
        query.setClientType(ClientType.mobile);

        List<CartVo> cartVos = cartB2bService.getCartList(query);

        return ResultData.createSuccess("cartList", cartVos);

    }

    //更新购物车数量
    @RequestMapping("/updateCartNum")
    @ResponseBody
    public ResultData updateCartNum(@RequestParam(value = "modelJsonStr", required = true) String modelJsonStr) {
        if (StringUtils.isNotEmpty(modelJsonStr)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                CartQuery query = objectMapper.readValue(modelJsonStr, CartQuery.class);
                query.setUserId(AccountUtils.getCurrShopId());
                cartB2bService.updateCartNum(query);
            } catch (IOException e) {
                e.printStackTrace();
                ResultData.createFail();
            }
        }
        return ResultData.createSuccess();
    }

    @RequestMapping("/getCartCount")
    @ResponseBody
    public ResultData countCartList(CartQuery query) {

        query.setUserId(AccountUtils.getCurrShopId());
        long num = cartB2bService.countCartList(query);

        return ResultData.createSuccess("num", num);
    }

    /**
     * 计算商品变化数量
     */
    @RequestMapping("changeGoodNum")
    @ResponseBody
    public ResultData changeGoodNum(String modelJson) {
        ValidateUtils.notNull(modelJson, "参数不能为空");

        ChangeGoodNumQuery query = JsonUtils.toObject(modelJson, ChangeGoodNumQuery.class);
        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        query.setStoreLevelId(storeLevelId);
        ChangeGoodNumVo changeGoodNumVo = cartB2bService.changeGoodNum(query);
        return ResultData.createSuccess(changeGoodNumVo);
    }
}
