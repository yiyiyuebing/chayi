package pub.makers.shop.purchaseGoods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.cargo.entity.vo.CargoBrandVo;

import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/6/1.
 */
@Service(version = "1.0.0")
public class PurchaseBrandBizServiceImpl implements PurchaseBrandBizService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CargoBrandVo> getCargoBrandList(List<String> classifyIds) {
        Map<String, Object> data = Maps.newHashMap();
        //获取分类和所以子分类id
        data.put("classifyIds", classifyIds);
        String sql = FreeMarkerHelper.getValueFromTpl("sql/purchaseGood/getPurchaseBrandList.sql", data);
        RowMapper<CargoBrandVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(CargoBrandVo.class);
        List<CargoBrandVo> brandVoList = Lists.newArrayList();
        for (CargoBrandVo cargoBrandVo : jdbcTemplate.query(sql, rowMapper)) {
            if (StringUtils.isNotBlank(cargoBrandVo.getId())) {
                brandVoList.add(cargoBrandVo);
            }
        }
        return brandVoList;
    }
}
