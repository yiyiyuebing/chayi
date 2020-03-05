package pub.makers.shop.promotion.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.promotion.entity.ManzengActivity;

/**
 * Created by dy on 2017/8/17.
 */
@Repository
public class ManzengActivityDao extends BaseCRUDDaoImpl<ManzengActivity, String> {
    @Override
    protected String getTableName() {

        return "sp_manzeng_activity";
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
