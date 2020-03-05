package pub.makers.shop.purchaseGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.purchaseGoods.dao.PurchaseGoodEvaluationDao;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodEvaluation;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodEvaluationService;

/**
 * Created by dy on 2017/5/12.
 */
@Service
public class PurchaseGoodEvaluationServiceImpl extends BaseCRUDServiceImpl<PurchaseGoodEvaluation, String, PurchaseGoodEvaluationDao>
        implements PurchaseGoodEvaluationService {

}
