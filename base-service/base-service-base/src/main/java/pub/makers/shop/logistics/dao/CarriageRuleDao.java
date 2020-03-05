package pub.makers.shop.logistics.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.logistics.entity.CarriageRule;

@Repository
public class CarriageRuleDao extends BaseCRUDDaoImpl<CarriageRule, String> {
	
	@Override
	protected String getTableName() {
		
		return "carriage_rule";
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
