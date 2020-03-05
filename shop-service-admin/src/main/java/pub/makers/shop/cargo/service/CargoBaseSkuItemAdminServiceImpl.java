package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.cargo.entity.CargoBaseSkuItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/5/22.
 */
@Service(version = "1.0.0")
public class CargoBaseSkuItemAdminServiceImpl implements CargoBaseSkuItemMgrBizService {

    private final static String selectSkuItemBySkuTypeIdStmt = "select * from cargo_base_sku_item where base_sku_type_id = ? and name like CONCAT('%', ? ,'%')";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CargoBaseSkuItemService cargoBaseSkuItemService;

    @Override
    public List<CargoBaseSkuItem> selectSkuItemBySkuTypeId(Long skuTypeId, String type, String searchName) {
        if (StringUtils.isBlank(searchName)) {
            searchName = "";
        }
        RowMapper<CargoBaseSkuItem> cargoBaseSkuItemRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(CargoBaseSkuItem.class);
        List<CargoBaseSkuItem> cargoBaseSkuItems = jdbcTemplate.query(selectSkuItemBySkuTypeIdStmt, cargoBaseSkuItemRowMapper, skuTypeId, searchName);
        return cargoBaseSkuItems;
    }

    @Override
    public Map<String, Object> addSkuItemBySkuTypeId(Long skuTypeId, String name) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<CargoBaseSkuItem> cargoBaseSkuItems = cargoBaseSkuItemService.list(Conds.get().eq("base_sku_type_id", skuTypeId).eq("name", name));
        if (cargoBaseSkuItems != null && cargoBaseSkuItems.size() > 0) {
            resultMap.put("code", false);
            resultMap.put("msg", "规格项重复");
            return resultMap;
        }
        CargoBaseSkuItem cargoBaseSkuItem = new CargoBaseSkuItem();
        cargoBaseSkuItem.setId(IdGenerator.getDefault().nextId() + "");
        cargoBaseSkuItem.setBaseSkuTypeId(skuTypeId + "");
        cargoBaseSkuItem.setName(name);
        cargoBaseSkuItem.setValue(name);
        cargoBaseSkuItem.setCode(name);
        cargoBaseSkuItemService.insert(cargoBaseSkuItem);
        resultMap.put("code", true);
        return resultMap;
    }
}
