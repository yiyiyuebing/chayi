package pub.makers.shop.purchaseGoods.service;


import java.util.Map;
import java.util.Set;

import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoods;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsVo;

public interface PurchaseGoodsService extends BaseCRUDService<PurchaseGoods>{
	
	PurchaseGoods getByMaterialSkuId(String skuId);
	
	PurchaseGoods getByPurGoodsSkuId(String purGoodsSkuId);

	String queryGoodImageByCargoId(Long cargoId);
	
	Map<String, PurchaseGoodsVo> listBySkuId(Set<String> skuIds);
}
