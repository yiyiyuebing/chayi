package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.CargoBaseSkuItemDao;
import pub.makers.shop.cargo.entity.CargoBaseSkuItem;
import pub.makers.shop.cargo.service.CargoBaseSkuItemService;

/**
 * Created by dy on 2017/5/22.
 */
@Service
public class CargoBaseSkuItemServiceImpl extends BaseCRUDServiceImpl<CargoBaseSkuItem, String, CargoBaseSkuItemDao> implements CargoBaseSkuItemService {
}
