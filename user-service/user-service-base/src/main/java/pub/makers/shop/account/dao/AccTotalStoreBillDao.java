package pub.makers.shop.account.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.account.entity.AccTotalStoreBill;

/**
 * Created by kok on 2017/6/2.
 */
@Repository
public class AccTotalStoreBillDao extends BaseCRUDDaoImpl<AccTotalStoreBill, String> {
    @Override
    protected String getTableName() {
        return "acc_total_store_bill";
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
