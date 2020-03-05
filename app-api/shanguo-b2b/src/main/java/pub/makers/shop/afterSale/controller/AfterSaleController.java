package pub.makers.shop.afterSale.controller;

import com.dev.base.json.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.afterSale.enums.OrderFlowStatus;
import pub.makers.shop.afterSale.service.AfterSaleB2bService;
import pub.makers.shop.afterSale.vo.AfterSaleApply;
import pub.makers.shop.afterSale.vo.OrderItemAsFlowVo;
import pub.makers.shop.base.entity.DeliveryAddress;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderStatus;
import pub.makers.shop.purchaseOrder.pojo.PurchaseOrderQuery;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderB2bService;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;
import pub.makers.shop.store.vo.SalesReturnReasonVo;
import pub.makers.shop.user.utils.AccountUtils;

import java.util.List;

/**
 * Created by dy on 2017/7/11.
 */
@Controller
@RequestMapping("/afterSale")
public class AfterSaleController {
    @Autowired
    private AfterSaleB2bService afterSaleB2bService;

    @Autowired
    private PurchaseOrderB2bService purchaseOrderB2bService;


    /**
     * 售后列表页面
     * @return
     */
    @RequestMapping("/afterSaleList")
    public String afterSaleList(Model model) {
        AccountUtils.getCurrShopId();
        return "www/afterSale/afterSaleList";
    }


    /**
     * 申请售后
     *
     * @return
     */
    @RequestMapping("afterSaleInfoList")
    @ResponseBody
    public ResultData afterSaleInfoList(String orderJson) {
        PurchaseOrderQuery orderQuery = JsonUtils.toObject(orderJson, PurchaseOrderQuery.class);
        orderQuery.setUserId(AccountUtils.getCurrShopId());
        return afterSaleB2bService.afterSaleInfoList(orderQuery);
    }



    /**
     * 处理跳转到售后服务选择页面的控制器方法
     * @param orderId
     * @param skuId
     * @param model
     * @return
     */
    @RequestMapping("/afterSaleSelect/{orderId}/{skuId}")
    public String afterSaleSelect(@PathVariable String orderId, @PathVariable String skuId, Model model){
        AccountUtils.getCurrShopId();
        PurchaseOrderListVo purchaseOrderListVo = purchaseOrderB2bService.getGoodInOrderMsg(orderId, skuId, AccountUtils.getCurrShopId());
        model.addAttribute("orderNo", purchaseOrderListVo.getOrderNo());
        model.addAttribute("goodMsgList", purchaseOrderListVo);
        model.addAttribute("orderId", orderId);
        model.addAttribute("skuId", skuId);
        return "www/afterSale/afterSaleSelect";
    }

    /**
     * 跳转到仅退款的页面第一步
     * @param orderId
     * @param skuId
     * @param model
     * @return
     */
    @RequestMapping("/afterSaleRefund/{orderId}/{skuId}")
    public String afterSaleRefund(@PathVariable String orderId, @PathVariable String skuId, String operType, Model model){
        Integer totalTime = purchaseOrderB2bService.getSysDictReturnTime();

        PurchaseOrderListVo purchaseOrderListVo = purchaseOrderB2bService.getGoodInOrderMsg(orderId, skuId, AccountUtils.getCurrShopId());

        OrderItemAsFlowVo orderItemAsFlow = afterSaleB2bService.getAfterSaleOrderItemFlow(orderId, skuId, OrderBizType.purchase);

        model.addAttribute("orderNo", purchaseOrderListVo.getOrderNo());
        model.addAttribute("goodMsgList", purchaseOrderListVo);
        model.addAttribute("orderId", orderId);
        model.addAttribute("skuId", skuId);
        model.addAttribute("totalTime", totalTime);

        if ((((OrderStatus.ship.getDbData() + "").equals(purchaseOrderListVo.getStatus())
                && purchaseOrderListVo.getShipReturnTime() == 0) || ((OrderStatus.receive.getDbData() + "").equals(purchaseOrderListVo.getStatus()) && purchaseOrderListVo.getReturnTime() == 0)) && StringUtils.isBlank(operType)) {
            List<SalesReturnReasonVo> returnReasons = afterSaleB2bService.getReturnReason();
            model.addAttribute("returnReasonList", returnReasons);
            return "www/afterSale/afterSaleRefund";
        } else if (orderItemAsFlow != null && StringUtils.isNotBlank(operType) && "modify".equals(operType)) {
            model.addAttribute("orderItemAsFlow", orderItemAsFlow);
            List<SalesReturnReasonVo> returnReasons = afterSaleB2bService.getReturnReason();
            model.addAttribute("returnReasonList", returnReasons);
            model.addAttribute("operType", "modify");
            return "www/afterSale/afterSaleRefund";
        } else if (orderItemAsFlow != null && StringUtils.isNotBlank(operType) && "reject".equals(operType)) {
            model.addAttribute("operType", "reject");
            List<SalesReturnReasonVo> returnReasons = afterSaleB2bService.getReturnReason();
            model.addAttribute("returnReasonList", returnReasons);
            return "www/afterSale/afterSaleRefund";
        } else if(OrderFlowStatus.please_refund.name().equals(orderItemAsFlow.getFlowStatus())
                || OrderFlowStatus.refund.name().equals(orderItemAsFlow.getFlowStatus())) {
            model.addAttribute("orderItemAsFlow", orderItemAsFlow);
            return "www/afterSale/afterSaleRefund_1";
        } else if (OrderFlowStatus.success_refund.name().equals(orderItemAsFlow.getFlowStatus())) {
            model.addAttribute("orderItemAsFlow", orderItemAsFlow);
            return "www/afterSale/afterSaleRefund_2";
        } else if (OrderFlowStatus.cancel_refund.name().equals(orderItemAsFlow.getFlowStatus())
                || OrderFlowStatus.refuse_refund.name().equals(orderItemAsFlow.getFlowStatus())) {
            model.addAttribute("orderItemAsFlow", orderItemAsFlow);
            return "www/afterSale/afterSaleRefund_cancel";
        }
        return "www/afterSale/afterSaleRefund";
    }

