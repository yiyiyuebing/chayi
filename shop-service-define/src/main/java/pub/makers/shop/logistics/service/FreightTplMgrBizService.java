package pub.makers.shop.logistics.service;

import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.logistics.entity.FreightTpl;
import pub.makers.shop.logistics.entity.FreightTplGoodRel;
import pub.makers.shop.logistics.entity.FreightTplRel;
import pub.makers.shop.logistics.vo.FreightTplParams;
import pub.makers.shop.logistics.vo.FreightTplRelParams;
import pub.makers.shop.purchaseGoods.vo.ClassifyVo;

import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/4/18.
 */
public interface FreightTplMgrBizService {

    /**
     * 获取运费模版列表
     * @param freightTplParams {name:运费模版名称, freightMethodId:运送方式}
     * @param paging
     * @return
     */
    ResultList<Map<String, Object>> listFreightTplByParams(FreightTplParams freightTplParams, Paging paging);

    /**
     * 保存更新
     * @param freightTpl
     */
    void saveOrUpdate(FreightTpl freightTpl);

    /**
     * 从字典中获取运费字典数据
     * @param parentName
     * @return
     */
    List<SysDict> listFreightDictByParentName(String parentName);

    /**
     * 查看运费模版有没有被关联
     * @param idStr
     * @return
     */
    Map<String,Object> checkFreightTplRel(String idStr);

    /**
     * 批量删除运费模版
     * @param idStr
     * @return
     */
    Map<String, Object> delBatchFreightTpls(String idStr);

    /**
     * 根据父级id和订单类型查找分类树
     * @param orderType
     * @param parentId
     * @return
     */
    ResultList<Map<String, Object>> getClassfyTrees(String orderType, String parentId);

    /**
     * 根据父级id和订单类型查找分类树
     * @param orderType
     * @param parentId
     * @return
     */
    ResultList<ClassifyVo> queryClassfyTrees(String orderType, String parentId);

    /**
     * 保存或更新商品或货品分类
     * @param freightTplRel
     */
    void saveOrUpdateFreightRel(FreightTplRel freightTplRel);

    /**
     * 获取商品分类运费模版列表关联
     * @param freightTplRelParams
     * @param pg
     * @return
     */
    ResultList<Map<String, Object>> listFreightRelByParams(FreightTplRelParams freightTplRelParams, Paging pg);

    /**
     * 批量删除商品分类运费模版关联
     * @param relIds
     * @return
     */
    Map<String, Object> delBatchFreightTplRels(String relIds);

    /**
     * 获取商品分类运费模版关联详情
     * @param relId
     * @return
     */
    FreightTplRel freightTplRelDetail(String relId);

    /**
     * 保存或更新单品与运费模版关联
     * @param freightTplGoodRel
     */
    void saveOrUpdateFreightGoodRel(FreightTplGoodRel freightTplGoodRel);

    /**
     * 通过tradeGoodId获取关联的运费模版
     * @param tradeGoodId
     * @return
     */
    List<FreightTplGoodRel> listFreightTplGoodRelByGoodId(String tradeGoodId, String relType);

    /**
     * 通过goodSkuId删除运费模版关联
     * @param goodSkuId
     * @param orderType
     */
    void delFreightTplGoodRelByGoodSkuId(String goodSkuId, String orderType);

    /**
     * 获取所有分类记录
     * @param orderType
     * @return
     */
    ResultList<ClassifyVo> getAllClassfyList(String orderType);
}
