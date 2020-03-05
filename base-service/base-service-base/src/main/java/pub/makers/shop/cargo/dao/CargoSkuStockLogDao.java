package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.CargoSkuStockLog;

/**
 * Created by kok on 2017/5/12.
 */
@Repository
public class CargoSkuStockLogDao extends BaseCRUDDaoImpl<CargoSkuStockLog, String> {
    @Override
    protected String getTableName() {
        return "cargo_sku_stock_log";
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
