package pub.makers.shop.store.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.store.entity.StoreLevel;

/**
 * Created by dy on 2017/4/14.
 */
@Repository
public class StoreLevelDao extends BaseCRUDDaoImpl<StoreLevel, String>{

    @Override
    protected String getTableName() {
        return "store_level";
    }

    @Override
    protected String getKeyName() {

        return "level_Id";
    }

    @Override
    protected boolean autoGenerateKey() {

        return false;
    }
}
