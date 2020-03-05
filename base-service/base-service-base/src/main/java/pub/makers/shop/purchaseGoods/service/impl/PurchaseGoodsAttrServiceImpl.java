package pub.makers.shop.purchaseGoods.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.purchaseGoods.dao.PurchaseGoodsAttrDao;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsAttr;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsAttrService;

/**
 * Created by kok on 2017/6/5.
 */
@Service
public class PurchaseGoodsAttrServiceImpl extends BaseCRUDServiceImpl<PurchaseGoodsAttr, String, PurchaseGoodsAttrDao> implements PurchaseGoodsAttrService {
}
