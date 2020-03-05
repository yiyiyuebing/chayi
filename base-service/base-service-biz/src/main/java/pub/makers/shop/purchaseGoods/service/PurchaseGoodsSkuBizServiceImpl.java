package pub.makers.shop.purchaseGoods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.baseGood.pojo.BaseGood;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.PromotionGoodQuery;
import pub.makers.shop.cargo.entity.Cargo;
import pub.makers.shop.cargo.entity.CargoSku;
import pub.makers.shop.cargo.entity.CargoSkuSupplyPrice;
import pub.makers.shop.cargo.service.*;
import pub.makers.shop.cargo.vo.CargoSkuSupplyPriceVo;
import pub.makers.shop.promotion.enums.PromotionActivityType;
import pub.makers.shop.promotion.service.PromotionBizService;
import pub.makers.shop.promotion.vo.GoodPromotionalInfoVo;
import pub.makers.shop.promotion.vo.PresellPromotionActivityVo;
import pub.makers.shop.promotion.vo.SalePromotionActivityVo;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoods;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSample;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSku;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsSampleVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsSkuVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsVo;
import pub.makers.shop.stock.pojo.StockQuery;
import pub.makers.shop.stock.service.StockBizService;
import pub.makers.shop.store.constant.StoreLevelConstant;
import pub.makers.shop.store.vo.ImageVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by kok on 2017/6/1.
 */
@Service(version = "1.0.0")
public class PurchaseGoodsSkuBizServiceImpl implements PurchaseGoodsSkuBizService {
    @Autowired
    private StockBizService stockBizService;
    @Autowired
    private PurchaseGoodsSkuService purchaseGoodsSkuService;
    @Autowired
    private PurchaseGoodsService purchaseGoodsService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private GoodPutawayScheduleService goodPutawayScheduleService;
    @Autowired
    private CargoImageBizService cargoImageBizService;
    @Autowired
    private PromotionBizService promotionBizService;
    @Autowired
    private CargoSkuSupplyPriceService cargoSkuSupplyPriceService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CargoSkuService cargoSkuService;
    @Autowired
    private PurchaseGoodsSampleService purchaseGoodsSampleService;
    @Autowired
    private CargoService cargoService;
    @Autowired
    private PurchaseClassifyBizService purchaseClassifyBizService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void upGoodSku(Long skuId, Integer num, Long userId) {
        Long count = goodPutawayScheduleService.count(Conds.get().eq("sku_id", skuId).eq("is_valid", BoolType.T.name()).eq("del_flag", BoolType.F.name()));
        ValidateUtils.isTrue(count.equals(0l), "商品sku已启动定时上架，无法再执行上架");
        upGoodSkuSchedule(skuId, num, userId);
    }

