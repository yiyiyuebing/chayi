package pub.makers.shop.appPager.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.appPager.entity.AppPagerModuleGood;

/**
 * Created by kok on 2017/6/2.
 */
@Repository
public class AppPagerModuleGoodDao extends BaseCRUDDaoImpl<AppPagerModuleGood, String> {
    @Override
    protected String getTableName() {
        return "app_pager_module_good";
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
