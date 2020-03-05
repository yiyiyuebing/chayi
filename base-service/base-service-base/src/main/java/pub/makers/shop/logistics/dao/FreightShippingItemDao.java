package pub.makers.shop.logistics.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.logistics.entity.FreightShippingItem;

@Repository
public class FreightShippingItemDao extends BaseCRUDDaoImpl<FreightShippingItem, String> {
	
	@Override
	protected String getTableName() {
		
		return "pd_freight_shipping_item";
	}
	
	@Override
	protected String getKeyName() {
		
		return "item_id";
	}

	@Override
	protected boolean autoGenerateKey() {
		
		return false;
	}
}
