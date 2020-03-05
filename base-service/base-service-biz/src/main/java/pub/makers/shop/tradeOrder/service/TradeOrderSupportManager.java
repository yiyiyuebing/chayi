package pub.makers.shop.tradeOrder.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.service.BaseOrderSupport;

@Service
public class TradeOrderSupportManager {
	
	private final Map<String, BaseOrderSupport> supportMap = Maps.newHashMap();
	@Resource(name="tradeOrderNormalSupport")
	private BaseOrderSupport normalSupport;
	@Resource(name="tradeOrderPresellSupport")
	private BaseOrderSupport presellSupport;
	

	public synchronized BaseOrderSupport getOrderSupport(OrderBizType orderBizType, OrderType orderType){
		if (supportMap.size() == 0){
			init();
		}
		
		return supportMap.get(getKey(orderBizType, orderType));
	}
	
	private void init(){
		supportMap.put(getKey(OrderBizType.trade, OrderType.normal), normalSupport);
		supportMap.put(getKey(OrderBizType.trade, OrderType.presell), presellSupport);
	}
	
	private String getKey(OrderBizType orderBizType, OrderType orderType) {

		return String.format("%s_%s", orderBizType.name(), orderType.name());
	}
}
