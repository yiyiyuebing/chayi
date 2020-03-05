package pub.makers.common.service;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

import pub.makers.shop.base.service.SmsService;

@Service
public class SmsB2bService {

	@Reference(version="1.0.0")
	private SmsService smsService;
	
	public void sendCheckCode(String smsType, String phoneNmuber){
		smsService.sendCheckCode(smsType, phoneNmuber);
	}
	
	public boolean icCodeValid(String smsType, String phoneNmuber, String yzm){
		return smsService.checkCode(smsType, phoneNmuber, yzm);
	}
}
