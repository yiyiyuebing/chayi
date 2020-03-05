package pub.makers.shop.tradeGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.tradeGoods.entity.TradeGoodExtra;

@Repository
public class TradeGoodExtraDao extends BaseCRUDDaoImpl<TradeGoodExtra, String> {
	
	@Override
	protected String getTableName() {
		
		return "trade_good_extra";
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
