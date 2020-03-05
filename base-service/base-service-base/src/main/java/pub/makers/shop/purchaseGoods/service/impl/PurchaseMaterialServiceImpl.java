package pub.makers.shop.purchaseGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.purchaseGoods.dao.PurchaseMaterialDao;
import pub.makers.shop.purchaseGoods.entity.PurchaseMaterial;
import pub.makers.shop.purchaseGoods.service.PurchaseMaterialService;

/**
 * Created by kok on 2017/6/2.
 */
@Service
public class PurchaseMaterialServiceImpl extends BaseCRUDServiceImpl<PurchaseMaterial, String, PurchaseMaterialDao> implements PurchaseMaterialService {
}
