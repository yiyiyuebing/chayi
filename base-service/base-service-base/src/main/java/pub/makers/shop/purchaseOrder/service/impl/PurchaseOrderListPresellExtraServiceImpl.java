package pub.makers.shop.purchaseOrder.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.baseOrder.entity.OrderListPresellExtra;
import pub.makers.shop.baseOrder.service.OrderListPresellExtraService;
import pub.makers.shop.purchaseOrder.dao.PurchaseOrderListPresellExtraDao;

@Service
public class PurchaseOrderListPresellExtraServiceImpl extends BaseCRUDServiceImpl<OrderListPresellExtra, String, PurchaseOrderListPresellExtraDao>
										implements OrderListPresellExtraService{
	
}
