package pub.makers.shop.tradeGoods.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.service.SysDictService;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.baseGood.pojo.BaseGood;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.PromotionGoodQuery;
import pub.makers.shop.cargo.entity.Cargo;
import pub.makers.shop.cargo.service.CargoBasePropertysBizService;
import pub.makers.shop.cargo.service.CargoImageBizService;
import pub.makers.shop.cargo.service.CargoService;
import pub.makers.shop.cargo.service.CargoSkuTypeBizService;
import pub.makers.shop.cargo.vo.CargoBasePropertysVo;
import pub.makers.shop.promotion.service.PromotionBizService;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSku;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuService;
import pub.makers.shop.store.service.SubbranchAccountBizService;
import pub.makers.shop.tradeGoods.entity.*;
import pub.makers.shop.tradeGoods.pojo.GoodsEvaluationQuery;
import pub.makers.shop.tradeGoods.vo.GoodCheckResult;
import pub.makers.shop.tradeGoods.vo.GoodSearchInfo;
import pub.makers.shop.tradeGoods.vo.TradeGoodSkuVo;
import pub.makers.shop.tradeGoods.vo.TradeGoodVo;
import pub.makers.shop.tradeOrder.vo.IndentListVo;
import pub.makers.shop.user.service.WeixinUserInfoBizService;

import java.math.BigDecimal;
import java.util.*;

@Service(version = "1.0.0")
public class TradeGoodBizServiceImpl implements TradeGoodBizService {

    @Autowired
    private TradeGoodExtraService goodExtService;
    @Autowired
    private TradeGoodSkuService tradeGoodSkuService;
    @Autowired
    private TradeGoodService tradeGoodService;
    @Autowired
    private GoodPackageService goodPackageService;
    @Autowired
    private TradeGiftRuleService giftRuleService;
    @Autowired
    private TradeGiftRuleService tradeGiftRuleService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CargoService cargoService;
    @Autowired
    private GoodEvaluationBizService goodEvaluationBizService;
    @Reference(version = "1.0.0")
    private WeixinUserInfoBizService weixinUserInfoBizService;
    @Autowired
    private TradeGoodSkuBizService tradeGoodSkuBizService;
    @Autowired
    private GoodsColumnService goodsColumnService;
    @Autowired
    private CargoSkuTypeBizService cargoSkuTypeBizService;
    @Autowired
    private CargoImageBizService cargoImageBizService;
    @Autowired
    private TradeGoodsClassifyBizService tradeGoodsClassifyBizService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private CargoBasePropertysBizService cargoBasePropertysBizService;
    @Autowired
    private PurchaseGoodsSkuService purchaseGoodsSkuService;
    @Autowired
    private PromotionBizService promotionBizService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Reference(version = "1.0.0")
    private SubbranchAccountBizService subbranchAccountBizService;

    public TradeGoodExtra getByGoodId(String goodId) {

        return goodExtService.get(Conds.get().eq("goodId", goodId));
    }

    public ResultList<Map<String, Object>> listByParams(TradeGood params, Paging paging) {
        // TODO Auto-generated method stub
        return null;
    }

    public GoodCheckResult checkGood(IndentListVo detail) {

        if (isPackage(detail.getTradeGoodSkuId())) {
            checkPackage(detail);
        } else {
            checkSingle(detail);
        }

        return GoodCheckResult.createSuccess();
    }


    public TradeGoodSku lockStore(String skuId, int num) {

        TradeGoodSku sku = tradeGoodSkuService.getById(skuId);
        // 当前剩余数量
        Long nowNum = sku.getNums();
        nowNum = nowNum == null ? 0 : nowNum;
        Integer nowSaleNum = sku.getSaleNum();
        nowSaleNum = nowSaleNum == null ? 0 : nowSaleNum;

        ValidateUtils.isTrue(num <= nowNum, "商品sku的对应的上架数量小于客户购买的数量");

        // 更新销售数量
        tradeGoodSkuService.update(Update.byId(skuId).set("nums", nowNum - num).set("sale_num", nowSaleNum + num));

        if (isPackage(skuId)) {
            // 更新套餐的销售数量
            goodPackageService.updateSaleNum(skuId, num);
        } else {
            // 更新商品的总销售数量
            TradeGood good = tradeGoodService.updateSaleNum(sku.getGoodId(), num);
            sku.setGood(good);
        }

        return sku;
    }

