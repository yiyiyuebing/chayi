package pub.makers.shop.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.user.service.CustomServiceAppService;

import java.util.Map;


@Controller
@RequestMapping("customservice")
public class CustomServiceController {

	@Autowired
	private CustomServiceAppService customServiceAppService;
	
	
	/**
	 * 获取当前会话的上下文信息
	 * @param goodId
	 * @param userId
	 * @param shopId
	 * @return
	 */
	@RequestMapping("getContext")
	@ResponseBody
	public ResultData getContext(String goodId ,Long userId ,String shopId){
		
		Map<String, Object> info = customServiceAppService.getContext(goodId, userId, shopId);
		return ResultData.createSuccess(info);
	}
}
