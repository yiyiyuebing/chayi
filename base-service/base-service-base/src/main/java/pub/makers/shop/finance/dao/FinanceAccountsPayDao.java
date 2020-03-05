package pub.makers.shop.finance.dao;

import org.springframework.stereotype.Repository;

import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.finance.entity.FinanceAccountsPay;

@Repository
public class FinanceAccountsPayDao extends BaseCRUDDaoImpl<FinanceAccountsPay, String> {
	
	@Override
	protected String getTableName() {
		
		return "finance_accountspay";
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
