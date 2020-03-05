package pub.makers.shop.purchaseGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.purchaseGoods.dao.PurchaseGoodsSkuDao;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSku;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuService;

/**
 * Created by kok on 2017/5/10.
 */
@Service
public class PurchaseGoodsSkuServiceImpl extends BaseCRUDServiceImpl<PurchaseGoodsSku, String, PurchaseGoodsSkuDao> implements PurchaseGoodsSkuService{
}
