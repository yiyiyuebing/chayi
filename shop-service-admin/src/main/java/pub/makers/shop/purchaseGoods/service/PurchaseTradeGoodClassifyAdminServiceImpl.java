package pub.makers.shop.purchaseGoods.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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
import pub.makers.shop.purchaseGoods.entity.PurchaseClassify;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassifyAttr;
import pub.makers.shop.purchaseGoods.entity.PurchaseTradeGoodClassify;
import pub.makers.shop.purchaseGoods.vo.PurchaseClassifyAttrVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseClassifyVo;
import pub.makers.shop.tradeGoods.constans.ClassifyConstant;

import java.util.*;

/**
 * Created by dy on 2017/5/26.
 */
@Service(version="1.0.0")
public class PurchaseTradeGoodClassifyAdminServiceImpl implements PurchaseTradeGoodClassifyMgrBizService {

    private final static String purchaseClassifyAttrParentList = "select * from purchase_classify_attr where pur_classify_id = ? and parent_id is null;";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PurchaseTradeGoodClassifyService purchaseTradeGoodClassifyService;

    @Autowired
    private PurchaseClassifyAttrService purchaseClassifyAttrService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Reference(version = "1.0.0")
    private PurchaseGoodsBizService purchaseGoodsBizService;

    @Override
    public ResultList<PurchaseClassifyVo> getPurchaseTradeGoddsClassfyTree(Long parentId, Integer status) {

        Map<String , Object> map=new HashMap<String, Object>();
        map.put("parentId", parentId);
        map.put("status", status);

        String listPurchaseTradeGoodClassifyStmt= FreeMarkerHelper.getValueFromTpl("sql/purchaseGoodsClassify/listPurchaseGoodsClassify.sql", map);

        List<PurchaseClassifyVo> classifyVos = new ArrayList<PurchaseClassifyVo>();
        RowMapper<PurchaseClassifyVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PurchaseClassifyVo.class);
        classifyVos = jdbcTemplate.query(listPurchaseTradeGoodClassifyStmt, rowMapper);
        List<PurchaseClassifyVo> classifyVoList = new ArrayList<PurchaseClassifyVo>();
        for (PurchaseClassifyVo classifyVo : classifyVos) {
            if ("1".equals(classifyVo.getParentId())) {
                getPurchaseClassifyAttrs(classifyVo);
            }
            classifyVoList.add(makeTrees(classifyVo, status));
        }
        ResultList<PurchaseClassifyVo> classifyVoResultList = new ResultList<PurchaseClassifyVo>();
        classifyVoResultList.setResultList(classifyVoList);
        return classifyVoResultList;

    }

    private void getPurchaseClassifyAttrs(PurchaseClassifyVo classifyVo) {
        RowMapper<PurchaseClassifyAttrVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PurchaseClassifyAttrVo.class);
        List<PurchaseClassifyAttrVo> purchaseClassifyAttrVos = jdbcTemplate.query(purchaseClassifyAttrParentList, rowMapper, classifyVo.getId());

        for (PurchaseClassifyAttrVo purchaseClassifyAttr : purchaseClassifyAttrVos) {
            List<PurchaseClassifyAttrVo> purchaseClassifyAttrVosChildren = Lists.newArrayList();
            List<PurchaseClassifyAttr> purchaseClassifyAttrs = purchaseClassifyAttrService.list(Conds.get().eq("parent_id", purchaseClassifyAttr.getId()).eq("pur_classify_id", classifyVo.getId()));
            for (PurchaseClassifyAttr classifyAttr : purchaseClassifyAttrs) {
                PurchaseClassifyAttrVo purchaseClassifyAttrVo = new PurchaseClassifyAttrVo();
                BeanUtils.copyProperties(classifyAttr, purchaseClassifyAttrVo);
                purchaseClassifyAttrVosChildren.add(purchaseClassifyAttrVo);
            }
            purchaseClassifyAttr.setChildren(purchaseClassifyAttrVosChildren);
        }
        classifyVo.setAttrs(purchaseClassifyAttrVos);

    }

    @Override
    public Map<String, Object> updateStatus(String ids, String status) {
        String[] Ids= ids.split(",");
        for(String id : Ids){
            purchaseTradeGoodClassifyService.update(Update.byId(id).set("status", status));
            batchUpdateClassifyStatus(id, status);
        }
        purchaseGoodsBizService.updateClassifyValid();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", true);
        return result;
    }



    /**
     * 批量修改子级状态
     * @param id
     */
    private void batchUpdateClassifyStatus(String id, String status) {
        List<PurchaseTradeGoodClassify> purchaseTradeGoodClassifies = purchaseTradeGoodClassifyService.list(Conds.get().eq("parent_id", id).eq("del_flag", "F"));
        if (purchaseTradeGoodClassifies.size() > 0) {
            for (PurchaseTradeGoodClassify purchaseTradeGoodClassify : purchaseTradeGoodClassifies) {
                purchaseTradeGoodClassifyService.update(Update.byId(purchaseTradeGoodClassify.getId()).set("status", status));
                batchUpdateClassifyStatus(purchaseTradeGoodClassify.getId(), status);
            }
        }
    }


    @Override
    public Map<String, Object> deleteClassify(String ids) {
        String[] Ids = ids.split(",");
        for(String id : Ids){
            purchaseTradeGoodClassifyService.update(Update.byId(id).set("del_flag", "T"));
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
        List<PurchaseTradeGoodClassify> purchaseTradeGoodClassifies = purchaseTradeGoodClassifyService.list(Conds.get().eq("parent_id", id).eq("del_flag", "F"));
        if (purchaseTradeGoodClassifies.size() > 0) {
            for (PurchaseTradeGoodClassify purchaseTradeGoodClassify : purchaseTradeGoodClassifies) {
                purchaseTradeGoodClassifyService.update(Update.byId(id).set("del_flag", "T"));
                batchDeleteClassify(purchaseTradeGoodClassify.getId());
            }
        }
    }

    @Override
    public List<PurchaseClassifyVo> getGoodsClassifyList(String childId) {
        List<PurchaseTradeGoodClassify> cargoClassifies = new ArrayList<PurchaseTradeGoodClassify>();
        PurchaseTradeGoodClassify cargoClassify = purchaseTradeGoodClassifyService.get(Conds.get().eq("id", childId).eq("del_flag", "F"));
        if (cargoClassify != null) {
            cargoClassifies.add(cargoClassify);
        }

        while (cargoClassify != null && cargoClassify.getParentId() != null) {
            cargoClassify = purchaseTradeGoodClassifyService.get(Conds.get().eq("id", cargoClassify.getParentId()).eq("del_flag", "F"));
            if (cargoClassify != null && cargoClassify.getParentId() != null) {
                cargoClassifies.add(cargoClassify);
            }
        }

        List<PurchaseClassifyVo> cargoClassifySampleVos = Lists.newArrayList();

        for (int i = cargoClassifies.size() - 1; i >= 0; i--) {
            PurchaseClassifyVo cargoClassifySampleVo = new PurchaseClassifyVo();
            cargoClassifySampleVo.setId(cargoClassifies.get(i).getId());
            cargoClassifySampleVo.setName(cargoClassifies.get(i).getName());
            cargoClassifySampleVos.add(cargoClassifySampleVo);
        }
        return cargoClassifySampleVos;
    }

    @Override
    public List<PurchaseClassifyVo> queryParents(Long parentId, Integer status, String storeLevel) {
//        List<PurchaseTradeGoodClassify> tradeGoodsClassifies = purchaseTradeGoodClassifyService.list(Conds.get().eq("parent_id", parentId).eq("status", 1));

        Map<String , Object> map=new HashMap<String, Object>();
        map.put("parentId", parentId + "");
        map.put("status", status);

        String listPurchaseTradeGoodClassifyStmt= FreeMarkerHelper.getValueFromTpl("sql/purchaseGoodsClassify/listPurchaseGoodsClassify.sql", map);
        List<PurchaseClassifyVo> classifyVos = new ArrayList<PurchaseClassifyVo>();
        RowMapper<PurchaseClassifyVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PurchaseClassifyVo.class);
        classifyVos = jdbcTemplate.query(listPurchaseTradeGoodClassifyStmt, rowMapper);
        List<PurchaseClassifyVo> tradeGoodsClassifyVoss = Lists.newArrayList();
        for (PurchaseClassifyVo tradeGoodsClassify : classifyVos) {
            getPurchaseClassifyAttrs(tradeGoodsClassify);
            tradeGoodsClassifyVoss.add(tradeGoodsClassify);
        }
        return tradeGoodsClassifyVoss;
    }

    @Override
    public ResultList<PurchaseClassifyVo> getPurchaseClassfyList(String parentId, Integer status) {
        Map<String , Object> map=new HashMap<String, Object>();
        map.put("parentId", parentId);
        map.put("status", status);

        String listPurchaseTradeGoodClassifyStmt= FreeMarkerHelper.getValueFromTpl("sql/purchaseGoodsClassify/listPurchaseGoodsClassify.sql", map);
        List<PurchaseClassifyVo> classifyVos = new ArrayList<PurchaseClassifyVo>();
        RowMapper<PurchaseClassifyVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PurchaseClassifyVo.class);
        classifyVos = jdbcTemplate.query(listPurchaseTradeGoodClassifyStmt, rowMapper);
        List<PurchaseClassifyVo> tradeGoodsClassifyVoss = Lists.newArrayList();
        for (PurchaseClassifyVo tradeGoodsClassify : classifyVos) {
            getPurchaseClassifyAttrs(tradeGoodsClassify);
            tradeGoodsClassify.setChildren(new ArrayList<PurchaseClassifyVo>());
            tradeGoodsClassifyVoss.add(tradeGoodsClassify);

        }
        ResultList<PurchaseClassifyVo> classifyVoResultList = new ResultList<PurchaseClassifyVo>();
        classifyVoResultList.setResultList(tradeGoodsClassifyVoss);
        return classifyVoResultList;
    }

    private String ids;

    @Override
    public PurchaseClassifyVo savePurchaseClassify(final Long userId, final PurchaseClassifyVo purchaseClassifyVo) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                if (StringUtils.isBlank(purchaseClassifyVo.getId())) {
                    PurchaseTradeGoodClassify purchaseClassify = new PurchaseTradeGoodClassify();
                    purchaseClassify.setId(IdGenerator.getDefault().nextId() + "");
                    purchaseClassify.setCreateBy(userId + "");
                    purchaseClassify.setUpdateBy(userId + "");
                    Date date = new Date();
                    purchaseClassify.setCreateTime(date);
                    purchaseClassify.setDelFlag("F");
                    purchaseClassify.setUpdateTime(date);
                    purchaseClassify.setName(purchaseClassifyVo.getName());
                    purchaseClassify.setOrderIndex(purchaseClassifyVo.getOrderIndex() + "");
                    purchaseClassify.setImgUrl(purchaseClassifyVo.getImgUrl());
                    purchaseClassify.setParentId(purchaseClassifyVo.getParentId() == null ? (ClassifyConstant.CARGO_CLASSIFY + "") : purchaseClassifyVo.getParentId() + "");
                    if (!(ClassifyConstant.CARGO_CLASSIFY + "").equals(purchaseClassifyVo.getParentId())) {
                        PurchaseTradeGoodClassify parent = purchaseTradeGoodClassifyService.get(Conds.get().eq("id", Long.parseLong(purchaseClassifyVo.getParentId())));
                        if (parent != null && "0".equals(parent.getStatus())) {
                            purchaseClassify.setStatus("0");
                        } else {
                            purchaseClassify.setStatus("1");
                        }
                        parent.getStoreLevel();
                        purchaseClassify.setStoreLevel(parent.getStoreLevel());
                    } else {
                        purchaseClassify.setStoreLevel(purchaseClassifyVo.getStoreLevel());
                        purchaseClassify.setStatus("1");
                    }
                    purchaseTradeGoodClassifyService.insert(purchaseClassify);
                    //新增属性
                    List<PurchaseClassifyAttrVo> attrVos = purchaseClassifyVo.getAttrs();
                    for (PurchaseClassifyAttrVo attrVo : attrVos) {
                        try {
                            makeFull(attrVo, purchaseClassifyVo.getId(), null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    ids = purchaseClassify.getId();
                } else {

                    PurchaseTradeGoodClassify target = purchaseTradeGoodClassifyService.getById(purchaseClassifyVo.getId());
                    //修改内容
                    target.setName(purchaseClassifyVo.getName());
                    target.setOrderIndex(purchaseClassifyVo.getOrderIndex() + "");
                    target.setImgUrl(purchaseClassifyVo.getImgUrl());


                    // 如果修改了顶级的等级分类级联修改下级所有分类的等级值
                    if ("1".equals(target.getParentId())) {

                        PurchaseTradeGoodClassify record = new PurchaseTradeGoodClassify();
                        record.setStoreLevel(purchaseClassifyVo.getStoreLevel());
                        record.setId(purchaseClassifyVo.getId());
                        record.setUpdateTime(new Date());
                        record.setUpdateBy(userId + "");
                        record.setName(purchaseClassifyVo.getName());
                        record.setOrderIndex(purchaseClassifyVo.getOrderIndex() + "");
                        record.setImgUrl(purchaseClassifyVo.getImgUrl());
                        record.setParentId(purchaseClassifyVo.getParentId());
                        update(record);
  //                      purchaseTradeGoodClassifyService.update(target);
                    } else {
                        target.setUpdateTime(new Date());
                        target.setUpdateBy(userId + "");
                        target.setStoreLevel(getClassStoreLevel(target.getParentId()));
                        purchaseTradeGoodClassifyService.update(target);
                    }
                    ids =  purchaseClassifyVo.getId();
                    //删除相关属性
                    purchaseClassifyAttrService.delete(Conds.get().eq("pur_classify_id", purchaseClassifyVo.getId()));
                    //新增属性
                    List<PurchaseClassifyAttrVo> attrVos = purchaseClassifyVo.getAttrs();
                    if (attrVos != null) {
                        for (PurchaseClassifyAttrVo attrVo : attrVos) {
                            try {
                                makeFull(attrVo, purchaseClassifyVo.getId(), null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }


                }
            }
        });

        Map<String , Object> map=new HashMap<String, Object>();
        map.put("ids", ids);
        String listPurchaseTradeGoodClassifyStmt= FreeMarkerHelper.getValueFromTpl("sql/purchaseGoodsClassify/getOnePurchaseGoodsClassify.sql", map);

        List<PurchaseClassifyVo> classifyVos = new ArrayList<PurchaseClassifyVo>();
        RowMapper<PurchaseClassifyVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PurchaseClassifyVo.class);
        classifyVos = jdbcTemplate.query(listPurchaseTradeGoodClassifyStmt, rowMapper);
        if ("1".equals(classifyVos.get(0).getParentId())) {
            getPurchaseClassifyAttrs(classifyVos.get(0));
        }
        //classifyVos.add(makeTrees(classifyVos.get(0), 1));
       /* PurchaseTradeGoodClassify purchaseTradeGoodClassify = new PurchaseTradeGoodClassify();
        purchaseTradeGoodClassify = purchaseTradeGoodClassifyService.get(Conds.get().eq("id",ids));
        if(purchaseTradeGoodClassify.getStoreLevel() != null){
            String[] jiamenshan = purchaseTradeGoodClassify.getStoreLevel().split(",");
        }*/
        return classifyVos.get(0);
    }

    public String getClassStoreLevel(String parentId) {
        PurchaseTradeGoodClassify purchaseClassify = purchaseTradeGoodClassifyService.getById(parentId);
        if (!(ClassifyConstant.CARGO_CLASSIFY + "").equals(purchaseClassify.getParentId())) {
            getClassStoreLevel(purchaseClassify.getParentId());
        }
        return purchaseClassify.getStoreLevel();
    }

    private void update(PurchaseTradeGoodClassify record) {
        purchaseTradeGoodClassifyService.update(record);
        List<PurchaseTradeGoodClassify> list = purchaseTradeGoodClassifyService.list(Conds.get().eq("parent_id", Long.valueOf(record.getId())));
        if (list.size() == 0) {
            return;
        }
        for (PurchaseTradeGoodClassify pcv : list) {
            pcv.setStatus(record.getStatus());
            pcv.setUpdateBy(record.getUpdateBy());
            pcv.setUpdateTime(record.getUpdateTime());
            pcv.setStoreLevel(record.getStoreLevel());
            update(pcv);
        }
    }

    private PurchaseClassifyAttrVo makeFull(PurchaseClassifyAttrVo attrVo, String classId, String parentId) throws Exception {
        PurchaseClassifyAttr purchaseClassifyAttr = new PurchaseClassifyAttr();
        purchaseClassifyAttr.setId(IdGenerator.getDefault().nextId() + "");
        purchaseClassifyAttr.setPurClassifyId(classId);
        purchaseClassifyAttr.setParentId(parentId);
        purchaseClassifyAttr.setStatus(1);
        purchaseClassifyAttr.setName(attrVo.getName());
        purchaseClassifyAttr.setCreateTime(new Date());
        purchaseClassifyAttr.setUpdateTime(new Date());
        List<PurchaseClassifyAttrVo> attrVos = attrVo.getChildren();
        purchaseClassifyAttrService.insert(purchaseClassifyAttr);

        if (attrVos != null && attrVos.size() > 0) {
            for (PurchaseClassifyAttrVo vo : attrVos) {
                makeFull(vo, classId, purchaseClassifyAttr.getId());
            }
        }

        return attrVo;
    }

    /**
     * 递归拼装树
     * @param classifyVo
     * @param status
     * @return
     */
    public PurchaseClassifyVo makeTrees(PurchaseClassifyVo classifyVo, Integer status) {
        Map<String , Object> map=new HashMap<String, Object>();
        map.put("parentId", classifyVo.getId());
        map.put("status", status);
        String listPurchaseTradeGoodClassifyStmt= FreeMarkerHelper.getValueFromTpl("sql/purchaseGoodsClassify/listPurchaseGoodsClassify.sql", map);
        List<PurchaseClassifyVo> classifyVos = new ArrayList<PurchaseClassifyVo>();
        RowMapper<PurchaseClassifyVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PurchaseClassifyVo.class);
        classifyVos = jdbcTemplate.query(listPurchaseTradeGoodClassifyStmt, rowMapper);
        List<PurchaseClassifyVo> purchaseClassifyVos = Lists.newArrayList();
        for (PurchaseClassifyVo co : classifyVos) {
            getPurchaseClassifyAttrs(co);
            PurchaseClassifyVo cvo = makeTrees(co, status);
            purchaseClassifyVos.add(cvo);
        }
        classifyVo.setChildren(purchaseClassifyVos);
        return classifyVo;
    }
    
}