package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.CargoSkuTypeDao;
import pub.makers.shop.cargo.entity.CargoSkuType;
import pub.makers.shop.cargo.service.CargoSkuTypeService;

/**
 * Created by dy on 2017/5/23.
 */
@Service
public class CargoSkuTypeServiceImpl extends BaseCRUDServiceImpl<CargoSkuType, String, CargoSkuTypeDao> implements CargoSkuTypeService {
}
