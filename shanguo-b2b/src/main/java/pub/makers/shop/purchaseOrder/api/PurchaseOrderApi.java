package pub.makers.shop.purchaseOrder.api;

import com.dev.base.json.JsonUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderClientType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.baseOrder.pojo.OrderConfirmInfo;
import pub.makers.shop.baseOrder.pojo.OrderDeleteInfo;
import pub.makers.shop.invoice.vo.InvoiceVo;
import pub.makers.shop.purchaseOrder.pojo.PurchaseOrderQuery;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderB2bService;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderCountVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.user.utils.AccountUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/6/30.
 */
@Controller
@RequestMapping("weixin/order")
public class PurchaseOrderApi {
    @Autowired
    private PurchaseOrderB2bService orderService;

    /**
     * 创建普通订单
     */
    @RequestMapping("createNormalOrder")
    @ResponseBody
    public ResultData createNormalOrder(@RequestParam(value = "modelJson", required = true) String modelJson, String cartIds) {
        PurchaseOrderVo orderVo = JsonUtils.toObject(modelJson, PurchaseOrderVo.class);
        String userId = AccountUtils.getCurrShopId();
        orderVo.setBuyerId(userId);
        orderVo.setSubbranchId(userId);
        orderVo.setClientType(ClientType.mobile.name());
        orderVo.setOrderClientType(OrderClientType.weixin.name());
        PurchaseOrderVo vo = (PurchaseOrderVo) orderService.createOrder(orderVo);

        if (org.apache.commons.lang.StringUtils.isNotEmpty(cartIds)) {
            orderService.delCart(cartIds, vo.getBuyerId());
        }
        return ResultData.createSuccess(vo);
    }

    /**
     * 创建预售订单
     */
    @RequestMapping("createPresellOrder")
    @ResponseBody
    public ResultData createPresellOrder(@RequestParam(value = "modelJson", required = true) String modelJson) {
        PurchaseOrderVo orderVo = JsonUtils.toObject(modelJson, PurchaseOrderVo.class);
        String userId = AccountUtils.getCurrShopId();
        orderVo.setBuyerId(userId);
        orderVo.setSubbranchId(userId);
        orderVo.setClientType(ClientType.mobile.name());
        orderVo.setOrderClientType(OrderClientType.weixin.name());
        PurchaseOrderVo vo = (PurchaseOrderVo) orderService.createPresell(orderVo);
        return ResultData.createSuccess(vo);
    }


