package pub.makers.shop.purchaseGoods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.service.SysDictService;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseGood.pojo.BaseGood;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.PromotionGoodQuery;
import pub.makers.shop.cargo.entity.*;
import pub.makers.shop.cargo.entity.vo.CargoBrandVo;
import pub.makers.shop.cargo.entity.vo.ImageGroupVo;
import pub.makers.shop.cargo.service.*;
import pub.makers.shop.cargo.vo.CargoBasePropertysVo;
import pub.makers.shop.promotion.enums.PromotionActivityType;
import pub.makers.shop.promotion.service.PromotionBizService;
import pub.makers.shop.promotion.vo.GoodPromotionalInfoVo;
import pub.makers.shop.promotion.vo.PresellPromotionActivityVo;
import pub.makers.shop.purchaseGoods.constans.PurchaseClassifyConstant;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassify;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoods;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSearch;
import pub.makers.shop.purchaseGoods.pojo.PurchaseGoodsQuery;
import pub.makers.shop.purchaseGoods.vo.PurchaseClassifyAttrVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseClassifyVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsVo;
import pub.makers.shop.store.constant.StoreLevelConstant;
import pub.makers.shop.tradeGoods.entity.TradeGood;
import pub.makers.shop.tradeGoods.entity.TradeGoodSku;
import pub.makers.shop.tradeGoods.service.TradeGoodService;
import pub.makers.shop.tradeGoods.service.TradeGoodSkuBizService;
import pub.makers.shop.tradeGoods.service.TradeGoodSkuService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kok on 2017/6/1.
 */
