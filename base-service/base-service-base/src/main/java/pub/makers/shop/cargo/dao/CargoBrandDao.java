package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.CargoBrand;

/**
 * Created by dy on 2017/5/21.
 */
@Repository
public class CargoBrandDao extends BaseCRUDDaoImpl<CargoBrand, String> {
    @Override
    protected String getTableName() {

        return "cargo_brand";
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
