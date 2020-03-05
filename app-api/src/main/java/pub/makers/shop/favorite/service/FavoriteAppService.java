package pub.makers.shop.favorite.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.favorite.pojo.FavoriteQuery;
import pub.makers.shop.favorite.vo.FavoriteVo;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by kok on 2017/7/1.
 */
@Service
public class FavoriteAppService {
    @Reference(version = "1.0.0")
    private FavoriteBizService favoriteBizService;

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
     * @param goodsIdList
     * @param userId
     * @param orderBizType
     */
    public void cancleFavorite(String goodsIdList, String userId, OrderBizType orderBizType) {
        List<FavoriteVo> favoriteVos = favoriteBizService.getFavoriteListByGoodsId(goodsIdList, userId, null, orderBizType);
        List<String> idList = Lists.newArrayList();
        for (FavoriteVo favoriteVo : favoriteVos) {
            idList.add(favoriteVo.getId());
        }
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
    public ResultData getFavoriteListByGoodsId(String goodsIdList, String userId, OrderBizType orderBizType) {
        List<FavoriteVo> favoriteVos = favoriteBizService.getFavoriteListByGoodsId(goodsIdList, userId, null, orderBizType);
        return ResultData.createSuccess(favoriteVos);
    }

    public ResultData getFavoriteCount(FavoriteQuery favoriteQuery) {
        Long count = favoriteBizService.getFavoriteCount(favoriteQuery);
        return ResultData.createSuccess(count.intValue(), favoriteQuery.getPageNum(), favoriteQuery.getPageSize(), null);
    }

    public ResultData batchAddToFavorite(String goodsIdList, String userId, OrderBizType orderBizType) {
        List<FavoriteVo> favoriteVos = favoriteBizService.getFavoriteListByGoodsId(null, userId, null, orderBizType);
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
}
