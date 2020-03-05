package pub.makers.shop.message.service;

/**
 * Created by dy on 2017/4/17.
 */
public interface MessageContentBizService {

    void noticeAdd(Long messageId, Long senderId, String content);

}
