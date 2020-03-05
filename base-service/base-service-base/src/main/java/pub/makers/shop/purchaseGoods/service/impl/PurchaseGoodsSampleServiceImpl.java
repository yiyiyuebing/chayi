package pub.makers.shop.purchaseGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.purchaseGoods.dao.PurchaseGoodsSampleDao;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSample;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSampleService;

import java.util.List;

/**
 * Created by dy on 2017/4/14.
 */
@Service
public class PurchaseGoodsSampleServiceImpl extends BaseCRUDServiceImpl<PurchaseGoodsSample, String, PurchaseGoodsSampleDao> implements PurchaseGoodsSampleService {
    @Override
    public List<PurchaseGoodsSample> selectByPurGoodsId(PurchaseGoodsSample purchaseGoodsSample) {
        return dao.list(Conds.get().eq("purGoodsId", purchaseGoodsSample.getPurGoodsId()));
    }
}
