package pub.makers.shop.marketing.service;

import pub.makers.shop.marketing.entity.VtwoStudyVisitor;

import java.util.Map;

/**
 * Created by dy on 2017/5/5.
 */
public interface VtwoStudyVisitorMgrBizService {

    /**
     * 通过文章ID和用户类型查询文章浏览记录数
     * @param studyId
     * @param userType
     * @return
     */
    long getStudyVisitorCountByStudyId(long studyId, String userType);

    /**
     * 通过文章ID和用户ID查询文章浏览记录详情
     * @param studyId
     * @param userId
     * @return
     */
    VtwoStudyVisitor getStudyVisitorByStudyIdAndUserId(long studyId, long userId);

    /**
     * 保存或更新文章流量信息
     * @param studyId
     * @param userId
     * @param userType
     */
    void saveOrUpdateStudyVisitor(long studyId, Long userId, String userType);

    /**
     * 获取微信用户信息
     * @param userId
     * @return
     */
    Map<String, Object> getWxUserInfo(long userId);

}
