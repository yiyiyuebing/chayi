package pub.makers.shop.promotion.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.baseGood.pojo.BaseGood;
import pub.makers.shop.baseGood.service.BaseGoodBizService;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.PromotionGoodQuery;
import pub.makers.shop.cargo.service.CargoImageBizService;
import pub.makers.shop.cargo.service.GoodPageTplMgrBizService;
import pub.makers.shop.promotion.entity.PresellActivity;
import pub.makers.shop.promotion.entity.PresellGood;
import pub.makers.shop.promotion.enums.PresellType;
import pub.makers.shop.promotion.enums.PromotionActivityType;
import pub.makers.shop.promotion.vo.PresellActivityVo;
import pub.makers.shop.promotion.vo.PresellGoodVo;
import pub.makers.shop.promotion.vo.PresellParam;
import pub.makers.shop.promotion.vo.PresellSaleVo;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassify;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSku;
import pub.makers.shop.purchaseGoods.service.PurchaseClassifyService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuService;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.tradeGoods.entity.Image;
import pub.makers.shop.tradeGoods.entity.TradeGoodSku;
import pub.makers.shop.tradeGoods.entity.TradeGoodsClassify;
import pub.makers.shop.tradeGoods.service.ImageService;
import pub.makers.shop.tradeGoods.service.TradeGoodSkuBizService;
import pub.makers.shop.tradeGoods.service.TradeGoodSkuService;
import pub.makers.shop.tradeGoods.service.TradeGoodsClassifyService;

import java.util.*;

/**
 * Created by daiwenfa on 2017/6/19.
 */
