package pub.makers.shop.marketing.service;

/**
 * Created by dy on 2017/5/5.
 */
public interface VtwoStudyReplyMgrBizService {

    /**
     * 通过文章ID和用户类型获取文章回复数量
     * @param userType
     * @param studyId
     * @return
     */
    long getStudyReplyCountByStudyId(String userType, long studyId);
}
