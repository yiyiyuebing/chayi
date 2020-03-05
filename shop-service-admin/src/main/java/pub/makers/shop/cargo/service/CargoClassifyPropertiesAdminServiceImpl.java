package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.cargo.entity.CargoClassify;
import pub.makers.shop.cargo.entity.CargoClassifyProperties;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by daiwenfa on 2017/5/19.
 */
@Service(version = "1.0.0")
public class CargoClassifyPropertiesAdminServiceImpl implements  CargoClassifyPropertiesMgrBizService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CargoClassifyPropertiesService cargoClassifyPropertiesService;

    @Autowired
    private CargoClassifyService cargoClassifyService;

    //查询分页数据，通过语句拼接
    @Override
    public ResultList<CargoClassifyProperties> cargoClassifyPropertiesList (CargoClassifyProperties ccp, Paging pg) {
        StringBuffer page = new StringBuffer();//页面语句
        StringBuffer count = new StringBuffer();//条数语句
        List param = new ArrayList();
        RowMapper<CargoClassifyProperties> ccpRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(CargoClassifyProperties.class);
        page.append(" select cargo_classify.id as cargoClassifyId,cargo_classify.`name` as cargoClassifyName," +
                    " group_concat(cargo_classify_properties.pname) as pname,cargo_classify_properties.date_update as dateUpdate   " +
                    " from cargo_classify " +
                    " LEFT JOIN cargo_classify_properties on cargo_classify_properties.cargo_classify_id = cargo_classify.id " +
                    " where cargo_classify.parent_id='1' " +
                    " and cargo_classify_properties.del_flag='F'  " +
                    " GROUP BY cargo_classify.id,cargo_classify_properties.date_update,cargo_classify_properties.del_flag " +
                    " order by cargo_classify.order_index desc limit ?,? ");
        /*if(StringUtils.isNotBlank(ccp.getIsRequired())){
            sb.append(" and ccp.is_required = ? ");
            param.add(ccp.getIsRequired());
        }
        if(StringUtils.isNotBlank(ccp.getCargoClassifyId())){
            sb.append(" and ccp.cargo_classify_id = ? ");
            param.add(ccp.getCargoClassifyId());
        }
        if(StringUtils.isNotBlank(ccp.getPname())){
            sb.append(" and ccp.pname = ? ");
            param.add(ccp.getPname());
        }*/
        param.add(pg.getPs());
        param.add(pg.getPn());
        count.append(" SELECT count(DISTINCT cargo_classify_id) FROM cargo_classify " +
                     " INNER JOIN cargo_classify_properties ON cargo_classify.id = cargo_classify_properties.cargo_classify_id " +
                     " WHERE cargo_classify.parent_id = '1' and cargo_classify_properties.del_flag='F' ");
        ResultList<CargoClassifyProperties> result = new ResultList<CargoClassifyProperties>();
        List<CargoClassifyProperties> resultList = jdbcTemplate.query(page.toString(), param.toArray(),ccpRowMapper);
        result.setResultList(resultList);
        Number total = jdbcTemplate.queryForObject(count.toString(),null, Integer.class);
        result.setTotalRecords(total != null ? total.intValue() : 0);
        return result;
    }

    @Override
    public CargoClassifyProperties delCargoClassifyPropertiesById(String id) {
        cargoClassifyPropertiesService.delete(Conds.get().eq("cargo_classify_id",id));
        return null;
    }

    @Override
    public Boolean saveOrUpdate(CargoClassifyProperties ccp) {
        String cargoClassifyId = ccp.getCargoClassifyId();
        String[] pname = ccp.getPname().split(",");
        String[] isvalid = ccp.getIsValid().split(",");
        Date now = new Date();
        cargoClassifyPropertiesService.delete(Conds.get().eq("cargo_classify_id",cargoClassifyId));
        for(int i= 0;i<pname.length;i++){
            CargoClassifyProperties ccps = new CargoClassifyProperties();
            ccps.setId(IdGenerator.getDefault().nextId()+"");
            ccps.setCargoClassifyId(cargoClassifyId);
            ccps.setDateCreated(now);
            ccps.setDateUpdate(now);
            ccps.setPname(pname[i]);
            ccps.setIsValid(isvalid[i]);
            ccps.setDelFlag("F");
            cargoClassifyPropertiesService.insert(ccps);
        }
        return true;
    }

   @Override
    public CargoClassifyProperties ableOrDisable(String id) {
        CargoClassifyProperties ccpOld = cargoClassifyPropertiesService.getById(id);
        String flag = ccpOld.getIsValid();
        if(flag.equals("T")){
            ccpOld.setIsValid("F");
        }else{
            ccpOld.setIsValid("T");
        }
        cargoClassifyPropertiesService.update(ccpOld);
        return null;
    }

    @Override
    public List<CargoClassify> getOneCargoClassify() {
        List<CargoClassify> list = cargoClassifyService.list(Conds.get().eq("parent_id", "1").order("order_index desc"));
        return list;
    }

    @Override
    public CargoClassifyProperties getCargoClassifyProperties(String id) {
        return cargoClassifyPropertiesService.get(Conds.get().eq("id", id));
    }

    @Override
    public List<CargoClassifyProperties> getCargoClassifyParamsByCargoClassifyId(String classifyId) {
        return cargoClassifyPropertiesService.list(Conds.get().eq("cargo_classify_id", classifyId).eq("del_flag", "F"));
    }
}

