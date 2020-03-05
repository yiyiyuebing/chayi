package pub.makers.shop.logistics.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.service.SysDictService;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.cargo.entity.CargoClassify;
import pub.makers.shop.cargo.service.CargoClassifyService;
import pub.makers.shop.logistics.entity.*;
import pub.makers.shop.logistics.enums.FreightTplRelType;
import pub.makers.shop.logistics.vo.FreightTplParams;
import pub.makers.shop.logistics.vo.FreightTplRelParams;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassify;
import pub.makers.shop.purchaseGoods.service.PurchaseClassifyService;
import pub.makers.shop.purchaseGoods.vo.ClassifyVo;
import pub.makers.shop.tradeGoods.entity.TradeGoodsClassify;
import pub.makers.shop.tradeGoods.service.TradeGoodsClassifyService;

import java.util.*;

/**
 * Created by dy on 2017/4/18.
 */
@Service(version = "1.0.0")
public class FreightTplAdminServiceImpl implements FreightTplMgrBizService{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FreightTplService freightTplService;
    @Autowired
    private FreightShippingService freightShippingService;
    @Autowired
    private FreightShippingItemService freightShippingItemService;
    @Autowired
    private FreightPinkageService freightPinkageService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private FreightTplRelService freightTplRelService;
    @Autowired
    private FreightTplGoodRelService freightTplGoodRelService;
    @Autowired
    private CargoClassifyService cargoClassifyService;
    @Autowired
    private TradeGoodsClassifyService tradeGoodsClassifyService;
    @Autowired
    private PurchaseClassifyService purchaseClassifyService;
    @Autowired
    private TransactionTemplate transcationTemplate;



    private final String listFreightTplByParamsStmt = "SELECT pft.tpl_id AS id, pft. NAME AS name, pft.price_type AS priceType FROM pd_freight_tpl pft LEFT JOIN pd_freight_pinkage pfp ON pfp.tpl_id = pft.tpl_id LEFT JOIN pd_freight_shipping pfs ON pfs.tpl_id = pft.tpl_id where 1=1 AND pft.is_valid = 'T' and pft.del_flag = 'F' and (pfp.servicer_id like ? or pfs.servicer_id like ?) AND pft.name LIKE ? AND pft.rel_type like ? GROUP BY pft.tpl_id ORDER BY pft.date_created desc limit ?, ?;";
    private final String countFreightTplByParamsStmt = "SELECT count(*) FROM (SELECT count(*) FROM pd_freight_tpl pft LEFT JOIN pd_freight_pinkage pfp ON pfp.tpl_id = pft.tpl_id LEFT JOIN pd_freight_shipping pfs ON pfs.tpl_id = pft.tpl_id where 1=1 AND pft.is_valid = 'T' and pft.del_flag = 'F' and (pfp.servicer_id like ? or pfs.servicer_id like ?) AND pft.name LIKE ? AND pft.rel_type like ? GROUP BY pft.tpl_id) u;";

    private final String listPurchaseClassifyStmt = "select concat(pc.id, '') as id, pc.name as name, pc.parent_id as parentId from purchase_classify pc where pc.parent_id = ? and pc.status = 1 order by pc.order_index desc;";
    private final String listCargoClassifyStmt = "select concat(cc.id, '') as id, cc.name as name, cc.parent_id as parentId from trade_goods_classify cc where cc.parent_id = ? and cc.status = 1 order by cc.order_index desc;";

    private final String listFreightRelByParamsStmt = "SELECT pftr.rel_id as id, pftr.tpl_id as tplId, pftr.order_type as orderType, pftr.rel_ids as relIds, pftr.sort as sort, pftr.is_valid as isValid, pft. NAME AS tplName FROM pd_freight_tpl_rel pftr LEFT JOIN pd_freight_tpl pft ON pft.tpl_id = pftr.tpl_id where pftr.del_flag = 'F' and pftr.order_type like ? and pftr.rel_ids like ? and pftr.is_valid  like ? and pftr.sort like ? and pft.name like ?;";
    private final String countFreightRelByParamsStmt = "SELECT count(*) FROM pd_freight_tpl_rel pftr LEFT JOIN pd_freight_tpl pft ON pft.tpl_id = pftr.tpl_id where pftr.del_flag = 'F' and pftr.order_type like ? and pftr.rel_ids like ? and pftr.is_valid  like ? and pftr.sort like ? and pft.name like ?;";

