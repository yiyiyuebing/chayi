package pub.makers.shop.base.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.entity.CommonText;

import java.util.List;

/**
 * Created by dy on 2017/7/28.
 */
@Service(version = "1.0.0")
public class CommonTextBizServiceImpl implements CommonTextBizService {

    @Autowired
    private CommonTextService commonTextService;

    @Override
    public CommonText getCommonTextByType(String type) {
        List<CommonText> commonTextList = commonTextService.list(Conds.get().eq("type", type));
        if (commonTextList.isEmpty()) {
            return null;
        }
        return commonTextList.get(0);
    }
}
