package pub.makers.shop.cart.controller;

import com.dev.base.json.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderClientType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.cargo.vo.CargoSkuSupplyPriceVo;
import pub.makers.shop.cart.entity.Cart;
import pub.makers.shop.cart.pojo.CartQuery;
import pub.makers.shop.cart.service.CartB2bService;
import pub.makers.shop.cart.vo.CartVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.user.utils.AccountUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dy on 2017/6/13.
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartB2bService cartB2bService;

    @RequestMapping("/purchaseCart")
    public String purchaseCart(Model model) {
        AccountUtils.getCurrShopId();
        return "www/cart/purchaseCart";
    }

    @RequestMapping("/addToCart")
    @ResponseBody
    public ResultData addToCart(HttpServletRequest request, String modelJsonStr) {
        if (StringUtils.isBlank(modelJsonStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        List<CartQuery> cartQueries = Lists.newArrayList();
        String userId = AccountUtils.getCurrShopId(); //TODO 获取当前登录用户id
        cartQueries = JsonUtils.toObject(modelJsonStr, ListUtils.getCollectionType(List.class, CartQuery.class));
        return cartB2bService.addToCart(cartQueries, userId);
    }


    @RequestMapping("/addGoodsToCart")
    @ResponseBody
    public ResultData addGoodsToCart(HttpServletRequest request, String modelJsonStr) {
        if (StringUtils.isBlank(modelJsonStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        List<CartQuery> cartQueries = Lists.newArrayList();
        String userId = AccountUtils.getCurrShopId(); //TODO 获取当前登录用户id
        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        cartQueries = JsonUtils.toObject(modelJsonStr, ListUtils.getCollectionType(List.class, CartQuery.class));
        return cartB2bService.addGoodsToCart(cartQueries, OrderBizType.purchase, ClientType.pc, userId, storeLevelId);
    }

    @RequestMapping("/calGoodsPriceInfo")
    @ResponseBody
    public ResultData calGoodsPriceInfo(HttpServletRequest request, String orderJson) {
        if (StringUtils.isBlank(orderJson)) {
            return ResultData.createFail("商品信息不存在");
        }

        PurchaseOrderVo pvo = JsonUtils.toObject(orderJson, PurchaseOrderVo.class);
        pvo.setBuyerId(AccountUtils.getCurrShopId());
        pvo.setSubbranchId(AccountUtils.getCurrShopId());
        pvo.setOrderType(OrderType.normal.name());
        pvo.setClientType(ClientType.pc.toString());
        pvo.setOrderClientType(OrderClientType.pc.name());
        BaseOrder baseOrder = cartB2bService.calGoodsPriceInfo(pvo);
        return ResultData.createSuccess(baseOrder);
    }


    @RequestMapping("/getCartList")
    @ResponseBody
    public ResultData getCartList(CartQuery query){
        query.setUserId(AccountUtils.getCurrShopId());
//        query.setShopId(AccountUtils.getCurrShopId());
        query.setStoreLevelId(AccountUtils.getCurrStoreLevelId());
        query.setClientType(ClientType.pc);
        List<CartVo> lcv = cartB2bService.getCartList(query);
        for (CartVo cart : lcv) {
            BaseGoodVo baseGoodVo = cart.getGood();
            if (BoolType.T.name().equals(baseGoodVo.getIsSancha())) {
                List<CargoSkuSupplyPriceVo> supplyPriceVos = baseGoodVo.getSupplyPriceList();
                for (CargoSkuSupplyPriceVo supplyPriceVo : supplyPriceVos) {
                    Integer sectionEnd = supplyPriceVo.getSectionEnd() == null ? 10000 : supplyPriceVo.getSectionEnd();
                    Integer goodsCount = StringUtils.isNotBlank(cart.getGoodsCount()) ? Integer.parseInt(cart.getGoodsCount()) : 0;
                    if (supplyPriceVo.getSectionStart() <= goodsCount && sectionEnd >= goodsCount) {
                        BigDecimal supplyPrice = new BigDecimal(supplyPriceVo.getSupplyPrice());
                        baseGoodVo.setPrice(supplyPrice.setScale(2));
                        break;
                    }
                }
            }
        }

      return  ResultData.createSuccess("lcv",lcv);
    }

    @RequestMapping("/delFromCart")
    @ResponseBody
    public ResultData delFromCart(String idList, String userId) throws IOException {
        if (StringUtils.isNotEmpty(idList)) {
                  userId = AccountUtils.getCurrShopId();

                 String id [] =idList.split(",");
                  List<String> list = Arrays.asList(id);

                cartB2bService.delFromCart(list, userId);
               return ResultData.createSuccess();
        }else {
            return  ResultData.createFail();
        }
    }

    @RequestMapping("/updateCartNum")
    @ResponseBody
    public  ResultData  updateCartNum(@RequestParam(value = "modelJsonStr", required = true)String modelJsonStr){
        if (StringUtils.isNotEmpty(modelJsonStr)){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                CartQuery query=objectMapper.readValue(modelJsonStr,CartQuery.class);
                query.setUserId(AccountUtils.getCurrShopId());
                cartB2bService.updateCartNum(query);
            } catch (IOException e) {
                e.printStackTrace();
                ResultData.createFail();
            }
        }
        return ResultData.createSuccess();
    }


    @RequestMapping("/num")
    @ResponseBody
    public ResultData countCartList(CartQuery query) {

        query.setUserId(AccountUtils.getCurrShopId());
        long num = cartB2bService.countCartList(query);

        return ResultData.createSuccess("num",num);
    }

}
