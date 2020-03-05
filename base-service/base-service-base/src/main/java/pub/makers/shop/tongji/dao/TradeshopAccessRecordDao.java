package pub.makers.shop.tongji.dao;

import org.springframework.stereotype.Repository;

import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.tongji.entity.ShopAccessRecord;

@Repository
public class TradeshopAccessRecordDao extends BaseCRUDDaoImpl<ShopAccessRecord, String> {
	
	@Override
	protected String getTableName() {
		
		return "tj_tradeshop_access_record";
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
