package pub.makers.shop.store.service;

import pub.makers.shop.store.vo.SubbranchVo;

/**
 * Created by dy on 2017/9/7.
 */
public interface SubbranchMgrBizService {
    /**
     * 获取店铺信息
     * @param storeId
     * @return
     */
    SubbranchVo getShopInfo(String storeId);
}
