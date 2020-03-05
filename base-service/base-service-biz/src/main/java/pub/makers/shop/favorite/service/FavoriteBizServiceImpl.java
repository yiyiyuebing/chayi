package pub.makers.shop.favorite.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.baseGood.service.BaseGoodBizService;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.favorite.entity.Favorite;
import pub.makers.shop.favorite.pojo.FavoriteQuery;
import pub.makers.shop.favorite.vo.FavoriteVo;
import pub.makers.shop.promotion.enums.PromotionActivityType;
import pub.makers.shop.promotion.vo.SalePromotionActivityVo;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuBizService;
import pub.makers.shop.tradeGoods.service.TradeGoodSkuBizService;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by dy on 2017/6/19.
 */
@Service(version = "1.0.0")
public class FavoriteBizServiceImpl implements FavoriteBizService {

    @Resource(name = "purchaseFavoriteServiceImpl")
    private FavoriteService purchaseFavoriteService;
    @Resource(name = "tradeFavoriteServiceImpl")
    private FavoriteService tradeFavoriteService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private TradeGoodSkuBizService tradeGoodSkuBizService;
    @Autowired
    private PurchaseGoodsSkuBizService purchaseGoodsSkuBizService;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public void addFavorite(FavoriteQuery query) {
        //前置验证
        check(query);
        Conds conds = Conds.get().eq("goods_id", query.getGoodsId()).eq("user_id", query.getUserId()).ne("del_flag", BoolType.T.name());
        if (OrderBizType.trade.equals(query.getOrderBizType())) {
            conds.eq("shop_id", query.getShopId());
        }
        FavoriteService favoriteService = getFavoriteService(query.getOrderBizType());
        Favorite existFavorite = favoriteService.get(conds); //获取是否已经收藏
        if (existFavorite == null) { //不存在则添加收藏
            Favorite favorite = query.getFavorite();
            favorite.setId(IdGenerator.getDefault().nextId());
            favorite.setCreateDate(new Date());
            favorite.setUpdateDate(new Date());
            favorite.setDelFlag(BoolType.F.toString());
            favoriteService.insert(favorite);
        }

    }

