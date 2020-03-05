package pub.makers.shop.base.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.store.entity.StoreSubbranchExt;
import pub.makers.shop.user.entity.WeixinUserInfoExt;

/**
 * Created by devpc on 2017/7/25.
 */
@Repository
public class StoreSubbranchExtDao extends BaseCRUDDaoImpl<StoreSubbranchExt, Long> {
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

        return true;
    }
}
