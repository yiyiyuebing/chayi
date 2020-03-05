package pub.makers.shop.store.service;

import pub.makers.shop.store.entity.VtwoGoodPackage;

/**
 * Created by dy on 2017/6/24.
 */
public interface VtwoGoodPackageBizService {

    /**
     * @param boomId
     * @return
     */
    VtwoGoodPackage getGoodPackageByBoomId(String boomId);

}
