package pub.makers.shop.purchaseGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSku;

/**
 * Created by kok on 2017/5/10.
 */
@Repository
public class PurchaseGoodsSkuDao extends BaseCRUDDaoImpl<PurchaseGoodsSku, String> {
    @Override
    protected String getTableName() {
        return "purchase_goods_sku";
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
