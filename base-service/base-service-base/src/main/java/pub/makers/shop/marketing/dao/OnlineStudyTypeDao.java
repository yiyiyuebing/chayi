package pub.makers.shop.marketing.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.marketing.entity.OnlineStudyType;

/**
 * Created by dy on 2017/5/3.
 */
@Repository
public class OnlineStudyTypeDao extends BaseCRUDDaoImpl<OnlineStudyType, String> {
    @Override
    protected String getTableName() {

        return "event_online_study_type";
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
