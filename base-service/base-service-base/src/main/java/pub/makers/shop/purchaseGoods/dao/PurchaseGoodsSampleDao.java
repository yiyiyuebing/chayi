package pub.makers.shop.purchaseGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSample;

/**
 * Created by dy on 2017/4/14.
 */
@Repository
public class PurchaseGoodsSampleDao extends BaseCRUDDaoImpl<PurchaseGoodsSample, String> {
    @Override
    protected String getTableName() {

        return "purchase_goods_sample";
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