@Service(version = "1.0.0")
public class PurchaseGoodsBizServiceImpl implements PurchaseGoodsBizService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CargoService cargoService;
    @Autowired
    private PurchaseGoodsService purchaseGoodsService;
    @Autowired
    private PurchaseClassifyService purchaseClassifyService;
    @Autowired
    private CargoBrandService cargoBrandService;
    @Autowired
    private CargoBasePropertysService cargoBasePropertysService;
    @Autowired
    private PurchaseGoodsSkuBizService purchaseGoodsSkuBizService;
    @Autowired
    private CargoSkuSupplyPriceService cargoSkuSupplyPriceService;
    @Autowired
    private CargoSkuTypeBizService cargoSkuTypeBizService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private PurchaseClassifyBizService purchaseClassifyBizService;
    @Autowired
    private PurchaseBrandBizService purchaseBrandBizService;
    @Autowired
    private CargoImageBizService cargoImageBizService;
    @Autowired
    private TradeGoodSkuService tradeGoodSkuService;
    @Autowired
    private TradeGoodSkuBizService tradeGoodSkuBizService;
    @Autowired
    private PromotionBizService promotionBizService;
    @Autowired
    private CargoBasePropertysBizService cargoBasePropertysBizService;
    @Autowired
    private TradeGoodService tradeGoodService;
    @Autowired
    private CargoSkuService cargoSkuService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public PurchaseGoods getPurGoods(PurchaseGoods goods) {
        return purchaseGoodsService.getById(goods.getId());
    }

    @Override
    public Integer getSearchGoodsCount(PurchaseGoodsQuery purchaseGoodsQuery) {
        return  getSearchGoodsCountFromMongo(purchaseGoodsQuery);
//        Map<String, Object> data = Maps.newHashMap();
//        if (StringUtils.isEmpty(purchaseGoodsQuery.getClassifyIds())) {
//            purchaseGoodsQuery.setClassifyIds(purchaseGoodsQuery.getClassifyId());
//        }
//        // 默认店铺等级
//        if (StringUtils.isEmpty(purchaseGoodsQuery.getStoreLevelId())) {
//            purchaseGoodsQuery.setStoreLevelId(StoreLevelConstant.DEFAULT_STORE_LEVEL);
//        }
//        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getClassifyIds())) {
//            Set<String> classifySet = Sets.newHashSet(Arrays.asList(purchaseGoodsQuery.getClassifyIds().split(",")));
//            classifySet = purchaseClassifyBizService.findAllIdByParentId(classifySet, purchaseGoodsQuery.getStoreLevelId());
//            purchaseGoodsQuery.setClassifyList(Lists.newArrayList(classifySet));
//        }
//        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getKeyword())) { // 关键词分类
//            List<PurchaseClassify> classifyList = purchaseClassifyService.list(Conds.get().like("name", purchaseGoodsQuery.getKeyword()));
//            Set<String> classifySet = ListUtils.getIdSet(classifyList, "id");
//            classifySet = purchaseClassifyBizService.findAllIdByParentId(classifySet, purchaseGoodsQuery.getStoreLevelId());
//            purchaseGoodsQuery.setKeywordClassifyList(Lists.newArrayList(classifySet));
//        }
//        data.put("query", purchaseGoodsQuery);
//
//        String sql = FreeMarkerHelper.getValueFromTpl("sql/purchaseGood/getSearchGoodCount.sql", data);
//        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
//        return count;
    }

    @Override
    public Integer getSearchGoodsCountFromMongo(PurchaseGoodsQuery purchaseGoodsQuery) {
        if (StringUtils.isEmpty(purchaseGoodsQuery.getClassifyIds())) {
            purchaseGoodsQuery.setClassifyIds(purchaseGoodsQuery.getClassifyId());
        }
        // 默认店铺等级
        if (StringUtils.isEmpty(purchaseGoodsQuery.getStoreLevelId())) {
            purchaseGoodsQuery.setStoreLevelId(StoreLevelConstant.DEFAULT_STORE_LEVEL);
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getClassifyIds())) {
            Set<String> classifySet = Sets.newHashSet(Arrays.asList(purchaseGoodsQuery.getClassifyIds().split(",")));
            classifySet = purchaseClassifyBizService.findAllIdByParentId(classifySet, purchaseGoodsQuery.getStoreLevelId());
            purchaseGoodsQuery.setClassifyList(Lists.newArrayList(classifySet));
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getKeyword())) { // 关键词分类
            List<PurchaseClassify> classifyList = purchaseClassifyService.list(Conds.get().like("name", purchaseGoodsQuery.getKeyword()));
            Set<String> classifySet = ListUtils.getIdSet(classifyList, "id");
            classifySet = purchaseClassifyBizService.findAllIdByParentId(classifySet, purchaseGoodsQuery.getStoreLevelId());
            purchaseGoodsQuery.setKeywordClassifyList(Lists.newArrayList(classifySet));
        }

        // 查询
        Query query = new Query();
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getKeyword())) {
            Criteria criteria = new Criteria();
            List<Criteria> criterias = Lists.newArrayList();
            criterias.add(Criteria.where("purGoodsName").regex(".*?" + purchaseGoodsQuery.getKeyword() + ".*"));
            criterias.add(Criteria.where("purSubtitle").regex(".*?" + purchaseGoodsQuery.getKeyword() + ".*"));
            criterias.add(Criteria.where("brandName").regex(".*?" + purchaseGoodsQuery.getKeyword() + ".*"));
            criterias.add(Criteria.where("labelName").regex(".*?" + purchaseGoodsQuery.getKeyword() + ".*"));
            for (String classify : purchaseGoodsQuery.getKeywordClassifyList()) {
                criterias.add(Criteria.where("classifyIds").regex(".*?" + classify + ".*"));
            }
            criteria.orOperator(criterias.toArray(new Criteria[criterias.size()]));
            query.addCriteria(criteria);
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getPurGoodsName())) {
            query.addCriteria(Criteria.where("purGoodsName").regex(".*?" + purchaseGoodsQuery.getPurGoodsName() + ".*"));
        }
        if (purchaseGoodsQuery.getClassifyList() != null && !purchaseGoodsQuery.getClassifyList().isEmpty()) {
            Criteria criteria = new Criteria();
            List<Criteria> criterias = Lists.newArrayList();
            for (String classify : purchaseGoodsQuery.getClassifyList()) {
                criterias.add(Criteria.where("classifyIds").regex(".*?" + classify + ".*"));
            }
            criteria.orOperator(criterias.toArray(new Criteria[criterias.size()]));
            query.addCriteria(criteria);
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getBrandIds())) {
            query.addCriteria(Criteria.where("brandId").in(StringUtils.split(purchaseGoodsQuery.getBrandIds())));
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getClassifyAttrIds())) {
            query.addCriteria(Criteria.where("classifyAttrIds").in(StringUtils.split(purchaseGoodsQuery.getClassifyAttrIds())));
        }
        Map<String, String> storeLevelMap = Maps.newHashMap();
        storeLevelMap.put("267371146347261952", "zyDeliveryPrice");
        storeLevelMap.put("267371179276472320", "jmsDeliveryPrice");
        storeLevelMap.put("267371213036695552", "lmsDeliveryPrice");
        storeLevelMap.put("267371242152202240", "ygDeliveryPrice");
        storeLevelMap.put("300711581773840384", "zyDeliveryPrice");
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getMinPrice())) {
            query.addCriteria(Criteria.where(storeLevelMap.get(purchaseGoodsQuery.getStoreLevelId())).gte(purchaseGoodsQuery.getMinPrice()));
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getMaxPrice())) {
            query.addCriteria(Criteria.where(storeLevelMap.get(purchaseGoodsQuery.getStoreLevelId())).lte(purchaseGoodsQuery.getMaxPrice()));
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getPriceSort())) {
            query.with(new Sort(new Sort.Order("1".equals(purchaseGoodsQuery.getPriceSort()) ? Sort.Direction.ASC : Sort.Direction.DESC, storeLevelMap.get(purchaseGoodsQuery.getStoreLevelId()))));
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getSaleNumSort())) {
            query.with(new Sort(new Sort.Order("1".equals(purchaseGoodsQuery.getSaleNumSort()) ? Sort.Direction.ASC : Sort.Direction.DESC, "saleNum")));
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getOrderIndex())) {
            query.with(new Sort(new Sort.Order("1".equals(purchaseGoodsQuery.getSaleNumSort()) ? Sort.Direction.ASC : Sort.Direction.DESC, "orderIndex")));
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getCreateTimeSort())) {
            query.with(new Sort(new Sort.Order("1".equals(purchaseGoodsQuery.getSaleNumSort()) ? Sort.Direction.ASC : Sort.Direction.DESC, "goodCreateTime")));
        }
        query.addCriteria(Criteria.where("status").is("1"));
        query.addCriteria(Criteria.where("classifyValid").is(BoolType.T.name()));

        Long count = mongoTemplate.count(query, "purchase_goods_search");
        return count.intValue();
    }

    @Override
    public List<BaseGoodVo> getSearchGoodsList(PurchaseGoodsQuery purchaseGoodsQuery) {
        return getSearchGoodsListFromMongo(purchaseGoodsQuery);
//        Map<String, Object> data = Maps.newHashMap();
//        if (StringUtils.isEmpty(purchaseGoodsQuery.getClassifyIds())) {
//            purchaseGoodsQuery.setClassifyIds(purchaseGoodsQuery.getClassifyId());
//        }
//        // 默认店铺等级
//        if (StringUtils.isEmpty(purchaseGoodsQuery.getStoreLevelId())) {
//            purchaseGoodsQuery.setStoreLevelId(StoreLevelConstant.DEFAULT_STORE_LEVEL);
//        }
//        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getClassifyIds())) {
//            Set<String> classifySet = Sets.newHashSet(Arrays.asList(purchaseGoodsQuery.getClassifyIds().split(",")));
//            classifySet = purchaseClassifyBizService.findAllIdByParentId(classifySet, purchaseGoodsQuery.getStoreLevelId());
//            purchaseGoodsQuery.setClassifyList(Lists.newArrayList(classifySet));
//        }
//        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getKeyword())) { // 关键词分类
//            List<PurchaseClassify> classifyList = purchaseClassifyService.list(Conds.get().like("name", purchaseGoodsQuery.getKeyword()));
//            Set<String> classifySet = ListUtils.getIdSet(classifyList, "id");
//            classifySet = purchaseClassifyBizService.findAllIdByParentId(classifySet, purchaseGoodsQuery.getStoreLevelId());
//            purchaseGoodsQuery.setKeywordClassifyList(Lists.newArrayList(classifySet));
//        }
//        data.put("query", purchaseGoodsQuery);
//
//        String sql = FreeMarkerHelper.getValueFromTpl("sql/purchaseGood/getSearchGoodList.sql", data);
//        List<PurchaseGoodsSearch> searchList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(PurchaseGoodsSearch.class));
//        if (searchList.isEmpty()) {
//            return Lists.newArrayList();
//        }
//        Map<String, PurchaseGoodsSearch> searchMap = Maps.newLinkedHashMap();
//        for (PurchaseGoodsSearch search : searchList) {
//            searchMap.put(search.getPurGoodsSkuId(), search);
//        }
//        List<BaseGoodVo> goodVoList = purchaseGoodsSkuBizService.getGoodSkuListBySkuId(Lists.newArrayList(searchMap.keySet()), purchaseGoodsQuery.getStoreLevelId(), purchaseGoodsQuery.getClientType());
//        for (BaseGoodVo goodVo : goodVoList) {
//            goodVo.setLabel(searchMap.get(goodVo.getSkuId()).getLabelName());
//        }
//        return goodVoList;
    }

    @Override
    public List<BaseGoodVo> getSearchGoodsListFromMongo(PurchaseGoodsQuery purchaseGoodsQuery) {
        if (StringUtils.isEmpty(purchaseGoodsQuery.getClassifyIds())) {
            purchaseGoodsQuery.setClassifyIds(purchaseGoodsQuery.getClassifyId());
        }
        // 默认店铺等级
        if (StringUtils.isEmpty(purchaseGoodsQuery.getStoreLevelId())) {
            purchaseGoodsQuery.setStoreLevelId(StoreLevelConstant.DEFAULT_STORE_LEVEL);
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getClassifyIds())) {
            Set<String> classifySet = Sets.newHashSet(Arrays.asList(purchaseGoodsQuery.getClassifyIds().split(",")));
            classifySet = purchaseClassifyBizService.findAllIdByParentId(classifySet, purchaseGoodsQuery.getStoreLevelId());
            purchaseGoodsQuery.setClassifyList(Lists.newArrayList(classifySet));
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getKeyword())) { // 关键词分类
            List<PurchaseClassify> classifyList = purchaseClassifyService.list(Conds.get().like("name", purchaseGoodsQuery.getKeyword()));
            Set<String> classifySet = ListUtils.getIdSet(classifyList, "id");
            classifySet = purchaseClassifyBizService.findAllIdByParentId(classifySet, purchaseGoodsQuery.getStoreLevelId());
            purchaseGoodsQuery.setKeywordClassifyList(Lists.newArrayList(classifySet));
        }

        // 查询
        Query query = new Query();
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getKeyword())) {
            Criteria criteria = new Criteria();
            List<Criteria> criterias = Lists.newArrayList();
            criterias.add(Criteria.where("purGoodsName").regex(".*?" + purchaseGoodsQuery.getKeyword() + ".*"));
            criterias.add(Criteria.where("purSubtitle").regex(".*?" + purchaseGoodsQuery.getKeyword() + ".*"));
            criterias.add(Criteria.where("brandName").regex(".*?" + purchaseGoodsQuery.getKeyword() + ".*"));
            criterias.add(Criteria.where("labelName").regex(".*?" + purchaseGoodsQuery.getKeyword() + ".*"));
            for (String classify : purchaseGoodsQuery.getKeywordClassifyList()) {
                criterias.add(Criteria.where("classifyIds").regex(".*?" + classify + ".*"));
            }
            criteria.orOperator(criterias.toArray(new Criteria[criterias.size()]));
            query.addCriteria(criteria);
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getPurGoodsName())) {
            query.addCriteria(Criteria.where("purGoodsName").regex(".*?" + purchaseGoodsQuery.getPurGoodsName() + ".*"));
        }
        if (purchaseGoodsQuery.getClassifyList() != null && !purchaseGoodsQuery.getClassifyList().isEmpty()) {
            Criteria criteria = new Criteria();
            List<Criteria> criterias = Lists.newArrayList();
            for (String classify : purchaseGoodsQuery.getClassifyList()) {
                criterias.add(Criteria.where("classifyIds").regex(".*?" + classify + ".*"));
            }
            criteria.orOperator(criterias.toArray(new Criteria[criterias.size()]));
            query.addCriteria(criteria);
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getBrandIds())) {
            query.addCriteria(Criteria.where("brandId").in(StringUtils.split(purchaseGoodsQuery.getBrandIds())));
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getClassifyAttrIds())) {
            query.addCriteria(Criteria.where("classifyAttrIds").in(StringUtils.split(purchaseGoodsQuery.getClassifyAttrIds())));
        }
        Map<String, String> storeLevelMap = Maps.newHashMap();
        storeLevelMap.put("267371146347261952", "zyDeliveryPrice");
        storeLevelMap.put("267371179276472320", "jmsDeliveryPrice");
        storeLevelMap.put("267371213036695552", "lmsDeliveryPrice");
        storeLevelMap.put("267371242152202240", "ygDeliveryPrice");
        storeLevelMap.put("300711581773840384", "zyDeliveryPrice");
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getMinPrice())) {
            query.addCriteria(Criteria.where(storeLevelMap.get(purchaseGoodsQuery.getStoreLevelId())).gte(purchaseGoodsQuery.getMinPrice()));
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getMaxPrice())) {
            query.addCriteria(Criteria.where(storeLevelMap.get(purchaseGoodsQuery.getStoreLevelId())).lte(purchaseGoodsQuery.getMaxPrice()));
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getPriceSort())) {
            query.with(new Sort(new Sort.Order("1".equals(purchaseGoodsQuery.getPriceSort()) ? Sort.Direction.ASC : Sort.Direction.DESC, storeLevelMap.get(purchaseGoodsQuery.getStoreLevelId()))));
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getSaleNumSort())) {
            query.with(new Sort(new Sort.Order("1".equals(purchaseGoodsQuery.getSaleNumSort()) ? Sort.Direction.ASC : Sort.Direction.DESC, "saleNum")));
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getOrderIndex())) {
            query.with(new Sort(new Sort.Order("1".equals(purchaseGoodsQuery.getSaleNumSort()) ? Sort.Direction.ASC : Sort.Direction.DESC, "orderIndex")));
        }
        if (StringUtils.isNotEmpty(purchaseGoodsQuery.getCreateTimeSort())) {
            query.with(new Sort(new Sort.Order("1".equals(purchaseGoodsQuery.getSaleNumSort()) ? Sort.Direction.ASC : Sort.Direction.DESC, "goodCreateTime")));
        }
        query.addCriteria(Criteria.where("status").is("1"));
        query.addCriteria(Criteria.where("classifyValid").is(BoolType.T.name()));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "orderIndex")));
        query.skip((purchaseGoodsQuery.getPageNum() - 1) * purchaseGoodsQuery.getPageSize());
        query.limit(purchaseGoodsQuery.getPageSize());

        List<PurchaseGoodsSearch> searchList = mongoTemplate.find(query, PurchaseGoodsSearch.class, "purchase_goods_search");
        if (searchList.isEmpty()) {
            return Lists.newArrayList();
        }
        Map<String, PurchaseGoodsSearch> searchMap = Maps.newLinkedHashMap();
        for (PurchaseGoodsSearch search : searchList) {
            searchMap.put(search.getPurGoodsSkuId(), search);
        }
        List<BaseGoodVo> goodVoList = purchaseGoodsSkuBizService.getGoodSkuListBySkuId(Lists.newArrayList(searchMap.keySet()), purchaseGoodsQuery.getStoreLevelId(), purchaseGoodsQuery.getClientType());
        for (BaseGoodVo goodVo : goodVoList) {
            String label = searchMap.get(goodVo.getSkuId()).getLabelName();
            if (!"代销".equals(label)) {
                goodVo.setLabel(label);
            }
        }
        return goodVoList;
    }

    @Override
    public PurchaseGoodsVo getPcGoodsDetail(String goodId) {
        PurchaseGoods goods = purchaseGoodsService.getById(goodId);
        ValidateUtils.notNull(goods, "商品不存在");
//        ValidateUtils.isTrue("1".equals(goods.getStatus()), "");
        PurchaseGoodsVo vo = PurchaseGoodsVo.fromPurchaseGoods(goods);

        // 分类
        PurchaseClassify classify = purchaseClassifyService.getById(vo.getPurClassifyId());
        if (classify != null) {
            vo.setClassifyName(classify.getName());
        }

        // 品牌
        CargoBrand cargoBrand = cargoBrandService.getById(vo.getBrandId());
        if (cargoBrand != null) {
            vo.setBrandName(cargoBrand.getName());
        }

        // 商品详情
        List<CargoBasePropertys> cargoBasePropertysList = cargoBasePropertysService.list(Conds.get().eq("cargo_id", goods.getCargoId()));
        vo.setCargoBasePropertysList(cargoBasePropertysList);

        Cargo cargo = cargoService.getById(vo.getCargoId());
        if (cargo == null) {
            return vo;
        }
        // PC端详情
        vo.setPcDetailInfo(cargo.getPcDetailInfo());
        vo.setAfterSell(cargo.getAfterSell());
        if (cargo.getDetailImageGroupId() != null) {
            vo.setDetailImages(cargoImageBizService.getImageGroup(cargo.getDetailImageGroupId().toString()));
        }
        vo.setIsSancha(cargo.getIsSancha() == null ? BoolType.F.name() : cargo.getIsSancha());
        return vo;
    }

    @Override
    public PurchaseGoodsVo getGoodsDetail(String goodId, String storeLevelId, ClientType clientType) {
        PurchaseGoods goods = purchaseGoodsService.getById(goodId);
        ValidateUtils.notNull(goods, "商品不存在");
//        ValidateUtils.isTrue("1".equals(goods.getStatus()), "");
        PurchaseGoodsVo vo = PurchaseGoodsVo.fromPurchaseGoods(goods);
        // 标签
        SysDict sysDict = sysDictService.getById(goods.getLabel());
        if (sysDict != null && !"代销".equals(sysDict.getValue())) {
            vo.setLabel(sysDict.getValue());
        }
        // sku列表
        vo.setGoodSkuVoList(purchaseGoodsSkuBizService.getGoodsSkuList(goodId, storeLevelId));
        // 商品轮播图
        vo.setShowImages(cargoImageBizService.getGoodsAlbum(goods.getCargoId().toString(), clientType));
        // 规格
        vo.setSkuTypeList(cargoSkuTypeBizService.getCargoSkuTypeList(goods.getCargoId().toString()));
        Cargo cargo = cargoService.getById(goods.getCargoId());
        // 详情
        if (ClientType.pc.equals(clientType)) {
            vo.setDetailInfo(cargo.getPcDetailInfo());
        } else if (ClientType.mobile.equals(clientType)) {
            vo.setDetailInfo(cargo.getMobileDetailInfo());
        }
        if (cargo.getDetailImageGroupId() != null) {
            vo.setDetailImages(cargoImageBizService.getImageGroup(cargo.getDetailImageGroupId().toString()));
        }
        vo.setAfterSell(cargo.getAfterSell());
        // 一口价
        vo.setFixedPrice(cargo.getFixedPrice() == null ? BigDecimal.ZERO.toString() : cargo.getFixedPrice().toString());
        vo.setIsSancha(StringUtils.isEmpty(cargo.getIsSancha()) ? BoolType.F.name() : cargo.getIsSancha());
        // 样品
        if ("1".equals(vo.getIsSample())) {
            vo.setSample(purchaseGoodsSkuBizService.getGoodSample(goodId));
        }
        // 公共参数
        Map<String, List<CargoBasePropertysVo>> propertysMap = cargoBasePropertysBizService.getPropertysList(Lists.newArrayList(cargo.getId().toString()));
        vo.setBasePropertysList(propertysMap.get(cargo.getId().toString()));
        return vo;
    }

    @Override
    public ImageGroupVo getGoodsAlbum(String goodId, ClientType clientType) {
        PurchaseGoods goods = purchaseGoodsService.getById(goodId);
        ValidateUtils.notNull(goods, "商品不存在");
        return cargoImageBizService.getGoodsAlbum(goods.getCargoId().toString(), clientType);
    }

    @Override
    public PurchaseGoodsVo getPcGoodsOnloadDetail(String goodId, String storeLevelId) {
        PurchaseGoods goods = purchaseGoodsService.getById(goodId);
        ValidateUtils.notNull(goods, "商品不存在");
//        ValidateUtils.isTrue("1".equals(goods.getStatus()), "");
        PurchaseGoodsVo vo = PurchaseGoodsVo.fromPurchaseGoods(goods);
        // 一口价
        Cargo cargo = cargoService.getById(goods.getCargoId());
        List<CargoSku> cargoSkus = cargoSkuService.list(Conds.get().eq("cargo_id", goods.getCargoId()));
        // 标签
        SysDict sysDict = sysDictService.getById(goods.getLabel());
        if (sysDict != null && !"代销".equals(sysDict.getValue())) {
            vo.setLabel(sysDict.getValue());
        }

        // 供货价
        Conds conds = Conds.get().eq("cargo_id", goods.getCargoId()).order("supply_price asc");
        if (StringUtils.isNotEmpty(storeLevelId) && !"T".equals(cargo.getIsSancha())) {
            conds.eq("store_level_id", storeLevelId);
        }
        List<CargoSkuSupplyPrice> supplyPriceList = cargoSkuSupplyPriceService.list(conds);
        if (supplyPriceList.isEmpty()) {
            vo.setMinSupplyPrice("0");
            vo.setMaxSupplyPrice("0");
            vo.setStartNum(0);
        } else {
            vo.setMinSupplyPrice(supplyPriceList.get(0).getSupplyPrice().toString());
            vo.setMaxSupplyPrice(supplyPriceList.get(supplyPriceList.size() - 1).getSupplyPrice().toString());
            vo.setStartNum(supplyPriceList.get(0).getStartNum());
        }


        if (!cargoSkus.isEmpty()) {
            vo.setFixedPrice(cargoSkus.get(0) == null || cargoSkus.get(0).getFixedPrice() == null ? BigDecimal.ZERO.toString() : cargoSkus.get(0).getFixedPrice().toString());
        } else {
            vo.setFixedPrice(BigDecimal.ZERO.toString());
        }


        vo.setIsSancha(cargo.getIsSancha() == null ? BoolType.F.name() : cargo.getIsSancha());

        // 规格
        vo.setSkuTypeList(cargoSkuTypeBizService.getCargoSkuTypeList(goods.getCargoId().toString()));

        // sku列表
        vo.setGoodSkuVoList(purchaseGoodsSkuBizService.getGoodsSkuList(goodId, storeLevelId));

        // 样品
        if ("1".equals(vo.getIsSample())) {
            vo.setSample(purchaseGoodsSkuBizService.getGoodSample(goodId));
        }
        vo.setAfterSell(cargo.getAfterSell());
        // 商品轮播图
        vo.setShowImages(cargoImageBizService.getGoodsAlbum(goods.getCargoId().toString(), ClientType.pc));
        return vo;
    }

    @Override
    public ResultData getListSearchParams(String classifyId, String storeLevelId) {
        Map<String, Object> map = Maps.newHashMap();
        // 没有传分类时默认查一级分类
        if (StringUtils.isEmpty(classifyId)) {
            classifyId = PurchaseClassifyConstant.CARGO_CLASSIFY;
        }
        // 默认店铺等级
        if (StringUtils.isEmpty(storeLevelId)) {
            storeLevelId = StoreLevelConstant.DEFAULT_STORE_LEVEL;
        }

        // 分类列表
        List<PurchaseClassifyVo> classifyVoList;
        PurchaseClassify classify;
        while (true) {
            classify = purchaseClassifyService.getById(classifyId);
            classifyVoList = purchaseClassifyBizService.findByParentId(classifyId, 1, storeLevelId);
            if (!classifyVoList.isEmpty() || classify == null) {
                break;
            }
            classifyId = classify.getParentId();
        }
        if (classify == null) {
            classify = new PurchaseClassify();
            classify.setId(PurchaseClassifyConstant.CARGO_CLASSIFY);
        }
        map.put("classify", classify);
        map.put("classifyList", classifyVoList);

        // 分类和子分类id
        List<String> classifyIdList = Lists.newArrayList(purchaseClassifyBizService.findAllIdByParentId(Sets.newHashSet(classify.getId()), storeLevelId));

        // 品牌列表
        List<CargoBrandVo> brandVoList = purchaseBrandBizService.getCargoBrandList(classifyIdList);
        map.put("brandList", brandVoList);

        // 属性列表
        List<PurchaseClassifyAttrVo> attrVoList = purchaseClassifyBizService.findAttrByClassifyId(classifyIdList);
        map.put("attrList", attrVoList);
        return ResultData.createSuccess(map);
    }

    @Override
    public BigDecimal getGoodProfit(String cargoId, String storeLevelId) {
        List<CargoSku> cargoSkuList = cargoSkuService.list(Conds.get().eq("cargo_id", cargoId));
        List<String> cargoSkuIdList = Lists.newArrayList(ListUtils.getIdSet(cargoSkuList, "id"));
        // 采购供货价
        Map<String, BigDecimal> supplyPriceMap = purchaseGoodsSkuBizService.getSkuPriceByCargoSku(cargoSkuIdList, storeLevelId);
        List<TradeGoodSku> tradeGoodSkuList = tradeGoodSkuService.list(Conds.get().in("cargo_sku_id", cargoSkuIdList));
        // 商城零售价
        Map<String, BigDecimal> tradeSkuPriceMap = tradeGoodSkuBizService.getSkuPrice(Lists.newArrayList(ListUtils.getIdSet(tradeGoodSkuList, "id")));
        List<BaseGood> baseGoodList = Lists.newArrayList();
        for (TradeGoodSku sku : tradeGoodSkuList) {
            BaseGood baseGood = new BaseGood();
            baseGood.setGoodId(sku.getGoodId().toString());
            baseGood.setGoodSkuId(sku.getId().toString());
            baseGood.setAmount(tradeSkuPriceMap.get(sku.getId().toString()));
            baseGoodList.add(baseGood);
        }
        PromotionGoodQuery query = new PromotionGoodQuery();
        query.setOrderBizType(OrderBizType.trade);
        query.setOrderType(OrderType.normal);
        query.setGoodList(baseGoodList);
        // 商城促销结果
        Map<String, GoodPromotionalInfoVo> tradeInfoVoMap = promotionBizService.applyPromotionRule(query);
        Map<String, BigDecimal> cargoSalePriceMap = Maps.newHashMap();
        for (TradeGoodSku sku : tradeGoodSkuList) {
            GoodPromotionalInfoVo infoVo = tradeInfoVoMap.get(sku.getId().toString());
            BigDecimal salePrice = tradeSkuPriceMap.get(sku.getId().toString());
            if (infoVo != null) {
                if (infoVo.getBestActivity() != null && PromotionActivityType.presell.name().equals(infoVo.getBestActivity().getActivityType())) {
                    salePrice = ((PresellPromotionActivityVo) infoVo.getBestActivity()).getPresellAmount();
                } else {
                    salePrice = infoVo.getPrice();
                }
            }
            cargoSalePriceMap.put(sku.getCargoSkuId().toString(), salePrice);
        }
        BigDecimal profit = BigDecimal.ZERO;
        for (CargoSku sku : cargoSkuList) {
            BigDecimal supplyPrice = supplyPriceMap.get(sku.getId().toString());
            BigDecimal salePrice = cargoSalePriceMap.get(sku.getId().toString());
            if (supplyPrice != null && salePrice != null && salePrice.subtract(supplyPrice).compareTo(BigDecimal.ZERO) > 0
                    && (salePrice.subtract(supplyPrice).compareTo(profit) < 0 || profit.compareTo(BigDecimal.ZERO) == 0)) {
                profit = salePrice.subtract(supplyPrice);
            }
        }
        return profit.multiply(new BigDecimal(0.93));
    }

    @Override
    public TradeGood getTradeGoodId(String goodId) {
        PurchaseGoods purchaseGoods = purchaseGoodsService.getById(goodId);
        Cargo cargo = cargoService.getById(purchaseGoods.getCargoId());
        TradeGood tradeGood = tradeGoodService.get(Conds.get().eq("cargo_id", cargo.getId()));
        return tradeGood;
    }

    @Override
    public void updateClassifyValid() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                jdbcTemplate.update("update purchase_goods set classify_valid = 'T'");
                List<PurchaseClassify> classifyList = purchaseClassifyBizService.getDisableClassifyList();
                if (classifyList.isEmpty()) {
                    return;
                }
                StringBuilder sql = new StringBuilder("update purchase_goods set classify_valid = 'F' where");
                List<Criteria> criterias = Lists.newArrayList();
                for (PurchaseClassify classify : classifyList) {
                    sql.append(" group_id like '%").append(classify.getId()).append("%' or");
                    criterias.add(Criteria.where("classifyIds").regex(".*?" + classify + ".*"));
                }
                sql.delete(sql.length() - 3, sql.length());
                jdbcTemplate.update(sql.toString());

                mongoTemplate.updateMulti(new Query(), Update.update("classifyValid", BoolType.T.name()), "purchase_goods_search");
                mongoTemplate.updateMulti(new Query(new Criteria().orOperator(criterias.toArray(new Criteria[criterias.size()]))), Update.update("classifyValid", BoolType.F.name()), "purchase_goods_search");
            }
        });
    }
}
