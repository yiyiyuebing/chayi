package pub.makers.common.interceptor;

import com.lantu.base.constant.CfgConstants;
import com.lantu.base.pay.ali.util.Base64;
import com.lantu.base.util.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		if (!reqUri.startsWith("/sumgotea")) {
			return true;
		}
		String openId = ObjectUtils.getDisplayString(request.getSession().getAttribute("wxopenId"));
		if ("T".equals(CfgConstants.getProperties().get("wxdebug"))){
			if (StringUtils.isBlank(openId)){
				openId = request.getParameter("openId");
			}
			request.getSession().setAttribute("wxopenId", openId);
		}
		
		
		if (StringUtils.isBlank(openId)){
			String paramStr = WebUtil.getParamStr(request);
			if (StringUtils.isBlank(paramStr)){
				paramStr = "redirect=true";
			}
			
			String curUrl = CfgConstants.getProperties().get("server.b2b") + request.getRequestURI() + "?" + paramStr;
			String param = Base64.encode(curUrl.getBytes());
			response.sendRedirect("/wx/login/auth?param=" + param);
			return false;
		}
		
		return true;
		
	}
	
}