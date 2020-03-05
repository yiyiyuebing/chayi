package pub.makers.shop.pay.service;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lantu.base.pay.PayNotifyService;
import com.lantu.base.pay.enums.PayWay;

import pub.makers.shop.baseOrder.entity.OrderListPayment;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.service.OrderPaymentBizService;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderManager;
import pub.makers.shop.tradeOrder.service.TradeOrderManager;
import pub.makers.shop.tradeOrder.vo.OrderPayInfo;

@Service
public class SgPayNotifyService implements PayNotifyService{
	
	@Reference(version="1.0.0")
	private OrderPaymentBizService paymentBizService;
	@Reference(version="1.0.0")
	private PurchaseOrderManager purchaseOrderMgr;
	@Reference(version="1.0.0")
	private TradeOrderManager tradeOrderMgr;
	private static Logger logger = LogManager.getLogger(SgPayNotifyService.class);
	
	@Override
	public boolean doBusiness(String orderno, String payChannel, Map<String, String> payData) {
		
		logger.info(String.format("订单[%s]回调成功", orderno));
		OrderListPayment payment = paymentBizService.getById(orderno);
		OrderBizType orderBizType = OrderBizType.fromName(payment.getOrderBizType());
		OrderType orderType = OrderType.fromName(payment.getOrderType());
		
		OrderPayInfo info = null;
		if (PayWay.alipay.name().equals(payChannel)){
			info = getPayInfoFromAlipay(payData);
		}
		else if (PayWay.wxpay.name().equals(payChannel)){
			info = getPayInfoFromWxpay(payData);
		}
		
		info.setOrderId(payment.getOrderId());
		info.setOrderBizType(orderBizType);
		info.setOrderType(orderType);
		info.setPaymentId(orderno);
		
		if (OrderBizType.purchase.equals(orderBizType)){
			purchaseOrderMgr.payOrder(info);
		}
		else {
			tradeOrderMgr.payOrder(info);
		}
		
		return true;
	}

	@Override
	public boolean hasBusinessDone(String orderno) {
		// TODO 检查支付单的付款状态
		return false;
	}

	@Override
	public void lockTrade(String orderno) {
		// TODO Auto-generated method stub
		
	}
	
	private OrderPayInfo getPayInfoFromWxpay(Map<String, String> payData){
		
		OrderPayInfo info = new OrderPayInfo();
		info.setPayNo(payData.get("transactionId"));
		// 微信支付金额需要除以100
		info.setPaymentAmount(new BigDecimal(payData.get("totalFee")).divide(new BigDecimal(100)));
		info.setPayAccount(payData.get("openid"));
		info.setPayWay(PayWay.wxpay.name());
		
		return info;
	}
	
	private OrderPayInfo getPayInfoFromAlipay(Map<String, String> payData){
		
		OrderPayInfo info = new OrderPayInfo();
		info.setPayNo(payData.get("trade_no"));
		info.setPaymentAmount(new BigDecimal(payData.get("total_fee")));
		info.setPayAccount(payData.get("buyer_email"));
		info.setPayWay(PayWay.alipay.name());
		
		return info;
	}

}
