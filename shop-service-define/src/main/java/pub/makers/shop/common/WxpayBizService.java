package pub.makers.shop.common;

import java.util.Map;

public interface WxpayBizService {

	
	/**
	 * 获取JS支付信息
	 * @param prepayid
	 * @return
	 */
	Map<String, Object> jsPaySign(String channel, String prepayid);
	
	
	/**
	 * 获取APP支付信息
	 * @param prepayid
	 * @return
	 */
	Map<String, Object> appPaySign(String prepayid);
}
