package pub.makers.shop.afterSale.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.afterSale.dao.OrderItemAsFlowDetailDao;
import pub.makers.shop.afterSale.entity.OrderItemAsFlowDetail;
import pub.makers.shop.afterSale.service.OrderItemAsFlowDetailService;

/**
 * Created by kok on 2017/5/13.
 */
@Service
public class OrderItemAsFlowDetailServiceImpl extends BaseCRUDServiceImpl<OrderItemAsFlowDetail, String, OrderItemAsFlowDetailDao> implements OrderItemAsFlowDetailService {
}
