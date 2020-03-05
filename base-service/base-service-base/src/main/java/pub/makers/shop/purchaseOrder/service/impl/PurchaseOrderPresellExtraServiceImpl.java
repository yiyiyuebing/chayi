package pub.makers.shop.purchaseOrder.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.baseOrder.entity.OrderPresellExtra;
import pub.makers.shop.baseOrder.service.OrderPresellExtraService;
import pub.makers.shop.purchaseOrder.dao.PurchaseOrderPresellExtraDao;

@Service
public class PurchaseOrderPresellExtraServiceImpl extends BaseCRUDServiceImpl<OrderPresellExtra, String, PurchaseOrderPresellExtraDao>
										implements OrderPresellExtraService{
	
}
