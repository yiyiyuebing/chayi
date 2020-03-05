package pub.makers.shop.tongji.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.tongji.entity.TjShopTotalKpi;

@Repository
public class TjTradeTotalKpiDao extends BaseCRUDDaoImpl<TjShopTotalKpi, String> {
	
	@Override
	protected String getTableName() {
		
		return "tj_trade_total_kpi";
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
