package pub.makers.shop.promotion.dao;

import org.springframework.stereotype.Repository;

import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.promotion.entity.RuleInt;

@Repository
public class RuleIntDao extends BaseCRUDDaoImpl<RuleInt, String> {
	
	@Override
	protected String getTableName() {
		
		return "sp_rule_int";
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
