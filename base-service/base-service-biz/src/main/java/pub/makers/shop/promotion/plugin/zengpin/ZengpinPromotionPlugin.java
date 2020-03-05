package pub.makers.shop.promotion.plugin.zengpin;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.*;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.baseGood.enums.PageTplApply;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderListGoodType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.*;
import pub.makers.shop.baseOrder.utils.BaseOrderHelps;
import pub.makers.shop.promotion.entity.ManzengActivityApply;
import pub.makers.shop.promotion.enums.ManzengDiscountType;
import pub.makers.shop.promotion.enums.PromotionActivityType;
import pub.makers.shop.promotion.plugin.PromotionPlugin;
import pub.makers.shop.promotion.pojo.ManzengQuery;
import pub.makers.shop.promotion.service.ManzengBizService;
import pub.makers.shop.promotion.vo.*;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoods;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSku;
import pub.makers.shop.purchaseGoods.service.PurchaseClassifyBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuService;
import pub.makers.shop.purchaseGoods.vo.PurchaseClassifyVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.service.SubbranchAccountBizService;
import pub.makers.shop.tradeGoods.entity.TradeGood;
import pub.makers.shop.tradeGoods.entity.TradeGoodSku;
import pub.makers.shop.tradeGoods.service.TradeGoodService;
import pub.makers.shop.tradeGoods.service.TradeGoodSkuService;
import pub.makers.shop.tradeGoods.service.TradeGoodsClassifyBizService;
import pub.makers.shop.tradeGoods.vo.TradeGoodsClassifyVo;
import pub.makers.shop.tradeOrder.enums.IndentListStatus;
import pub.makers.shop.tradeOrder.vo.IndentListVo;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ZengpinPromotionPlugin implements PromotionPlugin {
    @Autowired
    private ManzengBizService manzengBizService;
    @Autowired
    private PurchaseGoodsService purchaseGoodsService;
    @Autowired
    private PurchaseGoodsSkuService purchaseGoodsSkuService;
    @Autowired
    private TradeGoodService tradeGoodService;
    @Autowired
    private TradeGoodSkuService tradeGoodSkuService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Reference(version = "1.0.0")
    private SubbranchAccountBizService subbranchAccountBizService;
    @Autowired
    private PurchaseClassifyBizService purchaseClassifyBizService;
    @Autowired
    private TradeGoodsClassifyBizService tradeGoodsClassifyBizService;
    @Autowired
    private PurchaseGoodsSkuBizService purchaseGoodsSkuBizService;

    @Override
    public BaseOrder applyForCreateOrer(PromotionOrderQuery query, TradeContext tc) {
        BaseOrder order = query.getOrderInfo();
        ManzengQuery manzengQuery = new ManzengQuery();
        // 克隆一份数据
        List<BaseOrderItem> itemList = Lists.newArrayList(order.getItemList());
        // 散茶不参加活动
        List<PurchaseGoodsSku> shanchaList = purchaseGoodsSkuBizService.querySanchaSku(ListUtils.getIdSet(itemList, "goodSkuId"));
        Set<String> shanchaIds = ListUtils.getIdSet(shanchaList, "id");
        // 按skuId分组订单明细
        ListMultimap<String, BaseOrderItem> goodItemMap = ArrayListMultimap.create();
        for (BaseOrderItem item : itemList) {
            if (!BoolType.T.name().equals(item.getGiftFlag()) && !shanchaIds.contains(item.getGoodSkuId())) {
                goodItemMap.put(item.getGoodSkuId(), item);
            }
        }
        // 按明细商品分类分组订单明细
        ListMultimap<String, BaseOrderItem> classifyItemMap = ArrayListMultimap.create();
        ListMultimap<String, String> skuClassifyMap = ArrayListMultimap.create();
        if (OrderBizType.trade.equals(query.getOrderBizType())) {
            List<TradeGoodSku> skuList = tradeGoodSkuService.list(Conds.get().in("id", goodItemMap.keySet()));
            Map<String, TradeGoodSku> skuMap = ListUtils.toKeyMap(skuList, "goodId");
            List<TradeGood> goodList = tradeGoodService.list(Conds.get().in("id", skuMap.keySet()));
            Set<String> classifySet = Sets.newHashSet();
            for (TradeGood goods : goodList) {
                if (StringUtils.isNotEmpty(goods.getGroupId())) {
                    classifySet.addAll(Arrays.asList(StringUtils.split(goods.getGroupId(), ",")));
                }
            }
            List<TradeGoodsClassifyVo> classifyVoList = tradeGoodsClassifyBizService.findAllAndParent(Lists.newArrayList(classifySet));
            Map<String, TradeGoodsClassifyVo> classifyVoMap = ListUtils.toKeyMap(classifyVoList, "id");
            Map<String, TradeGood> goodsMap = ListUtils.toKeyMap(goodList, "id");
            for (TradeGoodSku sku : skuList) {
                TradeGood goods = goodsMap.get(sku.getGoodId().toString());
                if (StringUtils.isEmpty(goods.getGroupId())) {
                    continue;
                }
                for (String classifyId : StringUtils.split(goods.getGroupId(), ",")) {
                    TradeGoodsClassifyVo classifyVo = classifyVoMap.get(classifyId);
                    while (classifyVo != null) {
                        skuClassifyMap.put(sku.getId().toString(), classifyVo.getId());
                        classifyVo = classifyVoMap.get(classifyVo.getParentId());
                    }
                }
            }
        } else {
            PurchaseOrderVo orderVo = (PurchaseOrderVo) order;
            Subbranch subbranch = subbranchAccountBizService.getMainSubbranch(orderVo.getSubbranchId() + ""); //子账号处理，如果为子账号，则取出主账号。
            manzengQuery.setStoreLevelId(subbranch.getLevelId() + "");
            manzengQuery.setClientType(ClientType.valueOf(orderVo.getClientType()));
            // 父分类
            List<PurchaseGoodsSku> skuList = purchaseGoodsSkuService.list(Conds.get().in("id", goodItemMap.keySet()));
            List<PurchaseGoods> goodList = purchaseGoodsService.list(Conds.get().in("id", ListUtils.getIdSet(skuList, "purGoodsId")));
            Set<String> classifySet = Sets.newHashSet();
            for (PurchaseGoods goods : goodList) {
                if (StringUtils.isNotEmpty(goods.getGroupId())) {
                    classifySet.addAll(Arrays.asList(StringUtils.split(goods.getGroupId(), ",")));
                }
            }
            List<PurchaseClassifyVo> classifyVoList = purchaseClassifyBizService.findAllAndParent(Lists.newArrayList(classifySet), subbranch.getLevelId() + "");
            Map<String, PurchaseClassifyVo> classifyVoMap = ListUtils.toKeyMap(classifyVoList, "id");
            Map<String, PurchaseGoods> goodsMap = ListUtils.toKeyMap(goodList, "id");
            for (PurchaseGoodsSku sku : skuList) {
                PurchaseGoods goods = goodsMap.get(sku.getPurGoodsId());
                if (StringUtils.isEmpty(goods.getGroupId())) {
                    continue;
                }
                for (String classifyId : StringUtils.split(goods.getGroupId(), ",")) {
                    PurchaseClassifyVo classifyVo = classifyVoMap.get(classifyId);
                    while (classifyVo != null) {
                        skuClassifyMap.put(sku.getId(), classifyVo.getId());
                        classifyVo = classifyVoMap.get(classifyVo.getParentId());
                    }
                }
            }
        }
        for (BaseOrderItem item : itemList) {
            List<String> classifyIdList = skuClassifyMap.get(item.getGoodSkuId());
            for (String classifyId : classifyIdList) {
                classifyItemMap.put(classifyId, item);
            }
        }
        // 查询满减满赠活动
        manzengQuery.setClientType(ClientType.mobile);
        manzengQuery.setGoodSkuIdList(Lists.newArrayList(goodItemMap.keySet()));
        manzengQuery.setClassifyIdList(Lists.newArrayList(classifyItemMap.keySet()));
        manzengQuery.setOrderBizType(query.getOrderBizType().name());
        List<ManzengActivityVo> activityVoList = manzengBizService.getActivityList(manzengQuery);

        // 应用满减满赠活动
        ListMultimap<String, BaseOrderItem> zengpinMap = ArrayListMultimap.create();
        ManzengActivityVo discountActivityVo = null;
        ManzengRuleVo discountRuleVo = null;
        List<BaseOrderItem> discountItemList = Lists.newArrayList();
        List<PromotionActivityVo> manzengActivityList = Lists.newArrayList();
        for (ManzengActivityVo activityVo : activityVoList) {
            ManzengActivityApply apply = activityVo.getApply();
            List<BaseOrderItem> baseOrderItemList = Lists.newArrayList();
            if (PageTplApply.good.name().equals(apply.getApplyScope())) {
                for (String goodId : StringUtils.split(apply.getGoodIds(), ",")) {
                    baseOrderItemList.addAll(goodItemMap.get(goodId));
                }
            } else if (PageTplApply.classify.name().equals(apply.getApplyScope())) {
                for (String classifyId : StringUtils.split(apply.getClassifyIds(), ",")) {
                    baseOrderItemList.addAll(classifyItemMap.get(classifyId));
                }
            } else {
                baseOrderItemList.addAll(itemList);
            }
            ManzengRuleVo ruleVo = applyActivity(activityVo, baseOrderItemList, zengpinMap, query.getOrderBizType());
            if (ruleVo != null) {
                if (BoolType.T.name().equals(ruleVo.getJianFlag()) && (discountRuleVo == null || discountRuleVo.getJianNum().compareTo(ruleVo.getJianNum()) < 0)) {
                    discountRuleVo = ruleVo;
                    discountActivityVo = activityVo;
                    discountItemList = baseOrderItemList;
                }
                if (BoolType.T.name().equals(ruleVo.getZengFlag())) {
                    manzengActivityList.addAll(toPromotionActivityVo(activityVo, PromotionActivityType.manzeng));
                }
            }
        }

        List<PromotionActivityVo> activityList = Lists.newArrayList();
        if (OrderType.normal.equals(query.getOrderType())) { //普通商品应用满减
            // 计算满减优惠
            if (discountRuleVo != null && discountActivityVo != null && !discountItemList.isEmpty()) {
                BigDecimal itemTotalAmount = BigDecimal.ZERO;
                Set<String> itemIds = Sets.newHashSet();
                for (BaseOrderItem baseOrderItem : discountItemList) {
                    itemTotalAmount = itemTotalAmount.add(baseOrderItem.getWaitPayAmont());
                    itemIds.add(baseOrderItem.getItemId());
                }
                BigDecimal totalDiscountAmount = BigDecimal.ZERO;
                BigDecimal discountRate = discountRuleVo.getJianNum().divide(itemTotalAmount, 10, BigDecimal.ROUND_HALF_UP);
                for (BaseOrderItem item : itemList) {
                    if (itemIds.remove(item.getItemId())) {
                        BigDecimal discountAmount;
                        BigDecimal remainDiscount = discountRuleVo.getJianNum().subtract(totalDiscountAmount);
                        if (itemIds.isEmpty()) {
                            discountAmount = remainDiscount;
                        } else {
                            discountAmount = discountRate.multiply(item.getWaitPayAmont()).setScale(2, BigDecimal.ROUND_HALF_UP);
                            discountAmount = discountAmount.compareTo(remainDiscount) > 0 ? remainDiscount : discountAmount;
                        }
                        totalDiscountAmount = totalDiscountAmount.add(discountAmount);
                        item.setDiscountAmount(item.getDiscountAmount().add(discountAmount));
                        item.setWaitPayAmont(item.getWaitPayAmont().subtract(discountAmount));
                    }
                }
                order.setDiscountAmount(order.getDiscountAmount().add(discountRuleVo.getJianNum()));
                order.setWaitpayAmount(order.getWaitpayAmount().subtract(discountRuleVo.getJianNum()));
                activityList.addAll(toPromotionActivityVo(discountActivityVo, PromotionActivityType.manjian));
            }
        }

        List<BaseOrderItem> newItemList = Lists.newArrayList();
        BigDecimal zengpinAmount = BigDecimal.ZERO;
        for (BaseOrderItem item : itemList) {
            newItemList.add(item);
            List<String> messageList = Lists.newArrayList();
            for (BaseOrderItem zengpin : zengpinMap.get(item.getItemId())) {
                if (zengpin.getBuyNum() > 0) {
                    newItemList.add(zengpin);
                    zengpinAmount = zengpinAmount.add(zengpin.getWaitPayAmont().multiply(BigDecimal.valueOf(zengpin.getBuyNum())));
                } else {
                    messageList.add(String.format("[赠品]%s 已赠完", zengpin.getGoodName()));
                }
            }
            item.setMessageList(messageList);
        }
        activityList.addAll(manzengActivityList);
        order.setTotalPrice(order.getTotalPrice().add(zengpinAmount));
        order.setDiscountAmount(order.getDiscountAmount().add(zengpinAmount));
        order.setActivityList(activityList);
        order.setItemList(newItemList);
        return order;
    }

    private ManzengRuleVo applyActivity(ManzengActivityVo activityVo, List<BaseOrderItem> itemList, ListMultimap<String, BaseOrderItem> zengpinMap, OrderBizType orderBizType) {
        ManzengRuleVo applyRuleVo = null;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNum = BigDecimal.ZERO;
        for (BaseOrderItem item : itemList) {
            totalAmount = totalAmount.add(item.getWaitPayAmont());
            totalNum = totalNum.add(BigDecimal.valueOf(item.getBuyNum()));
        }
        BigDecimal total;
        // 根据金额或件数满减
        if (ManzengDiscountType.money.name().equals(activityVo.getDiscountType())) {
            total = totalAmount;
        } else {
            total = totalNum;
        }
        // 应用满减规则，只应用最大满减规则
        for (ManzengRuleVo manzengRuleVo : activityVo.getManzengRuleVoList()) {
            if (total.compareTo(manzengRuleVo.getMan()) >= 0) {
                applyRuleVo = manzengRuleVo;
                // 赠品
                if (BoolType.T.name().equals(applyRuleVo.getZengFlag())) {
                    Integer zengNum = applyRuleVo.getZengNum();
                    BaseOrderItem baseOrderItem = itemList.get(itemList.size() - 1);
                    // 所有赠品都赠送设置的数量
                    for (ManzengGoodVo goodVo : applyRuleVo.getGoodList()) {
                        BaseGoodVo baseGoodVo = goodVo.getGood();
                        if (baseGoodVo == null) {
                            continue;
                        }
                        // 赠品库存不足则赠送最大可赠数量
                        Integer num = Math.min(zengNum, baseGoodVo.getStock());
                        List<? extends BaseOrderItem> zengpinItemList = manzengGoodToOrderItem(goodVo, num, orderBizType, baseOrderItem);
                        zengpinMap.putAll(baseOrderItem.getItemId(), zengpinItemList);
                        baseGoodVo.setStock(baseGoodVo.getStock() - num);
                    }
                }
                break;
            }
        }
        return applyRuleVo;
    }

    private List<? extends BaseOrderItem> manzengGoodToOrderItem(ManzengGoodVo goodVo, Integer num, OrderBizType orderBizType, BaseOrderItem baseOrderItem) {
        BaseOrderItem item;
        BaseGoodVo baseGoodVo = goodVo.getGood();
        // 构建赠品明细
        if (OrderBizType.trade.equals(orderBizType)) {
            IndentListVo indentListVo = new IndentListVo();
            indentListVo.setIndentId(((IndentListVo) baseOrderItem).getIndentId());
            indentListVo.setCargoSkuId(baseGoodVo.getCargoSkuId());
            indentListVo.setTradeGoodId(baseGoodVo.getId());
            indentListVo.setGoodSkuId(baseGoodVo.getSkuId());
            indentListVo.setStatus(IndentListStatus.waitpay.name());
            indentListVo.setDateCreated(new Date());
            indentListVo.setIsValid(BoolType.T.name());
            indentListVo.setDelFlag(BoolType.F.name());
            indentListVo.setShipCancelAfter(BoolType.F.name());
            indentListVo.setReceiveCancelAfter(BoolType.F.name());
            indentListVo.setGiftFlag(indentListVo.getGiftFlag() == null ? BoolType.F.name() : indentListVo.getGiftFlag());
            indentListVo.setShipReturnTime(0);
            indentListVo.setReturnTime(0);
            indentListVo.setIsEvalution(0);
            indentListVo.setTradeGoodAmount(baseGoodVo.getPrice().toString());
            indentListVo.setOriginalAmount(baseGoodVo.getOriginalPrice().toString());
            indentListVo.setDiscountAmount(BigDecimal.ZERO);
            indentListVo.setBuyNum(num);
            indentListVo.setFinalAmount(baseGoodVo.getPrice().toString());
            indentListVo.setTradeGoodType(baseGoodVo.getCargoSkuName());
            indentListVo.setTradeGoodName(baseGoodVo.getName());
            indentListVo.setTradeGoodImgUrl(baseGoodVo.getImage().getUrl());
            indentListVo.setGoodType(OrderListGoodType.zengpin.name());
            item = indentListVo;
        } else {
            PurchaseOrderListVo orderListVo = new PurchaseOrderListVo();
            orderListVo.setOrderId(((PurchaseOrderListVo) baseOrderItem).getOrderId());
            orderListVo.setCargoSkuId(baseGoodVo.getCargoSkuId());
            orderListVo.setStatus(IndentListStatus.waitpay.name());
            orderListVo.setCreateTime(new Date());
            orderListVo.setPurGoodsId(baseGoodVo.getId());
            orderListVo.setPurGoodsSkuId(baseGoodVo.getSkuId());
            orderListVo.setIsValid(BoolType.T.name());
            orderListVo.setDelFlag(BoolType.F.name());
            orderListVo.setShipCancelAfter(BoolType.F.name());
            orderListVo.setReceiveCancelAfter(BoolType.F.name());
            orderListVo.setGiftFlag(orderListVo.getGiftFlag() == null ? BoolType.F.name() : orderListVo.getGiftFlag());
            orderListVo.setIsSample(orderListVo.getIsSample() == null ? BoolType.F.name() : orderListVo.getIsSample());
            orderListVo.setShipReturnTime(0);
            orderListVo.setReturnTime(0);
            orderListVo.setIsEvalution(0);
            orderListVo.setSupplyPrice(baseGoodVo.getOriginalPrice().doubleValue());
            orderListVo.setPurGoodsAmount(baseGoodVo.getPrice());
            orderListVo.setOriginalAmount(baseGoodVo.getOriginalPrice());
            orderListVo.setDiscountAmount(BigDecimal.ZERO);
            orderListVo.setBuyNum(num);
            orderListVo.setFinalAmount(baseGoodVo.getPrice());
            orderListVo.setSumAmount(baseGoodVo.getPrice());
            orderListVo.setPurGoodsName(baseGoodVo.getName());
            orderListVo.setPurGoodsType(baseGoodVo.getCargoSkuName());
            orderListVo.setPurGoodsImgUrl(baseGoodVo.getImage().getUrl());
            orderListVo.setGoodType(OrderListGoodType.zengpin.name());
            item = orderListVo;
        }
        List<? extends BaseOrderItem> itemList = Lists.newArrayList(item);
        // 按赠品数量复制
        if (item.getBuyNum() > 1) {
            itemList = BaseOrderHelps.splitItems(Lists.newArrayList(item));
        }
        return itemList;
    }

    @Override
    public BaseOrder applyForPreviewOrder(PromotionOrderQuery query, TradeContext tc) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<? extends PromotionActivityVo> applyForGoodList(PromotionGoodQuery query) {
        // 查询所有满减满赠活动
        ManzengQuery manzengQuery = new ManzengQuery();
        manzengQuery.setOrderBizType(query.getOrderBizType().name());
        manzengQuery.setIsSelectAll(BoolType.T.name());
        List<ManzengActivityVo> activityVoList = manzengBizService.getActivityList(manzengQuery);
        // 转换成促销活动对象
        Map<String, List<ManzengPromotionActivityVo>> activityVoMap = Maps.newHashMap();
        for (ManzengActivityVo activityVo : activityVoList) {
            activityVoMap.put(activityVo.getId(), toPromotionActivityVo(activityVo, null));
        }
        // 根据应用范围分组
        Multimap<String, ManzengPromotionActivityVo> goodActivityMap = ArrayListMultimap.create();
        Multimap<String, ManzengPromotionActivityVo> classifyActivityMap = ArrayListMultimap.create();
        List<ManzengPromotionActivityVo> allActivityList = Lists.newArrayList();
        for (ManzengActivityVo activityVo : activityVoList) {
            ManzengActivityApply apply = activityVo.getApply();
            if (PageTplApply.good.name().equals(apply.getApplyScope())) {
                for (String goodId : StringUtils.split(apply.getGoodIds(), ",")) {
                    goodActivityMap.putAll(goodId, activityVoMap.get(activityVo.getId()));
                }
            } else if (PageTplApply.classify.name().equals(apply.getApplyScope())) {
                for (String classifyId : StringUtils.split(apply.getClassifyIds(), ",")) {
                    classifyActivityMap.putAll(classifyId, activityVoMap.get(activityVo.getId()));
                }
            } else {
                allActivityList.addAll(activityVoMap.get(activityVo.getId()));
            }
        }
        // 子分类
        if (OrderBizType.trade.equals(query.getOrderBizType())) {
            List<TradeGoodsClassifyVo> classifyVoList = tradeGoodsClassifyBizService.findAllByParentId(Lists.newArrayList(classifyActivityMap.keySet()));
            ListMultimap<String, TradeGoodsClassifyVo> classifyVoListMultimap = ArrayListMultimap.create();
            for (TradeGoodsClassifyVo classifyVo : classifyVoList) {
                classifyVoListMultimap.put(classifyVo.getParentId(), classifyVo);
            }
            Set<String> parentClaccifySet = classifyActivityMap.keySet();
            for (String classifyId : parentClaccifySet) {
                List<TradeGoodsClassifyVo> secondList = classifyVoListMultimap.get(classifyId);
                if (secondList.isEmpty()) {
                    continue;
                }
                for (TradeGoodsClassifyVo second : secondList) {
                    classifyActivityMap.putAll(second.getId(), classifyActivityMap.get(classifyId));
                    List<TradeGoodsClassifyVo> thirdList = classifyVoListMultimap.get(second.getId());
                    if (thirdList.isEmpty()) {
                        continue;
                    }
                    for (TradeGoodsClassifyVo third : secondList) {
                        classifyActivityMap.putAll(third.getId(), classifyActivityMap.get(second.getId()));
                    }
                }
            }
        } else {
            List<PurchaseClassifyVo> classifyVoList = purchaseClassifyBizService.findAllByParentId(Lists.newArrayList(classifyActivityMap.keySet()), null);
            ListMultimap<String, PurchaseClassifyVo> classifyVoListMultimap = ArrayListMultimap.create();
            for (PurchaseClassifyVo classifyVo : classifyVoList) {
                classifyVoListMultimap.put(classifyVo.getParentId(), classifyVo);
            }
            Set<String> parentClaccifySet = classifyActivityMap.keySet();
            for (String classifyId : parentClaccifySet) {
                List<PurchaseClassifyVo> secondList = classifyVoListMultimap.get(classifyId);
                if (secondList.isEmpty()) {
                    continue;
                }
                for (PurchaseClassifyVo second : secondList) {
                    classifyActivityMap.putAll(second.getId(), classifyActivityMap.get(classifyId));
                    List<PurchaseClassifyVo> thirdList = classifyVoListMultimap.get(second.getId());
                    if (thirdList.isEmpty()) {
                        continue;
                    }
                    for (PurchaseClassifyVo third : secondList) {
                        classifyActivityMap.putAll(third.getId(), classifyActivityMap.get(second.getId()));
                    }
                }
            }
        }
        // 查询应用到规则的sku列表
        Map<String, Object> data = Maps.newHashMap();
        data.put("orderBizType", query.getOrderBizType().name());
        data.put("isSelectAll", allActivityList.isEmpty() ? BoolType.F.name() : BoolType.T.name());
        data.put("classifyIds", classifyActivityMap.keySet());
        data.put("skuIds", StringUtils.join(goodActivityMap.keySet(), ","));
        String sql = FreeMarkerHelper.getValueFromTpl("sql/promotion/manzeng/getSkuList.sql", data);
        List<BaseGoodVo> goodVoList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(BaseGoodVo.class));
        // 构建促销信息列表
        List<ManzengPromotionActivityVo> promotionActivityVoList = Lists.newArrayList();
        for (BaseGoodVo goodVo : goodVoList) {
            List<ManzengPromotionActivityVo> voList = Lists.newArrayList();
            voList.addAll(goodActivityMap.get(goodVo.getSkuId()));
            if (StringUtils.isNotEmpty(goodVo.getClassifyIds())) {
                for (String classifyId : StringUtils.split(goodVo.getClassifyIds())) {
                    voList.addAll(classifyActivityMap.get(classifyId));
                }
            }
            voList.addAll(allActivityList);

            for (ManzengPromotionActivityVo activityVo : voList) {
                try {
                    ManzengPromotionActivityVo vo = activityVo.clone();
                    vo.setGoodSkuId(goodVo.getSkuId());
                    promotionActivityVoList.add(vo);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
        return promotionActivityVoList;
    }

    private List<ManzengPromotionActivityVo> toPromotionActivityVo(ManzengActivityVo activityVo, PromotionActivityType type) {
        List<ManzengPromotionActivityVo> activityVoList = Lists.newArrayList();
        ManzengPromotionActivityVo manjianActivityVo = new ManzengPromotionActivityVo(activityVo, PromotionActivityType.manjian);
        ManzengPromotionActivityVo manzengActivityVo = new ManzengPromotionActivityVo(activityVo, PromotionActivityType.manzeng);
        // 排序字段升序
        Collections.sort(activityVo.getManzengRuleVoList(), new Comparator<ManzengRuleVo>() {
            @Override
            public int compare(ManzengRuleVo o1, ManzengRuleVo o2) {
                return o1.getSort() - o2.getSort();
            }
        });
        try {
            for (ManzengRuleVo ruleVo : activityVo.getManzengRuleVoList()) {
                // 满减
                if (BoolType.T.name().equals(ruleVo.getJianFlag())) {
                    ManzengRuleVo jianRuleVo = ruleVo.clone();
                    jianRuleVo.setZengFlag(null);
                    jianRuleVo.setZengNum(null);
                    jianRuleVo.setGoodList(null);
                    manjianActivityVo.addRule(jianRuleVo);

                }
                // 满赠
                if (BoolType.T.name().equals(ruleVo.getZengFlag())) {
                    ManzengRuleVo zengruleVo = ruleVo.clone();
                    zengruleVo.setJianFlag(null);
                    zengruleVo.setJianNum(null);
                    manzengActivityVo.addRule(zengruleVo);
                }
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (!manjianActivityVo.getRuleList().isEmpty() && !PromotionActivityType.manzeng.equals(type)) {
            activityVoList.add(manjianActivityVo);
        }
        if (!manzengActivityVo.getRuleList().isEmpty() && !PromotionActivityType.manjian.equals(type)) {
            activityVoList.add(manzengActivityVo);
        }
        return activityVoList;
    }

    @Override
    public void applyForGoodDetail() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

}
