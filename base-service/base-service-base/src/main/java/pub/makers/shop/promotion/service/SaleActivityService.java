package pub.makers.shop.promotion.service;


import java.util.List;
import java.util.Set;

import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.promotion.entity.SaleActivity;
import pub.makers.shop.promotion.entity.SaleActivityGood;

public interface SaleActivityService extends BaseCRUDService<SaleActivity>{
	
	/**
	 * 根据输入的SKUID过滤出适合的限时打折商品信息
	 * @param goodSkuIdSet
	 * @return
	 */
	List<SaleActivityGood> filterAvailableSaleGood(Set<String> goodSkuIdSet);
	
}
