package pub.makers.shop.base.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.base.entity.MsgTemplate;

/**
 * Created by dy on 2017/5/15.
 */
@Repository
public class MsgTemplateDao extends BaseCRUDDaoImpl<MsgTemplate, String> {

    @Override
    protected String getTableName() {

        return "msg_template";
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
