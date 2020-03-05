package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.CargoSupplier;

/**
 * Created by dy on 2017/5/24.
 */
@Repository
public class CargoSupplierDao extends BaseCRUDDaoImpl<CargoSupplier, String> {

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
