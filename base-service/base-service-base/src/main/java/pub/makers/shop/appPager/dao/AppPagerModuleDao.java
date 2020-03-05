package pub.makers.shop.appPager.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.appPager.entity.AppPagerModule;

/**
 * Created by kok on 2017/6/2.
 */
@Repository
public class AppPagerModuleDao extends BaseCRUDDaoImpl<AppPagerModule, String> {
    @Override
    protected String getTableName() {
        return "app_pager_module";
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
