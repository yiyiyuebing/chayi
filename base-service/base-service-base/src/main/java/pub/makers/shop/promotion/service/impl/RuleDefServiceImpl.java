package pub.makers.shop.promotion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.promotion.dao.RuleDefDao;
import pub.makers.shop.promotion.entity.RuleDef;
import pub.makers.shop.promotion.entity.RuleInt;
import pub.makers.shop.promotion.service.RuleDefService;
import pub.makers.shop.promotion.service.RuleIntService;

@Service
public class RuleDefServiceImpl extends BaseCRUDServiceImpl<RuleDef, String, RuleDefDao>
										implements RuleDefService{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private RuleIntService riService;
	
	@Override
	public RuleDef getByCouponId(String couponId) {
		
		RuleDef def = 
				jdbcTemplate.queryForObject("select * from sp_rule_def "
											+ "where rule_id = "
												+ "(select rule_id from sp_coupon_def "
												+ "where def_id = "
												+ "(select def_id from sp_coupon where coupon_id = ?))", 
												new BeanPropertyRowMapper<RuleDef>(RuleDef.class), couponId);
		
		List<RuleInt> riList = riService.list(Conds.get().eq("ruleId", def.getRuleId()));
		def.setRuleIntList(riList);
		
		return def;
	}
	
}
