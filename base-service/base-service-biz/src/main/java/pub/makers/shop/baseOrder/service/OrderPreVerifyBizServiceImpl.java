package pub.makers.shop.baseOrder.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.BaseOrderItem;
import pub.makers.shop.baseOrder.pojo.OrderVerificationResult;
import pub.makers.shop.baseOrder.pojo.TradeContext;

import java.util.Collection;
import java.util.List;

@Service(version = "1.0.0")
public class OrderPreVerifyBizServiceImpl implements OrderPreVerifyBizService{

	@Autowired
	private OrderPreValidatorManager validatorManager;
	
	@Override
	public OrderVerificationResult validate(List<? extends BaseOrderItem> itemList, OrderBizType bizType, OrderType orderType, TradeContext tradeContext) {
		
		OrderVerificationResult result = null;
		
		Collection<OrderPreValidator> validators = validatorManager.getValidators(bizType, orderType);
		for (OrderPreValidator validator : validators){
			result = validator.validate(itemList, bizType, orderType, tradeContext);
			if (result!= null && !result.isSuccess()){
				break;
			}
		}
		
		return result == null ? OrderVerificationResult.createSuccess() : result;
	}

}
