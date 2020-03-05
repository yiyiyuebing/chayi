package pub.makers.shop.thirdpart.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lantu.base.constant.CfgConstants;

import pub.makers.shop.base.util.SerializeUtils;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.u8.vo.U8Param;

@Service
public class U8SyncServiceImpl implements U8SyncService{

	@Autowired
    private AmqpTemplate amqpTemplate;

	@Override
	public void account(String orderId, OrderBizType orderBizType, OrderType orderType, int stage) {
		
		U8Param param = new U8Param();
		param.setOrderId(orderId);
		param.setOrderBizType(orderBizType);
		param.setOrderType(orderType);
		param.setStage(stage);
		
		amqpTemplate.convertAndSend(CfgConstants.getProperties().get("rabbit.keyU8"), SerializeUtils.hessianSerialize(param));
		
	}
}
