package pub.makers.shop.purchaseGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassifyAttr;

/**
 * Created by dy on 2017/4/14.
 */
@Repository
public class PurchaseClassifyAttrDao extends BaseCRUDDaoImpl<PurchaseClassifyAttr, String> {
    @Override
    protected String getTableName() {

        return "purchase_classify_attr";
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
