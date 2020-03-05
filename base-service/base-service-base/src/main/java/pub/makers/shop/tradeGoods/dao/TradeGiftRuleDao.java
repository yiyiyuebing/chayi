package pub.makers.shop.tradeGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.tradeGoods.entity.TradeGiftRule;

@Repository
public class TradeGiftRuleDao extends BaseCRUDDaoImpl<TradeGiftRule, String> {
	
	@Override
	protected String getTableName() {
		
		return "trade_gift_rule";
	}
	
	@Override
	protected String getKeyName() {
		
		return "rule_id";
	}

	@Override
	protected boolean autoGenerateKey() {
		
		return false;
	}
}
