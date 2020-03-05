package pub.makers.shop.base.service;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

@Service
public class SmsAppService {

	@Reference(version="1.0.0")
	private SmsService smsService;
	
	public void sendSms(String phone, String smsType){
		
		smsService.sendCheckCode(smsType, phone);
	}
	
	public void sendCheckCode(String smsType, String phoneNmuber){
		smsService.sendCheckCode(smsType, phoneNmuber);
	}
	
	public boolean icCodeValid(String smsType, String phoneNmuber, String yzm){
		return smsService.checkCode(smsType, phoneNmuber, yzm);
	}
}
