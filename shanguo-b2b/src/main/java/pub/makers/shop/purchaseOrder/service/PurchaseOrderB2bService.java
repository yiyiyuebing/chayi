package pub.makers.shop.purchaseOrder.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lantu.base.util.WebUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.service.SysDictBizService;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseGood.service.GiftBizService;
import pub.makers.shop.baseOrder.entity.OrderPresellExtra;
import pub.makers.shop.baseOrder.enums.*;
import pub.makers.shop.baseOrder.pojo.*;
import pub.makers.shop.baseOrder.vo.OrderPresellExtraVo;
import pub.makers.shop.bill.service.OrderBillMqTask;
import pub.makers.shop.cart.service.CartBizService;
import pub.makers.shop.common.WxpayBizService;
import pub.makers.shop.invoice.service.InvoiceBizService;
import pub.makers.shop.invoice.vo.InvoiceVo;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuBizService;
import pub.makers.shop.purchaseOrder.pojo.PurchaseOrderQuery;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderCountVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.tradeOrder.vo.OrderPayInfo;
import pub.makers.shop.tradeOrder.vo.ShippingInfo;
import pub.makers.shop.user.service.WeixinUserInfoBizService;
import pub.makers.shop.user.utils.AccountUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/6/12.
 */
@Service
public class PurchaseOrderB2bService {
	
	@Reference(version="1.0.0")
	private PurchaseOrderManager orderMgr;
	@Reference(version = "1.0.0")
	private PurchaseGoodsSkuBizService purchaseGoodsSkuBizService;
	@Reference(version = "1.0.0")
	private GiftBizService giftBizService;
	@Reference(version = "1.0.0")
    private  PurchaseOrderQueryService purchaseOrderQueryService;
	@Reference(version = "1.0.0")
	private CartBizService cartBizService;
	@Reference(version = "1.0.0")
	private WxpayBizService wxPayService;
	@Reference(version = "1.0.0")
	private WeixinUserInfoBizService weixinUserInfoBizService;
	@Reference(version = "1.0.0")
	private SysDictBizService sysDictBizService;
	@Reference(version = "1.0.0")
	private InvoiceBizService invoiceBizService;
	@Reference(version = "1.0.0")
	private OrderBillMqTask orderBillMqTask;


	/**
	 * 获取用户的发票信息
	 * @param orderBizType
	 * @param userId
	 * @return
	 */
	public InvoiceVo getInvoiceInfo(OrderBizType orderBizType, String userId) {
		ValidateUtils.notNull(userId, "用户信息不全");
		ValidateUtils.notNull(orderBizType, "业务类型不明确");
		return invoiceBizService.getInvoiceInfoByUser(orderBizType, userId);
	}

	public BaseOrder createOrder(PurchaseOrderVo orderVo){
		OrderSubmitInfo info = new OrderSubmitInfo();
		info.setBaseOrder(orderVo);
		info.setOrderBizType(OrderBizType.purchase);
		info.setOrderType(OrderType.normal);
		
		return orderMgr.createOrder(info);
	}
	
	public BaseOrder createPresell(PurchaseOrderVo orderVo){
		
		OrderSubmitInfo info = new OrderSubmitInfo();
		info.setBaseOrder(orderVo);
		info.setOrderBizType(OrderBizType.purchase);
		info.setOrderType(OrderType.presell);
		
		return orderMgr.createOrder(info);
	}

	public void saveInvoice(InvoiceVo invoiceVo) {
		String userId = AccountUtils.getCurrShopId();
		invoiceVo.setUserId(userId);
		invoiceVo.setOrderBizType(OrderBizType.purchase);
		invoiceBizService.saveInvoice(invoiceVo);
	}

	public BaseOrder getOrderInfoList(PurchaseOrderVo pvo, String storeLevel) {
		BaseOrder baseOrder = orderMgr.preview(pvo);
		return baseOrder;
	}

	public Map<String, Object> toWeixinPay(String orderId, String userId){
		String openId = AccountUtils.getOpenId();
		ValidateUtils.notNull(openId, "微信登陆信息超时，请重新登陆");
		PayParam param = new PayParam();
		param.setOrderType(OrderType.normal);
		param.setOrderBizType(OrderBizType.purchase);
		param.setOrderId(orderId);
		param.setPayWay("wxpay");
		param.setClientIp(WebUtil.getClientIp(WebUtil.getRequest()));
		param.setTradeType("JSAPI");
		param.setOpenId(openId);

		String prepayId = orderMgr.toPay(param);

		return wxPayService.jsPaySign("pur", prepayId);
	}

	public String toPay(String orderId, String payWay) {

		PayParam param = new PayParam();
		param.setOrderType(OrderType.normal);
		param.setOrderBizType(OrderBizType.purchase);
		param.setOrderId(orderId);
		param.setPayWay("wxpay");
		param.setClientIp(WebUtil.getClientIp(WebUtil.getRequest()));
		param.setTradeType("NATIVE");
		param.setOpenId("osH01wvbTxBGD_ljKoHeQizx2muA");

		return orderMgr.toPay(param);
	}


