package pub.makers.shop.store.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.util.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.entity.StoreLabel;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.entity.WeixinUserStoreLabel;
import pub.makers.shop.store.pojo.StoreLabelParam;
import pub.makers.shop.store.vo.StoreLabelVo;
import pub.makers.shop.user.service.WeixinFansBizService;
import pub.makers.shop.user.vo.WeixinFansVo;
import pub.makers.shop.user.vo.WeixinStoreWeixinuserVo;

import java.util.*;

/**
 * Created by dy on 2017/10/8.
 */
@Service(version = "1.0.0")
public class StoreLabelBizServiceImpl implements StoreLabelBizService {

    @Autowired
    private SubbranchService subbranchService;
    @Autowired
    private StoreLabelService storeLabelService;
    @Autowired
    private WeixinFansBizService weixinFansBizService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private WeixinUserStoreLabelService weixinUserStoreLabelService;


    @Override
    public ResultData fansLabelList(StoreLabelParam storeLabelParam) {

        ResultData resultData = new ResultData();
        resultData.setSuccess(true);


        //查询店铺信息
        Subbranch subbranch = subbranchService.getById(storeLabelParam.getShopId());


        //查询已经选中的标签
        storeLabelParam.setStoreIds(storeLabelParam.getShopId());
        String queryFansLabelListSql;
        queryFansLabelListSql = FreeMarkerHelper.getValueFromTpl("sql/subbranch/queryFansLabelList.sql", storeLabelParam);
        List<StoreLabelVo> mainStoreLabels = jdbcTemplate.query(queryFansLabelListSql, ParameterizedBeanPropertyRowMapper.newInstance(StoreLabelVo.class));

        //查询未选中的标签
        Set<String> stringSet = ListUtils.getIdSet(mainStoreLabels, "id");
        if (!stringSet.isEmpty()) {
            List<StoreLabelVo> mainStoreAllLabels = jdbcTemplate.query("select *, 0 as isChecked from store_label where store_id = ? and id not in ("+ StringUtils.join(stringSet, ",") +")", ParameterizedBeanPropertyRowMapper.newInstance(StoreLabelVo.class),
                    storeLabelParam.getShopId());
            for (StoreLabelVo mainStoreAllLabel : mainStoreAllLabels) {
                mainStoreLabels.add(mainStoreAllLabel);
            }
        }

        resultData.put("mianStoreLabels", mainStoreLabels);


        List<String> subStoreIds = Lists.newArrayList();
        if (BoolType.T.name().equals(subbranch.getIsSubAccount())) {
            return resultData;
        }

        //查询选中子账号标签
        subStoreIds = weixinFansBizService.getSubStoreIds(subbranch);
        storeLabelParam.setStoreIds(StringUtils.join(subStoreIds, ","));
        queryFansLabelListSql = FreeMarkerHelper.getValueFromTpl("sql/subbranch/queryFansLabelList.sql", storeLabelParam);
        List<StoreLabelVo> sutStoreLabels = jdbcTemplate.query(queryFansLabelListSql, ParameterizedBeanPropertyRowMapper.newInstance(StoreLabelVo.class));
        Set<String> subStoreLabels = ListUtils.getIdSet(sutStoreLabels, "name");
        resultData.put("subStoreLabels", subStoreLabels);
        return resultData;
    }

