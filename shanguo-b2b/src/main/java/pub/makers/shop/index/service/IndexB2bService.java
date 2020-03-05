package pub.makers.shop.index.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.index.entity.IndexAdImages;
import pub.makers.shop.index.entity.IndexFloorKeyword;
import pub.makers.shop.index.entity.IndexModule;
import pub.makers.shop.index.enums.IndexAdPlatform;
import pub.makers.shop.index.enums.IndexAdPost;
import pub.makers.shop.index.enums.IndexModuleType;
import pub.makers.shop.index.enums.KeywordPostCode;
import pub.makers.shop.index.pojo.IndexAdImagesQuery;
import pub.makers.shop.index.vo.BaseArticleInfo;
import pub.makers.shop.index.vo.IndentMobileModuleClassifyVo;
import pub.makers.shop.index.vo.IndentMobileModuleVo;
import pub.makers.shop.index.vo.IndexModuleGoodVo;
import pub.makers.shop.marketing.entity.Toutiao;
import pub.makers.shop.marketing.service.OnlineStudyBizService;
import pub.makers.shop.marketing.service.ToutiaoBizService;
import pub.makers.shop.marketing.vo.OnlineStudyVo;
import pub.makers.shop.purchaseGoods.entity.PurchaseSearchKeyword;
import pub.makers.shop.purchaseGoods.pojo.PurchaseGoodsQuery;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseSearchKeywordBizService;

import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/6/5.
 */
@Service
public class IndexB2bService {


    @Reference(version = "1.0.0")
    private OnlineStudyBizService onlineStudyBizService;
    @Reference(version = "1.0.0")
    private ToutiaoBizService toutiaoBizService;
    @Reference(version = "1.0.0")
    private IndexModuleBizService indexModuleBizService;
    @Reference(version = "1.0.0")
    private PurchaseSearchKeywordBizService purchaseSearchKeywordBizService;
    @Reference(version = "1.0.0")
    private PurchaseGoodsBizService purchaseGoodsBizService;


    public List<PurchaseSearchKeyword> getKeywordList(Integer limit, IndexAdPlatform platform) {
        return purchaseSearchKeywordBizService.getKeywordList(limit, platform);
    }

    public List<BaseArticleInfo> indexOnlineInfoList() {

        List<IndexAdImages> indexAdImageses = indexModuleBizService.getIndexAdImagesList(new IndexAdImagesQuery(IndexAdPost.notice, IndexAdPlatform.pc, null, 3));

        ResultData resultData = toutiaoBizService.getToutiaoList("gg", 1, 7);
        Map<String, Object> toutiaoMap = (Map<String, Object>) resultData.getData();
        ResultData mediaResultData = onlineStudyBizService.getOnlineStudyList("media", 1, 7);
        ResultData industryResultData = onlineStudyBizService.getOnlineStudyList("industry", 1, 7);
        Map<String, Object> mediaOnlineStudyVoMap = (Map<String, Object>) mediaResultData.getData();
        Map<String, Object> industryOnlineStudyVoMap = (Map<String, Object>) industryResultData.getData();

        List<BaseArticleInfo> baseArticleInfos = Lists.newArrayList();

        BaseArticleInfo toutiaoArticleInfo = new BaseArticleInfo();
        toutiaoArticleInfo.setTitle(toutiaoMap.get("tag").toString());
        toutiaoArticleInfo.setArticleType("gonggao");
        List<Toutiao> toutiaoList = (List<Toutiao>) toutiaoMap.get("toutiaoList");
        List<BaseArticleInfo> toutiaoArticleInfos = Lists.transform(toutiaoList, new Function<Toutiao, BaseArticleInfo>() {
            @Override
            public BaseArticleInfo apply(Toutiao toutiao) {
                return new BaseArticleInfo(toutiao);
            }
        });

        for (IndexAdImages indexAdImagese : indexAdImageses) {
            if ("proclamation".equals(indexAdImagese.getNoticeType())) {
                toutiaoArticleInfo.setIndexAdImages(indexAdImagese);
                break;
            }
        }

        toutiaoArticleInfo.setIndexAdImages(indexAdImageses.size() > 0 ? indexAdImageses.get(0) : null);
        toutiaoArticleInfo.setBaseArticleInfos(toutiaoArticleInfos);
        baseArticleInfos.add(toutiaoArticleInfo);


        BaseArticleInfo baseArticleInfo = new BaseArticleInfo();
        baseArticleInfo.setArticleType("media");
        baseArticleInfo.setTitle(mediaOnlineStudyVoMap.get("type").toString());
        List<OnlineStudyVo> mediaOnlineStudyVoList = (List<OnlineStudyVo>) mediaOnlineStudyVoMap.get("studyList");
        List<BaseArticleInfo> mediaArticleInfos = Lists.transform(mediaOnlineStudyVoList, new Function<OnlineStudyVo, BaseArticleInfo>() {
            @Override
            public BaseArticleInfo apply(OnlineStudyVo onlineStudyVo) {
                return new BaseArticleInfo(onlineStudyVo);
            }
        });

        for (IndexAdImages indexAdImagese : indexAdImageses) {
            if ("media".equals(indexAdImagese.getNoticeType())) {
                baseArticleInfo.setIndexAdImages(indexAdImagese);
                break;
            }
        }

        baseArticleInfo.setBaseArticleInfos(mediaArticleInfos);
        baseArticleInfos.add(baseArticleInfo);

        BaseArticleInfo industryArticleInfo = new BaseArticleInfo();
        industryArticleInfo.setArticleType("industry");
//        industryArticleInfo.setTitle(industryOnlineStudyVoMap.get("type").toString());
        industryArticleInfo.setTitle("行业资讯");
        List<OnlineStudyVo> industryOnlineStudyVoList = (List<OnlineStudyVo>) industryOnlineStudyVoMap.get("studyList");
        List<BaseArticleInfo> industryArticleInfos = Lists.transform(industryOnlineStudyVoList, new Function<OnlineStudyVo, BaseArticleInfo>() {
            @Override
            public BaseArticleInfo apply(OnlineStudyVo onlineStudyVo) {
                return new BaseArticleInfo(onlineStudyVo);
            }
        });
        for (IndexAdImages indexAdImagese : indexAdImageses) {
            if ("information".equals(indexAdImagese.getNoticeType())) {
                industryArticleInfo.setIndexAdImages(indexAdImagese);
                break;
            }
        }
        industryArticleInfo.setBaseArticleInfos(industryArticleInfos);
        baseArticleInfos.add(industryArticleInfo);

        return baseArticleInfos;
    }

