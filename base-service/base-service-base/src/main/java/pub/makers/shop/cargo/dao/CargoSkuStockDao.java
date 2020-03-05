package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.BaseCRUDDao;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.CargoSkuStock;

/**
 * Created by kok on 2017/5/10.
 */
@Repository
public class CargoSkuStockDao extends BaseCRUDDaoImpl<CargoSkuStock, String> {
    @Override
    protected String getTableName() {
        return "cargo_sku_stock";
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
