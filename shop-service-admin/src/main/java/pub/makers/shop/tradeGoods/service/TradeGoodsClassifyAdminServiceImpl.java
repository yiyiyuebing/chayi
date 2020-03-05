package pub.makers.shop.tradeGoods.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.tradeGoods.constans.ClassifyConstant;
import pub.makers.shop.tradeGoods.entity.TradeGoodsClassify;
import pub.makers.shop.tradeGoods.vo.TradeGoodsClassifyVo;

import java.util.*;

/**
 * Created by dy on 2017/5/25.
 */
@Service(version = "1.0.0")
public class TradeGoodsClassifyAdminServiceImpl implements TradeGoodsClassifyMgrBizService {

    private final static String queryTradeGoodsClassifyByParentIdStmt = "select * from trade_goods_classify where parent_id = ? and del_flag = 'F';";

    @Autowired
    private TradeGoodsClassifyService tradeGoodsClassifyService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Reference(version = "1.0.0")
    private TradeGoodBizService tradeGoodBizService;
    @Override
    public List<TradeGoodsClassifyVo> getTradeGoodsClassifyVoListByParentId(long parentId, Integer status) {


        Map<String , Object> map=new HashMap<String, Object>();
        map.put("parentId", parentId + "");
        map.put("status", status);

        String listPurchaseTradeGoodClassifyStmt= FreeMarkerHelper.getValueFromTpl("sql/tradeGoodsClassify/listTradeGoodsClassify.sql", map);
        List<TradeGoodsClassifyVo> classifyVos = new ArrayList<TradeGoodsClassifyVo>();
        RowMapper<TradeGoodsClassifyVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(TradeGoodsClassifyVo.class);
        classifyVos = jdbcTemplate.query(listPurchaseTradeGoodClassifyStmt, rowMapper);

//        List<TradeGoodsClassify> tradeGoodsClassifies = new ArrayList<TradeGoodsClassify>();
//
//        if (status != null) {
//            tradeGoodsClassifies = tradeGoodsClassifyService.list(Conds.get().eq("parent_id", parentId).eq("status", 1));
//        } else {
//            tradeGoodsClassifies = tradeGoodsClassifyService.list(Conds.get().eq("parent_id", parentId));
//        }
//        List<TradeGoodsClassifyVo> tradeGoodsClassifyVoss = Lists.newArrayList();
//        for (TradeGoodsClassify tradeGoodsClassify : tradeGoodsClassifies) {
//            TradeGoodsClassifyVo tradeGoodsClassifyVo = new TradeGoodsClassifyVo(tradeGoodsClassify);
//            tradeGoodsClassifyVoss.add(tradeGoodsClassifyVo);
//        }
        return classifyVos;
    }

    @Override
    public ResultList<TradeGoodsClassifyVo> getTradeGoodsClassfyList(String parentId, Integer status) {

        Map<String , Object> map=new HashMap<String, Object>();
        map.put("parentId", parentId);
        map.put("status", status);

        String listPurchaseTradeGoodClassifyStmt= FreeMarkerHelper.getValueFromTpl("sql/tradeGoodsClassify/listTradeGoodsClassify.sql", map);
        List<TradeGoodsClassifyVo> classifyVos = new ArrayList<TradeGoodsClassifyVo>();
        RowMapper<TradeGoodsClassifyVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(TradeGoodsClassifyVo.class);
        classifyVos = jdbcTemplate.query(listPurchaseTradeGoodClassifyStmt, rowMapper);
        List<TradeGoodsClassifyVo> tradeGoodsClassifyVoss = Lists.newArrayList();
        for (TradeGoodsClassifyVo tradeGoodsClassify : classifyVos) {
//            getPurchaseClassifyAttrs(tradeGoodsClassify);
            tradeGoodsClassify.setChildren(new ArrayList<TradeGoodsClassifyVo>());
            tradeGoodsClassifyVoss.add(tradeGoodsClassify);

        }
        ResultList<TradeGoodsClassifyVo> classifyVoResultList = new ResultList<TradeGoodsClassifyVo>();
        classifyVoResultList.setResultList(tradeGoodsClassifyVoss);
        return classifyVoResultList;
    }

    @Override
    public Map<String, Object> updateStatus(String ids, String status) {
        String[] Ids= ids.split(",");
        for(String id : Ids){
            tradeGoodsClassifyService.update(Update.byId(id).set("status", status));
            batchUpdateClassifyStatus(id, status);
        }
        tradeGoodBizService.updateClassifyValid();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", true);
        return result;
    }

    /**
     * 批量修改子级状态
     * @param id
     */
    private void batchUpdateClassifyStatus(String id, String status) {
        List<TradeGoodsClassify> purchaseTradeGoodClassifies = tradeGoodsClassifyService.list(Conds.get().eq("parent_id", id).eq("del_flag", "F"));
        if (purchaseTradeGoodClassifies.size() > 0) {
            for (TradeGoodsClassify purchaseTradeGoodClassify : purchaseTradeGoodClassifies) {
                tradeGoodsClassifyService.update(Update.byId(purchaseTradeGoodClassify.getId()).set("status", status));
                batchUpdateClassifyStatus(purchaseTradeGoodClassify.getId() + "", status);
            }
        }
    }