    private boolean isPackage(String skuId) {
        return Long.valueOf(skuId) <= 100000000000L;
    }


    /**
     * 检查单品
     *
     * @param detail
     */
    private void checkSingle(IndentListVo detail) {

        Long skuId = Long.valueOf(detail.getTradeGoodSkuId());
        // 检查商品的库存
        TradeGoodSku sku = tradeGoodSkuService.getById(skuId);
        ValidateUtils.notNull(sku, "商品已下架");
        ValidateUtils.isTrue(sku.getNums() >= detail.getNumber(), String.format("%s已售罄！", detail.getTradeGoodName()));

        TradeGood good = tradeGoodService.getById(sku.getGoodId());
        ValidateUtils.notNull(good, "商品已下架");
        Long nowTs = new Date().getTime();

        if (good.getBeginTime() != null && good.getEndTime() != null && good.getEndTime().getTime() < nowTs) {

            ValidateUtils.isTrue(true, "商品活动时间已结束，无法购买！");
        }

//		ValidateUtils.notNull(good.getPostid(), "该商品未设置邮费规则，请联系客服处理！");
    }


    /**
     * 检查套餐
     *
     * @param detail
     */
    public void checkPackage(IndentListVo detail) {

        GoodPackage p = goodPackageService.getByBoomId(detail.getTradeGoodSkuId());
        ValidateUtils.notNull(p, "商品已下架");
        ValidateUtils.notNull(p.getPostid(), "该商品未设置邮费规则，请联系客服处理！");
        ValidateUtils.isTrue(p.getOnSalesNo() >= detail.getNumber(), String.format("%s已售罄！", p.getPackName()));
    }


    public List<IndentListVo> applyGiftRule(List<IndentListVo> indentList) {

        List<IndentListVo> giftList = Lists.newArrayList();
        Map<String, IndentListVo> detailMap = Maps.newHashMap();
        for (IndentListVo detail : indentList) {
            detailMap.put(detail.getTradeGoodSkuId(), detail);
        }

        // 查询可赠送的赠品列表
        List<TradeGiftRule> ruleList = giftRuleService.list(Conds.get().in("goodSkuId", ListUtils.getIdSet(indentList, "tradeGoodSkuId")));
        for (TradeGiftRule rule : ruleList) {
            IndentListVo detail = detailMap.get(rule.getGoodSkuId() + "");
            IndentListVo gift = new IndentListVo();
            // 查询商品的库存信息
            TradeGoodSku giftSku = tradeGoodSkuService.getById(rule.getGiftSkuId());
            if (giftSku != null) {

                // 初始化赠品属性
                gift.setTradeGoodSkuId(giftSku.getId() + "");
                gift.setGiftFlag(BoolType.T.name());

                // 赠品的数量 = 规则指定的数量 * 商品的购买数量
                int giftNum = rule.getNum() * detail.getNumber();
                // 如果赠品数量不够，则赠送最大可赠送数量
                if (giftNum > giftSku.getNums()) {
                    giftNum = giftSku.getNums().intValue();
                }
                if (giftNum > 0) {
                    gift.setNumber(giftNum);
                    giftList.add(gift);
                }
            }

        }

        return giftList;
    }

    //保存商品的关联赠品
    public TradeGiftRule save(TradeGiftRule tradeGiftRule) {

        return tradeGiftRuleService.insert(tradeGiftRule);
    }

    //删除赠品
    public void deleteById(String tradeGoodId) {

        tradeGiftRuleService.delete(Conds.get().eq("good_id", tradeGoodId));//deleteById(tradeGoodId);
    }

    //根据商品id查询该商品是否已有关联赠品
    public List<TradeGiftRule> queryGiftByGoodId(String tradeGoodId) {
        return tradeGiftRuleService.list(Conds.get().eq("goodId", tradeGoodId));
    }

