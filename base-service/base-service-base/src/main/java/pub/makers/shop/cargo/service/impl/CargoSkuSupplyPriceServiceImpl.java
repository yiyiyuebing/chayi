package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.CargoSkuSupplyPriceDao;
import pub.makers.shop.cargo.entity.CargoSkuSupplyPrice;
import pub.makers.shop.cargo.service.CargoSkuSupplyPriceService;

/**
 * Created by dy on 2017/5/24.
 */
@Service
public class CargoSkuSupplyPriceServiceImpl extends BaseCRUDServiceImpl<CargoSkuSupplyPrice, String, CargoSkuSupplyPriceDao> implements CargoSkuSupplyPriceService {
}