    @Override
    public Map<String, Object> deleteClassify(String ids) {
        String[] Ids = ids.split(",");
        for(String id : Ids){
            tradeGoodsClassifyService.update(Update.byId(id).set("del_flag", "T"));
            batchDeleteClassify(id);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", true);
        return result;
    }

    /**
     * 批量删除
     * @param id
     */
    private void batchDeleteClassify(String id) {
        List<TradeGoodsClassify> purchaseTradeGoodClassifies = tradeGoodsClassifyService.list(Conds.get().eq("parent_id", id).eq("del_flag", "F"));
        if (purchaseTradeGoodClassifies.size() > 0) {
            for (TradeGoodsClassify purchaseTradeGoodClassify : purchaseTradeGoodClassifies) {
                tradeGoodsClassifyService.update(Update.byId(id).set("del_flag", "T"));
                batchDeleteClassify(purchaseTradeGoodClassify.getId() + "");
            }
        }
    }

    private Long ids;

    @Override
    public TradeGoodsClassify saveTradeClassify(final Long userId, final TradeGoodsClassifyVo tradeGoodsClassifyVo) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                if (StringUtils.isBlank(tradeGoodsClassifyVo.getId())) {
                    TradeGoodsClassify purchaseClassifyAttr = new TradeGoodsClassify();
                    purchaseClassifyAttr.setId(IdGenerator.getDefault().nextId());
                    purchaseClassifyAttr.setCreateBy(userId);
                    purchaseClassifyAttr.setUpdateBy(userId);
                    Date date = new Date();
                    purchaseClassifyAttr.setCreateTime(date);
                    purchaseClassifyAttr.setDelFlag("F");
                    purchaseClassifyAttr.setUpdateTime(date);
                    purchaseClassifyAttr.setName(tradeGoodsClassifyVo.getName());
                    purchaseClassifyAttr.setOrderIndex(tradeGoodsClassifyVo.getOrderIndex());
                    purchaseClassifyAttr.setImgUrl(tradeGoodsClassifyVo.getImgUrl());
                    purchaseClassifyAttr.setParentId(StringUtils.isBlank(tradeGoodsClassifyVo.getParentId()) ? (ClassifyConstant.CARGO_CLASSIFY) : Long.parseLong(tradeGoodsClassifyVo.getParentId()));
                    if (!(ClassifyConstant.CARGO_CLASSIFY + "").equals(tradeGoodsClassifyVo.getParentId())) {
                        TradeGoodsClassify parent = tradeGoodsClassifyService.get(Conds.get().eq("parent_id", Long.parseLong(tradeGoodsClassifyVo.getParentId())));
                        if (parent != null && "0".equals(parent.getStatus())) {
                            purchaseClassifyAttr.setStatus(0);
                        } else {
                            purchaseClassifyAttr.setStatus(1);
                        }
                    } else {
                        purchaseClassifyAttr.setStatus(1);
                    }
                    tradeGoodsClassifyService.insert(purchaseClassifyAttr);
                   ids = purchaseClassifyAttr.getId();
                } else {
                    TradeGoodsClassify target = tradeGoodsClassifyService.getById(tradeGoodsClassifyVo.getId());
                    //修改内容
                    target.setName(tradeGoodsClassifyVo.getName() != null && !tradeGoodsClassifyVo.getName().trim().equals("") ? tradeGoodsClassifyVo.getName() : "");
                    target.setParentId(StringUtils.isNotBlank(tradeGoodsClassifyVo.getParentId()) ? Long.parseLong(tradeGoodsClassifyVo.getParentId()) : (ClassifyConstant.CARGO_CLASSIFY));
                    target.setOrderIndex(tradeGoodsClassifyVo.getOrderIndex() != null ? tradeGoodsClassifyVo.getOrderIndex() : 0);
                    target.setImgUrl(tradeGoodsClassifyVo.getImgUrl());
                    target.setUpdateTime(new Date());
                    target.setUpdateBy(userId);
                    tradeGoodsClassifyService.update(target);
                    ids =  Long.parseLong(tradeGoodsClassifyVo.getId());
                }
            }
        });
        TradeGoodsClassify tradeGoodsClassifyVo1 = new TradeGoodsClassify();
        tradeGoodsClassifyVo1 = tradeGoodsClassifyService.get(Conds.get().eq("id",ids));
        return tradeGoodsClassifyVo1;
    }

    @Override
    public ResultList<TradeGoodsClassifyVo> getTradeGoddsClassfyTree(Long parentId, Integer status) {
        List<TradeGoodsClassifyVo> classifyVos = new ArrayList<TradeGoodsClassifyVo>();
        RowMapper<TradeGoodsClassifyVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(TradeGoodsClassifyVo.class);
        classifyVos = jdbcTemplate.query(queryTradeGoodsClassifyByParentIdStmt, rowMapper, parentId);
        List<TradeGoodsClassifyVo> classifyVoList = new ArrayList<TradeGoodsClassifyVo>();
        for (TradeGoodsClassifyVo classifyVo : classifyVos) {
            classifyVoList.add(makeTrees(classifyVo, status));
        }
        ResultList<TradeGoodsClassifyVo> classifyVoResultList = new ResultList<TradeGoodsClassifyVo>();
        classifyVoResultList.setResultList(classifyVoList);
        return classifyVoResultList;

    }


    /**
     * 递归拼装树
     * @param classifyVo
     * @param status
     * @return
     */
    public TradeGoodsClassifyVo makeTrees(TradeGoodsClassifyVo classifyVo, Integer status) {
        List<TradeGoodsClassifyVo> classifyVos = new ArrayList<TradeGoodsClassifyVo>();
        RowMapper<TradeGoodsClassifyVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(TradeGoodsClassifyVo.class);
        classifyVos = jdbcTemplate.query(queryTradeGoodsClassifyByParentIdStmt, rowMapper, classifyVo.getId());
        for (TradeGoodsClassifyVo co : classifyVos) {
            TradeGoodsClassifyVo cvo = makeTrees(co, status);
            classifyVo.getChildren().add(cvo);
        }
        return classifyVo;
    }
}