    /**
     * 获取用户购买订单列表
     * {purchaseOrder/getOrderList}
     * @return
     */
    @RequestMapping("getOrderList")
    @ResponseBody
    public ResultData getOrderList(String modelJson){
        ValidateUtils.notNull(modelJson, "查询条件不能为空");
        PurchaseOrderQuery purchaseOrderQuery = JsonUtils.toObject(modelJson, PurchaseOrderQuery.class);
        String userId = AccountUtils.getCurrShopId();
        purchaseOrderQuery.setUserId(userId);
        purchaseOrderQuery.setOrderClientType(OrderClientType.weixin.name());
        return orderService.orderList(purchaseOrderQuery);
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
     * 获取订单详情
     *  {purchaseOrder/getOrderDetail}
     * @param id
     * @return
     */
    @RequestMapping("getOrderDetail")
    @ResponseBody
    public ResultData getOrderDetail(String id){
        ValidateUtils.isTrue(StringUtils.isNotBlank(id), "订单id为空");
        PurchaseOrderVo purchaseOrderVo = orderService.getOrderDetail(id);
        return ResultData.createSuccess("purchaseOrderVo", purchaseOrderVo);
    }



    //删除订单
    @RequestMapping("deleteOrder")
    @ResponseBody
    public ResultData delOrder(String orderJsonStr) {
        if (org.apache.commons.lang.StringUtils.isBlank(orderJsonStr)) {
            return ResultData.createFail("订单不存在");
        }
        OrderDeleteInfo info = JsonUtils.toObject(orderJsonStr, OrderDeleteInfo.class);
        String userId = AccountUtils.getCurrShopId();
        info.setUserId(userId);
        return orderService.delOrder(info);
    }

    /**
     * 订单确认收货
     * @return
     */
    @RequestMapping("/confReceipt")
    @ResponseBody
    public ResultData confReceipt(String orderId, OrderType orderType) {
        ValidateUtils.notNull(orderId, "orderId不能为空");
        ValidateUtils.notNull(orderType, "orderType为空或类型错误");

        List<OrderConfirmInfo> orderConfirmInfos = Lists.newArrayList();
        OrderConfirmInfo orderConfirmInfo = new OrderConfirmInfo();
        orderConfirmInfo.setOrderId(orderId);
        orderConfirmInfo.setOrderType(orderType);
        orderConfirmInfos.add(orderConfirmInfo);
        String userId = AccountUtils.getCurrShopId();
        return orderService.confReceipt(orderConfirmInfos, userId);
    }

    /**
     * 发货提醒
     */
    @RequestMapping("shipNotice")
    @ResponseBody
    public ResultData shipNotice(String orderId, OrderType orderType) {
        ValidateUtils.notNull(orderId, "orderId不能为空");
        ValidateUtils.notNull(orderType, "orderType为空或类型错误");

        String userId = AccountUtils.getCurrShopId();
        orderService.shipNotice(orderId, userId, orderType);
        return ResultData.createSuccess();
    }

    /**
     * 创建微信支付信息
     * @param orderId
     * @return
     */
    @RequestMapping("toPay")
    @ResponseBody
    public ResultData toPay(String orderId, String userId){

        ValidateUtils.notNull(orderId, "orderId不能为空");
        userId = AccountUtils.getCurrShopId();
        Map<String, Object> payResult = orderService.toWeixinPay(orderId, userId);
        System.out.println(JsonUtils.toJson(payResult));

        return ResultData.createSuccess(payResult);
    }
    
    
    /**
     * 预览订单概况
     * @param orderJson
     * @return
     */
    @RequestMapping("preview")
    @ResponseBody
    public ResultData preview(String orderJson){

    	ValidateUtils.notNull(orderJson, "订单信息不能为空");
    	PurchaseOrderVo order = JsonUtils.toObject(orderJson, PurchaseOrderVo.class);
    	ValidateUtils.notNull(order, "订单信息不能为空");
    	ValidateUtils.notNull(order.getOrderType(), "订单类型不能为空");
        String userId = AccountUtils.getCurrShopId();
        order.setBuyerId(userId);
    	order.setSubbranchId(userId);
        order.setClientType(ClientType.mobile.name());
    	BaseOrder result = orderService.preview(order);
    	
    	return ResultData.createSuccess(result);
    }

    /**
     * 退款退货售后订单列表
     * @param modelJson
     * @return
     */
    @RequestMapping("getRefundOrderList")
    @ResponseBody
    public ResultData getRefundOrderList(String modelJson){

    	ValidateUtils.notNull(modelJson, "订单信息不能为空");
        PurchaseOrderQuery query = JsonUtils.toObject(modelJson, PurchaseOrderQuery.class);
        List<PurchaseOrderListVo> orderListVos = orderService.getRefundOrderList(query);

    	return ResultData.createSuccess("orderListVos", orderListVos);
    }

    /**
     * 状态对应的订单数量
     * @return
     */
    @RequestMapping("/orderCount")
    @ResponseBody
    public ResultData orderCount(String orderJson){

        PurchaseOrderQuery orderQuery = new PurchaseOrderQuery();

        if (org.apache.commons.lang.StringUtils.isNotBlank(orderJson)) {
            orderQuery = JsonUtils.toObject(orderJson, PurchaseOrderQuery.class);
        }
        orderQuery.setUserId(AccountUtils.getCurrShopId());
        orderQuery.setOrderClientType(OrderClientType.weixin.name());
        PurchaseOrderCountVo purchaseOrderCountVo = orderService.getOrderCount(orderQuery);
        return ResultData.createSuccess(purchaseOrderCountVo);
    }

    @RequestMapping("saveInvoice")
    @ResponseBody
    public ResultData saveInvoice(String modelJson) {
        ValidateUtils.notNull(modelJson, "发票信息不能为空");
        InvoiceVo invoiceVo = JsonUtils.toObject(modelJson, InvoiceVo.class);
        orderService.saveInvoice(invoiceVo);
        return ResultData.createSuccess();
    }

    @RequestMapping("getInvoice")
    @ResponseBody
    public ResultData getInvoice() {
        String userId = AccountUtils.getCurrShopId();
        InvoiceVo invoiceVo = orderService.getInvoiceInfo(OrderBizType.purchase, userId);
        return ResultData.createSuccess(invoiceVo);
    }
}
