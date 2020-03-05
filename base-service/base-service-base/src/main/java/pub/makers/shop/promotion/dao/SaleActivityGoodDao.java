package pub.makers.shop.promotion.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.promotion.entity.SaleActivityGood;

@Repository
public class SaleActivityGoodDao extends BaseCRUDDaoImpl<SaleActivityGood, String> {
	
	@Override
	protected String getTableName() {
		
		return "sp_sale_activity_good";
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
