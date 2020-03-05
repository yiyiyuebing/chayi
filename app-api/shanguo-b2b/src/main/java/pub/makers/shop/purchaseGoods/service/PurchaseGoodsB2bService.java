package pub.makers.shop.purchaseGoods.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lantu.base.common.entity.BoolType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseGood.pojo.ChangeGoodNumQuery;
import pub.makers.shop.baseGood.pojo.TplQuery;
import pub.makers.shop.baseGood.service.PageTplBizService;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.baseOrder.pojo.PromotionGoodQuery;
import pub.makers.shop.cargo.entity.vo.CargoBrandVo;
import pub.makers.shop.cargo.entity.vo.CargoSkuItemVo;
import pub.makers.shop.cargo.entity.vo.CargoSkuTypeVo;
import pub.makers.shop.cargo.service.CargoSkuItemBizService;
import pub.makers.shop.cargo.service.CargoSkuTypeBizService;
import pub.makers.shop.cargo.vo.GoodPageTplVo;
import pub.makers.shop.cart.service.CartBizService;
import pub.makers.shop.cart.vo.ChangeGoodNumVo;
import pub.makers.shop.index.vo.BaseArticleInfo;
import pub.makers.shop.marketing.entity.Toutiao;
import pub.makers.shop.marketing.service.ToutiaoBizService;
import pub.makers.shop.promotion.enums.PromotionActivityType;
import pub.makers.shop.promotion.service.PromotionBizService;
import pub.makers.shop.promotion.vo.*;
import pub.makers.shop.purchaseGoods.constans.PurchaseClassifyConstant;
import pub.makers.shop.purchaseGoods.pojo.PurchaseGoodsEvaluationQuery;
import pub.makers.shop.purchaseGoods.pojo.PurchaseGoodsQuery;
import pub.makers.shop.purchaseGoods.vo.*;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderQueryService;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dy on 2017/6/2.
 */
@Service
public class PurchaseGoodsB2bService {

    @Reference(version = "1.0.0")
    private PurchaseGoodsBizService purchaseGoodsBizService;

    @Reference(version = "1.0.0")
    private PurchaseGoodsEvaluationBizService purchaseGoodsEvaluationBizService;

    @Reference(version = "1.0.0")
    private ToutiaoBizService toutiaoBizService;

    @Reference(version = "1.0.0")
    private PageTplBizService pageTplBizService;

    @Reference(version = "1.0.0")
    private PromotionBizService promotionBizService;

    @Reference(version = "1.0.0")
    private PurchaseOrderQueryService orderQueryService;

    @Reference(version = "1.0.0")
    private PurchaseGoodsSkuBizService purchaseGoodsSkuBizService;

    @Reference(version = "1.0.0")
    private PurchaseClassifyBizService purchaseClassifyBizService;

    @Reference(version = "1.0.0")
    private PurchaseBrandBizService purchaseBrandBizService;

    @Reference(version = "1.0.0")
    private CargoSkuItemBizService cargoSkuItemBizService;
    @Reference(version = "1.0.0")
    private CargoSkuTypeBizService cargoSkuTypeBizService;

    @Reference(version = "1.0.0")
    private CartBizService cartBizService;

    public List<PurchaseGoodsEvaluationVo> getEvaluationList(PurchaseGoodsEvaluationQuery query) {
        return purchaseGoodsEvaluationBizService.getEvaluationList(query);
    }

    public List<BaseGoodVo> getSearchGoodsList(PurchaseGoodsQuery purchaseGoodsQuery) {

        List<BaseGoodVo> purchaseGoodsVos = purchaseGoodsBizService.getSearchGoodsList(purchaseGoodsQuery);

        for (BaseGoodVo purchaseGoodsVo : purchaseGoodsVos) {
            if (purchaseGoodsVo.getPromotionalInfo() == null) continue;
            List<String> tag2Set = new ArrayList<String>();
            for (PromotionActivityVo promotionActivityVo : purchaseGoodsVo.getPromotionalInfo().getActivityList()) {
                if (PromotionActivityType.manzeng.name().equals(promotionActivityVo.getActivityType())) {
                    ManzengPromotionActivityVo manzengPromotionActivityVo = (ManzengPromotionActivityVo) promotionActivityVo;
                    manzengPromotionActivityVo.setTag2(manzengPromotionActivityVo.getTag3());
                    if (!tag2Set.contains("满赠")) {
                        tag2Set.add("满赠");
                    }

                } else if (PromotionActivityType.manjian.name().equals(promotionActivityVo.getActivityType())) {
                    if (!tag2Set.contains("满减")) {
                        tag2Set.add("满减");
                    }
                } else if (PromotionActivityType.presell.name().equals(promotionActivityVo.getActivityType())) {
                    PresellPromotionActivityVo presellPromotionActivityVo = (PresellPromotionActivityVo) promotionActivityVo;
                    tag2Set.add(presellPromotionActivityVo.getTag2());
                } else if (PromotionActivityType.sale.name().equals(promotionActivityVo.getActivityType())) {
                    SalePromotionActivityVo salePromotionActivityVo = (SalePromotionActivityVo) promotionActivityVo;
                    tag2Set.add(salePromotionActivityVo.getTag2());
                }
            }
            purchaseGoodsVo.setTag2Set(tag2Set);
        }

        return purchaseGoodsVos;
    }

