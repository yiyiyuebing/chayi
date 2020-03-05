package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.cargo.entity.CargoBaseSkuType;
import pub.makers.shop.cargo.entity.vo.CargoBaseSkuTypeVo;

import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2017/5/22.
 */
@Service(version = "1.0.0")
public class CargoBaseSkuTypeAdminServiceImpl implements CargoBaseSkuTypeMgrBizService {

    private final static String getCargoBaseSkuTypeListStmt = "SELECT * FROM cargo_base_sku_type where id != 267387252474621952;";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CargoBaseSkuTypeService cargoBaseSkuTypeService;

    @Override
    public List<CargoBaseSkuTypeVo> getCargoBaseSkuTypeList() {
        RowMapper<CargoBaseSkuTypeVo> cargoBaseSkuTypeVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(CargoBaseSkuTypeVo.class);
        return jdbcTemplate.query(getCargoBaseSkuTypeListStmt, cargoBaseSkuTypeVoRowMapper);
    }


    @Override
    public CargoBaseSkuType addCargoBaseSkuType(CargoBaseSkuType cargoBaseSkuType) {
        cargoBaseSkuType.setId(IdGenerator.getDefault().nextId());
        cargoBaseSkuType.setCreateTime(new Date());
        cargoBaseSkuType.setType("1");
        return cargoBaseSkuTypeService.insert(cargoBaseSkuType);
    }
}
