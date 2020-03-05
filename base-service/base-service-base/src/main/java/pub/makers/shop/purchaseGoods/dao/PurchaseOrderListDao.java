package pub.makers.shop.purchaseGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrderList;

/**
 * Created by dy on 2017/4/14.
 */
@Repository
public class PurchaseOrderListDao extends BaseCRUDDaoImpl<PurchaseOrderList, String> {

    @Override
    protected String getTableName() {

        return "purchase_order_list";
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