    public Integer getSearchGoodsCount(PurchaseGoodsQuery purchaseGoodsQuery) {

        return purchaseGoodsBizService.getSearchGoodsCount(purchaseGoodsQuery);
    }

    public PurchaseGoodsEvaluationCountVo getEvaluationCount(PurchaseGoodsEvaluationQuery query) {
        return purchaseGoodsEvaluationBizService.getEvaluationCount(query);
    }

    public PurchaseGoodsVo getGoodsDetail(String goodId) {
        return purchaseGoodsBizService.getPcGoodsDetail(goodId);
    }

    public PurchaseGoodsVo getPcGoodsOnloadDetail(String goodId, String storeLevel) {
        return purchaseGoodsBizService.getPcGoodsOnloadDetail(goodId, storeLevel);
    }

    public BaseArticleInfo cuxiaoList() {

        BaseArticleInfo baseArticleInfo = new BaseArticleInfo();
        ResultData resultData = toutiaoBizService.getToutiaoList("cx", 1, 4);
        Map<String, Object> resultMap = (Map<String, Object>) resultData.getData();
        List<Toutiao> toutiaoList = (List<Toutiao>) resultMap.get("toutiaoList");
        List<BaseArticleInfo> baseArticleInfos = Lists.transform(toutiaoList, new Function<Toutiao, BaseArticleInfo>() {
            @Override
            public BaseArticleInfo apply(Toutiao toutiao) {
                return new BaseArticleInfo(toutiao);
            }
        });
        baseArticleInfo.setTitle(resultMap.get("tag").toString());
        baseArticleInfo.setBaseArticleInfos(baseArticleInfos);
        return baseArticleInfo;
    }

    public ResultData getListSearchParams(String classifyId, String storeLevelId) {
        return purchaseGoodsBizService.getListSearchParams(classifyId, storeLevelId);
    }

    /**
     * 查找页面模版
     *
     * @param tplQuery
     * @return
     */
    public GoodPageTplVo getPageTpl(TplQuery tplQuery) {
        return pageTplBizService.findMatchTpl(tplQuery);
    }

    public List<PromotionInfoDetailVo> getGoodsPromotionPrice(PromotionGoodQuery promotionGoodQuery) {
        Map<String, GoodPromotionalInfoVo> infoVoMap = promotionBizService.applyPromotionRule(promotionGoodQuery);
        List<PromotionInfoDetailVo> promotionInfoDetailVos = Lists.newArrayList();
        for (Map.Entry<String, GoodPromotionalInfoVo> entry : infoVoMap.entrySet()) {
            PromotionInfoDetailVo promotionInfoDetailVo = new PromotionInfoDetailVo();
            promotionInfoDetailVo.setGoodsSkuId(entry.getKey());
            promotionInfoDetailVo.setActivityType(entry.getValue().getBestActivity().getActivityType());
            promotionInfoDetailVo.setStartPrice(entry.getValue().getBestActivity().getFinalAmount());
            promotionInfoDetailVos.add(promotionInfoDetailVo);
        }
        return promotionInfoDetailVos;
    }