@Service(version="1.0.0")
public class PresellAdminServiceImpl implements PresellAdminService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PresellActivityService presellActivityService;
    @Autowired
    private PresellGoodService presellGoodService;
    @Reference(version = "1.0.0")
    private TradeGoodSkuBizService tradeGoodSkuBizService;
    @Reference(version = "1.0.0")
    private PurchaseGoodsSkuBizService purchaseGoodsSkuBizService;
    @Reference(version = "1.0.0")
    private CargoImageBizService cargoImageBizService;
    @Autowired
    private TradeGoodSkuService tradeGoodSkuService;
    @Autowired
    private PurchaseGoodsSkuService purchaseGoodsSkuService;
    @Autowired
    private ImageService imageService;
    @Reference(version = "1.0.0")
    private PromotionBizService promotionBizService;
    @Autowired
    private TradeGoodsClassifyService tradeGoodsClassifyService;
    @Autowired
    private PurchaseClassifyService purchaseClassifyService;
    @Autowired
    private GoodPageTplMgrBizService goodPageTplMgrBizService;

    @Override
    public ResultList<PresellActivityVo> getPageList(PresellParam param, Paging pg) {
        String sql = FreeMarkerHelper.getValueFromTpl("sql/presell/querySpPresellActivityList.sql", param);
        String pcAlbumIdSql = FreeMarkerHelper.getValueFromTpl("sql/presell/queryActivityGoodImage.sql", param);
        RowMapper<PresellActivityVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PresellActivityVo.class);
        List<PresellActivityVo> list = jdbcTemplate.query(sql + " limit ?,? " ,rowMapper,pg.getPs(),pg.getPn());
        for(PresellActivityVo presellActivityVo:list){
            if(presellActivityVo.getId()!=null&&!presellActivityVo.getId().equals("")) {
                try {
                    String pcAlbumId = jdbcTemplate.queryForObject(pcAlbumIdSql, String.class, presellActivityVo.getId());
                    if (pcAlbumId != null) {
                        List<Image> images = imageService.list(Conds.get().eq("group_id", pcAlbumId));
                        if (images.size() > 0) {
                            presellActivityVo.setImage(images.get(0).getPicUrl());
                        }
                    }
                }catch (Exception e){
                }
            }
        }
        Number total = jdbcTemplate.queryForObject("select count(0) from (" + sql +") nums ",null,Integer.class);
        ResultList<PresellActivityVo> resultList = new ResultList<PresellActivityVo>();
        resultList.setResultList(list);
        resultList.setTotalPages(total!=null?total.intValue():0);
        return resultList;
    }

    @Override
    public boolean remove(String id) {
        List<PresellGood> list = presellGoodService.list(Conds.get().eq("activity_id", id));
        if (list != null && list.size() > 0) {
            for (PresellGood spPresellGood : list) {
                presellGoodService.update(Update.byId(spPresellGood.getId()).set("del_flag", "T").set("last_updated", new Date()));
            }
        }
        presellActivityService.update(Update.byId(id).set("del_flag", "T").set("last_updated", new Date()));
        return true;
    }


    @Override
    public boolean ableOrDisable(String id, String operation, long userId) {
        PresellActivity presellActivityOld = presellActivityService.get(Conds.get().eq("id", id));
        String flag = presellActivityOld.getIsValid();
        if(!flag.equals(operation)) {
            presellActivityService.update(Update.byId(id).set("is_valid", operation).set("last_updated",new Date()));
            List<PresellGood> list = presellGoodService.list(Conds.get().eq("activity_id", id));
            if (list != null && list.size() > 0) {
                for (PresellGood spPresellGood : list) {
                    presellGoodService.update(Update.byId(spPresellGood.getId()).set("is_valid", operation).set("last_updated", new Date()));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void addOrUpdate(PresellActivity presellActivity, long userId) {
        String id = "";
        if(presellActivity.getId().equals("")||presellActivity.getId()==null){
            id = IdGenerator.getDefault().nextId() + "";
            presellActivity.setId(id);
            presellActivity.setDelFlag("F");
            presellActivity.setIsValid("T");
            presellActivity.setCreateBy(userId+"");
            presellActivity.setDateCreated(new Date());
            presellActivityService.insert(presellActivity);
        }else{
            id = presellActivity.getId();
            presellActivity.setLastUpdated(new Date());
            presellActivityService.update(presellActivity);
        }
        //刷新缓存
        List<PresellGood> list = presellGoodService.list(Conds.get().eq("activity_id",id));
        PromotionGoodQuery promotionGoodQuery = new PromotionGoodQuery();
        List<BaseGood> lists = new ArrayList<>();
        if(list.size()>0) {
            for (PresellGood presellGood : list) {
                BaseGood baseGood = new BaseGood();
                baseGood.setGoodId(presellGood.getGoodId());
                baseGood.setGoodSkuId(presellGood.getSkuId());
                lists.add(baseGood);
            }
        }
        promotionGoodQuery.setGoodList(lists);
        promotionGoodQuery.setOrderType(OrderType.normal);
        if(presellActivity.getOrderBizType().equals(OrderBizType.trade.toString())){
            promotionGoodQuery.setOrderBizType(OrderBizType.trade);
        }else{
            promotionGoodQuery.setOrderBizType(OrderBizType.purchase);
        }
        if(lists.size()>0) {
            promotionBizService.updatePromotionRule(promotionGoodQuery);
        }
    }

    @Override
    public PresellActivity getPresellActivityData(String id) {
        PresellActivity presellActivity = presellActivityService.get(Conds.get().eq("id", id));
        List<PresellGood> list = presellGoodService.list(Conds.get().eq("activity_id", id));
        presellActivity.setPresellGoodList(list);
        return presellActivity;
    }

    public String getAllClassifyId(String parentId, String goodType){
        List<String> allList = new ArrayList<>();
        String allClassifyId = "";
        if(StringUtils.isNotBlank(parentId)) {
            if(goodType.equals(OrderBizType.trade.toString())) {
                String classify = "or tg.group_id like ";
                List<TradeGoodsClassify> list = tradeGoodsClassifyService.list(Conds.get().eq("parent_id", parentId));
                List<String> xiajiList = new ArrayList<>();//有下级的分类
                if (list.size() > 0) {
                    for (TradeGoodsClassify cargoClassify : list) {
                        allList.add(classify + " '%"+cargoClassify.getId() + "%' ");
                        xiajiList.add(cargoClassify.getId() + "");
                    }
                }
                List<TradeGoodsClassify> lists = tradeGoodsClassifyService.list(Conds.get().in("parent_id", xiajiList));//查询下级的下级
                if (lists.size() > 0) {
                    for (TradeGoodsClassify tradeGoodsClassify : lists) {
                        allList.add(classify + " '%"+tradeGoodsClassify.getId() + "%' ");
                    }
                }
            }else{
                List<PurchaseClassify> list = purchaseClassifyService.list(Conds.get().eq("parent_id", parentId));
                List<String> xiajiList = new ArrayList<>();//有下级的分类
                String classify = "or pg.group_id like ";
                if (list.size() > 0) {
                    for (PurchaseClassify cargoClassify : list) {
                        allList.add(classify + " '%"+cargoClassify.getId() + "%' ");
                        xiajiList.add(cargoClassify.getId() + "");
                    }
                }
                List<PurchaseClassify> lists = purchaseClassifyService.list(Conds.get().in("parent_id", xiajiList));//查询下级的下级
                if (lists.size() > 0) {
                    for (PurchaseClassify purchaseClassify : lists) {
                        allList.add(classify + " '%"+purchaseClassify.getId() + "%' ");
                    }
                }
            }
            allList = new ArrayList(new HashSet(allList));//去重
            if(goodType.equals(OrderBizType.trade.toString())) {
                allClassifyId = " tg.group_id like '%"+parentId+"%' " + StringUtils.join(allList, " ");
            }else{
                allClassifyId = " pg.group_id like '%"+parentId+"%' " + StringUtils.join(allList, " ");
            }
        }
        return allClassifyId;
    }

    @Override
    public ResultList<PresellGoodVo> getAddGoodPageList(PresellParam param, Paging pg) {

        param.setClassifyId(goodPageTplMgrBizService.getAllClassifyId(param.getClassifyId(),param.getOrderBizType(), param.getApplyType()));

        Map<String, PresellSaleVo> presellSaleVoMap = getPresellSaleMap(param);


        String sql = FreeMarkerHelper.getValueFromTpl("sql/presell/queryAddGoodList.sql", param);
        RowMapper<PresellGoodVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PresellGoodVo.class);
        List<PresellGoodVo> list = jdbcTemplate.query(sql + " limit ?,? ", rowMapper, pg.getPs(), pg.getPn());
        Number total = jdbcTemplate.queryForObject("select count(0) from (" + sql +") nums ",null,Integer.class);
        ResultList<PresellGoodVo> resultList = new ResultList<PresellGoodVo>();
        List<String> groupIdList = new ArrayList<>();

        List<String> groupIdSet = Lists.newArrayList();
        Set<String> groupIdListImg = ListUtils.getIdSet(list,"groupId"); //获取图片
        groupIdSet.addAll(groupIdListImg);
        Map<String, ImageVo> mapImage =cargoImageBizService.getImageByGroup(groupIdSet); //获取图片 //获取图片


        for (PresellGoodVo presellGoodVo : list) {
            if(StringUtils.isNotBlank(presellGoodVo.getPcAlbumId())) {
                groupIdList.add(presellGoodVo.getPcAlbumId());//取到图片groupId
            }
            presellGoodVo.setImage(mapImage.get(presellGoodVo.getGroupId()).getUrl()); //获取图片
            presellGoodVo.setIsValid(true);
            if (presellSaleVoMap.get(presellGoodVo.getId()) != null) {
                PresellSaleVo presellSaleVo = presellSaleVoMap.get(presellGoodVo.getId());
                if (PromotionActivityType.presell.name().equals(presellSaleVo.getType())) { //预售商品
                    if (presellSaleVo.getActivityId().equals(param.getActivityId())) { //当前预售活动
                        presellGoodVo.setFirstAmount(presellSaleVo.getFirstAmount());
                        presellGoodVo.setPresellAmount(presellSaleVo.getPresellAmount());
                        presellGoodVo.setVmCount(presellSaleVo.getVmCount());
                        presellGoodVo.setPresellNum(presellSaleVo.getPresellNum());
                        presellGoodVo.setIsJoin("T");
                    } else {
                        presellGoodVo.setIsValid(false);
                    }
                }
                if (presellSaleVo.getEndTime().getTime() > new Date().getTime()) {
                    presellGoodVo.setIsValid(false);
                }
            }

            if (StringUtils.isNotBlank(param.getSkuIds())) {
                String[] skuIds = param.getSkuIds().split(",");
                Set<String> skuIdSet = new HashSet<String>(Lists.newArrayList(skuIds));
                for (String skuId : skuIdSet) {
                    if (presellGoodVo.getId().equals(skuId)) {
                        presellGoodVo.setIsJoin("T");
                    }
                }
            }


            /*if (param.getPresellGoodList() != null) {//判断是否加入活动
                for (PresellGood presellGood : param.getPresellGoodList()) {
                    if (presellGood != null) {
                        if (presellGoodVo.getId().equals(presellGood.getSkuId())){
                            presellGoodVo.setIsJoin("T");
                            presellGoodVo.setFirstAmount(presellGood.getFirstAmount());
                            presellGoodVo.setPresellAmount(presellGood.getPresellAmount());
                            presellGoodVo.setVmCount(presellGood.getVmCount());
                            presellGoodVo.setPresellNum(presellGood.getPresellNum());
                            break;
                        }
                    }
                }
                if (!"T".equals(presellGoodVo.getIsJoin())&&!"TT".equals(presellGoodVo.getIsJoin())) {
                    presellGoodVo.setIsJoin("F");
                }
            } else {
                if(!presellGoodVo.getIsJoin().equals("TT")) {
                    presellGoodVo.setIsJoin("F");
                }
            }*/
        }
        resultList.setResultList(list);
        resultList.setTotalRecords(total!=null ? total.intValue() : 0);
        return resultList;
    }

    private Map<String, PresellSaleVo> getPresellSaleMap(PresellParam param) {
        String queryPresellSaleSql = FreeMarkerHelper.getValueFromTpl("sql/promotion/presell/queryPresellSale.sql", param);
        List<PresellSaleVo> presellSaleVos = jdbcTemplate.query(queryPresellSaleSql, ParameterizedBeanPropertyRowMapper.newInstance(PresellSaleVo.class));

        return ListUtils.toKeyMap(presellSaleVos, "skuId");
    }

    @Override
    public void savePresellGood(PresellActivity presellActivity) {
        presellGoodService.delete(Conds.get().eq("activity_id",presellActivity.getId()));
        for(PresellGood presellGood:presellActivity.getPresellGoodList()){
            presellGood.setId(IdGenerator.getDefault().nextId() + "");
            presellGood.setActivityId(presellActivity.getId());
            presellGood.setDateCreated(new Date());
            presellGood.setIsValid("T");
            presellGood.setDelFlag("F");
            if(presellActivity.getPresellType().equals(PresellType.second.toString())) {
                presellGood.setRemainingAmount(presellGood.getPresellAmount().subtract(presellGood.getFirstAmount()));
            }
            presellGoodService.insert(presellGood);
        }
    }

    @Override
    public boolean IsCheckAllSkuId(PresellActivity presellActivity) {
        List<String> list = new ArrayList<>();
        for(PresellGood presellGood:presellActivity.getPresellGoodList()){
            list.add(presellGood.getGoodId());
        }
        if(presellActivity.getOrderBizType().equals(OrderBizType.trade.toString())){//当为商品时
            List<TradeGoodSku> TradeGoodSkuList = tradeGoodSkuService.list(Conds.get().in("good_id", list));
            for(TradeGoodSku tradeGoodSku:TradeGoodSkuList){
                boolean flag = false;
                for(PresellGood presellGood:presellActivity.getPresellGoodList()){
                    if((tradeGoodSku.getId()+"").equals(presellGood.getSkuId())){
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    return false;
                }
            }
        }else{//采购
            List<PurchaseGoodsSku> purchaseGoodsSkuList = purchaseGoodsSkuService.list(Conds.get().in("pur_goods_id", list));
            for(PurchaseGoodsSku purchaseGoodsSku:purchaseGoodsSkuList){
                boolean flag = false;
                for(PresellGood presellGood:presellActivity.getPresellGoodList()){
                    if((purchaseGoodsSku.getId()+"").equals(presellGood.getSkuId())){
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public List<PresellGood> getPresellActivityDataList(String idStr) {
        List<PresellGood> list = presellGoodService.list(Conds.get().in("activity_id",  Arrays.asList(idStr.split(","))));
        return list;
    }

    private BaseGoodBizService getGoodService(OrderBizType orderBizType) {
        if(OrderBizType.trade.equals(orderBizType)) {
            return tradeGoodSkuBizService;
        } else {
            return purchaseGoodsSkuBizService;
        }
    }
}
