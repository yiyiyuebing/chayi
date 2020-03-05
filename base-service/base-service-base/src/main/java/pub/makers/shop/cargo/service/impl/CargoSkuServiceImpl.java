package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.CargoSkuDao;
import pub.makers.shop.cargo.entity.CargoSku;
import pub.makers.shop.cargo.service.CargoSkuService;

/**
 * Created by dy on 2017/4/15.
 */
@Service
public class CargoSkuServiceImpl extends BaseCRUDServiceImpl<CargoSku, String, CargoSkuDao> implements CargoSkuService {
}