    private final String listFreightTplGoodRelByTradGoodIdStmt = "SELECT * FROM pd_freight_tpl_good_rel pftgr WHERE exists (select 1 from trade_good_sku tgs where pftgr.good_sku_id = tgs.id and tgs.good_id = ?) and pftgr.rel_type = ?;";
    private final String listFreightTplGoodRelByPurGoodIdStmt = "select * from pd_freight_tpl_good_rel pftgr where 1=1 and exists (select 1 from purchase_goods_sku pgs where pgs.material_sku_id = pftgr.good_sku_id and pgs.pur_goods_id = ?) and pftgr.rel_type = ?;";

    @Override
    public ResultList<Map<String, Object>> listFreightTplByParams(FreightTplParams freightTplParams, Paging paging) {
        String tplName = StringUtils.isNotBlank(freightTplParams.getName()) ? "%" + freightTplParams.getName() + "%" : "%%";
        String tplServiceId = StringUtils.isNotBlank(freightTplParams.getFreightMethodId()) ? freightTplParams.getFreightMethodId() : "%%";
        String relType = StringUtils.isNotBlank(freightTplParams.getRelType()) ? freightTplParams.getRelType() : "%%";
        List listParams = new ArrayList();
        List countParams = new ArrayList();

        //总数参数
        countParams.add(tplServiceId);
        countParams.add(tplServiceId);
        countParams.add(tplName);
        countParams.add(relType);

        //数据参数
        listParams.add(tplServiceId);
        listParams.add(tplServiceId);
        listParams.add(tplName);
        listParams.add(relType);
        listParams.add(paging.getPs());
        listParams.add(paging.getPn());

        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(listFreightTplByParamsStmt, listParams.toArray());

        //获取运送方式
        List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : resultList) {
            List<FreightShipping> freightShippingList = freightShippingService.listDetailByTplId(map.get("id").toString());
            List<FreightPinkage> freightPinkageList = freightPinkageService.list(Conds.get().eq("tpl_id", map.get("id").toString()));

            Map<String, Object> methodMap = new HashMap<String, Object>();


            for (FreightShipping freightShipping : freightShippingList) {
                methodMap.put(freightShipping.getServicerId(), freightShipping.getServicerName());
            }

            for (FreightPinkage freightPinkage : freightPinkageList) {
                methodMap.put(freightPinkage.getServicerId(), freightPinkage.getServicerName());
            }

            Iterator iter = methodMap.entrySet().iterator();
            String methodName = "";
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                if (entry.getValue() != null) {
                    methodName += entry.getValue().toString() + "、";
                }
            }

