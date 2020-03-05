package pub.makers.shop.promotion.dao;

import org.springframework.stereotype.Repository;

import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.promotion.entity.RuleDef;

@Repository
public class RuleDefDao extends BaseCRUDDaoImpl<RuleDef, String> {
	
	@Override
	protected String getTableName() {
		
		return "sp_rule_def";
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
