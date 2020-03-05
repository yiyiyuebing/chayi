package pub.makers.shop.store.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.store.entity.WeixinUserStoreLabel;

/**
 * Created by dy on 2017/10/10.
 */
@Repository
public class WeixinUserStoreLabelDao extends BaseCRUDDaoImpl<WeixinUserStoreLabel, String> {

    @Override
    protected String getTableName() {
        return "weixin_user_store_label";
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
