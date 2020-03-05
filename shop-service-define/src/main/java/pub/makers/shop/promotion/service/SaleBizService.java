package pub.makers.shop.promotion.service;

import pub.makers.shop.promotion.vo.SalePromotionActivityVo;

import java.util.List;
import java.util.Set;

/**
 * Created by kok on 2017/8/21.
 */
public interface SaleBizService {
    /**
     * skuId查询打折信息
     * @param goodSkuIdSet
     * @return
     */
    List<SalePromotionActivityVo> listForGoodsSku(Set<String> goodSkuIdSet);
}
