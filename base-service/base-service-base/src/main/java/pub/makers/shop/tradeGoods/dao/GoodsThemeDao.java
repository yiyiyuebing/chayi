package pub.makers.shop.tradeGoods.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.tradeGoods.entity.GoodsTheme;

/**
 * Created by kok on 2017/5/27.
 */
@Repository
public class GoodsThemeDao extends BaseCRUDDaoImpl<GoodsTheme, String> {
    @Override
    protected String getTableName() {
        return "vtwo_good_theme";
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