    @RequestMapping("/afterSaleReturn/{orderId}/{skuId}")
    public String afterSaleReturn(@PathVariable String orderId, @PathVariable String skuId, String operType, Model model){
        AccountUtils.getCurrShopId();
        PurchaseOrderListVo purchaseOrderListVo = purchaseOrderB2bService.getGoodInOrderMsg(orderId, skuId, AccountUtils.getCurrShopId());

        OrderItemAsFlowVo orderItemAsFlow = afterSaleB2bService.getAfterSaleOrderItemFlow(orderId, skuId, OrderBizType.purchase);

        model.addAttribute("orderNo", purchaseOrderListVo.getOrderNo());
        model.addAttribute("goodMsgList", purchaseOrderListVo);
        model.addAttribute("orderId", orderId);
        model.addAttribute("skuId", skuId);

        if (orderItemAsFlow == null && StringUtils.isBlank(operType)) {
            List<SalesReturnReasonVo> returnReasons = afterSaleB2bService.getReturnReason();
            model.addAttribute("returnReasonList", returnReasons);
            return "www/afterSale/afterSaleReturn";
        } else if (orderItemAsFlow != null && StringUtils.isNotBlank(operType) && "modify".equals(operType)) {
            model.addAttribute("orderItemAsFlow", orderItemAsFlow);
            List<SalesReturnReasonVo> returnReasons = afterSaleB2bService.getReturnReason();
            model.addAttribute("returnReasonList", returnReasons);
            model.addAttribute("operType", "modify");
            return "www/afterSale/afterSaleReturn";
        } else if (orderItemAsFlow != null && StringUtils.isNotBlank(operType) && "reject".equals(operType)) {
            model.addAttribute("operType", "reject");
            List<SalesReturnReasonVo> returnReasons = afterSaleB2bService.getReturnReason();
            model.addAttribute("returnReasonList", returnReasons);
            return "www/afterSale/afterSaleReturn";
        } else if(OrderFlowStatus.please_return.name().equals(orderItemAsFlow.getFlowStatus())) {
            model.addAttribute("orderItemAsFlow", orderItemAsFlow);
            return "www/afterSale/afterSaleReturn_1";
        }/* else if (OrderFlowStatus.return_refund.name().equals(orderItemAsFlow.getFlowStatus())) {
            DeliveryAddress deliveryAddress = afterSaleB2bService.exchangeAddressInfo();
            model.addAttribute("orderItemAsFlow", orderItemAsFlow);
            model.addAttribute("deliveryAddress", deliveryAddress);
            return "www/afterSale/afterSaleReturn_2";
        }*/ else if (OrderFlowStatus.ret_shipping.name().equals(orderItemAsFlow.getFlowStatus())
                || OrderFlowStatus.return_refund.name().equals(orderItemAsFlow.getFlowStatus())
                || OrderFlowStatus.ret_confirm_receive.name().equals(orderItemAsFlow.getFlowStatus())) {
            model.addAttribute("orderItemAsFlow", orderItemAsFlow);
            DeliveryAddress deliveryAddress = afterSaleB2bService.exchangeAddressInfo();
            model.addAttribute("deliveryAddress", deliveryAddress);
            return "www/afterSale/afterSaleReturn_2";
        } else if (OrderFlowStatus.success_return.name().equals(orderItemAsFlow.getFlowStatus())) {
            model.addAttribute("orderItemAsFlow", orderItemAsFlow);
            return "www/afterSale/afterSaleReturn_3";
        } else if (OrderFlowStatus.cancel_return.name().equals(orderItemAsFlow.getFlowStatus())
                || OrderFlowStatus.refuse_return.name().equals(orderItemAsFlow.getFlowStatus())) {
            model.addAttribute("orderItemAsFlow", orderItemAsFlow);
            return "www/afterSale/afterSaleReturn_cancel";
        } else {
            return "www/afterSale/afterSaleReturn";
        }
    }

