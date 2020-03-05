package pub.makers.shop.tradeGoods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
import pub.makers.shop.cargo.entity.CargoSku;
import pub.makers.shop.cargo.entity.CargoSkuSupplyPrice;
import pub.makers.shop.cargo.service.CargoImageBizService;
import pub.makers.shop.cargo.service.CargoSkuService;
import pub.makers.shop.cargo.service.CargoSkuSupplyPriceService;
import pub.makers.shop.cargo.service.GoodPutawayScheduleService;
import pub.makers.shop.promotion.enums.PromotionActivityType;
import pub.makers.shop.promotion.service.PromotionBizService;
import pub.makers.shop.promotion.vo.GoodPromotionalInfoVo;
import pub.makers.shop.promotion.vo.PresellPromotionActivityVo;
import pub.makers.shop.promotion.vo.SalePromotionActivityVo;
import pub.makers.shop.stock.pojo.StockQuery;
import pub.makers.shop.stock.service.StockBizService;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.tradeGoods.entity.TradeGood;
import pub.makers.shop.tradeGoods.entity.TradeGoodSku;
import pub.makers.shop.tradeGoods.vo.TradeGoodSkuVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service(version = "1.0.0")
public class TradeGoodSkuBizServiceImpl implements TradeGoodSkuBizService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StockBizService stockBizService;
    @Autowired
    private TradeGoodSkuService tradeGoodSkuService;
    @Autowired
    private TradeGoodService tradeGoodService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private GoodPutawayScheduleService goodPutawayScheduleService;
    @Autowired
    private CargoSkuService cargoSkuService;
    @Autowired
    private CargoImageBizService cargoImageBizService;
    @Autowired
    private PromotionBizService promotionBizService;
    @Autowired
    private CargoSkuSupplyPriceService cargoSkuSupplyPriceService;

    private String selectSkuByIdSql = "select *, (select `name` from trade_good where id = trade_good_sku.good_id) as good_name" +
            " from trade_good_sku" +
            " where 1=1 and id = ?";

    public List<TradeGoodSkuVo> listGiftSkus() {

        List<TradeGoodSkuVo> resultList = jdbcTemplate.query("select a.*, b.name as good_name from trade_good_sku a, trade_good b where a.good_id = b.id and b.gift_flag = 'T' and b.status = 1", new BeanPropertyRowMapper<TradeGoodSkuVo>(TradeGoodSkuVo.class));

        return resultList;
    }

    @Override
    public void upGoodSku(Long skuId, Integer num, Long userId) {
        Long count = goodPutawayScheduleService.count(Conds.get().eq("sku_id", skuId).eq("is_valid", BoolType.T.name()).eq("del_flag", BoolType.F.name()));
        ValidateUtils.isTrue(count.equals(0l), "商品sku已启动定时上架，无法再执行上架");
        upGoodSkuSchedule(skuId, num, userId);
    }

    @Override
    public void upGoodSkuSchedule(final Long skuId, final Integer num, final Long userId) {
        final TradeGoodSku sku = tradeGoodSkuService.getById(skuId);
        ValidateUtils.notNull(sku, "商品sku不存在");
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                // 更新sku库存
                stockBizService.upGoodStock(new StockQuery(skuId, OrderBizType.trade, num, userId));
                // 更新sku上架状态
                tradeGoodSkuService.update(Update.byId(sku.getId()).set("status", 1));
                // 更新商品上架状态
                tradeGoodService.update(Update.byId(sku.getGoodId()).set("status", 1));
            }
        });
    }

    @Override
    public void downGoodSku(final Long skuId, final Long userId) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                TradeGoodSku sku = tradeGoodSkuService.getById(skuId);
                ValidateUtils.notNull(sku, "商品sku不存在");
                if (sku.getOnSalesNo() != null && sku.getOnSalesNo() > 0) {
                    // 更新sku库存
                    stockBizService.downGoodStock(new StockQuery(skuId, OrderBizType.trade, sku.getOnSalesNo(), userId));
                }
                // 更新sku下架状态
                tradeGoodSkuService.update(Update.byId(sku.getId()).set("status", 0));
                // 商品的sku都下架时更新商品下架状态
                Long count = tradeGoodSkuService.count(Conds.get().eq("good_id", sku.getGoodId()).gt("on_sales_no", 0));
                if (count.equals(0l)) {
                    tradeGoodService.update(Update.byId(sku.getGoodId()).set("status", 0));
                }
            }
        });
    }

    @Override
    public void updateUpSkuNum(Long skuId, Integer num, Long userId) {
        TradeGoodSku sku = tradeGoodSkuService.getById(skuId);
        ValidateUtils.notNull(sku, "商品sku不存在");
        ValidateUtils.isTrue(num >= 0, "上架数量必须大于零");
        if (sku.getOnSalesNo() > num) {
            // 更新sku库存
            stockBizService.downGoodStock(new StockQuery(skuId, OrderBizType.trade, sku.getOnSalesNo() - num, userId));
        } else if (sku.getOnSalesNo() < num) {
            // 更新sku库存
            stockBizService.upGoodStock(new StockQuery(skuId, OrderBizType.trade, num - sku.getOnSalesNo(), userId));
        }
    }

    @Override
    public List<TradeGoodSkuVo> getGoodsSkuList(String goodId) {
        return getGoodsSkuList(goodId, null);
    }

    @Override
    public List<TradeGoodSkuVo> getGoodsSkuList(String goodId, String storeLevelId) {
        List<TradeGoodSku> skuList = tradeGoodSkuService.list(Conds.get().eq("good_id", goodId));
        List<String> skuIdList = Lists.newArrayList(ListUtils.getIdSet(skuList, "cargoSkuId"));
        List<CargoSku> cargoSkuList = cargoSkuService.list(Conds.get().in("id", skuIdList));
        Map<String, CargoSku> cargoSkuMap = ListUtils.toKeyMap(cargoSkuList, "id");
        // 供货价
        Map<String, BigDecimal> supplyPriceMap = Maps.newHashMap();
        if (StringUtils.isNotEmpty(storeLevelId)) {
            supplyPriceMap = getSkuSupplyPrice(Lists.newArrayList(ListUtils.getIdSet(skuList, "id")), storeLevelId);
        }
        // 图片
        Map<String, ImageVo> imageVoMap = cargoImageBizService.getImageByGroup(Lists.newArrayList(ListUtils.getIdSet(cargoSkuList, "coverImg")));
        List<TradeGoodSkuVo> skuVoList = Lists.newArrayList();
        List<BaseGood> baseGoodList = Lists.newArrayList();
        for (TradeGoodSku sku : skuList) {
            TradeGoodSkuVo skuVo = TradeGoodSkuVo.fromTradeGoodSku(sku);
            CargoSku cargoSku = cargoSkuMap.get(skuVo.getCargoSkuId());
            if (cargoSku != null) {
                skuVo.setSkuValue(cargoSku.getSkuItemValue());
                skuVo.setFixedPrice(cargoSku.getFixedPrice());
                skuVo.setRetailPrice(cargoSku.getRetailPrice());
                skuVo.setCoverImg(imageVoMap.get(cargoSku.getCoverImg()));
                skuVo.setCargoSkuName(cargoSku.getSkuName());
                //TODO 批量查询
                Integer cargoSkuStock = stockBizService.queryCurrStork(cargoSku.getId().toString(), "cargo");
                skuVo.setCargoSkuStock(cargoSkuStock);
                skuVo.setStock(Math.min(skuVo.getOnSalesNo(), skuVo.getCargoSkuStock()));
            }
            // 供货价
            if (supplyPriceMap.get(skuVo.getId()) != null) {
                skuVo.setSupplyPrice(supplyPriceMap.get(skuVo.getId()).toString());
            }
            skuVoList.add(skuVo);
            BaseGood baseGood = new BaseGood();
            baseGood.setGoodSkuId(skuVo.getId());
            baseGood.setGoodId(skuVo.getGoodId());
            baseGood.setAmount(skuVo.getSalePrice());
            baseGoodList.add(baseGood);
        }
        // 促销信息
        PromotionGoodQuery query = new PromotionGoodQuery();
        query.setOrderBizType(OrderBizType.trade);
        query.setOrderType(OrderType.normal);
        query.setGoodList(baseGoodList);
        query.setIsDetail(true);

        Map<String, GoodPromotionalInfoVo> infoVoMap = promotionBizService.applyPromotionRule(query);
        for (TradeGoodSkuVo skuVo : skuVoList) {
            GoodPromotionalInfoVo infoVo = infoVoMap.get(skuVo.getId());
            if (infoVo != null) {
                skuVo.setPromotionalInfo(infoVo);
                if (infoVo.getBestActivity() == null) {
                    continue;
                }
                Integer stock = null;
                if (PromotionActivityType.presell.name().equals(infoVo.getBestActivity().getActivityType())) {
                    PresellPromotionActivityVo activityVo = (PresellPromotionActivityVo) infoVo.getBestActivity();
                    stock = activityVo.getPresellNum();
                } else if (PromotionActivityType.sale.name().equals(infoVo.getBestActivity().getActivityType())) {
                    SalePromotionActivityVo activityVo = (SalePromotionActivityVo) infoVo.getBestActivity();
                    stock = activityVo.getMaxNum();
                }
                if (stock != null) {
                    skuVo.setOnSalesNo(stock);
                    skuVo.setCargoSkuStock(stock);
                    skuVo.setStock(stock);
                }
            }
        }
        return skuVoList;
    }

    @Override
    public TradeGoodSkuVo getGoodSkuDetail(String skuId) {
        TradeGoodSku sku = tradeGoodSkuService.getById(skuId);
        TradeGoodSkuVo vo = TradeGoodSkuVo.fromTradeGoodSku(sku);
        return vo;
    }

    @Override
    public BigDecimal getSkuPrice(String skuId) {
        TradeGoodSku sku = tradeGoodSkuService.getById(skuId);
        CargoSku cargoSku = cargoSkuService.getById(sku.getCargoSkuId());
        return cargoSku.getRetailPrice() == null ? BigDecimal.ZERO : cargoSku.getRetailPrice();
    }

    @Override
    public Map<String, BigDecimal> getSkuPrice(List<String> skuIdList) {
        List<TradeGoodSku> tradeGoodSkuList = tradeGoodSkuService.list(Conds.get().in("id", skuIdList));
        List<CargoSku> cargoSkuList = cargoSkuService.list(Conds.get().in("id", ListUtils.getIdSet(tradeGoodSkuList, "cargoSkuId")));
        Map<String, CargoSku> cargoSkuMap = ListUtils.toKeyMap(cargoSkuList, "id");
        Map<String, BigDecimal> skuPriceMap = Maps.newHashMap();
        for (TradeGoodSku goodSku : tradeGoodSkuList) {
            skuPriceMap.put(goodSku.getId().toString(), cargoSkuMap.get(goodSku.getCargoSkuId().toString()).getRetailPrice());
        }
        return skuPriceMap;
    }

    @Override
    public Map<String, BigDecimal> getSkuSupplyPrice(List<String> skuIdList, String storeLevelId) {
        List<TradeGoodSku> skuList = tradeGoodSkuService.list(Conds.get().in("id", skuIdList));
        Conds conds = Conds.get().in("cargo_sku_id", ListUtils.getIdSet(skuList, "cargoSkuId")).order("supply_price desc");
        if (StringUtils.isNotEmpty(storeLevelId)) {
            conds.eq("store_level_id", storeLevelId);
        }
        List<CargoSkuSupplyPrice> supplyPriceList = cargoSkuSupplyPriceService.list(conds);
        Map<String, CargoSkuSupplyPrice> supplyPriceMap = ListUtils.toKeyMap(supplyPriceList, "cargoSkuId");
        Map<String, BigDecimal> skuPriceMap = Maps.newHashMap();
        for (TradeGoodSku sku : skuList) {
            if (sku.getCargoSkuId() != null && supplyPriceMap.get(sku.getCargoSkuId().toString()) != null) {
                skuPriceMap.put(sku.getId().toString(), supplyPriceMap.get(sku.getCargoSkuId().toString()).getSupplyPrice());
            }
        }
        return skuPriceMap;
    }

    @Override
    public List<BaseGoodVo> getGoodSkuListBySkuId(List<String> skuIdList, ClientType type) {
        return getBaseGoodVoListBySkuId(skuIdList, type, "1");
    }

    @Override
    public List<BaseGoodVo> getGoodSkuListByGoodId(List<String> goodIdList, ClientType type) {
        return getBaseGoodVoListByGoodId(goodIdList, type, "1");
    }

    @Override
    public List<BaseGoodVo> getGoodSkuListBySkuId(List<String> skuIdList, String storeLevelId, ClientType type) {
        return getGoodSkuListBySkuId(skuIdList, type);
    }

    @Override
    public List<BaseGoodVo> getAllGoodSkuListBySkuId(List<String> skuIdList, String storeLevelId, ClientType type) {
        return getBaseGoodVoListBySkuId(skuIdList, type, null);
    }

    @Override
    public List<BaseGoodVo> getGoodSkuListByGoodId(List<String> goodIdList, String storeLevelId, ClientType type) {
        return getGoodSkuListByGoodId(goodIdList, type);
    }

    @Override
    public List<BaseGoodVo> getAllGoodSkuListByGoodId(List<String> goodIdList, String storeLevelId, ClientType type) {
        return getBaseGoodVoListByGoodId(goodIdList, type, null);
    }

    private List<BaseGoodVo> getBaseGoodVoListBySkuId(List<String> skuIdList, ClientType type, String status) {
        List<BaseGoodVo> goodVoList = Lists.newArrayList();
        PromotionGoodQuery query = new PromotionGoodQuery();
        List<BaseGood> baseGoodList = Lists.newArrayList();
        // sku
        Conds skuConds = Conds.get().in("id", skuIdList);
        if (StringUtils.isNotEmpty(status)) {
            skuConds.eq("status", status);
        }
        List<TradeGoodSku> tradeGoodSkuList = tradeGoodSkuService.list(skuConds);
        Map<String, TradeGoodSku> skuMap = ListUtils.toKeyMap(tradeGoodSkuList, "id");
        // 商品
        Conds goodConds = Conds.get().in("id", ListUtils.getIdSet(tradeGoodSkuList, "goodId")).order("sort desc");
        if (StringUtils.isNotEmpty(status)) {
            goodConds.eq("status", status);
        }
        List<TradeGood> goodList = tradeGoodService.list(goodConds);
        Map<String, TradeGood> goodMap = ListUtils.toKeyMap(goodList, "id");
        // 价格
        Map<String, BigDecimal> skuPriceMap = getSkuPrice(Lists.newArrayList(skuMap.keySet()));
        // 图片
        List<String> cargoIdList = Lists.newArrayList(ListUtils.getIdSet(goodList, "cargoId"));
        Map<String, ImageVo> imageVoMap = cargoImageBizService.getCargoImage(cargoIdList, type);
        // cargoSku
        List<String> cargoSkuIdList = Lists.newArrayList(ListUtils.getIdSet(tradeGoodSkuList, "cargoSkuId"));
        List<CargoSku> cargoSkuList = cargoSkuService.list(Conds.get().in("id", cargoSkuIdList));
        Map<String, CargoSku> cargoSkuMap = ListUtils.toKeyMap(cargoSkuList, "id");
        Map<String, Integer> cargoSkuStockMap = stockBizService.queryCurrStork(cargoSkuIdList, "cargo");
        for (String skuId : skuIdList) {
            TradeGoodSku tradeGoodSku = skuMap.get(skuId);
            if (tradeGoodSku == null) {
                continue;
            }
            TradeGood tradeGood = goodMap.get(tradeGoodSku.getGoodId().toString());
            if (tradeGood == null) {
                continue;
            }
            BaseGoodVo vo = new BaseGoodVo();
            CargoSku cargoSku = cargoSkuMap.get(tradeGoodSku.getCargoSkuId().toString());
            vo.complete(tradeGood).complete(tradeGoodSku).setSkuName(cargoSku.getSkuName());
            vo.setStatus(tradeGood.getStatus() == 1 && "1".equals(tradeGoodSku.getStatus()) ? "1" : "0");
            Integer cargoSkuStock = cargoSkuStockMap.get(cargoSku.getId().toString());
            vo.setCargoSkuStock(cargoSkuStock == null ? 0 : cargoSkuStock);
            vo.setStock(Math.min(vo.getOnSalesNo(), vo.getCargoSkuStock()));
            // 图片
            vo.setImage(imageVoMap.get(tradeGood.getCargoId().toString()));
            // 价格
            vo.setOriginalPrice(skuPriceMap.get(tradeGoodSku.getId().toString()));
            goodVoList.add(vo);
            // 构建促销查询参数
            BaseGood baseGood = new BaseGood();
            baseGood.setGoodId(tradeGood.getId().toString());
            baseGood.setGoodSkuId(tradeGoodSku.getId().toString());
            baseGood.setAmount(vo.getOriginalPrice());
            baseGoodList.add(baseGood);
        }
        // 构建促销查询参数
        query.setOrderBizType(OrderBizType.trade);
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
                if (PromotionActivityType.presell.name().equals(infoVo.getBestActivity().getActivityType())) {
                    PresellPromotionActivityVo activityVo = (PresellPromotionActivityVo) infoVo.getBestActivity();
                    stock = activityVo.getPresellNum();
                } else if (PromotionActivityType.sale.name().equals(infoVo.getBestActivity().getActivityType())) {
                    SalePromotionActivityVo activityVo = (SalePromotionActivityVo) infoVo.getBestActivity();
                    stock = activityVo.getMaxNum();
                }
                if (stock != null) {
                    goodVo.setOnSalesNo(stock);
                    goodVo.setCargoSkuStock(stock);
                    goodVo.setStock(stock);
                }
            } else {
                goodVo.setPrice(goodVo.getOriginalPrice());
            }
        }
        return goodVoList;
    }

    private List<BaseGoodVo> getBaseGoodVoListByGoodId(List<String> goodIdList, ClientType type, String status) {
        // 查询商品sku
        Map<String, Object> dataModel = Maps.newHashMap();
        dataModel.put("goodIds", StringUtils.join(goodIdList, ","));
        String sql = FreeMarkerHelper.getValueFromTpl("sql/tradeGood/getSkuByGood.sql", dataModel);
        List<String> skuIdList = jdbcTemplate.queryForList(sql, String.class);
        return getBaseGoodVoListBySkuId(skuIdList, type, status);
    }

    @Override
    public BigDecimal getSkuPrice(String skuId, String storeLevelId) {
        return getSkuPrice(skuId);
    }

    @Override
    public Map<String, BigDecimal> getSkuPrice(List<String> skuIdList, String storeLevelId) {
        return getSkuPrice(skuIdList);
    }

    @Override
    public Map<String, BigDecimal> getSkuPriceByGood(List<String> goodIdList, String storeLevelId) {
        return null;
    }
}
