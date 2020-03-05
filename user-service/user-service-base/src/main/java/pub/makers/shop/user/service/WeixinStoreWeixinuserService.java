package pub.makers.shop.user.service;


import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.user.entity.WeixinStoreWeixinuser;

public interface WeixinStoreWeixinuserService extends BaseCRUDService<WeixinStoreWeixinuser>{
	
	void addStoreUser(Long storeId, Long weixinUserId, Long id);
}
