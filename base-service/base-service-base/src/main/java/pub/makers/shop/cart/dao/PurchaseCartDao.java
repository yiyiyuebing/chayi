package pub.makers.shop.cart.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cart.entity.Cart;

/**
 * Created by kok on 2017/6/12.
 */
@Repository
public class PurchaseCartDao extends BaseCRUDDaoImpl<Cart, String> {
    @Override
    protected String getTableName() {

        return "purchase_shopping_cart";
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
