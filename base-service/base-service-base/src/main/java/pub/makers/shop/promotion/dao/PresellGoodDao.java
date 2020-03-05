package pub.makers.shop.promotion.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.promotion.entity.PresellGood;

@Repository
public class PresellGoodDao extends BaseCRUDDaoImpl<PresellGood, String> {
	
	@Override
	protected String getTableName() {
		
		return "sp_presell_good";
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
