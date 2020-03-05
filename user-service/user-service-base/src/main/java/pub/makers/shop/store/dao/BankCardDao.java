package pub.makers.shop.store.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.store.entity.BankCard;

@Repository
public class BankCardDao extends BaseCRUDDaoImpl<BankCard, String> {
	
	@Override
	protected String getTableName() {
		
		return "store_bank_card";
	}
	
	@Override
	protected String getKeyName() {
		
		return "bank_card_id";
	}

	@Override
	protected boolean autoGenerateKey() {
		
		return false;
	}
}
