package pub.makers.shop.pay.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lantu.base.pay.enums.PayWay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseOrder.entity.OrderListPayment;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.service.OrderPaymentBizService;
import pub.makers.shop.pay.service.SgPayNotifyService;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dy on 2017/9/21.
 */
@Service
public class TestPayService {
    @Autowired
    private SgPayNotifyService sgPayNotifyService;
    @Reference(version = "1.0.0")
    private OrderPaymentBizService paymentBizService;

    public ResultData testPay(String orderNo, String payChannel) {

        ValidateUtils.notNull(orderNo, "订单号不能为空");
        ValidateUtils.notNull(payChannel, "支付类型不能为空");
        List<OrderListPayment> orderListPaymentList = new ArrayList<OrderListPayment>();

        Map<String, String> payData = new HashMap<String, String>();

        if (orderNo.indexOf("s") >= 0) { //商城
            orderListPaymentList = paymentBizService.getPaymentList(orderNo, OrderBizType.trade);
        } else { //采购
            orderListPaymentList = paymentBizService.getPaymentList(orderNo, OrderBizType.purchase);
        }
        if (orderListPaymentList.isEmpty()) {
            return ResultData.createFail("当前订单不存在或不是待支付订单");
        }

        for (OrderListPayment orderListPayment : orderListPaymentList) {
            if (PayWay.alipay.name().equals(payChannel)) {
                payData.put("trade_no", new Date().getTime() + "");
                payData.put("total_fee", orderListPayment.getWaitpayAmount().toString());
                payData.put("buyer_email", "test@youchalian.com");
            } else {
                payData.put("transactionId", new Date().getTime() + "");
                payData.put("totalFee", orderListPayment.getWaitpayAmount().multiply(new BigDecimal(100)).toString());
                payData.put("openid", "test@youchalian.com");
            }

            boolean flag = sgPayNotifyService.doBusiness(orderListPayment.getId() + "", payChannel, payData);
            if (!flag) {
                return ResultData.createFail("支付失败");
            }
        }

        return ResultData.createSuccess();
    }
}