            if (methodName.length() > 0) {
                methodName = methodName.substring(0, methodName.length() - 1);
            }
            map.put("methodName", methodName);
            resultMapList.add(map);
        }

        Number total = jdbcTemplate.queryForObject(countFreightTplByParamsStmt, Integer.class, countParams.toArray());
        ResultList<Map<String, Object>> result = new ResultList<Map<String, Object>>();
        result.setTotalRecords(total != null ? total.intValue() : 0);
        result.setResultList(resultMapList);
        return result;
    }

    @Override
    public void saveOrUpdate(final FreightTpl freightTpl) {
        if (StringUtils.isBlank(freightTpl.getTplId())) {
            freightTpl.setTplId(IdGenerator.getDefault().nextId() + "");
            freightTpl.setIsValid("T");
            freightTpl.setDelFlag("F");
            freightTpl.setDateCreated(new Date());
            freightTpl.setLastUpdated(new Date());
            freightTplService.insert(freightTpl);

            List<FreightShipping> shippingList = freightTpl.getShippingList();
            for (FreightShipping freightShipping : shippingList) {
                freightShipping.setShippingId(IdGenerator.getDefault().nextId() + "");
                freightShipping.setDelFlag("F");
                freightShipping.setTplId(freightTpl.getTplId());
                freightShipping.setIsValid("T");
                freightShipping.setDateCreated(new Date());
                freightShippingService.insert(freightShipping);
                List<FreightShippingItem> itemList  = freightShipping.getItemList();
                int sort = 0;
                for (FreightShippingItem freightShippingItem : itemList) {
                    freightShippingItem.setItemId(IdGenerator.getDefault().nextId() + "");
                    freightShippingItem.setIsValid("T");
                    freightShippingItem.setTplId(freightTpl.getTplId());
                    freightShippingItem.setDelFlag("F");
                    freightShippingItem.setShippingId(freightShipping.getShippingId());
                    freightShippingItem.setDateCreated(new Date());
                    sort++;
                    freightShippingItem.setSort(sort);
                    freightShippingItem.setLastUpdated(new Date());
                    freightShippingItemService.insert(freightShippingItem);
                }
            }
            List<FreightPinkage> pinkageList = freightTpl.getPinkageList();
            int sort = 0;
            for (FreightPinkage freightPinkage : pinkageList) {
                freightPinkage.setPinkageId(IdGenerator.getDefault().nextId() + "");
                freightPinkage.setTplId(freightTpl.getTplId());
                freightPinkage.setDelFlag("F");
                freightPinkage.setIsValid("T");
                sort++;
                freightPinkage.setSort(sort);
                freightPinkage.setDateCreated(new Date());
                freightPinkageService.insert(freightPinkage);
            }

        } else {

            transcationTemplate.execute(new TransactionCallback<FreightTpl>() {
                @Override
                public FreightTpl doInTransaction(TransactionStatus status) {

                    //更新运费模版
                    FreightTpl freightTplOld = freightTplService.getById(freightTpl.getTplId());
                    freightTplOld.setName(freightTpl.getName());
                    freightTplOld.setPriceType(freightTpl.getPriceType());
                    freightTplOld.setLimitFreeFlag(freightTpl.getLimitFreeFlag());
                    freightTplOld.setLastUpdated(new Date());
                    freightTplService.update(freightTplOld);
                    List<FreightShipping> shippingList = freightTpl.getShippingList();
                    //删除旧运送方式
                    freightShippingService.delShippingByTplId(freightTplOld.getTplId());
                    for (FreightShipping freightShipping : shippingList) {
                        freightShipping.setShippingId(IdGenerator.getDefault().nextId() + "");
                        freightShipping.setDelFlag("F");
                        freightShipping.setTplId(freightTplOld.getTplId());
                        freightShipping.setIsValid("T");
                        freightShipping.setDateCreated(new Date());
                        //保存运送方式
                        freightShippingService.insert(freightShipping);

                        //删除运送方式详情
                        freightShippingItemService.delShippingItemByShippingId(freightShipping.getShippingId());
                        List<FreightShippingItem> itemList  = freightShipping.getItemList();
                        int sort = 0;
                        for (FreightShippingItem freightShippingItem : itemList) {
                            sort++;
                            freightShippingItem.setItemId(IdGenerator.getDefault().nextId() + "");
                            freightShippingItem.setIsValid("T");
                            freightShippingItem.setTplId(freightTplOld.getTplId());
                            freightShippingItem.setDelFlag("F");
                            freightShippingItem.setShippingId(freightShipping.getShippingId());
                            freightShippingItem.setDateCreated(new Date());
                            freightShippingItem.setLastUpdated(new Date());
                            freightShippingItem.setSort(sort);
                            freightShippingItemService.insert(freightShippingItem);
                        }
                    }
                    List<FreightPinkage> pinkageList = freightTpl.getPinkageList();

                    if (pinkageList == null || pinkageList.size() == 0) { //为指定条件包邮如果没有选择则删除原有数据
                        freightPinkageService.delPinkageByTplId(freightTpl.getTplId());
                    }

                    int sort = 0;
                    for (FreightPinkage freightPinkage : pinkageList) {
                        sort++;
                        if (StringUtils.isNotBlank(freightPinkage.getPinkageId())) {
                            FreightPinkage freightPinkageOld = freightPinkageService.getById(freightPinkage.getPinkageId());
                            freightPinkageOld.setLastUpdated(new Date());
                            freightPinkageOld.setAreaIds(freightPinkage.getAreaIds());
                            freightPinkageOld.setFreeCondVal(freightPinkage.getFreeCondVal());
                            freightPinkageOld.setServicerId(freightPinkage.getServicerId());
                            freightPinkageOld.setServicerName(freightPinkage.getServicerName());
                            freightPinkageOld.setSort(sort);
                            freightPinkageService.update(freightPinkageOld);
                        } else {
                            freightPinkage.setPinkageId(IdGenerator.getDefault().nextId() + "");
                            freightPinkage.setTplId(freightTpl.getTplId());
                            freightPinkage.setDelFlag("F");
                            freightPinkage.setIsValid("T");
                            freightPinkage.setSort(sort);
                            freightPinkage.setDateCreated(new Date());
                            freightPinkageService.insert(freightPinkage);
                        }
                    }

                    return null;
                }
            });


        }
    }

    @Override
    public List<SysDict> listFreightDictByParentName(String parentName) {
        List<SysDict> sysDicts = new ArrayList<SysDict>();
        if (StringUtils.isNotBlank(parentName)) {
            SysDict sysDict = sysDictService.get(Conds.get().eq("value", parentName));
            if (sysDict != null) {
                sysDicts = sysDictService.list(Conds.get().eq("parentId", sysDict.getDictId()));
            }
        }
        return sysDicts;
    }

    @Override
    public Map<String, Object> checkFreightTplRel(String idStr) {

        Map<String, Object> result = new HashMap<String, Object>();

        if (StringUtils.isBlank(idStr)) {
            result.put("code", false);
            result.put("msg", "运费模版编号不能为空");
            return result;
        }

        String[] ids = idStr.split(",");
        List<String> idList = Arrays.asList(ids);
        List<FreightTplRel> freightTplRels = freightTplRelService.list(Conds.get().in("tplId", idList));
        List<FreightTplGoodRel> freightTplGoodRels = freightTplGoodRelService.list(Conds.get().in("tplId", idList));

        if (freightTplRels.size() > 0 || freightTplGoodRels.size() > 0) {
            Map<String, Object> freightTplRelMap = new HashMap<String, Object>();
            if (freightTplRels.size() > 0) {
                for (FreightTplRel freightTplRel : freightTplRels) {
                    FreightTpl freightTpl = freightTplService.getById(freightTplRel.getTplId());
                    freightTplRelMap.put(freightTpl.getTplId(), freightTpl.getName());
                }
            }
            if (freightTplGoodRels.size() > 0) {
                for (FreightTplGoodRel freightTplGoodRel : freightTplGoodRels) {
                    FreightTpl freightTpl = freightTplService.getById(freightTplGoodRel.getTplId());
                    freightTplRelMap.put(freightTpl.getTplId(), freightTpl.getName());
                }
            }
            Iterator iter = freightTplRelMap.entrySet().iterator();
            String msg = "选择的运费模版中“";
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                msg += entry.getValue().toString() + "，";
            }
            msg = msg.substring(0, msg.length() - 1);
            msg += "”被占用运费模板无法删除！";
            result.put("code", false);
            result.put("msg", msg);
        } else {
            result.put("code", true);
        }
        return result;
    }

    @Override
    public Map<String, Object> delBatchFreightTpls(String idStr) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isBlank(idStr)) {
            result.put("code", false);
            result.put("msg", "操作失败");
            return result;
        }
        String[] ids = idStr.split(",");
        for (String id : ids) {
            FreightTpl freightTpl = freightTplService.getById(id);
            freightTpl.setDelFlag("T");
            freightTplService.update(freightTpl);
        }

        result.put("code", true);
        result.put("msg", "操作成功");
        return result;
    }

    @Override
    public ResultList<Map<String, Object>> getClassfyTrees(String orderType, String parentId) {
        List<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
        if (orderType.equals(OrderBizType.purchase.toString())) {
            resultMapList = jdbcTemplate.queryForList(listPurchaseClassifyStmt, parentId);
        } else {
            resultMapList = jdbcTemplate.queryForList(listCargoClassifyStmt, parentId);
        }
        for (Map<String, Object> map : resultMapList) {
            map.put("children", null);
        }

        ResultList<Map<String, Object>> result = new ResultList<Map<String, Object>>();
        result.setResultList(resultMapList);
        return result;
    }

    @Override
    public ResultList<ClassifyVo> queryClassfyTrees(String orderType, String parentId) {

        List<ClassifyVo> classifyVos = new ArrayList<ClassifyVo>();
        RowMapper<ClassifyVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(ClassifyVo.class);
        if (orderType.equals(OrderBizType.purchase.toString())) {
            classifyVos = jdbcTemplate.query(listPurchaseClassifyStmt, rowMapper, parentId);
        } else {
            classifyVos = jdbcTemplate.query(listCargoClassifyStmt, rowMapper, parentId);
        }
        List<ClassifyVo> classifyVoList = new ArrayList<ClassifyVo>();
        for (ClassifyVo classifyVo : classifyVos) {
            classifyVoList.add(makeTrees(classifyVo, orderType));
        }
        ResultList<ClassifyVo> classifyVoResultList = new ResultList<ClassifyVo>();
        classifyVoResultList.setResultList(classifyVoList);
        return classifyVoResultList;
    }

    @Override
    public void saveOrUpdateFreightRel(FreightTplRel freightTplRel) {

        if (StringUtils.isBlank(freightTplRel.getRelId())) {
            freightTplRel.setRelId(IdGenerator.getDefault().nextId() + "");
            freightTplRel.setRelType(FreightTplRelType.category.toString());
            freightTplRel.setDelFlag("F");
            freightTplRel.setDateCreated(new Date());
            freightTplRel.setLastUpdated(new Date());
            freightTplRelService.insert(freightTplRel);
        } else {
            FreightTplRel freightTplRelOld = freightTplRelService.getById(freightTplRel.getRelId());
            freightTplRelOld.setIsValid(freightTplRel.getIsValid());
            freightTplRelOld.setOrderType(freightTplRel.getOrderType());
            freightTplRelOld.setRelIds(freightTplRel.getRelIds());
            freightTplRelOld.setSort(freightTplRel.getSort());
            freightTplRelOld.setTplId(freightTplRel.getTplId());
            freightTplRelOld.setLastUpdated(new Date());
            freightTplRelService.update(freightTplRelOld);
        }
    }

    @Override
    public ResultList<Map<String, Object>> listFreightRelByParams(FreightTplRelParams freightTplRelParams, Paging pg) {
        List params = new ArrayList();


        params.add(StringUtils.isNotBlank(freightTplRelParams.getOrderType()) ? freightTplRelParams.getOrderType() : "%%");
        params.add(StringUtils.isNotBlank(freightTplRelParams.getRelIds()) ? "%" + freightTplRelParams.getRelIds() + "%" : "%%");
        params.add(StringUtils.isNotBlank(freightTplRelParams.getIsValid()) ? freightTplRelParams.getIsValid() : "%%");
        params.add(StringUtils.isNotBlank(freightTplRelParams.getSort()) ? freightTplRelParams.getSort() : "%%");
        params.add(StringUtils.isNotBlank(freightTplRelParams.getTplName()) ? "%" + freightTplRelParams.getTplName() + "%" : "%%");

        List<Map<String, Object>> resultMapList = jdbcTemplate.queryForList(listFreightRelByParamsStmt, params.toArray());
        int i = 0;
        for (Map<String, Object> map : resultMapList) {
            i++;
            map.put("rowNum", i);
            String relIds = (String) map.get("relIds");
            List<String> stringList = Arrays.asList(relIds.split(","));
            String relName = "";
            if (map.get("orderType").toString().equals(OrderBizType.purchase.toString())) {
                List<PurchaseClassify> purchaseClassifies = purchaseClassifyService.listByIds(stringList);
                for (PurchaseClassify purchaseClassify : purchaseClassifies) {
                    relName += purchaseClassify.getName() + ",";
                }
            } else {
                List<CargoClassify> cargoClassifies = cargoClassifyService.listByIds(stringList);
                for (CargoClassify cargoClassify : cargoClassifies) {
                    relName += cargoClassify.getName() + ",";
                }
            }
            if (relName.length() > 0) {
                relName = relName.substring(0, relName.length() - 1);
            }
            map.put("relName", relName);
        }

        Number total = jdbcTemplate.queryForObject(countFreightRelByParamsStmt.toString(), Integer.class, params.toArray());
        ResultList<Map<String, Object>> result = new ResultList<Map<String, Object>>();
        result.setTotalRecords(total != null ? total.intValue() : 0);
        result.setResultList(resultMapList);
        return result;
    }

    @Override
    public Map<String, Object> delBatchFreightTplRels(String relIds) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isBlank(relIds)) {
            result.put("code", false);
            result.put("msg", "操作失败");
            return result;
        }
        String[] ids = relIds.split(",");
        for (String id : ids) {
            FreightTplRel freightTplRel = freightTplRelService.getById(id);
            freightTplRel.setDelFlag("T");
            freightTplRelService.update(freightTplRel);
        }

        result.put("code", true);
        result.put("msg", "操作成功");
        return result;
    }

    @Override
    public FreightTplRel freightTplRelDetail(String relId) {
        return freightTplRelService.getById(relId);
    }

    @Override
    public void saveOrUpdateFreightGoodRel(FreightTplGoodRel freightTplGoodRel) {

        if (StringUtils.isBlank(freightTplGoodRel.getRelId())) { //新增
            freightTplGoodRel.setRelId(IdGenerator.getDefault().nextId() + "");
            freightTplGoodRel.setDelFlag("F");
            freightTplGoodRel.setIsValid("T");
            freightTplGoodRel.setDateCreated(new Date());
            freightTplGoodRel.setLastUpdated(new Date());
            freightTplGoodRelService.insert(freightTplGoodRel);
        } else {   //更新
            FreightTplGoodRel freightTplGoodRelOld = freightTplGoodRelService.getById(freightTplGoodRel.getRelId());
            freightTplGoodRelOld.setGoodSkuId(freightTplGoodRel.getGoodSkuId());
            freightTplGoodRelOld.setTplId(freightTplGoodRel.getTplId());
            freightTplGoodRelOld.setLastUpdated(new Date());
            freightTplGoodRelService.update(freightTplGoodRelOld);
        }

    }

    @Override
    public List<FreightTplGoodRel> listFreightTplGoodRelByGoodId(String tradeGoodId, String relType) {
        RowMapper<FreightTplGoodRel> freightTplGoodRelRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(FreightTplGoodRel.class);
        if (relType.equals(OrderBizType.trade.toString())) {
            return jdbcTemplate.query(listFreightTplGoodRelByTradGoodIdStmt, freightTplGoodRelRowMapper, tradeGoodId, relType);
        } else {
            return jdbcTemplate.query(listFreightTplGoodRelByPurGoodIdStmt, freightTplGoodRelRowMapper, tradeGoodId, relType);
        }
    }

    @Override
    public void delFreightTplGoodRelByGoodSkuId(String goodSkuId, String orderType) {
        freightTplGoodRelService.delete(Conds.get().eq("goodSkuId", goodSkuId).eq("relType", orderType));
    }

    @Override
    public ResultList<ClassifyVo> getAllClassfyList(String orderType) {
        List<ClassifyVo> classifyVos = new ArrayList<ClassifyVo>();
        if (orderType.equals(OrderBizType.purchase.toString())) { //查询采购分类
            List<PurchaseClassify> purchaseClassifies = purchaseClassifyService.list(Conds.get().eq("status", 1));
            for (PurchaseClassify purchaseClassify : purchaseClassifies) {
                ClassifyVo classifyVo = new ClassifyVo();
                classifyVo.setId(purchaseClassify.getId());
                classifyVo.setName(purchaseClassify.getName());
                classifyVo.setParentId(purchaseClassify.getParentId());
                classifyVos.add(classifyVo);
            }
        } else {    //查询商城分类
            List<TradeGoodsClassify> cargoClassifies = tradeGoodsClassifyService.list(Conds.get().eq("status", 1));
            for (TradeGoodsClassify cargoClassify : cargoClassifies) {
                ClassifyVo classifyVo = new ClassifyVo();
                classifyVo.setId(cargoClassify.getId() + "");
                classifyVo.setName(cargoClassify.getName());
                if (cargoClassify.getParentId() != null) {
                    classifyVo.setParentId(cargoClassify.getParentId() + "");
                    classifyVos.add(classifyVo);
                }
            }


        }
        ResultList<ClassifyVo> result = new ResultList<ClassifyVo>();
        result.setResultList(classifyVos);
        return result;
    }

    /**
     * 递归拼装树
     * @param classifyVo
     * @param orderType
     * @return
     */
    public ClassifyVo makeTrees(ClassifyVo classifyVo, String orderType) {
        List<ClassifyVo> classifyVos = new ArrayList<ClassifyVo>();

        RowMapper<ClassifyVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(ClassifyVo.class);
        if (orderType.equals(OrderBizType.purchase.toString())) {
            classifyVos = jdbcTemplate.query(listPurchaseClassifyStmt, rowMapper, classifyVo.getId());
        } else {
            classifyVos = jdbcTemplate.query(listCargoClassifyStmt, rowMapper, classifyVo.getId());
        }

        for (ClassifyVo co : classifyVos) {
            ClassifyVo cvo = makeTrees(co, orderType);
            classifyVo.getChildren().add(cvo);
        }
        return classifyVo;
    }


}
