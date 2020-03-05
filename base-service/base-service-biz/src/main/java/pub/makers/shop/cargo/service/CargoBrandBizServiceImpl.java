package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.cargo.entity.CargoClassify;
import pub.makers.shop.cargo.entity.vo.CargoBrandVo;

import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/5/25.
 */
@Service(version = "1.0.0")
public class CargoBrandBizServiceImpl implements CargoBrandBizService {
    @Autowired
    private CargoClassifyService cargoClassifyService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CargoBrandVo> getCargoBrandList(String classifyId, String category) {
        Map<String, Object> data = Maps.newHashMap();
        if (StringUtils.isNotEmpty(classifyId)) {
            //获取分类和所有子分类id
            List<String> ids = getAllIds(classifyId);
            data.put("classify", StringUtils.join(ids, ","));
        }
        data.put("category", category);

        String sql = FreeMarkerHelper.getValueFromTpl("sql/cargo/getCargoBrandList.sql", data);
        RowMapper<CargoBrandVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(CargoBrandVo.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    private List<String> getAllIds(String parentId) {
        List<String> ids = Lists.newArrayList();
        ids.add(parentId);
        List<CargoClassify> children = cargoClassifyService.list(Conds.get().eq("parent_id", parentId));
        for (CargoClassify child : children) {
            ids.addAll(getAllIds(child.getId().toString()));
        }
        return ids;
    }
}
