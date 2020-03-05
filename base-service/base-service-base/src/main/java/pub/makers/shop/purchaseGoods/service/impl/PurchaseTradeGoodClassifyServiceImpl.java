package pub.makers.shop.purchaseGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.purchaseGoods.dao.PurchaseTradeGoodClassifyDao;
import pub.makers.shop.purchaseGoods.entity.PurchaseTradeGoodClassify;
import pub.makers.shop.purchaseGoods.service.PurchaseTradeGoodClassifyService;

/**
 * Created by dy on 2017/5/26.
 */
@Service
public class PurchaseTradeGoodClassifyServiceImpl extends BaseCRUDServiceImpl<PurchaseTradeGoodClassify, String, PurchaseTradeGoodClassifyDao>
        implements PurchaseTradeGoodClassifyService {

}
