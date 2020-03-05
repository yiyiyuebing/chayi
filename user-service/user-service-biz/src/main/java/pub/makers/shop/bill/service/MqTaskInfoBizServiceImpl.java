package pub.makers.shop.bill.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.shop.bill.entity.MqTaskInfo;

/**
 * Created by dy on 2017/9/8.
 */
@Service(version = "1.0.0")
public class MqTaskInfoBizServiceImpl implements MqTaskInfoBizService {

    @Autowired
    private MqTaskInfoService mqTaskInfoService;

    @Override
    public void saveTaskInfo(MqTaskInfo mqTaskInfo) {
        mqTaskInfoService.insert(mqTaskInfo);
    }
}
