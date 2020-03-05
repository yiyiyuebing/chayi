package pub.makers.shop.afterSale.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.afterSale.entity.OrderItemAsFlow;

/**
 * Created by kok on 2017/5/13.
 */
@Repository
public class OrderItemAsFlowDao extends BaseCRUDDaoImpl<OrderItemAsFlow, String> {
    @Override
    protected String getTableName() {
        return "order_item_as_flow";
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
