package pub.makers.shop.purchaseGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.purchaseGoods.dao.PurchaseSearchKeywordDao;
import pub.makers.shop.purchaseGoods.entity.PurchaseSearchKeyword;
import pub.makers.shop.purchaseGoods.service.PurchaseSearchKeywordService;

/**
 * Created by kok on 2017/6/23.
 */
@Service
public class PurchaseSearchKeywordServiceImpl extends BaseCRUDServiceImpl<PurchaseSearchKeyword, String, PurchaseSearchKeywordDao> implements PurchaseSearchKeywordService {
}
