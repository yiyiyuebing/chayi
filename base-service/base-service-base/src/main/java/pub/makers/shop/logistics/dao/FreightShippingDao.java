package pub.makers.shop.logistics.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.logistics.entity.FreightShipping;

@Repository
public class FreightShippingDao extends BaseCRUDDaoImpl<FreightShipping, String> {
	
	@Override
	protected String getTableName() {
		
		return "pd_freight_shipping";
	}
	
	@Override
	protected String getKeyName() {
		
		return "shipping_id";
	}

	@Override
	protected boolean autoGenerateKey() {
		
		return false;
	}
}
