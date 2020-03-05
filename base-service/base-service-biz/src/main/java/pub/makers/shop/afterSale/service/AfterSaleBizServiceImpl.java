package pub.makers.shop.afterSale.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.common.entity.BoolType;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.afterSale.entity.OrderItemAsFlow;
import pub.makers.shop.afterSale.entity.OrderItemAsFlowDetail;
import pub.makers.shop.afterSale.entity.OrderItemReply;
import pub.makers.shop.afterSale.entity.OrderItemReplyImg;
import pub.makers.shop.afterSale.enums.FlowTargetType;
import pub.makers.shop.afterSale.enums.OperManType;
import pub.makers.shop.afterSale.enums.OrderAsType;
import pub.makers.shop.afterSale.enums.OrderFlowStatus;
import pub.makers.shop.afterSale.vo.AfterSaleApply;
import pub.makers.shop.afterSale.vo.AfterSaleTkResult;
import pub.makers.shop.base.entity.DeliveryAddress;
import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.service.DeliveryAddressService;
import pub.makers.shop.base.service.SysDictService;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.baseOrder.entity.OrderListPayment;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.PaymentQuery;
import pub.makers.shop.baseOrder.service.OrderPaymentBizService;
import pub.makers.shop.message.enums.MessageStatus;
import pub.makers.shop.message.service.MessageBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseOrderListService;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrder;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderService;
import pub.makers.shop.thirdpart.util.AliPayUtil;
import pub.makers.shop.thirdpart.util.WeixinPayUtil;
import pub.makers.shop.tradeOrder.entity.Indent;
import pub.makers.shop.tradeOrder.enums.IndentDealStatus;
import pub.makers.shop.tradeOrder.enums.IndentListStatus;
import pub.makers.shop.tradeOrder.service.IndentListService;
import pub.makers.shop.tradeOrder.service.IndentService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by kok on 2017/5/16.
 */
