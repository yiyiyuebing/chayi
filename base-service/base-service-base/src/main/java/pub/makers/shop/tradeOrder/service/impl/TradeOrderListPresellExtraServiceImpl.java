package pub.makers.shop.tradeOrder.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.baseOrder.entity.OrderListPresellExtra;
import pub.makers.shop.baseOrder.service.OrderListPresellExtraService;
import pub.makers.shop.tradeOrder.dao.TradeOrderListPresellExtraDao;

@Service
public class TradeOrderListPresellExtraServiceImpl extends BaseCRUDServiceImpl<OrderListPresellExtra, String, TradeOrderListPresellExtraDao>
										implements OrderListPresellExtraService{
	
}
