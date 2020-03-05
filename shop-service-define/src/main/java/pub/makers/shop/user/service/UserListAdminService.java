package pub.makers.shop.user.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.store.entity.StoreSubbranchExt;
import pub.makers.shop.store.vo.StoreSubbranceExtVo;
import pub.makers.shop.store.vo.SubbranchVo;
import pub.makers.shop.store.vo.TjStoreSubbranchVo;
import pub.makers.shop.user.vo.UserListPurchaseParam;
import pub.makers.shop.user.vo.UserListTradeParam;
import pub.makers.shop.user.vo.UserListTradeVo;

/**
 * Created by daiwenfa on 2017/7/19.
 */
public interface UserListAdminService {
    ResultList<UserListTradeVo> getUserListTradePageList(UserListTradeParam param, Paging pg);

    UserListTradeVo getUserData(String id);

    ResultList<TjStoreSubbranchVo> getUserListPurchasePageList(UserListPurchaseParam param, Paging pg);

    StoreSubbranceExtVo getStoreSubbranchExt(String id);

    void addOrUpdateStoreSubbranchExt(StoreSubbranchExt storeSubbranchExt, long userId);

    SubbranchVo getData(SubbranchVo sv);
}
