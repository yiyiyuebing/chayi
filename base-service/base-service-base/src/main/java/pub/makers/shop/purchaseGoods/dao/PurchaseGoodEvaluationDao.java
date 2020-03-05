package pub.makers.shop.purchaseGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodEvaluation;

@Repository
public class PurchaseGoodEvaluationDao extends BaseCRUDDaoImpl<PurchaseGoodEvaluation, String> {

    @Override
    protected String getTableName() {

        return "purchase_goods_evaluation";
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
