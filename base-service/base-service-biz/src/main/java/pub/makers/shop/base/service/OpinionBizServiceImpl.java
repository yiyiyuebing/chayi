package pub.makers.shop.base.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.shop.base.entity.Opinion;
import pub.makers.shop.base.enums.OpinionType;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.OpinionVo;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.service.SubbranchBizService;

import java.util.Date;

/**
 * Created by Think on 2017/8/25.
 */
@Service(version = "1.0.0")
public class OpinionBizServiceImpl implements OpinionBizService {
    @Autowired
    private OpinionService opinionService;
    @Reference(version = "1.0.0")
    private SubbranchBizService subbranchBizService;

    @Override
    public void saveOpinion(OpinionVo opinionVo) {
        Subbranch subbranch = subbranchBizService.getById(opinionVo.getClientId());
        opinionVo.setClientName(subbranch.getName());
        opinionVo.setId(IdGenerator.getDefault().nextStringId());
        Opinion opinion = new Opinion();
        opinion.setId(Long.valueOf(opinionVo.getId()));
        opinion.setClientId(Long.valueOf(opinionVo.getClientId()));
        opinion.setClientName(opinionVo.getClientName());
        opinion.setClientPhone(opinionVo.getClientPhone());
        opinion.setCreateTime(new Date());
        opinion.setDescription(opinionVo.getDescriptionText());
        opinion.setType(OpinionType.advice.getDbData() + "");
        opinionService.insert(opinion);
    }
}
