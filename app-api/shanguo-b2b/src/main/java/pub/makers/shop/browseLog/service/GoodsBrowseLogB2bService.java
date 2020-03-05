package pub.makers.shop.browseLog.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderClientType;
import pub.makers.shop.browseLog.pojo.GoodsBrowseLogQuery;
import pub.makers.shop.browseLog.vo.GoodsBrowseLogVo;
import pub.makers.shop.purchaseGoods.constans.PurchaseClassifyConstant;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoods;
import pub.makers.shop.purchaseGoods.service.PurchaseClassifyBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuBizService;
import pub.makers.shop.purchaseGoods.vo.PurchaseClassifyVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsVo;
import pub.makers.shop.user.utils.AccountUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kok on 2017/6/29.
 */
@Service
public class GoodsBrowseLogB2bService {
    @Reference(version = "1.0.0")
    private GoodsBrowseLogBizService goodsBrowseLogBizService;
    @Reference(version = "1.0.0")
    private PurchaseGoodsSkuBizService purchaseGoodsSkuBizService;
    @Reference(version = "1.0.0")
    private PurchaseClassifyBizService purchaseClassifyBizService;
    @Reference(version = "1.0.0")
    private PurchaseGoodsBizService purchaseGoodsBizService;

    /**
     * 添加足迹
     */
    public void addBrowseLog(PurchaseGoodsVo goodsVo, String userId) {
        GoodsBrowseLogVo logVo = new GoodsBrowseLogVo();
        logVo.setGoodsId(goodsVo.getId());
        logVo.setName(goodsVo.getPurGoodsName());
        if (goodsVo.getShowImages() != null && !goodsVo.getShowImages().getImages().isEmpty()) {
            logVo.setImageUrl(goodsVo.getShowImages().getImages().get(0).getUrl());
        }
        logVo.setClassifyId(goodsVo.getGroupId());
        logVo.setUserId(userId);
        logVo.setOrderBizType(OrderBizType.purchase);
        logVo.setOrderClientType(OrderClientType.pc);
        goodsBrowseLogBizService.addBrowseLog(logVo);
    }

    /**
     * 添加足迹
     */
    public void addBrowseLog(String skuId) {
        String userId = AccountUtils.getCurrShopId();
        GoodsBrowseLogVo logVo = new GoodsBrowseLogVo();
        List<BaseGoodVo> baseGoodVoList = purchaseGoodsSkuBizService.getGoodSkuListBySkuId(Lists.newArrayList(skuId), null, ClientType.mobile);
        if (baseGoodVoList.isEmpty()) {
            return;
        }
        BaseGoodVo baseGoodVo = baseGoodVoList.get(0);
        logVo.setGoodsId(baseGoodVo.getId());
        logVo.setGoodsSkuId(baseGoodVo.getSkuId());
        logVo.setName(baseGoodVo.getName());
        logVo.setSkuName(baseGoodVo.getSkuName());
        if (baseGoodVo.getImage() != null) {
            logVo.setImageUrl(baseGoodVo.getImage().getUrl());
        }
        PurchaseGoods search = new PurchaseGoods();
        search.setId(baseGoodVo.getId());
        PurchaseGoods purchaseGoods = purchaseGoodsBizService.getPurGoods(search);
        logVo.setClassifyId(purchaseGoods.getGroupId());
        logVo.setUserId(userId);
        logVo.setOrderBizType(OrderBizType.purchase);
        logVo.setOrderClientType(OrderClientType.weixin);
        goodsBrowseLogBizService.addBrowseLog(logVo);
    }

    /**
     * 足迹列表
     */
    public List<GoodsBrowseLogVo> getBrowseLogList(GoodsBrowseLogQuery query, String classifyId, String storeLevelId) {
        if (StringUtils.isNotEmpty(classifyId)) {
            Set<String> classifyIds = purchaseClassifyBizService.findAllIdByParentId(Sets.newHashSet(classifyId), storeLevelId);
            query.setClassifyIdList(Lists.newArrayList(classifyIds));
        }
        List<GoodsBrowseLogVo> logVoList = goodsBrowseLogBizService.getBrowseLogList(query);
        if (logVoList.isEmpty()) {
            return logVoList;
        }
        if (OrderClientType.pc.equals(query.getOrderClientType())) {
            List<String> goodIdList = Lists.transform(logVoList, new Function<GoodsBrowseLogVo, String>() {
                @Override
                public String apply(GoodsBrowseLogVo logVo) {
                    return logVo.getGoodsId();
                }
            });
            List<BaseGoodVo> baseGoodVoList = purchaseGoodsSkuBizService.getAllGoodSkuListByGoodId(goodIdList, storeLevelId, ClientType.pc);
            Map<String, BaseGoodVo> baseGoodVoMap = Maps.newHashMap();
            for (BaseGoodVo baseGoodVo : baseGoodVoList) {
                baseGoodVoMap.put(baseGoodVo.getId(), baseGoodVo);
            }
            for (GoodsBrowseLogVo logVo : logVoList) {
                BaseGoodVo baseGoodVo = baseGoodVoMap.get(logVo.getGoodsId());
                logVo.setGoodsPrice(baseGoodVo.getPrice());
                logVo.setGood(baseGoodVo);
            }
        } else {
            List<String> goodSkuIdList = Lists.transform(logVoList, new Function<GoodsBrowseLogVo, String>() {
                @Override
                public String apply(GoodsBrowseLogVo logVo) {
                    return logVo.getGoodsSkuId();
                }
            });
            List<BaseGoodVo> baseGoodVoList = purchaseGoodsSkuBizService.getAllGoodSkuListBySkuId(goodSkuIdList, storeLevelId, ClientType.pc);
            Map<String, BaseGoodVo> baseGoodVoMap = Maps.newHashMap();
            for (BaseGoodVo baseGoodVo : baseGoodVoList) {
                baseGoodVoMap.put(baseGoodVo.getSkuId(), baseGoodVo);
            }
            for (GoodsBrowseLogVo logVo : logVoList) {
                BaseGoodVo baseGoodVo = baseGoodVoMap.get(logVo.getGoodsSkuId());
                logVo.setGoodsPrice(baseGoodVo.getPrice());
                logVo.setGood(baseGoodVo);
            }
        }
        return logVoList;
    }

    /**
     * 删除足迹
     */
    public void delBrowseLogById(GoodsBrowseLogQuery query) {
        goodsBrowseLogBizService.delBrowseLogById(query);
    }

    /**
     * 删除足迹
     */
    public void delBrowseLogByDate(GoodsBrowseLogQuery query) {
        goodsBrowseLogBizService.delBrowseLogByDate(query);
    }

    /**
     * 根据父id查询分类
     */
    public List<PurchaseClassifyVo> findByParentId(){
        return purchaseClassifyBizService.findByParentId(PurchaseClassifyConstant.CARGO_CLASSIFY,1, AccountUtils.getCurrStoreLevelId());
    }
}
