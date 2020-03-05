package pub.makers.shop.favorite.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.browseLog.service.GoodsBrowseLogBizService;
import pub.makers.shop.cart.pojo.CartQuery;
import pub.makers.shop.cart.service.CartBizService;
import pub.makers.shop.favorite.pojo.FavoriteQuery;
import pub.makers.shop.favorite.vo.FavoriteVo;
import pub.makers.shop.purchaseGoods.service.PurchaseClassifyBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuBizService;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsSkuVo;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by kok on 2017/9/25.
 */
@Service
public class FavoriteB2cService {
    @Reference(version = "1.0.0")
    private FavoriteBizService favoriteBizService;
    @Reference(version = "1.0.0")
    private GoodsBrowseLogBizService goodsBrowseLogBizService;
    @Reference(version = "1.0.0")
    private PurchaseClassifyBizService purchaseClassifyBizService;
    @Reference(version = "1.0.0")
    private PurchaseGoodsSkuBizService purchaseGoodsSkuBizService;
    @Reference(version = "1.0.0")
    private CartBizService cartBizService;

    /**
     * 添加/取消收藏
     * @param favoriteQuery
     * @return
     */
    public ResultData addToFavorite(FavoriteQuery favoriteQuery) {
        favoriteBizService.addFavorite(favoriteQuery);
        return ResultData.createSuccess("收藏成功");
    }

    /**
     * 批量取消收藏
     * @param idList
     * @param userId
     * @param orderBizType
     */
    public void batchCancleFavorite(List<String> idList, String userId, OrderBizType orderBizType) {
        favoriteBizService.batchCancleFavorite(idList, userId, orderBizType);
    }

    /**
     * 获取收藏列表数据
     * @param favoriteQuery
     * @return
     */
    public ResultData getFavoriteList(FavoriteQuery favoriteQuery) {
        List<FavoriteVo> favoriteVos = favoriteBizService.getFavoriteList(favoriteQuery);
        return ResultData.createSuccess(favoriteVos);
    }


    /**
     *
     * @param goodsIdList
     * @param userId
     * @param orderBizType
     * @return
     */
    public ResultData getFavoriteListByGoodsId(String goodsIdList, String userId, String shopId, OrderBizType orderBizType) {
        List<FavoriteVo> favoriteVos = favoriteBizService.getFavoriteListByGoodsId(goodsIdList, userId, shopId, orderBizType);
        return ResultData.createSuccess(favoriteVos);
    }

    public ResultData batchAddToFavorite(String goodsIdList, String userId, String shopId, OrderBizType orderBizType) {
        List<FavoriteVo> favoriteVos = favoriteBizService.getFavoriteListByGoodsId(null, userId, shopId, orderBizType);
        List<String> idList = Arrays.asList(goodsIdList.split(","));
        Set<String> exIdList = Sets.newHashSet();

        if (!favoriteVos.isEmpty()) {
            for (FavoriteVo favoriteVo : favoriteVos) {
                for (String id : idList) {
                    if (!favoriteVo.getGoodsId().equals(id)) {
                        exIdList.add(id);
                    }
                }
            }
        } else {
            exIdList.addAll(idList);
        }

        for (String id : exIdList) {
            FavoriteQuery favoriteQuery = new FavoriteQuery();
            favoriteQuery.setGoodsId(id);
            favoriteQuery.setUserId(userId);
            favoriteQuery.setOrderBizType(orderBizType);
            favoriteBizService.addFavorite(favoriteQuery);
        }
        return ResultData.createSuccess();
    }


    public ResultData cancelFavorite(FavoriteQuery favoriteQuery) {
        favoriteBizService.cancelFavorite(favoriteQuery);
        return ResultData.createSuccess();
    }

    public ResultData addToCartFromFavorite(FavoriteQuery favoriteQuery) {
        List<PurchaseGoodsSkuVo> purchaseGoodsSkuVos = purchaseGoodsSkuBizService.getGoodsSkuList(favoriteQuery.getGoodsId(), favoriteQuery.getStoreLevelId());

        for (PurchaseGoodsSkuVo purchaseGoodsSkuVo : purchaseGoodsSkuVos) {
            CartQuery cartQuery = new CartQuery();
            cartQuery.setClientType(favoriteQuery.getClientType());
            cartQuery.setOrderBizType(favoriteQuery.getOrderBizType());
            cartQuery.setUserId(favoriteQuery.getUserId());
            cartQuery.setStoreLevelId(favoriteQuery.getStoreLevelId());
            cartQuery.setGoodsId(purchaseGoodsSkuVo.getId());
            cartQuery.setGoodsCount(purchaseGoodsSkuVo.getStartNum());
            cartBizService.addToCart(cartQuery);
        }
        return ResultData.createSuccess();
    }
}
