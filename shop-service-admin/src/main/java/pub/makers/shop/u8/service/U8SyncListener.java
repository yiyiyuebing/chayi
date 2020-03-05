package pub.makers.shop.u8.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import pub.makers.shop.base.util.SerializeUtils;
import pub.makers.shop.u8.vo.U8Param;

public class U8SyncListener implements MessageListener{

	@Autowired
	private U8MgrBizSerivce u8Service;
	
	public void onMessage(Message message) {
		try {
			Thread.sleep(10000);
			U8Param param = SerializeUtils.hessianDeSerialize(new String(message.getBody()));
			u8Service.u8OrderSync(param.getOrderId(), param.getOrderType(), param.getOrderBizType(), param.getStage());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
