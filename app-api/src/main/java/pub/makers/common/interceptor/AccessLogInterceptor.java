package pub.makers.common.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lantu.base.util.WebUtil;

/**
 * 访问日志拦截器
 * @ClassName: AccessLogInterceptor
 * @Description: TODO
 * @author shiqingju
 * @email jlr_6@foxmail.com
 * @date 2016年2月19日 上午10:31:59
 *
 */
public class AccessLogInterceptor extends HandlerInterceptorAdapter {
	private Logger logger= LoggerFactory.getLogger(this.getClass());

	//需要授权的操作
	private String[] excludeUrls = {"/static/","/favicon.icon"};
	private static final String CMD_START_KEY = "_cmd_start_key";
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String reqUri = request.getRequestURI();
		for (String excludeUrl : excludeUrls){
			
			if (reqUri.startsWith(excludeUrl)){
				return super.preHandle(request, response, handler);
			}
		}
		
		// 设置应用开始处理的时间
		request.setAttribute(CMD_START_KEY, new Date().getTime());
		
		return super.preHandle(request, response, handler);
	}
	

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		String reqUri = request.getRequestURI();
		for (String excludeUrl : excludeUrls){
			
			if (reqUri.startsWith(excludeUrl)){
				super.postHandle(request, response, handler, modelAndView);
				return;
			}
		}
		
		StringBuffer logSb = new StringBuffer();
		Long time = new Date().getTime() - (Long)request.getAttribute(CMD_START_KEY);
		logSb.append(WebUtil.getClientIp(request)).append("\t").append(reqUri).append("\t").append(WebUtil.getParamStr(request)).append("\t").append(time);
		logger.info(logSb.toString());
		
		super.postHandle(request, response, handler, modelAndView);
	}
	
	
}
