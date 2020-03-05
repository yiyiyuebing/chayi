package pub.makers.shop.index.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.index.entity.IndentMobileModuleGood;

@Repository
public class IndentMobileModuleGoodDao extends BaseCRUDDaoImpl<IndentMobileModuleGood, String> {
	
	@Override
	protected String getTableName() {
		
		return "index_mobile_module_good";
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
