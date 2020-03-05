package pub.makers.shop.afterSale.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.afterSale.entity.OrderItemAsFlowDetail;

/**
 * Created by kok on 2017/5/13.
 */
@Repository
public class OrderItemAsFlowDetailDao extends BaseCRUDDaoImpl<OrderItemAsFlowDetail, String> {
    @Override
    protected String getTableName() {
        return "order_item_as_flow_detail";
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
