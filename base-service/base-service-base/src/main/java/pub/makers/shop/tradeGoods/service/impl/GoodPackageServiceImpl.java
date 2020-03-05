package pub.makers.shop.tradeGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.tradeGoods.dao.GoodPackageDao;
import pub.makers.shop.tradeGoods.service.GoodPackageService;
import pub.makers.shop.tradeGoods.entity.GoodPackage;

@Service
public class GoodPackageServiceImpl extends BaseCRUDServiceImpl<GoodPackage, String, GoodPackageDao>
										implements GoodPackageService {

	public void updateSaleNum(String boomId, int num) {
		
		GoodPackage gp = get(Conds.get().eq("boom_id", boomId));
		int onSaleNo = gp.getOnSalesNo();
		int saleNum = gp.getSaleNum();
	}

	public GoodPackage getByBoomId(String boomId) {
		
		return get(Conds.get().eq("boom_id", boomId));
	}
	
}
