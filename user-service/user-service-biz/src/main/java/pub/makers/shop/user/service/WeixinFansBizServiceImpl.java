package pub.makers.shop.user.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Cond;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.DateParseUtil;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.tradeOrder.service.TradeOrderQueryService;
import pub.makers.shop.user.entity.WeixinStoreWeixinuser;
import pub.makers.shop.user.pojo.WeixinFansParam;
import pub.makers.shop.store.service.SubbranchService;
import pub.makers.shop.user.vo.WeixinFansVo;
import pub.makers.shop.user.vo.WeixinStoreWeixinuserVo;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dy on 2017/10/8.
 */
@Service(version = "1.0.0")
public class WeixinFansBizServiceImpl implements WeixinFansBizService {

    @Autowired
    private SubbranchService subbranchService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Reference(version = "1.0.0")
    private TradeOrderQueryService tradeOrderQueryService;
    @Autowired
    private WeixinStoreWeixinuserService weixinStoreWeixinuserService;


    @Override
    public ResultData listByParams(WeixinFansParam params, Paging paging) {

        ValidateUtils.notNull(params.getShopId(), "店铺信息不存在");

        //查询店铺信息
        Subbranch subbranch = subbranchService.getById(params.getShopId());
        List<String> storeIdList = Lists.newArrayList();
        if (!BoolType.T.name().equals(subbranch.getIsSubAccount())) { //查询所有子账号
            List<Subbranch> subbranchList = subbranchService.list(Conds.get().eq("parent_subranch_id", subbranch.getId()).eq("is_valid", BoolType.T.name()).eq("del_flag", BoolType.F.name()));
            Set<String> stringSet = ListUtils.getIdSet(subbranchList, "id");
            storeIdList.addAll(stringSet);
        }
        storeIdList.add(subbranch.getId() + "");
        if (!storeIdList.isEmpty()) {
            params.setShopIds(StringUtils.join(storeIdList, ","));
        }

        String queryWeixinfansListSql = FreeMarkerHelper.getValueFromTpl("sql/user/queryWeixinfansList.sql", params);

        List<WeixinFansVo> weixinFansVos = jdbcTemplate.query(queryWeixinfansListSql + "limit ?, ?", ParameterizedBeanPropertyRowMapper.newInstance(WeixinFansVo.class), paging.getPs(), paging.getPn());

        //查询粉丝所属
        if (!BoolType.T.name().equals(subbranch.getIsSubAccount())) {
            Set<String> weixinFansIdSet = ListUtils.getIdSet(weixinFansVos, "id");
            List<String> weixinFansIdList = new ArrayList<String>();
            weixinFansIdList.addAll(weixinFansIdSet);
            Map<String, Set<String>> fansBelongtoStoreMap = getFansBelongtoStore(subbranch.getId() + "", weixinFansIdList, "type");
            for (WeixinFansVo weixinFansVo : weixinFansVos) {
                weixinFansVo.setBelongTos(fansBelongtoStoreMap.get(weixinFansVo.getId()));
            }
        }


        ResultData resultData = new ResultData();
        if ("subscribeTime".equals(params.getOrderBy())) {
            List<Map<String, Object>> wexinFansVoMapList = fmtFansListBySubscribeTime(weixinFansVos);
            resultData.put("resultList", wexinFansVoMapList);
        } else {
            resultData.put("resultList", weixinFansVos);
        }
        resultData.setSuccess(true);
        resultData.setPageNo(paging.getPageNum());
        resultData.setPageSize(paging.getPageSize());
        return resultData;
    }
    @Override
    public List<String> getSubStoreIds(Subbranch subbranch) {
        List<String> storeIdList = Lists.newArrayList();
        if (!BoolType.T.name().equals(subbranch.getIsSubAccount())) { //查询所有子账号
            List<Subbranch> subbranchList = subbranchService.list(Conds.get().eq("parent_subranch_id", subbranch.getId()).eq("is_valid", BoolType.T.name()).eq("del_flag", BoolType.F.name()));
            Set<String> stringSet = ListUtils.getIdSet(subbranchList, "id");
            storeIdList.addAll(stringSet);
        }
        return storeIdList;
    }

