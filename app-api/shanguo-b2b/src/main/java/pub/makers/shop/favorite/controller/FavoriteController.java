package pub.makers.shop.favorite.controller;

import com.dev.base.json.JsonUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lantu.base.util.ListUtils;
import com.sun.javafx.collections.MappingChange;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.utils.DateParseUtil;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.browseLog.entity.GoodsBrowseLog;
import pub.makers.shop.browseLog.pojo.GoodsBrowseLogQuery;
import pub.makers.shop.browseLog.vo.GoodsBrowseLogVo;
import pub.makers.shop.cart.pojo.CartQuery;
import pub.makers.shop.cart.service.CartB2bService;
import pub.makers.shop.favorite.pojo.FavoriteQuery;
import pub.makers.shop.favorite.service.FavoriteB2bService;
import pub.makers.shop.user.utils.AccountUtils;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by dy on 2017/6/19.
 */
@Controller
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteB2bService favoriteB2bService;

    @Autowired
    private CartB2bService cartB2bService;

    @RequestMapping("/list")
    public String list() {
        AccountUtils.getCurrShopId();
        return "www/favorite/list";
    }

    @RequestMapping("/addToFavorite")
    @ResponseBody
    public ResultData addToFavorite(HttpServletRequest request, String modelJsonStr) {
        if (StringUtils.isBlank(modelJsonStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        List<FavoriteQuery> cartQueries = Lists.newArrayList();
        String userId = AccountUtils.getCurrShopId(); //TODO 获取当前登录用户id
        FavoriteQuery favoriteQuery = JsonUtils.toObject(modelJsonStr, FavoriteQuery.class);
        favoriteQuery.setUserId(userId);
        favoriteQuery.setOrderBizType(OrderBizType.purchase);
        favoriteQuery.setClientType(ClientType.pc);
        return favoriteB2bService.addToFavorite(favoriteQuery);
    }

    @RequestMapping("/cancelFavorite")
    @ResponseBody
    public ResultData cancelFavorite(HttpServletRequest request, String modelJsonStr) {
        if (StringUtils.isBlank(modelJsonStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        List<FavoriteQuery> cartQueries = Lists.newArrayList();
        String userId = AccountUtils.getCurrShopId(); //TODO 获取当前登录用户id
        FavoriteQuery favoriteQuery = JsonUtils.toObject(modelJsonStr, FavoriteQuery.class);
        favoriteQuery.setUserId(userId);
        favoriteQuery.setOrderBizType(OrderBizType.purchase);
        favoriteQuery.setClientType(ClientType.pc);
        return favoriteB2bService.cancelFavorite(favoriteQuery);
    }

    @RequestMapping("/batchAddToFavorite")
    @ResponseBody
    public ResultData batchAddToFavorite(HttpServletRequest request, String goodIdStr) {
        if (StringUtils.isBlank(goodIdStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        String userId = AccountUtils.getCurrShopId(); //TODO 获取当前登录用户id
        return favoriteB2bService.batchAddToFavorite(goodIdStr, userId, OrderBizType.purchase);
    }

    @RequestMapping("/batchCancleFavorite")
    @ResponseBody
    public ResultData batchCancleFavorite(HttpServletRequest request, String modelJsonStr) {
        if (StringUtils.isBlank(modelJsonStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        List<String> ids = Lists.newArrayList();
        ids = JsonUtils.toObject(modelJsonStr, ListUtils.getCollectionType(List.class, String.class));
        String userId = AccountUtils.getCurrShopId(); //TODO 获取当前登录用户id
        favoriteB2bService.batchCancleFavorite(ids, userId, OrderBizType.purchase);
        return ResultData.createSuccess();
    }

    @RequestMapping("/addToCartFromFavorite")
    @ResponseBody
    public ResultData addToCartFromFavorite(HttpServletRequest request, String modelJsonStr){
        if (StringUtils.isBlank(modelJsonStr)) {
            return ResultData.createFail("商品信息不存在");
        }
        FavoriteQuery favoriteQuery = JsonUtils.toObject(modelJsonStr, FavoriteQuery.class);
        favoriteQuery.setUserId(AccountUtils.getCurrShopId());
        favoriteQuery.setStoreLevelId(AccountUtils.getCurrStoreLevelId());
        favoriteQuery.setOrderBizType(OrderBizType.purchase);
        favoriteQuery.setClientType(ClientType.pc);
        return favoriteB2bService.addToCartFromFavorite(favoriteQuery);
    }


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
        return favoriteB2bService.getFavoriteCount(favoriteQuery);
    }


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
        favoriteQuery.setStoreLevelId(storeLevelId);
        favoriteQuery.setUserId(userId);
        favoriteQuery.setClientType(ClientType.pc);
        favoriteQuery.setOrderBizType(OrderBizType.purchase);
        return favoriteB2bService.getFavoriteList(favoriteQuery);
    }

    /***
     * 添加足迹
     * @param logJson
     * @return
     */
    @RequestMapping("/addPug")
    @ResponseBody
    public ResultData addPug(String logJson) {
        if (StringUtils.isNotEmpty(logJson)) {
            try {
                GoodsBrowseLogVo logVo =JsonUtils.toObject(logJson,GoodsBrowseLogVo.class);
//                favoriteB2bService.addBrowseLog(logVo);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultData.createFail();
            }
            return ResultData.createSuccess();
        } else {
            return ResultData.createFail();
        }

    }



}
