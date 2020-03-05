package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.CargoBasePropertys;

/**
 * Created by dy on 2017/5/23.
 */
@Repository
public class CargoBasePropertysDao extends BaseCRUDDaoImpl<CargoBasePropertys, String> {
    @Override
    protected String getTableName() {

        return "cargo_base_propertys";
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
