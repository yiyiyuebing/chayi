package pub.makers.shop.tradeGoods.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.cargo.entity.vo.ImageGroupVo;
import pub.makers.shop.cargo.service.CargoImageBizService;
import pub.makers.shop.tradeGoods.entity.GoodEvaluation;
import pub.makers.shop.tradeGoods.vo.GoodEvaluationManageVo;
import pub.makers.shop.tradeGoods.vo.GoodEvaluationParams;

import java.util.*;

@Service(version = "1.0.0")
public class GoodEvaluationAdminServiceImpl implements GoodEvaluationMgrBizService {

    private final String listGoodEvaluationStmt = "select ge.id, ge.content , ge.score, cs.`name` as goodName,ge.image AS imageId ,cs.code as skuName, i.name as orderId, (CASE ge.user_name " +
            "WHEN '匿名' THEN '匿名' " +
            " ELSE wui.nickname END) AS userName,  ge.evaluate_time as evaluateTime, ge.is_hide as isHide, ge.is_replied as isReplied, ge.replied_id as repliedId from good_evaluation ge  " +
            "left join trade_good_sku tgs on tgs.id = ge.good_sku_id " +
            "left join cargo_sku cs on cs.id = tgs.cargo_sku_id " +
            "LEFT JOIN weixin_user_info wui ON ge.`user`=wui.ID "+
            "left join indent i on i.id = ge.order_id " +
            "where (cs.code like ? or i.name like ? or cs.`name` like ? )and ge.score like ? order by ge.evaluate_time desc limit ?, ?;";
    private final String countGoodEvaluationStmt = "select count(*) from good_evaluation ge " +
            "left join trade_good_sku tgs on tgs.id = ge.good_sku_id " +
            "left join cargo_sku cs on cs.id = tgs.cargo_sku_id " +
            "left join indent i on i.id = ge.order_id " +
            "where (cs.code like ? or i.name like ? or cs.`name` like ? )and ge.score like ?;";
    private final String repliedContentStmt ="select * from good_evaluation where replied_id=?;";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private GoodEvaluationService goodEvaluationService;
    @Reference(version = "1.0.0")
    private CargoImageBizService cargoImageBizService;

    @Override
    public ResultList<GoodEvaluationManageVo> listByCondition(GoodEvaluationParams param, Paging pg) {
        String score = StringUtils.isNotBlank(param.getScore()) ? param.getScore() : "%%";
        String orderId = StringUtils.isNotBlank(param.getKeyword()) ? "%" + param.getKeyword() + "%" : "%%";
        String skuName = StringUtils.isNotBlank(param.getKeyword()) ? "%" + param.getKeyword() + "%" : "%%";
        String goodName = StringUtils.isNotBlank(param.getKeyword()) ? "%" + param.getKeyword() + "%" : "%%";

        //封装返回对象
        RowMapper<GoodEvaluationManageVo> goodEvaluationManageVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(GoodEvaluationManageVo.class);
        //查询用户评价数据列表
        List<GoodEvaluationManageVo> resultList = jdbcTemplate.query(listGoodEvaluationStmt, goodEvaluationManageVoRowMapper, skuName,orderId,goodName,score, pg.getPs(), pg.getPn());

        //遍历用户评价数据，查找对应回复评价
        for (GoodEvaluationManageVo goodEvaluationManageVo : resultList) {
            List<GoodEvaluationManageVo> replyList = new ArrayList<GoodEvaluationManageVo>();
            //利用replied_id字段分别查找回复评价
            replyList = jdbcTemplate.query(repliedContentStmt, goodEvaluationManageVoRowMapper, goodEvaluationManageVo.getId());
            goodEvaluationManageVo.setReplyEvaluationList(replyList);
        }

        Number total = jdbcTemplate.queryForObject(countGoodEvaluationStmt, Integer.class,  skuName,orderId,goodName,score);
        ResultList<GoodEvaluationManageVo> result = new ResultList<GoodEvaluationManageVo>();
        result.setTotalRecords(total != null ? total.intValue() : 0);
        result.setResultList(resultList);
        return result;
    }
    @Override
    public Map<String, Object> updateEvaluationIsHide(GoodEvaluationParams param, Paging pg){
        goodEvaluationService.update(Update.byId(param.getId()).set("is_hide", param.getIsHide()));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", true);
        return result;
    }
    @Override
    public Map<String, Object> updateEvaluationIsReplied(GoodEvaluationParams param, Map<String, Object> loginInfo){
        goodEvaluationService.update(Update.byId(param.getId()).set("is_replied", param.getIsReplied()));
        GoodEvaluation goodEvaluation = new GoodEvaluation();
        //IdGenerator.getDefault().nextId()随机得到id
        goodEvaluation.setId(IdGenerator.getDefault().nextId());
        //通过key得到value，得到的是Object类型，需要转成String类型
        goodEvaluation.setUser(loginInfo.get("staffId").toString());
        goodEvaluation.setUserName(loginInfo.get("staffName").toString());
        goodEvaluation.setContent(param.getContent());
        goodEvaluation.setRepliedId(param.getId());
        goodEvaluation.setIsHide("1");
        goodEvaluation.setEvaluateTime(new Date(System.currentTimeMillis()));
        goodEvaluationService.insert(goodEvaluation);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", true);
        //result只有<code,true>一对值
        return result;
    }

    @Override
    public Map<String, Object> getImageById (String ids) {
        List<String> list = Lists.newArrayList();
        list.add(ids);
        Map<String, ImageGroupVo> map = cargoImageBizService.getImageGroup(list);
        Map<String, Object> result = new HashMap<String, Object>();
        if (map.get(ids) != null) {
            result.put("code", true);
            result.put("image", map.get(ids));  //url
        }
        return result;
    }
}
