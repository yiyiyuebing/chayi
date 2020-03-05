package pub.makers.shop.marketing.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.marketing.entity.VtwoStudyGoods;

/**
 * Created by dy on 2017/5/3.
 */
@Repository
public class VtwoStudyGoodsDao extends BaseCRUDDaoImpl<VtwoStudyGoods, String> {

    @Override
    protected String getTableName() {

        return "vtwo_study_goods";
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
