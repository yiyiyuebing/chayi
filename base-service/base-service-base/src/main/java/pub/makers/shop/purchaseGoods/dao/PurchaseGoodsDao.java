package pub.makers.shop.purchaseGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoods;

@Repository
public class PurchaseGoodsDao extends BaseCRUDDaoImpl<PurchaseGoods, String> {
	
	@Override
	protected String getTableName() {
		
		return "purchase_goods";
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
