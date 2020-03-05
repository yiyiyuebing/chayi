package pub.makers.shop.thirdpart.util;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderClientType;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Title: WeiXinPayUtil.java
 * @Package com.club.web.weixin.util
 * @Description: TODO(微信支付)
 * @author 柳伟军
 * @date 2016年5月11日 上午9:23:40
 * @version V1.0
 */
@Component
@PropertySource("classpath:/weixin.properties")
public class WeixinPayUtil {

	@Value("${appId}")
	private String appId ;
	@Value("${appsecret}")
	private String appsecret ;
	@Value("${mchId}")
	private String mchId  ;
	@Value("${mchKey}")
	private String partnerkey ;
	@Value("${weixincerPath}")
	private String weixincerPath ;

    @Value("${appPay.appId}")
	private String apppayAppId ;
	@Value("${appPay.appsecret}")
	private String apppayAppsecret ;
	@Value("${appPay.mchId}")
	private String apppayMchId  ;
	@Value("${appPay.mchKey}")
	private String apppayPartnerkey ;
	@Value("${appPay.weixincerPath}")
	private String apppayWeixincerPath ;

    @Value("${pur.appId}")
	private String purAppId ;
	@Value("${pur.appsecret}")
	private String purAppsecret ;
	@Value("${pur.mchId}")
	private String purMchId  ;
	@Value("${pur.mchKey}")
	private String purPartnerkey ;
	@Value("${pur.weixincerPath}")
	private String purWeixincerPath ;

    /**
     * 微信支付退款
     */
    public SortedMap<String, Object> wxRefund(String outRefundNo, String outTradeNo, BigDecimal totalFee,
                                             BigDecimal refundFee, String orderBizType, String orderClientType) {
        Map<String, String> config = Maps.newHashMap();
        if (OrderBizType.trade.name().equals(orderBizType)) {
            config.put("appId", appId);
            config.put("appsecret", appsecret);
            config.put("mchId", mchId);
            config.put("partnerkey", partnerkey);
            config.put("weixincerPath", weixincerPath);
        } else {
            if (OrderClientType.app.name().equals(orderClientType)) {
                config.put("appId", apppayAppId);
                config.put("appsecret", apppayAppsecret);
                config.put("mchId", apppayMchId);
                config.put("partnerkey", apppayPartnerkey);
                config.put("weixincerPath", apppayWeixincerPath);
            } else {
                config.put("appId", purAppId);
                config.put("appsecret", purAppsecret);
                config.put("mchId", purMchId);
                config.put("partnerkey", purPartnerkey);
                config.put("weixincerPath", purWeixincerPath);
            }
        }
        return refund(outRefundNo, outTradeNo, totalFee, refundFee, config);
    }

    private SortedMap<String, Object> refund(String outRefundNo, String outTradeNo, BigDecimal totalFee,
                                               BigDecimal refundFee, Map<String, String> config) {

        SortedMap<String, Object> resInfo = new TreeMap<String, Object>();

        BigDecimal hundredFee = new BigDecimal(100D);

        // 总金额
        String total = totalFee.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(hundredFee).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
        // 退款金额
        String refund = refundFee.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(hundredFee).setScale(0, BigDecimal.ROUND_HALF_UP).toString();

        String currTime = TenpayUtil.getCurrTime();
        // 8位日期
        String strTime = currTime.substring(8, currTime.length());
        // 四位随机数
        String strRandom = TenpayUtil.buildRandom(4) + "";
        // 10位序列号,可以自行调整。
        String strReq = strTime + strRandom;
        // 随机数
        String nonce_str = strReq;
        String op_user_id = config.get("mchId");// 就是MCHID
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", config.get("appId"));
        packageParams.put("mch_id", config.get("mchId"));
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("out_trade_no", outTradeNo);
        packageParams.put("out_refund_no", outRefundNo);
        packageParams.put("total_fee", total);
        packageParams.put("refund_fee", refund);
        packageParams.put("op_user_id", op_user_id);

        RequestHandler reqHandler = new RequestHandler(null, null);
        reqHandler.init(config.get("appId"), config.get("appsecret"), config.get("partnerkey"));

        String sign = reqHandler.createSign(packageParams);
        String xml = "<xml>" //
                + "<appid>" + config.get("appId") + "</appid>" //
                + "<mch_id>" + config.get("mchId") + "</mch_id>" //
                + "<nonce_str>" + nonce_str + "</nonce_str>" //
                + "<sign><![CDATA[" + sign + "]]></sign>"//
                + "<out_trade_no>" + outTradeNo + "</out_trade_no>" //
                + "<out_refund_no>" + outRefundNo + "</out_refund_no>"//
                + "<total_fee>" + total + "</total_fee>"//
                + "<refund_fee>" + refund + "</refund_fee>"//
                + "<op_user_id>" + op_user_id + "</op_user_id>" //
                + "</xml>";

        // 退款接口
        String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
        try {
            Map map = ClientCustomSSL.doRefund(config.get("mchId"), createOrderURL, xml, config.get("weixincerPath"));
            resInfo.putAll(map);
        } catch (Exception e) {
            resInfo.put("success", false);
            resInfo.put("msg", e.getMessage());
            e.printStackTrace();
        }
        return resInfo;
    }
}
