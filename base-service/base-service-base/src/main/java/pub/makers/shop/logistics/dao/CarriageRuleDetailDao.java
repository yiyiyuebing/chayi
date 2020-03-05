package pub.makers.shop.logistics.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.logistics.entity.CarriageRuleDetail;

@Repository
public class CarriageRuleDetailDao extends BaseCRUDDaoImpl<CarriageRuleDetail, String> {
	
	@Override
	protected String getTableName() {
		
		return "carriage_rule_detail";
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