    @RequestMapping("/afterSaleExchange/{orderId}/{skuId}")
    public String afterSaleExchange(@PathVariable String orderId, @PathVariable String skuId, String operType, Model model){
        String shopId = AccountUtils.getCurrShopId();
        PurchaseOrderListVo purchaseOrderListVo = purchaseOrderB2bService.getGoodInOrderMsg(orderId, skuId, shopId);
        OrderItemAsFlowVo orderItemAsFlow = afterSaleB2bService.getAfterSaleOrderItemFlow(orderId, skuId, OrderBizType.purchase);

        model.addAttribute("orderNo", purchaseOrderListVo.getOrderNo());
        model.addAttribute("goodMsgList", purchaseOrderListVo);
        model.addAttribute("orderId", orderId);
        model.addAttribute("skuId", skuId);

        if (orderItemAsFlow == null && StringUtils.isBlank(operType)) {
            List<SalesReturnReasonVo> returnReasons = afterSaleB2bService.getReturnReason();
            model.addAttribute("returnReasonList", returnReasons);
            return "www/afterSale/afterSaleExchange";
        } else if (orderItemAsFlow != null && StringUtils.isNotBlank(operType) && "modify".equals(operType)) {
            model.addAttribute("orderItemAsFlow", orderItemAsFlow);
            List<SalesReturnReasonVo> returnReasons = afterSaleB2bService.getReturnReason();
            model.addAttribute("returnReasonList", returnReasons);
            model.addAttribute("operType", "modify");
            return "www/afterSale/afterSaleExchange";
        } else if (orderItemAsFlow != null && StringUtils.isNotBlank(operType) && "reject".equals(operType)) {
            model.addAttribute("operType", "reject");
            List<SalesReturnReasonVo> returnReasons = afterSaleB2bService.getReturnReason();
            model.addAttribute("returnReasonList", returnReasons);
            return "www/afterSale/afterSaleExchange";
        } else if(OrderFlowStatus.please_exchange.name().equals(orderItemAsFlow.getFlowStatus())
                || OrderFlowStatus.exchange.name().equals(orderItemAsFlow.getFlowStatus())
                || OrderFlowStatus.exc_shipping.name().equals(orderItemAsFlow.getFlowStatus())) {
            DeliveryAddress deliveryAddress = afterSaleB2bService.exchangeAddressInfo();
            model.addAttribute("deliveryAddress", deliveryAddress);
            model.addAttribute("orderItemAsFlow", orderItemAsFlow);
            return "www/afterSale/afterSaleExchange_1";
        } else if (OrderFlowStatus.success_exchange.name().equals(orderItemAsFlow.getFlowStatus())) {
            model.addAttribute("orderItemAsFlow", orderItemAsFlow);
            return "www/afterSale/afterSaleExchange_2";
        } else if (OrderFlowStatus.cancel_exchange.name().equals(orderItemAsFlow.getFlowStatus())
                || OrderFlowStatus.refuse_exchange.name().equals(orderItemAsFlow.getFlowStatus())) {
            model.addAttribute("orderItemAsFlow", orderItemAsFlow);
            return "www/afterSale/afterSaleExchange_cancel";
        }

        return "www/afterSale/afterSaleExchange";
    }

    /**
     * 申请售后
     *
     * @return
     */
    @RequestMapping("applyAfterSale")
    @ResponseBody
    public ResultData applyAfterSale(String modelJson) {
        ValidateUtils.notNull(modelJson, "请输入参数");
        AfterSaleApply apply = JsonUtils.toObject(modelJson, AfterSaleApply.class);
        afterSaleB2bService.applyAfterSale(apply);
        return ResultData.createSuccess();
    }

    /**
     * 撤销售后
     * @return
     */
    @RequestMapping("cancelAfterSale")
    @ResponseBody
    public ResultData cancelAfterSale(String flowId) {
        ValidateUtils.notNull(flowId, "请输入参数");
        afterSaleB2bService.cancelApply(flowId);
        return ResultData.createSuccess();
    }

    /**
     * 修改售后
     * @return
     */
    @RequestMapping("updateAfterSale")
    @ResponseBody
    public ResultData updateAfterSale(String modelJson) {
        ValidateUtils.notNull(modelJson, "请输入参数");
        AfterSaleApply apply = JsonUtils.toObject(modelJson, AfterSaleApply.class);
        afterSaleB2bService.updateAfterSale(apply);
        return ResultData.createSuccess();
    }



    /**
     * 修改售后
     * @return
     */
    @RequestMapping("editUserShipping")
    @ResponseBody
    public ResultData editUserShipping(String modelJson) {
        ValidateUtils.notNull(modelJson, "请输入参数");
        AfterSaleApply apply = JsonUtils.toObject(modelJson, AfterSaleApply.class);
        afterSaleB2bService.editUserShipping(apply);
        return ResultData.createSuccess();
    }
}
