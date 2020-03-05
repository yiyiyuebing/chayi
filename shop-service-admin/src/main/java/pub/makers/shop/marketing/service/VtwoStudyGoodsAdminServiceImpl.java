package pub.makers.shop.marketing.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;

/**
 * Created by dy on 2017/5/5.
 */
@Service(version = "1.0.0")
public class VtwoStudyGoodsAdminServiceImpl implements VtwoStudyGoodsMgrBizService {

    @Autowired
    private VtwoStudyGoodsService vtwoStudyGoodsService;

    @Override
    public Long getStudyGoodsCountsById(Long studyId) {
        return vtwoStudyGoodsService.count(Conds.get().eq("study_id", studyId));
    }
}
