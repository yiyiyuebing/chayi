package pub.makers.shop.tradegood.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.common.entity.BoolType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseGood.pojo.BaseGood;
import pub.makers.shop.baseGood.pojo.ChangeGoodNumQuery;
import pub.makers.shop.baseGood.pojo.TplQuery;
import pub.makers.shop.baseGood.service.PageTplBizService;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.PromotionGoodQuery;
import pub.makers.shop.cargo.entity.vo.CargoBrandVo;
import pub.makers.shop.cargo.entity.vo.CargoSkuItemVo;
import pub.makers.shop.cargo.entity.vo.CargoSkuTypeVo;
import pub.makers.shop.cargo.service.*;
import pub.makers.shop.cargo.vo.GoodPageTplVo;
import pub.makers.shop.cart.service.CartBizService;
import pub.makers.shop.cart.vo.ChangeGoodNumVo;
import pub.makers.shop.promotion.service.PromotionBizService;
import pub.makers.shop.promotion.vo.GoodPromotionalInfoVo;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.service.SubbranchAccountBizService;
import pub.makers.shop.tradeGoods.entity.GoodsBaseLabel;
import pub.makers.shop.tradeGoods.entity.Image;
import pub.makers.shop.tradeGoods.service.*;
import pub.makers.shop.tradeGoods.vo.*;

import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/5/25.
 */
@Service
public class WeixinTradeGoodAppService {
    @Reference(version = "1.0.0")
    private CargoClassifyBizService cargoClassifyBizService;
    @Reference(version = "1.0.0")
    private CargoBrandBizService cargoBrandBizService;
    @Reference(version = "1.0.0")
    private GoodsColumnBizService goodsColumnBizService;
    @Reference(version = "1.0.0")
    private GoodsBaseLabelBizService goodsBaseLabelBizService;
    @Reference(version = "1.0.0")
    private TradeGoodBizService tradeGoodBizService;
    @Reference(version = "1.0.0")
    private CargoImageBizService cargoImageBizService;
    @Reference(version = "1.0.0")
    private GoodsThemeBizService goodsThemeBizService;
    @Reference(version = "1.0.0")
    private PromotionBizService promotionBizService;
    @Reference(version = "1.0.0")
    private CargoSkuTypeBizService cargoSkuTypeBizService;
    @Reference(version = "1.0.0")
    private CargoSkuItemBizService cargoSkuItemBizService;
    @Reference(version = "1.0.0")
    private TradeGoodSkuBizService tradeGoodSkuBizService;

    @Reference(version = "1.0.0")
    private SubbranchAccountBizService accountBizService;
    @Reference(version = "1.0.0")
    private TradeGoodsClassifyBizService tradeGoodsClassifyBizService;
    @Reference(version = "1.0.0")
    private CartBizService cartBizService;
    @Reference(version = "1.0.0")
    private PageTplBizService pageTplBizService;

