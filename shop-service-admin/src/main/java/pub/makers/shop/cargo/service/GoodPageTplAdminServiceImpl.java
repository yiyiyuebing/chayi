package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.baseGood.enums.PageTplClassify;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.cargo.entity.*;
import pub.makers.shop.cargo.entity.vo.GoodPageParam;
import pub.makers.shop.cargo.entity.vo.GoodPageTplVo;
import pub.makers.shop.cargo.entity.vo.ImageGroupVo;
import pub.makers.shop.cargo.vo.GoodPageTplModelSortVo;
import pub.makers.shop.cargo.vo.GoodPageTplModelVo;
import pub.makers.shop.cart.enums.GoodType;
import pub.makers.shop.promotion.service.OtherAboutActivityBizService;
import pub.makers.shop.promotion.vo.ManZenAndPresellVo;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassify;
import pub.makers.shop.purchaseGoods.service.PurchaseClassifyBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseClassifyService;
import pub.makers.shop.purchaseGoods.vo.PurchaseClassifyVo;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.tradeGoods.entity.Image;
import pub.makers.shop.tradeGoods.entity.TradeGood;
import pub.makers.shop.tradeGoods.entity.TradeGoodsClassify;
import pub.makers.shop.tradeGoods.service.ImageService;
import pub.makers.shop.tradeGoods.service.TradeGoodService;
import pub.makers.shop.tradeGoods.service.TradeGoodsClassifyBizService;
import pub.makers.shop.tradeGoods.service.TradeGoodsClassifyService;
import pub.makers.shop.tradeGoods.vo.TradeGoodRelevanceParam;
import pub.makers.shop.tradeGoods.vo.TradeGoodRelevanceVo;
import pub.makers.shop.tradeGoods.vo.TradeGoodsClassifyVo;

import java.util.*;

/**
 * Created by daiwenfa on 2017/6/7.
 */
