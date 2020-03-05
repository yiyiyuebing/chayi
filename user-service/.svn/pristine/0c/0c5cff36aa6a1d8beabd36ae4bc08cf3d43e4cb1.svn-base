package pub.makers.shop.bill.service;

import com.dev.base.utils.DateUtil;
import com.lantu.base.common.entity.BoolType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.shop.base.util.SerializeUtils;
import pub.makers.shop.bill.entity.MqTaskInfo;
import pub.makers.shop.bill.param.OrderBillParam;
import pub.makers.shop.u8.service.U8MgrBizSerivce;
import pub.makers.shop.u8.vo.U8Param;

import java.util.Date;

/**
 * Created by dy on 2017/9/8.
 */
public class OrderBillListener implements MessageListener {

    @Autowired
    private BillBizService billBizService;
    @Autowired
    private MqTaskInfoService mqTaskInfoService;

    public void onMessage(Message message) {
        OrderBillParam param = null;
        try {
            Thread.sleep(5000);
            param = SerializeUtils.hessianDeSerialize(new String(message.getBody()));
            MqTaskInfo mqTaskInfo = mqTaskInfoService.getById(param.getTaskId());
            mqTaskInfo.setStatus(BoolType.T.name());
            mqTaskInfo.setFinishTime(new Date());
            Long spendTime = new Date().getTime() - mqTaskInfo.getSendTime().getTime();
            mqTaskInfo.setSpendTime(spendTime/1000); //转为秒
            mqTaskInfoService.update(mqTaskInfo);

            billBizService.addOrderBillRecord(param.getOrderId()); //账单结算
        } catch (InterruptedException e) {
            e.printStackTrace();
            MqTaskInfo mqTaskInfo = mqTaskInfoService.getById(param.getTaskId());
            mqTaskInfo.setBackMsg(e.getMessage());
            mqTaskInfoService.update(mqTaskInfo);

        }

    }

}
