package pub.makers.shop.finance.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.finance.entity.FinanceAccountspayItem;

/**
 * Created by dy on 2017/4/14.
 */
@Repository
public class FinanceAccountspayItemDao extends BaseCRUDDaoImpl<FinanceAccountspayItem, String> {
    @Override
    protected String getTableName() {

        return "finance_accountspay_item";
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
