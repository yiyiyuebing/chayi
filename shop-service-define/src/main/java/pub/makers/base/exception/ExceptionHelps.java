package pub.makers.base.exception;

import java.util.HashMap;
import java.util.Map;

public class ExceptionHelps {

	public static Map<String, Object> processError(Exception e, String code, String msg){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("code", code);
		if (e instanceof ValidateException){
			
			resultMap.put("msg", ((ValidateException) e).getErrorMsg());
		}
		else {
			resultMap.put("msg", msg);
		}
		
		return resultMap;
		
	}
}
