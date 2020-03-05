package pub.makers.shop.logistics.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;

import pub.makers.shop.base.util.SqlHelper;

@Service(version="1.0.0")
public class CarriageRuleBizServiceImpl implements CarriageRuleBizService{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public BigDecimal calcCarriageByIndentList(Map<Long, BigDecimal> postStat, String regionId) {
		
		BigDecimal result = BigDecimal.ZERO;
		for (Long carriageRuleId : postStat.keySet()){
			
			Map<String, Object> paramMap = Maps.newHashMap();
			paramMap.put("carriageRuleId", carriageRuleId + "");
			paramMap.put("regionId", regionId);
			String money = postStat.get(carriageRuleId).toString();
			paramMap.put("money", postStat.get(carriageRuleId).toString());
//			String stmt = FreeMarkerUtil.getValueFromTpl("sql/getCarriageByRegionId.sql", paramMap);
			String stmt = SqlHelper.getSql("sql/getCarriageByRegionId.sql");
			
			BigDecimal carriage = jdbcTemplate.queryForObject(stmt, BigDecimal.class, money, regionId, carriageRuleId, money, regionId, carriageRuleId, carriageRuleId);
			if (carriage.compareTo(result) > 0){
				result = carriage;
			}
		}
		
		return result;
	}

}
