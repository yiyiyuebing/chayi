package pub.makers.shop.base.dao;

import org.springframework.stereotype.Repository;

import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.base.entity.SysDict;

@Repository
public class SysDictDao extends BaseCRUDDaoImpl<SysDict, Long> {
	
	@Override
	protected String getTableName() {
		
		return "sys_dict";
	}
	
	@Override
	protected String getKeyName() {
		
		return "dict_id";
	}

	@Override
	protected boolean autoGenerateKey() {
		
		return true;
	}
}
