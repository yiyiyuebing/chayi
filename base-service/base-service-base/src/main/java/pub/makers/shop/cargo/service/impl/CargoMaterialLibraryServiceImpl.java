package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.CargoMaterialLibraryDao;
import pub.makers.shop.cargo.entity.CargoMaterialLibrary;
import pub.makers.shop.cargo.service.CargoMaterialLibraryService;

/**
 * Created by daiwenfa on 2017/6/4.
 */
@Service
public class CargoMaterialLibraryServiceImpl extends BaseCRUDServiceImpl<CargoMaterialLibrary,String,CargoMaterialLibraryDao> implements CargoMaterialLibraryService{
}