@Service(version = "1.0.0")
public class AfterSaleBizServiceImpl implements AfterSaleBizService {
    @Autowired
    private OrderItemAsFlowService orderItemAsFlowService;
    @Autowired
    private OrderItemAsFlowDetailService orderItemAsFlowDetailService;
    @Autowired
    private IndentService indentService;
    @Autowired
    private IndentListService indentListService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private OrderItemReplyService orderItemReplyService;
    @Autowired
    private OrderItemReplyImgService orderItemReplyImgService;
    @Autowired
    private WeixinPayUtil weixinPayUtil;
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @Autowired
    private PurchaseOrderListService purchaseOrderListService;
    @Autowired
    private AfterSaleTkBizService afterSaleTkBizService;
    @Autowired
    private AfterSaleHandlerManager afterSaleHandlerManager;
    @Autowired
    private OrderPaymentBizService orderPaymentBizService;
    @Reference(version = "1.0.0")
    private MessageBizService messageBizService;
    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @Override
    public void applyAfterSale(final AfterSaleApply apply) {
        ValidateUtils.notNull(apply, "参数错误");
        ValidateUtils.notNull(apply.getOrderBizType(), "订单类型为空");
        ValidateUtils.notNull(apply.getAsType(), "售后类型为空");
        ValidateUtils.notNull(apply.getReturnReason(), "售后原因为空");
        ValidateUtils.notNull(apply.getFlowTargetType(), "售后类型为空");
        if (FlowTargetType.order.equals(apply.getFlowTargetType())) {
            ValidateUtils.notNull(apply.getOrderId(), "订单id为空");
        } else {
            ValidateUtils.notNull(apply.getOrderListIdList(), "订单商品id为空");
            ValidateUtils.isTrue(!apply.getOrderListIdList().isEmpty(), "订单商品id为空");
        }
        ValidateUtils.notNull(apply.getOperManId(), "操作人为空");
        ValidateUtils.notNull(apply.getOperManType(), "操作人类型为空");

        final OrderItemAsFlow flow = new OrderItemAsFlow();
        flow.setOrderType(apply.getOrderBizType().name());
        flow.setAsType(apply.getAsType().name());
        flow.setReturnReason(apply.getReturnReason());
        flow.setAttachment(apply.getAttachment());
        flow.setDelFlag(BoolType.F.name());
        flow.setStartTime(new Date());
        flow.setDateCreated(new Date());
        flow.setTimeout(getTimeout(new Date(), apply.getOrderBizType()));
        flow.setFlowTargetType(apply.getFlowTargetType().name());
        switch (apply.getAsType()) {
            case refund:
                flow.setFlowStatus(OrderFlowStatus.please_refund.name());
                apply.setListStatus(IndentListStatus.return1);
                ValidateUtils.notNull(apply.getReturnAmount(), "退款金额为空");
                ValidateUtils.isTrue(apply.getReturnAmount().compareTo(BigDecimal.ZERO) > 0, "退款金额为空");
                break;
            case refund_return:
                flow.setFlowStatus(OrderFlowStatus.please_return.name());
                apply.setListStatus(IndentListStatus.return2);
                ValidateUtils.notNull(apply.getReturnAmount(), "退款金额为空");
                ValidateUtils.isTrue(apply.getReturnAmount().compareTo(BigDecimal.ZERO) > 0, "退款金额为空");
                break;
            case exchange:
                flow.setFlowStatus(OrderFlowStatus.please_exchange.name());
                apply.setListStatus(IndentListStatus.return3);
                break;
            default:
                flow.setFlowStatus(null);
                apply.setListStatus(null);
        }
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                //保存售后信息
                AfterSaleHandler handler = afterSaleHandlerManager.getHandler(apply.getOrderBizType(), apply.getOrderType());
                List<OrderItemAsFlow> flowList;
                if (FlowTargetType.order.equals(apply.getFlowTargetType())) {
                    apply.setOrderListIdList(null);
                    AfterSaleTkResult result = afterSaleTkBizService.queryTkResult(apply);
                    flowList = handler.createOrderFlow(flow, apply);
                    // 计算退款金额
                    BigDecimal returnAmount = BigDecimal.ZERO;
                    AfterSaleApply afterSaleApply = new AfterSaleApply();
                    BeanUtils.copyProperties(apply, afterSaleApply);
                    for (int i = 0; i < flowList.size() - 1; i++) {
                        OrderItemAsFlow orderItemAsFlow = flowList.get(i);
                        afterSaleApply.setOrderListIdList(Arrays.asList(StringUtils.split(orderItemAsFlow.getOrderListIds(), ",")));
                        AfterSaleTkResult afterSaleTkResult = afterSaleTkBizService.queryTkResult(afterSaleApply);
                        orderItemAsFlow.setReturnAmount(afterSaleTkResult.getMaxAmount());
                        orderItemAsFlow.setIsReturnCarriage(BoolType.F.name());
                        returnAmount = returnAmount.add(afterSaleTkResult.getMaxAmount());
                    }
                    OrderItemAsFlow itemAsFlow = flowList.get(flowList.size() - 1);
                    itemAsFlow.setIsReturnCarriage(result.getFreight() != null && result.getFreight().compareTo(BigDecimal.ZERO) > 0 ? BoolType.T.name() : BoolType.F.name());
                    itemAsFlow.setReturnAmount(result.getMaxAmount().subtract(returnAmount));
                } else {
                    OrderItemAsFlow orderItemAsFlow = handler.createListFlow(flow, apply);
                    apply.setOrderId(orderItemAsFlow.getOrderId());
                    // 退款金额
                    AfterSaleTkResult result = afterSaleTkBizService.queryTkResult(apply);
                    if (!OrderAsType.exchange.equals(apply.getAsType())) {
                        ValidateUtils.isTrue(result.getMaxAmount().compareTo(apply.getReturnAmount()) > -1, "退款金额不能大于实际支付金额");
                        orderItemAsFlow.setReturnAmount(apply.getReturnAmount());
                        orderItemAsFlow.setIsReturnCarriage(result.getFreight() != null && result.getFreight().compareTo(BigDecimal.ZERO) > 0 ? BoolType.T.name() : BoolType.F.name());
                    }
                    flowList = Lists.newArrayList(orderItemAsFlow);
                }
                String orderId = "";
                for (OrderItemAsFlow orderItemAsFlow : flowList) {
                    orderId = orderItemAsFlow.getOrderId();
                    orderItemAsFlowService.insert(orderItemAsFlow);
                    //创建售后变更记录
                    createFlowDetail(orderItemAsFlow, apply);
                    //创建协商记录
                    apply.setOperDesc("买家发起申请");
                    StringBuilder remark = new StringBuilder();
                    remark.append("发起了").append(apply.getAsType().getDisplayName()).append("申请，货物状态：").append(IndentListStatus.valueOf(orderItemAsFlow.getOrderListStatus()).getDisplayName())
                            .append("，原因：").append(orderItemAsFlow.getReturnReason());
                    if (OrderAsType.exchange.equals(apply.getAsType())) {
                        remark.append("。");
                    } else {
                        remark.append("，退款金额：").append(orderItemAsFlow.getReturnAmount()).append("元");
                    }
                    apply.setRemark(remark.toString());
                    createItemReply(orderItemAsFlow, apply);
                    apply.setOperDesc("优茶联处理");
                    remark.setLength(0);
                    remark.append("<p>如优茶联同意，请按照给出的退货地址退货</p>").append("<p>如优茶联拒绝，您可修改申请，卖家会重新处理</p>")
                        .append("<p>如优茶联在%s内未处理，申请将自动达成，请按系统给出的地址退货</p>")
                        .append("<p>请勿相信任何人给您发来的可以退款的链接，以免钱款被骗</p>");
                    apply.setRemark(remark.toString());
                    apply.setOperManType(OperManType.pending.name());
                    createItemReply(orderItemAsFlow, apply);
                }
                // 后台消息提醒
                String orderNo;
                String isReturnCarriage = flowList.get(flowList.size() - 1).getIsReturnCarriage();
                if (OrderBizType.trade.equals(apply.getOrderBizType())) {
                    Indent indent = indentService.getById(orderId);
                    orderNo = indent.getName();
                    if (BoolType.T.name().equals(isReturnCarriage)) {
                        indentService.update(Update.byId(orderId).set("is_return_carriage", BoolType.T.name()));
                    }
                } else {
                    PurchaseOrder purchaseOrder = purchaseOrderService.getById(orderId);
                    orderNo = purchaseOrder.getOrderNo();
                    if (BoolType.T.name().equals(isReturnCarriage)) {
                        purchaseOrderService.update(Update.byId(orderId).set("is_return_carriage", BoolType.T.name()));
                    }
                }
                if (OrderAsType.refund.name().equals(flow.getAsType())) {
                    messageBizService.addNoticeMessage(apply.getOperManId(), apply.getOperManId(), MessageStatus.refund1.getDbData(),
                            "[" + apply.getOrderBizType().getName() + "]" + orderNo + " 客户申请退款，请及时处理！");
                } else if (OrderAsType.refund_return.name().equals(flow.getAsType())) {
                    messageBizService.addNoticeMessage(apply.getOperManId(), apply.getOperManId(), MessageStatus.refund2.getDbData(),
                            "[" + apply.getOrderBizType().getName() + "]" + orderNo + " 客户申请退货，请及时处理！");
                }
            }
        });
    }

    @Override
    public void updateAfterSale(final AfterSaleApply apply) {
        ValidateUtils.notNull(apply, "参数错误");
        ValidateUtils.notNull(apply.getFlowId(), "售后id为空");
        ValidateUtils.notNull(apply.getReturnReason(), "售后原因为空");
        ValidateUtils.notNull(apply.getOperManId(), "操作人为空");
        ValidateUtils.notNull(apply.getOperManType(), "操作人类型为空");

        final OrderItemAsFlow flow = orderItemAsFlowService.getById(apply.getFlowId());

        ValidateUtils.notNull(flow, "售后流程不存在");
        ValidateUtils.isTrue(OrderFlowStatus.please_refund.name().equals(flow.getFlowStatus()) || OrderFlowStatus.please_exchange.name().equals(flow.getFlowStatus())
                || OrderFlowStatus.please_return.name().equals(flow.getFlowStatus()), "售后流程状态错误");
        flow.setReturnReason(apply.getReturnReason());
        flow.setReturnAmount(apply.getReturnAmount());
        flow.setAttachment(apply.getAttachment());
        if (apply.getAsType() != null) {
            flow.setAsType(apply.getAsType().name());
            switch (apply.getAsType()) {
                case refund:
                    flow.setFlowStatus(OrderFlowStatus.please_refund.name());
                    apply.setListStatus(IndentListStatus.return1);
                    break;
                case refund_return:
                    flow.setFlowStatus(OrderFlowStatus.please_return.name());
                    apply.setListStatus(IndentListStatus.return2);
                    break;
                case exchange:
                    flow.setFlowStatus(OrderFlowStatus.please_exchange.name());
                    apply.setListStatus(IndentListStatus.return3);
                    break;
                default:
                    flow.setFlowStatus(null);
                    apply.setListStatus(null);
            }
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                //更新售后流程信息
                orderItemAsFlowService.update(Update.byId(flow.getId()).set("return_reason", flow.getReturnReason()).set("return_amount", flow.getReturnAmount())
                        .set("as_type", flow.getAsType()).set("flow_status", flow.getFlowStatus())
                        .set("attachment", flow.getAttachment()).set("last_updated", new Date()));

                //更新订单状态
                if (apply.getListStatus() != null) {
                    List<String> ids = Lists.newArrayList(flow.getOrderListIds().split(","));
                    if (OrderBizType.trade.name().equals(flow.getOrderType())) {
                        for (String id : ids) {
                            indentListService.update(Update.byId(id).set("status", apply.getListStatus().name()).set("last_updated", new Date()));
                        }
                    } else if (OrderBizType.purchase.name().equals(flow.getOrderType())) {
                        for (String id : ids) {
                            purchaseOrderListService.update(Update.byId(id).set("status", apply.getListStatus().name()).set("last_updated", new Date()));
                        }
                    }
                }
                //创建售后变更记录
                createFlowDetail(flow, apply);
                //创建协商记录
                apply.setOperDesc("买家修改了申请");
                StringBuilder remark = new StringBuilder();
                remark.append("发起了").append(OrderAsType.valueOf(flow.getAsType()).getDisplayName()).append("申请，货物状态：").append(IndentListStatus.valueOf(flow.getOrderListStatus()).getDisplayName())
                        .append("，原因：").append(flow.getReturnReason());
                if (OrderAsType.exchange.name().equals(flow.getAsType())) {
                    remark.append("。");
                } else {
                    remark.append("，退款金额：").append(flow.getReturnAmount()).append("元");
                }
                apply.setRemark(remark.toString());
                createItemReply(flow, apply);
                apply.setOperDesc("优茶联处理");
                remark.setLength(0);
                remark.append("<p>如优茶联同意，请按照给出的退货地址退货</p>").append("<p>如优茶联拒绝，您可修改申请，卖家会重新处理</p>")
                        .append("<p>如优茶联在%s内未处理，申请将自动达成，请按系统给出的地址退货</p>")
                        .append("<p>请勿相信任何人给您发来的可以退款的链接，以免钱款被骗</p>");
                apply.setRemark(remark.toString());
                apply.setOperManType(OperManType.pending.name());
                createItemReply(flow, apply);
            }
        });
    }

    @Override
    public void cancelApply(AfterSaleApply apply) {
        ValidateUtils.notNull(apply, "参数错误");
        ValidateUtils.notNull(apply.getFlowId(), "售后id为空");
        ValidateUtils.notNull(apply.getOperManId(), "操作人为空");
        ValidateUtils.notNull(apply.getOperManType(), "操作人类型为空");

        final OrderItemAsFlow flow = orderItemAsFlowService.getById(apply.getFlowId());

        ValidateUtils.notNull(flow, "售后流程不存在");
        ValidateUtils.isTrue(OrderFlowStatus.please_refund.name().equals(flow.getFlowStatus()) || OrderFlowStatus.please_exchange.name().equals(flow.getFlowStatus())
                || OrderFlowStatus.please_return.name().equals(flow.getFlowStatus()), "售后流程状态错误");

        if (OrderFlowStatus.please_refund.name().equals(flow.getFlowStatus())) {
            flow.setFlowStatus(OrderFlowStatus.cancel_refund.name());
        } else if (OrderFlowStatus.please_exchange.name().equals(flow.getFlowStatus())) {
            flow.setFlowStatus(OrderFlowStatus.cancel_exchange.name());
        } else if (OrderFlowStatus.please_return.name().equals(flow.getFlowStatus())) {
            flow.setFlowStatus(OrderFlowStatus.cancel_return.name());
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                //更新订单商品状态
                List<String> ids = Lists.newArrayList(flow.getOrderListIds().split(","));
                String cancelAfter;
                if (IndentListStatus.ship.name().equals(flow.getOrderListStatus())) {
                    cancelAfter = "ship_cancel_after";
                } else {
                    cancelAfter = "receive_cancel_after";
                }
                if (OrderBizType.trade.name().equals(flow.getOrderType())) {
                    for (String id : ids) {
                        indentListService.update(Update.byId(id).set("status", flow.getOrderListStatus()).set(cancelAfter, BoolType.T.name()).set("last_updated", new Date()));
                    }
                    if (BoolType.T.name().equals(flow.getIsReturnCarriage())) {
                        indentService.update(Update.byId(flow.getOrderId()).set("is_return_carriage", BoolType.F.name()));
                    }
                } else if (OrderBizType.purchase.name().equals(flow.getOrderType())) {
                    for (String id : ids) {
                        purchaseOrderListService.update(Update.byId(id).set("status", flow.getOrderListStatus()).set(cancelAfter, BoolType.T.name()).set("last_updated", new Date()));
                    }
                    if (BoolType.T.name().equals(flow.getIsReturnCarriage())) {
                        purchaseOrderService.update(Update.byId(flow.getOrderId()).set("is_return_carriage", BoolType.F.name()));
                    }
                }
                //更新售后流程状态
                orderItemAsFlowService.update(Update.byId(flow.getId()).set("flow_status", flow.getFlowStatus()).set("last_updated", new Date())
                        .set("end_time", new Date()));
                //创建售后变更记录
                createFlowDetail(flow, apply);
                //创建协商记录
                apply.setOperDesc("买家撤销申请");
                apply.setRemark(String.format("买家撤销了本次%s服务申请。", OrderAsType.valueOf(flow.getAsType()).getDisplayName()));
                createItemReply(flow, apply);
            }
        });
    }

    @Override
    public void agreeApply(final AfterSaleApply apply) {
        ValidateUtils.notNull(apply, "参数错误");
        ValidateUtils.notNull(apply.getFlowId(), "售后id为空");
        ValidateUtils.notNull(apply.getOperDesc(), "操作备注为空");
        ValidateUtils.notNull(apply.getOperManId(), "操作人为空");
        ValidateUtils.notNull(apply.getOperManType(), "操作人类型为空");

        final OrderItemAsFlow flow = orderItemAsFlowService.getById(apply.getFlowId());

        ValidateUtils.notNull(flow, "售后流程不存在");
        ValidateUtils.isTrue(OrderFlowStatus.please_refund.name().equals(flow.getFlowStatus()) || OrderFlowStatus.please_exchange.name().equals(flow.getFlowStatus())
                || OrderFlowStatus.please_return.name().equals(flow.getFlowStatus()), "售后流程状态错误");

        // 申请退款流程通过则为同意退款
        if (OrderFlowStatus.please_refund.name().equals(flow.getFlowStatus())) {
            flow.setFlowStatus(OrderFlowStatus.refund.name());
            flow.setOrderListStatus(IndentListStatus.returning1.name());
        }
        // TODO 申请退货和申请换货同意后第二个状态可以共用一个{等待寄件}
        // 申请换货流程通过需要寄货并填写运单号
        else if (OrderFlowStatus.please_exchange.name().equals(flow.getFlowStatus())) {
            flow.setFlowStatus(OrderFlowStatus.exchange.name());
            flow.setOrderListStatus(IndentListStatus.returning3.name());
            flow.setBuyerTimeout(getTimeout(new Date(), OrderBizType.valueOf(flow.getOrderType())));
        }
        // 申请退货流程通过需要填写运单号
        else if (OrderFlowStatus.please_return.name().equals(flow.getFlowStatus())) {
            flow.setFlowStatus(OrderFlowStatus.return_refund.name());
            flow.setOrderListStatus(IndentListStatus.returning2.name());
            flow.setBuyerTimeout(getTimeout(new Date(), OrderBizType.valueOf(flow.getOrderType())));
        }

        if (OrderFlowStatus.exchange.name().equals(flow.getFlowStatus())) {
            apply.setReturnAmount(null);
        } else {
            ValidateUtils.notNull(apply.getReturnAmount(), "退款金额为空");
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                //更新订单商品状态
                List<String> ids = Lists.newArrayList(flow.getOrderListIds().split(","));
                if (OrderBizType.trade.name().equals(flow.getOrderType())) {
                    for (String id : ids) {
                        indentListService.update(Update.byId(id).set("status", flow.getOrderListStatus()).set("last_updated", new Date()));
                    }
                } else if (OrderBizType.purchase.name().equals(flow.getOrderType())) {
                    for (String id : ids) {
                        purchaseOrderListService.update(Update.byId(id).set("status", flow.getOrderListStatus()).set("last_updated", new Date()));
                    }
                }
                //更新售后流程状态
                orderItemAsFlowService.update(Update.byId(flow.getId()).set("flow_status", flow.getFlowStatus()).set("return_amount", apply.getReturnAmount()).set("last_updated", new Date()));
                //创建售后变更记录
                createFlowDetail(flow, apply);
                //创建协商记录
                apply.setOperDesc("优茶联已同意申请");
                apply.setRemark("优茶联同意本次" + OrderAsType.valueOf(flow.getAsType()).getDisplayName() + "服务申请");
                createItemReply(flow, apply);
                if (!OrderAsType.refund.name().equals(flow.getAsType())) {
                    apply.setOperDesc("优茶联已确认收货地址");
                    List<DeliveryAddress> addressList = deliveryAddressService.list(Conds.get());
                    if (!addressList.isEmpty()) {
                        Map<String, Object> data = Maps.newHashMap();
                        data.put("address", addressList.get(0));
                        apply.setRemark(FreeMarkerHelper.getValueFromTpl("afterSale/deliveryAddress.ftl", data));
                    }
                    createItemReply(flow, apply);
                }
            }
        });
    }

    @Override
    public void rejectApply(final AfterSaleApply apply) {
        ValidateUtils.notNull(apply, "参数错误");
        ValidateUtils.notNull(apply.getFlowId(), "售后id为空");
        ValidateUtils.notNull(apply.getOperDesc(), "操作备注为空");
        ValidateUtils.notNull(apply.getOperManId(), "操作人为空");
        ValidateUtils.notNull(apply.getOperManType(), "操作人类型为空");

        final OrderItemAsFlow flow = orderItemAsFlowService.getById(apply.getFlowId());

        ValidateUtils.notNull(flow, "售后流程不存在");
        ValidateUtils.isTrue(OrderFlowStatus.please_refund.name().equals(flow.getFlowStatus()) || OrderFlowStatus.please_exchange.name().equals(flow.getFlowStatus())
                || OrderFlowStatus.please_return.name().equals(flow.getFlowStatus()), "售后流程状态错误");

        // TODO 售后申请已拒绝可以共用一个已拒绝的状态，不需要定义这么多状态
        if (OrderFlowStatus.please_refund.name().equals(flow.getFlowStatus())) {
            flow.setFlowStatus(OrderFlowStatus.refuse_refund.name());
        } else if (OrderFlowStatus.please_exchange.name().equals(flow.getFlowStatus())) {
            flow.setFlowStatus(OrderFlowStatus.refuse_exchange.name());
        } else if (OrderFlowStatus.please_return.name().equals(flow.getFlowStatus())) {
            flow.setFlowStatus(OrderFlowStatus.refuse_return.name());
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                //更新订单商品状态
                List<String> ids = Lists.newArrayList(flow.getOrderListIds().split(","));
                if (OrderBizType.trade.name().equals(flow.getOrderType())) {
                    for (String id : ids) {
                        indentListService.update(Update.byId(id).set("status", flow.getOrderListStatus()).set("last_updated", new Date()));
                    }
                    if (BoolType.T.name().equals(flow.getIsReturnCarriage())) {
                        indentService.update(Update.byId(flow.getOrderId()).set("is_return_carriage", BoolType.F.name()));
                    }
                } else if (OrderBizType.purchase.name().equals(flow.getOrderType())) {
                    for (String id : ids) {
                        purchaseOrderListService.update(Update.byId(id).set("status", flow.getOrderListStatus()).set("last_updated", new Date()));
                    }
                    if (BoolType.T.name().equals(flow.getIsReturnCarriage())) {
                        purchaseOrderService.update(Update.byId(flow.getOrderId()).set("is_return_carriage", BoolType.F.name()));
                    }
                }
                //更新售后流程状态
                orderItemAsFlowService.update(Update.byId(flow.getId()).set("flow_status", flow.getFlowStatus()).set("last_updated", new Date())
                        .set("end_time", new Date()));
                //创建售后变更记录
                createFlowDetail(flow, apply);
                //创建协商记录
                apply.setOperDesc("优茶联拒绝申请");
                apply.setRemark(String.format("优茶联拒绝本次%s服务申请。", OrderAsType.valueOf(flow.getAsType()).getDisplayName()));
                createItemReply(flow, apply);
            }
        });
    }

    @Override
    public void refund(final AfterSaleApply apply) {
        ValidateUtils.notNull(apply, "参数错误");
        ValidateUtils.notNull(apply.getFlowId(), "售后id为空");
        ValidateUtils.notNull(apply.getReturnAmount(), "退款金额为空");
        ValidateUtils.notNull(apply.getOperDesc(), "操作备注为空");
        ValidateUtils.notNull(apply.getOperManId(), "操作人为空");
        ValidateUtils.notNull(apply.getOperManType(), "操作人类型为空");

        final OrderItemAsFlow flow = orderItemAsFlowService.getById(apply.getFlowId());
        ValidateUtils.notNull(flow, "售后流程不存在");
        ValidateUtils.isTrue(OrderFlowStatus.refund.name().equals(flow.getFlowStatus()) || OrderFlowStatus.ret_confirm_receive.name().equals(flow.getFlowStatus()), "售后流程状态错误");
        ValidateUtils.isTrue(flow.getReturnAmount().compareTo(apply.getReturnAmount()) != -1, "退款金额大于允许退款金额");

        if (OrderFlowStatus.refund.name().equals(flow.getFlowStatus())) {
            flow.setFlowStatus(OrderFlowStatus.success_refund.name());
        } else if (OrderFlowStatus.ret_confirm_receive.name().equals(flow.getFlowStatus())) {
            flow.setFlowStatus(OrderFlowStatus.success_return.name());
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                //更新售后流程状态
                orderItemAsFlowService.update(Update.byId(flow.getId()).set("flow_status", flow.getFlowStatus())
                        .set("last_updated", new Date()).set("end_time", new Date()).set("return_amount", apply.getReturnAmount()));

                //创建售后变更记录
                createFlowDetail(flow, apply);
                //创建协商记录
                apply.setOperDesc(null);
                apply.setRemark(String.format("退还给买家%s元", apply.getReturnAmount()));
                createItemReply(flow, apply);
                apply.setOperDesc("退款成功");
                apply.setRemark("请到支付宝/微信查看退款记录");
                createItemReply(flow, apply);

                BigDecimal totalFee = BigDecimal.ZERO;

                String tradeOrderNo = "";
                Integer payType = 0;
                String orderClientType = null;
                if (OrderBizType.trade.name().equals(flow.getOrderType())) {
                    Indent indent = indentService.getById(flow.getOrderId());
                    PaymentQuery paymentQuery = new PaymentQuery();
                    paymentQuery.setOrder(indent);
                    paymentQuery.setOrderBizType(OrderBizType.trade);
                    paymentQuery.setOrderType(OrderType.valueOf(indent.getOrderType()));
                    OrderListPayment orderListPayment = orderPaymentBizService.getFinalOrderListByBaseOrder(paymentQuery);

                    ValidateUtils.notNull(orderListPayment, "商户订单为空");
                    tradeOrderNo = orderListPayment.getId();
                    totalFee = orderListPayment.getWaitpayAmount();
                    payType = indent.getPayType();
                } else if (OrderBizType.purchase.name().equals(flow.getOrderType())) {
                    PurchaseOrder order = purchaseOrderService.getById(flow.getOrderId());
                    PaymentQuery paymentQuery = new PaymentQuery();
                    paymentQuery.setOrder(order);
                    paymentQuery.setOrderBizType(OrderBizType.purchase);
                    paymentQuery.setOrderType(OrderType.valueOf(order.getOrderType()));
                    OrderListPayment orderListPayment = orderPaymentBizService.getFinalOrderListByBaseOrder(paymentQuery);

                    ValidateUtils.notNull(orderListPayment, "商户订单为空");
                    tradeOrderNo = orderListPayment.getId();

                    totalFee = orderListPayment.getWaitpayAmount();
                    payType = Integer.valueOf(order.getPayType());
                    orderClientType = order.getOrderClientType();
                }

                Map<String, Object> result;
                if (payType == 1) {
                    result = weixinPayUtil.wxRefund(flow.getId(), tradeOrderNo, totalFee, apply.getReturnAmount(), flow.getOrderType(), orderClientType);
                } else {
                    result = AliPayUtil.refund(tradeOrderNo, apply.getReturnAmount(), flow.getId());
                }
                ValidateUtils.isTrue((Boolean) result.get("success"), result.get("msg") == null ? "系统繁忙，请重试" : result.get("msg").toString());

                String listStatus;
                if (OrderFlowStatus.success_refund.name().equals(flow.getFlowStatus())) {
                    listStatus = IndentListStatus.returned1.name();
                } else {
                    listStatus = IndentListStatus.returned2.name();
                }
                List<String> ids = Lists.newArrayList(flow.getOrderListIds().split(","));
                BigDecimal returnAmount = apply.getReturnAmount().divide(new BigDecimal(ids.size()), 2, RoundingMode.HALF_UP);
                if (OrderBizType.trade.name().equals(flow.getOrderType())) {
                    // 更新订单商品状态和退款金额
                    for (String id : ids) {
                        indentListService.update(Update.byId(id).set("status", listStatus).set("return_amount", returnAmount).set("last_updated", new Date()));
                    }
                    // 更新订单退款金额
                    Indent indent = indentService.getById(flow.getOrderId());
                    BigDecimal orderReturnAmount = indent.getRefundAmount() == null ? BigDecimal.ZERO : indent.getRefundAmount();
                    indentService.update(Update.byId(indent.getId()).set("refund_amount", orderReturnAmount.add(apply.getReturnAmount())));
                    // 订单商品都退款则订单交易关闭
                    Long count = indentListService.count(Conds.get().eq("indent_id", flow.getOrderId()).eq("gift_flag", BoolType.F.name()).ne("status", IndentListStatus.returned1.name()).ne("status", IndentListStatus.returned2.name()));
                    if (count.equals(0l)) {
                        indentService.update(Update.byId(flow.getOrderId()).set("deal_status", IndentDealStatus.deal_close.getDbData()).set("finish_time", new Date()));
                    }
                } else if (OrderBizType.purchase.name().equals(flow.getOrderType())) {
                    // 更新订单商品状态和退款金额
                    for (String id : ids) {
                        purchaseOrderListService.update(Update.byId(id).set("status", listStatus).set("return_amount", returnAmount).set("last_updated", new Date()));
                    }
                    // 更新订单退款金额
                    PurchaseOrder purchaseOrder = purchaseOrderService.getById(flow.getOrderId());
                    BigDecimal orderReturnAmount = purchaseOrder.getRefundAmount() == null ? BigDecimal.ZERO : purchaseOrder.getRefundAmount();
                    purchaseOrderService.update(Update.byId(purchaseOrder.getId()).set("refund_amount", orderReturnAmount.add(apply.getReturnAmount())));
                    // 订单商品都退款则订单交易关闭
                    Long count = purchaseOrderListService.count(Conds.get().eq("order_id", flow.getOrderId()).eq("gift_flag", BoolType.F.name()).ne("status", IndentListStatus.returned1.name()).ne("status", IndentListStatus.returned2.name()));
                    if (count.equals(0l)) {
                        purchaseOrderService.update(Update.byId(flow.getOrderId()).set("deal_status", IndentDealStatus.deal_close.getDbData()).set("finish_time", new Date()));
                    }
                }

                // TODO 给用户发送一个消息推送，提示订单退款成功
            }
        });
    }


    @Override
    public void editUserShipping(final AfterSaleApply apply) {
        ValidateUtils.notNull(apply, "参数错误");
        ValidateUtils.notNull(apply.getFlowId(), "售后id为空");
        ValidateUtils.notNull(apply.getFreightNo(), "运单号为空");
        ValidateUtils.notNull(apply.getFreightCompany(), "物流公司为空");
        ValidateUtils.notNull(apply.getOperManId(), "操作人为空");
        ValidateUtils.notNull(apply.getOperManType(), "操作人类型为空");

        final OrderItemAsFlow flow = orderItemAsFlowService.getById(apply.getFlowId());
        ValidateUtils.notNull(flow, "售后流程不存在");
        ValidateUtils.isTrue(OrderFlowStatus.exchange.name().equals(flow.getFlowStatus()) || OrderFlowStatus.return_refund.name().equals(flow.getFlowStatus()), "售后流程状态错误");
        if (OrderBizType.purchase.name().equals(flow.getOrderType())) {
            PurchaseOrder order = purchaseOrderService.getById(flow.getOrderId());
            ValidateUtils.isTrue(order.getBuyerId().equals(apply.getOperManId()), "只能操作自己的订单");
        } else {
            Indent order = indentService.getById(flow.getOrderId());
            ValidateUtils.isTrue(order.getBuyerId().equals(apply.getOperManId()), "只能操作自己的订单");
        }

        if (OrderFlowStatus.exchange.name().equals(flow.getFlowStatus())) {
            flow.setFlowStatus(OrderFlowStatus.exc_shipping.name());
        } else if (OrderFlowStatus.return_refund.name().equals(flow.getFlowStatus())) {
            flow.setFlowStatus(OrderFlowStatus.ret_shipping.name());
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                //更新售后流程
                orderItemAsFlowService.update(Update.byId(flow.getId()).set("last_updated", new Date()).set("user_freight_no", apply.getFreightNo()).set("user_freight_company", apply.getFreightCompany()).set("flow_status", flow.getFlowStatus()));
                //创建售后变更记录
                createFlowDetail(flow, apply);
                //创建协商记录
                apply.setOperDesc("买家已经退货");
                apply.setRemark(String.format("买家退货。物流公司：%s，物流单号：%s。", apply.getFreightCompany(), apply.getFreightNo()));
                createItemReply(flow, apply);
            }
        });
    }

    @Override
    public void editUserShippingManager(final AfterSaleApply apply) {
        ValidateUtils.notNull(apply, "参数错误");
        ValidateUtils.notNull(apply.getFlowId(), "售后id为空");
        ValidateUtils.notNull(apply.getFreightNo(), "运单号为空");
        ValidateUtils.notNull(apply.getFreightCompany(), "物流公司为空");
        ValidateUtils.notNull(apply.getOperManId(), "操作人为空");
        ValidateUtils.notNull(apply.getOperManType(), "操作人类型为空");

        final OrderItemAsFlow flow = orderItemAsFlowService.getById(apply.getFlowId());
        ValidateUtils.notNull(flow, "售后流程不存在");
        ValidateUtils.isTrue(OrderFlowStatus.exchange.name().equals(flow.getFlowStatus()) || OrderFlowStatus.return_refund.name().equals(flow.getFlowStatus()), "售后流程状态错误");

        if (OrderFlowStatus.exchange.name().equals(flow.getFlowStatus())) {
            flow.setFlowStatus(OrderFlowStatus.exc_shipping.name());
        } else if (OrderFlowStatus.return_refund.name().equals(flow.getFlowStatus())) {
            flow.setFlowStatus(OrderFlowStatus.ret_shipping.name());
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                //更新售后流程
                orderItemAsFlowService.update(Update.byId(flow.getId()).set("last_updated", new Date()).set("user_freight_no", apply.getFreightNo()).set("user_freight_company", apply.getFreightCompany()).set("flow_status", flow.getFlowStatus()));
                //创建售后变更记录
                createFlowDetail(flow, apply);
                //创建协商记录
                apply.setOperDesc("买家已经退货");
                apply.setRemark(String.format("买家退货。物流公司：%s，物流单号：%s。", apply.getFreightCompany(), apply.getFreightNo()));
                createItemReply(flow, apply);
            }
        });
    }

    @Override
    public void acceptShipping(final AfterSaleApply apply) {
        ValidateUtils.notNull(apply, "参数错误");
        ValidateUtils.notNull(apply.getFlowId(), "售后id为空");
        ValidateUtils.notNull(apply.getOperDesc(), "操作备注为空");
        ValidateUtils.notNull(apply.getOperManId(), "操作人为空");
        ValidateUtils.notNull(apply.getOperManType(), "操作人类型为空");

        final OrderItemAsFlow flow = orderItemAsFlowService.getById(apply.getFlowId());
        ValidateUtils.notNull(flow, "售后流程不存在");
        ValidateUtils.isTrue(OrderFlowStatus.exc_shipping.name().equals(flow.getFlowStatus()) || OrderFlowStatus.ret_shipping.name().equals(flow.getFlowStatus()), "售后流程状态错误");
        ValidateUtils.notNull(flow.getUserFreightNo(), "用户运单号为空");

        if (OrderFlowStatus.exc_shipping.name().equals(flow.getFlowStatus())) {
            flow.setFlowStatus(OrderFlowStatus.exc_confirm_receive.name());
        } else if (OrderFlowStatus.ret_shipping.name().equals(flow.getFlowStatus())) {
            flow.setFlowStatus(OrderFlowStatus.ret_confirm_receive.name());
        }
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                //更新售后流程状态
                Update update = Update.byId(flow.getId());
                if (apply.getReturnAmount() != null && apply.getReturnAmount().compareTo(BigDecimal.ZERO) > 0) {
                    update.set("return_amount", apply.getReturnAmount());
                    String remark = String.format("退款金额为%s元", apply.getReturnAmount());
                    if (StringUtils.isNotBlank(apply.getRemark())) {
                        remark += "，原因：" + apply.getRemark();
                    }
                    apply.setRemark(remark);
                }
                orderItemAsFlowService.update(update.set("flow_status", flow.getFlowStatus()).set("last_updated", new Date()));
                //创建售后变更记录
                createFlowDetail(flow, apply);
                //创建协商记录
                apply.setOperDesc("优茶联已确认收货");
                if (StringUtils.isBlank(apply.getRemark())) {
                    apply.setRemark("优茶联已确认收货");
                }
                createItemReply(flow, apply);
            }
        });
    }

    @Override
    public void changeGood(final AfterSaleApply apply) {
        ValidateUtils.notNull(apply, "参数错误");
        ValidateUtils.notNull(apply.getFlowId(), "售后id为空");
        ValidateUtils.notNull(apply.getFreightNo(), "运单号为空");
        ValidateUtils.notNull(apply.getOperDesc(), "操作备注为空");
        ValidateUtils.notNull(apply.getOperManId(), "操作人为空");
        ValidateUtils.notNull(apply.getOperManType(), "操作人类型为空");

        final OrderItemAsFlow flow = orderItemAsFlowService.getById(apply.getFlowId());
        ValidateUtils.notNull(flow, "售后流程不存在");
        ValidateUtils.isTrue(OrderFlowStatus.exc_confirm_receive.name().equals(flow.getFlowStatus()), "售后流程状态错误");

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                //更新订单商品状态
                List<String> ids = Lists.newArrayList(flow.getOrderListIds().split(","));
                if (OrderBizType.trade.name().equals(flow.getOrderType())) {
                    for (String id : ids) {
                        indentListService.update(Update.byId(id).set("status", IndentListStatus.returned3.name()).set("last_updated", new Date()));
                    }
                } else if (OrderBizType.purchase.name().equals(flow.getOrderType())) {
                    for (String id : ids) {
                        purchaseOrderListService.update(Update.byId(id).set("status", IndentListStatus.returned3.name()).set("last_updated", new Date()));
                    }
                }
                //更新售后流程状态
                orderItemAsFlowService.update(Update.byId(flow.getId()).set("flow_status", OrderFlowStatus.success_exchange.name())
                        .set("kefu_freight_no", apply.getFreightNo()).set("kefu_freight_company", apply.getFreightCompany()).set("end_time", new Date()).set("last_updated", new Date()));
                //创建售后变更记录
                createFlowDetail(flow, apply);
                //创建协商记录
                apply.setOperDesc("优茶联已发货");
                apply.setRemark(String.format("卖家发货，物流公司：%s，物流单号：%s。", apply.getFreightCompany(), apply.getFreightNo()));
                createItemReply(flow, apply);
            }
        });
    }

    private void createFlowDetail(OrderItemAsFlow flow, AfterSaleApply apply) {
        OrderItemAsFlowDetail detail = new OrderItemAsFlowDetail();
        detail.setId(IdGenerator.getDefault().nextStringId());
        detail.setRuleId(flow.getId());
        detail.setFlowStatus(flow.getFlowStatus());
        detail.setOperDesc(apply.getOperDesc());
        detail.setOperMan(apply.getOperManId());
        detail.setOperManType(apply.getOperManType());
        detail.setDelFlag(BoolType.F.name());
        detail.setDateCreated(new Date());
        orderItemAsFlowDetailService.insert(detail);
    }

    private void createItemReply(OrderItemAsFlow flow, AfterSaleApply apply) {
        OrderItemReply reply = new OrderItemReply();
        reply.setId(IdGenerator.getDefault().nextStringId());
        reply.setOrderId(flow.getOrderId());
        reply.setOrderType(flow.getOrderType());
        reply.setGoodSkuId(flow.getGoodSkuId());
        reply.setOrderFlowType(flow.getAsType());
        reply.setOperDesc(apply.getOperDesc());
        reply.setOperMan(apply.getOperManId());
        reply.setOperManType(apply.getOperManType());
        reply.setRemark(apply.getRemark());
        reply.setFlowTargetType(flow.getFlowTargetType());
        reply.setOperAmount(flow.getReturnAmount() == null ? null : flow.getReturnAmount().toString());
        reply.setOperReason(flow.getReturnReason());
        reply.setDelFlag(BoolType.F.name());
        reply.setDateCreated(new Date());
        if ("pending".equals(apply.getOperManType())) {
            reply.setTimeout(flow.getTimeout());
        }
        reply.setBuyerTimeout(flow.getBuyerTimeout());
        orderItemReplyService.insert(reply);

        if (StringUtils.isEmpty(flow.getAttachment())) {
            return;
        }
        List<String> imgUrlList = Arrays.asList(StringUtils.split(flow.getAttachment(), ","));
        OrderItemReplyImg img = new OrderItemReplyImg();
        img.setOrderItemReplyId(reply.getId());
        img.setDateCreated(new Date());
        img.setLastUpdated(new Date());
        for (String url : imgUrlList) {
            img.setId(IdGenerator.getDefault().nextStringId());
            img.setImgUrl(url);
            orderItemReplyImgService.insert(img);
        }
    }

    private Date getTimeout(Date createTime, OrderBizType orderBizType) {
        SysDict dict = sysDictService.get(Conds.get().eq("dict_type", "after_timeout").eq("code", orderBizType.name()));
        Integer hour = dict == null ? 24 : new BigDecimal(dict.getValue()).intValue();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createTime);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }
}