    @Override
    public List<BaseGoodVo> getSearchGoodListSer(GoodSearchInfo goodSearchInfo) {
        Map<String, Object> data = Maps.newHashMap();
        if (StringUtils.isNotEmpty(goodSearchInfo.getOneType())) {
            goodSearchInfo.setClassifyId(goodSearchInfo.getOneType());
        }
        if (StringUtils.isNotEmpty(goodSearchInfo.getTwoType())) {
            goodSearchInfo.setClassifyId(goodSearchInfo.getTwoType());
        }
        if (StringUtils.isNotEmpty(goodSearchInfo.getThreeType())) {
            goodSearchInfo.setClassifyId(goodSearchInfo.getThreeType());
        }
        if (StringUtils.isNotEmpty(goodSearchInfo.getClassifyId())) {
            Set<String> idSet = tradeGoodsClassifyBizService.findAllIdByParentId(Sets.newHashSet(goodSearchInfo.getClassifyId()));
            goodSearchInfo.setClassifyId(StringUtils.join(idSet, ","));
        }
        data.put("info", goodSearchInfo);

        String sql = FreeMarkerHelper.getValueFromTpl("sql/tradeGood/getSearchGoodList.sql", data);
        RowMapper<TradeGoodVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(TradeGoodVo.class);
        List<TradeGoodVo> list = jdbcTemplate.query(sql, rowMapper);
        if (list.isEmpty()) {
            return Lists.newArrayList();
        }
        Map<String, TradeGoodVo> searchMap = Maps.newLinkedHashMap();
        for (TradeGoodVo tradeGoodVo : list) {
            searchMap.put(tradeGoodVo.getGoodSkuId(), tradeGoodVo);
        }
        List<BaseGoodVo> goodVoList = tradeGoodSkuBizService.getGoodSkuListBySkuId(Lists.newArrayList(searchMap.keySet()), goodSearchInfo.getStoreLevelId(), ClientType.mobile);
        Boolean isSub = true;
        if (StringUtils.isNotEmpty(goodSearchInfo.getShopId())) {
            isSub = subbranchAccountBizService.isSubAccount(goodSearchInfo.getShopId());
        }
        for (BaseGoodVo goodVo : goodVoList) {
            TradeGoodVo tradeGoodVo = searchMap.get(goodVo.getSkuId());
            if (!isSub) {
                goodVo.setSupplyPrice(tradeGoodVo.getSupplyPrice() == null ? BigDecimal.ZERO : new BigDecimal(tradeGoodVo.getSupplyPrice()));
            } else {
                goodVo.setSupplyPrice(null);
            }
            goodVo.setLabel(tradeGoodVo.getLabel());
        }
        return goodVoList;
    }

    @Override
    public void saveOrUpdateGift(List<TradeGiftRule> tradeGiftRuleList, String skuId) {
        tradeGiftRuleService.delete(Conds.get().eq("good_sku_id",skuId));
        for(TradeGiftRule tradeGiftRule:tradeGiftRuleList) {
            tradeGiftRule.setRuleId(IdGenerator.getDefault().nextId());
            tradeGiftRule.setDelFlag("F");
            tradeGiftRule.setIsValid("T");
            tradeGiftRule.setDateCreated(new Date());
            tradeGiftRuleService.insert(tradeGiftRule);
        }
        PromotionGoodQuery promotionGoodQuery = new PromotionGoodQuery();
        TradeGoodSku tradeGoodSku = tradeGoodSkuService.get(Conds.get().eq("id", skuId));
        PurchaseGoodsSku purchaseGoodsSku = purchaseGoodsSkuService.get(Conds.get().eq("id",skuId));
        List<BaseGood> lists = new ArrayList<>();
        BaseGood baseGood = new BaseGood();
        if(tradeGoodSku!=null){
            promotionGoodQuery.setOrderBizType(OrderBizType.trade);
            baseGood.setGoodId(tradeGoodSku.getGoodId()+"");
            baseGood.setGoodSkuId(tradeGoodSku.getId()+"");
        }else{
            promotionGoodQuery.setOrderBizType(OrderBizType.purchase);
            baseGood.setGoodId(purchaseGoodsSku.getPurGoodsId()+"");
            baseGood.setGoodSkuId(purchaseGoodsSku.getId()+"");
        }
        lists.add(baseGood);
        promotionGoodQuery.setGoodList(lists);
        promotionGoodQuery.setOrderType(OrderType.normal);
        if(lists.size()>0) {
            promotionBizService.updatePromotionRule(promotionGoodQuery);
        }
    }

