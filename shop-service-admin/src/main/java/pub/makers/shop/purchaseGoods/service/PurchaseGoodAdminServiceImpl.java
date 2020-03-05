package pub.makers.shop.purchaseGoods.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
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
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.logistics.entity.FreightTplGoodRel;
import pub.makers.shop.logistics.service.FreightTplGoodRelService;
import pub.makers.shop.purchaseGoods.entity.*;
import pub.makers.shop.purchaseGoods.vo.NewPurchaseGoodsVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsSkuVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsVo;
import pub.makers.shop.purchaseGoods.vo.StockVo;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.tradeGoods.entity.GoodLabels;
import pub.makers.shop.tradeGoods.service.GoodLabelsService;
import pub.makers.shop.tradeGoods.vo.EvaluationImageVo;
import pub.makers.shop.tradeGoods.vo.GoodsEvaluationVo;

import java.util.*;

/**
 * Created by daiwenfa on 2017/5/25.
 */
@Service(version="1.0.0")
public class PurchaseGoodAdminServiceImpl implements PurchaseGoodMgrBizService {

    private final static String getPurchaseClassifyAttrListStmt = "select * from purchase_classify_attr where pur_classify_id=? and parent_id is null";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private PurchaseGoodsService purchaseGoodsService;
    @Autowired
    private PurchaseGoodsSkuService purchaseGoodsSkuService;
    @Autowired
    private FreightTplGoodRelService freightTplGoodRelService;
    @Autowired
    private GoodLabelsService goodLabelsService;

    @Autowired
    private PurchaseClassifyAttrService purchaseClassifyAttrService;

    @Autowired
    private PurchaseGoodsSampleService purchaseGoodsSampleService;

    @Autowired
    private PurchaseMaterialAttrService purchaseMaterialAttrService;
    @Autowired
    private PurchaseClassifyService purchaseClassifyService;
    @Reference(version = "1.0.0")
    private PurchaseGoodsBizService purchaseGoodsBizService;

    @Override
    public ResultList<NewPurchaseGoodsVo> getListPage(NewPurchaseGoodsVo newPurchaseGoodsVo, Paging pg) {
        if(StringUtils.isNotBlank(newPurchaseGoodsVo.getClassifyName())&&!newPurchaseGoodsVo.getClassifyName().equals("-1")){
            newPurchaseGoodsVo.setClassifyName(getAllClassifyId(newPurchaseGoodsVo.getClassifyName()));
        }
        String sql = FreeMarkerHelper.getValueFromTpl("sql/purchaseGood/queryPurchaseSkuList.sql", newPurchaseGoodsVo);
        RowMapper<NewPurchaseGoodsVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(NewPurchaseGoodsVo.class);
        List<NewPurchaseGoodsVo> list = jdbcTemplate.query(sql + " limit ?,? ", rowMapper, pg.getPs(), pg.getPn());
        Number total = jdbcTemplate.queryForObject("select count(0) from (" + sql +") nums ",null,Integer.class);
        ResultList<NewPurchaseGoodsVo> resultList = new ResultList<NewPurchaseGoodsVo>();
        resultList.setResultList(list);
        resultList.setTotalRecords(total!=null?total.intValue():0);
        return resultList;
    }

    public String getAllClassifyId(String parentId){
        List<String> allList = new ArrayList<>();
        String classify = " or find_in_set(%s, purchase_goods.group_id) ";//查询分类id字段
        if(StringUtils.isNotBlank(parentId)) {
            List<PurchaseClassify> list = purchaseClassifyService.list(Conds.get().eq("parent_id", parentId));
            List<String> xiajiList = new ArrayList<>();//下级的分类
            if (list.size() > 0) {
                for (PurchaseClassify cargoClassify : list) {
//                    allList.add(classify + "'%"+cargoClassify.getId()+"%'");
                    allList.add(classify.replace("%s", cargoClassify.getId() + ""));
                    xiajiList.add(cargoClassify.getId());
                }
            }
            List<PurchaseClassify> lists = purchaseClassifyService.list(Conds.get().in("parent_id", xiajiList));//查询下级的下级
            if (lists.size() > 0) {
                for (PurchaseClassify purchaseClassify : lists) {
//                    allList.add(classify + "'%"+purchaseClassify.getId() + "%'");
                    allList.add(classify.replace("%s", purchaseClassify.getId() + ""));
                }
            }
            allList = new ArrayList(new HashSet(allList));//去重
        }

        String allClassifyId = " (find_in_set("+ parentId +", purchase_goods.group_id) " + StringUtils.join(allList, " ") + ")";
//        String allClassifyId = "purchase_goods.group_id like '%"+ parentId+ "%' " + StringUtils.join(allList," ");
        return allClassifyId;
    }

