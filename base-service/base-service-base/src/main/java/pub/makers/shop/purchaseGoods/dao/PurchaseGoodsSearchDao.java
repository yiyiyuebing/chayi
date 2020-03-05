package pub.makers.shop.purchaseGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSearch;

/**
 * Created by kok on 2017/6/22.
 */
@Repository
public class PurchaseGoodsSearchDao extends BaseCRUDDaoImpl<PurchaseGoodsSearch, String> {
    @Override
    protected String getTableName() {

        return "purchase_goods_search";
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
