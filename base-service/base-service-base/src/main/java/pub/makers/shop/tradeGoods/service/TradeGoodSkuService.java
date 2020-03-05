package pub.makers.shop.tradeGoods.service;


import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.tradeGoods.entity.TradeGoodSku;

public interface TradeGoodSkuService extends BaseCRUDService<TradeGoodSku>{
	
	/**
	 * 更新销售数量
	 * @param skuId
	 * @param num
	 */
	void updateSaleNum(String skuId, int num);
	
	/**
	 * 查询剩余库存
	 * @param skuId
	 */
	int getOnSaleNum(String skuId);
}
