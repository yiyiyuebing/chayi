package pub.makers.shop.message.service;

/**
 * Created by dy on 2017/4/17.
 */
public interface MessageBizService {
    void addErrorMsg(String clientId, String storeId, Integer status, String content);

    void addNoticeMessage(String clientId, String storeId,Integer status, String content);
}
