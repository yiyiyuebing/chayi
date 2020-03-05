package pub.makers.shop.tradeOrder.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.baseOrder.entity.OrderListPresellExtra;

@Repository
public class TradeOrderListPresellExtraDao extends BaseCRUDDaoImpl<OrderListPresellExtra, String> {
	
	@Override
	protected String getTableName() {
		
		return "trade_order_list_presell_extra";
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
