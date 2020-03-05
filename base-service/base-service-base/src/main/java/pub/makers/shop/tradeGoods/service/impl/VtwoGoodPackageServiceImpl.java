package pub.makers.shop.tradeGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.store.entity.VtwoGoodPackage;
import pub.makers.shop.tradeGoods.dao.VtwoGoodPackageDao;
import pub.makers.shop.tradeGoods.service.VtwoGoodPackageService;

/**
 * Created by dy on 2017/4/14.
 */
@Service
public class VtwoGoodPackageServiceImpl extends BaseCRUDServiceImpl<VtwoGoodPackage, String, VtwoGoodPackageDao> implements VtwoGoodPackageService {

    @Override
    public VtwoGoodPackage selectByBomId(String tradeGoodSkuId) {
        return dao.get(Conds.get().eq("boom_id", tradeGoodSkuId));
    }
}
