package pub.makers.shop.store.service.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.store.entity.StoreSubbranchExt;

/**
 * Created by daiwenfa on 2017/7/27.
 */
@Repository
public class SubbranchExtDao  extends BaseCRUDDaoImpl<StoreSubbranchExt, String> {
    @Override
    protected String getTableName() {

        return "store_subbranch_ext";
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

