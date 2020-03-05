package pub.makers.shop.tradeGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.store.entity.VtwoGoodPackage;

/**
 * Created by dy on 2017/4/14.
 */
@Repository
public class VtwoGoodPackageDao extends BaseCRUDDaoImpl<VtwoGoodPackage, String> {

    @Override
    protected String getTableName() {
        return "vtwo_good_package";
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
