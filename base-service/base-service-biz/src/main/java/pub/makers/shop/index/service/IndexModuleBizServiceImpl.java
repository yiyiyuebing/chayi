package pub.makers.shop.index.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.index.entity.*;
import pub.makers.shop.index.enums.IndexModuleType;
import pub.makers.shop.index.enums.KeywordPostCode;
import pub.makers.shop.index.pojo.IndexAdImagesQuery;
import pub.makers.shop.index.vo.IndentMobileModuleClassifyVo;
import pub.makers.shop.index.vo.IndentMobileModuleVo;
import pub.makers.shop.index.vo.IndexModuleGoodVo;
import pub.makers.shop.purchaseGoods.constans.PurchaseClassifyConstant;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassify;
import pub.makers.shop.purchaseGoods.service.PurchaseClassifyBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseClassifyService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuBizService;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsVo;
import pub.makers.shop.store.constant.StoreLevelConstant;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kok on 2017/6/13.
 */
@Service(version = "1.0.0")
public class IndexModuleBizServiceImpl implements IndexModuleBizService {
    @Autowired
    private IndexAdImagesService indexAdImagesService;
    @Autowired
    private IndexModuleService indexModuleService;
    @Autowired
    private IndexFloorKeywordService indexFloorKeywordService;
    @Autowired
    private IndexModuleGoodService indexModuleGoodService;
    @Autowired
    private IndentMobileModuleService indentMobileModuleService;
    @Autowired
    private IndentMobileModuleClassifyService indentMobileModuleClassifyService;
    @Autowired
    private PurchaseClassifyService purchaseClassifyService;
    @Autowired
    private PurchaseGoodsSkuBizService purchaseGoodsSkuBizService;
    @Autowired
    private PurchaseClassifyBizService purchaseClassifyBizService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<IndexAdImages> getIndexAdImagesList(IndexAdImagesQuery query) {
        ValidateUtils.notNull(query, "参数错误");
        ValidateUtils.notNull(query.getPlatform(), "广告图平台不能为空");
        Conds conds = Conds.get();
        // 位置代码
        if (query.getPost() != null) {
            conds.eq("post_code", query.getPost().name());
        }
        // 分类
        if (StringUtils.isNotEmpty(query.getClassifyId())) {
            conds.eq("classify_id", query.getClassifyId());
        }

        List<IndexAdImages> imagesList = indexAdImagesService.list(conds.eq("is_valid", BoolType.T.name()).eq("del_flag", BoolType.F.name()).eq("platform", query.getPlatform().name())
                .page(0, query.getLimit()).order("sort desc"));
        return imagesList;
    }

