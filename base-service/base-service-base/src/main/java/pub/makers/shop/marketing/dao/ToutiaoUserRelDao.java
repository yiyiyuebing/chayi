package pub.makers.shop.marketing.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.marketing.entity.ToutiaoUserRel;

@Repository
public class ToutiaoUserRelDao extends BaseCRUDDaoImpl<ToutiaoUserRel, String> {
	
	@Override
	protected String getTableName() {
		
		return "ba_toutiao_user_rel";
	}
	
	@Override
	protected String getKeyName() {
		
		return "rel_id";
	}

	@Override
	protected boolean autoGenerateKey() {
		
		return false;
	}
}
