package pub.makers.shop.promotion.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.collections.SetUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.cargo.entity.vo.ImageGroupVo;
import pub.makers.shop.cargo.service.CargoImageBizService;
import pub.makers.shop.cargo.service.GoodPageTplMgrBizService;
import pub.makers.shop.promotion.entity.ManzengActivity;
import pub.makers.shop.promotion.entity.ManzengActivityApply;
import pub.makers.shop.promotion.entity.ManzengGood;
import pub.makers.shop.promotion.entity.ManzengRule;
import pub.makers.shop.promotion.vo.*;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassify;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSku;
import pub.makers.shop.purchaseGoods.service.PurchaseClassifyBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseClassifyService;
import pub.makers.shop.purchaseGoods.vo.PurchaseClassifyVo;
import pub.makers.shop.tradeGoods.entity.TradeGoodsClassify;
import pub.makers.shop.tradeGoods.service.TradeGoodsClassifyBizService;
import pub.makers.shop.tradeGoods.service.TradeGoodsClassifyService;
import pub.makers.shop.tradeGoods.vo.TradeGoodsClassifyVo;

import java.util.*;

/**
 * Created by dy on 2017/8/17.
 */
@Service(version = "1.0.0")
public class ManzengAdminServiceImpl implements ManzengAdminService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Reference(version = "1.0.0")
    private CargoImageBizService cargoImageBizService;
    @Reference(version = "1.0.0")
    private ManzengBizService manzengBizService;
    @Autowired
    private TradeGoodsClassifyService tradeGoodsClassifyService;
    @Autowired
    private PurchaseClassifyService purchaseClassifyService;
    @Autowired
    private OtherAboutActivityBizService otherAboutActivityBizService;
    @Reference(version = "1.0.0")
    private PurchaseClassifyBizService purchaseClassifyBizService;
    @Reference(version = "1.0.0")
    private TradeGoodsClassifyBizService tradeGoodsClassifyBizService;
    @Autowired
    private GoodPageTplMgrBizService goodPageTplMgrBizService;

    @Override
    public ResultList<ManzengActivityVo> manzengActivityList(ManzengParam manzengParam, Paging pg) {


        if (StringUtils.isNotBlank(manzengParam.getSkuCode()) || StringUtils.isNotBlank(manzengParam.getGoodName())) {
            String queryGoodListSql = FreeMarkerHelper.getValueFromTpl("sql/manzeng/queryGoodList.sql", manzengParam);
            List<ManzengParam> manzengParams = jdbcTemplate.query(queryGoodListSql, ParameterizedBeanPropertyRowMapper.newInstance(ManzengParam.class));
            if (!manzengParams.isEmpty()) {
                List<String> goodSkuIdList = new ArrayList<String>();
                goodSkuIdList.addAll(ListUtils.getIdSet(manzengParams, "goodSkuId"));
                manzengParam.setGoodSkuIdList(goodSkuIdList);
            }
        }

        String querySql = FreeMarkerHelper.getValueFromTpl("sql/manzeng/queryManzengActivityList.sql", manzengParam);
        List<ManzengActivityVo> manzengActivityVos = jdbcTemplate.query(querySql  + "limit ?, ?",
                ParameterizedBeanPropertyRowMapper.newInstance(ManzengActivityVo.class),
                pg.getPs(), pg.getPn());

        Set<String> activityIds = ListUtils.getIdSet(manzengActivityVos, "id");
        Map<String, List<ManzengRuleVo>> manzengRuleVoMap = getManzengRuleVoList(activityIds, manzengParam);
        manzengParam.setActivityIds(StringUtils.join(activityIds, ","));
        Map<String, List<ManzengGoodVo>> manzengGoodMap = getManzengGoodVoList(manzengParam);
        for (ManzengActivityVo manzengActivityVo : manzengActivityVos) {
            manzengActivityVo.setManzengRuleVoList(manzengRuleVoMap.get(manzengActivityVo.getId()));
            manzengActivityVo.setImageUrl(manzengGoodMap.get(manzengActivityVo.getId()) != null && manzengGoodMap.get(manzengActivityVo.getId()).size() > 0 ? manzengGoodMap.get(manzengActivityVo.getId()).get(0).getGoodImgUrl() : "");
            manzengActivityVo.setGoodNum(manzengGoodMap.get(manzengActivityVo.getId()) != null ? manzengGoodMap.get(manzengActivityVo.getId()).size() : 0);
        }

        Long count = jdbcTemplate.queryForObject("select count(0) from (" + querySql +") nums ", Long.class);
        ResultList<ManzengActivityVo> resultList = new ResultList<ManzengActivityVo>();
        resultList.setResultList(manzengActivityVos);
        resultList.setTotalRecords(count.intValue());
        return resultList;
    }

    private Map<String, List<ManzengGoodVo>> getManzengGoodVoList(ManzengParam manzengParam) {
        Map<String, List<ManzengGoodVo>> listMap = new HashMap<String, List<ManzengGoodVo>>();
        if (StringUtils.isBlank(manzengParam.getActivityIds())) {
            return listMap;
        }
        String querySql = FreeMarkerHelper.getValueFromTpl("sql/manzeng/queryManzengGoodList.sql", manzengParam);
        List<ManzengGoodVo> manzengGoodVos = jdbcTemplate.query(querySql, ParameterizedBeanPropertyRowMapper.newInstance(ManzengGoodVo.class));

        for (ManzengGoodVo manzengGoodVo : manzengGoodVos) {
            if (listMap.get(manzengGoodVo.getActivityId()) == null) {
                listMap.put(manzengGoodVo.getActivityId(), new ArrayList<ManzengGoodVo>());
            }
            listMap.get(manzengGoodVo.getActivityId()).add(manzengGoodVo);
        }
        return listMap;
    }

    private Map<String, List<ManzengRuleVo>> getManzengRuleVoList(Set<String> activityIds, ManzengParam manzengParam) {
        Map<String, List<ManzengRuleVo>> listMap = new HashMap<String, List<ManzengRuleVo>>();
        if (activityIds.size() <= 0) {
            return listMap;
        }
        manzengParam.setActivityIds(StringUtils.join(activityIds, ","));
        String querySql = FreeMarkerHelper.getValueFromTpl("sql/manzeng/queryManzengActivityRuleList.sql", manzengParam);
        List<ManzengRuleVo> manzengRuleVoList = jdbcTemplate.query(querySql, ParameterizedBeanPropertyRowMapper.newInstance(ManzengRuleVo.class));

        for (ManzengRuleVo manzengRuleVo : manzengRuleVoList) {
            if (listMap.get(manzengRuleVo.getActivityId()) == null) {
                listMap.put(manzengRuleVo.getActivityId(), new ArrayList<ManzengRuleVo>());
            }
            listMap.get(manzengRuleVo.getActivityId()).add(manzengRuleVo);
        }
        return listMap;
    }


    @Override
    public void saveOrUpdate(ManzengActivityVo manzengActivityVo, long userId) {

        if (StringUtils.isBlank(manzengActivityVo.getId())) { //新增

            ManzengActivity manzengActivity = new ManzengActivity();

            BeanUtils.copyProperties(manzengActivityVo, manzengActivity); //复制属性信息

//            if (StringUtils.isNotBlank(manzengActivityVo.getTag1())) {
//                List<String> imagList = new ArrayList<String>();
//                imagList.add(manzengActivityVo.getTag1());
//                Long groupId = cargoImageBizService.saveGroupImage(imagList, userId + "");
//                manzengActivity.setTag1(groupId + "");
//            }

            manzengActivity.setDateCreated(new Date());
            manzengActivity.setDelFlag(BoolType.F.name());
            manzengActivity.setIsValid(BoolType.T.name());
            manzengActivity.setLastUpdated(new Date());

            manzengActivity = manzengBizService.save(manzengActivity);

            List<ManzengRuleVo> manzengRuleVos = manzengActivityVo.getManzengRuleVoList();
            saveManzengRules(manzengRuleVos, manzengActivity.getId()); //保存优惠规则
        } else { //编辑

            ManzengActivity manzengActivity = manzengBizService.getManzengActivityById(manzengActivityVo.getId());
            BeanUtils.copyProperties(manzengActivityVo, manzengActivity); //复制属性信息
            manzengActivity.setLastUpdated(new Date());

//            if (StringUtils.isNotBlank(manzengActivity.getTag1())) {
//                cargoImageBizService.deleteImgByGroupId(manzengActivity.getTag1());
//            }

//            if (StringUtils.isNotBlank(manzengActivityVo.getTag1())) {
//                List<String> imagList = new ArrayList<String>();
//                imagList.add(manzengActivityVo.getTag1());
//                Long groupId = cargoImageBizService.saveGroupImage(imagList, userId + "");
//                manzengActivity.setTag1(groupId + "");
//            } else {
//                manzengActivity.setTag1("");
//            }

            manzengBizService.save(manzengActivity);
            List<ManzengRuleVo> manzengRuleVos = manzengActivityVo.getManzengRuleVoList();
            saveManzengRules(manzengRuleVos, manzengActivity.getId()); //保存优惠规则
        }

    }

    @Override
    public ManzengActivityVo getManzengActivityInfo(String id) {
        ManzengActivity manzengActivity = manzengBizService.getManzengActivityById(id);
        ManzengActivityVo manzengActivityVo = new ManzengActivityVo();
        BeanUtils.copyProperties(manzengActivity, manzengActivityVo);
        Set<String> activityIds = new HashSet<String>();
        activityIds.add(id);
        ManzengParam manzengParam = new ManzengParam();
        Map<String, List<ManzengRuleVo>> manzengRuleVoMap = getManzengRuleVoList(activityIds, manzengParam);
        manzengActivityVo.setManzengRuleVoList(manzengRuleVoMap.get(manzengActivity.getId()));
        return manzengActivityVo;
    }

    @Override
    public ResultData delManzengRule(ManzengParam manzengParam) {
//        List<ManzengGood> manzengGoods = manzengBizService.getManzengGoodListByRuleIds(manzengParam);
//        if (manzengGoods.size() > 0) {
//            return ResultData.createFail("该活动内容已有商品参加无法删除");
//        }
        if (StringUtils.isBlank(manzengParam.getRuleIds())) {
            return ResultData.createFail("活动内容信息不存在");
        }
        String delSql = "update sp_manzeng_rule set del_flag = 'T', is_valid = 'F' where id in(" + manzengParam.getRuleIds() + ")";
        jdbcTemplate.update(delSql);
        return ResultData.createSuccess();
    }

    @Override
    public ResultData delManzengActivity(ManzengParam manzengParam) {
        String queryManzengRuleSql = "select * from sp_manzeng_rule where activity_id in(" + manzengParam.getActivityIds() + ")";
        List<ManzengRuleVo> manzengRuleVos = jdbcTemplate.query(queryManzengRuleSql, ParameterizedBeanPropertyRowMapper.newInstance(ManzengRuleVo.class));
        if (manzengRuleVos.size() > 0) {
            Set<String> manzengRuleIds = ListUtils.getIdSet(manzengRuleVos, "id");
            manzengParam.setRuleIds(StringUtils.join(manzengRuleIds, ","));
            ResultData resultData = delManzengRule(manzengParam);
            if (!resultData.isSuccess()) {
                return resultData;
            }
        }
        String delSql = "update sp_manzeng_activity set del_flag = 'T', is_valid = 'F' where id in(" + manzengParam.getActivityIds() + ")";
        jdbcTemplate.update(delSql);
        return ResultData.createSuccess();
    }

    @Override
    public ResultData updateValidManzengActivity(ManzengParam manzengParam) {
        String delSql = "update sp_manzeng_activity set is_valid = '"+ manzengParam.getIsValid() +"' where id in(" + manzengParam.getActivityIds() + ")";
        jdbcTemplate.update(delSql);
        return ResultData.createSuccess();
    }

    @Override
    public ManzengActivityApplyVo manzengApplyInfo(ManzengParam manzengParam) {
        String getByApplyInfoSql = "select * from sp_manzeng_activity_apply where activity_id = ?";
        if (StringUtils.isBlank(manzengParam.getActivityIds())) {
            return null;
        }
        List<ManzengActivityApplyVo> manzengActivityApplyVos = jdbcTemplate.query(getByApplyInfoSql,
                ParameterizedBeanPropertyRowMapper.newInstance(ManzengActivityApplyVo.class),
                manzengParam.getActivityIds());
        if (!(manzengActivityApplyVos.size() > 0)) {
            return null;
        }

        ManzengActivityApplyVo manzengActivityApplyVo = manzengActivityApplyVos.get(0);
        if ("classify".equals(manzengActivityApplyVo.getApplyScope())) {
            List<String> classifyIdList = Arrays.asList(manzengActivityApplyVo.getClassifyIds().split(","));
            if (OrderBizType.trade.equals(manzengParam.getOrderBizType())) {
                List<TradeGoodsClassifyVo> tradeGoodsClassifyVos = tradeGoodsClassifyBizService.findAllAndParent(classifyIdList);
                manzengActivityApplyVo.setClassifyIds(StringUtils.join(ListUtils.getIdSet(tradeGoodsClassifyVos, "id"), ","));
            } else {
                List<PurchaseClassifyVo> purchaseClassifyVos = purchaseClassifyBizService.findAllAndParent(classifyIdList, null);
                manzengActivityApplyVo.setClassifyIds(StringUtils.join(ListUtils.getIdSet(purchaseClassifyVos, "id"), ","));

            }
        }
        return manzengActivityApplyVo;
    }


    @Override
    public void applyManzengRange(ManzengActivityApplyVo manzengActivityApplyVo) {
        String delApplyRangeSql = "delete from sp_manzeng_activity_apply where activity_id = ?";
        //删除原有的应用范围
        jdbcTemplate.update(delApplyRangeSql, manzengActivityApplyVo.getActivityId());
        ManzengActivityApply manzengActivityApply = new ManzengActivityApply();
        BeanUtils.copyProperties(manzengActivityApplyVo, manzengActivityApply);
        manzengActivityApply.setActivityId(Long.parseLong(manzengActivityApplyVo.getActivityId()));
        manzengActivityApply.setId(IdGenerator.getDefault().nextId());
        manzengActivityApply.setLastUpdated(new Date());
        manzengActivityApply.setDateCreated(new Date());
        manzengActivityApply.setIsValid(BoolType.T.name());
        manzengActivityApply.setDelFlag(BoolType.F.name());
        manzengBizService.saveManzengApply(manzengActivityApply);

    }

    @Override
    public List<ManzengRuleVo> getManzengRuleList(ManzengParam manzengParam) {
        Set<String> activityIdSet = new HashSet<String>();
        activityIdSet.add(manzengParam.getActivityIds());
        return getManzengRuleVoList(activityIdSet, manzengParam).get(manzengParam.getActivityIds());
    }

    @Override
    public ResultList<ActivityGoodVo> manzengGoodsList(ManzengParam param, Paging pg) {

        param.setClassifyId(goodPageTplMgrBizService.getAllClassifyId(param.getClassifyId(), param.getOrderBizType().name(), param.getApplyType()));
        String sql = FreeMarkerHelper.getValueFromTpl("sql/manzeng/queryGiftGoodlist.sql", param);
        List<ActivityGoodVo> activityGoodVos = jdbcTemplate.query(sql + "  limit ?,? ",
                ParameterizedBeanPropertyRowMapper.newInstance(ActivityGoodVo.class),
                pg.getPs(), pg.getPn());

        Set<String> goodIdSet = ListUtils.getIdSet(activityGoodVos, "id");
        List<String> imgGroupIdList = new ArrayList<String>();
        imgGroupIdList.addAll(ListUtils.getIdSet(activityGoodVos, "image"));

        Map<String, ActivityGoodVo> activityGoodVoMap = getActivityGoodRule(goodIdSet, param);
        Map<String, ImageGroupVo> imageGroupVoMap = cargoImageBizService.getImageGroup(imgGroupIdList);
        ManZenAndPresellVo manZenAndPresellVo = new ManZenAndPresellVo();
        manZenAndPresellVo.setOrderBizType(param.getOrderBizType().name());
        manZenAndPresellVo.setType("manzeng");
        manZenAndPresellVo.setOtherActivityIds(param.getActivityIds());
        manZenAndPresellVo.setGoodSkuIds(StringUtils.join(ListUtils.getIdSet(activityGoodVos, "id"), ","));
        Map<String, List<ManZenAndPresellVo>> manzengActivityMap = otherAboutActivityBizService.getManzengPresellGoodVoListMap(manZenAndPresellVo);
        for (ActivityGoodVo activityGoodVo : activityGoodVos) {
            if (activityGoodVoMap.get(activityGoodVo.getId()) != null) {
                activityGoodVo.setRuleId(activityGoodVoMap.get(activityGoodVo.getId()).getRuleId());
            } else {
                activityGoodVo.setIsJoin("未关联");
            }
            if (StringUtils.isNotBlank(activityGoodVo.getImage())
                    && imageGroupVoMap.get(activityGoodVo.getImage()) != null
                    && imageGroupVoMap.get(activityGoodVo.getImage()).getImages() != null) {
                activityGoodVo.setImage(imageGroupVoMap.get(activityGoodVo.getImage()).getImages().get(0).getUrl());
            }
            activityGoodVo.setManZenAndPresellVoList(manzengActivityMap.get(activityGoodVo.getId()));
        }

        Integer total = jdbcTemplate.queryForObject("select count(0) from (" + sql +") nums ", null, Integer.class);
        ResultList<ActivityGoodVo> resultList = new ResultList<ActivityGoodVo>();
        resultList.setResultList(activityGoodVos);
        resultList.setTotalRecords(total);
        return resultList;
    }

    /**
     * 获取已经关联赠品的商品
     * @param goodIdSet
     * @param param
     * @return
     */
    private Map<String, ActivityGoodVo> getActivityGoodRule(Set<String> goodIdSet, ManzengParam param) {
        Map<String, ActivityGoodVo> activityGoodVoMap = new HashMap<String, ActivityGoodVo>();

        if (goodIdSet.size() <= 0) {
            return activityGoodVoMap;
        }
        String queryRuleIdSql = "SELECT smg.good_sku_id as id, group_concat(smg.rule_id) as ruleId FROM sp_manzeng_good smg WHERE smg.good_sku_id IN (" + StringUtils.join(goodIdSet, ",") + ") AND smg.activity_id=? AND smg.del_flag!='T' GROUP BY good_sku_id;";
        List<ActivityGoodVo> tempActivityGoodVoList = jdbcTemplate.query(queryRuleIdSql,
                ParameterizedBeanPropertyRowMapper.newInstance(ActivityGoodVo.class), param.getActivityIds());
        for (ActivityGoodVo activityGoodVo : tempActivityGoodVoList) {
            activityGoodVoMap.put(activityGoodVo.getId(), activityGoodVo);
        }
        return activityGoodVoMap;
    }


    @Override
    public void addManzengGiftGood(ManzengParam manzengParam) {
        List<ManzengGoodVo> manzengGoodVos = manzengParam.getManzengGoodVos();
        for (ManzengGoodVo manzengGoodVo : manzengGoodVos) {
            ManzengGood manzengGood = new ManzengGood();
            BeanUtils.copyProperties(manzengGoodVo, manzengGood);
            manzengGood.setId(IdGenerator.getDefault().nextId() + "");
            manzengGood.setActivityId(manzengGoodVo.getActivityId());
            manzengGood.setIsValid(BoolType.T.name());
            manzengGood.setDelFlag(BoolType.F.name());
            manzengGood.setDateCreated(new Date());
            manzengGood.setLastUpdated(new Date());
            manzengBizService.saveManzengGood(manzengGood);
        }
    }

    @Override
    public void delManzengGood(ManzengParam manzengParam) {
        String delManzengGoodSql = "update sp_manzeng_good set del_flag = 'T', is_valid = 'F' where rule_id = ? and good_sku_id in (" + manzengParam.getGoodSkuId() + ")";
        jdbcTemplate.update(delManzengGoodSql, manzengParam.getRuleIds());
    }

    @Override
    public List<String> getGoodSkuIdByGoodId(String goodId, String orderBizType) {
        String querySkuIdSql = "";
        if (OrderBizType.purchase.name().equals(orderBizType)) {
            querySkuIdSql = "select * from purchase_goods_sku where pur_goods_id in ("+ goodId +") and status = 1 and del_flag != 'T'";
        } else {
            querySkuIdSql = "select * from trade_good_sku where good_id in ("+ goodId +") and status = 1 and del_flag != 'T'";
        }
        List<PurchaseGoodsSku> skuVos = jdbcTemplate.query(querySkuIdSql, ParameterizedBeanPropertyRowMapper.newInstance(PurchaseGoodsSku.class));
        List<String> skuIds = new ArrayList<String>();
        skuIds.addAll(ListUtils.getIdSet(skuVos, "id"));
        return skuIds;
    }

    public String getAllClassifyId(String parentId, String goodType){
        List<String> allList = new ArrayList<>();
        String allClassifyId = "";
        if(StringUtils.isNotBlank(parentId)) {
            if(goodType.equals(OrderBizType.trade.toString())) {
                String classify = "or find_in_set(%s, tg.group_id) ";
                List<TradeGoodsClassify> list = tradeGoodsClassifyService.list(Conds.get().eq("parent_id", parentId));
                List<String> xiajiList = new ArrayList<>();//有下级的分类
                if (list.size() > 0) {
                    for (TradeGoodsClassify cargoClassify : list) {
                        allList.add(classify.replace("%s", cargoClassify.getId() + ""));
                        xiajiList.add(cargoClassify.getId() + "");
                    }
                }
                List<TradeGoodsClassify> lists = tradeGoodsClassifyService.list(Conds.get().in("parent_id", xiajiList));//查询下级的下级
                if (lists.size() > 0) {
                    for (TradeGoodsClassify tradeGoodsClassify : lists) {
//                        allList.add(classify + " '%"+tradeGoodsClassify.getId() + "%' ");
                        allList.add(classify.replace("%s", tradeGoodsClassify.getId() + ""));
                    }
                }
            }else{
                List<PurchaseClassify> list = purchaseClassifyService.list(Conds.get().eq("parent_id", parentId));
                List<String> xiajiList = new ArrayList<>();//有下级的分类
                String classify = "or find_in_set(%s, pg.group_id) ";
                if (list.size() > 0) {
                    for (PurchaseClassify cargoClassify : list) {
//                        allList.add(classify + " '%"+cargoClassify.getId() + "%' ");
                        allList.add(classify.replace("%s", cargoClassify.getId() + ""));
                        xiajiList.add(cargoClassify.getId() + "");
                    }
                }
                List<PurchaseClassify> lists = purchaseClassifyService.list(Conds.get().in("parent_id", xiajiList));//查询下级的下级
                if (lists.size() > 0) {
                    for (PurchaseClassify purchaseClassify : lists) {
//                        allList.add(classify + " '%"+purchaseClassify.getId() + "%' ");
                        allList.add(classify.replace("%s", purchaseClassify.getId() + ""));
                    }
                }
            }
            allList = new ArrayList(new HashSet(allList));//去重
            if(goodType.equals(OrderBizType.trade.toString())) {
                allClassifyId = " (find_in_set("+ parentId +", tg.group_id) " + StringUtils.join(allList, " ") + ")";
            }else{
                allClassifyId = " (find_in_set("+ parentId +", pg.group_id) " + StringUtils.join(allList, " ") + ")";
            }
        }
        return allClassifyId;
    }

    private void saveManzengRules(List<ManzengRuleVo> manzengRuleVos, String activityId) {

        for (ManzengRuleVo manzengRuleVo : manzengRuleVos) {
            if (StringUtils.isBlank(manzengRuleVo.getId())) {

                ManzengRule manzengRule = new ManzengRule();
                BeanUtils.copyProperties(manzengRuleVo, manzengRule);
                manzengRule.setDelFlag(BoolType.F.name());
                manzengRule.setIsValid(BoolType.T.name());
                manzengRule.setDateCreated(new Date());
                manzengRule.setLastUpdated(new Date());
                manzengRule.setActivityId(activityId);
                manzengBizService.saveManzengRule(manzengRule);
            } else {
                ManzengRule manzengRule = manzengBizService.getManzengRuleById(manzengRuleVo.getId());
                BeanUtils.copyProperties(manzengRuleVo, manzengRule);
                manzengRule.setLastUpdated(new Date());
                manzengBizService.saveManzengRule(manzengRule);
            }

        }
    }
}
