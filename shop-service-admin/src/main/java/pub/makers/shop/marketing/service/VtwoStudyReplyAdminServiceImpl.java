package pub.makers.shop.marketing.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;

/**
 * Created by dy on 2017/5/5.
 */
@Service(version = "1.0.0")
public class VtwoStudyReplyAdminServiceImpl implements VtwoStudyReplyMgrBizService {

    @Autowired
    private VtwoStudyReplyService vtwoStudyReplyService;
    @Override
    public long getStudyReplyCountByStudyId(String userType, long studyId) {
        if (StringUtils.isNotBlank(userType)) {
            return vtwoStudyReplyService.count(Conds.get().eq("study_id", studyId).eq("status", 0).eq("user_type", userType));
        } else {
            return vtwoStudyReplyService.count(Conds.get().eq("study_id", studyId).eq("status", 0));
        }
    }
}
