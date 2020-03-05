package pub.makers.shop.thirdpart.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.dev.base.json.JsonUtils;
import com.dev.base.utils.PropertiesUtils;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

/**
 * Created by kok on 2017/7/17.
 */
public class AliPayUtil {

    public static Map<String, Object> refund(String outTradeNo, BigDecimal refundAmount, String outRequestNo) {
        Properties properties = PropertiesUtils.getProperties("alipay.properties");
        String appId = properties.getProperty("com.appId");
        String privateKey = properties.getProperty("rsa_private_key_new");
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, privateKey, "json", "utf-8");
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        Map<String, Object> data = Maps.newHashMap();
        data.put("out_trade_no", outTradeNo);
        data.put("refund_amount", refundAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        data.put("out_request_no", outRequestNo);
        request.setBizContent(JsonUtils.toJson(data));
        Map<String, Object> result = Maps.newHashMap();
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
            result.put("success", response.isSuccess());
            result.put("msg", response.getSubMsg() == null ? response.getMsg() : response.getSubMsg());
        } catch (AlipayApiException e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg", "系统繁忙，请重试");
        }
        return result;
    }
}
