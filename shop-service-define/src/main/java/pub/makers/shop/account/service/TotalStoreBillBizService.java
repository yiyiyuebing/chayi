package pub.makers.shop.account.service;

import pub.makers.shop.account.entity.AccStoreBillChangeRecord;

/**
 * Created by dy on 2017/10/7.
 */
public interface TotalStoreBillBizService {
    /**
     * 更新店铺账户
     * @param cr
     */
    void updateStoreBill(AccStoreBillChangeRecord cr);
}
