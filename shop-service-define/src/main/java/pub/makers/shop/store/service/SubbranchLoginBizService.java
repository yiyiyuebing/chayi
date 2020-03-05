package pub.makers.shop.store.service;

import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.pojo.SubbranchInfo;
import pub.makers.shop.store.vo.SubbranchVo;

/**
 * 供应商登陆服务
 * @author apple
 *
 */
public interface SubbranchLoginBizService {

	/**
	 * 获取登陆TOKEN
	 * @param subbranchId
	 * @return
	 */
	String getLoginToken(String subbranchId);


	/**
	 * 清除登陆TOKEN
	 * @param subbranchId
	 */
	void cleanLoginToken(String subbranchId);


	/**
	 * token 是否有效
	 * @param token
	 * @return
	 */
	boolean isTokenValid(String token);


	/**
	 * 根据token获取当前登陆用户信息
	 * @param token
	 * @return
	 */
	String getShopIdByToken(String token);

	/**
	 * 加盟商登陆
	 * @param username
	 * @param password
	 * @return
	 */
	SubbranchVo login(String username, String password);


	/**
	 * 加盟商注册
	 * @param svo
	 * @return
	 */
	SubbranchVo  register(SubbranchVo svo);


	SubbranchInfo doRegister(final SubbranchInfo subbranchInfo);


	/**
	 * 获取登录信息
	 * @param shopId
	 * @return
	 */
	SubbranchVo getShopInfo(String shopId);

	/**
	 * 通过手机号查询店铺信息
	 * @param phone
	 * @return
	 */
	Subbranch ShopInfoByMobile(String phone);
}
