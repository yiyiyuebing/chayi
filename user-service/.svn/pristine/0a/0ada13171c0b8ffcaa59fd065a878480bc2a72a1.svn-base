package pub.makers.shop.browseLog.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.browseLog.entity.GoodsBrowseLog;

/**
 * Created by kok on 2017/6/23.
 */
@Repository
public class TradeGoodsBrowseLogDao extends BaseCRUDDaoImpl<GoodsBrowseLog, String> {
    @Override
    protected String getTableName() {

        return "trade_goods_browse_log";
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
