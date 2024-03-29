package pub.makers.shop.marketing.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.marketing.entity.OnlineStudy;
import pub.makers.shop.marketing.vo.OnlineStudyVo;

import java.util.List;

public interface OnlineStudyBizService {


    /**
     * 保存预览
     *
     * @param onlineStudy
     * @return
     */
    OnlineStudy savePreview(OnlineStudy onlineStudy);

    /**
     * 根据主键查询预览
     *
     * @param id
     * @return
     */
    OnlineStudy getPreviewById(String id);

    /**
     * 通过文章标题查找文章列表数据
     *
     * @param title
     * @return
     */
    List<OnlineStudy> queryOnlineStudyByTitle(String title);

    /**
     * 根据主键查找文章
     *
     * @param id
     * @return
     */
    OnlineStudy getOnlineStudyById(String id);

    /**
     * 分类查找文章列表
     */
    ResultData getOnlineStudyList(String tag, Integer pageNum, Integer pageSize);

    /**
     * 文章详情
     */
    OnlineStudyVo getById(String id);

    /**
     * 营销推广文章列表
     * @param onlineStudyVo
     * @param paging
     * @return
     */
    ResultList<OnlineStudyVo> findStudyAllList(OnlineStudyVo onlineStudyVo, Paging paging);
}
