package pub.makers.shop.purchaseGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.purchaseGoods.dao.PurchaseMaterialAttrDao;
import pub.makers.shop.purchaseGoods.entity.PurchaseMaterialAttr;
import pub.makers.shop.purchaseGoods.service.PurchaseMaterialAttrService;

/**
 * Created by kok on 2017/6/9.
 */
@Service
public class PurchaseMaterialAttrServiceImpl extends BaseCRUDServiceImpl<PurchaseMaterialAttr, String, PurchaseMaterialAttrDao> implements PurchaseMaterialAttrService {
}
