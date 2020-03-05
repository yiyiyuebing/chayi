package pub.makers.shop.bill.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.constant.CfgConstants;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.shop.base.util.SerializeUtils;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.bill.entity.MqTaskInfo;
import pub.makers.shop.bill.enums.MqTaskType;
import pub.makers.shop.bill.param.OrderBillParam;

import java.util.Date;

/**
 * Created by dy on 2017/9/8.
 */
@Service(version = "1.0.0")
public class OrderBillMqTaskImpl implements OrderBillMqTask {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Reference(version = "1.0.0")
    private MqTaskInfoBizService mqTaskInfoBizService;


    @Override
    public void addOrderBillRecord(String orderId) {

        OrderBillParam param = new OrderBillParam();
        param.setOrderId(orderId);

        MqTaskInfo mqTaskInfo = new MqTaskInfo();
        mqTaskInfo.setId(IdGenerator.getDefault().nextId());
        mqTaskInfo.setMsgContent(SerializeUtils.hessianSerialize(param));
        mqTaskInfo.setMsgType(MqTaskType.bill.name());
        mqTaskInfo.setSendTime(new Date());
        mqTaskInfo.setStatus(BoolType.F.name());
        //保存队列消息
        mqTaskInfoBizService.saveTaskInfo(mqTaskInfo);

        param.setTaskId(mqTaskInfo.getId());

        //发送消息
        amqpTemplate.convertAndSend(CfgConstants.getProperties().get("rabbit.keyMqTask"), SerializeUtils.hessianSerialize(param));

    }
}
