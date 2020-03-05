package pub.makers.shop.favorite.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.favorite.entity.Favorite;

/**
 * Created by dy on 2017/6/19.
 */
@Repository
public class TradeFavoriteDao extends BaseCRUDDaoImpl<Favorite, String> {
    @Override
    protected String getTableName() {

        return "shopping_favorite";
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