    @Override
    public void upGoodSkuSchedule(final Long skuId, final Integer num, final Long userId) {
        final PurchaseGoodsSku sku = purchaseGoodsSkuService.getById(skuId);
        ValidateUtils.notNull(sku, "商品sku不存在");
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                // 更新sku库存
                stockBizService.upGoodStock(new StockQuery(skuId, OrderBizType.purchase, num, userId));
                // 更新sku上架状态
                purchaseGoodsSkuService.update(Update.byId(sku.getId()).set("status", 1));
                // 更新商品上架状态
                purchaseGoodsService.update(Update.byId(sku.getPurGoodsId()).set("status", 1));

                mongoTemplate.updateFirst(new Query(Criteria.where("purGoodsId").is(sku.getPurGoodsId())), org.springframework.data.mongodb.core.query.Update.update("status", "1"), "purchase_goods_search");
            }
        });
    }

    @Override
    public void downGoodSku(final Long skuId, final Long userId) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                PurchaseGoodsSku sku = purchaseGoodsSkuService.getById(skuId);
                ValidateUtils.notNull(sku, "商品sku不存在");
                if (sku.getOnSalesNo() != null && sku.getOnSalesNo() > 0) {
                    // 更新sku库存
                    stockBizService.downGoodStock(new StockQuery(skuId, OrderBizType.purchase, sku.getOnSalesNo(), userId));
                }
                // 更新sku下架状态
                purchaseGoodsSkuService.update(Update.byId(sku.getId()).set("status", 0));
                // 商品的sku都下架时更新商品下架状态
                Long count = purchaseGoodsSkuService.count(Conds.get().eq("pur_goods_id", sku.getPurGoodsId()).gt("on_sales_no", 0));
                if (count.equals(0l)) {
                    purchaseGoodsService.update(Update.byId(sku.getPurGoodsId()).set("status", 0));
                    mongoTemplate.updateFirst(new Query(Criteria.where("purGoodsId").is(sku.getPurGoodsId())), org.springframework.data.mongodb.core.query.Update.update("status", "0"), "purchase_goods_search");
                }
            }
        });
    }

    @Override
    public void updateUpSkuNum(Long skuId, Integer num, Long userId) {
        PurchaseGoodsSku sku = purchaseGoodsSkuService.getById(skuId);
        ValidateUtils.notNull(sku, "商品sku不存在");
        ValidateUtils.isTrue(num >= 0, "上架数量必须大于零");
        if (sku.getOnSalesNo() > num) {
            // 更新sku库存
            stockBizService.downGoodStock(new StockQuery(skuId, OrderBizType.purchase, sku.getOnSalesNo() - num, userId));
        } else if (sku.getOnSalesNo() < num) {
            // 更新sku库存
            stockBizService.upGoodStock(new StockQuery(skuId, OrderBizType.purchase, num - sku.getOnSalesNo(), userId));
        }
    }

    @Override
    public List<PurchaseGoodsSkuVo> getGoodsSkuList(String goodId, String storeLevelId) {
        List<PurchaseGoodsSku> purchaseGoodsSkuList = purchaseGoodsSkuService.list(Conds.get().eq("pur_goods_id", goodId));
        List<String> skuIdList = Lists.newArrayList(ListUtils.getIdSet(purchaseGoodsSkuList, "id"));
        List<CargoSku> cargoSkuList = cargoSkuService.list(Conds.get().in("id", ListUtils.getIdSet(purchaseGoodsSkuList, "cargoSkuId")));
        Cargo cargo = cargoService.getById(cargoSkuList.get(0).getCargoId());
        Map skuPriceMap;
        // 根据是否散茶查询供货价
        if (BoolType.T.name().equals(cargo.getIsSancha())) {
            skuPriceMap = querySanchaPrice(Sets.newHashSet(skuIdList));
        } else {
            skuPriceMap = getSkuPriceFull(skuIdList, storeLevelId);
        }
        Map<String, CargoSku> cargoSkuMap = ListUtils.toKeyMap(cargoSkuList, "id");
        // 图片
        Map<String, ImageVo> imageVoMap = cargoImageBizService.getImageByGroup(Lists.newArrayList(ListUtils.getIdSet(cargoSkuList, "coverImg")));
        List<PurchaseGoodsSkuVo> goodsSkuVoList = Lists.newArrayList();
        PromotionGoodQuery query = new PromotionGoodQuery();
        List<BaseGood> baseGoodList = Lists.newArrayList();
        for (PurchaseGoodsSku purchaseGoodsSku : purchaseGoodsSkuList) {
            PurchaseGoodsSkuVo vo = PurchaseGoodsSkuVo.fromPurchaseGoodsSku(purchaseGoodsSku);
            CargoSku cargoSku = cargoSkuMap.get(vo.getCargoSkuId());
            if (cargoSku == null) {
                continue;
            }
            // sku供货价
            CargoSkuSupplyPrice cargoSkuSupplyPrice = null;
            if (BoolType.T.name().equals(cargo.getIsSancha())) {
                Map<String, List<CargoSkuSupplyPrice>> map = skuPriceMap;
                List<CargoSkuSupplyPrice> cargoSkuSupplyPriceList = map.get(purchaseGoodsSku.getId());
                List<CargoSkuSupplyPriceVo> cargoSkuSupplyPriceVoList = Lists.newArrayList();
                if (cargoSkuSupplyPriceList != null && !cargoSkuSupplyPriceList.isEmpty()) {
                    cargoSkuSupplyPrice = cargoSkuSupplyPriceList.get(0);
                    for (CargoSkuSupplyPrice supplyPrice : cargoSkuSupplyPriceList) {
                        cargoSkuSupplyPriceVoList.add(CargoSkuSupplyPriceVo.fromCargoSkuSupplyPrice(supplyPrice));
                    }
                }
                vo.setSupplyPriceList(cargoSkuSupplyPriceVoList);
            } else {
                Map<String, CargoSkuSupplyPrice> map = skuPriceMap;
                cargoSkuSupplyPrice = map.get(purchaseGoodsSku.getId());
            }
            if (cargoSkuSupplyPrice != null) {
                vo.setSupplyPrice(cargoSkuSupplyPrice.getSupplyPrice() == null ? BigDecimal.ZERO.toString() : cargoSkuSupplyPrice.getSupplyPrice().toString());
                vo.setStartNum(cargoSkuSupplyPrice.getStartNum() == null ? 0 : cargoSkuSupplyPrice.getStartNum());
                vo.setMulNumFlag(cargoSkuSupplyPrice.getMulNumFlag() == null ? "F" : cargoSkuSupplyPrice.getMulNumFlag());
            } else {
                vo.setSupplyPrice(BigDecimal.ZERO.toString());
                vo.setStartNum(0);
                vo.setMulNumFlag(BoolType.F.name());
            }
            vo.setSkuValue(cargoSku.getSkuItemValue());
            vo.setRetailPrice(cargoSku.getRetailPrice() == null ? "0" : cargoSku.getRetailPrice().toString());
            vo.setCoverImg(imageVoMap.get(cargoSku.getCoverImg()));
            vo.setCargoSkuName(cargoSku.getSkuName());
            //TODO 批量查询
            Integer cargoSkuStock = stockBizService.queryCurrStork(cargoSku.getId().toString(), "cargo");
            vo.setCargoSkuStock(cargoSkuStock);
            vo.setStock(Math.min(vo.getOnSalesNo(), vo.getCargoSkuStock()));
            goodsSkuVoList.add(vo);

            // 构建促销查询参数
            BaseGood baseGood = new BaseGood();
            baseGood.setGoodId(vo.getPurGoodsId());
            baseGood.setGoodSkuId(purchaseGoodsSku.getId());
            baseGood.setAmount(new BigDecimal(vo.getSupplyPrice()));
            baseGoodList.add(baseGood);
        }
        // 构建促销查询参数
        query.setOrderBizType(OrderBizType.purchase);
        query.setGoodList(baseGoodList);
        query.setOrderType(OrderType.normal);
        query.setIsDetail(true);
        // 查询促销信息
        Map<String, GoodPromotionalInfoVo> infoVoMap = promotionBizService.applyPromotionRule(query);
        for (PurchaseGoodsSkuVo skuVo : goodsSkuVoList) {
            GoodPromotionalInfoVo infoVo = infoVoMap.get(skuVo.getId());
            if (infoVo != null) {
                skuVo.setPromotionalInfo(infoVo);
                if (infoVo.getBestActivity() == null) {
                    continue;
                }
                Integer stock = null;
                String isLimit = BoolType.F.name();
                if (PromotionActivityType.presell.name().equals(infoVo.getBestActivity().getActivityType())) {
                    PresellPromotionActivityVo activityVo = (PresellPromotionActivityVo) infoVo.getBestActivity();
                    stock = activityVo.getPresellNum();
                    isLimit = activityVo.getLimitFlg();
                } else if (PromotionActivityType.sale.name().equals(infoVo.getBestActivity().getActivityType())) {
                    SalePromotionActivityVo activityVo = (SalePromotionActivityVo) infoVo.getBestActivity();
                    stock = activityVo.getMaxNum();
                    isLimit = activityVo.getLimitFlg();
                }
                if (stock != null) {
                    skuVo.setOnSalesNo(stock);
                    skuVo.setCargoSkuStock(stock);
                    skuVo.setStock(stock);
                }
                if (BoolType.T.name().equals(isLimit)) {
                    skuVo.setMulNumFlag(BoolType.F.name());
                }
            }
        }
        return goodsSkuVoList;
    }

    @Override
    public PurchaseGoodsSampleVo getGoodSample(String goodId) {
        PurchaseGoodsSample sample = purchaseGoodsSampleService.get(Conds.get().eq("pur_goods_id", goodId));
        if (sample == null) {
            return null;
        }
        PurchaseGoodsSampleVo vo = new PurchaseGoodsSampleVo();
        BeanUtils.copyProperties(sample, vo);
        PurchaseGoods purchaseGoods = purchaseGoodsService.getById(goodId);
        PurchaseGoodsVo purchaseGoodsVo = new PurchaseGoodsVo();
        BeanUtils.copyProperties(purchaseGoods, purchaseGoodsVo);
        vo.setPurchaseGoodsVo(purchaseGoodsVo);
        return vo;
    }

    @Override
    public Map<String, PurchaseGoodsSampleVo> getGoodSampleBySku(List<String> skuIdList) {
        List<PurchaseGoodsSku> skuList = purchaseGoodsSkuService.list(Conds.get().in("id", skuIdList));
        List<PurchaseGoodsSample> sampleList = purchaseGoodsSampleService.list(Conds.get().in("pur_goods_id", ListUtils.getIdSet(skuList, "purGoodsId")));
        Map<String, PurchaseGoodsSample> sampleMap = ListUtils.toKeyMap(sampleList, "purGoodsId");
        Map<String, PurchaseGoodsSampleVo> sampleVoMap = Maps.newHashMap();
        for (PurchaseGoodsSku sku : skuList) {
            PurchaseGoodsSample sample = sampleMap.get(sku.getPurGoodsId());
            if (sample == null) {
                continue;
            }
            PurchaseGoodsSampleVo sampleVo = new PurchaseGoodsSampleVo();
            BeanUtils.copyProperties(sample, sampleVo);
            sampleVoMap.put(sku.getId(), sampleVo);
        }
        return sampleVoMap;
    }

    @Override
    public BigDecimal getSkuPrice(String skuId, String storeLevelId) {
        PurchaseGoodsSku sku = purchaseGoodsSkuService.getById(skuId);
        if (sku.getCargoSkuId() == null) {
            return BigDecimal.ZERO;
        }
        Conds conds = Conds.get().eq("cargo_sku_id", sku.getCargoSkuId()).order("supply_price asc");
        if (StringUtils.isNotEmpty(storeLevelId)) {
            conds.eq("store_level_id", storeLevelId);
        }
        List<CargoSkuSupplyPrice> supplyPriceList = cargoSkuSupplyPriceService.list(conds);
        if (supplyPriceList.isEmpty()) {
            return BigDecimal.ZERO;
        } else {
            return supplyPriceList.get(0).getSupplyPrice();
        }
    }

    @Override
    public Map<String, BigDecimal> getSkuPrice(List<String> skuIdList, String storeLevelId) {
        List<PurchaseGoodsSku> skuList = purchaseGoodsSkuService.list(Conds.get().in("id", skuIdList));
        Conds conds = Conds.get().in("cargo_sku_id", ListUtils.getIdSet(skuList, "cargoSkuId")).order("supply_price desc");
        if (StringUtils.isNotEmpty(storeLevelId)) {
            conds.eq("store_level_id", storeLevelId);
        }
        List<CargoSkuSupplyPrice> supplyPriceList = cargoSkuSupplyPriceService.list(conds);
        Map<String, CargoSkuSupplyPrice> supplyPriceMap = ListUtils.toKeyMap(supplyPriceList, "cargoSkuId");
        Map<String, BigDecimal> skuPriceMap = Maps.newHashMap();
        for (PurchaseGoodsSku goodsSku : skuList) {
            if (goodsSku.getCargoSkuId() != null && supplyPriceMap.get(goodsSku.getCargoSkuId().toString()) != null) {
                skuPriceMap.put(goodsSku.getId(), supplyPriceMap.get(goodsSku.getCargoSkuId().toString()).getSupplyPrice());
            }
        }
        return skuPriceMap;
    }

    @Override
    public Map<String, BigDecimal> getSkuPriceByCargoSku(List<String> cargoSkuIdList, String storeLevelId) {
        Conds conds = Conds.get().in("cargo_sku_id", cargoSkuIdList).order("supply_price desc");
        if (StringUtils.isNotEmpty(storeLevelId)) {
            conds.eq("store_level_id", storeLevelId);
        }
        List<CargoSkuSupplyPrice> supplyPriceList = cargoSkuSupplyPriceService.list(conds);
        Map<String, BigDecimal> skuPriceMap = Maps.newHashMap();
        for (CargoSkuSupplyPrice supplyPrice : supplyPriceList) {
            skuPriceMap.put(supplyPrice.getCargoSkuId().toString(), supplyPrice.getSupplyPrice());
        }
        return skuPriceMap;
    }

    @Override
    public Map<String, BigDecimal> getSkuPriceByGood(List<String> goodIdList, String storeLevelId) {
        // 查询商品sku
        Map<String, Object> dataModel = Maps.newHashMap();
        dataModel.put("goodIds", StringUtils.join(goodIdList, ","));
        String sql = FreeMarkerHelper.getValueFromTpl("sql/purchaseGood/getSkuByGood.sql", dataModel);
        List<PurchaseGoodsSku> skuList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(PurchaseGoodsSku.class));
        Map<String, PurchaseGoodsSku> skuMap = ListUtils.toKeyMap(skuList, "id");
        // 查询sku价格
        Map<String, BigDecimal> skuPriceMap = getSkuPrice(Lists.newArrayList(skuMap.keySet()), storeLevelId);
        Map<String, BigDecimal> goodPriceMap = Maps.newHashMap();
        for (String skuId : skuPriceMap.keySet()) {
            goodPriceMap.put(skuMap.get(skuId).getPurGoodsId(), skuPriceMap.get(skuId));
        }
        return goodPriceMap;
    }

    @Override
    public Map<String, CargoSkuSupplyPrice> getSkuPriceFull(List<String> skuIdList, String storeLevelId) {
        List<PurchaseGoodsSku> skuList = purchaseGoodsSkuService.list(Conds.get().in("id", skuIdList));
        Conds conds = Conds.get().in("cargo_sku_id", ListUtils.getIdSet(skuList, "cargoSkuId")).order("supply_price desc");
        if (StringUtils.isNotEmpty(storeLevelId)) {
            conds.eq("store_level_id", storeLevelId);
        }
        List<CargoSkuSupplyPrice> supplyPriceList = cargoSkuSupplyPriceService.list(conds);
        Map<String, CargoSkuSupplyPrice> supplyPriceMap = ListUtils.toKeyMap(supplyPriceList, "cargoSkuId");
        Map<String, CargoSkuSupplyPrice> skuPriceMap = Maps.newHashMap();
        for (PurchaseGoodsSku goodsSku : skuList) {
            if (goodsSku.getCargoSkuId() != null && supplyPriceMap.get(goodsSku.getCargoSkuId().toString()) != null) {
                skuPriceMap.put(goodsSku.getId(), supplyPriceMap.get(goodsSku.getCargoSkuId().toString()));
            }
        }
        return skuPriceMap;
    }

    @Override
    public List<BaseGoodVo> getGoodSkuListBySkuId(List<String> skuIdList, String storeLevelId, ClientType type) {
        return getBaseGoodVoListBySkuId(skuIdList, storeLevelId, type, "1");
    }

    @Override
    public List<BaseGoodVo> getAllGoodSkuListBySkuId(List<String> skuIdList, String storeLevelId, ClientType type) {
        return getBaseGoodVoListBySkuId(skuIdList, storeLevelId, type, null);
    }

    @Override
    public List<BaseGoodVo> getGoodSkuListByGoodId(List<String> goodIdList, String storeLevelId, ClientType type) {
        return getBaseGoodVoListByGoodId(goodIdList, storeLevelId, type, "1");
    }

    @Override
    public List<BaseGoodVo> getAllGoodSkuListByGoodId(List<String> goodIdList, String storeLevelId, ClientType type) {
        return getBaseGoodVoListByGoodId(goodIdList, storeLevelId, type, null);
    }

    private List<BaseGoodVo> getBaseGoodVoListBySkuId(List<String> skuIdList, String storeLevelId, ClientType type, String status) {
        if (StringUtils.isEmpty(storeLevelId)) {
            storeLevelId = StoreLevelConstant.DEFAULT_STORE_LEVEL;
        }

        List<BaseGoodVo> goodVoList = Lists.newArrayList();
        PromotionGoodQuery query = new PromotionGoodQuery();
        List<BaseGood> baseGoodList = Lists.newArrayList();
        // sku
        Conds skuConds = Conds.get().in("id", skuIdList);
        if (StringUtils.isNotEmpty(status)) {
            skuConds.eq("status", status);
        }
        List<PurchaseGoodsSku> purchaseGoodsSkuList = purchaseGoodsSkuService.list(skuConds);
        if (purchaseGoodsSkuList.isEmpty()) {
            return Lists.newArrayList();
        }
        Map<String, PurchaseGoodsSku> skuMap = ListUtils.toKeyMap(purchaseGoodsSkuList, "id");
        // 商品
        Map<String, Object> goodQueryMap = Maps.newHashMap();
        goodQueryMap.put("goodIds", StringUtils.join(ListUtils.getIdSet(purchaseGoodsSkuList, "purGoodsId"), ","));
        goodQueryMap.put("status", status);
        goodQueryMap.put("storeLevelId", storeLevelId);
        String goodSql = FreeMarkerHelper.getValueFromTpl("sql/purchaseGood/getGoodByLevel.sql", goodQueryMap);
        List<PurchaseGoods> goodList = jdbcTemplate.query(goodSql, ParameterizedBeanPropertyRowMapper.newInstance(PurchaseGoods.class));
        Map<String, PurchaseGoods> goodMap = ListUtils.toKeyMap(goodList, "id");
        // 价格
        Set<String> skuIds = ListUtils.getIdSet(purchaseGoodsSkuList, "id");
        Map<String, CargoSkuSupplyPrice> skuPriceMap = getSkuPriceFull(Lists.newArrayList(skuIds), storeLevelId);
        Map<String, List<CargoSkuSupplyPrice>> sanchaPriceMap = querySanchaPrice(skuIds);
        // 图片
        List<String> cargoIdList = Lists.newArrayList(ListUtils.getIdSet(goodList, "cargoId"));
        Map<String, ImageVo> imageVoMap = cargoImageBizService.getCargoImage(cargoIdList, type);
        // cargo
        List<Cargo> cargoList = cargoService.list(Conds.get().in("id", cargoIdList));
        Map<String, Cargo> cargoMap = ListUtils.toKeyMap(cargoList, "id");
        // cargoSku
        List<String> cargoSkuIdList = Lists.newArrayList(ListUtils.getIdSet(purchaseGoodsSkuList, "cargoSkuId"));
        List<CargoSku> cargoSkuList = cargoSkuService.list(Conds.get().in("id", cargoSkuIdList));
        Map<String, CargoSku> cargoSkuMap = ListUtils.toKeyMap(cargoSkuList, "id");
        Map<String, Integer> cargoSkuStockMap = stockBizService.queryCurrStork(cargoSkuIdList, "cargo");

        for (String skuId : skuIdList) {
            PurchaseGoodsSku purchaseGoodsSku = skuMap.get(skuId);
            if (purchaseGoodsSku == null) {
                continue;
            }
            PurchaseGoods purchaseGoods = goodMap.get(purchaseGoodsSku.getPurGoodsId());
            if (purchaseGoods == null) {
                continue;
            }
            BaseGoodVo vo = new BaseGoodVo();
            CargoSku cargoSku = cargoSkuMap.get(purchaseGoodsSku.getCargoSkuId().toString());
            vo.complete(purchaseGoods).complete(purchaseGoodsSku).setSkuName(cargoSku.getSkuName());
            vo.setStatus("1".equals(purchaseGoods.getStatus()) && "1".equals(purchaseGoodsSku.getStatus()) ? "1" : "0");
            Integer cargoSkuStock = cargoSkuStockMap.get(cargoSku.getId().toString());
            vo.setCargoSkuStock(cargoSkuStock == null ? 0 : cargoSkuStock);
            vo.setStock(Math.min(vo.getOnSalesNo(), vo.getCargoSkuStock()));
            if (purchaseGoods.getCargoId() != null) {
                vo.setImage(imageVoMap.get(purchaseGoods.getCargoId().toString()));
                vo.setIsSancha(cargoMap.get(purchaseGoods.getCargoId().toString()).getIsSancha());
            }
            // sku供货价
            CargoSkuSupplyPrice cargoSkuSupplyPrice = null;
            if (BoolType.T.name().equals(vo.getIsSancha())) {
                List<CargoSkuSupplyPrice> cargoSkuSupplyPriceList = sanchaPriceMap.get(purchaseGoodsSku.getId());
                List<CargoSkuSupplyPriceVo> cargoSkuSupplyPriceVoList = Lists.newArrayList();
                if (cargoSkuSupplyPriceList != null && !cargoSkuSupplyPriceList.isEmpty()) {
                    cargoSkuSupplyPrice = cargoSkuSupplyPriceList.get(0);
                    for (CargoSkuSupplyPrice supplyPrice : cargoSkuSupplyPriceList) {
                        cargoSkuSupplyPriceVoList.add(CargoSkuSupplyPriceVo.fromCargoSkuSupplyPrice(supplyPrice));
                    }
                }
                vo.setSupplyPriceList(cargoSkuSupplyPriceVoList);
            } else {
                cargoSkuSupplyPrice = skuPriceMap.get(purchaseGoodsSku.getId());
            }
            if (cargoSkuSupplyPrice != null) {
                vo.setOriginalPrice(cargoSkuSupplyPrice.getSupplyPrice() == null ? BigDecimal.ZERO : cargoSkuSupplyPrice.getSupplyPrice());
                vo.setStartNum(cargoSkuSupplyPrice.getStartNum() == null ? 0 : cargoSkuSupplyPrice.getStartNum());
                vo.setMulNumFlag(cargoSkuSupplyPrice.getMulNumFlag() == null ? "F" : cargoSkuSupplyPrice.getMulNumFlag());
            } else {
                vo.setOriginalPrice(BigDecimal.ZERO);
                vo.setStartNum(0);
                vo.setMulNumFlag(BoolType.F.name());
            }
            goodVoList.add(vo);
            // 构建促销查询参数
            BaseGood baseGood = new BaseGood();
            baseGood.setGoodId(purchaseGoods.getId());
            baseGood.setGoodSkuId(purchaseGoodsSku.getId());
            baseGood.setAmount(vo.getOriginalPrice());
            baseGoodList.add(baseGood);
        }
        // 构建促销查询参数
        query.setOrderBizType(OrderBizType.purchase);
        query.setGoodList(baseGoodList);
        query.setOrderType(OrderType.normal);
        // 查询促销信息
        Map<String, GoodPromotionalInfoVo> infoVoMap = promotionBizService.applyPromotionRule(query);
        for (BaseGoodVo goodVo : goodVoList) {
            GoodPromotionalInfoVo infoVo = infoVoMap.get(goodVo.getSkuId());
            if (infoVo != null) {
                goodVo.setPrice(infoVo.getPrice());
                goodVo.setPromotionalInfo(infoVo);
                if (infoVo.getBestActivity() == null) {
                    continue;
                }
                Integer stock = null;
                String isLimit = BoolType.F.name();
                if (PromotionActivityType.presell.name().equals(infoVo.getBestActivity().getActivityType())) {
                    PresellPromotionActivityVo activityVo = (PresellPromotionActivityVo) infoVo.getBestActivity();
                    stock = activityVo.getPresellNum();
                    isLimit = activityVo.getLimitFlg();
                } else if (PromotionActivityType.sale.name().equals(infoVo.getBestActivity().getActivityType())) {
                    SalePromotionActivityVo activityVo = (SalePromotionActivityVo) infoVo.getBestActivity();
                    stock = activityVo.getMaxNum();
                    isLimit = activityVo.getLimitFlg();
                }
                if (stock != null) {
                    goodVo.setOnSalesNo(stock);
                    goodVo.setCargoSkuStock(stock);
                    goodVo.setStock(stock);
                }
                if (BoolType.T.name().equals(isLimit)) {
                    goodVo.setMulNumFlag(BoolType.F.name());
                }
            } else {
                goodVo.setPrice(goodVo.getOriginalPrice());
            }
        }
        return goodVoList;
    }

    private List<BaseGoodVo> getBaseGoodVoListByGoodId(List<String> goodIdList, String storeLevelId, ClientType type, String status) {
        // 查询商品sku
        Map<String, Object> dataModel = Maps.newHashMap();
        dataModel.put("goodIds", StringUtils.join(goodIdList, ","));
        String sql = FreeMarkerHelper.getValueFromTpl("sql/purchaseGood/getSkuByGood.sql", dataModel);
        List<PurchaseGoodsSku> skuList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(PurchaseGoodsSku.class));
        Map<String, PurchaseGoodsSku> skuMap = ListUtils.toKeyMap(skuList, "purGoodsId");
        List<String> skuIdList = Lists.newArrayList();
        for (Object goodId : goodIdList) {
            if (skuMap.get(goodId.toString()) != null) {
                skuIdList.add(skuMap.get(goodId.toString()).getId());
            }
        }
        return getBaseGoodVoListBySkuId(skuIdList, storeLevelId, type, status);
    }

    @Override
    public Map<String, List<CargoSkuSupplyPrice>> querySanchaPrice(Set<String> cargoSkuIds) {
        List<PurchaseGoodsSku> skuList = querySanchaSku(cargoSkuIds);
        Map<String, PurchaseGoodsSku> skuMap = ListUtils.toKeyMap(skuList, "cargoSkuId");

        Map<String, List<CargoSkuSupplyPrice>> resultMap = Maps.newHashMap();
        List<CargoSkuSupplyPrice> priceList = cargoSkuSupplyPriceService.list(Conds.get().in("cargo_sku_id", ListUtils.getIdSet(skuList, "cargoSkuId")).order("section_start asc"));
        for (String cargoSkuId : skuMap.keySet()) {
            String purSkuId = skuMap.get(cargoSkuId).getId();
            List<CargoSkuSupplyPrice> currList = priceList.stream().filter(p -> (p.getCargoSkuId() + "").equals(cargoSkuId)).collect(Collectors.toList());
            resultMap.put(purSkuId, currList);
        }

        return resultMap;
    }

    @Override
    public List<PurchaseGoodsSku> querySanchaSku(Set<String> cargoSkuIds) {
        Map<String, Object> dataModel = Maps.newHashMap();
        dataModel.put("skuIds", StringUtils.join(cargoSkuIds, ","));
        String sql = FreeMarkerHelper.getValueFromTpl("sql/purchaseGood/querySanchaSku.sql", dataModel);
        List<PurchaseGoodsSku> skuList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(PurchaseGoodsSku.class));
        return skuList;
    }
}
