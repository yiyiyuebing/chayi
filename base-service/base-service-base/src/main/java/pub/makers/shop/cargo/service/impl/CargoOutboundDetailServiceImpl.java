package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.CargoOutboundDetailDao;
import pub.makers.shop.cargo.entity.CargoOutboundDetail;
import pub.makers.shop.cargo.service.CargoOutboundDetailService;

/**
 * Created by kok on 2017/5/12.
 */
@Service
public class CargoOutboundDetailServiceImpl extends BaseCRUDServiceImpl<CargoOutboundDetail, String, CargoOutboundDetailDao> implements CargoOutboundDetailService {
}
