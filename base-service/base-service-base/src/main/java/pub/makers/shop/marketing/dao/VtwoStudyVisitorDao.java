package pub.makers.shop.marketing.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.BaseCRUDDao;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.marketing.entity.VtwoStudyVisitor;

/**
 * Created by dy on 2017/5/5.
 */
@Repository
public class VtwoStudyVisitorDao extends BaseCRUDDaoImpl<VtwoStudyVisitor, String> {
    @Override
    protected String getTableName() {

        return "vtwo_study_visitor";
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
