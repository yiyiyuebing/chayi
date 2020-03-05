package pub.makers.shop.afterSale.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.afterSale.vo.AfterSaleApply;
import pub.makers.shop.afterSale.vo.AfterSaleTkResult;
import pub.makers.shop.afterSale.vo.OrderItemAsFlowVo;
import pub.makers.shop.afterSale.vo.OrderItemReplyVo;
import pub.makers.shop.base.entity.DeliveryAddress;
import pub.makers.shop.base.service.DeliveryAddressBizService;
import pub.makers.shop.base.service.SysDictBizService;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrder;
import pub.makers.shop.purchaseOrder.pojo.PurchaseOrderQuery;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderQueryService;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;
import pub.makers.shop.store.service.SalesReturnReasonBizService;
import pub.makers.shop.store.vo.SalesReturnReasonVo;
import pub.makers.shop.user.utils.AccountUtils;

import java.util.List;

/**
 * Created by dy on 2017/7/11.
 */
@Service
public class AfterSaleB2bService {
    @Reference(version="1.0.0")
    private AfterSaleBizService afterSaleBizService;
    @Reference(version = "1.0.0")
    private OrderItemReplyQueryService orderItemReplyQueryService;
    @Reference(version = "1.0.0")
    private OrderItemAsFlowQueryService orderItemAsFlowQueryService;
    @Reference(version = "1.0.0")
    private DeliveryAddressBizService deliveryAddressBizService;
    @Reference(version = "1.0.0")
    private AfterSaleTkBizService afterSaleTkBizService;
    @Reference(version = "1.0.0")
    private SysDictBizService sysDictBizService;
    @Reference(version = "1.0.0")
    private PurchaseOrderQueryService purchaseOrderQueryService;
    @Reference(version = "1.0.0")
    private SalesReturnReasonBizService salesReturnReasonBizService;

    public void applyAfterSale(AfterSaleApply apply) {
        String userId = AccountUtils.getCurrShopId();
        apply.setOperManId(userId);
        apply.setOrderBizType(OrderBizType.purchase);
        apply.setOperManType("buyer");
        apply.setOrderType(OrderType.normal);
        afterSaleBizService.applyAfterSale(apply);
    }
    public AfterSaleTkResult queryTkResult(AfterSaleApply apply){

        apply.setOrderBizType(OrderBizType.purchase);
        apply.setOperManType("buyer");

        return afterSaleTkBizService.queryTkResult(apply);
    }
    public List<SalesReturnReasonVo> getReturnReason() {
        return salesReturnReasonBizService.getReasonList();
    }

    public void editUserShipping(AfterSaleApply apply) {
        String userId = AccountUtils.getCurrShopId();
        apply.setOperManId(userId);
        apply.setOrderBizType(OrderBizType.purchase);
        apply.setOperManType("buyer");
        apply.setOrderType(OrderType.normal);
        afterSaleBizService.editUserShipping(apply);
    }

    public void updateAfterSale(AfterSaleApply apply) {
        String userId = AccountUtils.getCurrShopId();
        apply.setOperManId(userId);
        apply.setOrderBizType(OrderBizType.purchase);
        apply.setOperManType("buyer");
        apply.setOrderType(OrderType.normal);
        afterSaleBizService.updateAfterSale(apply);
    }
    public void cancelApply(String flowId) {
        AfterSaleApply apply = new AfterSaleApply();
        apply.setFlowId(flowId);
        String userId = AccountUtils.getCurrShopId();
        apply.setOperManId(userId);
        apply.setOrderBizType(OrderBizType.purchase);
        apply.setOperManType("buyer");
        apply.setOrderType(OrderType.normal);
        afterSaleBizService.cancelApply(apply);
    }

    public List<OrderItemReplyVo> getOrderReplyList(String orderId, String skuId) {
        PurchaseOrder purchaseOrder = purchaseOrderQueryService.get(orderId);
        ValidateUtils.notNull(purchaseOrder, "订单不存在");
        String userId = AccountUtils.getCurrShopId();
        ValidateUtils.isTrue(userId.equals(purchaseOrder.getBuyerId()), "只能操作自己的订单");
        return orderItemReplyQueryService.getOrderItemReplyList(orderId, skuId);
    }

    public OrderItemAsFlowVo getAfterSaleOrderItemFlow(String orderId, String skuId, OrderBizType orderBizType) {
        return orderItemAsFlowQueryService.getAfterSaleOrderItemFlow(orderId, skuId, orderBizType);
    }

    public void cancelAfterSale(AfterSaleApply apply) {
        afterSaleBizService.cancelApply(apply);
    }

    public DeliveryAddress exchangeAddressInfo() {
        return deliveryAddressBizService.exchangeAddressInfo();
    }

    public ResultData afterSaleInfoList(PurchaseOrderQuery orderQuery) {
        List<PurchaseOrderListVo> purchaseOrderListVos = purchaseOrderQueryService.getRefundOrderList(orderQuery);
        Long orderCount = purchaseOrderQueryService.countRefundOrderList(orderQuery);
        return ResultData.createSuccess(orderCount.intValue(), orderQuery.getPageNo(), orderQuery.getPageSize(), purchaseOrderListVos);
    }
}
