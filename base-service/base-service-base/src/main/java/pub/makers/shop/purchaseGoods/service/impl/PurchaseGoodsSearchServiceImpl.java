package pub.makers.shop.purchaseGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.purchaseGoods.dao.PurchaseGoodsSearchDao;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSearch;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSearchService;

/**
 * Created by kok on 2017/6/22.
 */
@Service
public class PurchaseGoodsSearchServiceImpl extends BaseCRUDServiceImpl<PurchaseGoodsSearch, String, PurchaseGoodsSearchDao> implements PurchaseGoodsSearchService {
}
