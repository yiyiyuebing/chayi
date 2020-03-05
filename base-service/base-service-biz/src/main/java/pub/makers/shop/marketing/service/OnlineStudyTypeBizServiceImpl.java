package pub.makers.shop.marketing.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.shop.marketing.entity.OnlineStudyType;

/**
 * Created by dy on 2017/5/3.
 */
@Service(version = "1.0.0")
public class OnlineStudyTypeBizServiceImpl implements OnlineStudyTypeBizService {

    @Autowired
    private OnlineStudyTypeService onlineStudyTypeService;

    @Override
    public OnlineStudyType getOnlineStudyTypeById(Long id) {
        return onlineStudyTypeService.getById(id);
    }
}
