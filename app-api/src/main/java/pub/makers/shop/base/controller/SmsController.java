package pub.makers.shop.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.service.SmsAppService;
import pub.makers.shop.base.vo.ResultData;

@Controller
@RequestMapping("/mobile/sms")
public class SmsController {

	@Autowired
	private SmsAppService smsAppService;
	
	
	/**
	 * 发送短信
	 * {/mobile/sms/send}
	 * @param phone
	 * @param smsType[绑定银行卡:bankcard]
	 * @return
	 */
	@RequestMapping("send")
	@ResponseBody
	public ResultData sendSms(String phone, String smsType){
		
		ValidateUtils.notNull(phone, "手机号码不能为空");
		ValidateUtils.notNull(smsType, "短信类型不能为空");
		
		smsAppService.sendSms(phone, smsType);
		
		return ResultData.createSuccess();
	}
}
