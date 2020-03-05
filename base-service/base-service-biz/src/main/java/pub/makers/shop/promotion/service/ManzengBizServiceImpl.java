package pub.makers.shop.promotion.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.baseGood.service.BaseGoodBizService;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.promotion.entity.ManzengActivity;
import pub.makers.shop.promotion.entity.ManzengActivityApply;
import pub.makers.shop.promotion.entity.ManzengGood;
import pub.makers.shop.promotion.entity.ManzengRule;
import pub.makers.shop.promotion.pojo.ManzengQuery;
import pub.makers.shop.promotion.vo.ManzengActivityVo;
import pub.makers.shop.promotion.vo.ManzengGoodVo;
import pub.makers.shop.promotion.vo.ManzengParam;
import pub.makers.shop.promotion.vo.ManzengRuleVo;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuBizService;
import pub.makers.shop.tradeGoods.service.TradeGoodSkuBizService;

import java.util.*;

/**
 * Created by dy on 2017/8/22.
 */
@Service(version = "1.0.0")
public class ManzengBizServiceImpl implements ManzengBizService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ManzengActivityService manzengActivityService;
    @Autowired
    private ManzengRuleService manzengRuleService;
    @Autowired
    private ManzengGoodService manzengGoodService;
    @Autowired
    private ManzengActivityApplyService manzengActivityApplyService;
    @Autowired
    private PurchaseGoodsSkuBizService purchaseGoodsSkuBizService;
    @Autowired
    private TradeGoodSkuBizService tradeGoodSkuBizService;

    @Override
    public List<ManzengActivityVo> getActivityList(ManzengQuery query) {
        // 查询活动应用范围
        Map<String, Object> data = Maps.newHashMap();
        data.put("query", query);
        String sql = FreeMarkerHelper.getValueFromTpl("sql/promotion/manzeng/getActivityList.sql", data);
        List<ManzengActivityApply> applyList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(ManzengActivityApply.class));
        Map<String, ManzengActivityApply> applyMap = ListUtils.toKeyMap(applyList, "activityId");
        // 查询活动列表
        List<ManzengActivity> activityList = manzengActivityService.list(Conds.get().in("id", applyMap.keySet()).eq("is_valid", BoolType.T.name()).eq("del_flag", BoolType.F.name()).eq("order_biz_type", query.getOrderBizType()).gt("end_time", new Date()).lt("start_time", new Date()));
        // 查询优惠规则列表
        Set<String> activityIds = ListUtils.getIdSet(activityList, "id");
        List<ManzengRule> ruleList = manzengRuleService.list(Conds.get().in("activity_id", activityIds).eq("is_valid", BoolType.T.name()).eq("del_flag", BoolType.F.name()).order("sort desc"));
        // 查询赠品列表
        Set<String> ruleIds = ListUtils.getIdSet(ruleList, "id");
        List<ManzengGood> goodList = manzengGoodService.list(Conds.get().in("rule_id", ruleIds).eq("is_valid", BoolType.T.name()).eq("del_flag", BoolType.F.name()));
        // 商品信息
        BaseGoodBizService baseGoodBizService = getService(query.getOrderBizType());
        List<BaseGoodVo> baseGoodVoList = baseGoodBizService.getGoodSkuListBySkuId(Lists.newArrayList(ListUtils.getIdSet(goodList, "goodSkuId")), query.getStoreLevelId(), query.getClientType());
        Map<String, BaseGoodVo> baseGoodVoMap = ListUtils.toKeyMap(baseGoodVoList, "skuId");
        // 赠品
        ListMultimap<String, ManzengGoodVo> goodVoListMultimap = ArrayListMultimap.create();
        for (ManzengGood manzengGood : goodList) {
            ManzengGoodVo manzengGoodVo = new ManzengGoodVo();
            BeanUtils.copyProperties(manzengGood, manzengGoodVo);
            manzengGoodVo.setGood(baseGoodVoMap.get(manzengGoodVo.getGoodSkuId()));
            goodVoListMultimap.put(manzengGoodVo.getRuleId(), manzengGoodVo);
        }
        // 优惠规则
        ListMultimap<String, ManzengRuleVo> ruleVoListMultimap = ArrayListMultimap.create();
        for (ManzengRule manzengRule : ruleList) {
            ManzengRuleVo manzengRuleVo = new ManzengRuleVo();
            BeanUtils.copyProperties(manzengRule, manzengRuleVo);
            manzengRuleVo.setGoodList(goodVoListMultimap.get(manzengRuleVo.getId()));
            ruleVoListMultimap.put(manzengRuleVo.getActivityId(), manzengRuleVo);
        }
        // 活动
        List<ManzengActivityVo> activityVoList = Lists.newArrayList();
        for (ManzengActivity manzengActivity : activityList) {
            ManzengActivityVo manzengActivityVo = new ManzengActivityVo();
            BeanUtils.copyProperties(manzengActivity, manzengActivityVo);
            manzengActivityVo.setManzengRuleVoList(ruleVoListMultimap.get(manzengActivityVo.getId()));
            manzengActivityVo.setApply(applyMap.get(manzengActivityVo.getId()));
            activityVoList.add(manzengActivityVo);
        }
        return activityVoList;
    }

    private BaseGoodBizService getService(String orderBizType) {
        if (OrderBizType.trade.name().equals(orderBizType)) {
            return tradeGoodSkuBizService;
        } else {
            return purchaseGoodsSkuBizService;
        }
    }

    @Override
    public ManzengActivity save(ManzengActivity manzengActivity) {
        if (StringUtils.isNotBlank(manzengActivity.getId())) {
            manzengActivityService.update(manzengActivity);
        } else {
            manzengActivity.setId(IdGenerator.getDefault().nextId() + "");
            manzengActivityService.insert(manzengActivity);
        }
        return manzengActivity;
    }

    @Override
    public void saveManzengRule(ManzengRule manzengRule) {
        if (StringUtils.isNotBlank(manzengRule.getId())) {
            manzengRuleService.update(manzengRule);
        } else {
            manzengRule.setId(IdGenerator.getDefault().nextId() + "");
            manzengRuleService.insert(manzengRule);
        }
    }

    @Override
    public ManzengRule getManzengRuleById(String id) {
        return manzengRuleService.getById(id);
    }

    @Override
    public ManzengActivity getManzengActivityById(String id) {
        return manzengActivityService.getById(id);
    }

    @Override
    public List<ManzengGood> getManzengGoodListByRuleIds(ManzengParam manzengParam) {
        String[] ruleIds = manzengParam.getRuleIds().split(",");
        return manzengGoodService.list(Conds.get().in("rule_id", Arrays.asList(ruleIds)).ne("del_flag", BoolType.T.name()));
    }

    @Override
    public void saveManzengApply(ManzengActivityApply manzengActivityApply) {
        manzengActivityApplyService.insert(manzengActivityApply);
    }

    @Override
    public void saveManzengGood(ManzengGood manzengGood) {
        manzengGoodService.insert(manzengGood);
    }
}
