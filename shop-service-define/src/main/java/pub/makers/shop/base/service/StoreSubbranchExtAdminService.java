package pub.makers.shop.base.service;

import pub.makers.shop.store.entity.StoreSubbranchExt;
import pub.makers.shop.user.entity.WeixinUserInfoExt;

/**
 * Created by devpc on 2017/7/25.
 */
public interface StoreSubbranchExtAdminService {

    //增加标签
    boolean addWeixinUserLabel(StoreSubbranchExt storeSubbranchExt);

    boolean updateUserLabel(String remarks, String labels, String id);
}
