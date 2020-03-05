package pub.makers.shop.purchaseGoods.service;

import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSample;

import java.util.List;

/**
 * Created by dy on 2017/4/14.
 */
public interface PurchaseGoodsSampleService extends BaseCRUDService<PurchaseGoodsSample> {
    List<PurchaseGoodsSample> selectByPurGoodsId(PurchaseGoodsSample purchaseGoodsSample);
}

