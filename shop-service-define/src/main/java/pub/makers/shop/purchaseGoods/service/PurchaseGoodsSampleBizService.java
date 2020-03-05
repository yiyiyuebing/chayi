package pub.makers.shop.purchaseGoods.service;

import java.util.Map;
import java.util.Set;

import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSample;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsSampleVo;

/**
 * Created by dy on 2017/6/24.
 */
public interface PurchaseGoodsSampleBizService {

    /**
     * 商品
     * @param goodsId
     * @return
     */
    PurchaseGoodsSample getPurGoodsSampleByGoodsId(String goodsId);
    
    
    /**
     * 根据skuid查询样品数据
     * @param skuIds
     * @return
     */
    Map<String, PurchaseGoodsSampleVo> findBySkus(Set<String> skuIds);

}
