package pub.makers.shop.tradeGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.tradeGoods.entity.GoodsColumn;

/**
 * Created by kok on 2017/5/26.
 */
@Repository
public class GoodsColumnDao extends BaseCRUDDaoImpl<GoodsColumn, String> {
    @Override
    protected String getTableName() {
        return "goods_column";
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
