package pub.makers.shop.promotion.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.lantu.base.util.ListUtils;
import com.sun.tools.classfile.Opcode;
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
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.cargo.entity.vo.ImageGroupVo;
import pub.makers.shop.cargo.service.CargoImageBizService;
import pub.makers.shop.cargo.service.CargoImageMgrBizService;
import pub.makers.shop.cargo.service.GoodPageTplAdminServiceImpl;
import pub.makers.shop.cargo.service.GoodPageTplMgrBizService;
import pub.makers.shop.promotion.entity.PresellGood;
import pub.makers.shop.promotion.entity.SaleActivity;
import pub.makers.shop.promotion.entity.SaleActivityGood;
import pub.makers.shop.promotion.pojo.SaleActivityGoodPram;
import pub.makers.shop.promotion.vo.ManZenAndPresellVo;
import pub.makers.shop.promotion.vo.PresellGoodVo;
import pub.makers.shop.promotion.vo.PresellParam;
import pub.makers.shop.promotion.vo.SaleActivityGoodVo;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassify;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSku;
import pub.makers.shop.purchaseGoods.service.PurchaseClassifyService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuService;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.tradeGoods.entity.TradeGoodSku;
import pub.makers.shop.tradeGoods.entity.TradeGoodsClassify;
import pub.makers.shop.tradeGoods.service.TradeGoodSkuBizService;
import pub.makers.shop.tradeGoods.service.TradeGoodSkuService;
import pub.makers.shop.tradeGoods.service.TradeGoodsClassifyService;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by devpc on 2017/8/19.
 */
