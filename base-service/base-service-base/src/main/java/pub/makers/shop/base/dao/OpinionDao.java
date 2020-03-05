package pub.makers.shop.base.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.base.entity.Opinion;

/**
 * Created by Think on 2017/8/25.
 */
@Repository
public class OpinionDao extends BaseCRUDDaoImpl<Opinion, String> {

    @Override
    protected String getTableName() {

        return "opinion";
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
