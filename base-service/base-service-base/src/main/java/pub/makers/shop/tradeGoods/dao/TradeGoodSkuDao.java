package pub.makers.shop.tradeGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.tradeGoods.entity.TradeGoodSku;

@Repository
public class TradeGoodSkuDao extends BaseCRUDDaoImpl<TradeGoodSku, String> {
	
	@Override
	protected String getTableName() {
		
		return "trade_good_sku";
	}
	
	@Override
	protected String getKeyName() {
		
		return "id";
	}

	@Override
	protected boolean autoGenerateKey() {
		
		return false;
	}
}
