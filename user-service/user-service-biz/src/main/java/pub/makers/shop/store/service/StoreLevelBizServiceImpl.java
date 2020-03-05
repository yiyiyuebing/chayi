package pub.makers.shop.store.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.util.IdGenerator;
import pub.makers.shop.store.entity.StoreLevel;
import pub.makers.shop.store.entity.Subbranch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/15 0015.
 */
@Service(version = "1.0.0")
public class StoreLevelBizServiceImpl implements StoreLevelBizService {

    private String findStoreLevelByShopIdSql = "select sl.* from store_level sl join store_subbranch ss on sl.level_Id = ss.level_id";
    private String getSubbranchSql = "SELECT * FROM store_subbranch";
    @Autowired
    private StoreLevelService storeLevelService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveStoreLevel(StoreLevel storeLevel) {
        if (storeLevel.getLevelId() == null) {
            storeLevel.setLevelId(IdGenerator.getDefault().nextId());
            storeLevelService.insert(storeLevel);
        } else {
            storeLevelService.update(storeLevel);
        }

    }

    @Override
    public StoreLevel getById(Long id) {
        return storeLevelService.getById(id);
    }


    @Override
    public Map<String, StoreLevel> getStoreLevelMap(Set<String> params) {
        RowMapper<StoreLevel> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(StoreLevel.class);
        Map<String, Object> queryMap = new HashMap<String, Object>();

        String sql = "";
        if (params.size() > 0) {
            sql = findStoreLevelByShopIdSql + " where ss.id in (" + StringUtils.join(Lists.newArrayList(params), ",") + ")";
            getSubbranchSql = getSubbranchSql + " where id in (" + StringUtils.join(Lists.newArrayList(params), ",") + ")";
            queryMap.put("params", StringUtils.join(Lists.newArrayList(params), ","));
        } else {
            return new HashMap<String, StoreLevel>();
        }
        List<StoreLevel> storeLevelList = jdbcTemplate.query(sql, rowMapper);

//        String getSubbranch = FreeMarkerHelper.getValueFromTpl("sql/storeLevel/getSubbranch.sql", queryMap);
        RowMapper<Subbranch> SubbranchRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(Subbranch.class);

        List<Subbranch> subbranchList = jdbcTemplate.query(getSubbranchSql, SubbranchRowMapper);

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

}
