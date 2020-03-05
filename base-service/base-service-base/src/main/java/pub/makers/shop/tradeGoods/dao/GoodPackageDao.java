package pub.makers.shop.tradeGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.tradeGoods.entity.GoodPackage;

@Repository
public class GoodPackageDao extends BaseCRUDDaoImpl<GoodPackage, String> {
	
	@Override
	protected String getTableName() {
		
		return "vtwo_good_package";
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
