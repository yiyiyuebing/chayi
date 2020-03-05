package pub.makers.shop.purchaseOrder.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dev.base.json.JsonUtils;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.common.util.DateUtils;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.utils.DateParseUtil;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.baseOrder.enums.*;
import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.baseOrder.pojo.OrderConfirmInfo;
import pub.makers.shop.baseOrder.pojo.OrderDeleteInfo;
import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.baseOrder.vo.OrderListPaymentVo;
import pub.makers.shop.baseOrder.vo.OrderPresellExtraVo;
import pub.makers.shop.bill.service.OrderBillRecordBizService;
import pub.makers.shop.freight.service.FreightTplB2bService;
import pub.makers.shop.invoice.vo.InvoiceVo;
import pub.makers.shop.logistics.vo.FreightResultVo;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrder;
import pub.makers.shop.purchaseOrder.pojo.PurchaseOrderQuery;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderB2bService;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderCountVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.user.utils.AccountUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by dy on 2017/6/12.
 */
@Controller
@RequestMapping("/order")
public class PurchaseOrderController {

	@Autowired
	private PurchaseOrderB2bService orderService;
    @Autowired
    private FreightTplB2bService freightTplB2bService;

    @RequestMapping("/testBill")
    @ResponseBody
    public ResultData testBill(String orderId) {
        orderService.addOrderBillRecord(orderId);
        return ResultData.createSuccess();
    }


    @RequestMapping("/getInvoiceInfo")
    @ResponseBody
    public ResultData getInvoiceInfo() {
        String shopId = AccountUtils.getCurrShopId();
        return ResultData.createSuccess("invoiceInfo", orderService.getInvoiceInfo(OrderBizType.purchase, shopId));
    }

    @RequestMapping("/saveInvoice")
    @ResponseBody
    public ResultData saveInvoice(String invoiceJson) {
        String shopId = AccountUtils.getCurrShopId();
        if (StringUtils.isBlank(invoiceJson)) {
            return ResultData.createFail("发票信息不完善");
        }
        InvoiceVo info = JsonUtils.toObject(invoiceJson, InvoiceVo.class);
        info.setUserId(shopId);
        info.setOrderBizType(OrderBizType.purchase);
        orderService.saveInvoice(info);
        return ResultData.createSuccess();
    }


