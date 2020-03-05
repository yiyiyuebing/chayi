package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.CargoClassify;

/**
 * Created by dy on 2017/4/21.
 */
@Repository
public class CargoClassifyDao extends BaseCRUDDaoImpl<CargoClassify, String> {

    @Override
    protected String getTableName() {

        return "cargo_classify";
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
