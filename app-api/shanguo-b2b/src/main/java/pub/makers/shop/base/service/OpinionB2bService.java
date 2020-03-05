package pub.makers.shop.base.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import pub.makers.shop.base.entity.Opinion;
import pub.makers.shop.base.vo.OpinionVo;
import pub.makers.shop.base.vo.ResultData;

/**
 * Created by Think on 2017/8/25.
 */
@Service
public class OpinionB2bService {

    @Reference(version = "1.0.0")
    private OpinionBizService opinionBizService;

    /**
     * 保存意见
     */
    public void save(OpinionVo opinion) {

        opinionBizService.saveOpinion(opinion);

    }
}
