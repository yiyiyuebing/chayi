package pub.makers.shop.tradeGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.tradeGoods.entity.GoodLabels;

/**
 * Created by dy on 2017/5/25.
 */
@Repository
public class GoodLabelsDao extends BaseCRUDDaoImpl<GoodLabels, String> {

    @Override
    protected String getTableName() {

        return "good_labels";
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
