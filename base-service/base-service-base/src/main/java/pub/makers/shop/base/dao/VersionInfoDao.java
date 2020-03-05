package pub.makers.shop.base.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.base.entity.VersionInfo;

@Repository
public class VersionInfoDao extends BaseCRUDDaoImpl<VersionInfo, String> {
	
	@Override
	protected String getTableName() {
		
		return "ba_version_info";
	}
	
	@Override
	protected String getKeyName() {
		
		return "id";
	}

	@Override
	protected boolean autoGenerateKey() {
		
		return false;
	}
}
