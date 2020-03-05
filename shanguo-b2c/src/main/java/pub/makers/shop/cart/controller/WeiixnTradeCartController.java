package pub.makers.shop.cart.controller;

import com.dev.base.json.JsonUtils;
import com.lantu.base.util.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseGood.pojo.ChangeGoodNumQuery;
import pub.makers.shop.cart.pojo.CartQuery;
import pub.makers.shop.cart.service.WeixinTradeCartAppService;
import pub.makers.shop.cart.vo.CartVo;
import pub.makers.shop.cart.vo.ChangeGoodNumVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by kok on 2017/6/14.
 */
@Controller
@RequestMapping("weixin/cart")
public class WeiixnTradeCartController {
    @Autowired
    private WeixinTradeCartAppService weixinTradeCartAppService;

    /**
     * 添加购物车
     */
    @RequestMapping("addToCart")
    @ResponseBody
    public ResultData addToCart(String json, HttpServletResponse response) {
        ValidateUtils.notNull(json, "请输入参数");

        CartQuery query = JsonUtils.toObject(json, CartQuery.class);
        weixinTradeCartAppService.addToCart(query);
        return ResultData.createSuccess();
    }

    /**
     * 添加购物车
     */
    @RequestMapping("addToCartList")
    @ResponseBody
    public ResultData addToCartList(String json, HttpServletResponse response) {
        ValidateUtils.notNull(json, "请输入参数");

        List<CartQuery> queryList = JsonUtils.toObject(json, ListUtils.getCollectionType(List.class, CartQuery.class));
        weixinTradeCartAppService.addToCartList(queryList);
        return ResultData.createSuccess();
    }

    /**
     * 从购物车删除
     */
    @RequestMapping("delFromCart")
    @ResponseBody
    public ResultData delFromCart(String json, HttpServletResponse response) {
        ValidateUtils.notNull(json, "请输入参数");

        CartQuery query = JsonUtils.toObject(json, CartQuery.class);
        weixinTradeCartAppService.delFromCart(query);
        return ResultData.createSuccess();
    }

    /**
     * 购物车列表
     */
    @RequestMapping("getCartList")
    @ResponseBody
    public ResultData getCartList(String json, HttpServletResponse response) {
        ValidateUtils.notNull(json, "请输入参数");

        CartQuery query = JsonUtils.toObject(json, CartQuery.class);
        List<CartVo> cartVoList =  weixinTradeCartAppService.getCartList(query);
        return ResultData.createSuccess(cartVoList);
    }

    /**
     * 购物车数量
     */
    @RequestMapping("countCartList")
    @ResponseBody
    public ResultData countCartList(String json, HttpServletResponse response) {
        ValidateUtils.notNull(json, "请输入参数");

        CartQuery query = JsonUtils.toObject(json, CartQuery.class);
        Long count =  weixinTradeCartAppService.countCartList(query);
        return ResultData.createSuccess("count", count.toString());
    }

    /**
     * 清空购物车
     */
    @RequestMapping("clearCart")
    @ResponseBody
    public ResultData clearCart(String json, HttpServletResponse response) {
        ValidateUtils.notNull(json, "请输入参数");

        CartQuery query = JsonUtils.toObject(json, CartQuery.class);
        weixinTradeCartAppService.clearCart(query);
        return ResultData.createSuccess();
    }

    /**
     * 修改购物车商品数量
     */
    @RequestMapping("updateCartNum")
    @ResponseBody
    public ResultData updateCartNum(String json, HttpServletResponse response) {
        ValidateUtils.notNull(json, "请输入参数");

        CartQuery query = JsonUtils.toObject(json, CartQuery.class);
        weixinTradeCartAppService.updateCartNum(query);
        return ResultData.createSuccess();
    }

    /**
     * 增加购物车商品数量
     */
    @RequestMapping("addCartNum")
    @ResponseBody
    public ResultData addCartNum(String json, HttpServletResponse response) {
        ValidateUtils.notNull(json, "请输入参数");

        CartQuery query = JsonUtils.toObject(json, CartQuery.class);
        weixinTradeCartAppService.addCartNum(query);
        return ResultData.createSuccess();
    }

    /**
     * 计算商品变化数量
     */
    @RequestMapping("changeGoodNum")
    @ResponseBody
    public ResultData changeGoodNum(String modelJson, HttpServletResponse response) {
        ValidateUtils.notNull(modelJson, "请输入参数");

        ChangeGoodNumQuery query = JsonUtils.toObject(modelJson, ChangeGoodNumQuery.class);
        ChangeGoodNumVo changeGoodNumVo = weixinTradeCartAppService.changeGoodNum(query);
        return ResultData.createSuccess(changeGoodNumVo);
    }
}
