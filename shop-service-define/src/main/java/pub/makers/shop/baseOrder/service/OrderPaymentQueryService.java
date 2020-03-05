package pub.makers.shop.baseOrder.service;

import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.vo.OrderListPaymentVo;

import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/6/20.
 */
public interface OrderPaymentQueryService {
    /**
     * 查询订单分期信息
     */
    List<OrderListPaymentVo> getPaymentListByOrderList(String orderId, OrderBizType orderBizType);

    /**
     * 查询订单分期信息
     */
    Map<String, List<OrderListPaymentVo>> getPaymentListByOrderList(List<String> orderIdList, OrderBizType orderBizType);
}
