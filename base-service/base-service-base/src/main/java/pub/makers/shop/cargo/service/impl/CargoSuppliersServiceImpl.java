package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.CargoSuppliersDao;
import pub.makers.shop.cargo.entity.CargoSuppliers;
import pub.makers.shop.cargo.service.CargoSuppliersService;

/**
 * Created by daiwenfa on 2017/5/22.
 */
@Service
public class CargoSuppliersServiceImpl extends BaseCRUDServiceImpl<CargoSuppliers, String, CargoSuppliersDao> implements CargoSuppliersService {
}
