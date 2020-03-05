package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.CargoSku;

/**
 * Created by dy on 2017/4/15.
 */
@Repository
public class CargoSkuDao extends BaseCRUDDaoImpl<CargoSku, String> {

    @Override
    protected String getTableName() {

        return "cargo_sku";
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