    @Override
    public void batchCancleFavorite(final List<String> idList, final String userId, OrderBizType orderBizType) {
        // 前置验证
        ValidateUtils.notNull(idList, "商品信息不能为空");
        ValidateUtils.notNull(userId, "用户不能为空");
        ValidateUtils.notNull(orderBizType, "业务类型不能为空");

        final FavoriteService favoriteService = getFavoriteService(orderBizType);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                for (String id : idList) {
                    Favorite favorite = favoriteService.getById(id);
                    if (favorite == null || !favorite.getUserId().toString().equals(userId)) {
                        continue;
                    }
                    favoriteService.update(Update.byId(id).set("del_flag", BoolType.T.name()));
                }
            }
        });

    }

    @Override
    public List<FavoriteVo> getFavoriteList(FavoriteQuery query) {

        // 前置验证
        ValidateUtils.notNull(query, "商品信息不能为空");
        ValidateUtils.notNull(query.getUserId(), "用户不能为空");
        ValidateUtils.notNull(query.getOrderBizType(), "业务类型不能为空");

        Map<String, Object> param = new HashMap<String, Object>();


        // 查询条件
        if (OrderBizType.trade.equals(query.getOrderBizType())) {
            ValidateUtils.notNull(query.getShopId(), "店铺不能为空");
        }

        Conds conds = Conds.get();
        if (StringUtils.isNotEmpty(query.getShopId())) {
            conds.eq("shop_id", query.getShopId());
        }
        if (StringUtils.isNotEmpty(query.getGoodsId())) {
            conds.eq("goods_id", query.getGoodsId());
        }
        FavoriteService favoriteService = getFavoriteService(query.getOrderBizType());
        List<Favorite> favorites = favoriteService.list(conds.eq("user_id", query.getUserId()).eq("del_flag", BoolType.F.name()).order("create_date desc"));
        List<FavoriteVo> favoriteVos = Lists.newArrayList();
        for (Favorite favorite : favorites) {
            FavoriteVo favoriteVo = new FavoriteVo();
            favoriteVo = favoriteVo.fromFavorite(favorite);
            BaseGoodBizService baseGoodBizService = getGoodService(query.getOrderBizType());
            List<BaseGoodVo> baseGoodList = baseGoodBizService.getAllGoodSkuListByGoodId(Lists.newArrayList(favorite.getGoodsId().toString()), query.getStoreLevelId(), query.getClientType());
            favoriteVo.setGood(baseGoodList.size() > 0 ? baseGoodList.get(0) : new BaseGoodVo());
            // 库存是否充足
            isCartValid(favoriteVo);
            favoriteVos.add(favoriteVo);
        }
        return favoriteVos;
    }

    private void isCartValid(FavoriteVo favoriteVo) {
        favoriteVo.setIsValid(BoolType.T.name());
        BaseGoodVo goodVo = favoriteVo.getGood();
        if (!"1".equals(goodVo.getStatus())) {
            favoriteVo.setIsValid(BoolType.F.name());
            favoriteVo.setMessage("售罄");
        } else if (!isStockEnough(favoriteVo)) {
            favoriteVo.setIsValid(BoolType.F.name());
            favoriteVo.setMessage("缺货");
        }
    }

    private Boolean isStockEnough(FavoriteVo favoriteVo) {
        Boolean isLimit = false;
        BaseGoodVo goodVo = favoriteVo.getGood();
        Integer minNum = 1;
        if (goodVo.getPromotionalInfo() != null && goodVo.getPromotionalInfo().getBestActivity() != null
                && PromotionActivityType.sale.name().equals(goodVo.getPromotionalInfo().getBestActivity().getActivityType())) {
            // 预售商品
            SalePromotionActivityVo activityVo = (SalePromotionActivityVo) goodVo.getPromotionalInfo().getBestActivity();
            isLimit = BoolType.T.name().equals(activityVo.getLimitFlg());
        }
        // 不限购有起订量按起订量递减
        if (!isLimit && BoolType.T.name().equals(goodVo.getMulNumFlag())) {
            minNum = goodVo.getStartNum();
        }
        return minNum <= goodVo.getStock();
    }

    @Override
    public List<FavoriteVo> getFavoriteListByGoodsId(String goodsSkuIdList, String userId, String shopId, OrderBizType orderBizType) {

        // 前置验证
//        ValidateUtils.notNull(goodsSkuIdList, "商品信息不能为空");
        ValidateUtils.notNull(orderBizType, "业务类型不能为空");

        Conds conds = new Conds();
        if (StringUtils.isNotBlank(goodsSkuIdList)) {
            conds.in("goods_id", Arrays.asList(goodsSkuIdList.split(",")));
        }
        if (StringUtils.isNotEmpty(shopId)) {
            conds.eq("shop_id", shopId);
        }
        conds.eq("user_id", userId);
        conds.ne("del_flag", BoolType.T.name());

        FavoriteService favoriteService = getFavoriteService(orderBizType);
        List<Favorite> favorites = favoriteService.list(conds);
        List<FavoriteVo> favoriteVos = Lists.newArrayList();
        for (Favorite favorite : favorites) {
            FavoriteVo favoriteVo = new FavoriteVo();
            favoriteVo = favoriteVo.fromFavorite(favorite);
            favoriteVos.add(favoriteVo);
        }
        return favoriteVos;
    }

    @Override
    public Long getFavoriteCount(FavoriteQuery favoriteQuery) {

        // 前置验证
        ValidateUtils.notNull(favoriteQuery, "商品信息不能为空");
        ValidateUtils.notNull(favoriteQuery.getOrderBizType(), "业务类型不能为空");

        Map<String, Object> param = new HashMap<String, Object>();

        // 查询条件
        if (OrderBizType.trade.equals(favoriteQuery.getOrderBizType())) {
            ValidateUtils.notNull(favoriteQuery.getShopId(), "店铺不能为空");
            favoriteQuery.setShopId(null);
        }
        param.put("query", favoriteQuery);
        String sql = FreeMarkerHelper.getValueFromTpl("sql/favorite/getPurchaseFavoriteList.sql", param);
        sql = "select count(*) from (" + sql + ") aa";
        Long totalCount = jdbcTemplate.queryForObject(sql, Long.class);

        return totalCount == null ? 0L : totalCount;

//        Conds conds = new Conds();
//        if (StringUtils.isNotBlank(favoriteQuery.getGoodsId())) {
//            conds.eq("goods_id", favoriteQuery.getGoodsId());
//        }
//        if (StringUtils.isNotBlank(favoriteQuery.getUserId())) {
//            conds.eq("user_id", favoriteQuery.getUserId());
//        }
//        conds.ne("del_flag", BoolType.T.name());
//        FavoriteService favoriteService = getFavoriteService(favoriteQuery.getOrderBizType());
//        return favoriteService.count(conds);
    }

    @Override
    public void cancelFavorite(FavoriteQuery query) {
        Conds conds = Conds.get().eq("goods_id", query.getGoodsId()).eq("user_id", query.getUserId()).ne("del_flag", BoolType.T.name());
        if (OrderBizType.trade.equals(query.getOrderBizType())) {
            conds.eq("shop_id", query.getShopId());
        }
        FavoriteService favoriteService = getFavoriteService(query.getOrderBizType());
        Favorite existFavorite = favoriteService.get(conds); //获取是否已经收藏
        ValidateUtils.notNull(existFavorite, "当前商品不存在");
        favoriteService.update(Update.byId(existFavorite.getId()).set("del_flag", BoolType.T.toString()));
    }

    private FavoriteService getFavoriteService(OrderBizType orderBizType) {
        if (OrderBizType.purchase.equals(orderBizType)) {
            return purchaseFavoriteService;
        } else {
            return tradeFavoriteService;
        }
    }

    private BaseGoodBizService getGoodService(OrderBizType orderBizType) {
        if (OrderBizType.trade.equals(orderBizType)) {
            return tradeGoodSkuBizService;
        } else {
            return purchaseGoodsSkuBizService;
        }
    }

    private void check(FavoriteQuery query) {
        ValidateUtils.notNull(query, "商品信息不能为空");
        ValidateUtils.notNull(query.getGoodsId(), "商品不能为空");
        ValidateUtils.notNull(query.getUserId(), "用户不能为空");
        ValidateUtils.notNull(query.getOrderBizType(), "业务类型不能为空");
        if (OrderBizType.trade.equals(query.getOrderBizType())) {
            ValidateUtils.notNull(query.getShopId(), "店铺不能为空");
        }
    }
}