    @RequestMapping("/info/{orderKey}")
    public String orderInfo(HttpServletRequest request, @PathVariable String orderKey, Model model){
        AccountUtils.getCurrShopId();
        HttpSession httpSession = request.getSession();
        PurchaseOrderVo pvo = (PurchaseOrderVo) httpSession.getAttribute(orderKey);
        model.addAttribute("orderInfo", pvo);
        return "www/purchaseOrder/orderInfo";
    }
    @RequestMapping("/submit/{orderId}")
    public String orderSubmit(@PathVariable String orderId, Model model) {
        AccountUtils.getCurrShopId();
        PurchaseOrderVo purchaseOrderVo = orderService.getOrderDetail(orderId);



        List<OrderListPaymentVo> paymentVoList = purchaseOrderVo.getPaymentList();

        BigDecimal unpayAmount = BigDecimal.ZERO;

        if (OrderType.presell.name().equals(purchaseOrderVo.getOrderType())) {
            for (OrderListPaymentVo orderListPaymentVo : paymentVoList) {
                if (BoolType.F.name().equals(orderListPaymentVo.getPayStatus())) {
                    unpayAmount = orderListPaymentVo.getWaitpayAmount();
                    break;
                }
            }
            List<PurchaseOrderListVo> purchaseOrderListVos = purchaseOrderVo.getOrderListVos();

            for (PurchaseOrderListVo purchaseOrderListVo : purchaseOrderListVos) {
                if (OrderListGoodType.zengpin.name().equals(purchaseOrderListVo.getGoodType())) {
                    continue;
                }
                if (purchaseOrderVo.getStatus() == OrderStatus.pay.getDbData()) {
                    purchaseOrderListVo.setFinalAmount(purchaseOrderListVo.getPresellExtra().getPresellFirst());
                }
                if (purchaseOrderVo.getStatus() == OrderStatus.payend.getDbData()) {
                    purchaseOrderListVo.setFinalAmount(purchaseOrderListVo.getPresellExtra().getPresellEnd());
                }
            }
        } else {
            unpayAmount = purchaseOrderVo.getWaitPaymentAmount();
        }

        model.addAttribute("purchaseOrder", purchaseOrderVo);
        model.addAttribute("unpayAmount", unpayAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
        return "www/purchaseOrder/orderSubmit";
    }
    @RequestMapping("/success/{orderId}")
    public String orderSuccess(@PathVariable String orderId, Model model){
        AccountUtils.getCurrShopId();
        PurchaseOrderVo purchaseOrderVo = orderService.getOrderDetail(orderId);
        List<OrderListPaymentVo> paymentList = purchaseOrderVo.getPaymentList();
        OrderListPaymentVo orderListPaymentVo = new OrderListPaymentVo();
        for (OrderListPaymentVo orderListPayment : paymentList) {
            if (BoolType.T.name().equals(orderListPayment.getPayStatus())) {
                orderListPaymentVo = orderListPayment;
                break;
            }
        }
        model.addAttribute("orderListPayment", orderListPaymentVo);
        model.addAttribute("purchaseOrder", purchaseOrderVo);
        return "www/purchaseOrder/orderSuccess";
    }

    @RequestMapping("/list")
    public String orderList(Model model){
        AccountUtils.getCurrShopId();
        return "www/purchaseOrder/order_list";
    }

    @RequestMapping("/precellOrderList")
    public String precellOrderList(Model model){
        AccountUtils.getCurrShopId();
        return "www/purchaseOrder/order_presell_list";
    }

    /***
     * 订单等待付款
     * @param orderId
     * @param model
     * @return
     */
    @RequestMapping("/await")
    public String await(String orderId, Model model){
        AccountUtils.getCurrShopId();
        return "www/purchaseOrder/order_await_payment";
    }

    /***
     * 订单付款成功
     * @return
     */
    @RequestMapping("/payment")
    public  String orderPayment (){
        AccountUtils.getCurrShopId();
        return "www/purchaseOrder/order_payment_succeed";
    }

    @RequestMapping("/delOrder")
    @ResponseBody
    public ResultData delOrder(String orderJsonStr) {
        if (StringUtils.isBlank(orderJsonStr)) {
            return ResultData.createFail("订单不存在");
        }
        OrderDeleteInfo info = JsonUtils.toObject(orderJsonStr, OrderDeleteInfo.class);
        String userId = AccountUtils.getCurrShopId();
        info.setUserId(userId);
        return orderService.delOrder(info);
    }

    @RequestMapping("/checkPaySuccess")
    @ResponseBody
    public ResultData checkPaySuccess(String orderId, Integer stageNum) {
        if (StringUtils.isBlank(orderId)) {
            return ResultData.createFail("订单不存在");
        }

        AccountUtils.getCurrShopId();

        PurchaseOrderVo purchaseOrderVo = orderService.getOrderDetail(orderId);
        List<OrderListPaymentVo> paymentList = purchaseOrderVo.getPaymentList();
        OrderListPaymentVo orderListPaymentVo = null;
        for (OrderListPaymentVo orderListPayment : paymentList) {
            if (BoolType.F.name().equals(orderListPayment.getPayStatus())) {
                orderListPaymentVo = orderListPayment;
                break;
            }
        }

        ValidateUtils.notNull(purchaseOrderVo, "1", "订单信息不存在");

        if (stageNum == 1
                && (OrderStatus.ship.getDbData() == purchaseOrderVo.getStatus()
                || OrderStatus.payend.getDbData() == purchaseOrderVo.getStatus())) {
            return ResultData.createSuccess();
        }
        if (stageNum == 2
                && OrderStatus.ship.getDbData() == purchaseOrderVo.getStatus()) {
            return ResultData.createSuccess();
        }
        return ResultData.createFail();
    }

    /**
     * 订单确认收货
     * @param confirmInfoListJson
     * @return
     */
    @RequestMapping("/confReceipt")
    @ResponseBody
    public ResultData confReceipt(String confirmInfoListJson) {
        if (StringUtils.isBlank(confirmInfoListJson)) {
            return ResultData.createFail("请选择待收货状态的订单");
        }
        List<OrderConfirmInfo> orderConfirmInfos = Lists.newArrayList();
        orderConfirmInfos = JsonUtils.toObject(confirmInfoListJson, ListUtils.getCollectionType(List.class, OrderConfirmInfo.class));
        String userId = AccountUtils.getCurrShopId();
        return orderService.confReceipt(orderConfirmInfos, userId);
    }

    /**
     * 订单列表数据
     * @param orderJson
     * @return
     */
    @RequestMapping("/orderList")
    @ResponseBody
    public ResultData orderList(String orderJson){
        PurchaseOrderQuery orderQuery = JsonUtils.toObject(orderJson, PurchaseOrderQuery.class);
        orderQuery.setUserId(AccountUtils.getCurrShopId());
        orderQuery.setOrderClientType(OrderClientType.pc.name());
        return orderService.orderList(orderQuery);
    }

    /**
     * 状态对应的订单数量
     * @return
     */
    @RequestMapping("/orderCount")
    @ResponseBody
    public ResultData orderCount(String orderJson){

        PurchaseOrderQuery orderQuery = new PurchaseOrderQuery();

        if (StringUtils.isNotBlank(orderJson)) {
            orderQuery = JsonUtils.toObject(orderJson, PurchaseOrderQuery.class);
        }
        orderQuery.setUserId(AccountUtils.getCurrShopId());
        orderQuery.setOrderClientType(OrderClientType.pc.name());
        PurchaseOrderCountVo purchaseOrderCountVo = orderService.getOrderCount(orderQuery);
        return ResultData.createSuccess(purchaseOrderCountVo);
    }


    /**
     * 创建正常订单
     * @param orderJson
     * @param userId
     * @return
     */
    @RequestMapping("/createNormal")
    @ResponseBody
    public ResultData createNormalOrder(String orderJson, String userId){
    	
    	PurchaseOrderVo pvo = JsonUtils.toObject(orderJson, PurchaseOrderVo.class);
    	pvo.setBuyerId(AccountUtils.getCurrShopId());
    	pvo.setSubbranchId(AccountUtils.getCurrShopId());
        pvo.setClientType(ClientType.pc.toString());
        pvo.setOrderClientType(OrderClientType.pc.name());
    	BaseOrder order = orderService.createOrder(pvo);
    	
    	return ResultData.createSuccess(order);
    }
    
    /**
	 * 去支付
	 * @param orderId
	 * @param payWay
	 * @return
	 */
	@RequestMapping("toPay")
	@ResponseBody
	public ResultData toPay(String orderId, String payWay){
        AccountUtils.getCurrShopId();
        //alipay wxpay
		String payStr = orderService.toPay(orderId, payWay);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("paycode", payStr);
        resultMap.put("orderId", orderId);
		return ResultData.createSuccess(resultMap);
	}

    /***
     * 微信支付页面
     * @return
     */
    @RequestMapping("/wxPay/{orderId}/{payWay}")
    public String wxPay(@PathVariable String orderId, @PathVariable String payWay, Model model) {
        AccountUtils.getCurrShopId();
        PurchaseOrderVo purchaseOrderVo = orderService.getOrderDetail(orderId);
        String payStr = orderService.toPay(orderId, payWay);
        List<OrderListPaymentVo> paymentList = purchaseOrderVo.getPaymentList();
        OrderListPaymentVo orderListPaymentVo = new OrderListPaymentVo();
        for (OrderListPaymentVo orderListPayment : paymentList) {
            if (BoolType.F.name().equals(orderListPayment.getPayStatus())) {
                orderListPaymentVo = orderListPayment;
                break;
            }
        }
        model.addAttribute("orderListPayment", orderListPaymentVo);
        model.addAttribute("purchaseOrder", purchaseOrderVo);
        model.addAttribute("qrcode", payStr);
        return "www/purchaseOrder/wxPay";
    }

    @RequestMapping("/detail/{orderId}/{orderType}")
    public String orderDetails(@PathVariable String orderId, @PathVariable OrderType orderType, Model model){
        String shopId = AccountUtils.getCurrShopId();
        PurchaseOrderVo pov= orderService.getOrderDetail(orderId);
        String weekCreate = DateUtils.getWeekOfDate(pov.getCreateTime());
        String weekPay = DateUtils.getWeekOfDate(pov.getPayTime());
        String weekShip = DateUtils.getWeekOfDate(pov.getShipTime());
        String weekFinish = DateUtils.getWeekOfDate(pov.getFinishTime());

        if (pov.getPresellExtra() != null) {
            OrderPresellExtraVo orderPresellExtra = pov.getPresellExtra();
            if (orderPresellExtra.getPaymentEnd() != null && orderPresellExtra.getPaymentStart() != null) {
                if (!DateParseUtil.compareDates(new Date(), orderPresellExtra.getPaymentStart())) {
                    model.addAttribute("payFlag", false);
                } else {
                    model.addAttribute("payFlag", true);
                }
            }
        }


        model.addAttribute("pov",pov);
        model.addAttribute("weekCreate",weekCreate);
        model.addAttribute("weekPay",weekPay);
        model.addAttribute("weekShip",weekShip);
        model.addAttribute("weekFinish",weekFinish);
        return "www/purchaseOrder/order_details";
    }


	
	/**
	 * 取消订单
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/cancel")
	@ResponseBody
	public ResultData cancelOrder(String orderId){
		orderService.cancelOrder(AccountUtils.getCurrShopId(), orderId);
		return ResultData.createSuccess();
	}
    /**
     * 发货提醒
     */
    @RequestMapping("/shipNotice")
    @ResponseBody
    public ResultData shipNotice(String orderId, OrderType orderType) {
        ValidateUtils.notNull(orderId, "orderId不能为空");
        ValidateUtils.notNull(orderType, "orderType为空或类型错误");

        String userId = AccountUtils.getCurrShopId();
        orderService.shipNotice(orderId, userId, orderType);
        return ResultData.createSuccess();
    }
	
	/**
	 * 测试订单已支付
	 * @return
	 */
	@RequestMapping("testPayed")
	@ResponseBody
	public ResultData testPayed(){
        String orderId = "";
		orderService.testPayed(orderId);
		return ResultData.createSuccess();
	}
	
	/**
	 * 测试发货
	 * @return
	 */
	@RequestMapping("testShip")
	@ResponseBody
	public ResultData testShip(){
        String orderId = "398658708952702976";
		orderService.shipOrder(orderId);
		return ResultData.createSuccess();
	}
	
	


    @RequestMapping("/createPreOrder")
    @ResponseBody
    public ResultData createPreOrder(HttpServletRequest request, String orderJson, String userId){
        PurchaseOrderVo pvo = JsonUtils.toObject(orderJson, PurchaseOrderVo.class);
        pvo.setBuyerId(AccountUtils.getCurrShopId());
        HttpSession httpSession = request.getSession();
        String orderKey = new Date().getTime() + "";
        httpSession.setMaxInactiveInterval(30 * 60);
        httpSession.setAttribute(orderKey, pvo);
        return ResultData.createSuccess("orderKey", orderKey);
    }


    @RequestMapping("/getOrderInfoList")
    @ResponseBody
    public ResultData getOrderInfoList(HttpServletRequest request, String orderJson){
        PurchaseOrderVo pvo = JsonUtils.toObject(orderJson, PurchaseOrderVo.class);
        String storeLevel = AccountUtils.getCurrStoreLevelId();
        pvo.setBuyerId(AccountUtils.getCurrShopId());
        pvo.setSubbranchId(AccountUtils.getCurrShopId());
        pvo.setClientType(ClientType.pc.toString());
        pvo.setOrderClientType(OrderClientType.pc.name());

        TradeContext tradeContext = new TradeContext();
        tradeContext.setCity(pvo.getCity());
        tradeContext.setArea(pvo.getDistrict());
        List<PurchaseOrderListVo> orderListVos = Lists.newArrayList();


        BaseOrder baseOrder = orderService.getOrderInfoList(pvo, storeLevel);

        PurchaseOrderVo purchaseOrderVo = (PurchaseOrderVo) baseOrder;
        for (PurchaseOrderListVo purchaseOrderListVo : purchaseOrderVo.getOrderListVos()) {
            if (!BoolType.T.name().equals(purchaseOrderListVo.getGiftFlag()) && !OrderListGoodType.zengpin.name().equals(purchaseOrderListVo.getGoodType())) {
//            if (!BoolType.T.name().equals(purchaseOrderListVo.getGiftFlag())) {
                orderListVos.add(purchaseOrderListVo);
            }
        }
        FreightResultVo freightResultVo = freightTplB2bService.showPurchaseOptions(orderListVos, tradeContext);

        Map<String, Object> resultObj = new HashMap<String, Object>();
        resultObj.put("baseOrder", baseOrder);
        resultObj.put("freightResult", freightResultVo);
        return ResultData.createSuccess(resultObj);
    }

    /**
     * 创建预售订单
     * @return
     */
    @RequestMapping("/createPresell")
    @ResponseBody
    public ResultData createPresellOrder(String orderJson) {
    	PurchaseOrderVo pvo = JsonUtils.toObject(orderJson, PurchaseOrderVo.class);
    	pvo.setBuyerId(AccountUtils.getCurrShopId());
    	pvo.setSubbranchId(AccountUtils.getCurrShopId());
        pvo.setClientType(ClientType.pc.toString());
        pvo.setOrderClientType(OrderClientType.pc.name());
    	BaseOrder result = orderService.createPresell(pvo);
    	return ResultData.createSuccess(result);
    }

}
