package pub.makers.shop.marketing.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.index.vo.BaseArticleInfo;
import pub.makers.shop.marketing.entity.Toutiao;
import pub.makers.shop.marketing.vo.OnlineStudyVo;

import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/6/5.
 */
@Service
public class PurchaseMarktingB2bService {

    @Reference(version = "1.0.0")
    private OnlineStudyBizService onlineStudyBizService;
    @Reference(version = "1.0.0")
    private ToutiaoBizService toutiaoBizService;

    /**
     * 获取资讯模版的营销文章列表
     *
     * @param pageNum
     * @param pageSize
     * @param type
     * @return
     */
    public BaseArticleInfo marktingArticleList(Integer pageNum, Integer pageSize, String type) {

        if ("gonggao".equals(type)) {
            ResultData resultData = toutiaoBizService.getToutiaoList("gg", pageNum, pageSize);
            Map<String, Object> gongGaoMap = (Map<String, Object>) resultData.getData();
            List<Toutiao> toutiaoList = (List<Toutiao>) gongGaoMap.get("toutiaoList");
            BaseArticleInfo baseArticleInfo = new BaseArticleInfo();
            baseArticleInfo.setPageNum(pageNum);
            baseArticleInfo.setPageSize(pageSize);
            baseArticleInfo.setTotalRecods(Long.parseLong(gongGaoMap.get("count").toString()));
            List<BaseArticleInfo> baseArticleInfos = Lists.transform(toutiaoList, new Function<Toutiao, BaseArticleInfo>() {
                @Override
                public BaseArticleInfo apply(Toutiao toutiao) {
                    return new BaseArticleInfo(toutiao);
                }
            });
            baseArticleInfo.setBaseArticleInfos(baseArticleInfos);
            return baseArticleInfo;
        }

        if ("cuxiao".equals(type)) {
            ResultData resultData = toutiaoBizService.getToutiaoList("cx", pageNum, pageSize);
            Map<String, Object> cuiXiaoMap = (Map<String, Object>) resultData.getData();
            List<Toutiao> cuXiaoList = (List<Toutiao>) cuiXiaoMap.get("toutiaoList");
            BaseArticleInfo baseArticleInfo = new BaseArticleInfo();
            baseArticleInfo.setPageNum(pageNum);
            baseArticleInfo.setPageSize(pageSize);
            baseArticleInfo.setTotalRecods(Long.parseLong(cuiXiaoMap.get("count").toString()));
            List<BaseArticleInfo> baseArticleInfos = Lists.transform(cuXiaoList, new Function<Toutiao, BaseArticleInfo>() {
                @Override
                public BaseArticleInfo apply(Toutiao toutiao) {
                    return new BaseArticleInfo(toutiao);
                }
            });
            baseArticleInfo.setBaseArticleInfos(baseArticleInfos);
            return baseArticleInfo;
        }

        if ("media".equals(type)) {
            ResultData resultData = onlineStudyBizService.getOnlineStudyList("media", pageNum, pageSize);
            Map<String, Object> mediaMap = (Map<String, Object>) resultData.getData();
            List<OnlineStudyVo> mediaList = (List<OnlineStudyVo>) mediaMap.get("studyList");
            BaseArticleInfo baseArticleInfo = new BaseArticleInfo();
            baseArticleInfo.setPageNum(pageNum);
            baseArticleInfo.setPageSize(pageSize);
            baseArticleInfo.setTotalRecods(Long.parseLong(mediaMap.get("count").toString()));
            List<BaseArticleInfo> baseArticleInfos = Lists.transform(mediaList, new Function<OnlineStudyVo, BaseArticleInfo>() {
                @Override
                public BaseArticleInfo apply(OnlineStudyVo onlineStudyVo) {
                    return new BaseArticleInfo(onlineStudyVo);
                }
            });
            baseArticleInfo.setBaseArticleInfos(baseArticleInfos);
            return baseArticleInfo;
        }
        if ("industry".equals(type)) {
            ResultData resultData = onlineStudyBizService.getOnlineStudyList("industry", pageNum, pageSize);
            Map<String, Object> industryMap = (Map<String, Object>) resultData.getData();
            List<OnlineStudyVo> industryList = (List<OnlineStudyVo>) industryMap.get("studyList");
            BaseArticleInfo baseArticleInfo = new BaseArticleInfo();
            baseArticleInfo.setPageNum(pageNum);
            baseArticleInfo.setPageSize(pageSize);
            baseArticleInfo.setTotalRecods(Long.parseLong(industryMap.get("count").toString()));
            List<BaseArticleInfo> baseArticleInfos = Lists.transform(industryList, new Function<OnlineStudyVo, BaseArticleInfo>() {
                @Override
                public BaseArticleInfo apply(OnlineStudyVo onlineStudyVo) {
                    return new BaseArticleInfo(onlineStudyVo);
                }
            });
            baseArticleInfo.setBaseArticleInfos(baseArticleInfos);
            return baseArticleInfo;
        }
        return null;
    }

    /**
     * 获取详情
     *
     * @param articleId
     * @return
     */
    public OnlineStudyVo getOnlineStudyById(String articleId) {
        return onlineStudyBizService.getById(articleId);
    }

    public Toutiao getToutiaoById(String toutiaoId) {
        return toutiaoBizService.getById(toutiaoId);
    }

    /**
     * 查询用户的头条信息
     *
     * @param userId
     * @param classifys 逗号连接，支持多个classify拼接:如good,new
     * @param pi
     * @return
     */
    public List<Toutiao> listByUser(String userId, String target, String classifys, Paging pi) {
        return toutiaoBizService.listByUser(userId, target, classifys, pi);
    }

    /**
     * 用户删除一条头条信息
     *
     * @param userId
     * @param tag
     * @param toutiaoId
     */
    public void delUserToutiao(String userId, String classify, String toutiaoId) {
        toutiaoBizService.delUserToutiao(userId, classify, toutiaoId);
    }


    /**
     * 删除用户的所有头条信息
     *
     * @param userId
     * @param tag
     */
    public void delAllUserToutiao(String userId, String classifys) {
        toutiaoBizService.delAllUserToutiao(userId, classifys);
    }


    /**
     * 根据主键查询一条头条详情
     *
     * @param toutiaoId
     * @return
     */
    public Toutiao getById(String toutiaoId) {
        return toutiaoBizService.getById(toutiaoId);
    }


    /**
     * 查询未读的消息数
     *
     * @param userId
     * @param tag
     * @return
     */
    public int countUnreaded(String userId, String target, String classifys) {
        return toutiaoBizService.countUnreaded(userId, target, classifys);
    }


    /**
     * 标记一条为已读
     *
     * @param userId
     * @param classify
     */
    public void markAsReaded(String userId, String toutiaoId, String classify) {
        toutiaoBizService.markAsReaded(userId, toutiaoId, classify);
    }

    /**
     * 头条列表
     *
     * @param tag      gg：公告 cx：促销
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ResultData getToutiaoList(String tag, Integer pageNum, Integer pageSize) {
        return toutiaoBizService.getToutiaoList(tag, pageNum, pageSize);
    }
}
