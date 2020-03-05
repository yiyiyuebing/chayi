package pub.makers.shop.cart.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.service.RegionService;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.baseGood.enums.ChangeGoodNumOperation;
import pub.makers.shop.baseGood.pojo.ChangeGoodNumQuery;
import pub.makers.shop.baseGood.service.BaseGoodBizService;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.cart.entity.Cart;
import pub.makers.shop.cart.pojo.CartQuery;
import pub.makers.shop.cart.vo.CartVo;
import pub.makers.shop.cart.vo.ChangeGoodNumVo;
import pub.makers.shop.promotion.enums.PromotionActivityType;
import pub.makers.shop.promotion.vo.GoodPromotionalInfoVo;
import pub.makers.shop.promotion.vo.PresellPromotionActivityVo;
import pub.makers.shop.promotion.vo.PromotionActivityVo;
import pub.makers.shop.promotion.vo.SalePromotionActivityVo;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuBizService;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsSampleVo;
import pub.makers.shop.tradeGoods.service.TradeGoodSkuBizService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/6/12.
 */
@Service(version = "1.0.0")
public class CartBizServiceImpl implements CartBizService {
    @Resource(name = "purchaseCartServiceImpl")
    private CartService purchaseCartService;
    @Resource(name = "tradeCartServiceImpl")
    private CartService tradeCartService;
    @Autowired
    private TradeGoodSkuBizService tradeGoodSkuBizService;
    @Autowired
    private PurchaseGoodsSkuBizService purchaseGoodsSkuBizService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private RegionService regionService;

    @Override
    public void addToCart(CartQuery query) {
        // 前置验证
        check(query);
        // 查询条件
        Conds conds = Conds.get().eq("goods_id", query.getGoodsId()).eq("user_id", query.getUserId()).ne("del_flag", BoolType.T.name());
        if (OrderBizType.trade.equals(query.getOrderBizType())) {
            conds.eq("shop_id", query.getShopId());
        }

        CartService cartService = getCartService(query.getOrderBizType());
        Cart exist = cartService.get(conds);
        if (exist != null) {
            query.setId(exist.getId().toString());
            addCartNum(query);
        } else {
            Cart cart = query.toCart();
            cart.setId(IdGenerator.getDefault().nextId());
            cart.setCreateDate(new Date());
            cart.setUpdateDate(new Date());
            cart.setDelFlag(BoolType.F.name());
            cartService.insert(cart);
        }
    }

