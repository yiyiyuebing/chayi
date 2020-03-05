package pub.makers.shop.promotion.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.promotion.dao.CouponUseDao;
import pub.makers.shop.promotion.entity.CouponUse;
import pub.makers.shop.promotion.service.CouponUseService;

@Service
public class CouponUseServiceImpl extends BaseCRUDServiceImpl<CouponUse, String, CouponUseDao>
										implements CouponUseService{
	
}
