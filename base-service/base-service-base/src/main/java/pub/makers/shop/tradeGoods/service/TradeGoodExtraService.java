package pub.makers.shop.tradeGoods.service;


import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.tradeGoods.entity.TradeGoodExtra;

public interface TradeGoodExtraService extends BaseCRUDService<TradeGoodExtra>{
	
	/**
	 * 根据商品ID查询商品扩展信息
	 * @param goodId
	 * @return
	 */
	TradeGoodExtra getByGoodId(String goodId);
	
	
	/**
	 * 根据商品SKUID查询商品扩展信息
	 * @param goodSkuId
	 * @return
	 */
	TradeGoodExtra getByGoodSkuId(String goodSkuId);
}