    /**
     * 获取首页广告列表数据
     * @return
     */
    public List<IndexAdImages> getIndexAdImages(IndexAdPost post, String classifyId, Integer limit, IndexAdPlatform platform) {
        return indexModuleBizService.getIndexAdImagesList(new IndexAdImagesQuery(post, platform, classifyId, limit));
    }

    public ResultData indexModuleInfo(String type, String storeLevelId, Integer limit) {
        List<IndexModule> indexModules = Lists.newArrayList();
        if (type.equals(IndexModuleType.re.toString())) {
            indexModules = indexModuleBizService.getIndexModuleList(IndexModuleType.re, storeLevelId);
            for (IndexModule indexModule : indexModules) {
                List<IndexModuleGoodVo> indexModuleGoodVos = Lists.newArrayList();
                indexModuleGoodVos = indexModuleBizService.getGoodListByModule(indexModule.getId(), storeLevelId);
                indexModule.setIndexModuleGoodVoList(indexModuleGoodVos);
            }
        } else if (type.equals(IndexModuleType.floor.toString())) {
            indexModules = indexModuleBizService.getIndexModuleList(IndexModuleType.floor, storeLevelId);
            List<IndexAdImages> indexAdImageses = indexModuleBizService.getIndexAdImagesList(new IndexAdImagesQuery(IndexAdPost.floorad, IndexAdPlatform.pc, null, 3));
            int i = 0;
            for (IndexModule indexModule : indexModules) {
                List<IndexModuleGoodVo> indexModuleGoodVos = Lists.newArrayList();
                indexModuleGoodVos = indexModuleBizService.getGoodListByModule(indexModule.getId(), storeLevelId);
                indexModule.setIndexModuleGoodVoList(indexModuleGoodVos);
                List<IndexFloorKeyword> indexFloorKeywords = Lists.newArrayList();
                indexFloorKeywords = indexModuleBizService.getKeywordListByModule(indexModule.getId(), KeywordPostCode.adkey ,8);
                indexModule.setFloorKeywords(indexFloorKeywords);
                indexModule.setFloorTopKeywords(indexModuleBizService.getKeywordListByModule(indexModule.getId(), KeywordPostCode.rtkey , 5));
                indexModule.setIndexAdImages(indexAdImageses.size() > i ? indexAdImageses.get(i) : null);
                i++;
            }
        } else {
            return ResultData.createFail("楼层类型错误");
        }
        return ResultData.createSuccess(indexModules);
    }

    public List<IndentMobileModuleVo> getIndexFloorList(String storeLevelId) {
        // 楼层
        List<IndentMobileModuleVo> moduleVoList = indexModuleBizService.getMobileIndexModuleList(storeLevelId);
        List<String> moduleIdList = Lists.newArrayList();
        for (IndentMobileModuleVo moduleVo : moduleVoList) {
            moduleIdList.add(moduleVo.getId());
        }
        // 分类
        Map<String, List<IndentMobileModuleClassifyVo>> classifyVoMap = indexModuleBizService.getClassifyListByModule(moduleIdList, storeLevelId);
        for (IndentMobileModuleVo moduleVo : moduleVoList) {
            moduleVo.setClassifyList(classifyVoMap.get(moduleVo.getId()));
            // 商品
            PurchaseGoodsQuery query = new PurchaseGoodsQuery();
            query.setClassifyIds(moduleVo.getClassifyId());
            query.setStoreLevelId(storeLevelId);
            query.setPageNum(1);
            query.setPageSize(3);
            query.setSaleNumSort("0");
            List<BaseGoodVo> goodVoList = purchaseGoodsBizService.getSearchGoodsList(query);
            moduleVo.setGoodList(goodVoList);
        }
        return moduleVoList;
    }
}
