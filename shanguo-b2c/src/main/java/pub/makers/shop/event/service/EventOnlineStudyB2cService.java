package pub.makers.shop.event.service;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.marketing.entity.OnlineStudy;
import pub.makers.shop.marketing.service.OnlineStudyBizService;

@Service
public class EventOnlineStudyB2cService {

	@Reference(version="1.0.0")
	private OnlineStudyBizService studyBizService;
	
//	public OnlineStudy showArtic(String id, Long userId, String userType){
//		
//		OnlineStudy onlineStudy = studyBizService.getOnlineStudyById(id);
//		ValidateUtils.notNull(onlineStudy, "文章已被删除");
//		
//		// TODO 浏览数+1
//		
//		
//	}
}
