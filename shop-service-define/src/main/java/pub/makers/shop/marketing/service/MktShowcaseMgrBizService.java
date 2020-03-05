package pub.makers.shop.marketing.service;

import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.marketing.entity.MktShowcase;
import pub.makers.shop.marketing.entity.MktShowcaseGood;
import pub.makers.shop.purchaseGoods.vo.PurchaseClassifyVo;

import java.util.List;

/**
 * Created by dy on 2017/5/29.
 */
public interface MktShowcaseMgrBizService {
    /**
     * 获取列表
     * @param mktShowcase
     * @param pg
     * @return
     */
    ResultList<MktShowcase> mktShowcaseList(MktShowcase mktShowcase, Paging pg);

    /**
     * 获取橱窗模版字典数据
     * @return
     */
    List<SysDict> mktShowcaseTypeList();

    /**
     * 保存模版
     * @param mktShowcase
     */
    void saveMktShowcase(MktShowcase mktShowcase);

    /**
     * 保存模版关联商品
     * @param showcaseId
     * @param goodsIds
     */
    void saveMktShowcaseGoods(String showcaseId, String goodsIds);

    /**
     * 更新启用禁用状态
     * @param ids
     * @param isValid
     */
    void editMktShowcaseValid(String ids, String isValid);

    /**
     * 删除模版
     * @param showcase
     */
    void delMktShowcase(String showcase);

    /**
     * 获取关联商品
     * @param showcaseId
     * @return
     */
    List<MktShowcaseGood> mktShowcaseGoodsList(String showcaseId);

}
