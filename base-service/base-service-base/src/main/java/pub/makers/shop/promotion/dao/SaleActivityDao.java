package pub.makers.shop.promotion.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.promotion.entity.SaleActivity;

@Repository
public class SaleActivityDao extends BaseCRUDDaoImpl<SaleActivity, String> {
	
	@Override
	protected String getTableName() {
		
		return "sp_sale_activity";
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
