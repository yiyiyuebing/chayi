package pub.makers.shop.cart.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cart.entity.Cart;

@Repository
public class CartDao extends BaseCRUDDaoImpl<Cart, String> {
	
	@Override
	protected String getTableName() {
		
		return "shopping_cart";
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
