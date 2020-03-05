package pub.makers.shop.appPager.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.appPager.entity.AppPager;

/**
 * Created by kok on 2017/6/2.
 */
@Repository
public class AppPagerDao extends BaseCRUDDaoImpl<AppPager, String> {
    @Override
    protected String getTableName() {
        return "app_pager";
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