    @Override
    public Map<String, Object> savePurchaseGoods(final PurchaseGoodsVo purchaseGoodsVo, final long userId) {


        if (StringUtils.isBlank(purchaseGoodsVo.getId())) {
//            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//                @Override
//                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    PurchaseGoods tradeGood = new PurchaseGoods();
                    BeanUtils.copyProperties(purchaseGoodsVo, tradeGood);
                    tradeGood.setId(IdGenerator.getDefault().nextId() + "");
                    tradeGood.setCargoId(Long.parseLong(purchaseGoodsVo.getCargoId()));
                    tradeGood.setStatus(0 + "");
                    tradeGood.setCreateTime(new Date());
                    tradeGood.setUpdateTime(new Date());
                    tradeGood.setIsStore(purchaseGoodsVo.getIsStore());
                    tradeGood.setClassifyValid(BoolType.T.name());
                    purchaseGoodsService.insert(tradeGood);
                    purchaseGoodsBizService.updateClassifyValid();
                    if ("1".equals(purchaseGoodsVo.getIsSample())) {
                        saveSamplePurGoods(tradeGood, purchaseGoodsVo.getPurchaseGoodsSample());
                    }

                    saveGoodsLabels(purchaseGoodsVo.getLabelIds(), tradeGood); //保存商品
                    saveTradeGoodsSku(purchaseGoodsVo.getGoodSkuVoList(), tradeGood, purchaseGoodsVo.getDelTradeGoodsSkuIds()); //保存商品SKU
                    saveMaterialAttr(purchaseGoodsVo.getPurchaseMaterialAttrList(), tradeGood);
//                }
//            });
        } else {
//            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//                @Override
//                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    PurchaseGoods tradeGood = purchaseGoodsService.getById(purchaseGoodsVo.getId());
                    tradeGood.setBaseSaleNum(purchaseGoodsVo.getBaseSaleNum());
                    tradeGood.setCargoId(Long.parseLong(purchaseGoodsVo.getCargoId()));
                    tradeGood.setCategory(purchaseGoodsVo.getCategory());
                    tradeGood.setPurSubtitle(purchaseGoodsVo.getPurSubtitle());
                    tradeGood.setPurGoodsName(purchaseGoodsVo.getPurGoodsName());
                    tradeGood.setGroupId(purchaseGoodsVo.getGroupId());
                    tradeGood.setGroupName(purchaseGoodsVo.getGroupName());
                    tradeGood.setLabel(purchaseGoodsVo.getLabel());
                    tradeGood.setTheme(purchaseGoodsVo.getTheme());
                    tradeGood.setIsStore(purchaseGoodsVo.getIsStore());
                    tradeGood.setUpdateTime(new Date());
                    tradeGood.setIsSample(purchaseGoodsVo.getIsSample());

                    if ("1".equals(purchaseGoodsVo.getIsSample())) {
                        saveSamplePurGoods(tradeGood, purchaseGoodsVo.getPurchaseGoodsSample());
                    }

//                    tradeGood.setType(purchaseGoodsVo.getType());
                    tradeGood.setOrderIndex(purchaseGoodsVo.getOrderIndex());
                    purchaseGoodsService.update(tradeGood);
                    purchaseGoodsBizService.updateClassifyValid();
                    saveGoodsLabels(purchaseGoodsVo.getLabelIds(), tradeGood); //保存商品
                    saveTradeGoodsSku(purchaseGoodsVo.getGoodSkuVoList(), tradeGood, purchaseGoodsVo.getDelTradeGoodsSkuIds()); //保存商品SKU
                    saveMaterialAttr(purchaseGoodsVo.getPurchaseMaterialAttrList(), tradeGood);
//                }
//            });
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        return resultMap;

    }

    /**
     * 保存分类属性
     * @param purchaseMaterialAttrList
     * @param tradeGood
     */
    private void saveMaterialAttr(List<PurchaseMaterialAttr> purchaseMaterialAttrList, PurchaseGoods tradeGood) {
        purchaseMaterialAttrService.delete(Conds.get().eq("pur_goods_id", tradeGood.getId()));
        if (purchaseMaterialAttrList == null) {
            return;
        }
        for (PurchaseMaterialAttr purchaseMaterialAttr : purchaseMaterialAttrList) {
            purchaseMaterialAttr.setId(IdGenerator.getDefault().nextId() + "");
            purchaseMaterialAttr.setPurGoodsId(tradeGood.getId());
            purchaseMaterialAttrService.insert(purchaseMaterialAttr);
        }

    }

    /**
     * 保存样品
     * @param tradeGood
     * @param purchaseGoodsSample
     */
    private void saveSamplePurGoods(PurchaseGoods tradeGood, PurchaseGoodsSample purchaseGoodsSample) {
        PurchaseGoodsSample goodsSample = purchaseGoodsSampleService.get(Conds.get().eq("pur_goods_id", tradeGood.getId()));
        if (goodsSample != null) {
            goodsSample.setSampleCode(purchaseGoodsSample.getSampleCode());
            goodsSample.setSamplePrice(purchaseGoodsSample.getSamplePrice());
            goodsSample.setSampleSku(purchaseGoodsSample.getSampleSku());
            goodsSample.setStartNum(purchaseGoodsSample.getStartNum());
            goodsSample.setUnit(purchaseGoodsSample.getUnit());
            goodsSample.setPurGoodsId(tradeGood.getId());
            purchaseGoodsSampleService.update(goodsSample);
        } else {
            purchaseGoodsSample.setId(IdGenerator.getDefault().nextId() + "");
            purchaseGoodsSample.setPurGoodsId(tradeGood.getId());
            purchaseGoodsSampleService.insert(purchaseGoodsSample);
        }

    }

    @Override
    public List<Map<String, Object>> purClassifyAttrList(String classifyId) {

        RowMapper<PurchaseClassifyAttr> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PurchaseClassifyAttr.class);
        List<PurchaseClassifyAttr> parentClassifyAttrList = jdbcTemplate.query(getPurchaseClassifyAttrListStmt, rowMapper, classifyId);
        List<Map<String, Object>> resultMapList = Lists.newArrayList();
        for (PurchaseClassifyAttr purchaseClassifyAttr : parentClassifyAttrList) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("keyName", purchaseClassifyAttr.getName());
            resultMap.put("keyId", purchaseClassifyAttr.getId());
            List<PurchaseClassifyAttr> purchaseClassifyAttrs = purchaseClassifyAttrService.list(Conds.get().eq("pur_classify_id", classifyId).eq("parent_id", purchaseClassifyAttr.getId()));
            resultMap.put("keyValue", purchaseClassifyAttrs);
            resultMapList.add(resultMap);
        }
        return resultMapList;
    }

    @Override
    public PurchaseGoodsVo getPurchaseGoodsInfo(String id) {
        PurchaseGoodsVo tradeGoodVo = new PurchaseGoodsVo();
        if (StringUtils.isBlank(id)) {
            return null;
        }
        PurchaseGoods tradeGood = purchaseGoodsService.getById(id);
        BeanUtils.copyProperties(tradeGood, tradeGoodVo);
        tradeGoodVo.setId(tradeGood.getId() + "");
        tradeGoodVo.setCargoId(tradeGood.getCargoId() + "");
        tradeGoodVo.setOrderIndex(tradeGood.getOrderIndex());
        //获取TradeGoodsSkuVoList
        getPurchaseGoodsSkuVoList(tradeGood, tradeGoodVo);
        //获取labelIds
        getPurchaseGoodsLabelId(tradeGood, tradeGoodVo);

        getSamplePurchaseGoods(tradeGood, tradeGoodVo);
        //获取分类属性
        getMateriaAttrList(tradeGood, tradeGoodVo);
        return tradeGoodVo;


    }

    /**
     * 获取分类属性列表
     * @param tradeGood
     * @param tradeGoodVo
     */
    private void getMateriaAttrList(PurchaseGoods tradeGood, PurchaseGoodsVo tradeGoodVo) {
        List<PurchaseMaterialAttr> purchaseMaterialAttrs = purchaseMaterialAttrService.list(Conds.get().eq("pur_goods_id", tradeGood.getId()));
        tradeGoodVo.setPurchaseMaterialAttrList(purchaseMaterialAttrs);
    }

    @Override
    public StockVo selectTradeGoodSkuById(String id) {
        String sql = "select t.on_sales_no as onSalesNo,s.curr_stock as outShelvesNo," +
                "     s.on_pay_no as onPayNo,t.cargo_sku_id as cargoSkuId,t.id,c.code,c.name as skuName,t.pur_goods_id as goodId " +
                "       from purchase_goods_sku t left join  cargo_sku c on c.id=t.cargo_sku_id LEFT JOIN cargo_sku_stock s ON s.cargo_sku_id=t.cargo_sku_id " +
                "        where 1=1 and t.id = ? ";
        StockVo stockVo = new StockVo();
        RowMapper<StockVo> stockVoMapper = ParameterizedBeanPropertyRowMapper.newInstance(StockVo.class);
        List<StockVo> list = jdbcTemplate.query(sql,stockVoMapper,id);
        stockVo = list.get(0);
        String sqls = "select if(isnull(sum(out_shelves_no)),0,out_shelves_no) from cargo_sku_stock where cargo_sku_id= ? ";
        int leftNums = jdbcTemplate.queryForObject(sqls,Integer.class,id);
        stockVo.setLeftNums(leftNums + "");
        return stockVo;
    }

    @Override
    public void deleteGoods(String id) {
        // 根据skuid删除商品评论
        String sql1 = " delete from purchase_goods_evaluation where good_sku_id = ? ";
        jdbcTemplate.update(sql1,id);
        // 根据skuid删除跟该商品有关的所有SKU的供货价
        String sql2 = " delete from store_supply_price where good_sku_id = ? ";
        jdbcTemplate.update(sql2, id);
        // 根据skuid删除商品sku记录
        String sql3 = " update purchase_goods_sku set del_flag='T' where id = ? ";
        jdbcTemplate.update(sql3, id);
    }

    @Override
    public List<GoodsEvaluationVo> getEvaluation(String purchaseGoodsSkuId) {
        RowMapper<GoodsEvaluationVo> geRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(GoodsEvaluationVo.class);
        String sql = " SELECT purchase_goods_evaluation.*, weixin_user_info.headImgUrl headImage,cargo_sku.sku_name " +
                     "   FROM purchase_goods_evaluation " +
                     "   LEFT JOIN weixin_user_info ON weixin_user_info.ID = purchase_goods_evaluation. USER " +
                     "   LEFT JOIN purchase_goods_sku on purchase_goods_evaluation.good_sku_id = purchase_goods_sku.id " +
                     "   LEFT JOIN cargo_sku on purchase_goods_sku.cargo_sku_id = cargo_sku.id " +
                     "  WHERE purchase_goods_evaluation.good_sku_id = ? ";
        List<GoodsEvaluationVo> list = jdbcTemplate.query(sql, geRowMapper, purchaseGoodsSkuId);
        for (GoodsEvaluationVo goodEvaluationVo : list) {
            String groupId = goodEvaluationVo.getImage();
            if (groupId != null && groupId != "") {
                String sqls = "select id, group_id, pic_url, create_time, create_by from image where image.group_id = ?";
                RowMapper<ImageVo> ImageMapper = ParameterizedBeanPropertyRowMapper.newInstance(ImageVo.class);
                List<ImageVo> imageVoList = jdbcTemplate.query(sqls, ImageMapper, goodEvaluationVo.getImage());
                List<EvaluationImageVo> eimageVoList = new ArrayList<EvaluationImageVo>();
                for (ImageVo imageVo : imageVoList) {
                    EvaluationImageVo evaluationImageVo = new EvaluationImageVo();
                    evaluationImageVo.setId(Long.valueOf(imageVo.getId()));
                    evaluationImageVo.setUrl(imageVo.getPicUrl());
                    eimageVoList.add(evaluationImageVo);
                }
                goodEvaluationVo.setImageVoList(eimageVoList);
            }
        }
        return list;
    }

    @Override
    public boolean deleteEvaluationSer(String id) {
        String sql = "delete from purchase_goods_evaluation where id = ? ";
        int temp = jdbcTemplate.update(sql,id);
        if(temp>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void saveEvaluation(GoodsEvaluationVo v, String[] urlArray, long userId) {
        long groupId=-1L;
        //保存图片
        if(urlArray!=null && !urlArray.equals("") && urlArray.length !=0) {
            groupId = IdGenerator.getDefault().nextId();
            String deGroupSql = " delete from image where group_id = ? ";
            jdbcTemplate.update(deGroupSql, groupId);
            String insertImage = "insert into image (id, group_id, pic_url,create_time, create_by) values (?,?,?,?,?) ";
            for (int i = 0; i < urlArray.length; i++) {
                List imageParam = new ArrayList();
                imageParam.add(IdGenerator.getDefault().nextId() + "");
                imageParam.add(groupId);
                imageParam.add(urlArray[i]);
                imageParam.add(new Date());
                imageParam.add(userId);
                jdbcTemplate.update(insertImage, imageParam.toArray());
            }
        }
        // 保存评价的信息
        List evaluationParam = new ArrayList();
        evaluationParam.add(IdGenerator.getDefault().nextId());
        evaluationParam.add(v.getGoodSkuid());
        evaluationParam.add(userId);
        evaluationParam.add(v.getUsername());
        evaluationParam.add(v.getContent());
        if(urlArray!=null && !urlArray.equals("") && urlArray.length !=0 &&groupId !=-1){
            evaluationParam.add(Long.toString(groupId));
        }else {
            evaluationParam.add("");
        }
        evaluationParam.add(new Date());
        evaluationParam.add(v.getScore());
        evaluationParam.add(v.getPurGoodsId());
        String insertEvaluation = "insert into purchase_goods_evaluation " +
                "(id, good_sku_id, `user`, userName, content, image, evaluate_time, score,pur_goods_id,is_hide) " +
                "    values (?,?,?,?,?,?,?,?,?,'1')";
        jdbcTemplate.update(insertEvaluation, evaluationParam.toArray());
    }

    @Override
    public boolean editSort(String purchaseGoodsId, String sort) {
        String sql = "update purchase_goods set purchase_goods.order_index = ? where purchase_goods.id = ? ";
        int result = jdbcTemplate.update(sql,sort,purchaseGoodsId);
        if(result>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean timingUpGoodSkuStatus(PurchaseGoodsSku purchaseGoodsSku, long userId) {
        PurchaseGoodsSku tgs = purchaseGoodsSkuService.get(Conds.get().eq("id", purchaseGoodsSku.getId()));
        if(tgs.getNums()!=0){
            return false;
        }
        purchaseGoodsSkuService.update(purchaseGoodsSku);
        return true;
    }

    @Override
    public StockVo getTimeingData(String id) {
        RowMapper<StockVo> stockVoMapper = ParameterizedBeanPropertyRowMapper.newInstance(StockVo.class);
        String sql = FreeMarkerHelper.getValueFromTpl("sql/purchaseGood/getTimeingData.sql", null);
        List<StockVo> list = jdbcTemplate.query(sql, stockVoMapper, id);
        return list.get(0);
    }
    //查询所有父分类
    @Override
    public List<String> getParentClassify(String idStr) {
        List<String> list = Arrays.asList(idStr.split(","));
        List<String> classifyIdList = new ArrayList<>();
        classifyIdList.addAll(list);
        List<PurchaseClassify> lists = purchaseClassifyService.list(Conds.get().in("id", list));
        List<String> parentList = new ArrayList<>();
        for(PurchaseClassify tradeGoodsClassify:lists){
            if(tradeGoodsClassify.getParentId()!=null){
                if(!tradeGoodsClassify.getParentId().toString().equals("1")){//判断上级是否为最上级
                    parentList.add(tradeGoodsClassify.getParentId().toString());
                }
                classifyIdList.add(tradeGoodsClassify.getParentId().toString());
            }
        }
        if(parentList.size()>0) {//查询最上级
            List<PurchaseClassify> listss = purchaseClassifyService.list(Conds.get().in("id", parentList));
            if (listss.size() > 0) {
                for (PurchaseClassify tradeGoodsClassifys : listss) {
                    if (tradeGoodsClassifys.getParentId() != null) {
                        classifyIdList.add(tradeGoodsClassifys.getParentId()+"");
                    }
                }
            }
        }
        List newClassifyIdList = new ArrayList(new HashSet(classifyIdList));//去重
        return newClassifyIdList;

    }

    @Override
    public ResultList<PurchaseGoodsVo> getPurchaseGoodsList(PurchaseGoodsVo purchaseGoodsVo, Paging pg) {

        String getPurchaseGoodsListStmt = FreeMarkerHelper.getValueFromTpl("sql/purchaseGood/getPurhcaseGoodsList.sql", purchaseGoodsVo);

        List<PurchaseGoodsVo> purchaseGoodsVos = Lists.newArrayList();
        RowMapper<PurchaseGoodsVo> purchaseGoodsVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PurchaseGoodsVo.class);
        purchaseGoodsVos = jdbcTemplate.query(getPurchaseGoodsListStmt, purchaseGoodsVoRowMapper, pg.getPs(), pg.getPn());

        String countPurchaseGoodsStmt = FreeMarkerHelper.getValueFromTpl("sql/purchaseGood/countPurchaseGoods.sql", purchaseGoodsVo);
        Integer totalCount = jdbcTemplate.queryForObject(countPurchaseGoodsStmt, Integer.class);

        ResultList<PurchaseGoodsVo> resultList = new ResultList<PurchaseGoodsVo>();
        resultList.setResultList(purchaseGoodsVos);
        resultList.setTotalRecords(totalCount);
        return resultList;
    }


    private void getSamplePurchaseGoods(PurchaseGoods tradeGood, PurchaseGoodsVo tradeGoodVo) {
        PurchaseGoodsSample purchaseGoodsSample = purchaseGoodsSampleService.get(Conds.get().eq("pur_goods_id", tradeGood.getId()));
        tradeGoodVo.setPurchaseGoodsSample(purchaseGoodsSample);
    }

    private void getPurchaseGoodsLabelId(PurchaseGoods tradeGood, PurchaseGoodsVo tradeGoodVo) {
        List<GoodLabels> goodLabelses = goodLabelsService.list(Conds.get().eq("good_id", tradeGood.getId()));
        List<String> labelIds = Lists.newArrayList();
        for (GoodLabels goodLabelse : goodLabelses) {
            labelIds.add(goodLabelse.getGoodBaseLabelId() + "");
        }
        tradeGoodVo.setLabelIds(labelIds);
    }

    private void getPurchaseGoodsSkuVoList(PurchaseGoods tradeGood, PurchaseGoodsVo tradeGoodVo) {
        List<PurchaseGoodsSku> tradeGoodSkus = purchaseGoodsSkuService.list(Conds.get().eq("pur_goods_id", tradeGood.getId()));
        List<PurchaseGoodsSkuVo> tradeGoodSkuVoList = Lists.newArrayList();
        for (PurchaseGoodsSku tradeGoodSku : tradeGoodSkus) {
            PurchaseGoodsSkuVo tradeGoodSkuVo = new PurchaseGoodsSkuVo();
            BeanUtils.copyProperties(tradeGoodSku, tradeGoodSkuVo);
            tradeGoodSkuVo.setId(tradeGoodSku.getId() + "");
            tradeGoodSkuVo.setCargoSkuId(tradeGoodSku.getCargoSkuId() + "");
            tradeGoodSkuVoList.add(tradeGoodSkuVo);
        }

        if (tradeGoodSkus.size() > 0) {
            //获取运费模版
            getPurchaseGoodsSkuFreightTpl(tradeGoodSkus.get(0), tradeGoodVo);
        }

        tradeGoodVo.setGoodSkuVoList(tradeGoodSkuVoList);
    }

    private void getPurchaseGoodsSkuFreightTpl(PurchaseGoodsSku purchaseGoodsSku, PurchaseGoodsVo tradeGoodVo) {
        FreightTplGoodRel freightTplGoodRel = freightTplGoodRelService.get(Conds.get().eq("good_sku_id", purchaseGoodsSku.getId()).eq("rel_type", "purchase"));
        if (freightTplGoodRel != null) {
            tradeGoodVo.setFreightTplId(freightTplGoodRel.getTplId());
        }
    }

    private void saveGoodsLabels(List<String> labelIds, PurchaseGoods tradeGood) {
        if (labelIds == null) {
            return;
        }
        goodLabelsService.delete(Conds.get().eq("good_id", tradeGood.getId()));
        for (String labelId : labelIds) {
            GoodLabels goodLabels = new GoodLabels();
            goodLabels.setId(IdGenerator.getDefault().nextId());
            goodLabels.setGoodId(Long.parseLong(tradeGood.getId()));
            goodLabels.setGoodBaseLabelId(Long.parseLong(labelId));
            goodLabels.setGoodType("purchase");
            goodLabelsService.insert(goodLabels);
        }
    }

    private void saveTradeGoodsSku(List<PurchaseGoodsSkuVo> tradeGoodSkuVoList, PurchaseGoods tradeGood, List<String> delTradeGoodsSkuIds) {

        List<PurchaseGoodsSku> tradeGoodSkus = purchaseGoodsSkuService.list(Conds.get().eq("pur_goods_id", tradeGood.getId()));

        for (PurchaseGoodsSku tradeGoodSku : tradeGoodSkus) {
            int flag = -1;
            for (int i = 0; i < tradeGoodSkuVoList.size(); i++) {
                if (tradeGoodSku.getCargoSkuId() != null
                        && tradeGoodSku.getCargoSkuId().equals(Long.parseLong(tradeGoodSkuVoList.get(i).getCargoSkuId()))) {
                    flag = i;
                    break;
                }
            }
            if (flag == -1) {
                // delete 編輯后的商品sku比原來的少，則刪除少去的商品Sku
                purchaseGoodsSkuService.delete(Conds.get().eq("id", tradeGoodSku.getId()));
            } else {
                tradeGoodSku.setCargoSkuId(Long.parseLong(tradeGoodSkuVoList.get(flag).getCargoSkuId()));
                tradeGoodSku.setCargoSkuName(tradeGoodSkuVoList.get(flag).getCargoSkuName());
                purchaseGoodsSkuService.update(tradeGoodSku);
                saveTradeGoodSkuFreightTplRel(tradeGoodSkuVoList.get(flag).getTplId(), tradeGoodSku.getId()); //保存运费模版
            }
        }

        for (int i = 0; i < tradeGoodSkuVoList.size(); i++) {
            int flag = -1;
            for (PurchaseGoodsSku tradeGoodSku : tradeGoodSkus) {
                if (tradeGoodSku.getCargoSkuId() != null && tradeGoodSku.getCargoSkuId().equals(Long.parseLong(tradeGoodSkuVoList.get(i).getCargoSkuId()))) {
                    flag = i;
                    break;
                }
            }
            if (flag == -1) {
                PurchaseGoodsSku tradeGoodSku = new PurchaseGoodsSku();
                BeanUtils.copyProperties(tradeGoodSkuVoList.get(i), tradeGoodSku);
                tradeGoodSku.setCargoSkuId(Long.parseLong(tradeGoodSkuVoList.get(i).getCargoSkuId()));
                tradeGoodSku.setId(IdGenerator.getDefault().nextId() + "");
                tradeGoodSku.setPurGoodsId(tradeGood.getId());
                tradeGoodSku.setStatus("2");
                tradeGoodSku.setDelFlag(BoolType.F.name());
                purchaseGoodsSkuService.insert(tradeGoodSku);
                saveTradeGoodSkuFreightTplRel(tradeGoodSkuVoList.get(i).getTplId(), tradeGoodSku.getId()); //保存运费模版
            }
        }


    }

    private void saveTradeGoodSkuFreightTplRel(String tplId, String tradeGoodSkuId) {
        FreightTplGoodRel freightTplGoodRel = freightTplGoodRelService.get(Conds.get().eq("good_sku_id", tradeGoodSkuId).eq("rel_type", "purchase"));
        if (StringUtils.isBlank(tplId)) {
            return;
        }
        if (freightTplGoodRel == null) {
            freightTplGoodRel = new FreightTplGoodRel();
            freightTplGoodRel.setRelId(IdGenerator.getDefault().nextId() + "");
            freightTplGoodRel.setGoodSkuId(tradeGoodSkuId + "");
            freightTplGoodRel.setTplId(tplId);
            freightTplGoodRel.setIsValid("T");
            freightTplGoodRel.setDelFlag("F");
            freightTplGoodRel.setDateCreated(new Date());
            freightTplGoodRel.setLastUpdated(new Date());
            freightTplGoodRel.setRelType("purchase");
            freightTplGoodRelService.insert(freightTplGoodRel);
        } else {
            freightTplGoodRel.setTplId(tplId);
            freightTplGoodRel.setLastUpdated(new Date());
            freightTplGoodRel.setRelType("purchase");
            freightTplGoodRelService.update(freightTplGoodRel);
        }
    }
}
