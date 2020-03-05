package pub.makers.shop.bill.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.bill.entity.OrderBillRecord;

/**
 * Created by dy on 2017/9/6.
 */
@Repository
public class OrderBillRecordDao extends BaseCRUDDaoImpl<OrderBillRecord, String> {

    @Override
    protected String getTableName() {

        return "order_bill_record";
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
