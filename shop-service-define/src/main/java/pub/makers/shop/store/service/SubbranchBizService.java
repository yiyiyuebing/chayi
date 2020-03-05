package pub.makers.shop.store.service;

import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.entity.VtwoStoreRole;
import pub.makers.shop.store.pojo.SubbranchInfo;
import pub.makers.shop.store.vo.SubbranchVo;

import java.math.BigDecimal;
import java.util.Set;

public interface SubbranchBizService {

	/**
	 * 查询商城供货价
	 * @param shopId
	 * @param skuId
	 * @return
	 */
	BigDecimal queryTradeSuppliersPrice(String shopId, String skuId);
	
	/**
	 * 查询采购供货价
	 * TODO 后续这个方法考虑和商城的合并成一个
	 * @param shopId
	 * @param skuId
	 * @return
	 */
	BigDecimal queryPurchaseSuppliersPrice(String shopId, String skuId);
	
	/**
	 * 根据主键查询店铺信息
	 * @param shopId
	 * @return
	 */
	Subbranch getById(String shopId);

	/**
	 * 根据手机号码查询店铺信息
	 * @param mobile
	 * @return
	 */

	Subbranch getByMobile(String mobile);

	/**
	 * 根据店铺mobile查代理商信息
	 * @param mobile
	 *@return
	 */
	VtwoStoreRole getStoreRoleInfo(String mobile);

	/**
	 * 查询店铺经营概况
	 * @param subbranchId
	 */
	ResultData querySummary(String subbranchId);
	
	/**
	 * 获取店铺的所有子账号信息
	 * 包括当前账号
	 * @param subbranchId
	 * @return
	 */
	Set<Long> getChildren(Long subbranchId);

	/**
	 * 根据id查询店铺信息
	 */
	SubbranchVo shopInfo(String id);

	/**
	 * 更新店铺信息
	 */
	void updateShop(SubbranchInfo info);

	/**
	 * 修改密码
	 */
	void updatePassword(String id, String password, String mobile);
}
