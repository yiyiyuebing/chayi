package pub.makers.shop.tradeGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.tradeGoods.entity.TradeHeadStore;

/**
 * Created by dy on 2017/4/15.
 */
@Repository
public class TradeHeadStoreDao extends BaseCRUDDaoImpl<TradeHeadStore, String> {
    @Override
    protected String getTableName() {

        return "trade_head_store";
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
