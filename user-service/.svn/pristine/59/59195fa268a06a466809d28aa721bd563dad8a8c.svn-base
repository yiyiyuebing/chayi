package pub.makers.shop.store.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.store.entity.Subbranch;

@Repository
public class SubbranchDao extends BaseCRUDDaoImpl<Subbranch, String> {
	
	@Override
	protected String getTableName() {
		
		return "store_subbranch";
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