    @Override
    public List<TradeGiftRule> getGiftData(String skuId) {
        return tradeGiftRuleService.list(Conds.get().eq("good_sku_id",skuId));
    }

    @Override
    public void updateClassifyValid() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                jdbcTemplate.update("update trade_good set classify_valid = 'T'");
                List<TradeGoodsClassify> classifyList = tradeGoodsClassifyBizService.getDisableClassifyList();
                if (classifyList.isEmpty()) {
                    return;
                }
                StringBuilder sql = new StringBuilder("update trade_good set classify_valid = 'F' where");
                for (TradeGoodsClassify classify : classifyList) {
                    sql.append(" group_id like '%").append(classify.getId()).append("%' or");
                }
                sql.delete(sql.length() - 3, sql.length());
                jdbcTemplate.update(sql.toString());
            }
        });
    }

    @Override
    public TradeGood getGoodById(String goodId) {

        return tradeGoodService.getById(goodId);
    }

    @Override
    public TradeGoodVo getGoodVoById(String goodId, ClientType clientType) {
        return getGoodVoById(goodId, null, clientType);
    }

    @Override
    public TradeGoodVo getGoodVoById(String goodId, String storeLevelId, ClientType clientType) {
        TradeGood tradeGood = tradeGoodService.getById(goodId);
        ValidateUtils.notNull(tradeGood, "商品不存在");
        TradeGoodVo vo = TradeGoodVo.fromTradeGood(tradeGood);
        // 标签
        SysDict sysDict = sysDictService.getById(tradeGood.getLabel());
        if (sysDict != null && !"代销".equals(sysDict.getValue())) {
            vo.setLabel(sysDict.getValue());
        }
        // 商品sku列表
        List<TradeGoodSkuVo> tradeGoodSkuList = tradeGoodSkuBizService.getGoodsSkuList(goodId, storeLevelId);
        vo.setTradeGoodSkuVoList(tradeGoodSkuList);

        GoodsColumn column = goodsColumnService.getById(tradeGood.getCategory());
        if (column != null) {
            vo.setColumnName(column.getColumnName());
        }

        Cargo cargo = cargoService.getById(tradeGood.getCargoId());
        if (cargo != null) {
            // 规格
            vo.setSkuTypeList(cargoSkuTypeBizService.getCargoSkuTypeList(tradeGood.getCargoId().toString()));
            // 轮播图
            vo.setShowImages(cargoImageBizService.getGoodsAlbum(tradeGood.getCargoId().toString(), clientType));
            // 详情
            if (ClientType.pc.equals(clientType)) {
                vo.setDetailInfo(cargo.getPcDetailInfo());
            } else if (ClientType.mobile.equals(clientType)) {
                vo.setDetailInfo(cargo.getMobileDetailInfo());
            }
            if (cargo.getDetailImageGroupId() != null) {
                vo.setDetailImages(cargoImageBizService.getImageGroup(cargo.getDetailImageGroupId().toString()));
            }
            vo.setCargoNo(cargo.getCargoNo());
            vo.setFixedPrice(cargo.getFixedPrice() == null ? BigDecimal.ZERO.toString() : cargo.getFixedPrice().toString());
            // 公共参数
            Map<String, List<CargoBasePropertysVo>> propertysMap = cargoBasePropertysBizService.getPropertysList(Lists.newArrayList(cargo.getId().toString()));
            vo.setBasePropertysList(propertysMap.get(cargo.getId().toString()));
        }

        // 评论
        GoodsEvaluationQuery query = new GoodsEvaluationQuery();
        query.setGoodId(vo.getId());
        vo.setGoodEvaluationList(goodEvaluationBizService.getEvaluationList(query));
        return vo;
    }

}
