package pub.makers.shop.afterSale.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.afterSale.vo.AfterSaleApply;
import pub.makers.shop.afterSale.vo.AfterSaleTkResult;
import pub.makers.shop.afterSale.vo.OrderItemReplyVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.store.service.SalesReturnReasonBizService;
import pub.makers.shop.store.vo.SalesReturnReasonVo;
import pub.makers.shop.tradeOrder.entity.Indent;
import pub.makers.shop.tradeOrder.service.TradeOrderQueryService;

import java.util.List;

/**
 * Created by kok on 2017/6/16.
 */
@Service
public class WeixinTradeAfterSaleAppService {
    @Reference(version = "1.0.0")
    private AfterSaleBizService afterSaleBizService;
    @Reference(version = "1.0.0")
    private AfterSaleTkBizService tkBizService;
    @Reference(version = "1.0.0")
    private OrderItemReplyQueryService orderItemReplyQueryService;
    @Reference(version = "1.0.0")
    private TradeOrderQueryService tradeOrderQueryService;
    @Reference(version = "1.0.0")
    private SalesReturnReasonBizService salesReturnReasonBizService;

    /**
     * 申请售后
     * @param apply
     * asType 售后类型
     * returnReason 售后理由
     * attachment 附件
     * orderListIds 订单商品ids
     * operDesc 操作备注
     * operManId 操作人id
     * operManType 操作人类型
     */
    public void applyAfterSale(AfterSaleApply apply) {
        apply.setOrderBizType(OrderBizType.trade);
        apply.setOperManType("buyer");
        apply.setOrderType(OrderType.normal);
        afterSaleBizService.applyAfterSale(apply);
    }

    public void updateAfterSale(AfterSaleApply apply) {
        apply.setOrderBizType(OrderBizType.trade);
        apply.setOperManType("buyer");
        afterSaleBizService.updateAfterSale(apply);
    }

    public void cancelApply(AfterSaleApply apply) {
        apply.setOrderBizType(OrderBizType.trade);
        apply.setOperManType("buyer");
        afterSaleBizService.cancelApply(apply);
    }

    public AfterSaleTkResult queryTkResult(AfterSaleApply apply){
    	
    	apply.setOrderBizType(OrderBizType.trade);
        apply.setOperManType("buyer");
        
        return tkBizService.queryTkResult(apply);
    }

    /**
     * 修改用户物流信息
     * flowId 售后流程id
     * freightNo 客户运单号
     */
    public void editUserShipping(AfterSaleApply apply) {
        apply.setOrderBizType(OrderBizType.trade);
        apply.setOperManType("buyer");
        afterSaleBizService.editUserShipping(apply);
    }

    public List<OrderItemReplyVo> getOrderReplyList(String orderId, String skuId) {
        Indent indent = tradeOrderQueryService.get(orderId);
        ValidateUtils.notNull(indent, "订单不存在");
//        String userId = AccountUtils.getCurrShopId();
//        ValidateUtils.isTrue(userId.equals(purchaseOrder.getBuyerId()), "只能操作自己的订单");
        return orderItemReplyQueryService.getOrderItemReplyList(orderId, skuId);
    }

    public List<SalesReturnReasonVo> getReturnReason() {
        return salesReturnReasonBizService.getReasonList();
    }
}
