package pub.makers.shop.logistics.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.logistics.entity.FreightTpl;

@Repository
public class FreightTplDao extends BaseCRUDDaoImpl<FreightTpl, String> {
	
	@Override
	protected String getTableName() {
		
		return "pd_freight_tpl";
	}
	
	@Override
	protected String getKeyName() {
		
		return "tpl_id";
	}

	@Override
	protected boolean autoGenerateKey() {
		
		return false;
	}
}
