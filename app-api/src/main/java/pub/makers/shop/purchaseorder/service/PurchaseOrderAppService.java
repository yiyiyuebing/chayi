package pub.makers.shop.purchaseorder.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.lantu.base.util.WebUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.baseOrder.enums.*;
import pub.makers.shop.baseOrder.pojo.*;
import pub.makers.shop.cart.service.CartBizService;
import pub.makers.shop.common.WxpayBizService;
import pub.makers.shop.invoice.service.InvoiceBizService;
import pub.makers.shop.invoice.vo.InvoiceVo;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsEvaluationBizService;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsEvaluationVo;
import pub.makers.shop.purchaseOrder.pojo.PurchaseOrderQuery;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderManager;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderQueryService;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.user.utils.AccountUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/5/24.
 */
@Service
public class PurchaseOrderAppService {
    @Reference(version = "1.0.0")
    private PurchaseOrderManager purchaseOrderManager; 
    @Reference(version = "1.0.0")
    private PurchaseOrderQueryService purchaseOrderQueryService;
    @Reference(version = "1.0.0")
    private PurchaseGoodsEvaluationBizService purchaseGoodsEvaluationBizService;
    @Reference(version = "1.0.0")
    private WxpayBizService wxPayService;
    @Reference(version = "1.0.0")
    private CartBizService cartBizService;
    @Reference(version = "1.0.0")
    private InvoiceBizService invoiceBizService;
    /**
     * 创建采购订单
     * @param order
     */
    public PurchaseOrderVo createNormalOrder(PurchaseOrderVo order) {
        OrderSubmitInfo info = new OrderSubmitInfo();
        info.setBaseOrder(order);
        info.setOrderBizType(OrderBizType.purchase);
        info.setOrderType(OrderType.normal);
        BaseOrder bo = purchaseOrderManager.createOrder(info);
        return (PurchaseOrderVo)bo;
    }


    /**
     * 创建采购订单
     * @param order
     */
    public PurchaseOrderVo createPresellOrder(PurchaseOrderVo order) {
        OrderSubmitInfo info = new OrderSubmitInfo();
        info.setBaseOrder(order);
        info.setOrderBizType(OrderBizType.purchase);
        info.setOrderType(OrderType.presell);
        BaseOrder bo = purchaseOrderManager.createOrder(info);
        return (PurchaseOrderVo)bo;
    }


    /**
     * 取消订单
     */
    public void cancelOrder(String userId, String orderId, OrderType orderType) {
        OrderCancelInfo info = new OrderCancelInfo();
        info.setCancelType(OrderCancelType.user);
        info.setOrderBizType(OrderBizType.purchase);
        info.setOrderType(orderType);
        info.setUserId(userId);
        info.setOrderId(orderId);
        purchaseOrderManager.cancelOrder(info);
    }

    public void confirmReceipt(String userId, String orderId, OrderType orderType) {
        purchaseOrderManager.confirmReceipt(new OrderConfirmInfo(userId, orderId, OrderConfirmType.user, OrderBizType.purchase, orderType));
    }

    public Map<String, Object> toPay(String orderId, String payWay) {
    	
    	String tradeType = "wxpay".equals(payWay) ? "APP" : "app";
        PayParam param = new PayParam();
        param.setOrderType(OrderType.normal);
        param.setOrderBizType(OrderBizType.purchase);
        param.setOrderId(orderId);
        param.setPayWay(payWay);
        param.setClientIp(WebUtil.getClientIp(WebUtil.getRequest()));
        param.setTradeType(tradeType);
        param.setOpenId("osH01wvbTxBGD_ljKoHeQizx2muA");
        
        String prepayId = purchaseOrderManager.toPay(param);
        
        if ("wxpay".equals(payWay)){
        	return wxPayService.appPaySign(prepayId);
        }
        else {
        	Map<String, Object> resultMap = Maps.newHashMap();
        	resultMap.put("sign", prepayId);
        	
        	return resultMap;
        }
        
    }

    public void deleteOrder(String orderId, String userId, OrderType orderType) {
        purchaseOrderManager.deleteOrder(new OrderDeleteInfo(userId, orderId, OrderDeleteType.buyer, OrderBizType.purchase, orderType));
    }

    public void shipNotice(String orderId, String userId, OrderType orderType) {
        purchaseOrderManager.shipNotice(new ShipNoticeInfo(userId, orderId, OrderBizType.purchase, orderType));
    }
    
    public List<PurchaseOrderVo> getOrderList(PurchaseOrderQuery query){
    	return purchaseOrderQueryService.getOrderList(query);
    }
    
    public PurchaseOrderVo getOrderDetail(String id){
        String userId = AccountUtils.getCurrShopId();
		PurchaseOrderVo vo =  purchaseOrderQueryService.getOrderDetail(id);
        ValidateUtils.isTrue(userId.equals(vo.getBuyerId()), "只能操作自己的订单");
        return vo;
    }

    public void addEvaluation(PurchaseGoodsEvaluationVo purchaseGoodsEvaluationVo) {
        purchaseGoodsEvaluationBizService.addEvaluation(purchaseGoodsEvaluationVo);
    }

    public void delCart(String cartIds, String userId) {
        List<String> idList = Arrays.asList(StringUtils.split(cartIds, ","));
        cartBizService.delFromCart(idList, userId, OrderBizType.purchase);
    }
    
    public PurchaseOrderVo queryPayInfo(String orderId){
    	return purchaseOrderQueryService.queryPayInfo(orderId);
    }

    public BaseOrder preview(PurchaseOrderVo order){

        return purchaseOrderManager.preview(order);
    }

    public void saveInvoice(InvoiceVo invoiceVo) {
        String userId = AccountUtils.getCurrShopId();
        invoiceVo.setUserId(userId);
        invoiceVo.setOrderBizType(OrderBizType.purchase);
        invoiceBizService.saveInvoice(invoiceVo);
    }

    public InvoiceVo getInvoiceInfo() {
        String userId = AccountUtils.getCurrShopId();
        return invoiceBizService.getInvoiceInfoByUser(OrderBizType.purchase, userId);
    }
}
