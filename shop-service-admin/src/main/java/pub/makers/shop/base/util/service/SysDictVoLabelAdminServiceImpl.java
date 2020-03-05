package pub.makers.shop.base.util.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lantu.base.common.entity.BoolType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.service.SysDictService;
import pub.makers.shop.base.service.SysDictVoLabelAdminService;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.base.vo.SysDictVo;


import java.sql.JDBCType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by devpc on 2017/7/24.
 */
@Service(version = "1.0.0")
public class SysDictVoLabelAdminServiceImpl implements SysDictVoLabelAdminService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SysDictService sysDictService;

    /**
     * 得到标签
     * @param label
     * @return
     */
    @Override
    public List<SysDictVo> getSysDictLabel(String label) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("label", label);
        RowMapper<SysDictVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(SysDictVo.class);
        String sql = FreeMarkerHelper.getValueFromTpl("sql/base.util/getSysDictVoLab.sql", paramMap);
        List<SysDictVo> list =  jdbcTemplate.query(sql, rowMapper);
        return list;
    }

    /**
     * 增加标签
     * @param sysDict
     * @return
     */
    @Override
    public boolean addSysDictLabel(SysDict sysDict) {
        String id = IdGenerator.getDefault().nextId() + "";
        sysDict.setDictId(id);
        sysDict.setCode(sysDict.getValue());
        sysDict.setDelFlag("F");
        sysDict.setIsValid("T");
        sysDictService.insert(sysDict);
        return true;
    }

    /**
     * 分页得到标签
     * @param
     * @param pg
     * @return   getSysDictLabelToShow.sql
     */
    @Override
    public ResultList<SysDict> getSysDictLabelToShow(SysDict sysDict,Paging pg) {
        /*Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("label", label);
        RowMapper<SysDict> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(SysDict.class);
        String sql = FreeMarkerHelper.getValueFromTpl("sql/base.util/getSysDictLabelToShow.sql", paramMap);
        ResultList<SysDict> list =  jdbcTemplate.query(sql, rowMapper);
*/
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("label", sysDict.getValue());
        paramMap.put("dict_type",sysDict.getDictType());
        String sql = FreeMarkerHelper.getValueFromTpl("sql/base.util/getSysDictLabelToShow.sql", paramMap);
        RowMapper<SysDict> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(SysDict.class);
        List<SysDict> list = jdbcTemplate.query(sql + " limit ?,? ", rowMapper, pg.getPs(), pg.getPn());
        Number total = jdbcTemplate.queryForObject("select count(0) from (" + sql +") nums ",null,Integer.class);
        ResultList<SysDict> resultList = new ResultList<SysDict>();
        resultList.setResultList(list);
        resultList.setTotalRecords(total!=null?total.intValue():0);
        return resultList;
        //return list;
    }

    /**
     *
     * 修改标签   可用于   改
     * @param sysDict
     * @return
     */
    @Override
    public  boolean updateSysDictLabel(SysDict sysDict){
        sysDictService.update(Update.byId(sysDict.getDictId()).set("value",sysDict.getValue()).set("order_num",sysDict.getOrderNum()));
        return true;
    }

    /**
     * 删除标签
     * @param dictId
     * @return
     */
    @Override
    public  boolean deleteSysDictLabel(String dictId){
        sysDictService.update(Update.byId(dictId).set("del_flag","T").set("is_valid","F"));
        return true;
    }

    /**
     * 查询单个
     * @param dictid
     * @return
     */
    @Override
    public   SysDict getOneSysDictLabel(String dictid){
        Conds conds = new Conds().eq("dict_id",dictid).eq("del_flag", "F").eq("is_valid","T");
        return   sysDictService.get(conds);
    }

}
