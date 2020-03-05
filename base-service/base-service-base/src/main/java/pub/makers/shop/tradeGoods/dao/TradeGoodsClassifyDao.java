package pub.makers.shop.tradeGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.tradeGoods.entity.TradeGoodsClassify;

/**
 * Created by dy on 2017/5/25.
 */
@Repository
public class TradeGoodsClassifyDao extends BaseCRUDDaoImpl<TradeGoodsClassify, String > {

    @Override
    protected String getTableName() {

        return "trade_goods_classify";
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
