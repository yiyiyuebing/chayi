package pub.makers.shop.promotion.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.dev.base.utils.UUIDUtils;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.promotion.dao.CouponDao;
import pub.makers.shop.promotion.entity.Coupon;
import pub.makers.shop.promotion.entity.CouponUse;
import pub.makers.shop.promotion.entity.RuleDef;
import pub.makers.shop.promotion.entity.RuleInt;
import pub.makers.shop.promotion.service.CouponService;
import pub.makers.shop.promotion.service.CouponUseService;
import pub.makers.shop.promotion.service.RuleDefService;
import pub.makers.shop.promotion.service.RuleIntService;

@Service
public class CouponServiceImpl extends BaseCRUDServiceImpl<Coupon, String, CouponDao>
										implements CouponService{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private RuleDefService defService;
	@Autowired
	private RuleIntService intService;
	@Autowired
	private CouponUseService couponUseService;
	
	@Override
	public List<Coupon> findUserAvailable(String userId) {
		
		// 查询用户当前可用的优惠券
		List<Coupon> couponList = jdbcTemplate.query("select a.*, b.rule_id from sp_coupon a, sp_coupon_def b "
				+ "where a.def_id = b.def_id "
				+ "and a.is_valid = 'T' and a.user_id = ? "
				+ "and b.is_valid = 'T' and b.start_time <= now() and b.end_time >= now()", new BeanPropertyRowMapper<Coupon>(Coupon.class), userId);
		
		// 查询优惠券对应的规则定义
		List<RuleDef> defList = defService.list(Conds.get().in("ruleId", ListUtils.getIdSet(couponList, "ruleId")));
		
		// 查询规则利益
		List<RuleInt> intList = intService.list(Conds.get().in("ruleId", ListUtils.getIdSet(couponList, "ruleId")));
		ListUtils.joinMulit(defList, intList, "ruleId", "ruleId", "ruleIntList");
		
		ListUtils.join(couponList, defList, "ruleId", "ruleId", "ruleDef");
		
		return couponList;
	}

	@Override
	public Coupon getUserCoupon(String userId, String couponId) {
		
		return get(Conds.get().eq("userId", userId).eq("couponId", couponId));
		
	}

	@Override
	public void useCoupon(String couponId, String userId, String orderId, BigDecimal num) {
		
		// 优惠券数量减少
		jdbcTemplate.update("update sp_coupon set num = num - ? where coupon_id = ?", num.doubleValue(), couponId);
		
		// 记录优惠券使用记录
		CouponUse cu = new CouponUse();
		cu.setCouponId(couponId);
		cu.setDelFlag(BoolType.F.name());
		cu.setIsValid(BoolType.T.name());
		cu.setNum(BigDecimal.ZERO.subtract(num));
		cu.setOrderId(orderId);
		cu.setRecordId(UUIDUtils.uuid3());
		cu.setUserId(userId);
		cu.setUseTime(new Date());
		
		couponUseService.insert(cu);
	}
	
}
