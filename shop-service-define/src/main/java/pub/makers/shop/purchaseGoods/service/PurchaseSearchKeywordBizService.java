package pub.makers.shop.purchaseGoods.service;

import pub.makers.shop.index.enums.IndexAdPlatform;
import pub.makers.shop.purchaseGoods.entity.PurchaseSearchKeyword;

import java.util.List;

/**
 * Created by kok on 2017/6/23.
 */
public interface PurchaseSearchKeywordBizService {
    /**
     * 搜索关键词列表
     */
    List<PurchaseSearchKeyword> getKeywordList(Integer limit, IndexAdPlatform platform);
}
