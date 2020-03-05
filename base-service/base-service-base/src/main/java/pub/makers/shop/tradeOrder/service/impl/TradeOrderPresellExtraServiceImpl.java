package pub.makers.shop.tradeOrder.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.baseOrder.entity.OrderPresellExtra;
import pub.makers.shop.baseOrder.service.OrderPresellExtraService;
import pub.makers.shop.tradeOrder.dao.TradeOrderPresellExtraDao;

@Service
public class TradeOrderPresellExtraServiceImpl extends BaseCRUDServiceImpl<OrderPresellExtra, String, TradeOrderPresellExtraDao>
										implements OrderPresellExtraService{
	
}
