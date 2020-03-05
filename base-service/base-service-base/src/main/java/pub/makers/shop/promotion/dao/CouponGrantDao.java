package pub.makers.shop.promotion.dao;

import org.springframework.stereotype.Repository;

import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.promotion.entity.CouponGrant;

@Repository
public class CouponGrantDao extends BaseCRUDDaoImpl<CouponGrant, String> {
	
	@Override
	protected String getTableName() {
		
		return "sp_coupon_grant";
	}
	
	@Override
	protected String getKeyName() {
		
		return "record_id";
	}

	@Override
	protected boolean autoGenerateKey() {
		
		return false;
	}
}
