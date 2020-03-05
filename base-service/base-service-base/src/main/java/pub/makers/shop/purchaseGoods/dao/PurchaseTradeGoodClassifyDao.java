package pub.makers.shop.purchaseGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.purchaseGoods.entity.PurchaseTradeGoodClassify;

/**
 * Created by dy on 2017/5/26.
 */
@Repository
public class PurchaseTradeGoodClassifyDao extends BaseCRUDDaoImpl<PurchaseTradeGoodClassify, String> {
    @Override
    protected String getTableName() {

        return "purchase_classify";
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
