package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.CargoSuppliers;

/**
 * Created by daiwenfa on 2017/5/22.
 */
@Repository
public class CargoSuppliersDao extends BaseCRUDDaoImpl<CargoSuppliers, String> {
    @Override
    protected String getTableName() {
        return "cargo_supplier";
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
