package pub.makers.shop.message.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.message.entity.Message;

/**
 * Created by dy on 2017/4/14.
 */
@Repository
public class MessageDao extends BaseCRUDDaoImpl<Message, String> {
    @Override
    protected String getTableName() {

        return "message";
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
