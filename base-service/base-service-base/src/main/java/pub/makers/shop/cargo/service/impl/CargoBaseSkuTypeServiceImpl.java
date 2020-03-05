package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.CargoBaseSkuTypeDao;
import pub.makers.shop.cargo.entity.CargoBaseSkuType;
import pub.makers.shop.cargo.service.CargoBaseSkuTypeService;

/**
 * Created by dy on 2017/5/22.
 */
@Service
public class CargoBaseSkuTypeServiceImpl extends BaseCRUDServiceImpl<CargoBaseSkuType, String, CargoBaseSkuTypeDao> implements CargoBaseSkuTypeService {
}
