package pub.makers.common.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.Base64;
import com.dev.base.json.JsonUtils;
import com.lantu.base.constant.CfgConstants;

import pub.makers.common.utils.WxUserUtils;

/**
 * @Title: WeixinInterceptor.java
 * @Package com.club.web.mobile.interceptors
 * @Description: TODO(微信拦截器)
 * @author 柳伟军
 * @date 2016年4月23日 上午11:34:06
 * @version V1.0
 */

public class WeixinInterceptor2 implements HandlerInterceptor {

	Logger logger = LoggerFactory.getLogger(getClass());
	private Map<String, String> properties = CfgConstants.getProperties();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 如果用户已经登录,则无需拦截
		if (StringUtils.isNotBlank(WxUserUtils.getUserId())){
			return true;
		}
		
		logger.info("微信地址拦截 url : {}", request.getRequestURI());
		logger.info("微信地址拦截 model : {}", request.getMethod());

		String uri = request.getRequestURI();
		String storeId = request.getParameter("storeId");
		String id = request.getParameter("id");
		if(StringUtils.isBlank(storeId)){
			storeId = "0";
		}
		
		if(StringUtils.isBlank(id)){
            id = "0";
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("uri", uri);
		paramMap.put("storeId", storeId);
		paramMap.put("id", id);
		String strParam = JsonUtils.toJson(paramMap);

		logger.info("微信地址拦截 参数 : {}", strParam);
		String encodeParam = Base64.byteArrayToBase64(strParam.getBytes());
		
		String redirectUrl = 
				String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s?param=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect", 
							  properties.get("wx.appid"), properties.get("wx.logincallback"), encodeParam);
		response.sendRedirect(redirectUrl);
		
		return false;
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
