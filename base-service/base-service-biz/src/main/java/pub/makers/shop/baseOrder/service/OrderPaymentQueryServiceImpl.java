package pub.makers.shop.baseOrder.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.common.entity.BoolType;
import org.springframework.beans.BeanUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.baseOrder.entity.OrderListPayment;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.vo.OrderListPaymentVo;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/6/20.
 */
@Service(version = "1.0.0")
public class OrderPaymentQueryServiceImpl implements OrderPaymentQueryService {
    @Resource(name = "purchaseOrderListPaymentServiceImpl")
    private OrderListPaymentService purchaseOrderListPaymentServiceImpl;
    @Resource(name = "tradeOrderListPaymentServiceImpl")
    private OrderListPaymentService tradeOrderListPaymentServiceImpl;

    @Override
    public List<OrderListPaymentVo> getPaymentListByOrderList(String orderId, OrderBizType orderBizType) {
        OrderListPaymentService paymentService = getService(orderBizType);
        List<OrderListPayment> paymentList = paymentService.list(Conds.get().eq("order_id", orderId).eq("del_flag", BoolType.F.name()).order("stage_num asc"));
        List<OrderListPaymentVo> paymentVoList = Lists.newArrayList();
        for (OrderListPayment payment : paymentList) {
            OrderListPaymentVo vo = new OrderListPaymentVo();
            BeanUtils.copyProperties(payment, vo);
            paymentVoList.add(vo);
        }
        return paymentVoList;
    }

    @Override
    public Map<String, List<OrderListPaymentVo>> getPaymentListByOrderList(List<String> orderIdList, OrderBizType orderBizType) {
        OrderListPaymentService paymentService = getService(orderBizType);
        List<OrderListPayment> paymentList = paymentService.list(Conds.get().in("order_id", orderIdList).eq("del_flag", BoolType.F.name()).order("stage_num asc"));
        Map<String, List<OrderListPaymentVo>> paymentMap = Maps.newHashMap();
        for (OrderListPayment payment : paymentList) {
            List<OrderListPaymentVo> paymentVoList = paymentMap.get(payment.getOrderId()) == null ? Lists.newArrayList() : paymentMap.get(payment.getOrderId());
            OrderListPaymentVo vo = new OrderListPaymentVo();
            BeanUtils.copyProperties(payment, vo);
            paymentVoList.add(vo);
            paymentMap.put(payment.getOrderId(), paymentVoList);
        }
        return paymentMap;
    }

    private OrderListPaymentService getService(OrderBizType orderBizType) {
        if (OrderBizType.purchase.equals(orderBizType)) {
            return purchaseOrderListPaymentServiceImpl;
        } else {
            return tradeOrderListPaymentServiceImpl;
        }
    }
}