    /**
     * 查询促销信息
     *
     * @param promotionGoodQuery
     * @return
     */
    public PromotionInfoDetailVo getPromotionPriceInfo(PromotionGoodQuery promotionGoodQuery) {
        Map<String, GoodPromotionalInfoVo> infoVoMap = promotionBizService.applyPromotionRule(promotionGoodQuery);
        List<PromotionActivityVo> promotionActivityVos = Lists.newArrayList();
        PromotionInfoDetailVo goodPromotionalInfoVo = new PromotionInfoDetailVo();
        List<BigDecimal> firstAmount = Lists.newArrayList();
        List<BigDecimal> finalAmount = Lists.newArrayList();
        for (Map.Entry<String, GoodPromotionalInfoVo> entry : infoVoMap.entrySet()) {
            if (entry.getValue().getBestActivity().getActivityType().equals("presell")) {
                PresellPromotionActivityVo presellPromotionActivityVo = (PresellPromotionActivityVo) entry.getValue().getBestActivity();
                goodPromotionalInfoVo = PromotionInfoDetailVo.getPresellInfo(presellPromotionActivityVo);
                firstAmount.add(presellPromotionActivityVo.getFirstAmount() != null ? presellPromotionActivityVo.getFirstAmount() : BigDecimal.ZERO);
                finalAmount.add(presellPromotionActivityVo.getPresellAmount() != null ? presellPromotionActivityVo.getPresellAmount() : BigDecimal.ZERO);
            } else if (entry.getValue().getBestActivity().getActivityType().equals("sale")) {
                SalePromotionActivityVo salePromotionActivityVo = (SalePromotionActivityVo) entry.getValue().getBestActivity();
                goodPromotionalInfoVo = PromotionInfoDetailVo.getSaleInfo(salePromotionActivityVo);
                finalAmount.add(salePromotionActivityVo.getFinalAmount() != null ? salePromotionActivityVo.getFinalAmount() : BigDecimal.ZERO);
            }
            promotionActivityVos.addAll(entry.getValue().getActivityList());
        }

        if (firstAmount.size() > 0) {
            goodPromotionalInfoVo.setStartFirstAmount(Collections.min(firstAmount));
            goodPromotionalInfoVo.setEndFirstAmount(Collections.max(firstAmount));
        }

        if (finalAmount.size() > 0) {
            goodPromotionalInfoVo.setEndPrice(Collections.max(finalAmount));
            goodPromotionalInfoVo.setStartPrice(Collections.min(finalAmount));
        }

        for (PromotionActivityVo promotionActivityVo : promotionActivityVos) {


            if (PromotionActivityType.manzeng.name().equals(promotionActivityVo.getActivityType())) {
                List<String> tag2DescList = Lists.newArrayList();
                ManzengPromotionActivityVo manzengPromotionActivityVo = (ManzengPromotionActivityVo) promotionActivityVo;
                manzengPromotionActivityVo.setTag2(manzengPromotionActivityVo.getTag3());
                for (ManzengRuleVo manzengRuleVo : manzengPromotionActivityVo.getRuleList()) {
                    if (StringUtils.isNotBlank(manzengRuleVo.getIntro())) {
                        tag2DescList.add(manzengRuleVo.getIntro());
                    }
                }
                if (!tag2DescList.isEmpty()) {
                    manzengPromotionActivityVo.setTag2SaleDesc(StringUtils.join(tag2DescList, ","));
                }
            }


        }

        goodPromotionalInfoVo.setActivityList(promotionActivityVos);
        return goodPromotionalInfoVo;
    }

    public List<PurchaseClassifyVo> getClassifyList(String storeLevel) {
        List<PurchaseClassifyVo> firstList = purchaseClassifyBizService.findByParentId(PurchaseClassifyConstant.CARGO_CLASSIFY, 1, storeLevel);
        for (PurchaseClassifyVo classifyVo : firstList) {
            // 一级分类id
            List<String> classifyIdList = Lists.newArrayList(classifyVo.getId());
            // 子分类
            List<PurchaseClassifyVo> secondList = purchaseClassifyBizService.findByParentId(classifyVo.getId(), 1, storeLevel);
            // 二级分类id
            classifyIdList.addAll(Lists.transform(secondList, new Function<PurchaseClassifyVo, String>() {
                @Override
                public String apply(PurchaseClassifyVo purchaseClassifyVo) {
                    return purchaseClassifyVo.getId();
                }
            }));
            for (PurchaseClassifyVo purchaseClassifyVo : secondList) {
                List<PurchaseClassifyVo> thirdList = purchaseClassifyBizService.findByParentId(purchaseClassifyVo.getId(), 1, storeLevel);
                // 三级分类id
                classifyIdList.addAll(Lists.transform(thirdList, new Function<PurchaseClassifyVo, String>() {
                    @Override
                    public String apply(PurchaseClassifyVo purchaseClassifyVo) {
                        return purchaseClassifyVo.getId();
                    }
                }));
                purchaseClassifyVo.setChildren(thirdList);
            }
            classifyVo.setChildren(secondList);
            // 品牌
            List<String> classifyIds = Lists.newArrayList(purchaseClassifyBizService.findAllIdByParentId(Sets.newHashSet(classifyVo.getId()), storeLevel));
            List<CargoBrandVo> brandVoList = purchaseBrandBizService.getCargoBrandList(classifyIds);
            classifyVo.setBrandList(brandVoList);

            // 属性列表
            List<PurchaseClassifyAttrVo> attrVoList = purchaseClassifyBizService.findAttrByClassifyId(classifyIdList);
            classifyVo.setAttrs(attrVoList);
        }
        return firstList;
    }

