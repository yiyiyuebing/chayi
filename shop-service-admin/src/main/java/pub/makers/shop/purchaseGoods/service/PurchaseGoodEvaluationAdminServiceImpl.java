package pub.makers.shop.purchaseGoods.service;

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
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodEvaluation;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodEvaluationManageVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodEvaluationParams;

import java.util.*;

@Service(version="1.0.0")
public class PurchaseGoodEvaluationAdminServiceImpl implements PurchaseGoodEvaluationMgrBizService{

    private final String listGoodEvaluationStmt = "select pge.id, pge.pur_goods_id as purGoodsId, pge.image  AS imageId ,pge.content , pge.score, cs.`name` as goodName, cs.code as skuName, po.order_no as orderId, (CASE pge.user_name " +
            "WHEN '匿名' THEN '匿名' " +
            "ELSE ss.`name` END) AS userName,  pge.evaluate_time as evaluateTime, pge.is_hide as isHide, pge.is_replied as isReplied, pge.replied_id as repliedId from purchase_goods_evaluation pge " +
            "left join purchase_goods_sku pgs on pgs.id = pge.good_sku_id " +
            "left join cargo_sku cs on cs.id = pgs.cargo_sku_id " +
            "LEFT JOIN store_subbranch ss ON ss.id=pge.`user` "+
            "left join purchase_order po on po.id = pge.order_id " +
            "where (cs.code like ? or po.order_no like ? or cs.`name` like ?)and pge.score like ? order by pge.evaluate_time desc limit ?,?;";
    private final String countGoodEvaluationStmt = "select count(*) from purchase_goods_evaluation pge " +
            "left join purchase_goods_sku pgs on pgs.id = pge.good_sku_id " +
            "left join cargo_sku cs on cs.id = pgs.cargo_sku_id " +
            "left join purchase_order po on po.id = pge.order_id " +
            "where (cs.code like ? or po.order_no like ? or cs.`name` like ?)and pge.score like ? order by pge.evaluate_time desc;";
    private final String repliedContentStmt ="select * from purchase_goods_evaluation where replied_id=?;";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PurchaseGoodEvaluationService purchaseGoodEvaluationService;
    @Reference(version = "1.0.0")
    private CargoImageBizService cargoImageBizService;
    @Override
    public ResultList<PurchaseGoodEvaluationManageVo> listByCondition(PurchaseGoodEvaluationParams param, Paging pg) {
        String score = StringUtils.isNotBlank(param.getScore()) ? param.getScore() : "%%";
        String orderId = StringUtils.isNotBlank(param.getKeyword()) ? "%" + param.getKeyword() + "%" : "%%";
        String skuName = StringUtils.isNotBlank(param.getKeyword()) ? "%" + param.getKeyword() + "%" : "%%";
        String goodName = StringUtils.isNotBlank(param.getKeyword()) ? "%" + param.getKeyword() + "%" : "%%";

        //封装返回对象
        RowMapper<PurchaseGoodEvaluationManageVo> goodEvaluationManageVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PurchaseGoodEvaluationManageVo.class);
        //查询用户评价数据列表
        List<PurchaseGoodEvaluationManageVo> resultList = jdbcTemplate.query(listGoodEvaluationStmt, goodEvaluationManageVoRowMapper, skuName,orderId,goodName,score, pg.getPs(), pg.getPn());

        //遍历用户评价数据，查找对应回复评价
        for (PurchaseGoodEvaluationManageVo goodEvaluationManageVo : resultList) {
            List<PurchaseGoodEvaluationManageVo> replyList = new ArrayList<PurchaseGoodEvaluationManageVo>();
            //利用replied_id字段分别查找回复评价
            replyList = jdbcTemplate.query(repliedContentStmt, goodEvaluationManageVoRowMapper, goodEvaluationManageVo.getId());
            goodEvaluationManageVo.setReplyEvaluationList(replyList);
        }

        Number total = jdbcTemplate.queryForObject(countGoodEvaluationStmt, Integer.class,  skuName,orderId,goodName,score);
        ResultList<PurchaseGoodEvaluationManageVo> result = new ResultList<PurchaseGoodEvaluationManageVo>();
        result.setTotalRecords(total != null ? total.intValue() : 0);
        result.setResultList(resultList);
        return result;
    }
    @Override
    public Map<String, Object> updateEvaluationIsHide(PurchaseGoodEvaluationParams param, Paging pg){
        purchaseGoodEvaluationService.update(Update.byId(param.getId()).set("is_hide", param.getIsHide()));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", true);
        return result;
    }
    @Override
    public Map<String, Object> updateEvaluationIsReplied(PurchaseGoodEvaluationParams param, Map<String, Object> loginInfo){
        purchaseGoodEvaluationService.update(Update.byId(param.getId()).set("is_replied", param.getIsReplied()));
        PurchaseGoodEvaluation purchaseGoodEvaluation = new PurchaseGoodEvaluation();
        //IdGenerator.getDefault().nextId()随机得到id
        purchaseGoodEvaluation.setId(IdGenerator.getDefault().nextId());
        //通过key得到value，得到的是Object类型，需要转成String类型
        purchaseGoodEvaluation.setPurGoodsId(param.getPurGoodsId());
        purchaseGoodEvaluation.setUser(loginInfo.get("staffId").toString());
        purchaseGoodEvaluation.setUserName(loginInfo.get("staffName").toString());
        purchaseGoodEvaluation.setContent(param.getContent());
        purchaseGoodEvaluation.setIsHide("1");
        purchaseGoodEvaluation.setRepliedId(param.getId());
        purchaseGoodEvaluation.setEvaluateTime(new Date(System.currentTimeMillis()));
        purchaseGoodEvaluationService.insert(purchaseGoodEvaluation);
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
