package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.cargo.entity.CargoSuppliers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by daiwenfa on 2017/5/22.
 */
@Service(version = "1.0.0")
public class CargoSuppliersAdminServiceImpl implements CargoSuppliersMgrBizService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CargoSuppliersService cargoSuppliersService;
    
    @Override
    public ResultList<CargoSuppliers> cargoSuppliersPage(CargoSuppliers cargoSuppliers, Paging pg) {
        StringBuffer page = new StringBuffer();
        StringBuffer count = new StringBuffer();
        StringBuffer sb = new StringBuffer();
        List param = new ArrayList();
        RowMapper<CargoSuppliers> cargoSuppliersRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(CargoSuppliers.class);
        page.append("SELECT cs.* ");
        count.append("select count(0) ");
        sb.append(" FROM cargo_supplier cs where 1=1 and cs.del_flag <> 'T' ");
        if(StringUtils.isNotBlank(cargoSuppliers.getCode())){
            sb.append(" and cs.code = ? ");
            param.add(cargoSuppliers.getCode());
        }
        if(StringUtils.isNotBlank(cargoSuppliers.getName())){
            sb.append(" and cs.name = ? ");
            param.add(cargoSuppliers.getName());
        }
        if(StringUtils.isNotBlank(cargoSuppliers.getContacts())){
            sb.append(" and cs.contacts = ? ");
            param.add(cargoSuppliers.getContacts());
        }
        if(StringUtils.isNotBlank(cargoSuppliers.getTel())){
            sb.append(" and cs.tel = ? ");
            param.add(cargoSuppliers.getTel());
        }
        if(StringUtils.isNotBlank(cargoSuppliers.getIsValid())){
            sb.append(" and cs.is_valid = ? ");
            param.add(cargoSuppliers.getIsValid());
        }
        if(StringUtils.isNotBlank(cargoSuppliers.getRemark())){
            sb.append(" and cs.remark = ? ");
            param.add(cargoSuppliers.getRemark());
        }
        Number total = jdbcTemplate.queryForObject(count.toString() + sb.toString(), param.toArray(), Integer.class);
        sb.append(" order by cs.sort desc,cs.name limit ?,? ");
        param.add(pg.getPs());
        param.add(pg.getPn());
        List<CargoSuppliers> resultList = jdbcTemplate.query(page.toString() + sb.toString(), param.toArray(), cargoSuppliersRowMapper);
        ResultList<CargoSuppliers> result = new ResultList<CargoSuppliers>();
        result.setResultList(resultList);
        result.setTotalRecords(total != null ? total.intValue() : 0);
        return result;
    }

    @Override
    public CargoSuppliers delCargoSuppliersById(String id) {
        cargoSuppliersService.update(Update.byId(id).set("del_flag", "T"));
        return null;
    }

    @Override
    public CargoSuppliers saveOrUpdate(CargoSuppliers cargoSuppliers) {
        if (StringUtils.isBlank(cargoSuppliers.getId())) {
            cargoSuppliers.setId(IdGenerator.getDefault().nextId()+"");
            cargoSuppliers.setDelFlag("F");
            cargoSuppliers.setCreateTime(new Date());
            cargoSuppliersService.insert(cargoSuppliers);
        } else {
            CargoSuppliers cargoSuppliersOld = cargoSuppliersService.getById(cargoSuppliers.getId());
            cargoSuppliersOld.setCode(cargoSuppliers.getCode());
            cargoSuppliersOld.setName(cargoSuppliers.getName());
            cargoSuppliersOld.setContacts(cargoSuppliers.getContacts());
            cargoSuppliersOld.setTel(cargoSuppliers.getTel());
            cargoSuppliersOld.setAddr(cargoSuppliers.getAddr());
            cargoSuppliersOld.setIsValid(cargoSuppliers.getIsValid());
            cargoSuppliersOld.setSort(cargoSuppliers.getSort());
            cargoSuppliersOld.setRemark(cargoSuppliers.getRemark());
            cargoSuppliersOld.setUpdateBy(cargoSuppliers.getCreateBy());
            cargoSuppliersOld.setUpdateTime(new Date());
            cargoSuppliersService.update(cargoSuppliersOld);
            cargoSuppliers = cargoSuppliersOld;
        }
        return cargoSuppliers;
    }

    @Override
    public boolean ableOrDisable(String id, String operation) {
        CargoSuppliers cargoSuppliersOld = cargoSuppliersService.get(Conds.get().eq("id", id));
        String flag = cargoSuppliersOld.getIsValid();
        if(!flag.equals(operation)) {
            cargoSuppliersService.update(Update.byId(id).set("is_valid", operation));
            return true;
        }
        return false;
    }

    @Override
    public CargoSuppliers getCargoSuppliersInfo(String id) {
        return cargoSuppliersService.get(Conds.get().eq("id", id));
    }
}
