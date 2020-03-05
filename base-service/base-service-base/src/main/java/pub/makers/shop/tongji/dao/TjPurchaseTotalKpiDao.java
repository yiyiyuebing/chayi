package pub.makers.shop.tongji.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.tongji.entity.TjShopTotalKpi;

@Repository
public class TjPurchaseTotalKpiDao extends BaseCRUDDaoImpl<TjShopTotalKpi, String> {
	
	@Override
	protected String getTableName() {
		
		return "tj_purchase_total_kpi";
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