    private Map<String, Set<String>> getFansBelongtoStore(String storeId, List<String> fansIds, String resultType) {

        //查询店铺信息
        Subbranch subbranch = subbranchService.getById(storeId);
        List<String> storeIdList = Lists.newArrayList();
        if (!BoolType.T.name().equals(subbranch.getIsSubAccount())) { //查询所有子账号
            List<Subbranch> subbranchList = subbranchService.list(Conds.get().eq("parent_subranch_id", subbranch.getId()).eq("is_valid", BoolType.T.name()).eq("del_flag", BoolType.F.name()));
            Set<String> stringSet = ListUtils.getIdSet(subbranchList, "id");
            storeIdList.addAll(stringSet);
        }
        storeIdList.add(subbranch.getId() + "");
        Map<String, String> params = new HashMap<String, String>();
        params.put("shopIds", StringUtils.join(storeIdList, ","));
        params.put("fansIds", StringUtils.join(fansIds, ","));

        String queryFansListByFansIdsAndShopIds = FreeMarkerHelper.getValueFromTpl("sql/user/queryFansListByFansIdsAndShopIds.sql", params);

        List<WeixinStoreWeixinuserVo> storeWeixinuserVos = jdbcTemplate.query(queryFansListByFansIdsAndShopIds,
                ParameterizedBeanPropertyRowMapper.newInstance(WeixinStoreWeixinuserVo.class));


        Map<String, Set<String>> resultMap = new HashMap<String, Set<String>>();

        for (WeixinStoreWeixinuserVo storeWeixinuserVo : storeWeixinuserVos) {
            if (resultMap.get(storeWeixinuserVo.getWeixinuserId() + "") == null) {
                resultMap.put(storeWeixinuserVo.getWeixinuserId() + "", new HashSet<String>());
            }
            if ("type".equals(resultType)) {
                if (storeWeixinuserVo.getStoreId().equals(Long.parseLong(storeId))) {
                    resultMap.get(storeWeixinuserVo.getWeixinuserId() + "").add("主账号");
                } else {
                    resultMap.get(storeWeixinuserVo.getWeixinuserId() + "").add("子账号");
                }
            } else {
                if (!storeWeixinuserVo.getStoreId().equals(Long.parseLong(storeId)) && !BoolType.T.name().equals(subbranch.getIsSubAccount())) {
                    resultMap.get(storeWeixinuserVo.getWeixinuserId() + "").add(storeWeixinuserVo.getStoreName());
                }
            }
        }

        return resultMap;
    }

    /**
     * 格式化关注时间返回结构
     * @param weixinFansVos
     * @return
     */
    private List<Map<String, Object>> fmtFansListBySubscribeTime(List<WeixinFansVo> weixinFansVos) {

        Map<String, List<WeixinFansVo>> weixinFansMap = new HashMap<String, List<WeixinFansVo>>();
        for (WeixinFansVo weixinFansVo : weixinFansVos) {
            if (weixinFansMap.get(DateParseUtil.getYearMoth(weixinFansVo.getDateCreated())) == null) {
                weixinFansMap.put(DateParseUtil.getYearMoth(weixinFansVo.getDateCreated()), new ArrayList<WeixinFansVo>());
            }
            weixinFansMap.get(DateParseUtil.getYearMoth(weixinFansVo.getDateCreated())).add(weixinFansVo);
        }

        List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();

        for (Map.Entry<String, List<WeixinFansVo>> entry : weixinFansMap.entrySet()) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            String yearMonth = entry.getKey();
            if (Integer.parseInt(yearMonth.substring(0, 4)) < DateParseUtil.getYear(new Date())) { //比较年份
                resultMap.put("time", yearMonth.substring(0, 4) + "年" + yearMonth.substring(4, yearMonth.length()) + "月");
            } else {
                resultMap.put("time", yearMonth.substring(4, yearMonth.length()) + "月");
            }
            resultMap.put("weixinFansList", entry.getValue());

            resultMapList.add(resultMap);
        }


