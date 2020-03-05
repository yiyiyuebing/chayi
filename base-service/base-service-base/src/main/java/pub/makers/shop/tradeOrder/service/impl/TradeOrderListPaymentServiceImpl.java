package pub.makers.shop.tradeOrder.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.baseOrder.entity.OrderListPayment;
import pub.makers.shop.baseOrder.service.OrderListPaymentService;
import pub.makers.shop.tradeOrder.dao.TradeOrderListPaymentDao;

@Service
public class TradeOrderListPaymentServiceImpl extends BaseCRUDServiceImpl<OrderListPayment, String, TradeOrderListPaymentDao>
										implements OrderListPaymentService{
	
}
