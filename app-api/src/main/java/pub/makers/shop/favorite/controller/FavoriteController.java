package pub.makers.shop.favorite.controller;

import com.dev.base.json.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.favorite.pojo.FavoriteQuery;
import pub.makers.shop.favorite.service.FavoriteAppService;
import pub.makers.shop.user.utils.AccountUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kok on 2017/7/1.
 */
@Controller
@RequestMapping("favorite")
public class FavoriteController {
    @Autowired
    private FavoriteAppService favoriteAppService;

    @RequestMapping("/addToFavorite")
    @ResponseBody
    public ResultData addToFavorite(HttpServletRequest request, String goodsId) {
        String userId = AccountUtils.getCurrShopId(); //TODO 获取当前登录用户id
        FavoriteQuery favoriteQuery = new FavoriteQuery();
        favoriteQuery.setGoodsId(goodsId);
        favoriteQuery.setUserId(userId);
        favoriteQuery.setOrderBizType(OrderBizType.purchase);
        return favoriteAppService.addToFavorite(favoriteQuery);
    }

    @RequestMapping("/batchAddToFavorite")
    @ResponseBody
    public ResultData batchAddToFavorite(HttpServletRequest request, String goodIdStr) {
        if (StringUtils.isBlank(goodIdStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        String userId = AccountUtils.getCurrShopId(); //TODO 获取当前登录用户id
        return favoriteAppService.batchAddToFavorite(goodIdStr, userId, OrderBizType.purchase);
    }

    @RequestMapping("/cancleFavorite")
    @ResponseBody
    public ResultData cancleFavorite(HttpServletRequest request, String ids) {
        ValidateUtils.notNull(ids, "商品信息不存在");
        String userId = AccountUtils.getCurrShopId(); //TODO 获取当前登录用户id
        favoriteAppService.cancleFavorite(ids, userId, OrderBizType.purchase);
        return ResultData.createSuccess();
    }


    @RequestMapping("/getFavoriteListByGoodsId")
    @ResponseBody
    public ResultData getFavoriteListByGoodsSkuId(HttpServletRequest request, String goodsIdList) {
        if (StringUtils.isBlank(goodsIdList)) {
            return ResultData.createFail("商品信息不存在");
        }
        String userId = AccountUtils.getCurrShopId(); //TODO 获取当前登录用户id

        if (StringUtils.isBlank(userId)) {
            return ResultData.createFail();
        }

        return favoriteAppService.getFavoriteListByGoodsId(goodsIdList, userId, OrderBizType.purchase);
    }

    @RequestMapping("/getFavoriteAllCount")
    @ResponseBody
    public ResultData getFavoriteAllCount(HttpServletRequest request, String modelJsonStr, String countType) {
        if (StringUtils.isBlank(modelJsonStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        FavoriteQuery favoriteQuery = JsonUtils.toObject(modelJsonStr, FavoriteQuery.class) != null ?
                JsonUtils.toObject(modelJsonStr, FavoriteQuery.class) : new FavoriteQuery();
        String userId = AccountUtils.getCurrShopId();
        favoriteQuery.setUserId(userId);
        favoriteQuery.setOrderBizType(OrderBizType.purchase);
        return favoriteAppService.getFavoriteCount(favoriteQuery);
    }


    @RequestMapping("/getFavoriteList")
    @ResponseBody
    public ResultData getFavoriteList(HttpServletRequest request, String modelJson) {
        if (StringUtils.isBlank(modelJson)) {
            return ResultData.createFail("商品信息不存在");
        }
        FavoriteQuery favoriteQuery = JsonUtils.toObject(modelJson, FavoriteQuery.class) != null ?
                JsonUtils.toObject(modelJson, FavoriteQuery.class) : new FavoriteQuery();
        String userId = AccountUtils.getCurrShopId();
        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        favoriteQuery.setStoreLevelId(storeLevelId);
        favoriteQuery.setUserId(userId);
        favoriteQuery.setOrderBizType(OrderBizType.purchase);
        return favoriteAppService.getFavoriteList(favoriteQuery);
    }
}
