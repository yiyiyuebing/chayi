package pub.makers.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import pub.makers.base.exception.ValidateUtils;

public class WxUserUtils {

	
	public static String getUserId(){
		
		return null;
	}
	
	private static HttpServletRequest getRequest(){
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		ValidateUtils.notNull(requestAttributes, "无法获取请求参数");

		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		return request;
	}
}
