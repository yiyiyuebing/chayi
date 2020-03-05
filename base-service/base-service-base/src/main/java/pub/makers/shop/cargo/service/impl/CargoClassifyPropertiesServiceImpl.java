package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.CargoClassifyPropertiesDao;
import pub.makers.shop.cargo.entity.CargoClassifyProperties;
import pub.makers.shop.cargo.service.CargoClassifyPropertiesService;

/**
 * Created by daiwenfa on 2017/5/19.
 */
@Service
public class CargoClassifyPropertiesServiceImpl extends BaseCRUDServiceImpl<CargoClassifyProperties, String, CargoClassifyPropertiesDao> implements CargoClassifyPropertiesService  {
}
