package pub.makers.shop.tradeGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.tradeGoods.entity.GoodEvaluation;

@Repository
public class GoodEvaluationDao extends BaseCRUDDaoImpl<GoodEvaluation, String> {

    @Override
    protected String getTableName() {

        return "good_evaluation";
    }

    @Override
    protected String getKeyName() {

        return "id";
    }

    @Override
    protected boolean autoGenerateKey() {

        return false;
    }
}
