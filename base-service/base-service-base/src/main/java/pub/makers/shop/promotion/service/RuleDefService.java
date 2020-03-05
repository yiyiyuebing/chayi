package pub.makers.shop.promotion.service;


import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.promotion.entity.RuleDef;

public interface RuleDefService extends BaseCRUDService<RuleDef>{
	
	/**
	 * 根据优惠券id查询优惠券的促销规则
	 * @param couponId
	 * @return
	 */
	RuleDef getByCouponId(String couponId);
}
