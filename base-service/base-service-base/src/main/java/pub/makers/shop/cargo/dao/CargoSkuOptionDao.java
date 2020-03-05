package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.CargoSkuOption;

/**
 * Created by dy on 2017/6/27.
 */
@Repository
public class CargoSkuOptionDao extends BaseCRUDDaoImpl<CargoSkuOption, String> {
    @Override
    protected String getTableName() {

        return "cargo_sku_option";
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
