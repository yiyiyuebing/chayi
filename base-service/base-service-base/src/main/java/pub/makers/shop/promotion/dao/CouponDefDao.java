package pub.makers.shop.promotion.dao;

import org.springframework.stereotype.Repository;

import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.promotion.entity.CouponDef;

@Repository
public class CouponDefDao extends BaseCRUDDaoImpl<CouponDef, String> {
	
	@Override
	protected String getTableName() {
		
		return "sp_coupon_def";
	}
	
	@Override
	protected String getKeyName() {
		
		return "def_id";
	}

	@Override
	protected boolean autoGenerateKey() {
		
		return false;
	}
}
