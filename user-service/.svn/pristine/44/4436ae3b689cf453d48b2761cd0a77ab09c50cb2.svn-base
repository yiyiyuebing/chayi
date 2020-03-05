package pub.makers.shop.store.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.store.entity.StoreLabel;

/**
 * Created by dy on 2017/10/8.
 */
@Repository
public class StoreLabelDao extends BaseCRUDDaoImpl<StoreLabel, String> {

    @Override
    protected String getTableName() {

        return "store_label";
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
