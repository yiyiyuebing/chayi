package pub.makers.shop.common;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import com.lantu.base.pay.weixin.constant.WeixinPayConstants;
import com.lantu.base.pay.weixin.constant.WxPayConfig;
import com.lantu.base.pay.weixin.utils.WeixinUtil;
import com.lantu.base.util.UUIDUtil;

import java.util.Date;
import java.util.Map;

@Service(version="1.0.0")
public class WxpayBizServiceImpl implements WxpayBizService{

	@Override
	public Map<String, Object> jsPaySign(String channel, String prepayid) {
		
		WxPayConfig cfg = WeixinPayConstants.getPayConfig(channel);
		
		String nonstr = UUIDUtil.getUUID();
		Long ts = new Date().getTime() / 1000;
		String sign = WeixinUtil.sign(prepayid, nonstr, ts, channel);
		
		Map<String, Object> resultMap = Maps.newHashMap();
		resultMap.put("appId", cfg.getAppId());
		resultMap.put("timeStamp", ts + "");
		resultMap.put("nonceStr", nonstr);
		resultMap.put("package", String.format("prepay_id=%s", prepayid));
		resultMap.put("signType", "MD5");
		resultMap.put("paySign", sign);
		
		return resultMap;
	}
	
	public Map<String, Object> appPaySign(String prepayid) {
		
		String channel = "appPay";
		WxPayConfig cfg = WeixinPayConstants.getPayConfig(channel);
		
		String nonstr = UUIDUtil.getUUID();
		Long ts = new Date().getTime() / 1000;
		
		String sign = WeixinUtil.appSign(prepayid, nonstr, ts);
		
		Map<String, Object> resultMap = Maps.newHashMap();
    	resultMap.put("partnerId", cfg.getMchId());
    	resultMap.put("package", "Sign=WXPay");
		resultMap.put("timeStamp", ts + "");
		resultMap.put("nonceStr", nonstr);
		resultMap.put("sign", sign);
		resultMap.put("prepayId", prepayid);
		resultMap.put("appId", cfg.getAppId());
		
		return resultMap;
	}

}
