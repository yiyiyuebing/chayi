package pub.makers.shop.afterSale.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.afterSale.enums.FlowTargetType;
import pub.makers.shop.afterSale.vo.AfterSaleApply;
import pub.makers.shop.afterSale.vo.AfterSaleTkResult;
import pub.makers.shop.afterSale.vo.OrderItemReplyVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrder;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderQueryService;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.store.service.SalesReturnReasonBizService;
import pub.makers.shop.store.vo.SalesReturnReasonVo;
import pub.makers.shop.user.utils.AccountUtils;

import java.util.List;

/**
 * Created by kok on 2017/5/18.
 */
@Service
public class AfterSaleAppService {
    @Reference(version="1.0.0")
    private AfterSaleBizService afterSaleBizService;
    @Reference(version = "1.0.0")
    private OrderItemReplyQueryService orderItemReplyQueryService;
    @Reference(version = "1.0.0")
    private PurchaseOrderQueryService purchaseOrderQueryService;
    @Reference(version = "1.0.0")
    private SalesReturnReasonBizService salesReturnReasonBizService;
    @Reference(version = "1.0.0")
    private AfterSaleTkBizService afterSaleTkBizService;

    public void applyAfterSale(AfterSaleApply apply) {
        String userId = AccountUtils.getCurrShopId();
        apply.setOperManId(userId);
        apply.setOrderBizType(OrderBizType.purchase);
        apply.setOperManType("buyer");
        apply.setOrderType(OrderType.normal);
        if (FlowTargetType.order.equals(apply.getFlowTargetType())) {
            PurchaseOrderVo purchaseOrder = purchaseOrderQueryService.getOrderDetail(apply.getOrderId());
            ValidateUtils.isTrue(userId.equals(purchaseOrder.getBuyerId()), "只能操作自己的订单");
            apply.setOrderType(OrderType.valueOf(purchaseOrder.getOrderType()));
        }
        afterSaleBizService.applyAfterSale(apply);
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

    public void submitRefund(AfterSaleApply apply) {
        afterSaleBizService.refund(apply);
    }

    public List<SalesReturnReasonVo> getReturnReason() {
        return salesReturnReasonBizService.getReasonList();
    }

    public AfterSaleTkResult queryTkResult(AfterSaleApply apply){
        apply.setOrderBizType(OrderBizType.purchase);
        apply.setOperManType("buyer");
        return afterSaleTkBizService.queryTkResult(apply);
    }
}
