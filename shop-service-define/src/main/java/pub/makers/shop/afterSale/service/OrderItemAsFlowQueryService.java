package pub.makers.shop.afterSale.service;

import pub.makers.shop.afterSale.vo.OrderItemAsFlowVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;

import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/6/17.
 */
public interface OrderItemAsFlowQueryService {
    /**
     * 订单售后列表 key是orderId
     */
    Map<String, List<OrderItemAsFlowVo>> getOrderFlowList(List<String> orderIdList);

    /**
     * 订单售后列表 外层key是orderId 内层key是skuId
     */
    Map<String, Map<String, List<OrderItemAsFlowVo>>> getListFlowList(List<String> orderIdList);

    OrderItemAsFlowVo getAfterSaleOrderItemFlow(String orderId, String skuId, OrderBizType orderBizType);
}
