package pub.makers.shop.store.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.store.entity.VtwoStoreRole;

@Repository
public class VtwoStoreRoleDao extends BaseCRUDDaoImpl<VtwoStoreRole, String> {
	
	@Override
	protected String getTableName() {
		
		return "vtwo_store_role";
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
