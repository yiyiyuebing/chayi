package pub.makers.shop.message.dao;

import org.springframework.stereotype.Repository;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.shop.message.entity.MessageContent;

/**
 * Created by dy on 2017/4/14.
 */
@Repository
public class MessageContentDao extends BaseCRUDDaoImpl<MessageContent, String> {

    @Override
    protected String getTableName() {

        return "message_content";
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