    public String getStoreLevelId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return null;
        }
        Subbranch subbranch = accountBizService.getMainSubbranch(userId);
        return subbranch.getLevelId().toString();
    }

    public List<TradeGoodsClassifyVo> getClassifyListAll() {
        return tradeGoodsClassifyBizService.getClassifyListAll();
    }

    public List<TradeGoodsClassifyVo> getClassifyListByParentId(String parentId) {
        Map<String, List<TradeGoodsClassifyVo>> firstMap = tradeGoodsClassifyBizService.getClassifyListByParentId(Lists.newArrayList(parentId));
        List<TradeGoodsClassifyVo> firstList = firstMap.get(parentId);
        List<String> firstIdList = Lists.newArrayList();
        for (TradeGoodsClassifyVo tradeGoodsClassifyVo : firstList) {
            firstIdList.add(tradeGoodsClassifyVo.getId());
        }
        Map<String, List<TradeGoodsClassifyVo>> secondMap = tradeGoodsClassifyBizService.getClassifyListByParentId(firstIdList);
        for (TradeGoodsClassifyVo first : firstList) {
            first.setChildren(secondMap.get(first.getId()));
        }
        return firstList;
    }

    public List<CargoBrandVo> getCargoBrandList(String classifyId, String category) {
        return cargoBrandBizService.getCargoBrandList(classifyId, category);
    }

    public List<GoodsColumnVo> getAllGoodsColumn(Integer limit) {
        return goodsColumnBizService.getAllGoodsColumn(limit);
    }

    public GoodsColumnVo getGoodsColumnDetail(String id) {
        return goodsColumnBizService.getGoodsColumnDetail(id);
    }

    public List<BaseGoodVo> getSearchGoodListSer(GoodSearchInfo goodSearchInfo) {
        return tradeGoodBizService.getSearchGoodListSer(goodSearchInfo);
    }

    public TradeGoodVo getShanguoGoodDetailSer(String goodId, String storeLevelId) {
        TradeGoodVo vo = tradeGoodBizService.getGoodVoById(goodId, storeLevelId, ClientType.mobile);
        vo.setCargoSkuType(getCargoSkuTypeSelector(vo));
        return vo;
    }

    public List<Image> listDetailImageByGoodId(String goodId) {
        return cargoImageBizService.listDetailImageByGoodId(goodId);
    }

    public List<GoodsThemeVo> selectGoodsThemeForIndex(Integer limit) {
        return goodsThemeBizService.selectGoodsThemeForIndex(limit);
    }

    public ResultData sortTypeIndex() {
        List<TradeGoodsClassifyVo> type = tradeGoodsClassifyBizService.getClassifyListAll();
        List<CargoBrandVo> list = cargoBrandBizService.getCargoBrandList(type.get(0).getId(), null);//品牌
        List<GoodsBaseLabel> list1 = goodsBaseLabelBizService.getListAll();//用途
        List<GoodsBaseLabelVo> labelVoList = Lists.newArrayList();
        for (GoodsBaseLabel goodsBaseLabel : list1) {
            GoodsBaseLabelVo vo = new GoodsBaseLabelVo(goodsBaseLabel);
            labelVoList.add(vo);
        }
        Map<String, Object> data = Maps.newHashMap();
        data.put("sortType", type);
        data.put("barnd", list);
        data.put("use", labelVoList);
        return ResultData.createSuccess(data);
    }

    public ResultData getListSearchParams() {
        List<TradeGoodsClassifyVo> type = tradeGoodsClassifyBizService.getClassifyListAll();
        List<CargoBrandVo> list = cargoBrandBizService.getCargoBrandList(null, null);//品牌
        List<GoodsBaseLabel> list1 = goodsBaseLabelBizService.getListAll();//用途
        List<GoodsBaseLabelVo> labelVoList = Lists.newArrayList();
        for (GoodsBaseLabel goodsBaseLabel : list1) {
            GoodsBaseLabelVo vo = new GoodsBaseLabelVo(goodsBaseLabel);
            labelVoList.add(vo);
        }
        Map<String, Object> data = Maps.newHashMap();
        data.put("sortType", type);
        data.put("barnd", list);
        data.put("use", labelVoList);
        return ResultData.createSuccess(data);
    }

    public Map<String, GoodPromotionalInfoVo> applyPromotionRule(PromotionGoodQuery query) {
        return promotionBizService.applyPromotionRule(query);
    }

    public TradeGoodSkuVo getGoodSkuDetail(String skuId) {
        TradeGoodSkuVo vo = tradeGoodSkuBizService.getGoodSkuDetail(skuId);
        // 促销信息
        PromotionGoodQuery query = new PromotionGoodQuery();
        query.setOrderBizType(OrderBizType.trade);
        query.setOrderType(OrderType.normal);
        List<BaseGood> baseGoodList = Lists.newArrayList();
        BaseGood baseGood = new BaseGood();
        baseGood.setGoodSkuId(vo.getId());
        baseGood.setGoodId(vo.getId());
        baseGood.setAmount(vo.getSalePrice());
        baseGoodList.add(baseGood);
        query.setGoodList(baseGoodList);

        Map<String, GoodPromotionalInfoVo> map = promotionBizService.applyPromotionRule(query);
        vo.setPromotionalInfo(map.get(vo.getId()));
        return vo;
    }

    private CargoSkuTypeVo getCargoSkuTypeSelector(TradeGoodVo goodsVo) {
        CargoSkuTypeVo typeVo = null;
        List<CargoSkuTypeVo> skuTypeList = goodsVo.getSkuTypeList();
        if (skuTypeList.size() == 1) {
            typeVo = copyCargoSkuTypeVo(skuTypeList.get(0), goodsVo.getTradeGoodSkuVoList(), true);
        } else if (skuTypeList.size() == 2) {
            typeVo = copyCargoSkuTypeVo(skuTypeList.get(0), goodsVo.getTradeGoodSkuVoList(), false);
            for (CargoSkuItemVo itemVo : typeVo.getCargoSkuItemList()) {
                itemVo.setSkuType(copyCargoSkuTypeVo(skuTypeList.get(1), itemVo.getTradeGoodSkuList(), true));
                itemVo.setTradeGoodSkuList(null);
            }
        } else if (skuTypeList.size() == 3) {
            typeVo = copyCargoSkuTypeVo(skuTypeList.get(0), goodsVo.getTradeGoodSkuVoList(), false);
            for (CargoSkuItemVo itemVo : typeVo.getCargoSkuItemList()) {
                itemVo.setSkuType(copyCargoSkuTypeVo(skuTypeList.get(1), itemVo.getTradeGoodSkuList(), false));
                itemVo.setTradeGoodSkuList(null);
                for (CargoSkuItemVo skuItemVo : itemVo.getSkuType().getCargoSkuItemList()) {
                    skuItemVo.setSkuType(copyCargoSkuTypeVo(skuTypeList.get(2), skuItemVo.getTradeGoodSkuList(), true));
                    skuItemVo.setTradeGoodSkuList(null);
                }
            }
        }
        return typeVo;
    }

    private CargoSkuTypeVo copyCargoSkuTypeVo(CargoSkuTypeVo cargoSkuTypeVo, List<TradeGoodSkuVo> skuList, Boolean last) {
        CargoSkuTypeVo typeVo = new CargoSkuTypeVo();
        BeanUtils.copyProperties(cargoSkuTypeVo, typeVo);
        List<CargoSkuItemVo> itemVoList = Lists.newArrayList();
        for (CargoSkuItemVo cargoSkuItemVo : cargoSkuTypeVo.getCargoSkuItemList()) {
            CargoSkuItemVo itemVo = new CargoSkuItemVo();
            BeanUtils.copyProperties(cargoSkuItemVo, itemVo);
            List<TradeGoodSkuVo> tradeGoodSkuList = Lists.newArrayList();
            for (TradeGoodSkuVo skuVo : skuList) {
                if (skuVo.getSkuValue().contains(itemVo.getId())) {
                    tradeGoodSkuList.add(skuVo);
                }
            }
            itemVo.setIsValid(tradeGoodSkuList.isEmpty() ? BoolType.F.name() : BoolType.T.name());
            if (last) {
                itemVo.setSkuId(tradeGoodSkuList.isEmpty() ? null : tradeGoodSkuList.get(0).getId());
            } else {
                itemVo.setTradeGoodSkuList(tradeGoodSkuList);
            }
            itemVoList.add(itemVo);
        }
        typeVo.setCargoSkuItemList(itemVoList);
        return typeVo;
    }

    /**
     * 计算商品变化数量
     */
    public ChangeGoodNumVo changeGoodNum(ChangeGoodNumQuery query) {
        query.setOrderBizType(OrderBizType.trade);
        return cartBizService.changeGoodNum(query);
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
}
