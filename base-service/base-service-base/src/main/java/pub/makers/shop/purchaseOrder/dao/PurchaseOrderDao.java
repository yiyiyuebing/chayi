package pub.makers.shop.purchaseOrder.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrder;

/**
 * Created by dy on 2017/4/10.
 */
@Repository
public class PurchaseOrderDao extends BaseCRUDDaoImpl<PurchaseOrder, String> {
    @Override
    protected String getTableName() {
        return "purchase_order";
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
