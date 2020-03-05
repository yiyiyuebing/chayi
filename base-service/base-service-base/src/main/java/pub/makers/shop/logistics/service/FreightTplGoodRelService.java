package pub.makers.shop.logistics.service;


import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.logistics.entity.FreightTplGoodRel;

public interface FreightTplGoodRelService extends BaseCRUDService<FreightTplGoodRel>{
	
	FreightTplGoodRel getRelBySkuIdAndType(String skuId, String relType);
}
