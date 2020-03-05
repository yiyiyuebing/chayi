package pub.makers.shop.logistics.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lantu.base.common.entity.BoolType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.service.SysDictService;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;

import java.util.*;

/**
 * Created by dy on 2017/4/26.
 */
@Service(version = "1.0.0")
public class ExpressCompanyAdminServiceImpl implements ExpressCompanyMgrBizService {

    private final String listExpressCompanyStmt = "select * from sys_dict where parent_id = 5 and value like ? order by order_num desc limit ?, ?;";
    private final String countExpressCompanyStmt = "select count(*) from sys_dict where parent_id = 5 and value like ?;";

    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public ResultList<SysDict> expressCompanyList(SysDict sysDict, Paging pg) {
        String value = StringUtils.isNotBlank(sysDict.getValue()) ? "%" + sysDict.getValue() + "%" : "%%";
        RowMapper<SysDict> sysDictRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(SysDict.class);
        List<SysDict> resultList = jdbcTemplate.query(listExpressCompanyStmt, sysDictRowMapper, value, pg.getPs(), pg.getPn());
        Number total = jdbcTemplate.queryForObject(countExpressCompanyStmt, Integer.class, value);
        ResultList<SysDict> result = new ResultList<SysDict>();
        result.setTotalRecords(total != null ? total.intValue() : 0);
        result.setResultList(resultList);
        return result;
    }

    @Override
    public SysDict saveOrUpdate(SysDict sysDict) {

        if (StringUtils.isBlank(sysDict.getDictId())) {
            sysDict.setDictId(IdGenerator.getDefault().nextId() + "");
            sysDict.setIsValid("T");
            sysDict.setParentId("5");
            sysDict.setDateCreated(new Date());
            sysDict.setLastUpdated(new Date());
            sysDictService.insert(sysDict);
        } else {
            SysDict sysDictOld = sysDictService.getById(sysDict.getDictId());
            sysDictOld.setDictType(sysDict.getDictType());
            sysDictOld.setValue(sysDict.getValue());
            sysDictOld.setOrderNum(sysDict.getOrderNum());
            sysDictOld.setMemo(sysDict.getMemo());
            sysDictOld.setCode(sysDict.getCode());
            sysDictOld.setLastUpdated(new Date());
            sysDictService.update(sysDictOld);
            sysDict = sysDictOld;
        }
        return sysDict;
    }

    @Override
    public SysDict delExpressCompanyById(String id) {
        sysDictService.deleteById(id);
        return null;
    }

    @Override
    public Map<String, Object> checkUictType(SysDict sysDict) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<SysDict> sysDicts = new ArrayList<SysDict>();
        if (StringUtils.isBlank(sysDict.getDictId())) {
            sysDicts = sysDictService.list(Conds.get().eq("code", sysDict.getCode()).eq("is_valid", BoolType.T.toString()));
        } else {
            sysDicts = sysDictService.list(Conds.get().eq("code", sysDict.getCode()).eq("is_valid", BoolType.T.toString()).ne("dict_id", sysDict.getDictId()));
        }
        if (sysDicts.size() > 0) {
            result.put("code", false);
            result.put("msg", "快递公司编码已存在，请重新输入");
        } else {
            result.put("code", true);
        }
        return result;
    }
}
