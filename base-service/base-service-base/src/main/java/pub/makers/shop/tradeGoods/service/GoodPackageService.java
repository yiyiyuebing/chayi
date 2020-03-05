package pub.makers.shop.tradeGoods.service;


import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.tradeGoods.entity.GoodPackage;

public interface GoodPackageService extends BaseCRUDService<GoodPackage>{
	
	void updateSaleNum(String boomId, int num);
	
	GoodPackage getByBoomId(String boomId);
}
