package pub.makers.shop.tradeGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.tradeGoods.entity.GoodsBaseLabel;

/**
 * Created by dy on 2017/5/25.
 */
@Repository
public class GoodsBaseLabelDao extends BaseCRUDDaoImpl<GoodsBaseLabel, String> {
    @Override
    protected String getTableName() {

        return "goods_base_label";
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
