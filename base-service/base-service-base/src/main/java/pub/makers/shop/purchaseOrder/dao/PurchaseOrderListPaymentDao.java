package pub.makers.shop.purchaseOrder.dao;

import org.springframework.stereotype.Repository;

import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.baseOrder.entity.OrderListPayment;

@Repository
public class PurchaseOrderListPaymentDao extends BaseCRUDDaoImpl<OrderListPayment, String> {
	
	@Override
	protected String getTableName() {
		
		return "purchase_order_list_payment";
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
