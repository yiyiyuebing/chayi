package pub.makers.shop.base.util.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.service.MsgTemplateMgrBizService;
import pub.makers.shop.base.service.MsgTemplateService;
import pub.makers.shop.base.service.SysDictService;
import pub.makers.shop.base.vo.MsgTemplateParams;
import pub.makers.shop.base.vo.MsgTemplateVo;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.baseOrder.enums.OrderBizType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/5/15.
 */
@Service(version = "1.0.0")
public class MsgTemplateAdminServiceImpl implements MsgTemplateMgrBizService {

    private final String listMsgTemplateStmt = "select * from msg_template where type like ? and title like ? and is_valid like ?" +
            "order by create_time desc limit ?,?";

    private final String countMsgTemplateStmt = "select count(*) from msg_template where type like ? and title like ? and is_valid like ?" +
            "order by create_time desc ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MsgTemplateService msgTemplateService;

    @Autowired
    private SysDictService sysDictService;

    public ResultList<MsgTemplateVo> listByCondition(MsgTemplateParams param, Paging pg) {

        String orderType = StringUtils.isNotBlank(param.getOrderType()) ? param.getOrderType() : "%%";
        String title = StringUtils.isNotBlank(param.getTitle()) ? "%" + param.getTitle() + "%" : "%%";
        String isValid = StringUtils.isNotBlank(param.getIsValid()) ? param.getIsValid() : "%%";

        RowMapper<MsgTemplateVo> msgTemplateVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(MsgTemplateVo.class);
        List<MsgTemplateVo> resultList = jdbcTemplate.query(listMsgTemplateStmt, msgTemplateVoRowMapper, orderType, title, isValid, pg.getPs(), pg.getPn());
        Number total = jdbcTemplate.queryForObject(countMsgTemplateStmt, Integer.class, orderType, title, isValid);
        ResultList<MsgTemplateVo> result = new ResultList<MsgTemplateVo>();
        result.setTotalRecords(total != null ? total.intValue() : 0);
        result.setResultList(resultList);
        return result;
    }

    public SysDict editTime() {

        SysDict sysDict = sysDictService.get(Conds.get().eq("dict_type", "order_timeout"));
        return sysDict;
    }

    public void saveTime(SysDict param) {

        sysDictService.update(Update.byId(param.getDictId()).set("value", param.getValue()));
    }

    public Map<String, Object> changeState(MsgTemplateParams param, Paging pg) {
        msgTemplateService.update(Update.byId(param.getId()).set("is_valid", param.getIsValid()));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", true);
        return result;
    }

    public void saveOrderMsg(MsgTemplateParams param) {

        msgTemplateService.update(Update.byId(param.getId()).set("title", param.getTitle())
                .set("content", param.getContent())
                .set("is_valid", param.getIsValid())
                .set("type", param.getOrderType()));
    }
}
