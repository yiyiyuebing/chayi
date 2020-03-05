package pub.makers.shop.bill.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.bill.entity.MqTaskInfo;

/**
 * Created by dy on 2017/9/8.
 */
@Repository
public class MqTaskInfoDao extends BaseCRUDDaoImpl<MqTaskInfo, String> {
    @Override
    protected String getTableName() {

        return "mq_task_info";
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
