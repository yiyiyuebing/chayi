package pub.makers.shop.finance.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.finance.entity.FinanceAccountsPay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/6/26.
 */
@Service(version = "1.0.0")
public class FinanceAccountsPayAdminServiceImpl implements FinanceAccountsPayAdminService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<FinanceAccountsPay> getVoByU8Missing(String order) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderType", order);
        String sql = FreeMarkerHelper.getValueFromTpl("sql/finance/getExcepionFinanceList.sql", paramMap);
        return jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(FinanceAccountsPay.class));
    }

}
