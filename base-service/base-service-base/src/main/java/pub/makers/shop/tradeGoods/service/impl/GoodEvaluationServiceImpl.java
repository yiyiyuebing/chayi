package pub.makers.shop.tradeGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.tradeGoods.dao.GoodEvaluationDao;
import pub.makers.shop.tradeGoods.service.GoodEvaluationService;
import pub.makers.shop.tradeGoods.entity.GoodEvaluation;

/**
 * Created by dy on 2017/5/12.
 */
@Service
public class GoodEvaluationServiceImpl extends BaseCRUDServiceImpl<GoodEvaluation, String, GoodEvaluationDao>
        implements GoodEvaluationService {

}
