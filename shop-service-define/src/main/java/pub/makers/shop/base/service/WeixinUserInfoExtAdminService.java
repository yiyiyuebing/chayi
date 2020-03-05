package pub.makers.shop.base.service;

import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.user.entity.WeixinUserInfoExt;

/**
 * Created by devpc on 2017/7/25.
 */
public interface WeixinUserInfoExtAdminService {

    //增加标签
    boolean addWeixinUserLabel(WeixinUserInfoExt weixinUserInfoExt);

    boolean updateUserLabel(String remarks , String labels , String id);
}
