package pub.makers.shop.store.service;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.util.FreeMarkerHelper;

import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.store.entity.StoreLevel;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.vo.StoreLevelVo;
import sun.util.calendar.LocalGregorianCalendar;


import javax.servlet.http.HttpServletRequest;
import java.sql.*;

import java.util.*;
import java.util.Date;

import static pub.makers.daotemplate.vo.Conds.*;

/**
 * Created by dy on 2017/6/1.
 */
@Service(version = "1.0.0")
public class StoreLevelAdminServiceImpl implements StoreLevelMgrBizService {

    private String findStoreLevelByShopIdSql = "select sl.* from store_level sl join store_subbranch ss on sl.level_Id = ss.level_id";

    @Reference(version = "1.0.0")
    private StoreLevelBizService storeLevelBizService;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<StoreLevelVo> findAllStoreLevel() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String getStoreLevelList = FreeMarkerHelper.getValueFromTpl("sql/storeLevel/getStoreLevelList.sql", paramMap);
        RowMapper<StoreLevelVo> storeLevelVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(StoreLevelVo.class);
        List<StoreLevelVo> storeLevelVos = jdbcTemplate.query(getStoreLevelList, storeLevelVoRowMapper);
        return storeLevelVos;
    }

    @Override
    public StoreLevel findStoreLevelBySubbranchId(String subbranchId) {
        String findStoreLevelByShopIdStmt = findStoreLevelByShopIdSql;
        RowMapper<StoreLevel> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(StoreLevel.class);
        findStoreLevelByShopIdStmt = findStoreLevelByShopIdStmt + " where ss.id = ?";
        List<StoreLevel> storeLevelList = jdbcTemplate.query(findStoreLevelByShopIdStmt, rowMapper, subbranchId);
        if (!storeLevelList.isEmpty()) {
            return storeLevelList.get(0);
        }
        return null;
    }

    @Override
    public Map<String, StoreLevel> getStoreLevelMap(Set<String> Params) {
        RowMapper<StoreLevel> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(StoreLevel.class);
        Map<String, Object> queryMap = new HashMap<String, Object>();

        String sql = "";
        if (Params.size() > 0) {
            sql = findStoreLevelByShopIdSql + " where ss.id in (" + StringUtils.join(Lists.newArrayList(Params), ",") + ")";
            queryMap.put("params", StringUtils.join(Lists.newArrayList(Params), ","));
        } else {
            return new HashMap<String, StoreLevel>();
        }
        List<StoreLevel> storeLevelList = jdbcTemplate.query(sql, rowMapper);

        String getSubbranch = FreeMarkerHelper.getValueFromTpl("sql/storeLevel/getSubbranch.sql", queryMap);
        RowMapper<Subbranch> SubbranchRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(Subbranch.class);
        List<Subbranch> subbranchList = jdbcTemplate.query(getSubbranch, SubbranchRowMapper);

        Map<String, StoreLevel> storeLevelMap = new HashMap<String, StoreLevel>();
        for (Subbranch subbranch : subbranchList) {
            for (StoreLevel storeLevel : storeLevelList) {
                if (storeLevel.getLevelId().equals(subbranch.getLevelId())) {
                    if (storeLevelMap.get(subbranch.getId() + "") != null) {
                        continue;
                    }
                    storeLevelMap.put(subbranch.getId() + "", storeLevel);
                }
            }
        }
        return storeLevelMap;
    }

    @Override
    public ResultList<StoreLevelVo> storeLevelList(Paging pg) {
        String storeLevelList = FreeMarkerHelper.getValueFromTpl("sql/storeLevel/storeLevelList.sql", null);
        RowMapper<StoreLevelVo> storeLevelVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(StoreLevelVo.class);
        List<StoreLevelVo> StoreLevelVos = jdbcTemplate.query(storeLevelList, storeLevelVoRowMapper, pg.getPn());
        ResultList<StoreLevelVo> resultList = new ResultList<StoreLevelVo>();
        resultList.setResultList(StoreLevelVos);
        return resultList;

    }
    //编辑店铺等级排序
    @Override
    public Map<String, Object> saveOrUpdateStoreLevel(final StoreLevelVo storeLevelVo) {
        StoreLevel storeLevel = new StoreLevel();
        BeanUtils.copyProperties(storeLevelVo, storeLevel);
        storeLevel.setLevelId(Long.parseLong(storeLevelVo.getLevelId()));
        storeLevel.setStatue(Long.parseLong(storeLevelVo.getStatue()));
        storeLevel.setCreateBy(Long.parseLong(storeLevelVo.getCreateBy()));
        storeLevel.setUpdateBy(Long.parseLong(storeLevelVo.getUpdateBy()));
        storeLevel.setCreateTime(storeLevelVo.getCreateTime());
        storeLevel.setUpdateTime(new Date());
        storeLevel.setName(storeLevelVo.getName());
        storeLevel.setSort(storeLevelVo.getSort());
        storeLevel.setStorePro(storeLevelVo.getStorePro());
        storeLevelBizService.saveStoreLevel(storeLevel);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        return resultMap;

    }
    //启用禁用
    @Override
    public ResultData updateStoreLevelStatue(String id, String statue) {
        //Map<String, Object> result = new HashMap<String, Object>();
        StoreLevel storeLevel = storeLevelBizService.getById(Long.parseLong(id));
        String flag = String.valueOf(storeLevel.getStatue());
        if (!flag.equals(statue)){
            //storeLevelBizService.update(Update.byId(id).set("statue", statue).set("update_time",new Date()));
            storeLevel.setStatue(Long.parseLong(statue));
            storeLevel.setUpdateTime(new Date());
            storeLevelBizService.saveStoreLevel(storeLevel);
            return ResultData.createSuccess();
        }
        return ResultData.createFail("操作失败！");
    }

}
