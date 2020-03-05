package pub.makers.shop.purchaseorder.controller;

import com.dev.base.json.JsonUtils;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseOrder.enums.OrderClientType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.invoice.vo.InvoiceVo;
import pub.makers.shop.logistics.service.FreightTplAppService;
import pub.makers.shop.logistics.vo.FreightResultVo;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsEvaluationVo;
import pub.makers.shop.purchaseOrder.pojo.PurchaseOrderQuery;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.purchaseorder.service.PurchaseOrderAppService;
import pub.makers.shop.user.utils.AccountUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("purchaseOrder")
public class PurchaseOrderController {

    @Autowired
    private FreightTplAppService freightTplAppService;
    @Autowired
    private PurchaseOrderAppService purchaseOrderAppService;

    /**
     * 查询物流选项
     *
     * @return
     */
    @RequestMapping("showCarriageOptions")
    @ResponseBody
    public ResultData showCarriageOptions(HttpServletResponse response, String listJson, String tradeContextJson) {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=utf-8");

        ValidateUtils.notNull(listJson, "订单信息不能为空");
        ValidateUtils.notNull(tradeContextJson, "交易上下文信息不能为空");

        List<PurchaseOrderListVo> orderListVos = JsonUtils.toObject(listJson, ListUtils.getCollectionType(List.class, PurchaseOrderListVo.class));
        TradeContext tradeContext = JsonUtils.toObject(tradeContextJson, TradeContext.class);

        ValidateUtils.notNull(tradeContext.getArea(), "下单地区信息有错");
        ValidateUtils.notNull(tradeContext.getCity(), "下单地区信息有错");

        FreightResultVo fvo = freightTplAppService.showPurchaseOptions(orderListVos, tradeContext);

        return ResultData.createSuccess("fvo", fvo);

    }

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
        orderVo.setOrderClientType(OrderClientType.app.name());
        PurchaseOrderVo vo = purchaseOrderAppService.createNormalOrder(orderVo);

        if (org.apache.commons.lang.StringUtils.isNotEmpty(cartIds)) {
            purchaseOrderAppService.delCart(cartIds, vo.getBuyerId());
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
        orderVo.setOrderClientType(OrderClientType.app.name());
        PurchaseOrderVo vo = purchaseOrderAppService.createPresellOrder(orderVo);
        return ResultData.createSuccess(vo);
    }

    /**
     * 取消订单
     */
    @RequestMapping("cancelOrder")
    @ResponseBody
    public ResultData cancelOrder(String orderId, OrderType orderType) {
        ValidateUtils.notNull(orderId, "订单id为空");
        ValidateUtils.notNull(orderType, "订单类型为空");

        String userId = AccountUtils.getCurrShopId();
        purchaseOrderAppService.cancelOrder(userId, orderId, orderType);
        return ResultData.createSuccess();
    }

    /**
     * 确认收货
     */
    @RequestMapping("confirmReceipt")
    @ResponseBody
    public ResultData confirmReceipt(String orderId, OrderType orderType) {
        ValidateUtils.notNull(orderId, "订单id为空");
        ValidateUtils.notNull(orderType, "订单类型为空");

        String userId = AccountUtils.getCurrShopId();
        purchaseOrderAppService.confirmReceipt(userId, orderId, orderType);
        return ResultData.createSuccess();
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
        //alipay wxpay
        Map<String, Object> resultMap = purchaseOrderAppService.toPay(orderId, payWay);
        resultMap.put("orderId", orderId);
        return ResultData.createSuccess(resultMap);
    }

    /**
     * 删除订单
     */
    @RequestMapping("deleteOrder")
    @ResponseBody
    public ResultData deleteOrder(String orderId, OrderType orderType) {
        ValidateUtils.notNull(orderId, "orderId不能为空");
        ValidateUtils.notNull(orderType, "orderType为空或类型错误");

        String userId = AccountUtils.getCurrShopId();
        purchaseOrderAppService.deleteOrder(orderId, userId, orderType);
        return ResultData.createSuccess();
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
        purchaseOrderAppService.shipNotice(orderId, userId, orderType);
        return ResultData.createSuccess();
    }


    /**
     * 评论
     */
    @RequestMapping("addEvaluation")
    @ResponseBody
    public ResultData addEvaluation(String modelJson) {
        PurchaseGoodsEvaluationVo purchaseGoodsEvaluationVo = JsonUtils.toObject(modelJson, PurchaseGoodsEvaluationVo.class);
        String userId = AccountUtils.getCurrShopId();
        purchaseGoodsEvaluationVo.setUser(userId);
        purchaseOrderAppService.addEvaluation(purchaseGoodsEvaluationVo);
        return ResultData.createSuccess();
    }

    /**
     * 获取用户购买订单列表
     * {purchaseOrder/getOrderList}
     * @return
     */
    @RequestMapping("getOrderList")
    @ResponseBody
    public ResultData getOrderList(String modelJson){
    	ValidateUtils.notNull(modelJson, "JSON数据为空");
    	PurchaseOrderQuery purchaseOrderQuery = JsonUtils.toObject(modelJson, PurchaseOrderQuery.class);
        String userId = AccountUtils.getCurrShopId();
        purchaseOrderQuery.setUserId(userId);
        purchaseOrderQuery.setOrderClientType(OrderClientType.app.name());
    	List<PurchaseOrderVo> orderVos = purchaseOrderAppService.getOrderList(purchaseOrderQuery);
    	return ResultData.createSuccess("orderList", orderVos);
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
		PurchaseOrderVo purchaseOrderVo = purchaseOrderAppService.getOrderDetail(id);
		return ResultData.createSuccess("purchaseOrderVo", purchaseOrderVo);
    }
    
    
    /**
     * 查询订单的支付信息
     * @param id
     * @return
     */
    @RequestMapping("getPayInfo")
    @ResponseBody
    public ResultData getPayInfo(String id){
    	
    	ValidateUtils.isTrue(StringUtils.isNotBlank(id), "订单id为空");
		PurchaseOrderVo purchaseOrderVo = purchaseOrderAppService.queryPayInfo(id);
		return ResultData.createSuccess("purchaseOrderVo", purchaseOrderVo);
		
    }

    /**
     * 预览订单概况
     * @return
     */
    @RequestMapping("preview")
    @ResponseBody
    public ResultData preview(String modelJson){

        ValidateUtils.notNull(modelJson, "订单信息不能为空");
        PurchaseOrderVo order = JsonUtils.toObject(modelJson, PurchaseOrderVo.class);
        ValidateUtils.notNull(order, "订单信息不能为空");
        ValidateUtils.notNull(order.getOrderType(), "订单类型不能为空");
        String userId = AccountUtils.getCurrShopId();
        order.setBuyerId(userId);
        order.setSubbranchId(userId);
        order.setClientType(ClientType.mobile.name());
        BaseOrder result = purchaseOrderAppService.preview(order);

        return ResultData.createSuccess(result);
    }

    @RequestMapping("saveInvoice")
    @ResponseBody
    public ResultData saveInvoice(String modelJson) {
        ValidateUtils.notNull(modelJson, "发票信息不能为空");
        InvoiceVo invoiceVo = JsonUtils.toObject(modelJson, InvoiceVo.class);
        purchaseOrderAppService.saveInvoice(invoiceVo);
        return ResultData.createSuccess();
    }

    @RequestMapping("getInvoice")
    @ResponseBody
    public ResultData getInvoice() {
        InvoiceVo invoiceVo = purchaseOrderAppService.getInvoiceInfo();
        return ResultData.createSuccess(invoiceVo);
    }
}
