package pub.makers.shop.promotion.dao;

import org.springframework.stereotype.Repository;

import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.promotion.entity.Coupon;

@Repository
public class CouponDao extends BaseCRUDDaoImpl<Coupon, String> {
	
	@Override
	protected String getTableName() {
		
		return "sp_coupon";
	}
	
	@Override
	protected String getKeyName() {
		
		return "coupon_id";
	}

	@Override
	protected boolean autoGenerateKey() {
		
		return false;
	}
}
