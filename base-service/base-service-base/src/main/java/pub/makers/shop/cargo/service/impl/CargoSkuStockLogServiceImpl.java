package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.CargoSkuStockLogDao;
import pub.makers.shop.cargo.entity.CargoSkuStockLog;
import pub.makers.shop.cargo.service.CargoSkuStockLogService;

/**
 * Created by kok on 2017/5/12.
 */
@Service
public class CargoSkuStockLogServiceImpl extends BaseCRUDServiceImpl<CargoSkuStockLog, String, CargoSkuStockLogDao> implements CargoSkuStockLogService {
}
