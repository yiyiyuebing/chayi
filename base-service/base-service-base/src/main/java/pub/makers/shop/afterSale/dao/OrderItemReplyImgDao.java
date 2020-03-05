package pub.makers.shop.afterSale.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.afterSale.entity.OrderItemReplyImg;

/**
 * Created by dy on 2017/5/16.
 */
@Repository
public class OrderItemReplyImgDao extends BaseCRUDDaoImpl<OrderItemReplyImg, String> {

    @Override
    protected String getTableName() {
        return "order_item_reply_imgs";
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
