package pub.makers.shop.bill.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.bill.entity.TimeCycle;

/**
 * Created by dy on 2017/9/6.
 */
@Repository
public class TimeCycleDao extends BaseCRUDDaoImpl<TimeCycle, String> {

    @Override
    protected String getTableName() {

        return "time_cycle";
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
