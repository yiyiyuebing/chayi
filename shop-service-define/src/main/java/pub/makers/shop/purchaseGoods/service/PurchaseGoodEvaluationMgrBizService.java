package pub.makers.shop.purchaseGoods.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodEvaluationManageVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodEvaluationParams;

import java.util.Map;

public interface PurchaseGoodEvaluationMgrBizService {

    /**
     * 根据参数查询相应的评价
     * @param param
     * @param pg
     * @return
     */
    ResultList<PurchaseGoodEvaluationManageVo> listByCondition(PurchaseGoodEvaluationParams param, Paging pg);

    /**
     * 根据参数更新数据库的is_hide字段
     * @param param
     * @param pg
     * @return
     */
    Map<String, Object> updateEvaluationIsHide(PurchaseGoodEvaluationParams param, Paging pg);

    /**
     * 根据参数更新数据库的is_replied字段并增加（给用户评价的）回复评论
     * @param param
     * @param map
     * @return
     */
    Map<String, Object> updateEvaluationIsReplied(PurchaseGoodEvaluationParams param, Map<String, Object> map);

    /**
     * 得到图片
     * @param ids
     * @return
     */
    Map<String, Object> getImageById (String ids);

}
