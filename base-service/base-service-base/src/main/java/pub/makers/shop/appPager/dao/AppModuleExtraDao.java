package pub.makers.shop.appPager.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.appPager.entity.AppModuleExtra;

/**
 * Created by kok on 2017/6/2.
 */
@Repository
public class AppModuleExtraDao extends BaseCRUDDaoImpl<AppModuleExtra, String> {
    @Override
    protected String getTableName() {
        return "app_module_extra";
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
