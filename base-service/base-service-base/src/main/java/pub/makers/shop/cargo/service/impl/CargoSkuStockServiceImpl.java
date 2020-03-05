package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.CargoSkuStockDao;
import pub.makers.shop.cargo.entity.CargoSkuStock;
import pub.makers.shop.cargo.service.CargoSkuStockService;

/**
 * Created by kok on 2017/5/10.
 */
@Service
public class CargoSkuStockServiceImpl extends BaseCRUDServiceImpl<CargoSkuStock, String, CargoSkuStockDao> implements CargoSkuStockService {
}
