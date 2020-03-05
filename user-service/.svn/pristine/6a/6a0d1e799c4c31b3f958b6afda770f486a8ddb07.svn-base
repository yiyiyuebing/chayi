package pub.makers.shop.message.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;

import pub.makers.shop.base.util.IdGenerator;
import pub.makers.shop.message.entity.MessageContent;
import pub.makers.shop.message.enums.MessageContentStatus;
import pub.makers.shop.message.enums.MessageContentType;

/**
 * Created by dy on 2017/4/17.
 */
@Service(version = "1.0.0")
public class MessageContentBizServiceImpl implements MessageContentBizService {

    @Autowired
    private MessageContentService messageContentService;

    @Override
    public void noticeAdd(Long messageId, Long senderId, String content) {
        MessageContent contentDo = new MessageContent();
        contentDo.setId(IdGenerator.getDefault().nextId());
        contentDo.setMessageId(messageId);
        contentDo.setSendTime(new Date());
        contentDo.setContent(content);
        contentDo.setSenderId(senderId);
        contentDo.setType(MessageContentType.text.getDbData());;
        contentDo.setStatus(MessageContentStatus.no.getDbData());
        messageContentService.insert(contentDo);
    }
}
