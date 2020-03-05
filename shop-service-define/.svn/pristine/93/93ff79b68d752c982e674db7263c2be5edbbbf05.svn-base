package pub.makers.shop.cargo.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.cargo.entity.GoodPageTpl;
import pub.makers.shop.cargo.entity.GoodPageTplApply;
import pub.makers.shop.cargo.entity.GoodPageTplModel;
import pub.makers.shop.cargo.entity.GoodPageTplPost;
import pub.makers.shop.cargo.entity.vo.GoodPageParam;
import pub.makers.shop.cargo.entity.vo.GoodPageTplVo;
import pub.makers.shop.cargo.vo.GoodPageTplModelVo;
import pub.makers.shop.tradeGoods.vo.TradeGoodRelevanceParam;
import pub.makers.shop.tradeGoods.vo.TradeGoodRelevanceVo;

import java.util.List;

/**
 * Created by daiwenfa on 2017/6/7.
 */
public interface GoodPageTplMgrBizService {
    ResultList<GoodPageTplVo> getPageList(GoodPageParam goodPageParam, Paging pg);

    boolean ableOrDisable(String id, String operation, long userId);

    boolean remove(String id);

    String getAllClassifyId(String parentId,String goodType, String applyType);

    ResultList<TradeGoodRelevanceVo> getTradeGoodRelevanceListPage(TradeGoodRelevanceParam param, Paging pg);

    int countRelevanceNum(String id, String type);

    boolean relevanceOrCancel(TradeGoodRelevanceParam param, long userId);

    boolean setApply(GoodPageTplApply goodPageTplApply);

    void addOrUpdate(GoodPageTpl goodPageTpl,Long userId);

    GoodPageTpl getGoodPageTplData(String id);

    List<GoodPageTplPost> getGoodPageTplPostData(String type);

    GoodPageTplApply getGoodPageTplApplyData(String id);

    ResultList<TradeGoodRelevanceVo> getLinkPage(TradeGoodRelevanceParam param, Paging pg);

    ResultList<GoodPageTplModelVo> getBottomGoodPagePageList(GoodPageTplModel param, Paging pg);
}
