package pub.makers.shop.purchaseGoods.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.purchaseGoods.entity.PurchaseSearchKeyword;

/**
 * Created by daiwenfa on 2017/6/23.
 */
public interface PurchaseSearchKeywordAdminService {
    ResultList<PurchaseSearchKeyword> getPageList(PurchaseSearchKeyword purchaseSearchKeyword, Paging pg);

    boolean ableOrDisable(String id, String operation, long userId);

    boolean remove(String id);

    PurchaseSearchKeyword getData(String id);

    void addOrUpdate(PurchaseSearchKeyword purchaseSearchKeyword, long userId);
}
