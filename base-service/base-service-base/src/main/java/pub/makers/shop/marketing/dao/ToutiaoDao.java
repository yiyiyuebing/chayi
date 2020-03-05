package pub.makers.shop.marketing.dao;

import org.springframework.stereotype.Repository;

import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.marketing.entity.Toutiao;

@Repository
public class ToutiaoDao extends BaseCRUDDaoImpl<Toutiao, String> {
	
	@Override
	protected String getTableName() {
		
		return "ba_toutiao";
	}
	
	@Override
	protected String getKeyName() {
		
		return "toutiao_id";
	}

	@Override
	protected boolean autoGenerateKey() {
		
		return false;
	}
}
