package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.CargoClassifyProperties;

/**
 * Created by daiwenfa on 2017/5/19.
 */
@Repository
public class CargoClassifyPropertiesDao extends BaseCRUDDaoImpl<CargoClassifyProperties, String> {
    @Override
    protected String getTableName() {

        return "cargo_classify_properties";
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
