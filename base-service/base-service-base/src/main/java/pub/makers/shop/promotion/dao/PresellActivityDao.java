package pub.makers.shop.promotion.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.promotion.entity.PresellActivity;

@Repository
public class PresellActivityDao extends BaseCRUDDaoImpl<PresellActivity, String> {
	
	@Override
	protected String getTableName() {
		
		return "sp_presell_activity";
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