    @Override
    public ResultData editFansLabel(StoreLabelParam storeLabelParam) {

        //删除标签关联
        if (StringUtils.isNotBlank(storeLabelParam.getDelLabelIds())) {
            jdbcTemplate.update("delete from weixin_user_store_label where label_id in ("+ storeLabelParam.getDelLabelIds() +") and store_id = ? and weixin_id = ?", storeLabelParam.getShopId(), storeLabelParam.getFansId());
        }

        if (StringUtils.isNotBlank(storeLabelParam.getNewLabelNames())) {
            for (String labelName : storeLabelParam.getNewLabelNames().split(",")) {
                Long counts = storeLabelService.count(Conds.get().eq("name", labelName));
                ValidateUtils.isTrue(counts > 0, labelName + "标签已重复");
            }
            for (String labelName : storeLabelParam.getNewLabelNames().split(",")) {
                //保存新标签
                StoreLabel storeLabel = new StoreLabel();
                storeLabel.setId(IdGenerator.getDefault().nextId());
                storeLabel.setName(labelName);
                storeLabel.setStoreId(Long.parseLong(storeLabelParam.getShopId()));
                storeLabel.setDateCreated(new Date());
                storeLabel.setDateUpdate(new Date());
                storeLabelService.insert(storeLabel);

                //关联新标签
                WeixinUserStoreLabel weixinUserStoreLabel = new WeixinUserStoreLabel();
                weixinUserStoreLabel.setStoreId(Long.parseLong(storeLabelParam.getShopId()));
                weixinUserStoreLabel.setId(IdGenerator.getDefault().nextId());
                weixinUserStoreLabel.setLabelId(storeLabel.getId());
                weixinUserStoreLabel.setWeixinId(Long.parseLong(storeLabelParam.getFansId()));
                weixinUserStoreLabel.setDateUpdate(new Date());
                weixinUserStoreLabel.setDateUpdate(new Date());
                weixinUserStoreLabelService.insert(weixinUserStoreLabel);
            }
        }
        return ResultData.createSuccess();
    }

    @Override
    public ResultData storeLabelList(StoreLabelParam storeLabelParam, Paging paging) {

        List<StoreLabelVo> storeLabelVos = jdbcTemplate.query("select * from store_label where store_id=? limit ?, ?",
                ParameterizedBeanPropertyRowMapper.newInstance(StoreLabelVo.class), storeLabelParam.getShopId(), paging.getPs(), paging.getPn());

        Set<String> labelIds = ListUtils.getIdSet(storeLabelVos, "id");

        Map<String, List<WeixinStoreWeixinuserVo>> storeLabelFans = getStoreLabelFans(storeLabelParam.getShopId(), labelIds);
        for (StoreLabelVo storeLabelVo : storeLabelVos) {
            if (storeLabelFans.get(storeLabelVo.getId()) != null) {
                storeLabelVo.setFansNum(storeLabelFans.get(storeLabelVo.getId()).size());
            } else {
                storeLabelVo.setFansNum(0);
            }
        }

        ResultData resultData = new ResultData();
        resultData.setSuccess(true);
        resultData.put("resultList", storeLabelVos);
        resultData.setPageNo(paging.getPageNum());
        resultData.setPageSize(paging.getPageSize());
        return resultData;
    }


    @Override
    public ResultData saveLabel(StoreLabelVo storeLabelVo) {

        if (StringUtils.isNotBlank(storeLabelVo.getId())) {//编辑
            StoreLabel storeLabelOld = storeLabelService.getById(storeLabelVo.getId());
            storeLabelOld.setName(storeLabelVo.getName());
            storeLabelOld.setDateUpdate(new Date());
            storeLabelService.update(storeLabelOld);
        } else { //新增
            StoreLabel storeLabel = new StoreLabel();
            storeLabel.setId(IdGenerator.getDefault().nextId());
            storeLabel.setName(storeLabelVo.getName());
            storeLabel.setStoreId(Long.parseLong(storeLabelVo.getStoreId()));
            storeLabel.setDateCreated(new Date());
            storeLabel.setDateUpdate(new Date());
            storeLabelVo.setId(storeLabel.getId() + "");
            storeLabelService.insert(storeLabel);
        }


        if (StringUtils.isNotBlank(storeLabelVo.getDelFansIds())) { //删除粉丝
            jdbcTemplate.update("delete from weixin_user_store_label where store_id = ? and weixin_id in(?) and label_id = ?", storeLabelVo.getStoreId(), storeLabelVo.getDelFansIds(), storeLabelVo.getId());
        }

        if (StringUtils.isNotBlank(storeLabelVo.getNewFansIds())) { //新增粉丝
            for (String fansId : storeLabelVo.getNewFansIds().split(",")) {
                WeixinUserStoreLabel weixinUserStoreLabel = new WeixinUserStoreLabel();
                weixinUserStoreLabel.setLabelId(Long.parseLong(storeLabelVo.getId()));
                weixinUserStoreLabel.setStoreId(Long.parseLong(storeLabelVo.getStoreId()));
                weixinUserStoreLabel.setWeixinId(Long.parseLong(fansId));
                weixinUserStoreLabel.setId(IdGenerator.getDefault().nextId());
                weixinUserStoreLabel.setDateCreated(new Date());
                weixinUserStoreLabel.setDateUpdate(new Date());
                weixinUserStoreLabelService.insert(weixinUserStoreLabel);
            }
        }
        return ResultData.createSuccess();
    }

