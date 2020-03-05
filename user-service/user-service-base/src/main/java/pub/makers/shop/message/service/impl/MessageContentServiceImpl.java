package pub.makers.shop.message.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.message.dao.MessageContentDao;
import pub.makers.shop.message.entity.MessageContent;
import pub.makers.shop.message.service.MessageContentService;

/**
 * Created by dy on 2017/4/14.
 */
@Service
public class MessageContentServiceImpl extends BaseCRUDServiceImpl<MessageContent, String, MessageContentDao> implements MessageContentService {
}
