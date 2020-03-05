package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.CargoSupplierDao;
import pub.makers.shop.cargo.entity.CargoSupplier;
import pub.makers.shop.cargo.service.CargoSupplierService;

/**
 * Created by dy on 2017/5/24.
 */
@Service
public class CargoSupplierServiceImpl extends BaseCRUDServiceImpl<CargoSupplier, String, CargoSupplierDao> implements CargoSupplierService {
}
