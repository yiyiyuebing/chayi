package pub.makers.shop.promotion.service;


import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.promotion.vo.ManZenAndPresellVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by devpc on 2017/8/25.
 */
@Service(version="1.0.0")
public class OtherAboutActivityAdminServiceImpl implements OtherAboutActivityBizService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ManZenAndPresellVo> getManZenAndPresellBySkuId(ManZenAndPresellVo manZenAndPresellVo) {

        String sql = FreeMarkerHelper.getValueFromTpl("sql/promotion/presell/queryPresellOrSaleOrManzeng.sql", manZenAndPresellVo);

        RowMapper<ManZenAndPresellVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(ManZenAndPresellVo.class);

        List<ManZenAndPresellVo> list = jdbcTemplate.query(sql,rowMapper);





        return list;
    }

    @Override
    public Map<String, List<ManZenAndPresellVo>> getManzengPresellGoodVoListMap (ManZenAndPresellVo manZenAndPresellVo) {

        Map<String, List<ManZenAndPresellVo>> resultMap = new HashMap<String, List<ManZenAndPresellVo>>();


        String sql = FreeMarkerHelper.getValueFromTpl("sql/promotion/presell/queryPresellOrSaleOrManzeng.sql", manZenAndPresellVo);

        RowMapper<ManZenAndPresellVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(ManZenAndPresellVo.class);

        List<ManZenAndPresellVo> list = jdbcTemplate.query(sql,rowMapper);

        for (ManZenAndPresellVo zenAndPresellVo : list) {

            if (zenAndPresellVo.getSkuId() != null && zenAndPresellVo.getSkuId().indexOf(",") > -1) {
                String[] skuIdArr = zenAndPresellVo.getSkuId().split(",");
                for (String skuId : skuIdArr) {
                    if (resultMap.get(skuId) == null) {
                        resultMap.put(skuId, new ArrayList<ManZenAndPresellVo>());
                    }
                    zenAndPresellVo.setSkuId(skuId);
                    resultMap.get(skuId).add(zenAndPresellVo);
                }
            } else {
                if (resultMap.get(zenAndPresellVo.getSkuId()) == null) {
                    resultMap.put(zenAndPresellVo.getSkuId(), new ArrayList<ManZenAndPresellVo>());
                }
                resultMap.get(zenAndPresellVo.getSkuId()).add(zenAndPresellVo);
            }

        }
        return resultMap;
    }

}