	public void cancelOrder(String userId, String orderId) {

		OrderCancelInfo info = new OrderCancelInfo();
		info.setCancelType(OrderCancelType.user);
		info.setOrderBizType(OrderBizType.purchase);
		info.setOrderType(OrderType.normal);
		info.setUserId(userId);
		info.setOrderId(orderId);

		orderMgr.cancelOrder(info);
	}

	public void testPayed(String orderId) {

		OrderPayInfo info = new OrderPayInfo();
		info.setOrderId(orderId);
		info.setPayNo("12121312121312");
		info.setPaymentId("389132818965938176");
		info.setPayAccount("abc@qq.com");
		info.setPayWay("wxpay");
		info.setPaymentAmount(new BigDecimal(10));
		info.setUserId(269839126291873792L);
		info.setOrderBizType(OrderBizType.purchase);
		info.setOrderType(OrderType.normal);

		orderMgr.payOrder(info);
	}

	/**
	 * 订单详情
	 */
	 public PurchaseOrderVo getOrderDetail(String id){
		 String userId = AccountUtils.getCurrShopId();
		 PurchaseOrderVo vo =  purchaseOrderQueryService.getOrderDetail(id);
		 ValidateUtils.isTrue(userId.equals(vo.getBuyerId()), "只能操作自己的订单");
		 return vo;
	 }

	/**
	 * 获取订单列表数量
	 * @param orderQuery
	 * @return
	 */
	public PurchaseOrderCountVo getOrderCount(PurchaseOrderQuery orderQuery) {
		return purchaseOrderQueryService.getOrderCount(orderQuery);
	}

	/**
	 * 查询订单列表数据
	 * @param orderQuery
	 * @return
	 */
	public ResultData orderList(PurchaseOrderQuery orderQuery) {
		List<PurchaseOrderVo> purchaseOrderVos = purchaseOrderQueryService.getOrderList(orderQuery);
		Long orderCount = purchaseOrderQueryService.countOrderList(orderQuery);
		return ResultData.createSuccess(orderCount.intValue(), orderQuery.getPageNo(), orderQuery.getPageSize(), purchaseOrderVos);
	}

	/**
	 * 获取订单中的商品信息,数据信息主要提供给售后服务选择页面
	 *
	 * @param orderId
	 * @param skuId
	 * @param shopId
	 * @return
	 */
	public PurchaseOrderListVo getGoodInOrderMsg(String orderId, String skuId, String shopId) {
		return purchaseOrderQueryService.getGoodInOrderMsg(orderId, skuId, shopId);
	}

	/**
	 * 订单确认收货
	 * @param orderConfirmInfos
	 * @param userId
	 * @return
	 */
	public ResultData confReceipt(List<OrderConfirmInfo> orderConfirmInfos, String userId) {
		for (OrderConfirmInfo orderConfirmInfo : orderConfirmInfos) {
			orderConfirmInfo.setUserId(userId);
			orderConfirmInfo.setOrderBizType(OrderBizType.purchase);
			orderConfirmInfo.setConfirmType(OrderConfirmType.user);
			orderMgr.confirmReceipt(orderConfirmInfo);
		}
		return ResultData.createSuccess("确认收货成功！");
	}

	/**
	 * 删除订单
	 * @param info
	 * @return
	 */
	public ResultData delOrder(OrderDeleteInfo info) {
		info.setDeleteType(OrderDeleteType.buyer);
		info.setOrderBizType(OrderBizType.purchase);
		orderMgr.deleteOrder(info);
		return ResultData.createSuccess("删除成功");
	}

	public void delCart(String cartIds, String userId) {
		List<String> idList = Arrays.asList(StringUtils.split(cartIds, ","));
		cartBizService.delFromCart(idList, userId, OrderBizType.purchase);
	}

	public void shipNotice(String orderId, String userId, OrderType orderType) {
		orderMgr.shipNotice(new ShipNoticeInfo(userId, orderId, OrderBizType.purchase, orderType));
	}
	
	public void shipOrder(String orderId){
		
		ShippingInfo si = new ShippingInfo();
		si.setOrderId(orderId);
		si.setExpressCompany("顺丰");
		si.setExpressId("shunfen");
		si.setExpressNumber("12345678");
		si.setOrderBizType(OrderBizType.purchase);
		si.setOrderType(OrderType.normal);
		si.setUserId(123344L);
		si.setStaffName("");
		
		orderMgr.shipmentOrder(si);
	}

	public BaseOrder preview(PurchaseOrderVo order){

		return orderMgr.preview(order);
	}

	public List<PurchaseOrderListVo> getRefundOrderList(PurchaseOrderQuery query) {
		query.setUserId(AccountUtils.getCurrShopId());
		return purchaseOrderQueryService.getRefundOrderList(query);
	}

	public Integer getSysDictReturnTime() {
		List<SysDict> sysDicts = sysDictBizService.list("return_time", OrderBizType.purchase.name());
		if (sysDicts.isEmpty()) {
			return 0;
		}

		if (StringUtils.isBlank(sysDicts.get(0).getValue())) {
			return 0;
		}
		return Integer.parseInt(sysDicts.get(0).getValue());
	}

	public void addOrderBillRecord(String orderId) {
		orderBillMqTask.addOrderBillRecord(orderId);
	}
}
