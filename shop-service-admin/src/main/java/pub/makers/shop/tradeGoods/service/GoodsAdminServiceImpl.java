package pub.makers.shop.tradeGoods.service;

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
import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.service.SysDictService;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.logistics.entity.FreightTplGoodRel;
import pub.makers.shop.logistics.service.FreightTplGoodRelService;
import pub.makers.shop.purchaseGoods.vo.StockVo;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.tradeGoods.entity.GoodLabels;
import pub.makers.shop.tradeGoods.entity.TradeGood;
import pub.makers.shop.tradeGoods.entity.TradeGoodSku;
import pub.makers.shop.tradeGoods.entity.TradeGoodsClassify;
import pub.makers.shop.tradeGoods.service.impl.TradeGoodSkuServiceImpl;
import pub.makers.shop.tradeGoods.vo.*;

import java.util.*;

/**
 * Created by daiwenfa on 2017/5/23.
 */
@Service(version = "1.0.0")
public class GoodsAdminServiceImpl implements GoodsMgrBizService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private TradeGoodService tradeGoodService;
    @Autowired
    private GoodLabelsService goodLabelsService;
    @Autowired
    private TradeGoodSkuService tradeGoodSkuService;
    @Autowired
    private FreightTplGoodRelService freightTplGoodRelService;
    @Autowired
    private TradeGoodSkuServiceImpl tradeGoodSkuServiceImpl;
    @Autowired
    private TradeGoodsClassifyService tradeGoodsClassifyService;
    @Reference(version = "1.0.0")
    private TradeGoodBizService tradeGoodBizService;

    @Override
    public ResultList<NewTradeGoodVo> newTradeGoodsPage(NewTradeGoodVo newTradeGoodVo, Paging pg) {
        if(StringUtils.isNotBlank(newTradeGoodVo.getCargoClassIfyName())&&!newTradeGoodVo.getCargoClassIfyName().equals("-1")){
            newTradeGoodVo.setCargoClassIfyName(getAllClassifyId(newTradeGoodVo.getCargoClassIfyName()));
        }
        String sql = FreeMarkerHelper.getValueFromTpl("sql/tradeGood/queryTradeGoodsSkuList.sql", newTradeGoodVo);
        RowMapper<NewTradeGoodVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(NewTradeGoodVo.class);
        List<NewTradeGoodVo> list = jdbcTemplate.query(sql + " limit ?,? ", rowMapper, pg.getPs(), pg.getPn());
        Number total = jdbcTemplate.queryForObject("select count(0) from (" + sql +") nums ",null,Integer.class);
        ResultList<NewTradeGoodVo> resultList = new ResultList<NewTradeGoodVo>();
        resultList.setResultList(list);
        resultList.setTotalRecords(total!=null?total.intValue():0);
        return resultList;
    }

    public String getAllClassifyId(String parentId){
        List<String> allList = new ArrayList<>();
        String classify = " or trade_good.group_id like ";//查询分类id字段
        if(StringUtils.isNotBlank(parentId)) {
            List<TradeGoodsClassify> list = tradeGoodsClassifyService.list(Conds.get().eq("parent_id", parentId));
            List<String> xiajiList = new ArrayList<>();//下级的分类
            if (list.size() > 0) {
                for (TradeGoodsClassify cargoClassify : list) {
                    allList.add(classify+"'%"+cargoClassify.getId() + "%'");
                    xiajiList.add(cargoClassify.getId()+"");
                }
            }
            List<TradeGoodsClassify> lists = tradeGoodsClassifyService.list(Conds.get().in("parent_id", xiajiList));//查询下级的下级
            if (lists.size() > 0) {
                for (TradeGoodsClassify tradeGoodsClassify : lists) {
                    allList.add(classify+"'%"+tradeGoodsClassify.getId() + "%'");
                }
            }
            allList = new ArrayList(new HashSet(allList));//去重
        }
        String allClassifyId = "trade_good.group_id like '%"+ parentId  + "%' " + StringUtils.join(allList," ");
        return allClassifyId;
    }

    @Override
    public List<Map<String, Object>> selectTradeGoodSkuVo(String tradeGoodId) {
        String sql = "select s.on_sales_no,s.curr_stock as out_shelves_no,s.on_pay_no,t.*,c.code,c.name  " +
                "        from trade_good_sku t left join  cargo_sku c on c.id=t.cargo_sku_id LEFT JOIN cargo_sku_stock s ON s.cargo_sku_id=t.cargo_sku_id " +
                "        where 1=1 and t.del_flag != 'T' and t.good_id = ? ";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,tradeGoodId);
        for(int i=0;i<list.size();i++){
            String cargoSkuId = list.get(i).get("cargo_sku_id")==null?"":list.get(0).get("cargo_sku_id").toString();
            String sqls = "select if(isnull(sum(out_shelves_no)),0,out_shelves_no) from cargo_sku_stock where cargo_sku_id= ? ";
            int leftNums = jdbcTemplate.queryForObject(sqls,Integer.class,cargoSkuId);
            list.get(i).put("leftNums",leftNums+"");
        }
        return list;
    }

    @Override
    public List<SysDict> getGoodsTags() {
        return sysDictService.list(Conds.get().eq("dict_type", "goodtags").eq("is_valid","T").eq("del_flag","F"));
    }

    @Override
    public SysDict saveGoodsTags(String tagsName) {
        Map<String, Object>  resultMap = new HashMap<String, Object>();
        SysDict sysDict = new SysDict();
        sysDict.setDictId(IdGenerator.getDefault().nextId() + "");
        sysDict.setDictType("goodtags");
        sysDict.setCode(tagsName);
        sysDict.setValue(tagsName);
        sysDict.setDelFlag("F");
        sysDict.setIsValid("T");
        sysDictService.insert(sysDict);
        return sysDict;
    }

    @Override
    public Map<String, Object> saveTradeGoods(final TradeGoodVo tradeGoodVo, final long userId) {

        if (StringUtils.isBlank(tradeGoodVo.getId())) {
//            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//                @Override
//                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            TradeGood tradeGood = new TradeGood();
            BeanUtils.copyProperties(tradeGoodVo, tradeGood);
            tradeGood.setId(IdGenerator.getDefault().nextId());
            tradeGood.setCargoId(Long.parseLong(tradeGoodVo.getCargoId()));
            tradeGood.setStatus(0);
            tradeGood.setCreator(userId);
            tradeGood.setSort(Long.parseLong(tradeGoodVo.getSort()));
            tradeGood.setCreateTime(new Date());
            tradeGood.setUpdateTime(new Date());
            tradeGood.setClassifyValid(BoolType.T.name());
            tradeGoodService.insert(tradeGood);
            tradeGoodBizService.updateClassifyValid();
            saveGoodsLabels(tradeGoodVo.getLabelIds(), tradeGood); //保存商品
            saveTradeGoodsSku(tradeGoodVo.getTradeGoodSkuVoList(), tradeGood, tradeGoodVo.getDelTradeGoodsSkuIds()); //保存商品SKU
//                }
//            });
        } else {
//            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//                @Override
//                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            TradeGood tradeGood = tradeGoodService.getById(tradeGoodVo.getId());
            tradeGood.setBaseSale(tradeGoodVo.getBaseSale());
            tradeGood.setCargoId(Long.parseLong(tradeGoodVo.getCargoId()));
            tradeGood.setCategory(tradeGoodVo.getCategory());
            tradeGood.setSubtitle(tradeGoodVo.getSubtitle());
            tradeGood.setName(tradeGoodVo.getName());
            tradeGood.setGroupId(tradeGoodVo.getGroupId());
            tradeGood.setGroupName(tradeGoodVo.getGroupName());
            tradeGood.setLabel(tradeGoodVo.getLabel());
            tradeGood.setTheme(tradeGoodVo.getTheme());
            tradeGood.setType(tradeGoodVo.getType());
            tradeGood.setIsStore(tradeGoodVo.getIsStore());
            tradeGood.setUpdateTime(new Date());
            tradeGood.setSort(Long.parseLong(tradeGoodVo.getSort()));
            tradeGoodService.update(tradeGood);
            tradeGoodBizService.updateClassifyValid();
            saveGoodsLabels(tradeGoodVo.getLabelIds(), tradeGood); //保存商品
            saveTradeGoodsSku(tradeGoodVo.getTradeGoodSkuVoList(), tradeGood, tradeGoodVo.getDelTradeGoodsSkuIds()); //保存商品SKU
//                }
//            });
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("success", true);
        return resultMap;
    }

    @Override
    public TradeGoodVo getTradeGoodsInfo(String id) {
        TradeGoodVo tradeGoodVo = new TradeGoodVo();
        if (StringUtils.isBlank(id)) {
            return null;
        }
        TradeGood tradeGood = tradeGoodService.getById(id);
        BeanUtils.copyProperties(tradeGood, tradeGoodVo);
        tradeGoodVo.setId(tradeGood.getId() + "");
        tradeGoodVo.setCargoId(tradeGood.getCargoId() + "");
        tradeGoodVo.setSort(tradeGood.getSort() + "");
        //获取TradeGoodsSkuVoList
        getTradeGoodsSkuVoList(tradeGood, tradeGoodVo);
        //获取labelIds
        getTradeGoodsLabelId(tradeGood, tradeGoodVo);
        return tradeGoodVo;
    }

    @Override
    public List<GoodsEvaluationVo> getEvaluation(String tradeGoodsSkuId) {
        RowMapper<GoodsEvaluationVo> geRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(GoodsEvaluationVo.class);
        String sql = " SELECT good_evaluation.*,weixin_user_info.headImgUrl headImage,cargo_sku.sku_name  " +
                     "FROM " +
                     "good_evaluation " +
                     "LEFT JOIN weixin_user_info ON weixin_user_info.ID = good_evaluation. USER " +
                     "LEFT JOIN trade_good_sku ON good_evaluation.good_sku_id = trade_good_sku.id " +
                     "LEFT JOIN cargo_sku ON trade_good_sku.cargo_sku_id = cargo_sku.id " +
                     "WHERE " +
                     "good_evaluation.good_sku_id = ?";
        List<GoodsEvaluationVo> list = jdbcTemplate.query(sql, geRowMapper, tradeGoodsSkuId);
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
        String sql = "delete from good_evaluation where id = ? ";
        int temp = jdbcTemplate.update(sql,id);
        if(temp>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void saveEvaluation(GoodsEvaluationVo v, String[] urlArray, Long userId) {
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
        String insertEvaluation = "insert into good_evaluation " +
                "(id, good_sku_id, `user`, userName, content, image, evaluate_time, score,is_hide) " +
                "    values (?,?,?,?,?,?,?,?,'1')";
        jdbcTemplate.update(insertEvaluation, evaluationParam.toArray());
    }

    @Override
    public void deleteGoods(String id) {
        // 根据skuid删除商品评论
        String sql1 = " delete from good_evaluation where good_sku_id = ? ";
        jdbcTemplate.update(sql1,id);
        // 根据skuid删除跟该商品有关的所有SKU的供货价
        String sql2 = " delete from store_supply_price where good_sku_id = ? ";
        jdbcTemplate.update(sql2, id);
        // 根据skuid删除商品sku记录
        String sql3 = " update trade_good_sku set del_flag = 'T' where id = ? ";
        jdbcTemplate.update(sql3, id);
    }

    @Override
    public boolean editSort(String tradeGoodId, String sort) {
        String sql = "update trade_good set trade_good.sort = ? where trade_good.id = ? ";
        int result = jdbcTemplate.update(sql,sort,tradeGoodId);
        if(result>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Map<String,Object> selectTradeGoodSkuById(String id) {
        String sql = "select t.on_sales_no, (IFNULL(s.curr_stock, 0) + IFNULL(s.on_pay_no, 0)) AS out_shelves_no, s.on_pay_no,t.*,c.code,c.name as skuName  " +
                    "        from trade_good_sku t left join  cargo_sku c on c.id=t.cargo_sku_id LEFT JOIN cargo_sku_stock s ON s.cargo_sku_id=t.cargo_sku_id " +
                    "        where 1=1 and t.id = ? ";
        Map<String,Object> map = jdbcTemplate.queryForMap(sql,id);
        map.put("id",id);
        if(map.get("cargo_sku_id")!=null){
            map.put("cargo_sku_id",map.get("cargo_sku_id")+"");
        }
        if(map.get("good_id")!=null){
            map.put("good_id",map.get("good_id")+"");
        }
        String sqls = "select if(isnull(sum(out_shelves_no)),0,out_shelves_no) from cargo_sku_stock where cargo_sku_id= ? ";
        int leftNums = jdbcTemplate.queryForObject(sqls,Integer.class,id);
        map.put("leftNums", leftNums + "");
        return map;
    }

    @Override
    public boolean timingUpGoodSkuStatus(TradeGoodSku tradeGoodSku, long userId) {
        TradeGoodSku tgs = tradeGoodSkuServiceImpl.get(Conds.get().eq("id", tradeGoodSku.getId()));
        if(tgs.getNums()!=0){
            return false;
        }
        tradeGoodSkuServiceImpl.update(tradeGoodSku);
        return true;
    }

    @Override
    public List<Map<String, Object>> getGoodsLabel() {
        String sql = "select sys_dict.dict_id,sys_dict.value from sys_dict where sys_dict.dict_type = 'goodtags' AND sys_dict.del_flag='F' AND sys_dict.is_valid='T'  ORDER BY sys_dict.order_num DESC  ";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    /**
     * 获取TradeGoodsLabelsId信息
     * @param tradeGood
     * @param tradeGoodVo
     */
    private void getTradeGoodsLabelId(TradeGood tradeGood, TradeGoodVo tradeGoodVo) {
        List<GoodLabels> goodLabelses = goodLabelsService.list(Conds.get().eq("good_id", tradeGood.getId()));
        List<String> labelIds = Lists.newArrayList();
        for (GoodLabels goodLabelse : goodLabelses) {
            labelIds.add(goodLabelse.getGoodBaseLabelId() + "");
        }
        tradeGoodVo.setLabelIds(labelIds);
    }

    /**
     * 获取TradeGoodsSkuVoList信息
     * @param tradeGood
     * @param tradeGoodVo
     */
    private void getTradeGoodsSkuVoList(TradeGood tradeGood, TradeGoodVo tradeGoodVo) {
        List<TradeGoodSku> tradeGoodSkus = tradeGoodSkuService.list(Conds.get().eq("good_id", tradeGood.getId()));
        List<TradeGoodSkuVo> tradeGoodSkuVoList = Lists.newArrayList();
        for (TradeGoodSku tradeGoodSku : tradeGoodSkus) {
            TradeGoodSkuVo tradeGoodSkuVo = new TradeGoodSkuVo();
            BeanUtils.copyProperties(tradeGoodSku, tradeGoodSkuVo);
            tradeGoodSkuVo.setId(tradeGoodSku.getId() + "");
            tradeGoodSkuVo.setCargoSkuId(tradeGoodSku.getCargoSkuId() + "");
            tradeGoodSkuVoList.add(tradeGoodSkuVo);
        }

        if (tradeGoodSkus.size() > 0) {
            //获取运费模版
            getTradeGoodsSkuFreightTpl(tradeGoodSkus.get(0), tradeGoodVo);
        }

        tradeGoodVo.setTradeGoodSkuVoList(tradeGoodSkuVoList);
    }

    /**
     * 获取运费模版
     * @param tradeGoodSku
     * @param tradeGoodVo
     */
    private void getTradeGoodsSkuFreightTpl(TradeGoodSku tradeGoodSku, TradeGoodVo tradeGoodVo) {
        FreightTplGoodRel freightTplGoodRel = freightTplGoodRelService.get(Conds.get().eq("good_sku_id", tradeGoodSku.getId()).eq("rel_type", "trade"));
        if (freightTplGoodRel != null) {
            tradeGoodVo.setFreightTplId(freightTplGoodRel.getTplId());
        }
    }

    /**
     * 保存商品SKU信息
     * @param tradeGoodSkuVoList
     * @param tradeGood
     * @param delTradeGoodsSkuIds
     */
    private void saveTradeGoodsSku(List<TradeGoodSkuVo> tradeGoodSkuVoList, TradeGood tradeGood, List<String> delTradeGoodsSkuIds) {

        List<TradeGoodSku> tradeGoodSkus = tradeGoodSkuService.list(Conds.get().eq("good_id", tradeGood.getId()));

        for (TradeGoodSku tradeGoodSku : tradeGoodSkus) {
            int flag = -1;
            for (int i = 0; i < tradeGoodSkuVoList.size(); i++) {
                if (tradeGoodSku.getCargoSkuId().equals(Long.parseLong(tradeGoodSkuVoList.get(i).getCargoSkuId()))) {
                    flag = i;
                    break;
                }
            }
            if (flag == -1) {
                // delete 編輯后的商品sku比原來的少，則刪除少去的商品Sku
                tradeGoodSkuService.delete(Conds.get().eq("id", tradeGoodSku.getId()));
            } else {
                tradeGoodSku.setCargoSkuId(Long.parseLong(tradeGoodSkuVoList.get(flag).getCargoSkuId()));
                tradeGoodSku.setCargoSkuName(tradeGoodSkuVoList.get(flag).getCargoSkuName());
                tradeGoodSkuService.update(tradeGoodSku);
                saveTradeGoodSkuFreightTplRel(tradeGoodSkuVoList.get(flag).getTplId(), tradeGoodSku.getId()); //保存运费模版
            }
        }

        for (int i = 0; i < tradeGoodSkuVoList.size(); i++) {
            int flag = -1;
            for (TradeGoodSku tradeGoodSku : tradeGoodSkus) {
                if (tradeGoodSku.getCargoSkuId().equals(Long.parseLong(tradeGoodSkuVoList.get(i).getCargoSkuId()))) {
                    flag = i;
                    break;
                }
            }
            if (flag == -1) {
                TradeGoodSku tradeGoodSku = new TradeGoodSku();
                BeanUtils.copyProperties(tradeGoodSkuVoList.get(i), tradeGoodSku);
                tradeGoodSku.setCargoSkuId(Long.parseLong(tradeGoodSkuVoList.get(i).getCargoSkuId()));
                tradeGoodSku.setId(IdGenerator.getDefault().nextId());
                tradeGoodSku.setGoodId(tradeGood.getId());
                tradeGoodSku.setStatus("2");
                tradeGoodSku.setDelFlag(BoolType.F.name());
                tradeGoodSkuService.insert(tradeGoodSku);
                saveTradeGoodSkuFreightTplRel(tradeGoodSkuVoList.get(i).getTplId(), tradeGoodSku.getId()); //保存运费模版
            }
        }

/*
        //删除不存在的货品SKU对应
        if (delTradeGoodsSkuIds != null && delTradeGoodsSkuIds.size() > 0) {
            for (String delTradeGoodsSkuId : delTradeGoodsSkuIds) {
                tradeGoodSkuService.delete(Conds.get().eq("good_id", tradeGood.getId()).eq("cargo_sku_id", delTradeGoodsSkuId));
            }
        }
        for (TradeGoodSkuVo tradeGoodSkuVo : tradeGoodSkuVoList) {
            if (StringUtils.isBlank(tradeGoodSkuVo.getId())) {
                TradeGoodSku tradeGoodSku = new TradeGoodSku();
                BeanUtils.copyProperties(tradeGoodSkuVo, tradeGoodSku);
                tradeGoodSku.setCargoSkuId(Long.parseLong(tradeGoodSkuVo.getCargoSkuId()));
                tradeGoodSku.setId(IdGenerator.getDefault().nextId());
                tradeGoodSku.setGoodId(tradeGood.getId());
                tradeGoodSkuService.insert(tradeGoodSku);
                saveTradeGoodSkuFreightTplRel(tradeGoodSkuVo.getTplId(), tradeGoodSku.getId()); //保存运费模版
            } else {
                TradeGoodSku tradeGoodSku = tradeGoodSkuService.getById(tradeGoodSkuVo.getId());
                tradeGoodSku.setCargoSkuId(Long.parseLong(tradeGoodSkuVo.getCargoSkuId()));
                tradeGoodSku.setCargoSkuName(tradeGoodSkuVo.getCargoSkuName());
                tradeGoodSkuService.update(tradeGoodSku);
                saveTradeGoodSkuFreightTplRel(tradeGoodSkuVo.getTplId(), tradeGoodSku.getId()); //保存运费模版
            }
        }
        */

    }

    /**
     * 保存运费模版
     * @param tplId
     * @param tradeGoodSkuId
     */
    private void saveTradeGoodSkuFreightTplRel(String tplId, Long tradeGoodSkuId) {

        FreightTplGoodRel freightTplGoodRel = freightTplGoodRelService.get(Conds.get().eq("good_sku_id", tradeGoodSkuId).eq("rel_type", "trade"));
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
            freightTplGoodRel.setRelType("trade");
            freightTplGoodRelService.insert(freightTplGoodRel);
        } else {
            freightTplGoodRel.setTplId(tplId);
            freightTplGoodRel.setLastUpdated(new Date());
            freightTplGoodRel.setRelType("trade");
            freightTplGoodRelService.update(freightTplGoodRel);
        }
    }

    /**
     * 保存商品
     * @param labelIds
     * @param tradeGood
     */
    private void saveGoodsLabels(List<String> labelIds, TradeGood tradeGood) {
        goodLabelsService.delete(Conds.get().eq("good_id", tradeGood.getId()));
        for (String labelId : labelIds) {
            GoodLabels goodLabels = new GoodLabels();
            goodLabels.setId(IdGenerator.getDefault().nextId());
            goodLabels.setGoodId(tradeGood.getId());
            goodLabels.setGoodBaseLabelId(Long.parseLong(labelId));
            goodLabels.setGoodType("trade");
            goodLabelsService.insert(goodLabels);
        }
    }

    /**
     * 获得所有父分类id
     * @param idStr
     * @return
     */
    @Override
    public List<String> getParentClassify(String idStr) {
        List<String> list = Arrays.asList(idStr.split(","));
        List<String> classifyIdList = new ArrayList<>();
        classifyIdList.addAll(list);
        List<TradeGoodsClassify> lists = tradeGoodsClassifyService.list(Conds.get().in("id", list));
        List<String> parentList = new ArrayList<>();
        for(TradeGoodsClassify tradeGoodsClassify:lists){
            if(tradeGoodsClassify.getParentId()!=null){
                if(!tradeGoodsClassify.getParentId().toString().equals("1")){//判断上级是否为最上级
                    parentList.add(tradeGoodsClassify.getParentId().toString());
                }
                classifyIdList.add(tradeGoodsClassify.getParentId().toString());
            }
        }
        if(parentList.size()>0) {//查询最上级
            List<TradeGoodsClassify> listss = tradeGoodsClassifyService.list(Conds.get().in("id", parentList));
            if (listss.size() > 0) {
                for (TradeGoodsClassify tradeGoodsClassifys : listss) {
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
    public StockVo getTimeingData(String id) {
        RowMapper<StockVo> stockVoMapper = ParameterizedBeanPropertyRowMapper.newInstance(StockVo.class);
        String sql = FreeMarkerHelper.getValueFromTpl("sql/tradeGood/getTimeingData.sql", null);
        List<StockVo> list = jdbcTemplate.query(sql, stockVoMapper, id);
        return list.get(0);
    }
}
