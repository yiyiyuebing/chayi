package pub.makers.shop.tradeGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.tradeGoods.entity.TradeGood;

@Repository
public class TradeGoodDao extends BaseCRUDDaoImpl<TradeGood, String> {
	
	@Override
	protected String getTableName() {
		
		return "trade_good";
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
