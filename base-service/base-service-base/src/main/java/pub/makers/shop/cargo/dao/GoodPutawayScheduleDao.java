package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.GoodPutawaySchedule;

/**
 * Created by daiwenfa on 2017/6/1.
 */
@Repository
public class GoodPutawayScheduleDao  extends BaseCRUDDaoImpl<GoodPutawaySchedule, String> {
    @Override
    protected String getTableName() {
        return "good_putaway_schedule";
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
