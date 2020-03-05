package pub.makers.shop.marketing.dao;

import org.springframework.stereotype.Repository;

import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.marketing.entity.OnlineStudy;

@Repository
public class OnlineStudyDao extends BaseCRUDDaoImpl<OnlineStudy, String> {
	
	@Override
	protected String getTableName() {
		
		return "event_online_study";
	}
	
	@Override
	protected String getKeyName() {
		
		return "ID";
	}

	@Override
	protected boolean autoGenerateKey() {
		
		return false;
	}
}
