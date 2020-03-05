package pub.makers.shop.purchaseGoods.service;

import pub.makers.shop.purchaseGoods.pojo.PurchaseGoodsEvaluationQuery;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsEvaluationCountVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsEvaluationVo;

import java.util.List;

/**
 * Created by kok on 2017/6/1.
 */
public interface PurchaseGoodsEvaluationBizService {
    /**
     * 添加评论
     */
    void addEvaluation(PurchaseGoodsEvaluationVo purchaseGoodsEvaluationVo);

    /**
     * 评论详情
     */
    PurchaseGoodsEvaluationVo getEvaluation(String id);

    /**
     * 查询评价列表
     */
    List<PurchaseGoodsEvaluationVo> getEvaluationList(PurchaseGoodsEvaluationQuery query);

    /**
     * 查询评价列表数
     */
    PurchaseGoodsEvaluationCountVo getEvaluationCount(PurchaseGoodsEvaluationQuery query);
}
