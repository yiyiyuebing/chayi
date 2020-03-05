package pub.makers.shop.store.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.store.vo.SubbranchVo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dy on 2017/9/7.
 */
@Service(version = "1.0.0")
public class SubbranchAdminBizServiceImpl implements SubbranchMgrBizService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public SubbranchVo getShopInfo(String storeId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("storeId", storeId);
        String getSubbranchInfoSql = FreeMarkerHelper.getValueFromTpl("sql/store/getSubbranchInfo.sql", param);
        SubbranchVo subbranchVo = jdbcTemplate.queryForObject(getSubbranchInfoSql, ParameterizedBeanPropertyRowMapper.newInstance(SubbranchVo.class));
        return subbranchVo;
    }
}
