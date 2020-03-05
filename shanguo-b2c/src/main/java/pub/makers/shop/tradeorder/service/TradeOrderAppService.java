package pub.makers.shop.tradeorder.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lantu.base.util.WebUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.baseOrder.enums.*;
import pub.makers.shop.baseOrder.pojo.*;
import pub.makers.shop.baseOrder.service.OrderPresellExtraQueryService;
import pub.makers.shop.cart.service.CartBizService;
import pub.makers.shop.common.WxpayBizService;
import pub.makers.shop.invoice.service.InvoiceBizService;
import pub.makers.shop.invoice.vo.InvoiceVo;
import pub.makers.shop.tradeGoods.service.GoodEvaluationBizService;
import pub.makers.shop.tradeGoods.vo.GoodEvaluationVo;
import pub.makers.shop.tradeOrder.pojo.TradeOrderQuery;
import pub.makers.shop.tradeOrder.service.TradeOrderManager;
import pub.makers.shop.tradeOrder.service.TradeOrderQueryService;
import pub.makers.shop.tradeOrder.vo.IndentVo;
import pub.makers.shop.tradeOrder.vo.OrderPayInfo;
import pub.makers.shop.user.entity.WeixinUserInfo;
import pub.makers.shop.user.service.WeixinUserInfoBizService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class TradeOrderAppService {

	@Reference(version="1.0.0")
	private TradeOrderManager orderManager;
	@Reference(version="1.0.0")
	private WxpayBizService wxPayService;
	@Reference(version="1.0.0")
	private TradeOrderQueryService tradeOrderQueryService;
    @Reference(version = "1.0.0")
    private CartBizService cartBizService;
	@Reference(version = "1.0.0")
	private WeixinUserInfoBizService weixinUserInfoBizService;
	@Reference(version = "1.0.0")
	private OrderPresellExtraQueryService orderPresellExtraQueryService;
	@Reference(version = "1.0.0")
	private GoodEvaluationBizService goodEvaluationBizService;
	@Reference(version = "1.0.0")
	private InvoiceBizService invoiceBizService;
	
	public IndentVo createNormalOrder(IndentVo order){
		
		OrderSubmitInfo info = new OrderSubmitInfo();
		info.setBaseOrder(order);
		info.setOrderBizType(OrderBizType.trade);
		info.setOrderType(OrderType.normal);
		
		BaseOrder bo = orderManager.createOrder(info);
		return (IndentVo)bo;
	}

	public IndentVo createPresellOrder(IndentVo order){

		OrderSubmitInfo info = new OrderSubmitInfo();
		info.setBaseOrder(order);
		info.setOrderBizType(OrderBizType.trade);
		info.setOrderType(OrderType.presell);

		BaseOrder bo = orderManager.createOrder(info);
		IndentVo indentVo = (IndentVo)bo;
		indentVo.setPresellExtra(orderPresellExtraQueryService.getPresellExtra(indentVo.getId(), OrderBizType.trade));
		return (IndentVo)bo;
	}
	
	public Map<String, Object> toPay(String orderId, String userId){
		WeixinUserInfo userInfo = weixinUserInfoBizService.getWxUserById(Long.valueOf(userId));
		ValidateUtils.notNull(userInfo, "用户不存在");

		PayParam param = new PayParam();
		param.setOrderType(OrderType.normal);
		param.setOrderBizType(OrderBizType.trade);
		param.setOrderId(orderId);
		param.setPayWay("wxpay");
		param.setClientIp(WebUtil.getClientIp(WebUtil.getRequest()));
		param.setTradeType("JSAPI");
		param.setOpenId(userInfo.getOpenId());
		
		String prepayId = orderManager.toPay(param);
		
		return wxPayService.jsPaySign("", prepayId);
	}
	
	public void cancelOrder(String userId, String orderId){
		
		OrderCancelInfo info = new OrderCancelInfo();
		info.setCancelType(OrderCancelType.user);
		info.setOrderBizType(OrderBizType.trade);
		info.setOrderType(OrderType.normal);
		info.setUserId(userId);
		info.setOrderId(orderId);
		
		orderManager.cancelOrder(info);
	}
	
    public void testPayed(String orderId) {

        OrderPayInfo info = new OrderPayInfo();
        info.setOrderId(orderId);
        info.setPayNo("12121312121312");
        info.setPaymentId("388643823549313024");
        info.setPayAccount("abc@qq.com");
        info.setPayWay("wxpay");
        info.setPaymentAmount(new BigDecimal(10));
        info.setUserId(343086952282382336L);
        info.setOrderBizType(OrderBizType.trade);
        info.setOrderType(OrderType.normal);


        orderManager.payOrder(info);
    }

    public List<IndentVo> getOrderList(TradeOrderQuery query) {
        return tradeOrderQueryService.getOrderList(query);
    }

    public IndentVo getOrderDetail(String id, String userId) {
		IndentVo vo =  tradeOrderQueryService.getOrderDetail(id);
		ValidateUtils.isTrue(vo.getBuyerId().equals(userId), "只能操作自己的订单");
		return vo;
    }

    public void delCart(String cartIds, String userId) {
        List<String> idList = Arrays.asList(StringUtils.split(cartIds, ","));
        cartBizService.delFromCart(idList, userId, OrderBizType.trade);
    }

	public void confirmReceipt(String userId, String orderId, OrderType orderType) {
		orderManager.confirmReceipt(new OrderConfirmInfo(userId, orderId, OrderConfirmType.user, OrderBizType.trade, orderType));
	}

	public void deleteOrder(String orderId, String userId, OrderType orderType) {
		orderManager.deleteOrder(new OrderDeleteInfo(userId, orderId, OrderDeleteType.buyer, OrderBizType.trade, orderType));
	}

	public void shipNotice(String orderId, String userId, OrderType orderType) {
		orderManager.shipNotice(new ShipNoticeInfo(userId, orderId, OrderBizType.trade, orderType));
	}
	
	public IndentVo queryPayInfo(String orderId){
    	return tradeOrderQueryService.queryPayInfo(orderId);
    }

	public void addEvaluation(List<GoodEvaluationVo> goodEvaluationVoList) {
		for (GoodEvaluationVo evaluationVo : goodEvaluationVoList) {
			goodEvaluationBizService.addEvaluation(evaluationVo);
		}
	}

	public BaseOrder preview(IndentVo order){

		return orderManager.preview(order);
	}

	public InvoiceVo getInvoiceInfo(OrderBizType orderBizType, String userId) {
		ValidateUtils.notNull(userId, "用户信息不全");
		ValidateUtils.notNull(orderBizType, "业务类型不明确");
		return invoiceBizService.getInvoiceInfoByUser(orderBizType, userId);
	}

	public void saveInvoice(InvoiceVo invoiceVo) {
		invoiceVo.setOrderBizType(OrderBizType.trade);
		invoiceBizService.saveInvoice(invoiceVo);
	}

	public void testConfirmReceipt(String orderNo) {
		IndentVo indentVo = tradeOrderQueryService.getOrderDetailByOrderNo(orderNo);
		orderManager.confirmReceipt(new OrderConfirmInfo(indentVo.getBuyerId(), indentVo.getOrderId(), OrderConfirmType.user, OrderBizType.trade, OrderType.valueOf(indentVo.getOrderType())));
	}
}
