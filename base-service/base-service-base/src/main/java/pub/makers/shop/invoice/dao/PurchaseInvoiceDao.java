package pub.makers.shop.invoice.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.invoice.entity.Invoice;

/**
 * Created by kok on 2017/9/4.
 */
@Repository
public class PurchaseInvoiceDao extends BaseCRUDDaoImpl<Invoice, String> {
    @Override
    protected String getTableName() {

        return "purchase_invoice";
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
