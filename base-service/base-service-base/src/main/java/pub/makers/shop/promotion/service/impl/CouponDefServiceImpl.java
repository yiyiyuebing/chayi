package pub.makers.shop.promotion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.promotion.dao.CouponDefDao;
import pub.makers.shop.promotion.entity.CouponDef;
import pub.makers.shop.promotion.service.CouponDefService;

@Service
public class CouponDefServiceImpl extends BaseCRUDServiceImpl<CouponDef, String, CouponDefDao>
										implements CouponDefService{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public CouponDef getByCouponId(String couponId) {
		
		return jdbcTemplate.queryForObject("select * from sp_coupon_def where def_id = (select def_id from sp_coupon where coupon_id = ?)", 
				new BeanPropertyRowMapper<CouponDef>(CouponDef.class), couponId);
	}
	
}
