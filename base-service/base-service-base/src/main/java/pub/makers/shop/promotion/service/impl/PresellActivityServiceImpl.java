package pub.makers.shop.promotion.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.promotion.dao.PresellActivityDao;
import pub.makers.shop.promotion.entity.PresellActivity;
import pub.makers.shop.promotion.entity.PresellGood;
import pub.makers.shop.promotion.service.PresellActivityService;

@Service
public class PresellActivityServiceImpl extends BaseCRUDServiceImpl<PresellActivity, String, PresellActivityDao>
										implements PresellActivityService{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String queryGoodStmt = "select * from sp_presell_good "
			+ "where sku_id = ? and is_valid = 'T' and del_flag = 'F' "
			+ "and activity_id in (select id from sp_presell_activity where is_valid = 'T' and del_flag = 'F' and presell_start < now() and presell_end > now())";
	
	@Override
	public PresellGood getValidGoodBySkuid(String skuId) {
		
		PresellGood good = ListUtils.getSingle(jdbcTemplate.query(queryGoodStmt, new BeanPropertyRowMapper<PresellGood>(PresellGood.class), skuId));
		if (good != null){
			PresellActivity activity = getById(good.getActivityId());
			good.setActivity(activity);
		}
		
		return good;
	}

	@Override
	public PresellActivity getValidActivityBySkuid(String skuId) {
		
		Date now = new Date();
		return get(Conds.get().eq("is_valid", BoolType.T.name()).eq("del_flag", BoolType.F.name()).lt("presell_start", now).gt("presell_end", now));
	}
	
}
