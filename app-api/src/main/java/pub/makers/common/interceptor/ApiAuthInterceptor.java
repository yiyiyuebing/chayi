package pub.makers.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dev.base.json.JsonUtils;

import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.user.service.AccountAppService;

public class ApiAuthInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	private AccountAppService storeLoginAppService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String uri = request.getRequestURI();
		if (uri.startsWith("/login/") || uri.startsWith("/appapi/login/") || uri.startsWith("/alipay/") || uri.startsWith("/appapi/alipay/")){
			return super.preHandle(request, response, handler);
		}
		
		boolean hasLogin = storeLoginAppService.hasLogin(request);
		if (!hasLogin){
			
			ResultData resultData = ResultData.createFail();
			resultData.setErrorCode("00001");
			response.setContentType("application/json");
			response.getWriter().print(JsonUtils.toJson(resultData));
		}
		
		return hasLogin && super.preHandle(request, response, handler);
	}
	
}