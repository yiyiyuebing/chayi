package pub.makers.shop.store.service.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.store.entity.SalesReturnReason;

/**
 * Created by dy on 2017/4/15.
 */
@Repository
public class SalesReturnReasonDao extends BaseCRUDDaoImpl<SalesReturnReason, String> {

    @Override
    protected String getTableName() {
        return "sales_return_reason";
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
