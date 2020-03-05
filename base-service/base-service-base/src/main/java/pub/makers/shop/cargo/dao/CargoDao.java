package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.Cargo;

@Repository
public class CargoDao extends BaseCRUDDaoImpl<Cargo, String> {
	
	@Override
	protected String getTableName() {
		
		return "cargo";
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
