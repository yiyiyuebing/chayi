package pub.makers.shop.store.service;


import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.store.entity.BankCard;

public interface BankCardService extends BaseCRUDService<BankCard>{
	
	BankCard getByStoreId(String storeId);
}
