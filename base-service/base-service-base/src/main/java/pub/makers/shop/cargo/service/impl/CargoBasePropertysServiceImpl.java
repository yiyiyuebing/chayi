package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.CargoBasePropertysDao;
import pub.makers.shop.cargo.entity.CargoBasePropertys;
import pub.makers.shop.cargo.service.CargoBasePropertysService;

/**
 * Created by dy on 2017/5/23.
 */
@Service
public class CargoBasePropertysServiceImpl extends BaseCRUDServiceImpl<CargoBasePropertys, String, CargoBasePropertysDao> implements CargoBasePropertysService {
}
