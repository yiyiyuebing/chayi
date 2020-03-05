package pub.makers.shop.logistics.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.logistics.entity.FreightTplRel;

@Repository
public class FreightTplRelDao extends BaseCRUDDaoImpl<FreightTplRel, String> {
	
	@Override
	protected String getTableName() {
		
		return "pd_freight_tpl_rel";
	}
	
	@Override
	protected String getKeyName() {
		
		return "rel_id";
	}

	@Override
	protected boolean autoGenerateKey() {
		
		return false;
	}
}
