package pub.makers.shop.afterSale.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.afterSale.dao.OrderItemAsFlowDao;
import pub.makers.shop.afterSale.entity.OrderItemAsFlow;
import pub.makers.shop.afterSale.service.OrderItemAsFlowService;

/**
 * Created by kok on 2017/5/13.
 */
@Service
public class OrderItemAsFlowServiceImpl extends BaseCRUDServiceImpl<OrderItemAsFlow, String, OrderItemAsFlowDao> implements OrderItemAsFlowService {
}
