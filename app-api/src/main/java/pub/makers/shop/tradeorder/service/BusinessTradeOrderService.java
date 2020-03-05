package pub.makers.shop.tradeorder.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.afterSale.enums.OrderFlowStatus;
import pub.makers.shop.afterSale.vo.OrderItemAsFlowVo;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderCancelType;
import pub.makers.shop.baseOrder.enums.OrderConfirmType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.baseOrder.pojo.OrderCancelInfo;
import pub.makers.shop.baseOrder.pojo.OrderConfirmInfo;
import pub.makers.shop.baseOrder.pojo.OrderSubmitInfo;
import pub.makers.shop.bill.entity.IndentBill;
import pub.makers.shop.bill.service.OrderBillRecordBizService;
import pub.makers.shop.store.service.SubbranchAccountBizService;
import pub.makers.shop.tradeOrder.enums.IndentListStatus;
import pub.makers.shop.tradeOrder.service.TradeOrderManager;
import pub.makers.shop.tradeOrder.service.TradeOrderQueryService;
import pub.makers.shop.tradeOrder.vo.IndentExtendVo;
import pub.makers.shop.tradeOrder.vo.IndentListVo;
import pub.makers.shop.tradeOrder.vo.IndentVo;
import pub.makers.shop.user.entity.WeixinUserInfo;
import pub.makers.shop.user.service.WeixinUserInfoBizService;
import pub.makers.shop.user.utils.AccountUtils;

import java.util.List;
import java.util.Set;

@Service
public class BusinessTradeOrderService {

    @Reference(version = "1.0.0")
    private TradeOrderQueryService tradeOrderQueryService;
    @Reference(version = "1.0.0")
    private TradeOrderManager tradeOrderManager;
    @Reference(version = "1.0.0")
    private WeixinUserInfoBizService weixinUserInfoBizService;
    @Reference(version = "1.0.0")
    private SubbranchAccountBizService subbranchAccountService;
    @Reference(version = "1.0.0")
    private OrderBillRecordBizService orderBillRecordBizService;

    public List<IndentExtendVo> listShopOrder(String shopId, String userId, String status, Paging pi) {

        return tradeOrderQueryService.listShopOrder(shopId, userId, status, pi);
    }

    public IndentVo createOrder(IndentVo indentVo) {
        OrderSubmitInfo info = new OrderSubmitInfo();
        info.setBaseOrder(indentVo);
        info.setOrderBizType(OrderBizType.trade);
        info.setOrderType(OrderType.normal);
        BaseOrder bo = tradeOrderManager.createOrder(info);
        return (IndentVo)bo;
    }

    public void cancelOrder(String userId, String orderId, OrderType orderType) {
        OrderCancelInfo info = new OrderCancelInfo();
        info.setCancelType(OrderCancelType.user);
        info.setOrderBizType(OrderBizType.trade);
        info.setOrderType(orderType);
        info.setUserId(userId);
        info.setOrderId(orderId);
        tradeOrderManager.cancelOrder(info);
    }

    public void confirmReceipt(String userId, String orderId, OrderType orderType) {
        tradeOrderManager.confirmReceipt(new OrderConfirmInfo(userId, orderId, OrderConfirmType.user, OrderBizType.trade, orderType));
    }

    public IndentVo orderDetail(String orderId) {
        IndentVo indentVo = tradeOrderQueryService.getOrderDetail(orderId);
        String shopId = AccountUtils.getCurrShopId();
        Set<Long> shopIds = subbranchAccountService.queryChildrens(shopId);
        ValidateUtils.isTrue(shopIds.contains(Long.valueOf(indentVo.getSubbranchId())), "只能查看自己店铺的订单");
        WeixinUserInfo userInfo = weixinUserInfoBizService.getWxUserById(Long.valueOf(indentVo.getBuyerId()));
        ValidateUtils.notNull(userInfo, "当前订单买家不存在");
        indentVo.setBuyerName(userInfo.getNickname());
        indentVo.setBuyerHeadUrl(userInfo.getHeadImgUrl());
        List<String> statusList = Lists.newArrayList(OrderFlowStatus.cancel_exchange.name(), OrderFlowStatus.cancel_refund.name(),
                OrderFlowStatus.cancel_return.name(), OrderFlowStatus.refuse_exchange.name(), OrderFlowStatus.refuse_refund.name(), OrderFlowStatus.refuse_return.name(),
                OrderFlowStatus.success_exchange.name(), OrderFlowStatus.success_refund.name(), OrderFlowStatus.success_return.name());
        for (IndentListVo listVo : indentVo.getIndentList()) {
            if (listVo.getFlowList() != null && !listVo.getFlowList().isEmpty()) {
                for (OrderItemAsFlowVo flowVo : listVo.getFlowList()) {
                    if (!statusList.contains(flowVo.getFlowStatus())) {
                        listVo.setFlowStatus(flowVo.getFlowStatus());
                        if (IndentListStatus.ship.name().equals(flowVo.getOrderListStatus())) {
                            listVo.setFlowStatusStr("退款中");
                        } else {
                            listVo.setFlowStatusStr("售后中");
                        }
                        break;
                    }
                }
            }
        }
        IndentBill record = orderBillRecordBizService.getIndentBill(orderId);
        if (record != null) {
            indentVo.setIndentStatus(record.getStatus());
        }
        return indentVo;
    }
}
