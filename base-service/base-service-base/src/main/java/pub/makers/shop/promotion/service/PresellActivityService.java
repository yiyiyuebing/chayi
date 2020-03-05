package pub.makers.shop.promotion.service;


import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.promotion.entity.PresellActivity;
import pub.makers.shop.promotion.entity.PresellGood;

public interface PresellActivityService extends BaseCRUDService<PresellActivity>{
	
	PresellGood getValidGoodBySkuid(String skuId);
	
	PresellActivity getValidActivityBySkuid(String skuId);
}
