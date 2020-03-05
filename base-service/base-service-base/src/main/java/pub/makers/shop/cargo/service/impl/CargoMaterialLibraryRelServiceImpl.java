package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.CargoMaterialLibraryRelDao;
import pub.makers.shop.cargo.entity.CargoMaterialLibraryRel;
import pub.makers.shop.cargo.service.CargoMaterialLibraryRelService;

/**
 * Created by dy on 2017/9/11.
 */
@Service
public class CargoMaterialLibraryRelServiceImpl extends BaseCRUDServiceImpl<CargoMaterialLibraryRel, String, CargoMaterialLibraryRelDao> implements CargoMaterialLibraryRelService {
}