        return resultMapList;
    }

    @Override
    public ResultData getFansDetail(WeixinFansParam params) {
        String getWeixinFansDetailSql = FreeMarkerHelper.getValueFromTpl("sql/user/getWeixinFansDetail.sql", params);
        WeixinStoreWeixinuserVo weixinuserVo = jdbcTemplate.queryForObject(getWeixinFansDetailSql, ParameterizedBeanPropertyRowMapper.newInstance(WeixinStoreWeixinuserVo.class));

        List<String> weixinFansIdList = Lists.newArrayList();
        weixinFansIdList.add(params.getFansId());
        Map<String, Set<String>> fansBelongtoStoreMap = getFansBelongtoStore(params.getShopId() + "", weixinFansIdList, "name");
        if (fansBelongtoStoreMap.get(weixinuserVo.getId()) != null) {
            weixinuserVo.setStoreName(StringUtils.join(fansBelongtoStoreMap.get(weixinuserVo.getId()), ","));
        }

        ResultData resultData = new ResultData();
        resultData.setSuccess(true);
        resultData.put("fansDetail", weixinuserVo);
        return resultData;
    }

    @Override
    public ResultData orderRecordInfo(WeixinFansParam weixinFansParam) {
        String getOrderRecordInfoSql = "";
        //交易笔数
        weixinFansParam.setQueryType("totalNum");
        getOrderRecordInfoSql = FreeMarkerHelper.getValueFromTpl("sql/user/getOrderRecordInfo.sql", weixinFansParam);
        Number totalNum = jdbcTemplate.queryForObject(getOrderRecordInfoSql, Integer.class);
        //最近购买日期
        weixinFansParam.setQueryType("recentBuyDate");
        getOrderRecordInfoSql = FreeMarkerHelper.getValueFromTpl("sql/user/getOrderRecordInfo.sql", weixinFansParam);
        List<String> recentBuyDate = jdbcTemplate.query(getOrderRecordInfoSql, ParameterizedBeanPropertyRowMapper.newInstance(String.class));
        //交易总额
        weixinFansParam.setQueryType("dealTotalAmount");
        getOrderRecordInfoSql = FreeMarkerHelper.getValueFromTpl("sql/user/getOrderRecordInfo.sql", weixinFansParam);
        BigDecimal dealTotalAmount = jdbcTemplate.queryForObject(getOrderRecordInfoSql, BigDecimal.class);
        //交易次数
        weixinFansParam.setQueryType("dealNum");
        getOrderRecordInfoSql = FreeMarkerHelper.getValueFromTpl("sql/user/getOrderRecordInfo.sql", weixinFansParam);
        Number dealNum = jdbcTemplate.queryForObject(getOrderRecordInfoSql, Integer.class);
        //购买件数
        weixinFansParam.setQueryType("buyNum");
        getOrderRecordInfoSql = FreeMarkerHelper.getValueFromTpl("sql/user/getOrderRecordInfo.sql", weixinFansParam);
        Number buyNum = jdbcTemplate.queryForObject(getOrderRecordInfoSql, Integer.class);

        ResultData resultData = new ResultData();
        resultData.setSuccess(true);
        resultData.put("totalNum", totalNum);
        if (!recentBuyDate.isEmpty() && StringUtils.isNotBlank(recentBuyDate.get(0))) {
            resultData.put("recentBuyDate", DateParseUtil.formatDate(DateParseUtil.parseDate(recentBuyDate.get(0)), "yyyy-MM-dd"));
        }
        resultData.put("dealTotalAmount", dealTotalAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
        resultData.put("dealNum", dealNum);
        resultData.put("buyNum", buyNum);
        return resultData;
    }

    @Override
    public ResultData editFansInfo(WeixinStoreWeixinuserVo weixinStoreWeixinuserVo) {
        ValidateUtils.notNull(weixinStoreWeixinuserVo.getStoreId(), "店铺参数异常");
        ValidateUtils.notNull(weixinStoreWeixinuserVo.getWeixinuserId(), "粉丝参数异常");
        WeixinStoreWeixinuser weixinStoreWeixinuser = weixinStoreWeixinuserService.get(Conds.get().eq("store_id", weixinStoreWeixinuserVo.getStoreId()).eq("weixinuser_id", weixinStoreWeixinuserVo.getWeixinuserId()));
        weixinStoreWeixinuser.resetProperties(weixinStoreWeixinuserVo);
        weixinStoreWeixinuserService.update(weixinStoreWeixinuser);
        return ResultData.createSuccess();
    }

    @Override
    public ResultData fansLabelList(WeixinFansParam weixinFansParam) {
        ResultData resultData = new ResultData();
        resultData.put("labels", null);
        resultData.put("subAccLabels", null); //去重
        return resultData;
    }

    @Override
    public ResultData editLabel(WeixinFansParam weixinFansParam) {
        //TODO 为当前店铺保存标签，并给用户关联标签
        return null;
    }

    @Override
    public ResultData getFansRemarkInfo(WeixinFansParam weixinFansParam) {
        //查询店铺信息
        Subbranch subbranch = subbranchService.getById(weixinFansParam.getShopId());
        List<String> subStoreIds = Lists.newArrayList();
        if (!BoolType.T.name().equals(subbranch.getIsSubAccount())) { //查询所有子账号
            subStoreIds = getSubStoreIds(subbranch);
        }
        subStoreIds.add(subbranch.getId() + "");

        Map<String, String> params = new HashMap<String, String>();
        params.put("shopIds", StringUtils.join(subStoreIds, ","));
        params.put("fansIds", weixinFansParam.getFansId());

        String queryFansListByFansIdsAndShopIds = FreeMarkerHelper.getValueFromTpl("sql/user/queryFansListByFansIdsAndShopIds.sql", params);

        List<WeixinStoreWeixinuserVo> storeWeixinuserVos = jdbcTemplate.query(queryFansListByFansIdsAndShopIds,
                ParameterizedBeanPropertyRowMapper.newInstance(WeixinStoreWeixinuserVo.class));

        List<Map<String, String>> subResultMapList = new ArrayList<Map<String, String>>();
        ResultData resultData = new ResultData();
        resultData.setSuccess(true);
        if (!BoolType.T.name().equals(subbranch.getIsSubAccount())) { //主账号
            for (WeixinStoreWeixinuserVo storeWeixinuserVo : storeWeixinuserVos) {
                if (storeWeixinuserVo.getStoreId().equals(subbranch.getId() + "")) {
                    resultData.put("mainStore", storeWeixinuserVo.getRemark());
                } else {
                    Map<String, String> subRemarkMap = new HashMap<String, String>();
                    subRemarkMap.put("storeName", storeWeixinuserVo.getStoreName());
                    subRemarkMap.put("remark", storeWeixinuserVo.getRemark());
                    subResultMapList.add(subRemarkMap);
                }
            }
        } else {
            resultData.put("mainStore", !storeWeixinuserVos.isEmpty() && storeWeixinuserVos.get(0) != null ? storeWeixinuserVos.get(0).getRemark() : "");
        }
        resultData.put("subStore", subResultMapList);
        return resultData;
    }

    @Override
    public ResultData getFansBelongToStore(WeixinFansParam weixinFansParam) {
        //查询店铺信息
        Subbranch subbranch = subbranchService.getById(weixinFansParam.getShopId());
        List<String> subStoreIds = Lists.newArrayList();
        if (!BoolType.T.name().equals(subbranch.getIsSubAccount())) { //查询所有子账号
            subStoreIds = getSubStoreIds(subbranch);
        }
        subStoreIds.add(subbranch.getId() + "");

        Map<String, String> params = new HashMap<String, String>();
        params.put("shopIds", StringUtils.join(subStoreIds, ","));
        params.put("fansIds", weixinFansParam.getFansId());

        String queryFansListByFansIdsAndShopIds = FreeMarkerHelper.getValueFromTpl("sql/user/queryFansListByFansIdsAndShopIds.sql", params);

        List<WeixinStoreWeixinuserVo> storeWeixinuserVos = jdbcTemplate.query(queryFansListByFansIdsAndShopIds,
                ParameterizedBeanPropertyRowMapper.newInstance(WeixinStoreWeixinuserVo.class));

        List<Map<String, String>> subResultMapList = new ArrayList<Map<String, String>>();
        ResultData resultData = new ResultData();
        resultData.setSuccess(true);
        if (!BoolType.T.name().equals(subbranch.getIsSubAccount())) { //主账号
            for (WeixinStoreWeixinuserVo storeWeixinuserVo : storeWeixinuserVos) {
                Map<String, String> subRemarkMap = new HashMap<String, String>();
                if (storeWeixinuserVo.getStoreId().equals(subbranch.getId() + "")) {
                    subRemarkMap.put("subscribeTime", storeWeixinuserVo.getDateCreated() != null ? DateParseUtil.formatDate(storeWeixinuserVo.getDateCreated(), "yyyy-MM-dd HH:mm:ss") : "未关注");
                    subRemarkMap.put("storeName", "主账号");
                    resultData.put("mainStore", subRemarkMap);
                } else {
                    subRemarkMap.put("storeName", storeWeixinuserVo.getStoreName());
                    subRemarkMap.put("subscribeTime", storeWeixinuserVo.getDateCreated() != null ? DateParseUtil.formatDate(storeWeixinuserVo.getDateCreated(), "yyyy-MM-dd HH:mm:ss") : "");
                    subResultMapList.add(subRemarkMap);
                }
            }
        } else {
            Map<String, String> subRemarkMap = new HashMap<String, String>();
            if (!storeWeixinuserVos.isEmpty() && storeWeixinuserVos.get(0) != null) {
                WeixinStoreWeixinuserVo storeWeixinuserVo = storeWeixinuserVos.get(0);
                subRemarkMap.put("subscribeTime", storeWeixinuserVo.getDateCreated() != null ? DateParseUtil.formatDate(storeWeixinuserVo.getDateCreated(), "yyyy-MM-dd HH:mm:ss") : "未关注");
                subRemarkMap.put("storeName", storeWeixinuserVo.getStoreName());
                resultData.put("mainStore", subRemarkMap);
            }
        }
        resultData.put("subStore", subResultMapList);
        return resultData;
    }
}
