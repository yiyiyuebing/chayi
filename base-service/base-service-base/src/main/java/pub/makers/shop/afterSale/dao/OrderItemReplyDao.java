package pub.makers.shop.afterSale.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.afterSale.entity.OrderItemReply;

/**
 * Created by dy on 2017/5/17.
 */
@Repository
public class OrderItemReplyDao extends BaseCRUDDaoImpl<OrderItemReply, String> {

    @Override
    protected String getTableName() {
        return "order_item_reply";
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
