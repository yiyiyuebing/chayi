package pub.makers.shop.favorite.controller;

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
import pub.makers.shop.favorite.pojo.FavoriteQuery;
import pub.makers.shop.favorite.service.FavoriteB2cService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by kok on 2017/9/25.
 */
@Controller
@RequestMapping("weixin/favorite")
public class FavoriteController {
    @Autowired
    private FavoriteB2cService favoriteB2cService;

    /**
     * 加入收藏
     * @param modelJsonStr
     * @return
     */
    @RequestMapping("/addToFavorite")
    @ResponseBody
    public ResultData addToFavorite(HttpServletRequest request,String modelJsonStr, String userId, String shopId) {
        if (StringUtils.isBlank(modelJsonStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        FavoriteQuery favoriteQuery = JsonUtils.toObject(modelJsonStr, FavoriteQuery.class);
        favoriteQuery.setUserId(userId);
        favoriteQuery.setShopId(shopId);
        favoriteQuery.setOrderBizType(OrderBizType.trade);
        favoriteQuery.setClientType(ClientType.mobile);
        return favoriteB2cService.addToFavorite(favoriteQuery);
    }

    /**
     * 取消收藏
     * @param modelJsonStr
     * @return
     */
    @RequestMapping("/cancelFromFavorite")
    @ResponseBody
    public ResultData cancelFromFavorite(HttpServletRequest request,String modelJsonStr, String userId, String shopId) {
        if (StringUtils.isBlank(modelJsonStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        FavoriteQuery favoriteQuery = JsonUtils.toObject(modelJsonStr, FavoriteQuery.class);
        favoriteQuery.setUserId(userId);
        favoriteQuery.setShopId(shopId);
        favoriteQuery.setOrderBizType(OrderBizType.trade);
        favoriteQuery.setClientType(ClientType.mobile);
        return favoriteB2cService.cancelFavorite(favoriteQuery);
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
    public ResultData batchAddToFavorite(HttpServletRequest request, String goodIdStr, String userId, String shopId) {
        if (StringUtils.isBlank(goodIdStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        return favoriteB2cService.batchAddToFavorite(goodIdStr, userId, shopId, OrderBizType.trade);
    }

    //通过商品ID查收藏列表
    @RequestMapping("/getFavoriteListByGoodsSkuId")
    @ResponseBody
    public ResultData getFavoriteListByGoodsSkuId(HttpServletRequest request, String goodsSkuIdListStr, String userId, String shopId) {
        if (StringUtils.isBlank(goodsSkuIdListStr)) {
            return ResultData.createFail("商品信息不存在");
        }

        if (StringUtils.isBlank(userId)) {
            return ResultData.createFail();
        }

        return favoriteB2cService.getFavoriteListByGoodsId(goodsSkuIdListStr, userId, shopId, OrderBizType.trade);
    }

//    //从收藏加入购物车
//    @RequestMapping("/addToCartFromFavorite")
//    @ResponseBody
//    public ResultData addToCartFromFavorite(HttpServletRequest request, String modelJsonStr){
//        if (StringUtils.isBlank(modelJsonStr)) {
//            return ResultData.createFail("商品信息不存在");
//        }
//
//        List<CartQuery> cartQueryList = Lists.newArrayList();
//        String userId = AccountUtils.getCurrShopId();
//        cartQueryList = JsonUtils.toObject(modelJsonStr, ListUtils.getCollectionType(List.class, CartQuery.class));
//        return cartB2bService.addToCart(cartQueryList, userId);
//    }

    /**
     * 取消收藏
     * @param request
     * @param modelJsonStr
     * @return
     */
    @RequestMapping("/cancelFavorite")
    @ResponseBody
    public ResultData cancelFavorite(HttpServletRequest request, String modelJsonStr, String userId) {
        if (StringUtils.isBlank(modelJsonStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        List<String> ids = Lists.newArrayList();
        ids = JsonUtils.toObject(modelJsonStr, ListUtils.getCollectionType(List.class, String.class));
        favoriteB2cService.batchCancleFavorite(ids, userId, OrderBizType.trade);
        return ResultData.createSuccess();
    }

    //获取收藏列表
    @RequestMapping("/getFavoriteList")
    @ResponseBody
    public ResultData getFavoriteList(HttpServletRequest request, String modelJsonStr, String userId, String shopId) {
        if (StringUtils.isBlank(modelJsonStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        FavoriteQuery favoriteQuery = JsonUtils.toObject(modelJsonStr, FavoriteQuery.class) != null ?
                JsonUtils.toObject(modelJsonStr, FavoriteQuery.class) : new FavoriteQuery();
        favoriteQuery.setUserId(userId);
        favoriteQuery.setShopId(shopId);
        favoriteQuery.setClientType(ClientType.mobile);
        favoriteQuery.setOrderBizType(OrderBizType.trade);
        return favoriteB2cService.getFavoriteList(favoriteQuery);
    }
}