    @Override
    public List<IndexModule> getIndexModuleList(IndexModuleType type, String storeLevelId) {
        ValidateUtils.notNull(type, "请选择模块类型");

        // 默认店铺等级
        if (StringUtils.isEmpty(storeLevelId)) {
            storeLevelId = StoreLevelConstant.DEFAULT_STORE_LEVEL;
        }
        Set<String> classifySet = Sets.newHashSet(PurchaseClassifyConstant.CARGO_CLASSIFY);
        classifySet = purchaseClassifyBizService.findAllIdByParentId(classifySet, storeLevelId);
        Map<String, Object> data = Maps.newHashMap();
        data.put("type", type);
        data.put("classifyIds", StringUtils.join(classifySet, ","));

        String sql = FreeMarkerHelper.getValueFromTpl("sql/index/getIndexModuleList.sql", data);
        List<IndexModule> moduleList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(IndexModule.class));
        return moduleList;
    }

    @Override
    public List<IndexFloorKeyword> getKeywordListByModule(String floorId, KeywordPostCode keywordPostCode, Integer limit) {
        ValidateUtils.notNull(floorId, "请选择楼层");
        ValidateUtils.notNull(keywordPostCode, "请选择位置");

        List<IndexFloorKeyword> keywordList = indexFloorKeywordService.list(Conds.get().eq("is_valid", BoolType.T.name()).eq("del_flag", BoolType.F.name())
                .eq("floor_id", floorId).eq("post_code", keywordPostCode.name()).page(0, limit).order("sort desc"));
        return keywordList;
    }

    @Override
    public List<IndexModuleGoodVo> getGoodListByModule(String moduleId, String storeLevelId) {
        ValidateUtils.notNull(moduleId, "请选择模块");

        List<IndexModuleGood> goodList = indexModuleGoodService.list(Conds.get().eq("is_valid", BoolType.T.name()).eq("del_flag", BoolType.F.name())
                .eq("module_id", moduleId).order("sort desc"));
        List<String> skuIdList = Lists.newArrayList(ListUtils.getIdSet(goodList, "goodSkuId"));
        // 商品信息
        List<BaseGoodVo> goodVoList = purchaseGoodsSkuBizService.getGoodSkuListBySkuId(skuIdList, storeLevelId, ClientType.pc);
        Map<String, BaseGoodVo> goodVoMap = ListUtils.toKeyMap(goodVoList, "skuId");
        List<IndexModuleGoodVo> moduleGoodVoList = Lists.newArrayList();
        for (IndexModuleGood good : goodList) {
            IndexModuleGoodVo vo = new IndexModuleGoodVo();
            BeanUtils.copyProperties(good, vo);
            BaseGoodVo baseGoodVo = goodVoMap.get(good.getGoodSkuId());
            if (baseGoodVo == null) {
                continue;
            }
            PurchaseGoodsVo purchaseGoodsVo = new PurchaseGoodsVo();
            purchaseGoodsVo.setId(baseGoodVo.getId());
            purchaseGoodsVo.setPurGoodsName(baseGoodVo.getName());
            purchaseGoodsVo.setPurGoodsSkuName(baseGoodVo.getSkuName());
            purchaseGoodsVo.setSupplyPrice(baseGoodVo.getPrice().toString());
            purchaseGoodsVo.setSmallImage(baseGoodVo.getImage().getUrl());
            purchaseGoodsVo.setStock(baseGoodVo.getStock());
            vo.setGood(purchaseGoodsVo);
            moduleGoodVoList.add(vo);
        }
        return moduleGoodVoList;
    }

    @Override
    public List<IndentMobileModuleVo> getMobileIndexModuleList(String storeLevelId) {
        // 默认店铺等级
        if (StringUtils.isEmpty(storeLevelId)) {
            storeLevelId = StoreLevelConstant.DEFAULT_STORE_LEVEL;
        }
        Set<String> classifySet = Sets.newHashSet(PurchaseClassifyConstant.CARGO_CLASSIFY);
        classifySet = purchaseClassifyBizService.findAllIdByParentId(classifySet, storeLevelId);

        List<IndentMobileModule> moduleList = indentMobileModuleService.list(Conds.get().in("classify_id", classifySet)
                .eq("is_valid", BoolType.T.name()).eq("del_flag", BoolType.F.name()).order("sort desc"));
        List<IndentMobileModuleVo> moduleVoList = Lists.newArrayList();
        for (IndentMobileModule module : moduleList) {
            IndentMobileModuleVo moduleVo = new IndentMobileModuleVo();
            BeanUtils.copyProperties(module, moduleVo);
            moduleVoList.add(moduleVo);
        }
        return moduleVoList;
    }

    @Override
    public Map<String, List<IndentMobileModuleClassifyVo>> getClassifyListByModule(List<String> moduleIdList, String storeLevelId) {
        // 默认店铺等级
        if (StringUtils.isEmpty(storeLevelId)) {
            storeLevelId = StoreLevelConstant.DEFAULT_STORE_LEVEL;
        }
        Set<String> classifySet = Sets.newHashSet(PurchaseClassifyConstant.CARGO_CLASSIFY);
        classifySet = purchaseClassifyBizService.findAllIdByParentId(classifySet, storeLevelId);

        List<IndentMobileModuleClassify> moduleClassifyList = indentMobileModuleClassifyService.list(Conds.get().in("module_id", moduleIdList)
                .in("sub_classify_id", classifySet).eq("is_valid", BoolType.T.name()).eq("del_flag", BoolType.F.name()).order("sort desc"));
        List<PurchaseClassify> purchaseClassifyList = purchaseClassifyService.list(Conds.get().in("id", ListUtils.getIdSet(moduleClassifyList, "subClassifyId")));
        Map<String, PurchaseClassify> purchaseClassifyMap = ListUtils.toKeyMap(purchaseClassifyList, "id");
        Map<String, List<IndentMobileModuleClassifyVo>> moduleClassifyVoMap = Maps.newHashMap();
        for (IndentMobileModuleClassify moduleClassify : moduleClassifyList) {
            PurchaseClassify purchaseClassify =  purchaseClassifyMap.get(moduleClassify.getSubClassifyId());
            if (purchaseClassify == null) {
                continue;
            }
            List<IndentMobileModuleClassifyVo> moduleClassifyVoList = moduleClassifyVoMap.get(moduleClassify.getModuleId()) == null ? Lists.newArrayList() : moduleClassifyVoMap.get(moduleClassify.getModuleId());
            IndentMobileModuleClassifyVo moduleClassifyVo = new IndentMobileModuleClassifyVo();
            BeanUtils.copyProperties(moduleClassify, moduleClassifyVo);
            moduleClassifyVo.setSubClassifyName(purchaseClassify.getName());
            moduleClassifyVoList.add(moduleClassifyVo);
            moduleClassifyVoMap.put(moduleClassify.getModuleId(), moduleClassifyVoList);
        }
        return moduleClassifyVoMap;
    }
}
