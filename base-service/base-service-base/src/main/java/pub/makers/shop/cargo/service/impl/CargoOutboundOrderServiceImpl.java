package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.CargoOutboundOrderDao;
import pub.makers.shop.cargo.entity.CargoOutboundOrder;
import pub.makers.shop.cargo.service.CargoOutboundOrderService;

/**
 * Created by kok on 2017/5/12.
 */
@Service
public class CargoOutboundOrderServiceImpl extends BaseCRUDServiceImpl<CargoOutboundOrder, String, CargoOutboundOrderDao> implements CargoOutboundOrderService {
}
