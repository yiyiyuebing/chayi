package pub.makers.shop.user.service;

import pub.makers.shop.user.entity.WeixinUserInfo;
import pub.makers.shop.user.entity.WeixinUserInfoVo;

/**
 * Created by dy on 2017/5/5.
 */
public interface WeixinUserInfoBizService {

    /**
     * 通过ID获取微信用户信息
     * @param userId
     * @return
     */
    WeixinUserInfo getWxUserById(Long userId);
    
    
    /**
     * 使用微信登陆后的用户信息新增或者更新当前的用户信息
     * @param openId
     * @param userInfo
     * @return
     */
    WeixinUserInfo addOrUpdateUserInfo(String openId, Long storeId, WeixinUserInfoVo userInfo);
}
