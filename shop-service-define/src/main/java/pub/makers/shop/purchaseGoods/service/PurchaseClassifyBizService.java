package pub.makers.shop.purchaseGoods.service;

import pub.makers.shop.purchaseGoods.entity.PurchaseClassify;
import pub.makers.shop.purchaseGoods.vo.PurchaseClassifyAttrVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseClassifyVo;

import java.util.List;
import java.util.Set;

/**
 * Created by kok on 2017/6/1.
 */
public interface PurchaseClassifyBizService {
    /**
     * 根据父id查询分类
     */
    List<PurchaseClassifyVo> findByParentId(String parentId, Integer status, String storeLevel);

    /**
     * 首页分类列表
     */
    List<PurchaseClassifyVo> indexClassifyList(String storeLevel);

    /**
     * 分类id查询类别列表
     */
    List<PurchaseClassifyAttrVo> findAttrByClassifyId(List<String> classifyIdList);

    /**
     * 查询所有子分类id
     */
    Set<String> findAllIdByParentId(Set<String> parentIdList, String storeLevel);

    /**
     * 查找所有父分类
     */
    List<PurchaseClassifyVo> findAllAndParent(List<String> classifyIdList, String storeLevel);

    /**
     * 查找所有子分类
     */
    List<PurchaseClassifyVo> findAllByParentId(List<String> classifyIdList, String storeLevel);

    /**
     * 禁用分类列表
     */
    List<PurchaseClassify> getDisableClassifyList();
}
