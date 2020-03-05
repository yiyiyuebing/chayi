package pub.makers.shop.promotion.service;


import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.promotion.entity.CouponDef;

public interface CouponDefService extends BaseCRUDService<CouponDef>{
	
	/**
	 * 根据优惠券查询优惠券定义
	 * @param couponId
	 * @return
	 */
	CouponDef getByCouponId(String couponId);
	
}
