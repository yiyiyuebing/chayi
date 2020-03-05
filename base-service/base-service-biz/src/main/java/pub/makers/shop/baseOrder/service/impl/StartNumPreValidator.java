package pub.makers.shop.baseOrder.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.BaseOrderItem;
import pub.makers.shop.baseOrder.pojo.OrderVerificationResult;
import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.baseOrder.service.OrderPreValidator;
import pub.makers.shop.promotion.enums.PromotionActivityType;
import pub.makers.shop.promotion.vo.GoodPromotionalInfoVo;
import pub.makers.shop.promotion.vo.PresellPromotionActivityVo;
import pub.makers.shop.promotion.vo.SalePromotionActivityVo;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoods;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuBizService;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsSampleVo;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.service.SubbranchAccountBizService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/8/5.
 */
@Service("startNumPreValidator")
public class StartNumPreValidator implements OrderPreValidator {
    @Reference(version = "1.0.0")
    private SubbranchAccountBizService subbranchAccountBizService;
    @Autowired
    private PurchaseGoodsSkuBizService purchaseGoodsSkuBizService;
    @Autowired
    private PurchaseGoodsService purchaseGoodsService;

    @Override
    public OrderVerificationResult validate(List<? extends BaseOrderItem> itemList, OrderBizType bizType, OrderType orderType, TradeContext tradeContext) {
        Subbranch subbranch = subbranchAccountBizService.getMainSubbranch(tradeContext.getBuyerId()); //子账号处理，如果为子账号，则取出主账号。
        ListMultimap<String, BaseOrderItem> sampleMultimap = ArrayListMultimap.create();
        ListMultimap<String, BaseOrderItem> skuMultimap = ArrayListMultimap.create();
        for (BaseOrderItem baseOrderItem : itemList) {
            if ("1".equals(baseOrderItem.getIsSample())) {
                sampleMultimap.put(baseOrderItem.getGoodSkuId(), baseOrderItem);
            } else {
                skuMultimap.put(baseOrderItem.getGoodSkuId(), baseOrderItem);
            }
        }
        List<String> sampleMsg = Lists.newArrayList();
        // 样品
        Map<String, PurchaseGoodsSampleVo> sampleVoMap = purchaseGoodsSkuBizService.getGoodSampleBySku(Lists.newArrayList(sampleMultimap.keySet()));
        for (String skuId : sampleVoMap.keySet()) {
            List<BaseOrderItem> baseOrderItemList = sampleMultimap.get(skuId);
            Integer totalNum = 0;
            for (BaseOrderItem baseOrderItem : baseOrderItemList) {
                totalNum += baseOrderItem.getBuyNum();
            }
            PurchaseGoodsSampleVo sampleVo = sampleVoMap.get(skuId);
            if (totalNum < sampleVo.getStartNum()) {
                PurchaseGoods goods = purchaseGoodsService.getById(sampleVo.getPurGoodsId());
                sampleMsg.add(goods.getPurGoodsName());
            }
        }
        // 商品
        List<String> skuStartMsg = Lists.newArrayList();
        List<String> skuLimitMsg = Lists.newArrayList();
        List<BaseGoodVo> goodVoList = purchaseGoodsSkuBizService.getGoodSkuListBySkuId(Lists.newArrayList(skuMultimap.keySet()), subbranch.getLevelId() + "", ClientType.pc);
        for (BaseGoodVo goodVo : goodVoList) {
            List<BaseOrderItem> baseOrderItemList = skuMultimap.get(goodVo.getSkuId());
            Integer totalNum = 0;
            for (BaseOrderItem baseOrderItem : baseOrderItemList) {
                totalNum += baseOrderItem.getBuyNum();
            }
            Integer minNum = 1;
            Integer maxNum = Integer.MAX_VALUE;
            Boolean isLimit = false;
            GoodPromotionalInfoVo infoVo = goodVo.getPromotionalInfo();
            if (infoVo != null && infoVo.getBestActivity() != null && Lists.newArrayList(PromotionActivityType.presell.name(), PromotionActivityType.sale.name()).contains(infoVo.getBestActivity().getActivityType()) && new Date().after(infoVo.getBestActivity().getActivityStart())) {
                if (PromotionActivityType.presell.name().equals(infoVo.getBestActivity().getActivityType())) {
                    // 预售商品
                    PresellPromotionActivityVo activityVo = (PresellPromotionActivityVo) goodVo.getPromotionalInfo().getBestActivity();
                    isLimit = BoolType.T.name().equals(activityVo.getLimitFlg());
                    maxNum = activityVo.getLimitNum();
                } else {
                    // 打折商品比较活动可售卖数量
                    SalePromotionActivityVo activityVo = (SalePromotionActivityVo) infoVo.getBestActivity();
                    isLimit = BoolType.T.name().equals(activityVo.getLimitFlg());
                    maxNum = activityVo.getLimitNum();
                }
            }
            // 不限购有起订量按起订量递减
            if (!isLimit && BoolType.T.name().equals(goodVo.getMulNumFlag())) {
                minNum = goodVo.getStartNum();
            }
            if (totalNum < minNum) {
                skuStartMsg.add(goodVo.getName() + "，起订量为" + minNum);
            }
            if (isLimit && totalNum > maxNum) {
                skuLimitMsg.add(goodVo.getName() + "，限购数量为" + maxNum);
            }
        }

        StringBuilder errMsg = new StringBuilder();
        if (!sampleMsg.isEmpty()) {
            errMsg.append(StringUtils.join(sampleMsg, "、")).append("，样品小于起订量");
        }
        if (!skuStartMsg.isEmpty()) {
            errMsg.append(StringUtils.join(skuStartMsg, "、")).append("，请重新选择数量");
        }
        if (!skuLimitMsg.isEmpty()) {
            errMsg.append(StringUtils.join(skuLimitMsg, "、")).append("，请重新选择数量");
        }

        if (errMsg.length() > 0) {
            return OrderVerificationResult.createError(errMsg.toString());
        } else {
            return OrderVerificationResult.createSuccess();
        }
    }
}
