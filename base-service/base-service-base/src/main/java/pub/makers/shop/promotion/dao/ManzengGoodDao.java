package pub.makers.shop.promotion.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.promotion.entity.ManzengGood;

/**
 * Created by dy on 2017/8/17.
 */
@Repository
public class ManzengGoodDao extends BaseCRUDDaoImpl<ManzengGood, String> {
    @Override
    protected String getTableName() {

        return "sp_manzeng_good";
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
