package pub.makers.shop.pay.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.base.utils.ValidateUtils;
import com.google.common.collect.Maps;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.constant.CfgConstants;

import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.pay.service.SgPayNotifyService;
import pub.makers.shop.pay.service.TestPayService;

@Controller
@RequestMapping("sgpay")
public class SgPayController {

	@Autowired
	private SgPayNotifyService payService;

	@Autowired
	private TestPayService testPayService;
	
	@RequestMapping("debugpay")
	@ResponseBody
	public String debugpay(String orderno){
		
		String orderBug = CfgConstants.getProperties().get("order.debug");
		ValidateUtils.isTrue("true".equals(orderBug), "非法请求");
		
		Map<String, String> payData = Maps.newHashMap();
		payData.put("buyer_email", "test@youchalian.com");
		payData.put("total_fee", "15811.31");
		payData.put("trade_no", "aaaayouchaliantestaaa");
		
		payService.doBusiness(orderno, "alipay", payData);
		
		return "success";
	}

	@RequestMapping("/pay")
	@ResponseBody
	public ResultData testPay(String orderNo, String payChannel) {
		String orderBug = CfgConstants.getProperties().get("order.debug");
		ValidateUtils.isTrue("true".equals(orderBug), "非法请求");
		return testPayService.testPay(orderNo, payChannel);
	}
}
