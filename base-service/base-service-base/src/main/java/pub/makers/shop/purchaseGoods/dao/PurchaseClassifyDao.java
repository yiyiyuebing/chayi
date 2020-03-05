package pub.makers.shop.purchaseGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassify;

/**
 * Created by dy on 2017/4/14.
 */
@Repository
public class PurchaseClassifyDao extends BaseCRUDDaoImpl<PurchaseClassify, String> {
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