@Service(version="1.0.0")
public class SaleActivityGoodAdminServiceImpl implements SaleActivityGoodBizService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SaleActivityGoodService saleActivityGoodService;

    @Autowired
    private SaleActivityService saleActivityService;

    @Reference(version = "1.0.0")
    private CargoImageBizService cargoImageBizService;

    @Autowired
    private PurchaseClassifyService purchaseClassifyService;
    @Autowired
    private TradeGoodsClassifyService tradeGoodsClassifyService;

    @Reference(version = "1.0.0")
    private TradeGoodSkuBizService tradeGoodSkuBizService;
    @Reference(version = "1.0.0")
    private PurchaseGoodsSkuBizService purchaseGoodsSkuBizService;
    @Autowired
    private OtherAboutActivityAdminServiceImpl otherAboutActivityAdminService;

    @Autowired
    private TradeGoodSkuService tradeGoodSkuService;
    @Autowired
    private PurchaseGoodsSkuService purchaseGoodsSkuService;
    @Autowired
    private CargoImageMgrBizService cargoImageMgrBizService;
    @Autowired
    private GoodPageTplMgrBizService goodPageTplMgrBizService;
    /***
     * 查询活动
     * @param saleActivityGoodPram
     * @param pg
     * @return
     */
    @Override
    public ResultList<SaleActivityGoodVo> getSaleActivityGoodsList(SaleActivityGoodPram saleActivityGoodPram ,Paging pg) {
//
        Date date = new Date();
        SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd");
        String creatDate = matter1.format(date);
        saleActivityGoodPram.setNowDate(creatDate);
        String sql = FreeMarkerHelper.getValueFromTpl("sql/promotion/presell/querySaleActivityGoodList.sql", saleActivityGoodPram);

        RowMapper<SaleActivityGoodVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(SaleActivityGoodVo.class);
        List<SaleActivityGoodVo> list = jdbcTemplate.query(sql + " limit ?,? " ,rowMapper,pg.getPs(),pg.getPn());

        Number total = jdbcTemplate.queryForObject("select count(0) from (" + sql +") nums ",null,Integer.class);
        ResultList<SaleActivityGoodVo> resultList = new ResultList<SaleActivityGoodVo>();
        resultList.setResultList(list);
        resultList.setTotalRecords(total!=null?total.intValue():0);
        return resultList;
    }

    /**
     * 启用或复用
     * @param saleActivityGoodPram
     * @return
     */
    @Override
    public Map<String, Object> updateIsValue(SaleActivityGoodPram saleActivityGoodPram) {

        String[] ids = saleActivityGoodPram.getIds().split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            for (int i = 0; i < ids.length; i ++ ) {
                saleActivityService.update(Update.byId(ids[i]).set("is_valid",saleActivityGoodPram.getIsValid()));
            }
            map.put("success",true);
        }catch (Exception e) {
            map.put("success","失败");
            map.put("erro",e.getMessage());
        }
        return map;
    }

    /**
     * 增加或修改活动
     * @param saleActivity
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> updateOrAddSaleActivityGood(SaleActivity saleActivity,String userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Date date = new Date();
        SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd");
        String creatDate = matter1.format(date);
       // Date date1 = new Date(creatDate);

        try {
            if ( saleActivity.getId() != null &&  !"".equals(saleActivity.getId())) {
                saleActivity.setLastUpdated(matter1.parse(creatDate));
                /*if (saleActivity.getTag1() != null && !"".equals(saleActivity.getTag1())) {
                    Long groupId = cargoImageBizService.saveGroupImage(Arrays.asList(saleActivity.getTag1().split(",")), userId);
                    saleActivity.setTag1(String.valueOf(groupId));
                }*/
                    saleActivityService.update(saleActivity);
                }else {
                /*if (saleActivity.getTag1() != null && !"".equals(saleActivity.getTag1())) {
                    Long groupId = cargoImageBizService.saveGroupImage(Arrays.asList(saleActivity.getTag1().split(",")), userId);
                    saleActivity.setTag1(String.valueOf(groupId));
                }*/
                    saleActivity.setDelFlag("F");
                    String id =  IdGenerator.getDefault().nextId() + "";
                    saleActivity.setId(id);
                    saleActivity.setIsValid("T");
                    saleActivity.setLastUpdated(matter1.parse(creatDate));
                    saleActivity.setDateCreated(matter1.parse(creatDate));
                    saleActivity.setCreateBy(userId);
                    saleActivityService.insert(saleActivity);
            }
            map.put("success",true);
        }catch (Exception e) {
            map.put("success","失败");
            map.put("erro",e.getMessage());
        }
        return map;
    }

    /***
     * 删除活动
     * @param saleActivityGoodPram
     * @return
     */
    @Override
    public Map<String, Object> delSaleActivityGood(SaleActivityGoodPram saleActivityGoodPram) {

        String[] ids = saleActivityGoodPram.getIds().split(",");
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            for (int i = 0 ; i < ids.length ; i++) {
                List<SaleActivityGood> list = saleActivityGoodService.list(Conds.get().eq("activity_id",ids[i]));
                if (list != null && list.size() > 0) {
                    for (SaleActivityGood saleActivityGood : list) {
                        saleActivityGoodService.update(Update.byId(saleActivityGood.getId()).set("del_flag","T"));
                    }
                }
                saleActivityService.update(Update.byId(ids[i]).set("del_flag","T"));
            }
            map.put("success",true);
        }catch (Exception e) {
            map.put("success","失败");
            map.put("erro",e.getMessage());
        }
        return map;
    }
    @Override
    public Map<String,Object> getSaleActivityById(String id){
        Map<String,Object> map = new HashMap<String,Object>();
        SaleActivity saleActivity = saleActivityService.getById(id);
        List<SaleActivityGood> list = saleActivityGoodService.list(Conds.get().eq("activity_id",saleActivity.getId()).eq("del_flag","F"));
       /* if (saleActivity.getTag1() != null && !"".equals(saleActivity.getTag1())){
            ImageGroupVo imageGroupVo = cargoImageBizService.getImageGroup(saleActivity.getTag1());
            if (imageGroupVo != null) {
                saleActivity.setTag1(imageGroupVo.getImages().get(0).getUrl());
            }
        }*/
        SaleActivityGoodVo saleActivityGoodVo = new SaleActivityGoodVo(saleActivity);
        if ( !list.isEmpty()) {
            saleActivityGoodVo.setSaleActivityGoodList(list);
        }
        map.put("success",saleActivityGoodVo);
        return map;
    }

    /**
     * 获取关联商品列表
     * @param param
     * @param pg
     * @return
     */
    @Override
    public ResultList<PresellGoodVo> getAddGoodPageList(PresellParam param, Paging pg) {
        param.setClassifyId(goodPageTplMgrBizService.getAllClassifyId(param.getClassifyId(), param.getOrderBizType(), param.getApplyType()));
        String sql = FreeMarkerHelper.getValueFromTpl("sql/promotion/presell/queryAddSaleGood.sql", param);
        RowMapper<PresellGoodVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PresellGoodVo.class);
        List<PresellGoodVo> list = jdbcTemplate.query(sql + " limit ?,? ", rowMapper, pg.getPs(), pg.getPn());
        Number total = jdbcTemplate.queryForObject("select count(0) from (" + sql + ") nums ", null, Integer.class);
        ResultList<PresellGoodVo> resultList = new ResultList<PresellGoodVo>();

        List<String> groupIdSet = Lists.newArrayList(); //转换集合
        Set<String> groupIdList = ListUtils.getIdSet(list,"groupId"); //获取图片
        groupIdSet.addAll(groupIdList);
       /* Map<String,Object> map = new HashMap<String,Object>();
        RowMapper<SaleActivityGood> saleMapper = ParameterizedBeanPropertyRowMapper.newInstance(SaleActivityGood.class);*/

        //参加的活动 //查询关于商品参加过得活动
        ManZenAndPresellVo manZenAndPresellVo = new ManZenAndPresellVo();
        manZenAndPresellVo.setOrderBizType(param.getOrderBizType());

        List<String> skuidList = new ArrayList<String>();  //得到skuid 的集合；sql用in 的方法。查询一次就好
        for (PresellGoodVo presellGoodVo : list) {
            skuidList.add(presellGoodVo.getId());
        }

       Map<String, ImageVo> mapImage =cargoImageBizService.getImageByGroup(groupIdSet); //获取图片

        if (skuidList != null && !skuidList.isEmpty()){
            manZenAndPresellVo.setGoodSkuIdList(skuidList);
            manZenAndPresellVo.setGoodSkuIds(StringUtils.join(skuidList,","));
        }
        Map<String,List<ManZenAndPresellVo>> resultMap = otherAboutActivityAdminService.getManzengPresellGoodVoListMap(manZenAndPresellVo);

        for (PresellGoodVo presellGoodVo : list) {

            presellGoodVo.setImage(mapImage.get(presellGoodVo.getGroupId()).getUrl()); //获取图片


            presellGoodVo.setList(resultMap.get(presellGoodVo.getId()));  //查询关于商品参加过得活动


            if (param.getSaleActivityGoodList() != null) {//判断是否加入活动
                for (SaleActivityGood saleActivityGood : param.getSaleActivityGoodList()) {
                    if (saleActivityGood != null) {
                        if (presellGoodVo.getId().equals(saleActivityGood.getGoodSkuId())){
                            presellGoodVo.setIsJoin("T");
                            presellGoodVo.setSaleType(saleActivityGood.getSaleType());//打折类型
                            presellGoodVo.setDisCount(saleActivityGood.getDiscount());//打折数
                            presellGoodVo.setActivityMoney(saleActivityGood.getActivityPrice());
                            presellGoodVo.setVmNum(saleActivityGood.getVmNum());
                            presellGoodVo.setMaxNum(saleActivityGood.getMaxNum());
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
            }
        }
        resultList.setResultList(list);
        resultList.setTotalRecords(total!=null?total.intValue():0);
        return resultList;
    }

    public String getAllClassifyId(String parentId,String goodType){
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


    //获得相关商品
    @Override
    public SaleActivity getSaleActivityAndGoodByActivityId (String id) {
        SaleActivity saleActivity = saleActivityService.get(Conds.get().eq("id", id));
        List<SaleActivityGood> saleActivityGoodList = saleActivityGoodService.list(Conds.get().eq("activity_id", id));
        saleActivity.setSaleActivityGoodList(saleActivityGoodList);
        return saleActivity;
    }

    //保存saveSaleActivityGood
    @Override
    public void saveSaleActivityGood (SaleActivity saleActivity) {
        saleActivityGoodService.delete(Conds.get().eq("activity_id", saleActivity.getId()));
        for (SaleActivityGood saleActivityGood : saleActivity.getSaleActivityGoodList()) {
            saleActivityGood.setId(IdGenerator.getDefault().nextId() + "");
            saleActivityGood.setActivityId(saleActivity.getId());
            saleActivityGood.setIsValid("T");
            saleActivityGood.setDelFlag("F");
            saleActivityGood.setDateCreated(new Date());
            saleActivityGood.setLastUpdated(new Date());
            saleActivityGoodService.insert(saleActivityGood);
        }
    }

    //判断有没有这个tradegoodskuid  如果有这个id的话就添加
    @Override
    public boolean IsCheckAllSkuId (SaleActivity saleActivity) {
        List<String> list = new ArrayList<String>();
        for (SaleActivityGood saleActivityGood : saleActivity.getSaleActivityGoodList()) {
            list.add(saleActivityGood.getGoodId());
        }
        if (saleActivity.getSaleActivityGoodList().isEmpty()){   //如果没有商品（或者清空商品）的话也可以保存
            return true;
        }

        if (saleActivity.getOrderBizType().equals(OrderBizType.trade.toString())){  //如果是商品的话
            List<TradeGoodSku> tradeGoodSkuList = tradeGoodSkuService.list(Conds.get().in("good_id", list));
            boolean flag = true;
            for (SaleActivityGood saleActivityGood : saleActivity.getSaleActivityGoodList()){
                for (TradeGoodSku tradeGoodSku : tradeGoodSkuList) {
                    if ((tradeGoodSku.getId()+"").equals(saleActivityGood.getGoodSkuId())){    //如果sku的id和 goodskuid
                        flag = false;
                        break;
                    }
                }
                if (!flag) {  //如果有了sku 关联的商品  flag=false；执行，返回true  可加入
                    return true;
                }
            }
        }else {                                                //采购
            List<PurchaseGoodsSku> purchaseGoodsSkuList = purchaseGoodsSkuService.list(Conds.get().in("pur_goods_id", list));
            boolean flag = true;
            for (SaleActivityGood saleActivityGood : saleActivity.getSaleActivityGoodList()) {
                for (PurchaseGoodsSku purchaseGoodsSku : purchaseGoodsSkuList) {
                    if ((purchaseGoodsSku.getId()+"").equals(saleActivityGood.getGoodSkuId())){
                        flag = false;
                        break;
                    }
                }
                if (!flag) {
                    return true;
                }

            }

        }
        return false;
    }


}
