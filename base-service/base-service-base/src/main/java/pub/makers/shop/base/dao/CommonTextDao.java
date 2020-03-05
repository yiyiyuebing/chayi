package pub.makers.shop.base.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.base.entity.CommonText;

/**
 * Created by dy on 2017/7/28.
 */
@Repository
public class CommonTextDao extends BaseCRUDDaoImpl<CommonText, String> {
    @Override
    protected String getTableName() {

        return "common_text";
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
