package pub.makers.shop.index.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.index.entity.IndentMobileModuleClassify;

@Repository
public class IndentMobileModuleClassifyDao extends BaseCRUDDaoImpl<IndentMobileModuleClassify, String> {
	
	@Override
	protected String getTableName() {
		
		return "index_mobile_module_classify";
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
