package pub.makers.shop.user.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.store.entity.GoodReceiptAddr;

/**
 * Created by daiwenfa on 2017/7/21.
 */
@Repository
public class GoodReceiptAddrDao extends BaseCRUDDaoImpl<GoodReceiptAddr, String> {
    @Override
    protected String getTableName() {

        return "good_receipt_addr";
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
