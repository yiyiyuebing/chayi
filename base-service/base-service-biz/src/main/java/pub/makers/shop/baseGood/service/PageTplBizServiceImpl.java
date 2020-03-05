package pub.makers.shop.baseGood.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.base.Function;
import com.google.common.collect.*;
import com.lantu.base.common.entity.BoolType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.baseGood.pojo.TplQuery;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.cargo.entity.GoodPageTplModel;
import pub.makers.shop.cargo.entity.GoodPageTplModelSort;
import pub.makers.shop.cargo.service.GoodPageTplModelService;
import pub.makers.shop.cargo.service.GoodPageTplModelSortService;
import pub.makers.shop.cargo.vo.GoodPageTplModelVo;
import pub.makers.shop.cargo.vo.GoodPageTplVo;
import pub.makers.shop.purchaseGoods.service.PurchaseClassifyBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuBizService;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.tradeGoods.entity.Image;
import pub.makers.shop.tradeGoods.service.ImageService;
import pub.makers.shop.tradeGoods.service.TradeGoodSkuBizService;
import pub.makers.shop.tradeGoods.service.TradeGoodsClassifyBizService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by kok on 2017/6/8.
 */
@Service(version = "1.0.0")
public class PageTplBizServiceImpl implements PageTplBizService {
    @Autowired
    private GoodPageTplModelService goodPageTplModelService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PurchaseClassifyBizService purchaseClassifyBizService;
    @Autowired
    private TradeGoodsClassifyBizService tradeGoodsClassifyBizService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private TradeGoodSkuBizService tradeGoodSkuBizService;
    @Autowired
    private PurchaseGoodsSkuBizService purchaseGoodsSkuBizService;
    @Autowired
    private GoodPageTplModelSortService goodPageTplModelSortService;

    @Override
    public GoodPageTplVo findMatchTpl(TplQuery query) {
        GoodPageTplVo vo = null;

        // 商品
        if (StringUtils.isNotEmpty(query.getGoodSkuId())) {
            query.setGoodSkuIdList(Arrays.asList(StringUtils.split(query.getGoodSkuId(), ",")));
            vo = findMatchTplTask(query);
        }
        if (vo != null) {
            return vo;
        }
        query.setGoodSkuId(null);
        query.setGoodSkuIdList(null);

        // 类别
        if (StringUtils.isNotEmpty(query.getClassifyId())) {
            Set<String> classifyIds = Sets.newHashSet(Arrays.asList(StringUtils.split(query.getClassifyId(), ",")));
            // 查询子分类分类
            if (OrderBizType.trade.name().equals(query.getOrderBizType())) { //商城
                query.setClassifyIdList(Lists.newArrayList(tradeGoodsClassifyBizService.findAllIdByParentId(classifyIds)));
            } else if (OrderBizType.purchase.name().equals(query.getOrderBizType())) { //采购
                query.setClassifyIdList(Lists.newArrayList(purchaseClassifyBizService.findAllIdByParentId(classifyIds, query.getStoreLevelId())));
            } else {
                query.setClassifyIdList(Lists.newArrayList(classifyIds));
            }
            vo = findMatchTplTask(query);
            if (vo != null) {
                return vo;
            }
        }
        query.setClassifyId(null);
        query.setClassifyIdList(null);

        //全部
        vo = findMatchTplTask(query);
        return vo;
    }

    private GoodPageTplVo findMatchTplTask(TplQuery query) {
        GoodPageTplVo vo = null;
        Map<String, Object> data = Maps.newHashMap();
        data.put("query", query);

        // 页面模板
        String sql = FreeMarkerHelper.getValueFromTpl("sql/baseGood/findMatchTpl.sql", data);
        List<GoodPageTplVo> voList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(GoodPageTplVo.class));
        if (voList.isEmpty()) {
            return vo;
        }
        for (GoodPageTplVo tplVo : voList) {
            // 页面模板模块
            List<GoodPageTplModel> modelList = goodPageTplModelService.list(Conds.get().eq("tpl_id", tplVo.getId()).eq("is_valid", BoolType.T.name())
                    .eq("del_flag", BoolType.F.name()).order("sort desc"));
            if (!modelList.isEmpty()) {
                vo = tplVo;
                List<GoodPageTplModelVo> modelVoList = Lists.newArrayList();
                // 查询橱窗中的商品信息
                List<GoodPageTplModelSort> modelSortList = goodPageTplModelSortService.list(Conds.get().eq("tpl_id", vo.getId()).order("sort desc"));
                List<String> idList = Lists.newArrayList();
                ListMultimap<String, String> idMap = ArrayListMultimap.create();
                for (GoodPageTplModelSort modelSort : modelSortList) {
                    idList.add(String.valueOf(modelSort.getGoodSkuId()));
                    idMap.put(String.valueOf(modelSort.getModelId()), String.valueOf(modelSort.getGoodSkuId()));
                }
                BaseGoodBizService baseGoodBizService = getGoodService(OrderBizType.valueOf(query.getOrderBizType()));
                List<BaseGoodVo> baseVoList = baseGoodBizService.getGoodSkuListBySkuId(idList, query.getStoreLevelId(), query.getClientType());

                for (GoodPageTplModel model : modelList) {
                    GoodPageTplModelVo modelVo = GoodPageTplModelVo.fromGoodPageTplModel(model);
                    // 商品列表
                    List<String> skuIdList = idMap.get(model.getId().toString());

                    // TODO 这段逻辑有问题
                    baseVoList.stream().forEach(b -> System.out.println(b.getId()));
                    List<BaseGoodVo> currBaseVos = baseVoList.stream().filter(baseVo -> skuIdList.contains(baseVo.getSkuId())).collect(Collectors.toList());
                    modelVo.setGoodList(currBaseVos);
                    // 广告图片
                    List<Image> imageList = imageService.list(Conds.get().eq("group_id", model.getAdImgId()));
                    modelVo.setAdImgList(Lists.transform(imageList, new Function<Image, ImageVo>() {
                        @Override
                        public ImageVo apply(Image image) {
                            return ImageVo.fromImage(image);
                        }
                    }));
                    modelVoList.add(modelVo);
                }
                vo.setModelList(modelVoList);
                break;
            }
        }
        return vo;
    }

    private BaseGoodBizService getGoodService(OrderBizType orderBizType) {
        if (OrderBizType.trade.equals(orderBizType)) {
            return tradeGoodSkuBizService;
        } else {
            return purchaseGoodsSkuBizService;
        }
    }
}
