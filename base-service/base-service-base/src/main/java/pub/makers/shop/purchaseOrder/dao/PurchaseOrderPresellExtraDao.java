package pub.makers.shop.purchaseOrder.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.baseOrder.entity.OrderPresellExtra;

@Repository
public class PurchaseOrderPresellExtraDao extends BaseCRUDDaoImpl<OrderPresellExtra, String> {
	
	@Override
	protected String getTableName() {
		
		return "purchase_order_presell_extra";
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
