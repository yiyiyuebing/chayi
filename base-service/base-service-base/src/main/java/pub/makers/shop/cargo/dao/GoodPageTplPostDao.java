package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.GoodPageTplPost;

/**
 * Created by daiwenfa on 2017/6/8.
 */
@Repository
public class GoodPageTplPostDao extends BaseCRUDDaoImpl<GoodPageTplPost, String> {
    @Override
    protected String getTableName() {
        return "good_page_tpl_post";
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
