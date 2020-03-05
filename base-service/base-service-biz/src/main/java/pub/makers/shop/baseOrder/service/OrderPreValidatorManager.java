package pub.makers.shop.baseOrder.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Service;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;

import javax.annotation.Resource;
import java.util.Collection;

@Service
public class OrderPreValidatorManager {

	private final Multimap<String, OrderPreValidator> validatorMap = ArrayListMultimap.create();
	@Resource(name="stockPreValidator")
	private OrderPreValidator stockPreValidator;
	@Resource(name="tuangouPreValidator")
	private OrderPreValidator tuangouPreValidator;
	@Resource(name = "startNumPreValidator")
	private OrderPreValidator startNumPreValidator;
	
	public synchronized Collection<OrderPreValidator> getValidators(OrderBizType orderBizType, OrderType orderType){
		
		if (validatorMap.size() == 0){
			init();
		}
		
		return validatorMap.get(getKey(orderBizType, orderType));
	}
	
	private void init(){
		
		// 库存检查应用于采购，商城的普通订单
		validatorMap.put(getKey(OrderBizType.trade, OrderType.normal), stockPreValidator);
		validatorMap.put(getKey(OrderBizType.purchase, OrderType.normal), stockPreValidator);
		
		// 团购检查应用于商城的普通订单
		validatorMap.put(getKey(OrderBizType.trade, OrderType.normal), tuangouPreValidator);

		// 起订量检查应用于采购的普通订单、预售订单
		validatorMap.put(getKey(OrderBizType.purchase, OrderType.normal), startNumPreValidator);
		validatorMap.put(getKey(OrderBizType.purchase, OrderType.presell), startNumPreValidator);
	}
	
	private String getKey(OrderBizType orderBizType, OrderType orderType){
		
		return String.format("%s_%s", orderBizType.name(), orderType.name());
	}
}
