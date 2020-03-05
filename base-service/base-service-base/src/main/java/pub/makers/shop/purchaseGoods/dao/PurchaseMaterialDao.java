package pub.makers.shop.purchaseGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.purchaseGoods.entity.PurchaseMaterial;

/**
 * Created by kok on 2017/6/2.
 */
@Repository
public class PurchaseMaterialDao extends BaseCRUDDaoImpl<PurchaseMaterial, String> {
    @Override
    protected String getTableName() {
        return "purchase_material";
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
