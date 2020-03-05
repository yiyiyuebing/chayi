package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.GoodPageTplModelSort;

/**
 * Created by daiwenfa on 2017/7/28.
 */
@Repository
public class GoodPageTplModelSortDao extends BaseCRUDDaoImpl<GoodPageTplModelSort, String> {
    @Override
    protected String getTableName() {
        return "good_page_tpl_model_sort";
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
