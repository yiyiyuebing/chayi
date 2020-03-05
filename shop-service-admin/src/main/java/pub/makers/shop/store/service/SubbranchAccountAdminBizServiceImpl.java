package pub.makers.shop.store.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.lantu.base.common.entity.BoolType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.vo.SubbranchVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/4/26.
 */
@Service(version="1.0.0")
public class SubbranchAccountAdminBizServiceImpl implements SubbranchAccountMgrBizService {

    private final String listSubbranchAccountByParentId = "select * from store_subbranch ss where ss.is_sub_account = 'T' and ss.del_flag = 'F' and ss.user_name like ? and ss.parent_subranch_id = ? order by ss.create_time desc limit ?, ?;";
    private final String countSubbranchAccountByParentId = "select count(*) from store_subbranch ss where ss.is_sub_account = 'T' and ss.del_flag = 'F' and ss.user_name like ? and ss.parent_subranch_id = ?;";

    @Reference(version="1.0.0")
    private SubbranchAccountBizService subbranchAccountBizService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ResultList<SubbranchVo> listSubbranchAccountByParent(SubbranchVo subbranchVo, Paging paging) {
        String userName = StringUtils.isNotBlank(subbranchVo.getUserName()) ? "%" + subbranchVo.getUserName() + "%" : "%%";
        List listParams = new ArrayList();
        List countParams = new ArrayList();

        listParams.add(userName);
        listParams.add(subbranchVo.getId());
        listParams.add(paging.getPs());
        listParams.add(paging.getPn());

        countParams.add(userName);
        countParams.add(subbranchVo.getId());

        RowMapper<SubbranchVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(SubbranchVo.class);
        List<SubbranchVo> subbranchList = jdbcTemplate.query(listSubbranchAccountByParentId, rowMapper, listParams.toArray());
        Number total = jdbcTemplate.queryForObject(countSubbranchAccountByParentId, Integer.class, countParams.toArray());
        ResultList<SubbranchVo> result = new ResultList<SubbranchVo>();
        result.setTotalRecords(total != null ? total.intValue() : 0);
        result.setResultList(subbranchList);
        return result;
    }

    @Override
    public Map<String, Object> checkSubAccountMobile(String mobile, Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        String queryId = id == null ? "%%" : id + "";
        List<Subbranch> subbranchList = subbranchAccountBizService.checkAccountMobile(mobile,id);
        if (subbranchList.size() > 0) {
            result.put("code", false);
            result.put("msg", "手机号已经存在！");
        } else {
            result.put("code", true);
        }
        return result;
    }
}
