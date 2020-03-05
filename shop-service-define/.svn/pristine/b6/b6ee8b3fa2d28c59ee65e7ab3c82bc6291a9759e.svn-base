package pub.makers.shop.tradeGoods.service;

import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.purchaseGoods.vo.StockVo;
import pub.makers.shop.tradeGoods.entity.TradeGoodSku;
import pub.makers.shop.tradeGoods.vo.GoodsEvaluationVo;
import pub.makers.shop.tradeGoods.vo.NewTradeGoodVo;
import pub.makers.shop.tradeGoods.vo.TradeGoodVo;

import java.util.List;
import java.util.Map;

/**
 * Created by daiwenfa on 2017/5/23.
 */
public interface GoodsMgrBizService {
    ResultList<NewTradeGoodVo> newTradeGoodsPage(NewTradeGoodVo newTradeGoodVo, Paging pg);

    List<Map<String,Object>> selectTradeGoodSkuVo(String tradeGoodId);

    /**
     * 获取商品标签
     * @return
     */
    List<SysDict> getGoodsTags();

    /**
     * 保存商品标签
     * @param tagsName
     * @return
     */
    SysDict saveGoodsTags(String tagsName);

    /**
     * 保存商品信息
     * @param tradeGoodVo
     * @param userId
     * @return
     */
    Map<String,Object> saveTradeGoods(TradeGoodVo tradeGoodVo, long userId);

    /**
     * 获取商品详情
     * @param id
     * @return
     */
    TradeGoodVo getTradeGoodsInfo(String id);

    List<GoodsEvaluationVo> getEvaluation(String tradeGoodsSkuId);

    boolean deleteEvaluationSer(String id);

    void saveEvaluation(GoodsEvaluationVo v, String[] array, Long i);

    void deleteGoods(String id);

    boolean editSort(String tradeGoodId, String sort);

    Map selectTradeGoodSkuById(String id);

    boolean timingUpGoodSkuStatus(TradeGoodSku tradeGoodSku, long userId);

    List<Map<String,Object>> getGoodsLabel();

    List<String> getParentClassify(String idStr);

    StockVo getTimeingData(String id);
}
