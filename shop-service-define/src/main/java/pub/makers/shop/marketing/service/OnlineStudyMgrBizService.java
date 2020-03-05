package pub.makers.shop.marketing.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.marketing.entity.OnlineStudy;
import pub.makers.shop.marketing.vo.OnlineStudyVo;

import java.util.Map;

/**
 * Created by dy on 2017/5/3.
 */
public interface OnlineStudyMgrBizService {

    /**
     * 保存文章信息
     * @param onlineStudyVo
     * @return
     */
    OnlineStudyVo saveOnlineStudy(OnlineStudyVo onlineStudyVo);

    /**
     * 更新文章信息
     *
     * @param oldOnlineStudy
     * @param onlineStudyVo
     * @return
     */
    OnlineStudyVo updateOnlineStudy(OnlineStudy oldOnlineStudy, OnlineStudyVo onlineStudyVo);

    /**
     * 获取文章列表数据
     * @param onlineStudyVo
     * @param pg
     * @return
     */
    ResultList<OnlineStudyVo> listOnlineStudyByConditions(OnlineStudyVo onlineStudyVo, Paging pg);

    /**
     * 处理文章分享功能
     * @param idStr
     * @param isShare
     * @return
     */
    Map<String, Object> optOnlineStudyShare(String idStr, Integer isShare);

    /**
     * 获取文章详细信息
     * @param id
     * @param userId
     * @param userType
     * @return
     */
    Map<String, Object> findOnlineStudyDetail(String id, Long userId, String userType);

    /**
     * 获取文章信息
     * @param id
     * @param userId
     * @return
     */
    OnlineStudyVo getOnlineStudyInfo(String id, Long userId);

}
