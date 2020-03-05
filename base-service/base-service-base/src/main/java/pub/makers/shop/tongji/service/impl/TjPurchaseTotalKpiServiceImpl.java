package pub.makers.shop.tongji.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.tongji.dao.TjPurchaseTotalKpiDao;
import pub.makers.shop.tongji.entity.TjShopTotalKpi;
import pub.makers.shop.tongji.service.TjShopTotalKpiService;

@Service
public class TjPurchaseTotalKpiServiceImpl extends BaseCRUDServiceImpl<TjShopTotalKpi, String, TjPurchaseTotalKpiDao>
										implements TjShopTotalKpiService {
	
}
