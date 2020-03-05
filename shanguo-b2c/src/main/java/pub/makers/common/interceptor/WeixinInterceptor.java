package pub.makers.common.interceptor;

import com.alibaba.druid.util.Base64;
import com.dev.base.json.JsonUtils;
import com.google.common.collect.Maps;
import com.lantu.base.constant.CfgConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 微信登陆拦截器
 * @author apple
 *
 */
public class WeixinInterceptor extends HandlerInterceptorAdapter{

	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		String reqUri = request.getRequestURI();

		// 商品中转页面不拦截
		if (reqUri.endsWith("togood.html")){
			return true;
		}

		String openId = ObjectUtils.getDisplayString(request.getSession().getAttribute("wxopenId"));
		if ("T".equals(CfgConstants.getProperties().get("wxdebug"))){
			if (StringUtils.isBlank(openId)){
				openId = request.getParameter("openId");
			}
			request.getSession().setAttribute("wxopenId", "wxdebug");
		}
		
		String flag = request.getParameter("flag");
		String userId = ObjectUtils.getDisplayString(request.getSession().getAttribute("userId"));
		String requestUserId = ObjectUtils.getDisplayString(request.getAttribute("userId"));
		if (StringUtils.isBlank(openId) || StringUtils.isNoneBlank(flag) || StringUtils.isBlank(userId) || (!userId.equals(requestUserId) && StringUtils.isNotBlank(requestUserId))){
			System.out.println("session-userId:"+userId);
			System.out.println("request-userId:"+requestUserId);
			Map paramMap = Maps.newHashMap(request.getParameterMap());
			paramMap.remove("flag");
			String uri = CfgConstants.getProperties().get("server.b2c") + request.getRequestURI();
			paramMap.put("uri", uri);
			String paramStr = JsonUtils.toJson(paramMap);
			System.out.println(paramStr);
			System.out.println("客户端要求强制登陆");
			
			String curUrl = CfgConstants.getProperties().get("server.b2c") + request.getRequestURI() + "?" + paramStr;
			String param = Base64.byteArrayToBase64(curUrl.getBytes());
			response.sendRedirect("/wx/login/auth?param=" + param);
			return false;
		}
		
		return true;
		
	}
	
	
}