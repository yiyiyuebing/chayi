package pub.makers.shop.promotion.service;


import java.math.BigDecimal;
import java.util.List;

import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.promotion.entity.Coupon;

public interface CouponService extends BaseCRUDService<Coupon>{
	
	
	/**
	 * 查询用户可用的优惠券
	 * @param userId
	 * @return
	 */
	List<Coupon> findUserAvailable(String userId);
	
	
	/**
	 * 获取用户优惠券
	 * @param userId
	 * @return
	 */
	Coupon getUserCoupon(String userId, String couponId);

	
	/**
	 * 使用优惠券并记录使用记录
	 * @param couponId
	 * @param userId
	 * @param orderId
	 * @param num
	 */
	void useCoupon(String couponId, String userId, String orderId, BigDecimal num);
	
}
