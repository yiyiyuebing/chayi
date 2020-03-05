package pub.makers.shop.user.service;

/**
 * 店铺登陆服务
 * 对应加盟商/联盟商
 * @author apple
 *
 */
public interface StoreLoginBizService {

    /**
     * 登陆
     */
    void login();

    /**
     * 判断登陆token是否有效
     * @param token
     * @return
     */
    Boolean hasLogin(String token);
}