@Service(version = "1.0.0")
public class GoodPageTplAdminServiceImpl implements GoodPageTplMgrBizService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private GoodPageTplService goodPageTplService;
    @Autowired
    private GoodPageTplModelService goodPageTplModelService;
    @Autowired
    private GoodPageTplApplyService goodPageTplApplyService;
    @Autowired
    private GoodPageTplPostService goodPageTplPostService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private TradeGoodService tradeGoodService;
    @Autowired
    private TradeGoodsClassifyService tradeGoodsClassifyService;
    @Autowired
    private PurchaseClassifyService purchaseClassifyService;
    @Autowired
    private GoodPageTplModelSortService goodPageTplModelSortService;

    @Autowired
    private OtherAboutActivityBizService otherAboutActivityBizService;
    @Reference(version = "1.0.0")
    private PurchaseClassifyBizService purchaseClassifyBizService;
    @Reference(version = "1.0.0")
    private TradeGoodsClassifyBizService tradeGoodsClassifyBizService;
    @Reference(version = "1.0.0")
    private CargoImageBizService cargoImageBizService;


    @Override
    public ResultList<GoodPageTplVo> getPageList(GoodPageParam goodPageParam, Paging pg) {
        String sql = FreeMarkerHelper.getValueFromTpl("sql/cargo/queryGoodPageTplList.sql", goodPageParam);
        RowMapper<GoodPageTplVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(GoodPageTplVo.class);
        List<GoodPageTplVo> list = jdbcTemplate.query(sql,rowMapper,pg.getPs(),pg.getPn());

        String countSql = FreeMarkerHelper.getValueFromTpl("sql/cargo/countGoodPageTplList.sql",goodPageParam);
        Number total = jdbcTemplate.queryForObject(countSql,null,Integer.class);

        ResultList<GoodPageTplVo> resultList = new ResultList<GoodPageTplVo>();
        resultList.setResultList(list);
        resultList.setTotalPages(total!=null?total.intValue():0);
        return resultList;
    }

    @Override
    public boolean ableOrDisable(String id, String operation, long userId) {
        GoodPageTpl cargoMaterialLibraryOld = goodPageTplService.get(Conds.get().eq("id", id));
        String flag = cargoMaterialLibraryOld.getIsValid();
        if(!flag.equals(operation)) {
            goodPageTplService.update(Update.byId(id).set("is_valid", operation).set("last_updated",new Date()));
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(String id) {
        goodPageTplService.update(Update.byId(id).set("del_flag", "T").set("last_updated", new Date()));
        return true;
    }

    @Override
    public String getAllClassifyId(String parentId, String goodType, String applyType){
        List<String> allList = new ArrayList<>();
        String allClassifyId = "";
        if(StringUtils.isNotBlank(parentId)) {
            if(OrderBizType.trade.toString().equals(goodType)) {
//                String classify = "or tg.group_id like ";
                String classify = "or find_in_set(%s, tg.group_id) ";
                List<TradeGoodsClassify> list = tradeGoodsClassifyService.list(Conds.get().eq("parent_id", parentId));
                List<String> xiajiList = new ArrayList<>();//有下级的分类
                if (list.size() > 0) {
                    for (TradeGoodsClassify cargoClassify : list) {
//                        allList.add(classify + " '%"+cargoClassify.getId() + "%' ");
                        allList.add(classify.replace("%s", cargoClassify.getId() + ""));
                        xiajiList.add(cargoClassify.getId() + "");
                    }
                }
                List<TradeGoodsClassify> lists = tradeGoodsClassifyService.list(Conds.get().in("parent_id", xiajiList));//查询下级的下级
                if (lists.size() > 0) {
                    for (TradeGoodsClassify tradeGoodsClassify : lists) {
//                        allList.add(classify + " '%"+tradeGoodsClassify.getId() + "%' ");
                        allList.add(classify.replace("%s", tradeGoodsClassify.getId() + ""));
                    }
                }
            }else{
                List<PurchaseClassify> list = purchaseClassifyService.list(Conds.get().eq("parent_id", parentId));
                List<String> xiajiList = new ArrayList<>();//有下级的分类
                String classify = "or find_in_set(%s, pg.group_id) ";
                if (list.size() > 0) {
                    for (PurchaseClassify cargoClassify : list) {
//                        allList.add(classify + " '%"+cargoClassify.getId() + "%' ");
                        allList.add(classify.replace("%s", cargoClassify.getId() + ""));
                        xiajiList.add(cargoClassify.getId() + "");
                    }
                }
                List<PurchaseClassify> lists = purchaseClassifyService.list(Conds.get().in("parent_id", xiajiList));//查询下级的下级
                if (lists.size() > 0) {
                    for (PurchaseClassify purchaseClassify : lists) {
//                        allList.add(classify + " '%"+purchaseClassify.getId() + "%' ");
                        allList.add(classify.replace("%s", purchaseClassify.getId() + ""));
                    }
                }
            }

            allList = new ArrayList(new HashSet(allList));//去重
//            if(goodType.equals(OrderBizType.trade.toString())) {
//                allClassifyId = " tg.group_id like '%"+parentId+"%' " + StringUtils.join(allList, " ");
//            }else{
//                allClassifyId = " pg.group_id like '%"+parentId+"%' " + StringUtils.join(allList, " ");
//            }

            if(OrderBizType.trade.toString().equals(goodType)) {
                allClassifyId = " (find_in_set("+ parentId +", tg.group_id) " + StringUtils.join(allList, " ") + ")";
            }else{
                allClassifyId = " (find_in_set("+ parentId +", pg.group_id) " + StringUtils.join(allList, " ") + ")";
            }

        }

        if (StringUtils.isBlank(parentId) && StringUtils.isNotBlank(applyType) && OrderBizType.purchase.toString().equals(goodType)) {
            String classify = "and !find_in_set(%s, pg.group_id) ";
            PurchaseClassify sanchaClassify = purchaseClassifyService.get(Conds.get().eq("name", "散茶"));
            List<PurchaseClassify> list = purchaseClassifyService.list(Conds.get().eq("parent_id", sanchaClassify.getId()));
            List<String> xiajiList = new ArrayList<>();//有下级的分类
            if (list.size() > 0) {
                for (PurchaseClassify cargoClassify : list) {
//                        allList.add(classify + " '%"+cargoClassify.getId() + "%' ");
                    allList.add(classify.replace("%s", cargoClassify.getId() + ""));
                    xiajiList.add(cargoClassify.getId() + "");
                }
            }
            List<PurchaseClassify> lists = purchaseClassifyService.list(Conds.get().in("parent_id", xiajiList));//查询下级的下级
            if (lists.size() > 0) {
                for (PurchaseClassify purchaseClassify : lists) {
//                        allList.add(classify + " '%"+purchaseClassify.getId() + "%' ");
                    allList.add(classify.replace("%s", purchaseClassify.getId() + ""));
                }
            }

            if(OrderBizType.purchase.toString().equals(goodType)) {
                allClassifyId = " (!find_in_set("+ sanchaClassify.getId() +", pg.group_id) " + StringUtils.join(allList, " ") + ")";
            }
        }


        return allClassifyId;
    }

    @Override
    public ResultList<TradeGoodRelevanceVo> getTradeGoodRelevanceListPage(TradeGoodRelevanceParam param, Paging pg) {

        param.setClassifyId(getAllClassifyId(param.getClassifyId(), param.getGoodType(), param.getApplyType()));

        if (StringUtils.isNotBlank(param.getSkuIds())) {
            param.setSkuIds(param.getSkuIds().replace(",,", ","));
        }
        if (StringUtils.isNotBlank(param.getGoodIds())) {
            param.setGoodIds(param.getGoodIds().replace(",,", ","));
        }

        String[] skuIds = param.getSkuIds().split(",");
        String sql = FreeMarkerHelper.getValueFromTpl("sql/purchaseGood/queryPurchaseGoodSkuList.sql", param);
        RowMapper<TradeGoodRelevanceVo> tradeGoodRelevanceVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(TradeGoodRelevanceVo.class);
        List<TradeGoodRelevanceVo> tradeGoodRelevanceVoList = jdbcTemplate.query(sql,tradeGoodRelevanceVoRowMapper,pg.getPs(),pg.getPn());

        //查询图片
        List<String> imageList = Lists.newArrayList();
        imageList.addAll(ListUtils.getIdSet(tradeGoodRelevanceVoList, "smallImage"));
        Map<String, ImageVo> imageVoMap = cargoImageBizService.getImageByGroup(imageList);

        ManZenAndPresellVo manZenAndPresellVo = new ManZenAndPresellVo();
        manZenAndPresellVo.setOrderBizType(param.getGoodType());
        List<String> goodSkuIds = new ArrayList<String>();
        goodSkuIds.addAll(ListUtils.getIdSet(tradeGoodRelevanceVoList, "id"));
        if (goodSkuIds != null && !goodSkuIds.isEmpty()) {
            manZenAndPresellVo.setGoodSkuIdList(goodSkuIds);
            manZenAndPresellVo.setGoodSkuIds(StringUtils.join(goodSkuIds, ","));
        }
        Map<String, List<ManZenAndPresellVo>> resultMap = otherAboutActivityBizService.getManzengPresellGoodVoListMap(manZenAndPresellVo);

//        List<String> pcAlbumIdList = new ArrayList<>();
        for (TradeGoodRelevanceVo tradeGoodRelevanceVo : tradeGoodRelevanceVoList) {
            //设置图片
            if (StringUtils.isNotBlank(tradeGoodRelevanceVo.getSmallImage()) && imageVoMap.get(tradeGoodRelevanceVo.getSmallImage()) != null) {
                tradeGoodRelevanceVo.setSmallImage(imageVoMap.get(tradeGoodRelevanceVo.getSmallImage()).getUrl());
            }

            if (resultMap.get(tradeGoodRelevanceVo.getId()) != null) {
                tradeGoodRelevanceVo.setManZenAndPresellVoList(resultMap.get(tradeGoodRelevanceVo.getId()));
            }

            if(skuIds != null) {//判断是否关联
                for (String skuId : skuIds) {
                    if(tradeGoodRelevanceVo.getId().equals(skuId)){
                        tradeGoodRelevanceVo.setIsRelevance("T");
                        break;
                    }
                }
                if(!"T".equals(tradeGoodRelevanceVo.getIsRelevance())){
                    tradeGoodRelevanceVo.setIsRelevance("F");
                }
            }else {
                tradeGoodRelevanceVo.setIsRelevance("F");
            }
        }

        String countSql = FreeMarkerHelper.getValueFromTpl("sql/purchaseGood/countPurchaseGoodSkuList.sql", param);
        Integer totalCount = jdbcTemplate.queryForObject(countSql, Integer.class);

        ResultList<TradeGoodRelevanceVo> cargoVoResultList = new ResultList<TradeGoodRelevanceVo>();
        cargoVoResultList.setResultList(tradeGoodRelevanceVoList);
        cargoVoResultList.setTotalRecords(totalCount);
        return cargoVoResultList;
    }

    @Override
    public int countRelevanceNum(String id, String type) {
        int num = 0;
        if("apply".equals(type)){
            GoodPageTplApply goodPageTplApply = goodPageTplApplyService.get(Conds.get().eq("id", id));
            if(goodPageTplApply!=null) {
                if (goodPageTplApply.getGoodIds() != null && !"".equals(goodPageTplApply.getGoodIds())) {
                    num = goodPageTplApply.getGoodIds().split(",").length;
                }
            }
        }else if("model".equals(type)){
            GoodPageTplModel goodPageTplModel = goodPageTplModelService.get(Conds.get().eq("id", id));
            if(goodPageTplModel!=null) {
                if (goodPageTplModel.getGoodIds() != null && !"".equals(goodPageTplModel.getGoodIds())) {
                    num = goodPageTplModel.getGoodIds().split(",").length;
                }
            }
        }
        return num;
    }

    /**
     * 关联或取消关联
     * @param param
     * @param userId
     * @return
     */
    @Override
    public boolean relevanceOrCancel(TradeGoodRelevanceParam param, long userId) {
        String tradeGoodId = param.getGoodIds();
        String goodIds = "";
        GoodPageTplApply goodPageTplApply = null;
        GoodPageTplModel goodPageTplModel = null;
        String goodIdsOld = null;

        if("apply".equals(param.getType())){//判断操作位置
            goodPageTplApply = goodPageTplApplyService.get(Conds.get().eq("id", param.getId()));
            if(goodPageTplApply!=null) {
                goodIdsOld = goodPageTplApply.getGoodIds()+"";//加""必定不为空
            }
        }else if("model".equals(param.getType())){
            goodPageTplModel = goodPageTplModelService.get(Conds.get().eq("id", param.getId()));
            if(goodPageTplModel!=null) {
                goodIdsOld = goodPageTplModel.getGoodIds()+"";
            }
        }
        if(goodIdsOld==null){//判断是否存在
            return false;
        }

        TradeGood tradeGood = tradeGoodService.get(Conds.get().eq("id", param.getGoodIds()));
        if(tradeGood==null){
            return false;
        }
        //操作动作，relevance关联cancel取消关联
        String operation = param.getOperation();
        if (!"relevance".equals(operation)&&!"cancel".equals(operation)){
            return false;
        }

        if(goodIdsOld!=null&&!"".equals(goodIdsOld)) {
            String[] relevance = goodIdsOld.split(",");
            if(operation.equals("relevance")) {
                for (String id : relevance) {
                    if (id.equals(tradeGoodId)) {
                        return true;
                    }
                }
                goodIds = goodIdsOld+","+tradeGoodId;
            }else if(operation.equals("cancel")){
                for(int i = 0;i<relevance.length;i++){
                    if(!relevance[i].equals(tradeGoodId)){
                        String id = relevance[i];
                        if(i==0){
                            goodIds = id;
                        }else {
                            goodIds = goodIds + "," + id;
                        }
                    }
                }
            }
        }else{
            if(operation.equals("cancel")){
                return true;
            }else if(operation.equals("relevance")){
                goodIds = tradeGoodId;
            }
        }
        if("apply".equals(param.getType())){
            goodPageTplApply.setLastUpdated(new Date());
            goodPageTplApply.setGoodIds(goodIds);
            goodPageTplApplyService.update(goodPageTplApply);
        }else{
            goodPageTplModel.setLastUpdated(new Date());
            goodPageTplModel.setGoodIds(goodIds);
            goodPageTplModelService.update(goodPageTplModel);
        }
        return true;
    }

    @Override
    public boolean setApply(GoodPageTplApply goodPageTplApply) {
        GoodPageTplApply goodPageTplApplyOld =goodPageTplApplyService.get(Conds.get().eq("tpl_id",goodPageTplApply.getTplId()));
        if(goodPageTplApplyOld==null){
            goodPageTplApply.setId(IdGenerator.getDefault().nextId());
            goodPageTplApply.setDelFlag("F");
            goodPageTplApply.setIsValid("T");
            goodPageTplApply.setDateCreated(new Date());
            goodPageTplApplyService.insert(goodPageTplApply);
        }else{
            goodPageTplApply.setId(goodPageTplApplyOld.getId());
            goodPageTplApply.setLastUpdated(new Date());
            goodPageTplApplyService.update(goodPageTplApply);
        }
        return true;
    }

    @Override
    public void addOrUpdate(GoodPageTpl goodPageTpl,Long userId) {
        Long tplId;
        String tplClassCode = "";
        if(goodPageTpl.getId()==null||goodPageTpl.getId().equals("")){
            tplId = IdGenerator.getDefault().nextId();
            if("trade".equals(goodPageTpl.getOrderBizType())){
                goodPageTpl.setOrderBizType(GoodType.trade.toString());
            }else{
                goodPageTpl.setOrderBizType(GoodType.purchase.toString());
            }
            if(PageTplClassify.pclist.toString().equals(goodPageTpl.getTplClassCode())){
                tplClassCode = PageTplClassify.pclist.toString();
            }else if(PageTplClassify.pcdetail.toString().equals(goodPageTpl.getTplClassCode())) {
                tplClassCode = PageTplClassify.pcdetail.toString();
            }else if(PageTplClassify.mobileb2b.toString().equals(goodPageTpl.getTplClassCode())){
                tplClassCode = PageTplClassify.mobileb2b.toString();
            }else if(PageTplClassify.mobileb2c.toString().equals(goodPageTpl.getTplClassCode())){
                tplClassCode = PageTplClassify.mobileb2c.toString();
            }else if(PageTplClassify.mobileb2bbottom.toString().equals(goodPageTpl.getTplClassCode())){
                tplClassCode = PageTplClassify.mobileb2bbottom.toString();
            }
            goodPageTpl.setId(tplId);
            goodPageTpl.setCreateBy(userId + "");
            goodPageTpl.setDateCreated(new Date());
            goodPageTpl.setDelFlag("F");
            goodPageTpl.setIsValid(BoolType.T.name());
            goodPageTpl.setLastUpdated(new Date());
            goodPageTplService.insert(goodPageTpl);
        }else{
            tplId = goodPageTpl.getId();
            goodPageTpl.setLastUpdated(new Date());
            goodPageTplService.update(goodPageTpl);
        }

        goodPageTplModelService.delete(Conds.get().eq("tpl_id", tplId));
        goodPageTplModelSortService.delete(Conds.get().eq("tpl_id", tplId));
        List<GoodPageTplModel> list = goodPageTpl.getGoodPageTplModelList();
        long i = list.size();
        for(GoodPageTplModel goodPageTplModel:list){
            i--;
            //保存图片
            String insertImage = "insert into image (id, pic_url,group_id,create_time, create_by) values (?,?,?,?,?) ";
            List imageParam = new ArrayList();
            String id = IdGenerator.getDefault().nextId() + "";
            String groupId = IdGenerator.getDefault().nextId() + "";
            imageParam.add(id);
            imageParam.add(goodPageTplModel.getAdImgId());
            imageParam.add(groupId);
            imageParam.add(new Date());
            imageParam.add(userId);
            jdbcTemplate.update(insertImage, imageParam.toArray());
            Long modelId = IdGenerator.getDefault().nextId();
            goodPageTplModel.setSort(i);
            goodPageTplModel.setAdImgId(groupId);
            goodPageTplModel.setId(modelId);
            goodPageTplModel.setTplId(tplId);
            goodPageTplModel.setCreateBy(userId);
            goodPageTplModel.setTplClassCode(tplClassCode);
            goodPageTplModel.setIsValid(goodPageTpl.getIsValid());
            goodPageTplModel.setDelFlag("F");
            goodPageTplModel.setDateCreated(new Date());
            goodPageTplModel.setLastUpdated(new Date());
            goodPageTplModelService.insert(goodPageTplModel);
            List<GoodPageTplModelSort> lists = goodPageTplModel.getGoodPageTplModelSortList();
            if(lists!=null&&lists.size()>0) {
                for (GoodPageTplModelSort goodPageTplModelSort : lists) {
                    goodPageTplModelSort.setId(IdGenerator.getDefault().nextId());
                    goodPageTplModelSort.setTplId(tplId);
                    goodPageTplModelSort.setModelId(modelId);
                    goodPageTplModelSortService.insert(goodPageTplModelSort);
                }
            }
        }

        if ("mobileb2bbottom".equals(goodPageTpl.getTplClassCode())) {//默认设置应用全局应用
            GoodPageTplApply goodPageTplApply = new GoodPageTplApply();
            goodPageTplApply.setId(IdGenerator.getDefault().nextId());
            goodPageTplApply.setDelFlag("F");
            goodPageTplApply.setIsValid("T");
            goodPageTplApply.setDateCreated(new Date());
            goodPageTplApply.setTplId(tplId);
            goodPageTplApply.setApplyScope("all");
            goodPageTplApply.setSort(Long.parseLong(1+""));
            this.setApply(goodPageTplApply);
        }
    }

    @Override
    public GoodPageTpl getGoodPageTplData(String id) {
        GoodPageTpl goodPageTpl = new GoodPageTpl();
        goodPageTpl = goodPageTplService.get(Conds.get().eq("id", id));
        List<GoodPageTplModel> list = goodPageTplModelService.list(Conds.get().eq("tpl_id",id));
        for (GoodPageTplModel goodPageTplModel : list) {
            if (StringUtils.isNotBlank(goodPageTplModel.getAdImgId())) {//查询图片
                List<Image> images = imageService.list(Conds.get().eq("group_id", goodPageTplModel.getAdImgId()));
                if (images.size() > 0) {
                    goodPageTplModel.setImage(images);
                }
            }
            List<GoodPageTplModelSort> goodPageTplModelSortlist = goodPageTplModelSortService.list(Conds.get().eq("tpl_id", id).eq("model_id", goodPageTplModel.getId()));
            if (goodPageTplModelSortlist != null) {
                List<GoodPageTplModelSortVo> goodPageTplModelSortVolist = new ArrayList<>();
                for(GoodPageTplModelSort goodPageTplModelSort:goodPageTplModelSortlist) {
                    GoodPageTplModelSortVo goodPageTplModelSortVo = new GoodPageTplModelSortVo();
                    goodPageTplModelSortVo.setId(goodPageTplModelSort.getId()+"");
                    goodPageTplModelSortVo.setTplId(goodPageTplModelSort.getTplId()+"");
                    goodPageTplModelSortVo.setGoodId(goodPageTplModelSort.getGoodId()+"");
                    goodPageTplModelSortVo.setGoodSkuId(goodPageTplModelSort.getGoodSkuId()+"");
                    goodPageTplModelSortVo.setSort(goodPageTplModelSort.getSort());
                    goodPageTplModelSortVolist.add(goodPageTplModelSortVo);
                }
                goodPageTplModel.setGoodPageTplModelSortVoList(goodPageTplModelSortVolist);
            }
        }
        goodPageTpl.setGoodPageTplModelList(list);
        return goodPageTpl;
    }

    @Override
    public List<GoodPageTplPost> getGoodPageTplPostData(String type) {
        List<GoodPageTplPost> goodPageTplPostList = goodPageTplPostService.list(Conds.get().eq("tpl_class_code", type).eq("is_valid","T").eq("del_flag","F"));
        return goodPageTplPostList;
    }

    @Override
    public GoodPageTplApply getGoodPageTplApplyData(String id) {
        GoodPageTplApply goodPageTplApply = goodPageTplApplyService.get(Conds.get().eq("tpl_id", id));

        if (goodPageTplApply == null) {
            return null;
        }

        GoodPageTpl goodPageTpl = goodPageTplService.get(Conds.get().eq("id", goodPageTplApply.getTplId()));
        if ("classify".equals(goodPageTplApply.getApplyScope())) {
            List<String> classifyIdList = Arrays.asList(goodPageTplApply.getClassifyIds().split(","));
            if (OrderBizType.trade.name().equals(goodPageTpl.getOrderBizType())) {
                List<TradeGoodsClassifyVo> tradeGoodsClassifyVos = tradeGoodsClassifyBizService.findAllAndParent(classifyIdList);
                goodPageTplApply.setClassifyIds(StringUtils.join(ListUtils.getIdSet(tradeGoodsClassifyVos, "id"), ","));
            } else {
                List<PurchaseClassifyVo> purchaseClassifyVos = purchaseClassifyBizService.findAllAndParent(classifyIdList, null);
                goodPageTplApply.setClassifyIds(StringUtils.join(ListUtils.getIdSet(purchaseClassifyVos, "id"), ","));

            }
        }
        return goodPageTplApply;
    }

    @Override
    public ResultList<TradeGoodRelevanceVo> getLinkPage(TradeGoodRelevanceParam param, Paging pg) {
//        String[] goodIds = null;
//        if(param.getGoodIds()!=null) {
//            goodIds = param.getGoodIds().split(",");
//        }
        /*
        * if(StringUtils.isNotBlank(newPurchaseGoodsVo.getClassifyName())&&!newPurchaseGoodsVo.getClassifyName().equals("-1")){
            newPurchaseGoodsVo.setClassifyName(getAllClassifyId(newPurchaseGoodsVo.getClassifyName()));
        }*/
        if(StringUtils.isNotBlank(param.getClassifyName())&&!param.getClassifyName().equals("-1")){
            param.setClassifyName(getAllClassifyId(param.getClassifyName(), "purchase", param.getApplyType()));
        }
        String sql = FreeMarkerHelper.getValueFromTpl("sql/purchaseGood/queryLinkList.sql",param);
//        System.out.print(sql);
        RowMapper<TradeGoodRelevanceVo> tradeGoodRelevanceVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(TradeGoodRelevanceVo.class);
        List<TradeGoodRelevanceVo> tradeGoodRelevanceVoList = jdbcTemplate.query(sql+" limit ?,? ",tradeGoodRelevanceVoRowMapper,pg.getPs(),pg.getPn());

        /*for (TradeGoodRelevanceVo tradeGoodRelevanceVo : tradeGoodRelevanceVoList) {
            if (StringUtils.isNotBlank(tradeGoodRelevanceVo.getPcAlbumId())) {//查询图片
                List<Image> images = imageService.list(Conds.get().eq("group_id", tradeGoodRelevanceVo.getPcAlbumId()));
                if (images.size() > 0) {
                    tradeGoodRelevanceVo.setSmallImage(images.get(0).getPicUrl());
                }
            }
            if(goodIds!=null) {//判断是否关联
                for (String goodId : goodIds) {
                    if(tradeGoodRelevanceVo.getId().equals(goodId)){
                        tradeGoodRelevanceVo.setIsRelevance("T");
                        break;
                    }
                }
                if(!"T".equals(tradeGoodRelevanceVo.getIsRelevance())){
                    tradeGoodRelevanceVo.setIsRelevance("F");
                }
            }else {
                tradeGoodRelevanceVo.setIsRelevance("F");
            }
        }
*/
        //String countSql = FreeMarkerHelper.getValueFromTpl("sql/purchaseGood/countLinkList.sql", param);
        //Integer totalCount = jdbcTemplate.queryForObject(countSql, Integer.class);

        Integer totalCount = jdbcTemplate.queryForObject("select count(0) from (" + sql +") nums ",null,Integer.class);

        ResultList<TradeGoodRelevanceVo> cargoVoResultList = new ResultList<TradeGoodRelevanceVo>();
        cargoVoResultList.setResultList(tradeGoodRelevanceVoList);
        cargoVoResultList.setTotalRecords(totalCount);
        return cargoVoResultList;
    }

    public String getAllClassifyId(String parentId){
        List<String> allList = new ArrayList<>();
        String classify = " or p.group_id like ";//查询分类id字段
        if(StringUtils.isNotBlank(parentId)) {
            List<PurchaseClassify> list = purchaseClassifyService.list(Conds.get().eq("parent_id", parentId));
            List<String> xiajiList = new ArrayList<>();//下级的分类
            if (list.size() > 0) {
                for (PurchaseClassify cargoClassify : list) {
                    allList.add(classify + "'%"+cargoClassify.getId()+"%'");
                    xiajiList.add(cargoClassify.getId());
                }
            }
            List<PurchaseClassify> lists = purchaseClassifyService.list(Conds.get().in("parent_id", xiajiList));//查询下级的下级
            if (lists.size() > 0) {
                for (PurchaseClassify purchaseClassify : lists) {
                    allList.add(classify + "'%"+purchaseClassify.getId() + "%'");
                }
            }
            allList = new ArrayList(new HashSet(allList));//去重
        }
        String allClassifyId = "p.group_id like '%"+ parentId+ "%' " + StringUtils.join(allList," ");
        return allClassifyId;
    }

    @Override
    public ResultList<GoodPageTplModelVo> getBottomGoodPagePageList(GoodPageTplModel param, Paging pg) {
        String sql = FreeMarkerHelper.getValueFromTpl("sql/cargo/queryBottomGoodList.sql", param);
        RowMapper<GoodPageTplModelVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(GoodPageTplModelVo.class);
        List<GoodPageTplModelVo> list = jdbcTemplate.query(sql + " limit ?,? ",rowMapper, pg.getPs(), pg.getPn());
        Number total = jdbcTemplate.queryForObject("select count(0) from (" + sql + ") nums ", null, Integer.class);
        ResultList<GoodPageTplModelVo> resultList = new ResultList<GoodPageTplModelVo>();
        resultList.setResultList(list);
        resultList.setTotalRecords(total != null ? total.intValue() : 0);
        return resultList;
    }
}
