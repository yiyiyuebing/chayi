package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.CargoSkuItem;

/**
 * Created by dy on 2017/5/24.
 */
@Repository
public class CargoSkuItemDao extends BaseCRUDDaoImpl<CargoSkuItem, String> {
    @Override
    protected String getTableName() {

        return "cargo_sku_item";
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
