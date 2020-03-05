package pub.makers.shop.base.service;

import java.util.List;
import java.util.Map;

public interface SmsService {

	/**
	 * 
	 * @name 发送短信内容
	 * @Description
	 * @CreateDate 2015年12月31日下午4:50:55
	 */
	void sendMsgByTpl(String phoneNumber, String tplName, List<String> data);
	
	/**
	 * 发送短信内容
	 * @param phoneNumber
	 * @param tplName
	 * @param data
	 */
	void sendMsgByTpl(String phoneNumber, String tplName, Map<String, Object> data);
	

	/**
	 * 传入短信服务类别，通过手机号码发送短信验证
	 * 
	 * @Title: sendCheckCode
	 * @email 292795112@qq.com
	 * @date 2016年1月8日 下午7:28:46
	 * @version
	 *
	 * @param smsType
	 * @param phoneNmuber
	 *
	 * @return void
	 * @throws
	 */
	void sendCheckCode(String smsType, String phoneNmuber);
	
	/**
	 *  发送输入的验证码
	 * @param smsType
	 * @param phoneNmuber
	 * @param code
	 * @param session
	 */
	void sendCheckCodeWithInput(String smsType, String phoneNmuber, String code);

	/**
	 * 校验所输入的验证码是否在规定的时间内有效，并且正确
	 * 
	 * @Title: checkCode
	 * @email 292795112@qq.com
	 * @date 2016年1月8日 下午8:16:32
	 * @version
	 * 
	 * @param smsType
	 *            短信服务类别
	 * @param phoneNmuber
	 *            手机号码
	 * @param yzm
	 *            输入的验证码
	 * @return
	 *
	 * @return boolean
	 * @throws
	 */
	boolean checkCode(String smsType, String phoneNmuber, String yzm);
	
	
	/**
	 * 发送短信
	 * @param phone
	 * @param content
	 */
	void sendSms(String phone, String content);
	
}
