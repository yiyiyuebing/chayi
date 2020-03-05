package pub.makers.shop.marketing.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.marketing.entity.MktShowcaseGood;

/**
 * Created by dy on 2017/5/29.
 */
@Repository
public class MktShowcaseGoodDao extends BaseCRUDDaoImpl<MktShowcaseGood, String> {

    @Override
    protected String getTableName() {

        return "mkt_showcase_good";
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
