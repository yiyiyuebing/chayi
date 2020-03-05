package pub.makers.shop.purchaseGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.purchaseGoods.entity.PurchaseMaterialAttr;

/**
 * Created by kok on 2017/6/9.
 */
@Repository
public class PurchaseMaterialAttrDao extends BaseCRUDDaoImpl<PurchaseMaterialAttr, String> {
    @Override
    protected String getTableName() {
        return "purchase_material_attr";
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
