package pub.makers.shop.base.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.base.entity.Region;

@Repository
public class RegionDao extends BaseCRUDDaoImpl<Region, String> {
	
	@Override
	protected String getTableName() {
		
		return "region";
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
