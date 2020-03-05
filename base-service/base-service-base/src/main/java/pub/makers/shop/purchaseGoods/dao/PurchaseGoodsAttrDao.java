package pub.makers.shop.purchaseGoods.dao;

import org.springframework.stereotype.Repository;

import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsAttr;

/**
 * Created by kok on 2017/6/5.
 */
@Repository
public class PurchaseGoodsAttrDao extends BaseCRUDDaoImpl<PurchaseGoodsAttr, String> {
    @Override
    protected String getTableName() {
        return "purchase_goods_attr";
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