    @Override
    public void delFromCart(final List<String> idList, final String userId, OrderBizType orderBizType) {
        // 前置验证
        ValidateUtils.notNull(idList, "商品信息不能为空");
        ValidateUtils.notNull(userId, "用户不能为空");
        ValidateUtils.notNull(orderBizType, "业务类型不能为空");

        final CartService cartService = getCartService(orderBizType);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                for (String id : idList) {
                    Cart cart = cartService.getById(id);
                    if (cart == null || !cart.getUserId().toString().equals(userId)) {
                        continue;
                    }

                    cartService.update(Update.byId(id).set("del_flag", BoolType.T.name()));
                }
            }
        });
    }

    @Override
    public List<CartVo> getCartList(CartQuery query) {
        // 前置验证
        ValidateUtils.notNull(query, "商品信息不能为空");
        ValidateUtils.notNull(query.getUserId(), "用户不能为空");
        ValidateUtils.notNull(query.getOrderBizType(), "业务类型不能为空");

        // 查询条件
        Conds conds = Conds.get().eq("user_id", query.getUserId()).ne("del_flag", BoolType.T.name());
        if (OrderBizType.trade.equals(query.getOrderBizType())) {
            ValidateUtils.notNull(query.getShopId(), "店铺不能为空");
            conds.eq("shop_id", query.getShopId());
        }

        CartService cartService = getCartService(query.getOrderBizType());
        List<Cart> cartList = cartService.list(conds);
        // 购物车商品信息
        BaseGoodBizService baseGoodBizService = getGoodService(query.getOrderBizType());
        List<String> goodIdList = Lists.newArrayList();
        for (Cart cart : cartList) {
            goodIdList.add(cart.getGoodsId().toString());
        }
        List<BaseGoodVo> baseGoodVoList = baseGoodBizService.getAllGoodSkuListBySkuId(goodIdList, query.getStoreLevelId(), query.getClientType());
        Map<String, BaseGoodVo> baseGoodVoMap = ListUtils.toKeyMap(baseGoodVoList, "skuId");
        List<CartVo> cartVoList1 = Lists.newArrayList();
        List<CartVo> cartVoList2 = Lists.newArrayList();
        for (Cart cart : cartList) {
            CartVo vo = CartVo.fromCart(cart);
            if (baseGoodVoMap.get(vo.getGoodsId()) != null) {
                BaseGoodVo baseGoodVo = baseGoodVoMap.get(vo.getGoodsId());

                vo.setGood(baseGoodVo);
                // 可加
                try {
                    addGoodNum(baseGoodVo, cart.getGoodsCount());
                    vo.setCanAdd(BoolType.T.name());
                } catch (Exception e) {
                    vo.setCanAdd(BoolType.F.name());
                }
                // 可减
                try {
                    subGoodNum(baseGoodVo, cart.getGoodsCount());
                    vo.setCanSub(BoolType.T.name());
                } catch (Exception e) {
                    vo.setCanSub(BoolType.F.name());
                }


                if (baseGoodVo.getPromotionalInfo() != null
                        && baseGoodVo.getPromotionalInfo().getBestActivity() != null) {
                    PromotionActivityVo basePromotionActivityVo = baseGoodVo.getPromotionalInfo().getBestActivity();
                    if (PromotionActivityType.presell.name().equals(basePromotionActivityVo.getActivityType())) {
                        PresellPromotionActivityVo promotionActivityVo = (PresellPromotionActivityVo) basePromotionActivityVo;
                        vo.setLimitFlg(promotionActivityVo.getLimitFlg());
                        vo.setLimitNum(promotionActivityVo.getLimitNum());
                    }

                    if (PromotionActivityType.sale.name().equals(basePromotionActivityVo.getActivityType())) {
                        SalePromotionActivityVo promotionActivityVo = (SalePromotionActivityVo) basePromotionActivityVo;
                        vo.setLimitFlg(promotionActivityVo.getLimitFlg());
                        vo.setLimitNum(promotionActivityVo.getLimitNum());
                    }
                }

                // 库存是否充足
                isCartValid(vo);
                if (BoolType.T.name().equals(vo.getIsValid())) {
                    cartVoList1.add(vo);
                } else {
                    cartVoList2.add(vo);
                }
            }
        }
        cartVoList1.addAll(cartVoList2);
        return cartVoList1;
    }

    private void isCartValid(CartVo cartVo) {
        cartVo.setIsValid(BoolType.T.name());
        BaseGoodVo goodVo = cartVo.getGood();
        cartVo.setIsStockEnough(isStockEnough(cartVo) ? BoolType.T.name() : BoolType.F.name());
        if (!"1".equals(goodVo.getStatus())) {
            cartVo.setIsValid(BoolType.F.name());
            cartVo.setMessage("售罄");
        } else if (!BoolType.T.name().equals(cartVo.getIsStockEnough())) {
            cartVo.setIsValid(BoolType.F.name());
            cartVo.setMessage("缺货");
        } else if (goodVo.getPromotionalInfo() != null && goodVo.getPromotionalInfo().getBestActivity() != null
                && PromotionActivityType.presell.name().equals(goodVo.getPromotionalInfo().getBestActivity().getActivityType())) {
            cartVo.setIsValid(BoolType.F.name());
            cartVo.setMessage("失效");
        }
    }

    private Boolean isStockEnough(CartVo cartVo) {
        Boolean isLimit = false;
        BaseGoodVo goodVo = cartVo.getGood();
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
    public void clearCart(CartQuery query) {
        // 前置验证
        ValidateUtils.notNull(query, "商品信息不能为空");
        ValidateUtils.notNull(query.getUserId(), "用户不能为空");
        ValidateUtils.notNull(query.getOrderBizType(), "业务类型不能为空");

        // 查询条件
        Conds conds = Conds.get().eq("user_id", query.getUserId()).ne("del_flag", BoolType.T.name());
        if (OrderBizType.trade.equals(query.getOrderBizType())) {
            ValidateUtils.notNull(query.getShopId(), "店铺不能为空");
            conds.eq("shop_id", query.getShopId());
        }

        CartService cartService = getCartService(query.getOrderBizType());
        List<Cart> cartList = cartService.list(conds);
        for (Cart cart : cartList) {
            cartService.update(Update.byId(cart.getId()).set("del_flag", BoolType.T.name()));
        }
    }

    @Override
    public void updateCartNum(CartQuery query) {
        // 前置验证
        ValidateUtils.notNull(query, "商品信息不能为空");
        ValidateUtils.notNull(query.getId(), "商品信息不能为空");
        ValidateUtils.notNull(query.getUserId(), "用户不能为空");
        ValidateUtils.notNull(query.getGoodsCount(), "商品数量不能为空");
        ValidateUtils.notNull(query.getOrderBizType(), "业务类型不能为空");

        CartService cartService = getCartService(query.getOrderBizType());
        Cart cart = cartService.getById(query.getId());
        ValidateUtils.notNull(cart, "购物车信息不存在");
        ValidateUtils.isTrue(cart.getUserId().toString().equals(query.getUserId()), "只能操作自己的购物车");

        cartService.update(Update.byId(cart.getId()).set("goods_count", query.getGoodsCount()).set("update_date", new Date()));
    }

    @Override
    public void updateCartNumByGoodsId(CartQuery query) {
        // 前置验证
        ValidateUtils.notNull(query, "商品信息不能为空");
        ValidateUtils.notNull(query.getGoodsId(), "商品信息不能为空");
        ValidateUtils.notNull(query.getUserId(), "用户不能为空");
        ValidateUtils.notNull(query.getGoodsCount(), "商品数量不能为空");
        ValidateUtils.notNull(query.getOrderBizType(), "业务类型不能为空");

        CartService cartService = getCartService(query.getOrderBizType());

        Conds conds = Conds.get().eq("goods_id", query.getGoodsId()).eq("user_id", query.getUserId()).ne("del_flag", BoolType.T.name());
        if (OrderBizType.trade.equals(query.getOrderBizType())) {
            conds.eq("shop_id", query.getShopId());
        }
        Cart cart = cartService.get(conds);
        ValidateUtils.notNull(cart, "购物车信息不存在");
        ValidateUtils.isTrue(cart.getUserId().toString().equals(query.getUserId()), "只能操作自己的购物车");

        cartService.update(Update.byId(cart.getId()).set("goods_count", query.getGoodsCount()).set("update_date", new Date()));
    }

    @Override
    public void addCartNum(CartQuery query) {
        // 前置验证
        ValidateUtils.notNull(query, "商品信息不能为空");
        ValidateUtils.notNull(query.getId(), "商品信息不能为空");
        ValidateUtils.notNull(query.getUserId(), "用户不能为空");
        ValidateUtils.notNull(query.getGoodsCount(), "商品数量不能为空");
        ValidateUtils.notNull(query.getOrderBizType(), "业务类型不能为空");

        CartService cartService = getCartService(query.getOrderBizType());
        Cart cart = cartService.getById(query.getId());
        ValidateUtils.notNull(cart, "购物车信息不存在");

        Integer goodsCount = cart.getGoodsCount() + query.getGoodsCount();
        query.setGoodsCount(goodsCount);

        cartService.update(Update.byId(cart.getId()).set("goods_count", query.getGoodsCount()).set("update_date", new Date()));
    }

    @Override
    public Long countCartList(CartQuery query) {
        // 前置验证
        ValidateUtils.notNull(query, "商品信息不能为空");
        ValidateUtils.notNull(query.getUserId(), "用户不能为空");
        ValidateUtils.notNull(query.getOrderBizType(), "业务类型不能为空");

        // 查询条件
        Conds conds = Conds.get().eq("user_id", query.getUserId()).ne("del_flag", BoolType.T.name());
        if (OrderBizType.trade.equals(query.getOrderBizType())) {
            ValidateUtils.notNull(query.getShopId(), "店铺不能为空");
            conds.eq("shop_id", query.getShopId());
        }

        CartService cartService = getCartService(query.getOrderBizType());
        Long count = cartService.count(conds);
        return count;
    }

    @Override
    public ChangeGoodNumVo changeGoodNum(ChangeGoodNumQuery query) {
        ValidateUtils.notNull(query, "参数不能为空");
        ValidateUtils.notNull(query.getSkuId(), "skuId不能为空");
        ValidateUtils.notNull(query.getOrderBizType(), "业务类型不能为空");
        ValidateUtils.notNull(query.getNowNum(), "当前数量不能为空");
        ValidateUtils.notNull(query.getOperation(), "操作类型不能为空");

        ChangeGoodNumVo vo;
        if (BoolType.T.name().equals(query.getIsSample())) { //样品
            Map<String, PurchaseGoodsSampleVo> sampleVoMap = purchaseGoodsSkuBizService.getGoodSampleBySku(Lists.newArrayList(query.getSkuId()));
            PurchaseGoodsSampleVo sampleVo = sampleVoMap.get(query.getSkuId());
            ValidateUtils.notNull(sampleVo, "样品不存在");
            List<BaseGoodVo> goodVoList = purchaseGoodsSkuBizService.getAllGoodSkuListBySkuId(Lists.newArrayList(query.getSkuId()), query.getStoreLevelId(), query.getClientType());
            ValidateUtils.isTrue(!goodVoList.isEmpty(), "商品不存在");
            BaseGoodVo goodVo = goodVoList.get(0);
            vo = doChangeSampleNum(goodVo, sampleVo, query.getNowNum(), query.getOperation());
        } else { //商品
            BaseGoodBizService baseGoodBizService = getGoodService(query.getOrderBizType());
            List<BaseGoodVo> goodVoList = baseGoodBizService.getAllGoodSkuListBySkuId(Lists.newArrayList(query.getSkuId()), query.getStoreLevelId(), query.getClientType());
            ValidateUtils.isTrue(!goodVoList.isEmpty(), "商品不存在");
            BaseGoodVo goodVo = goodVoList.get(0);
            vo = doChangeGoodNum(goodVo, query.getNowNum(), query.getOperation());
        }
        return vo;
    }

    private ChangeGoodNumVo doChangeSampleNum(BaseGoodVo goodVo, PurchaseGoodsSampleVo sampleVo, Integer nowNum, ChangeGoodNumOperation operation) {
        ChangeGoodNumVo vo = new ChangeGoodNumVo();
        Integer num;
        if (ChangeGoodNumOperation.add.equals(operation)) {
            num = addSampleNum(goodVo, sampleVo, nowNum);
        } else {
            num = subSampleNum(goodVo, sampleVo, nowNum);
        }
        vo.setNum(num);
        // 可加
        try {
            addSampleNum(goodVo, sampleVo, num);
            vo.setCanAdd(BoolType.T.name());
        } catch (Exception e) {
            vo.setCanAdd(BoolType.F.name());
        }
        // 可减
        try {
            subSampleNum(goodVo, sampleVo, num);
            vo.setCanSub(BoolType.T.name());
        } catch (Exception e) {
            vo.setCanSub(BoolType.F.name());
        }
        return vo;
    }

    private Integer addSampleNum(BaseGoodVo goodVo, PurchaseGoodsSampleVo sampleVo, Integer num) {
        Integer newNum = num + 1;
        ValidateUtils.isTrue(goodVo.getStock() >= newNum, "不能超过库存");
        if (sampleVo.getStartNum() > newNum) {
            newNum = sampleVo.getStartNum();
        }
        return newNum;
    }

    private Integer subSampleNum(BaseGoodVo goodVo, PurchaseGoodsSampleVo sampleVo, Integer num) {
        ValidateUtils.isTrue(sampleVo.getStartNum() <= (num - 1), "不能小于起订量");
        return num - 1;
    }

    private ChangeGoodNumVo doChangeGoodNum(BaseGoodVo goodVo, Integer nowNum, ChangeGoodNumOperation operation) {
        ChangeGoodNumVo vo = new ChangeGoodNumVo();
        Integer num;
        if (ChangeGoodNumOperation.add.equals(operation)) {
            num = addGoodNum(goodVo, nowNum);
        } else {
            num = subGoodNum(goodVo, nowNum);
        }
        vo.setNum(num);
        // 可加
        try {
            addGoodNum(goodVo, num);
            vo.setCanAdd(BoolType.T.name());
        } catch (Exception e) {
            vo.setCanAdd(BoolType.F.name());
        }
        // 可减
        try {
            subGoodNum(goodVo, num);
            vo.setCanSub(BoolType.T.name());
        } catch (Exception e) {
            vo.setCanSub(BoolType.F.name());
        }
        return vo;
    }

    private Integer addGoodNum(BaseGoodVo goodVo, Integer num) {
        Integer addNum = 1;
        Boolean isStartMul = BoolType.T.name().equals(goodVo.getMulNumFlag());
        if (goodVo.getPromotionalInfo() != null && goodVo.getPromotionalInfo().getBestActivity() != null
                && Lists.newArrayList(PromotionActivityType.presell.name(), PromotionActivityType.sale.name())
                .contains(goodVo.getPromotionalInfo().getBestActivity().getActivityType()) && new Date().after(goodVo.getPromotionalInfo().getBestActivity().getActivityStart())) {
            Boolean isLimit;
            Integer limitNum;
            Integer maxNum;
            String numName;
            if (PromotionActivityType.presell.name().equals(goodVo.getPromotionalInfo().getBestActivity().getActivityType())) {
                // 预售商品
                PresellPromotionActivityVo activityVo = (PresellPromotionActivityVo) goodVo.getPromotionalInfo().getBestActivity();
                isLimit = BoolType.T.name().equals(activityVo.getLimitFlg());
                limitNum = activityVo.getLimitNum();
                maxNum = activityVo.getPresellNum();
                numName = "库存";
            } else {
                // 打折商品
                SalePromotionActivityVo activityVo = (SalePromotionActivityVo) goodVo.getPromotionalInfo().getBestActivity();
                isLimit = BoolType.T.name().equals(activityVo.getLimitFlg());
                limitNum = activityVo.getLimitNum();
                if (activityVo.getMaxNum() == null) {
                    maxNum = goodVo.getStock();
                    numName = "库存";
                } else {
                    maxNum = activityVo.getMaxNum();
                    numName = "可售卖数量";
                }
            }
            if (isLimit) {
                // 预售限购判断限购数
                ValidateUtils.isTrue(limitNum >= addNum(num, addNum), "不能超过限购数");
            } else {
                // 预售不限购
                if (isStartMul) {
                    // 起订量倍增
                    addNum = goodVo.getStartNum();
                }
            }
            ValidateUtils.isTrue(maxNum >= addNum(num, addNum), "不能超过" + numName);
        } else {
            // 普通商品
            if (isStartMul) {
                // 起订量倍增
                addNum = goodVo.getStartNum();
            }
            ValidateUtils.isTrue(goodVo.getStock() >= addNum(num, addNum), "不能超过库存");
        }

        return addNum(num, addNum);
    }

    private Integer addNum(Integer nowNum, Integer addNum) {
        return nowNum / addNum * addNum + addNum;
    }

    private Integer subGoodNum(BaseGoodVo goodVo, Integer num) {
        Integer subNum = 1;
        Integer minNum = 1;
        Integer maxNum = goodVo.getStock();
        Boolean isLimit = false;
        GoodPromotionalInfoVo infoVo = goodVo.getPromotionalInfo();
        if (infoVo != null && infoVo.getBestActivity() != null && Lists.newArrayList(PromotionActivityType.presell.name(), PromotionActivityType.sale.name()).contains(infoVo.getBestActivity().getActivityType()) && new Date().after(goodVo.getPromotionalInfo().getBestActivity().getActivityStart())) {
            if (PromotionActivityType.presell.name().equals(infoVo.getBestActivity().getActivityType())) {
                // 预售商品
                PresellPromotionActivityVo activityVo = (PresellPromotionActivityVo) goodVo.getPromotionalInfo().getBestActivity();
                maxNum = activityVo.getPresellNum();
                isLimit = BoolType.T.name().equals(activityVo.getLimitFlg());
                if (isLimit) {
                    maxNum = Math.min(activityVo.getLimitNum(), maxNum);
                }
            } else {
                // 打折商品比较活动可售卖数量
                SalePromotionActivityVo activityVo = (SalePromotionActivityVo) infoVo.getBestActivity();
                isLimit = BoolType.T.name().equals(activityVo.getLimitFlg());
                if (activityVo.getMaxNum() != null) {
                    maxNum = activityVo.getMaxNum();
                }
                if (isLimit) {
                    maxNum = Math.min(activityVo.getLimitNum(), maxNum);
                }
            }
        }
        // 不限购有起订量按起订量递减
        if (!isLimit && BoolType.T.name().equals(goodVo.getMulNumFlag())) {
            subNum = goodVo.getStartNum();
            minNum = goodVo.getStartNum();
        }
        Integer newNum = subNum(num, subNum);
        if (newNum > maxNum) {
            newNum = maxNum % subNum == 0 ? maxNum : maxNum / subNum * subNum;
        }
        ValidateUtils.isTrue(minNum <= newNum, "不能小于起订量");
        return newNum;
    }

    private Integer subNum(Integer nowNum, Integer subNum) {
        return nowNum % subNum == 0 ? nowNum - subNum : nowNum / subNum * subNum;
    }

    private void check(CartQuery query) {
        ValidateUtils.notNull(query, "商品信息不能为空");
        ValidateUtils.notNull(query.getGoodsId(), "商品不能为空");
        ValidateUtils.notNull(query.getUserId(), "用户不能为空");
        ValidateUtils.notNull(query.getGoodsCount(), "商品数量不能为空");
        ValidateUtils.notNull(query.getOrderBizType(), "业务类型不能为空");
        if (OrderBizType.trade.equals(query.getOrderBizType())) {
            ValidateUtils.notNull(query.getShopId(), "店铺不能为空");
        }
    }

    private CartService getCartService(OrderBizType orderBizType) {
        if (OrderBizType.trade.equals(orderBizType)) {
            return tradeCartService;
        } else {
            return purchaseCartService;
        }
    }

    private BaseGoodBizService getGoodService(OrderBizType orderBizType) {
        if (OrderBizType.trade.equals(orderBizType)) {
            return tradeGoodSkuBizService;
        } else {
            return purchaseGoodsSkuBizService;
        }
    }
}
