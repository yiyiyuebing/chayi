package pub.makers.shop.purchaseGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.purchaseGoods.entity.PurchaseSearchKeyword;

/**
 * Created by kok on 2017/6/23.
 */
@Repository
public class PurchaseSearchKeywordDao extends BaseCRUDDaoImpl<PurchaseSearchKeyword, String> {
    @Override
    protected String getTableName() {

        return "purchase_search_keyword";
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
