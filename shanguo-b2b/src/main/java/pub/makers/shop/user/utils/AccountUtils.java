package pub.makers.shop.user.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dev.base.utils.SpringContextUtils;
import com.lantu.base.util.HttpUtil;

import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.user.service.AccountAppService;

public class AccountUtils {

	private static AccountAppService accountAppService;
	private final static String REDIRECT_URL = "_redirect_url";
	
	public static String getCurrShopId(){
		
		return getCurrShopId(true);
		
	}
	
	public static String getCurrShopId(boolean forceLogin){
		
		AccountAppService service = getService();
		HttpServletRequest request = getRequest();
		
		return service.getCurrShopId(request, forceLogin);
		
	}

	public static String getCurrStoreLevelId(){
		AccountAppService service = getService();
		HttpServletRequest request = getRequest();
		return service.getCurrStoreLevelId(request);

	}

	public static String getCurrShopName(){
		AccountAppService service = getService();
		HttpServletRequest request = getRequest();
		return service.getCurrShopName(request);

	}
	
	private synchronized static AccountAppService getService(){
		if (accountAppService == null){
			accountAppService = SpringContextUtils.getBean(AccountAppService.class);
		}
		
		return accountAppService;
	}
	
	private static HttpServletRequest getRequest(){
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		ValidateUtils.notNull(requestAttributes, "无法获取请求参数");

		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		return request;
	}
	
	/**
	 * 设置跳转到登录页之前的访问地址
	 * @param request
	 */
	public static void setRedirectUrl(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		String redirectUrl = request.getRequestURI() +  "?" + HttpUtil.getParamStr(request);
		session.setAttribute(REDIRECT_URL, redirectUrl);
	}
	
	public static void setOpenId(String openId){
		getRequest().getSession().setAttribute("wxopenId", openId);
	}
	
	public static String getOpenId(){
		
		return ObjectUtils.getDisplayString(getRequest().getSession().getAttribute("wxopenId"));
	}
}
