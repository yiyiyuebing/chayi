package pub.makers.shop.favorite.api;

import com.dev.base.json.JsonUtils;
import com.google.common.collect.Lists;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.cart.pojo.CartQuery;
import pub.makers.shop.cart.service.CartB2bService;
import pub.makers.shop.favorite.pojo.FavoriteQuery;
import pub.makers.shop.favorite.service.FavoriteB2bService;
import pub.makers.shop.user.utils.AccountUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * Created by Think on 2017/7/19.
 */
@Controller
@RequestMapping("weixin/favorite")
public class FavoriteApi {
    @Autowired
    private FavoriteB2bService favoriteB2bService;
    @Autowired
    private CartB2bService cartB2bService;

    /**
     * 加入收藏
     * @param modelJsonStr
     * @return
     */
    @RequestMapping("/addToFavorite")
    @ResponseBody
    public ResultData addToFavorite(HttpServletRequest request,String modelJsonStr) {
        if (StringUtils.isBlank(modelJsonStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        String userId = AccountUtils.getCurrShopId(); //TODO 获取当前登录用户id
         FavoriteQuery favoriteQuery = JsonUtils.toObject(modelJsonStr, FavoriteQuery.class);
        favoriteQuery.setUserId(userId);
        favoriteQuery.setOrderBizType(OrderBizType.purchase);
        favoriteQuery.setClientType(ClientType.mobile);
        return favoriteB2bService.addToFavorite(favoriteQuery);
    }

    /**
     * 取消收藏
     * @param modelJsonStr
     * @return
     */
    @RequestMapping("/cancelFromFavorite")
    @ResponseBody
    public ResultData cancelFromFavorite(HttpServletRequest request,String modelJsonStr) {
        if (StringUtils.isBlank(modelJsonStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        String userId = AccountUtils.getCurrShopId();
         FavoriteQuery favoriteQuery = JsonUtils.toObject(modelJsonStr, FavoriteQuery.class);
        favoriteQuery.setUserId(userId);
        favoriteQuery.setOrderBizType(OrderBizType.purchase);
        favoriteQuery.setClientType(ClientType.mobile);
        return favoriteB2bService.cancelFavorite(favoriteQuery);
    }
    //取消收藏
    /**
     * 批量加入收藏
     * @param request
     * @param goodIdStr
     * @return
     */
    @RequestMapping("/batchAddToFavorite")
    @ResponseBody
    public ResultData batchAddToFavorite(HttpServletRequest request, String goodIdStr) {
        if (StringUtils.isBlank(goodIdStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        String userId = AccountUtils.getCurrShopId(); //TODO 获取当前登录用户id
        return favoriteB2bService.batchAddToFavorite(goodIdStr, userId, OrderBizType.purchase);
    }

    //通过商品ID查收藏列表
    @RequestMapping("/getFavoriteListByGoodsSkuId")
    @ResponseBody
    public ResultData getFavoriteListByGoodsSkuId(HttpServletRequest request, String goodsSkuIdListStr) {
        if (StringUtils.isBlank(goodsSkuIdListStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        String userId = AccountUtils.getCurrShopId(); //TODO 获取当前登录用户id

        if (StringUtils.isBlank(userId)) {
            return ResultData.createFail();
        }

        return favoriteB2bService.getFavoriteListByGoodsId(goodsSkuIdListStr, userId, OrderBizType.purchase);
    }

    //从收藏加入购物车
    @RequestMapping("/addToCartFromFavorite")
    @ResponseBody
    public ResultData addToCartFromFavorite(HttpServletRequest request, String modelJsonStr){
        if (StringUtils.isBlank(modelJsonStr)) {
            return ResultData.createFail("商品信息不存在");
        }

        List<CartQuery> cartQueryList = Lists.newArrayList();
        String userId = AccountUtils.getCurrShopId();
        cartQueryList = JsonUtils.toObject(modelJsonStr, ListUtils.getCollectionType(List.class, CartQuery.class));
        return cartB2bService.addToCart(cartQueryList, userId);
    }

    /**
     * 取消收藏
     * @param request
     * @param modelJsonStr
     * @return
     */
    @RequestMapping("/cancelFavorite")
    @ResponseBody
    public ResultData cancelFavorite(HttpServletRequest request, String modelJsonStr) {
        if (StringUtils.isBlank(modelJsonStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        List<String> ids = Lists.newArrayList();
        ids = JsonUtils.toObject(modelJsonStr, ListUtils.getCollectionType(List.class, String.class));
        String userId = AccountUtils.getCurrShopId(); //TODO 获取当前登录用户id
        favoriteB2bService.batchCancleFavorite(ids, userId, OrderBizType.purchase);
        return ResultData.createSuccess();
    }

    //获取收藏列表
    @RequestMapping("/getFavoriteList")
    @ResponseBody
    public ResultData getFavoriteList(HttpServletRequest request, String modelJsonStr) {
        if (StringUtils.isBlank(modelJsonStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        FavoriteQuery favoriteQuery = JsonUtils.toObject(modelJsonStr, FavoriteQuery.class) != null ?
                JsonUtils.toObject(modelJsonStr, FavoriteQuery.class) : new FavoriteQuery();
        String userId = AccountUtils.getCurrShopId();
        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        favoriteQuery.setUserId(userId);
        favoriteQuery.setStoreLevelId(storeLevelId);
        favoriteQuery.setClientType(ClientType.mobile);
        favoriteQuery.setOrderBizType(OrderBizType.purchase);
        return favoriteB2bService.getFavoriteList(favoriteQuery);
    }
}
