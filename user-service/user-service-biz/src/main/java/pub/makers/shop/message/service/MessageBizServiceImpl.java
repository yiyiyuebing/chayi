package pub.makers.shop.message.service;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;

import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.util.IdGenerator;
import pub.makers.shop.message.entity.Message;
import pub.makers.shop.message.enums.MessageType;

/**
 * Created by dy on 2017/4/17.
 */
@Service(version = "1.0.0")
public class MessageBizServiceImpl implements MessageBizService {

	private static Logger logger = LogManager
			.getLogger(MessageBizServiceImpl.class);

    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageContentBizService messageContentBizService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addErrorMsg(String clientId, String storeId, Integer status, String content) {

        Message result = new Message();
        result.setId(IdGenerator.getDefault().nextId());
        result.setClientId(Long.valueOf(clientId));
        result.setStoreId(Long.valueOf(storeId));
        result.setType(MessageType.notice.getDbData());
        result.setStatus(status);
        messageService.insert(result);
        messageContentBizService.noticeAdd(result.getId(), result.getStoreId(), String.format("[异常单]%s", content));

//        websocketManager.send(messageCount());

    }

    @Override
    public void addNoticeMessage(String clientId, String storeId, Integer status, String content) {
    	
    	try{
    		Map<String, Object> data = Maps.newHashMap();
            data.put("status", status);
            data.put("storeId", storeId);
            data.put("clientId", clientId);
            String sql = FreeMarkerHelper.getValueFromTpl("sql/message/queryNoticeCountByMap.sql", data);
            Integer noticeCount = jdbcTemplate.queryForObject(sql, Integer.class);
            if (noticeCount == 0) {
                Message message = new Message();
                message.setId(IdGenerator.getDefault().nextId());
                message.setClientId(Long.valueOf(clientId));
                message.setStoreId(Long.valueOf(storeId));
                message.setType(MessageType.notice.getDbData());
                message.setStatus(status);
                messageService.insert(message);
                messageContentBizService.noticeAdd(message.getId(), message.getClientId(), content);
            }
    	}
        catch (Exception e){
        	logger.error(String.format("发送站内通知消息出错，原因是:[%s]", e.getLocalizedMessage()));
        }
    }


}
