package pub.makers.shop.purchaseGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.purchaseGoods.dao.PurchaseClassifyAttrDao;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassifyAttr;
import pub.makers.shop.purchaseGoods.service.PurchaseClassifyAttrService;

/**
 * Created by dy on 2017/4/14.
 */
@Service
public class PurchaseClassifyAttrServiceImpl extends BaseCRUDServiceImpl<PurchaseClassifyAttr, String, PurchaseClassifyAttrDao> implements PurchaseClassifyAttrService {
}
