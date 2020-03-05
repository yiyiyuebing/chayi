package pub.makers.shop.tradeGoods.service;


import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.tradeGoods.entity.TradeGood;

public interface TradeGoodService extends BaseCRUDService<TradeGood>{
	
	/**
	 * 更新总销售数量
	 * @param num
	 */
	TradeGood updateSaleNum(Long goodId, int num);
	
	String queryGoodImageByCargoId(Long cargoId);
	
	TradeGood getBySkuId(String skuId);
}
