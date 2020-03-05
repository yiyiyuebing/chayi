package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.CargoSkuOptionDao;
import pub.makers.shop.cargo.entity.CargoSkuOption;
import pub.makers.shop.cargo.service.CargoSkuOptionService;

/**
 * Created by dy on 2017/6/27.
 */
@Service
public class CargoSkuOptionServiceImpl extends BaseCRUDServiceImpl<CargoSkuOption, String, CargoSkuOptionDao> implements CargoSkuOptionService {
}
