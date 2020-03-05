package pub.makers.common.exception;

import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.dev.base.exception.BaseRuntimeException;
import com.dev.base.exception.SessionTimeoutException;
import com.dev.base.exception.errorcode.ErrorCodeTool;
import com.dev.base.exception.errorcode.SysErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.lantu.base.util.WebUtil;

import pub.makers.base.exception.ValidateException;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.user.utils.AccountUtils;

public class B2bBaseExceptionHandler implements HandlerExceptionResolver {

	private static Logger logger = LogManager
			.getLogger(B2bBaseExceptionHandler.class);
	private ObjectMapper om = new ObjectMapper();

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		ModelAndView result = new ModelAndView();
		// 只处理登陆超时异常
		if (ex instanceof SessionTimeoutException){
			
			if (WebUtil.isAjaxReq(request)){
				logger.info("ajaxtimeout");
				Map<String,Object> errorInfo = Maps.newHashMap();
				errorInfo.put("errorCode", "10003");
				errorInfo.put("errorMsg", "登陆超时，请重新登陆");
				response.setContentType("text/json");
				result.setViewName("error/ajax");
				result.addAllObjects(errorInfo);

			}
			else {
				logger.debug("跳转到登陆页面");
				
				// 设置登录前的访问地址和参数
				AccountUtils.setRedirectUrl(request);
				
				return new ModelAndView("redirect:/passport/login");
			}
		}
		
		if (logger.isDebugEnabled() && !(ex instanceof SessionTimeoutException)) {
			ex.printStackTrace();
		}
		
		if (WebUtil.isAjaxReq(request)){
			Map<String,Object> errorInfo = parseErrorInfo(ex);
			response.setContentType("text/json");
			result.setViewName("error/ajax");
			result.addAllObjects(errorInfo);

		}
		else {
			response.setContentType("text/html");
			result.setViewName("error/404");
		}
		return result;
	}

	// 组装错误信息
	private Map<String, Object> parseErrorInfo(Exception ex) {
		Map<String, Object> errorInfo = Maps.newHashMap();
		String errorCode;
		String errorMsg;
		if (ex instanceof BaseRuntimeException) {// 自定义异常

			BaseRuntimeException be = (BaseRuntimeException) ex;
			errorCode = be.getErrorCode();
			errorMsg = be.getErrorMsg();
			if (StringUtils.isEmpty(errorMsg)) {
				errorMsg = ErrorCodeTool.get(be.getErrorCode());
			}
		} else if (ex instanceof ValidateException) {
			ValidateException ve = (ValidateException) ex;
			errorCode = ve.getErrorCode();
			errorMsg = ve.getErrorMsg();
		} else {// 其他异常
			errorCode = SysErrorCode.BUSY;
			errorMsg = "系统繁忙";
		}
		
		if (isChinese(errorCode)){
			errorCode = SysErrorCode.BUSY;
		}

		errorInfo.put("errorCode", errorCode);
		errorInfo.put("errorMsg", errorMsg);
		logger.error(String.format("异常码:%s，异常消息:%s", errorCode, errorMsg));

		ResultData result = ResultData.createFail();
		result.setErrorCode(errorCode);
		result.setMsg(errorMsg);
		
		try {
			errorInfo.put("ajaxList", om.writeValueAsString(result));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		errorInfo.put("exception", ex);

		return errorInfo;
	}
	
	private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
 
    // 完整的判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }
 
    // 只能判断部分CJK字符（CJK统一汉字）
    public static boolean isChineseByREG(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str.trim()).find();
    }
 
    // 只能判断部分CJK字符（CJK统一汉字）
    public static boolean isChineseByName(String str) {
        if (str == null) {
            return false;
        }
        // 大小写不同：\\p 表示包含，\\P 表示不包含
        // \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
        String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
        Pattern pattern = Pattern.compile(reg);
        return pattern.matcher(str.trim()).find();
    }

}
