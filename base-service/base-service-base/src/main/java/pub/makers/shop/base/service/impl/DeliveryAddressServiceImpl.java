package pub.makers.shop.base.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.base.dao.DeliveryAddressDao;
import pub.makers.shop.base.entity.DeliveryAddress;
import pub.makers.shop.base.service.DeliveryAddressService;

/**
 * Created by kok on 2017/7/13.
 */
@Service
public class DeliveryAddressServiceImpl extends BaseCRUDServiceImpl<DeliveryAddress, String, DeliveryAddressDao> implements DeliveryAddressService {
}
