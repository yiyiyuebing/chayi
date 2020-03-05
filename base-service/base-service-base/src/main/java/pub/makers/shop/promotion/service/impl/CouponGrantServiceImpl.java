package pub.makers.shop.promotion.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.promotion.dao.CouponGrantDao;
import pub.makers.shop.promotion.entity.CouponGrant;
import pub.makers.shop.promotion.service.CouponGrantService;

@Service
public class CouponGrantServiceImpl extends BaseCRUDServiceImpl<CouponGrant, String, CouponGrantDao>
										implements CouponGrantService{
	
}
