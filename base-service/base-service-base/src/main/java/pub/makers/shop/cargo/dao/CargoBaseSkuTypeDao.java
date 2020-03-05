package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.CargoBaseSkuType;

/**
 * Created by dy on 2017/5/22.
 */
@Repository
public class CargoBaseSkuTypeDao extends BaseCRUDDaoImpl<CargoBaseSkuType, String> {
    @Override
    protected String getTableName() {

        return "cargo_base_sku_type";
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
