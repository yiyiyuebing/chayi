package pub.makers.shop.purchaseGoods.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSku;
import pub.makers.shop.purchaseGoods.vo.NewPurchaseGoodsVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsVo;
import pub.makers.shop.purchaseGoods.vo.StockVo;
import pub.makers.shop.tradeGoods.vo.GoodsEvaluationVo;

import java.util.List;
import java.util.Map;

/**
 * Created by daiwenfa on 2017/5/25.
 */
public interface PurchaseGoodMgrBizService {
    ResultList<NewPurchaseGoodsVo> getListPage(NewPurchaseGoodsVo newPurchaseGoods, Paging pg);

    /**
     * 保存采购商品信息
     * @param purchaseGoodsVo
     * @param userId
     * @return
     */
    Map<String,Object> savePurchaseGoods(PurchaseGoodsVo purchaseGoodsVo, long userId);

    /**
     * 获取分类属性
     * @param classifyId
     * @return
     */
    List<Map<String, Object>> purClassifyAttrList(String classifyId);

    /**
     * 获取进货商品详情
     * @param id
     * @return
     */
    PurchaseGoodsVo getPurchaseGoodsInfo(String id);

    /**
     * 获取商品列表数据
     * @param purchaseGoodsVo
     * @param pg
     * @return
     */
    ResultList<PurchaseGoodsVo> getPurchaseGoodsList(PurchaseGoodsVo purchaseGoodsVo, Paging pg);

    StockVo selectTradeGoodSkuById(String id);

    void deleteGoods(String id);

    List<GoodsEvaluationVo> getEvaluation(String purchaseGoodsSkuId);

    boolean deleteEvaluationSer(String id);

    void saveEvaluation(GoodsEvaluationVo v, String[] array, long userId);

    boolean editSort(String purchaseGoodsId, String sort);

    Boolean timingUpGoodSkuStatus(PurchaseGoodsSku purchaseGoodsSku, long userId);

    StockVo getTimeingData(String id);

    List<String> getParentClassify(String idStr);
}
