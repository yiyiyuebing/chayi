package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.CargoSkuItemDao;
import pub.makers.shop.cargo.entity.CargoSkuItem;
import pub.makers.shop.cargo.service.CargoSkuItemService;

/**
 * Created by dy on 2017/5/24.
 */
@Service
public class CargoSkuItemServiceImpl extends BaseCRUDServiceImpl<CargoSkuItem, String, CargoSkuItemDao> implements CargoSkuItemService {
}
