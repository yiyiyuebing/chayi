package pub.makers.shop.logistics.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.logistics.entity.FreightPinkage;

@Repository
public class FreightPinkageDao extends BaseCRUDDaoImpl<FreightPinkage, String> {
	
	@Override
	protected String getTableName() {
		
		return "pd_freight_pinkage";
	}
	
	@Override
	protected String getKeyName() {
		
		return "pinkage_id";
	}

	@Override
	protected boolean autoGenerateKey() {
		
		return false;
	}
}
