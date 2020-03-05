package pub.makers.shop.afterSale.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;

@Service
public class AfterSaleHandlerManager {

	@Resource(name="purchaseNormalAfterSaleHandler")
	private AfterSaleHandler purchaseNormalAfterSaleHandler;
	
	@Resource(name="purchasePresellAfterSaleHandler")
	private AfterSaleHandler purchasePresellAfterSaleHandler;
	
	@Resource(name="tradeNormalAfterSaleHandler")
	private AfterSaleHandler tradeNormalAfterSaleHandler;
	
	@Resource(name="tradePresellAfterSaleHandler")
	private AfterSaleHandler tradePresellAfterSaleHandler;
	
	private static final Map<String, AfterSaleHandler> handlerMap = Maps.newHashMap();
	
	
	public synchronized AfterSaleHandler getHandler(OrderBizType orderBizType, OrderType orderType){
		
		if (handlerMap.isEmpty()){
			handlerMap.put(getKey(OrderBizType.purchase, OrderType.normal), purchaseNormalAfterSaleHandler); 
			handlerMap.put(getKey(OrderBizType.purchase, OrderType.presell), purchasePresellAfterSaleHandler); 
			handlerMap.put(getKey(OrderBizType.trade, OrderType.normal), tradeNormalAfterSaleHandler); 
			handlerMap.put(getKey(OrderBizType.trade, OrderType.presell), tradePresellAfterSaleHandler);
		}
		
		return handlerMap.get(getKey(orderBizType, orderType));
		
	}
	
	private String getKey(OrderBizType orderBizType, OrderType orderType){
		
		return String.format("%s_%s", orderBizType.name(), orderType.name());
	}
	
}