    @Override
    public ResultData getLabelInfo(StoreLabelVo storeLabelVo) {

        //标签信息
        StoreLabel storeLabel = storeLabelService.getById(storeLabelVo.getId());


        //标签关联粉丝
        Map<String, String> params = new HashMap<String, String>();
        params.put("labelIds", storeLabelVo.getId());
        params.put("shopId", storeLabelVo.getStoreId());
        String getStoreLabelFansSql = FreeMarkerHelper.getValueFromTpl("sql/store/getStoreLabelFans.sql", params);
        List<WeixinStoreWeixinuserVo> weixinuserVos = jdbcTemplate.query(getStoreLabelFansSql, ParameterizedBeanPropertyRowMapper.newInstance(WeixinStoreWeixinuserVo.class));

        ResultData resultData = new ResultData();
        resultData.setSuccess(true);
        resultData.put("label", storeLabel);
        resultData.put("fansList", weixinuserVos);
        return resultData;
    }

    @Override
    public ResultData getAllFansList(StoreLabelVo storeLabelVo, Paging paging) {

        storeLabelVo.setShopIds(storeLabelVo.getStoreId());
        String queryWeixinfansListSql = FreeMarkerHelper.getValueFromTpl("sql/user/queryWeixinfansList.sql", storeLabelVo);
        List<WeixinFansVo> weixinFansVos = jdbcTemplate.query(queryWeixinfansListSql + "limit ?, ?", ParameterizedBeanPropertyRowMapper.newInstance(WeixinFansVo.class), paging.getPs(), paging.getPn());

        if (StringUtils.isNotBlank(storeLabelVo.getSelFansId())) { //设置是否选中
            for (WeixinFansVo weixinFansVo : weixinFansVos) {
                for (String selFansId : storeLabelVo.getSelFansId().split(",")) {
                    if (weixinFansVo.getId().equals(selFansId)) {
                        weixinFansVo.setIsSelected(true);
                    }
                }
            }
        }

        ResultData resultData = new ResultData();
        resultData.setSuccess(true);
        resultData.put("resultList", weixinFansVos);
        resultData.setPageNo(paging.getPageNum());
        resultData.setPageSize(paging.getPageSize());
        return resultData;
    }

    /**
     * 标签下的粉丝用户
     * @param shopId
     * @param labelIds
     * @return
     */
    private Map<String, List<WeixinStoreWeixinuserVo>> getStoreLabelFans(String shopId, Set<String> labelIds) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("shopId", shopId);
        params.put("labelIds", StringUtils.join(labelIds, ","));
        Map<String, List<WeixinStoreWeixinuserVo>> weixinStoreUserListMap = new HashMap<String, List<WeixinStoreWeixinuserVo>>();

        if (labelIds.isEmpty()) {
           return weixinStoreUserListMap;
        }

        String getStoreLabelFansSql = FreeMarkerHelper.getValueFromTpl("sql/store/getStoreLabelFans.sql", params);
        List<WeixinStoreWeixinuserVo> storeWeixinuserVos = jdbcTemplate.query(getStoreLabelFansSql, ParameterizedBeanPropertyRowMapper.newInstance(WeixinStoreWeixinuserVo.class));

        for (WeixinStoreWeixinuserVo storeWeixinuserVo : storeWeixinuserVos) {
            if (weixinStoreUserListMap.get(storeWeixinuserVo.getLabelId()) == null) {
                weixinStoreUserListMap.put(storeWeixinuserVo.getLabelId(), new ArrayList<WeixinStoreWeixinuserVo>());
            }
            weixinStoreUserListMap.get(storeWeixinuserVo.getLabelId()).add(storeWeixinuserVo);
        }
        return weixinStoreUserListMap;
    }
}
