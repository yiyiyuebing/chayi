package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by daiwenfa on 2017/5/24.
 */
@Service(version = "1.0.0")
public class CargosAdminServiceImpl implements CargosMgrBizService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ResultList<Map<String, Object>> getListPage(Map<String, Object> param, Paging pg) {
        StringBuilder sb = new StringBuilder();
        List list = new ArrayList();
        sb.append("select a.id, " +
                "    a.cargo_no, " +
                "    b.pic_url small_image, " +
                "    a.`name`, " +
                "    c.`name` classify, " +
                "    d.`name` brand, " +
                "    e.`name` supplier, " +
                "    f.specs, " +
                "    sum(if(isnull(h.remain_count), 0, h.remain_count)) remain_count, " +
                "    a.create_time, " +
                "    a.create_by, " +
                "    a.update_time, " +
                "    a.update_by " +
                "    from cargo a " +
                "    left join image b on a.small_image_id=b.id " +
                "    left join cargo_classify c on a.classify_id=c.id " +
                "    left join cargo_brand d on a.brand_id=d.id " +
                "    left join cargo_supplier e on a.supplier_id=e.id " +
                "    left join ( " +
                "            select cargo_id, group_concat(`name`) specs " +
                "    from cargo_sku_type " +
                "    group by cargo_id) f on a.id=f.cargo_id " +
                "    left join cargo_sku g on a.id=g.cargo_id " +
                "    left join cargo_sku_stock h on g.id=h.cargo_sku_id " +
                "    where 1=1");
        if (param.get("name") != null) {
            sb.append(" and (a.`name` like ? or a.`cargo_no` like ? or f.specs like ? ) ");
            list.add(param.get("name").toString());
            list.add(param.get("name").toString());
            list.add(param.get("name").toString());
        }
        sb.append(" group by a.id  " +
                "    ORDER BY a.update_time DESC  " +
                "    limit ? , ? ");
        list.add(pg.getPs());
        list.add(pg.getPn());
        List<Map<String,Object>> lists = jdbcTemplate.queryForList(sb.toString(),list.toArray());
        ResultList<Map<String,Object>> result = new ResultList<Map<String, Object>>();
        result.setResultList(lists);
        return result;
    }
}
