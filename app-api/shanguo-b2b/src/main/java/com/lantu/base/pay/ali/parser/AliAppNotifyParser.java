package com.lantu.base.pay.ali.parser;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.dev.base.exception.errorcode.SysErrorCode;
import com.dev.base.utils.ValidateUtils;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.pay.AliPayCfg;
import com.lantu.base.pay.PayNotifyParser;
import com.lantu.base.pay.ali.util.AliPayUtils;
import com.lantu.base.pay.ali.util.RSA;
import com.lantu.base.util.HttpUtil;

/**
 * APP支付请求解析器
 * @author lantu
 *
 */
@Service
public class AliAppNotifyParser implements PayNotifyParser{

	@Override
	public Map<String, String> parse(HttpServletRequest request) {
		Map<String, Object> paramMap = HttpUtil.parsePageMap(request);
		Map<String, String> signMap = Maps.newTreeMap();
		for (String key : paramMap.keySet()){
			signMap.put(key, ObjectUtils.getDisplayString(paramMap.get(key)));
		}
		signMap.remove("sign");
		signMap.remove("sign_type");
		
		List<String> signList = Lists.newArrayList();
		for (String key : signMap.keySet()){
			signList.add(key + "=" + signMap.get(key));
		}
		String signStr = Joiner.on("&").join(signList);
//		boolean signResult = RSA.verify(signStr, ObjectUtils.getDisplayString(paramMap.get("sign")), AliPayCfg.getComAliPubKey(), "UTF-8");
//		ValidateUtils.isTrue(signResult, SysErrorCode.SYS_001, "支付签名不正确");
		String notifyId = signMap.get("notify_id");
		
		String response = AliPayUtils.verifyResponse(notifyId);
		ValidateUtils.isTrue("true".equals(response), SysErrorCode.SYS_001, "支付验证失败");
		
		return signMap;
	}

}
