package pub.makers.shop.purchaseOrder.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.baseOrder.entity.OrderListPayment;
import pub.makers.shop.baseOrder.service.OrderListPaymentService;
import pub.makers.shop.purchaseOrder.dao.PurchaseOrderListPaymentDao;

@Service
public class PurchaseOrderListPaymentServiceImpl extends BaseCRUDServiceImpl<OrderListPayment, String, PurchaseOrderListPaymentDao>
										implements OrderListPaymentService{
	
}
