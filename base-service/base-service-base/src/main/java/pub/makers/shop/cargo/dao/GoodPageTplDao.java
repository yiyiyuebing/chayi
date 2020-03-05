package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.GoodPageTpl;

/**
 * Created by daiwenfa on 2017/6/7.
 */
@Repository
public class GoodPageTplDao extends BaseCRUDDaoImpl<GoodPageTpl, String> {
    @Override
    protected String getTableName() {
        return "good_page_tpl";
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
