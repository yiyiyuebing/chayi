package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.GoodPageTplModel;

/**
 * Created by daiwenfa on 2017/6/7.
 */
@Repository
public class GoodPageTplModelDao extends BaseCRUDDaoImpl<GoodPageTplModel, String> {
    @Override
    protected String getTableName() {
        return "good_page_tpl_model";
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