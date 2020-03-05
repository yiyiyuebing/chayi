package pub.makers.shop.purchaseOrder.service;

import pub.makers.shop.purchaseOrder.entity.PurchaseOrder;
import pub.makers.shop.purchaseOrder.pojo.PurchaseOrderQuery;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderCountVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;

import java.util.List;

/**
 * Created by kok on 2017/6/17.
 */
public interface PurchaseOrderQueryService {
    /**
     * 订单
     */
    PurchaseOrder get(String id);
    /**
     * 订单列表
     */
    List<PurchaseOrderVo> getOrderList(PurchaseOrderQuery query);

    /**
     * 订单列表数量
     */
    Long countOrderList(PurchaseOrderQuery query);

    /**
     * 订单详情
     */
    PurchaseOrderVo getOrderDetail(String id);

    /**
     * 订单数量
     */
    PurchaseOrderCountVo getOrderCount(PurchaseOrderQuery query);
    
    /**
     * 查询订单的支付信息
     * @param orderId
     * @return
     */
    PurchaseOrderVo queryPayInfo(String orderId);

    PurchaseOrderListVo getGoodInOrderMsg(String orderId, String skuId, String shopId);

    /**
     * 退款退货售后订单明细列表
     */
    List<PurchaseOrderListVo> getRefundOrderList(PurchaseOrderQuery query);

    /**
     * 退款退货售后订单明细列表数量
     * @param query
     * @return
     */
    Long countRefundOrderList(PurchaseOrderQuery query);
}
