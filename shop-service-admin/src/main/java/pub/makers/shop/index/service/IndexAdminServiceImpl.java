package pub.makers.shop.index.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.service.SysDictService;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.index.entity.IndexAdImages;
import pub.makers.shop.index.entity.IndexFloorKeyword;
import pub.makers.shop.index.entity.IndexModule;
import pub.makers.shop.index.entity.IndexModuleGood;
import pub.makers.shop.index.enums.IndexModuleType;
import pub.makers.shop.index.vo.IndexAdImagesVo;
import pub.makers.shop.index.vo.IndexFloorVo;
import pub.makers.shop.index.vo.IndexHotSaleVo;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassify;
import pub.makers.shop.purchaseGoods.service.PurchaseClassifyService;
import pub.makers.shop.tradeGoods.entity.TradeGoodsClassify;
import pub.makers.shop.tradeGoods.service.ImageService;
import pub.makers.shop.tradeGoods.service.TradeGoodsClassifyService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by daiwenfa on 2017/6/13.
 */
@Service(version = "1.0.0")
public class IndexAdminServiceImpl implements IndexAdminService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IndexAdImagesService indexAdImagesService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private TradeGoodsClassifyService tradeGoodsClassifyService;
    @Autowired
    private PurchaseClassifyService purchaseClassifyService;
    @Autowired
    private IndexModuleService indexModuleService;
    @Autowired
    private IndexModuleGoodService indexModuleGoodService;
    @Autowired
    private IndexFloorKeywordService indexFloorKeywordService;
    @Autowired
    private SysDictService sysDictService;
    /**
     * 查询图片管理表格数据
     * @param indexAdImages
     * @param pg
     * @return
     */
    @Override
    public ResultList<IndexAdImagesVo> getIndexAdImagesPageList(IndexAdImages indexAdImages, Paging pg) {
        String sql = FreeMarkerHelper.getValueFromTpl("sql/index/queryIndexAdImagesList.sql", indexAdImages);
        RowMapper<IndexAdImagesVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(IndexAdImagesVo.class);
        List<IndexAdImagesVo> list = jdbcTemplate.query(sql,rowMapper,pg.getPs(),pg.getPn());

        String countSql = FreeMarkerHelper.getValueFromTpl("sql/index/countIndexAdImagesList.sql", indexAdImages);
        Number total = jdbcTemplate.queryForObject(countSql,null,Integer.class);

        ResultList<IndexAdImagesVo> resultList = new ResultList<IndexAdImagesVo>();
        resultList.setResultList(list);
        resultList.setTotalPages(total!=null?total.intValue():0);
        return resultList;
    }

    /**
     * 新增或修改图片管理
     * @param indexAdImages
     */
    @Override
    public void addOrUpdateIndexAdImages(IndexAdImages indexAdImages) {
        if(indexAdImages.getId()==null||indexAdImages.getId().equals("")) {
            indexAdImages.setId(IdGenerator.getDefault().nextId() + "");
            indexAdImages.setDelFlag("F");
            indexAdImages.setDateCreated(new Date());
            indexAdImages.setLastUpdated(new Date());
            indexAdImagesService.insert(indexAdImages);
        }else {
            indexAdImages.setLastUpdated(new Date());
            indexAdImagesService.update(indexAdImages);
        }
    }

    /**
     * 禁用和启用图片管理
     * @param id
     * @param operation
     * @param userId
     * @return
     */
    @Override
    public boolean ableOrDisableIndexAdImages(String id, String operation, long userId) {
        IndexAdImages indexAdImagesOld = indexAdImagesService.get(Conds.get().eq("id", id));
        String flag = indexAdImagesOld.getIsValid();
        if(!flag.equals(operation)) {
            indexAdImagesService.update(Update.byId(id).set("is_valid", operation).set("last_updated",new Date()));
            return true;
        }
        return false;
    }

    /**
     * 删除图片管理
     * @param id
     * @return
     */
    @Override
    public boolean removeIndexAdImages(String id) {
        indexAdImagesService.update(Update.byId(id).set("del_flag","T").set("last_updated",new Date()));
        return true;
    }

    /**
     * 获取商品分类的一级菜单
     * @return
     */
    @Override
    public List<PurchaseClassify> getOneGoodsClassify() {
        List<PurchaseClassify> list = purchaseClassifyService.list(Conds.get().eq("parent_id", "1").eq("del_flag","F"));
        return list;
    }

    /**
     * 通过id获取图片信息
     * @param id
     * @return
     */
    @Override
    public IndexAdImages getIndexAdImagesData(String id) {
        return indexAdImagesService.get(Conds.get().eq("id",id));
    }

    /**
     * 热销列表数据
     * @param indexModule
     * @param pg
     * @return
     */
    @Override
    public ResultList<IndexHotSaleVo> getIndexModuleGoodPageList(IndexModule indexModule, Paging pg) {
        String sql = FreeMarkerHelper.getValueFromTpl("sql/index/queryIndexHotSaleList.sql", indexModule);
        RowMapper<IndexHotSaleVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(IndexHotSaleVo.class);
        List<IndexHotSaleVo> list = jdbcTemplate.query(sql + " limit ?,? ", rowMapper, pg.getPs(), pg.getPn());
        Number total = jdbcTemplate.queryForObject("select count(0) from (" + sql +") nums ",null,Integer.class);
        ResultList<IndexHotSaleVo> resultList = new ResultList<IndexHotSaleVo>();
        resultList.setResultList(list);
        resultList.setTotalRecords(total!=null?total.intValue():0);
        return resultList;
    }

    @Override
    public boolean removeIndexModule(String id) {
        List<IndexModuleGood> list = indexModuleGoodService.list(Conds.get().eq("module_id", id));
        if (list != null && list.size() > 0) {
            for (IndexModuleGood indexModuleGood : list) {
                indexModuleGoodService.update(Update.byId(indexModuleGood.getId()).set("del_flag", "T").set("last_updated", new Date()));
            }
        }
        List<IndexFloorKeyword> lists = indexFloorKeywordService.list(Conds.get().eq("floor_id", id));
        if (lists != null && lists.size() > 0) {
            for (IndexFloorKeyword indexFloorKeyword : lists) {
                indexFloorKeywordService.update(Update.byId(indexFloorKeyword.getId()).set("del_flag", "T").set("last_updated", new Date()));
            }
        }
        indexModuleService.update(Update.byId(id).set("del_flag", "T").set("last_updated", new Date()));
        return true;
    }

    @Override
    public void addOrUpdateIndexModuleGood(IndexModule indexModule) {
        String id = "";
        if(indexModule.getModuleType().equals("re")){
            indexModule.setModuleType(IndexModuleType.re.toString());
        }
        if(indexModule.getId().equals("")||indexModule.getId()==null){
            id = IdGenerator.getDefault().nextId() + "";
            indexModule.setId(id);
            indexModule.setDelFlag("F");
            indexModule.setIsValid("T");
            indexModule.setDateCreated(new Date());
            indexModuleService.insert(indexModule);
        }else{
            id = indexModule.getId();
            indexModule.setLastUpdated(new Date());
            indexModuleService.update(indexModule);
        }
        indexModuleGoodService.delete(Conds.get().eq("module_id",id));
        List<IndexModuleGood> list = indexModule.getIndexModuleGoodList();
        for (IndexModuleGood indexModuleGood:list){
            indexModuleGood.setId(IdGenerator.getDefault().nextId() + "");
            indexModuleGood.setModuleId(id);
            indexModuleGood.setIsValid("T");
            indexModuleGood.setDelFlag("F");
            indexModuleGood.setDateCreated(new Date());
            indexModuleGoodService.insert(indexModuleGood);
        }
    }

    /**
     * 获取热卖专区信息
     * @param id
     * @return
     */
    @Override
    public IndexModule getIndexModuleGoodData(String id) {
        IndexModule indexModule = indexModuleService.get(Conds.get().eq("id", id));
        List<IndexModuleGood> list = indexModuleGoodService.list(Conds.get().eq("module_id",id));
        indexModule.setIndexModuleGoodList(list);
        return indexModule;
    }

    /**
     * 编辑商品
     * @param indexModule
     */
    @Override
    public void editRelevance(IndexModule indexModule) {
        List<IndexModuleGood> list = Lists.newArrayList();
        for(IndexModuleGood indexModuleGood:indexModule.getIndexModuleGoodList()){
            IndexModuleGood indexModuleGood1 = indexModuleGoodService.get(Conds.get().eq("module_id", indexModule.getId()).eq("good_sku_id", indexModuleGood.getGoodSkuId()));
            if(indexModuleGood1!=null){
                indexModuleGood.setSort(indexModuleGood1.getSort());
            }
            list.add(indexModuleGood);
        }
        indexModule.setLastUpdated(new Date());
        indexModuleService.update(indexModule);
        indexModuleGoodService.delete(Conds.get().eq("module_id",indexModule.getId()));
        for (IndexModuleGood indexModuleGood:list){
            indexModuleGood.setId(IdGenerator.getDefault().nextId() + "");
            indexModuleGood.setModuleId(indexModule.getId());
            indexModuleGood.setIsValid("T");
            indexModuleGood.setDelFlag("F");
            indexModuleGood.setDateCreated(new Date());
            indexModuleGoodService.insert(indexModuleGood);
        }
    }

    /**
     * 楼层列表数据
     * @param pg
     * @return
     */
    @Override
    public ResultList<IndexFloorVo> getIndexFloorPageList(Paging pg) {
        String sql = FreeMarkerHelper.getValueFromTpl("sql/index/queryIndexFloorKeyword.sql", null);
        RowMapper<IndexFloorVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(IndexFloorVo.class);
        List<IndexFloorVo> list = jdbcTemplate.query(sql + " limit ?,? ", rowMapper, pg.getPs(), pg.getPn());
        Number total = jdbcTemplate.queryForObject("select count(0) from (" + sql +") nums ",null,Integer.class);
        ResultList<IndexFloorVo> resultList = new ResultList<IndexFloorVo>();
        resultList.setResultList(list);
        resultList.setTotalRecords(total!=null?total.intValue():0);
        return resultList;
    }

    /**
     * 新增或编辑楼层管理
     * @param indexModule
     */
    @Override
    public void addOrUpdateIndexFloor(IndexModule indexModule) {
        String id = "";
        if(indexModule.getModuleType().equals("floor")){
            indexModule.setModuleType(IndexModuleType.floor.toString());
        }
        if(indexModule.getId().equals("")||indexModule.getId()==null){
            id = IdGenerator.getDefault().nextId() + "";
            indexModule.setId(id);
            indexModule.setDelFlag("F");
            indexModule.setIsValid("T");
            indexModule.setDateCreated(new Date());
            indexModule.setLastUpdated(new Date());
            indexModuleService.insert(indexModule);
        }else{
            id = indexModule.getId();
            indexModule.setLastUpdated(new Date());
            indexModuleService.update(indexModule);
        }
        indexModuleGoodService.delete(Conds.get().eq("module_id",id));
        List<IndexModuleGood> list = indexModule.getIndexModuleGoodList();
        for (IndexModuleGood indexModuleGood:list){
            indexModuleGood.setId(IdGenerator.getDefault().nextId() + "");
            indexModuleGood.setModuleId(id);
            indexModuleGood.setIsValid("T");
            indexModuleGood.setDelFlag("F");
            indexModuleGood.setDateCreated(new Date());
            indexModuleGoodService.insert(indexModuleGood);
        }
        indexFloorKeywordService.delete(Conds.get().eq("floor_id", id));
        List<IndexFloorKeyword> lists = indexModule.getFloorKeywords();
        for (IndexFloorKeyword indexFloorKeyword:lists){
            indexFloorKeyword.setId(IdGenerator.getDefault().nextId() + "");
            indexFloorKeyword.setFloorId(id);
            indexFloorKeyword.setIsValid("T");
            indexFloorKeyword.setDelFlag("F");
            indexFloorKeyword.setDateCreated(new Date());
            indexFloorKeywordService.insert(indexFloorKeyword);
        }
    }

    @Override
    public IndexModule getIndexFloorData(String id) {
        IndexModule indexModule = indexModuleService.get(Conds.get().eq("id", id));
        List<IndexModuleGood> list = indexModuleGoodService.list(Conds.get().eq("module_id",id));
        indexModule.setIndexModuleGoodList(list);
        List<IndexFloorKeyword> list1 = indexFloorKeywordService.list(Conds.get().eq("floor_id", id));
        indexModule.setFloorKeywords(list1);
        return indexModule;
    }

    @Override
    public List<SysDict> getNoticeType() {
        List<SysDict> sysDictList = sysDictService.list(Conds.get().eq("dict_type","notice"));
        return sysDictList;
    }

    public <T> ResultList fangfa(RowMapper<T> rowMapper, String sqlArea,Map<String,Object> param,Paging pg){
        String sql = FreeMarkerHelper.getValueFromTpl(sqlArea, param);
        List list = jdbcTemplate.query(sql+" limit ?,? ", rowMapper, pg.getPs(), pg.getPn());
        Number total = jdbcTemplate.queryForObject("select count(0) from (" + sql +") nums ", null, Integer.class);
        ResultList resultList = new ResultList();
        resultList.setResultList(list);
        resultList.setTotalPages(total!=null?total.intValue():0);
        return resultList;
    }
}
