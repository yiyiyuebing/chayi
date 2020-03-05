package pub.makers.shop.logistics.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.logistics.entity.FreightTplGoodRel;

@Repository
public class FreightTplGoodRelDao extends BaseCRUDDaoImpl<FreightTplGoodRel, String> {
	
	@Override
	protected String getTableName() {
		
		return "pd_freight_tpl_good_rel";
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
