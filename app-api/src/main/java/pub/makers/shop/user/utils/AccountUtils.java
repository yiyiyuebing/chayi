package pub.makers.shop.user.utils;

import com.dev.base.utils.SpringContextUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.user.service.AccountAppService;

import javax.servlet.http.HttpServletRequest;

public class AccountUtils {

	private static AccountAppService accountAppService;
	
	public static String getCurrShopId(){
		
		AccountAppService service = getService();
		HttpServletRequest request = getRequest();
		
		return service.getCurrShopId(request);
		
	}

	public static String getCurrStoreLevelId(){

		AccountAppService service = getService();
		HttpServletRequest request = getRequest();

		return service.getCurrStoreLevelId(request);

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
}
