package pub.makers.shop.purchasegood.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lantu.base.common.entity.BoolType;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseGood.pojo.ChangeGoodNumQuery;
import pub.makers.shop.baseGood.pojo.TplQuery;
import pub.makers.shop.baseGood.service.PageTplBizService;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.cargo.entity.vo.*;
import pub.makers.shop.cargo.service.CargoImageBizService;
import pub.makers.shop.cargo.service.CargoMaterialLibraryBizService;
import pub.makers.shop.cargo.service.CargoSkuItemBizService;
import pub.makers.shop.cargo.service.CargoSkuTypeBizService;
import pub.makers.shop.cargo.vo.*;
import pub.makers.shop.cargo.vo.GoodPageTplVo;
import pub.makers.shop.cart.service.CartBizService;
import pub.makers.shop.cart.vo.ChangeGoodNumVo;
import pub.makers.shop.promotion.service.PromotionBizService;
import pub.makers.shop.purchaseGoods.constans.PurchaseClassifyConstant;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoods;
import pub.makers.shop.purchaseGoods.pojo.PurchaseGoodsEvaluationQuery;
import pub.makers.shop.purchaseGoods.pojo.PurchaseGoodsQuery;
import pub.makers.shop.purchaseGoods.service.PurchaseBrandBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseClassifyBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsEvaluationBizService;
import pub.makers.shop.purchaseGoods.vo.*;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.tradeGoods.entity.TradeGood;
import pub.makers.shop.tradeGoods.service.TradeGoodBizService;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kok on 2017/6/2.
 */
@Service
public class PurchaseGoodAppService {
    @Reference(version = "1.0.0")
    private PurchaseClassifyBizService purchaseClassifyBizService;
    @Reference(version = "1.0.0")
    private PurchaseBrandBizService purchaseBrandBizService;
    @Reference(version = "1.0.0")
    private PurchaseGoodsBizService purchaseGoodsBizService;
    @Reference(version = "1.0.0")
    private PromotionBizService promotionBizService;
    @Reference(version = "1.0.0")
    private PurchaseGoodsEvaluationBizService purchaseGoodsEvaluationBizService;
    @Reference(version = "1.0.0")
    private CargoSkuItemBizService cargoSkuItemBizService;
    @Reference(version = "1.0.0")
    private CargoSkuTypeBizService cargoSkuTypeBizService;
    @Reference(version = "1.0.0")
    private CargoMaterialLibraryBizService cargoMaterialLibraryBizService;
    @Reference(version = "1.0.0")
    private TradeGoodBizService tradeGoodBizService;
    @Reference(version = "1.0.0")
    private CargoImageBizService cargoImageBizService;
    @Reference(version = "1.0.0")
    private CartBizService cartBizService;
    @Reference(version = "1.0.0")
    private PageTplBizService pageTplBizService;

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

    public List<BaseGoodVo> getSearchGoodsList(PurchaseGoodsQuery purchaseGoodsQuery) {

        List<BaseGoodVo> purchaseGoodsVos = purchaseGoodsBizService.getSearchGoodsList(purchaseGoodsQuery);

        return purchaseGoodsVos;
    }

    public Integer getSearchGoodsCount(PurchaseGoodsQuery purchaseGoodsQuery) {

        return purchaseGoodsBizService.getSearchGoodsCount(purchaseGoodsQuery);
    }

    public PurchaseGoodsVo getGoodsDetail(String goodId, String storeLevelId) {
        PurchaseGoodsVo vo = purchaseGoodsBizService.getGoodsDetail(goodId, storeLevelId, ClientType.mobile);
        vo.setCargoSkuType(getCargoSkuTypeSelector(vo));
        
        
        // 处理移动端详情图片
        if (StringUtils.isNotBlank(vo.getDetailInfo())){
        	List<String> detailImages = Lists.newArrayList();
        	Document doc = Jsoup.parseBodyFragment(vo.getDetailInfo());
        	Elements elements = doc.select("img");
        	for (Element ele : elements){
        		detailImages.add(ele.attr("src"));
        	}
        	vo.setDetailInfoImages(detailImages);
        }
        
        return vo;
    }

    public List<PurchaseGoodsEvaluationVo> getEvaluationList(PurchaseGoodsEvaluationQuery query) {
        query.setType(1);
        return purchaseGoodsEvaluationBizService.getEvaluationList(query);
    }

    public PurchaseGoodsEvaluationCountVo getEvaluationCount(PurchaseGoodsEvaluationQuery query) {
        return purchaseGoodsEvaluationBizService.getEvaluationCount(query);
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

    public List<CargoMaterialLibraryVo> getMaterialByGoodsId(String goodsId, OrderBizType orderBizType){
    	return cargoMaterialLibraryBizService.getMaterialByGoodsId(goodsId, orderBizType);
    }

    public ResultData getListSearchParams(String classifyId, String storeLevelId) {
        return purchaseGoodsBizService.getListSearchParams(classifyId, storeLevelId);
    }

    public BigDecimal getGoodProfit(String goodId, String storeLevelId) {
        PurchaseGoods search = new PurchaseGoods();
        search.setId(goodId);
        PurchaseGoods goods = purchaseGoodsBizService.getPurGoods(search);
        TradeGood good = tradeGoodBizService.getGoodById(goodId);
        String cargoId;
        if (goods != null) {
            cargoId = goods.getCargoId().toString();
        } else {
            cargoId = good.getCargoId().toString();
        }
        return purchaseGoodsBizService.getGoodProfit(cargoId, storeLevelId);
    }

    public BaseGoodVo getSimpleDetail(String goodId) {
        BaseGoodVo baseGoodVo = new BaseGoodVo();
        PurchaseGoods search = new PurchaseGoods();
        search.setId(goodId);
        PurchaseGoods goods = purchaseGoodsBizService.getPurGoods(search);
        TradeGood good = tradeGoodBizService.getGoodById(goodId);
        String cargoId;
        if (goods != null) {
            baseGoodVo.setId(goods.getId());
            baseGoodVo.setName(goods.getPurGoodsName());
            cargoId = goods.getCargoId().toString();
        } else {
            baseGoodVo.setId(good.getId().toString());
            baseGoodVo.setName(good.getName());
            cargoId = good.getCargoId().toString();
        }
        ImageGroupVo imageGroupVo = cargoImageBizService.getGoodsAlbum(cargoId, ClientType.mobile);
        baseGoodVo.setImage(imageGroupVo.getImages() == null ? new ImageVo() : imageGroupVo.getImages().get(0));
        return baseGoodVo;
    }

    public ChangeGoodNumVo changeGoodNum(ChangeGoodNumQuery query) {
        query.setOrderBizType(OrderBizType.purchase);
        return cartBizService.changeGoodNum(query);
    }

    public GoodPageTplVo getPageTpl(TplQuery tplQuery) {
        return pageTplBizService.findMatchTpl(tplQuery);
    }
}
