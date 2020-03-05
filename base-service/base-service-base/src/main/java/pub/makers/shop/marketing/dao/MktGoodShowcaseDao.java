package pub.makers.shop.marketing.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.marketing.entity.MktGoodShowcase;

/**
 * Created by dy on 2017/5/29.
 */
@Repository
public class MktGoodShowcaseDao extends BaseCRUDDaoImpl<MktGoodShowcase, String> {
    @Override
    protected String getTableName() {

        return "mkt_good_showcase";
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
