package pub.makers.shop.promotion.service;

import pub.makers.shop.promotion.entity.ManzengActivity;
import pub.makers.shop.promotion.entity.ManzengActivityApply;
import pub.makers.shop.promotion.entity.ManzengGood;
import pub.makers.shop.promotion.entity.ManzengRule;
import pub.makers.shop.promotion.pojo.ManzengQuery;
import pub.makers.shop.promotion.vo.ManzengActivityVo;
import pub.makers.shop.promotion.vo.ManzengParam;

import java.util.List;

/**
 * Created by dy on 2017/8/22.
 */
public interface ManzengBizService {
    /**
     * 满减满赠活动列表
     */
    List<ManzengActivityVo> getActivityList(ManzengQuery query);

    /**
     * 保存活动
     * @param manzengActivity
     */
    ManzengActivity save(ManzengActivity manzengActivity);

    /**
     * 保存活动规则
     * @param manzengRule
     */
    void saveManzengRule(ManzengRule manzengRule);


    ManzengRule getManzengRuleById(String id);

    ManzengActivity getManzengActivityById(String id);

    /**
     * 查询满赠活动商品信息
     * @param manzengParam
     * @return
     */
    List<ManzengGood> getManzengGoodListByRuleIds(ManzengParam manzengParam);

    /**
     * 保存满减活动应用范围
     * @param manzengActivityApply
     */
    void saveManzengApply(ManzengActivityApply manzengActivityApply);

    /**
     * 保存赠品
     * @param manzengGood
     */
    void saveManzengGood(ManzengGood manzengGood);
}
