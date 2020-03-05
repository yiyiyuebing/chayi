package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.cargo.entity.CargoMaterialLibraryRel;

/**
 * Created by dy on 2017/9/11.
 */
@Repository
public class CargoMaterialLibraryRelDao extends BaseCRUDDaoImpl<CargoMaterialLibraryRel, String> {

    @Override
    protected String getTableName() {
        return "cargo_material_library_rel";
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