    public List<PurchaseClassifyVo> getFirstClassifyList(String storeLevel) {
        List<PurchaseClassifyVo> firstList = purchaseClassifyBizService.findByParentId(PurchaseClassifyConstant.CARGO_CLASSIFY, 1, storeLevel);
        return firstList;
    }

    public PurchaseGoodsVo getGoodsDetail(String goodId, String storeLevelId) {
        PurchaseGoodsVo vo = purchaseGoodsBizService.getGoodsDetail(goodId, storeLevelId, ClientType.mobile);
        vo.setCargoSkuType(getCargoSkuTypeSelector(vo));
        return vo;
    }

    private CargoSkuTypeVo getCargoSkuTypeSelector(PurchaseGoodsVo goodsVo) {
        CargoSkuTypeVo typeVo = null;
        List<CargoSkuTypeVo> skuTypeList = goodsVo.getSkuTypeList();
        if (skuTypeList.size() == 1) {
            typeVo = copyCargoSkuTypeVo(skuTypeList.get(0), goodsVo.getGoodSkuVoList(), true);
        } else if (skuTypeList.size() == 2) {
            typeVo = copyCargoSkuTypeVo(skuTypeList.get(0), goodsVo.getGoodSkuVoList(), false);
            for (CargoSkuItemVo itemVo : typeVo.getCargoSkuItemList()) {
                itemVo.setSkuType(copyCargoSkuTypeVo(skuTypeList.get(1), itemVo.getPurchaseGoodsSkuList(), true));
                itemVo.setPurchaseGoodsSkuList(null);
            }
        } else if (skuTypeList.size() == 3) {
            typeVo = copyCargoSkuTypeVo(skuTypeList.get(0), goodsVo.getGoodSkuVoList(), false);
            for (CargoSkuItemVo itemVo : typeVo.getCargoSkuItemList()) {
                itemVo.setSkuType(copyCargoSkuTypeVo(skuTypeList.get(1), itemVo.getPurchaseGoodsSkuList(), false));
                itemVo.setPurchaseGoodsSkuList(null);
                for (CargoSkuItemVo skuItemVo : itemVo.getSkuType().getCargoSkuItemList()) {
                    skuItemVo.setSkuType(copyCargoSkuTypeVo(skuTypeList.get(2), skuItemVo.getPurchaseGoodsSkuList(), true));
                    skuItemVo.setPurchaseGoodsSkuList(null);
                }
            }
        }
        return typeVo;
    }

    private CargoSkuTypeVo copyCargoSkuTypeVo(CargoSkuTypeVo cargoSkuTypeVo, List<PurchaseGoodsSkuVo> skuList, Boolean last) {
        CargoSkuTypeVo typeVo = new CargoSkuTypeVo();
        BeanUtils.copyProperties(cargoSkuTypeVo, typeVo);
        List<CargoSkuItemVo> itemVoList = Lists.newArrayList();
        for (CargoSkuItemVo cargoSkuItemVo : cargoSkuTypeVo.getCargoSkuItemList()) {
            CargoSkuItemVo itemVo = new CargoSkuItemVo();
            BeanUtils.copyProperties(cargoSkuItemVo, itemVo);
            List<PurchaseGoodsSkuVo> purchaseGoodsSkuList = Lists.newArrayList();
            for (PurchaseGoodsSkuVo skuVo : skuList) {
                if (skuVo.getSkuValue().contains(itemVo.getId())) {
                    purchaseGoodsSkuList.add(skuVo);
                }
            }
            itemVo.setIsValid(purchaseGoodsSkuList.isEmpty() ? BoolType.F.name() : BoolType.T.name());
            if (last) {
                itemVo.setSkuId(purchaseGoodsSkuList.isEmpty() ? null : purchaseGoodsSkuList.get(0).getId());
            } else {
                itemVo.setPurchaseGoodsSkuList(purchaseGoodsSkuList);
            }
            itemVoList.add(itemVo);
        }
        typeVo.setCargoSkuItemList(itemVoList);
        return typeVo;
    }

    public ChangeGoodNumVo changeGoodNum(ChangeGoodNumQuery goodNumQuery) {
        return cartBizService.changeGoodNum(goodNumQuery);
    }
}
