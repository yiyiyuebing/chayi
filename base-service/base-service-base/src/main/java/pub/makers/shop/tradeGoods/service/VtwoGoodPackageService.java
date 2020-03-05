package pub.makers.shop.tradeGoods.service;

import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.store.entity.VtwoGoodPackage;

/**
 * Created by dy on 2017/4/14.
 */
public interface VtwoGoodPackageService extends BaseCRUDService<VtwoGoodPackage> {
    VtwoGoodPackage selectByBomId(String tradeGoodSkuId);
}
