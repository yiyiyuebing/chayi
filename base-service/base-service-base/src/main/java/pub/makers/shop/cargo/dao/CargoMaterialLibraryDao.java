package pub.makers.shop.cargo.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.cargo.entity.CargoMaterialLibrary;

/**
 * Created by daiwenfa on 2017/6/4.
 */
@Repository
public class CargoMaterialLibraryDao extends BaseCRUDDaoImpl<CargoMaterialLibrary,String> {
    @Override
    protected String getTableName() {
        return "cargo_material_library";
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
