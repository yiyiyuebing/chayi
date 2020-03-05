package pub.makers.shop.afterSale.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.common.entity.BoolType;

import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.afterSale.entity.OrderItemAsFlow;
import pub.makers.shop.afterSale.enums.FlowTargetType;
import pub.makers.shop.afterSale.vo.OrderItemAsFlowVo;
import pub.makers.shop.afterSale.vo.OrderItemReplyVo;
import pub.makers.shop.baseOrder.enums.OrderBizType;

/**
 * Created by kok on 2017/6/17.
 */
@Service(version = "1.0.0")
public class OrderItemAsFlowQueryServiceImpl implements OrderItemAsFlowQueryService {
    @Autowired
    private OrderItemAsFlowService orderItemAsFlowService;
    @Autowired
    private OrderItemReplyService orderItemReplyService;
    @Autowired
    private OrderItemReplyQueryService orderItemReplyQueryService;

    @Override
    public Map<String, List<OrderItemAsFlowVo>> getOrderFlowList(List<String> orderIdList) {
        List<OrderItemAsFlow> flowList = orderItemAsFlowService.list(Conds.get().in("order_id", orderIdList).eq("flow_target_type", FlowTargetType.order.name()).ne("del_flag", BoolType.T.name()).order("start_time desc"));
        Map<String, List<OrderItemAsFlowVo>> map = Maps.newHashMap();
        for (OrderItemAsFlow flow : flowList) {
            List<OrderItemAsFlowVo> flowVoList = map.get(flow.getGoodSkuId()) == null ? Lists.newArrayList() : map.get(flow.getGoodSkuId());
            OrderItemAsFlowVo vo = new OrderItemAsFlowVo();
            BeanUtils.copyProperties(flow, vo);
            flowVoList.add(vo);
            map.put(flow.getOrderId(), flowVoList);
        }
        return map;
    }

    @Override
    public Map<String, Map<String, List<OrderItemAsFlowVo>>> getListFlowList(List<String> orderIdList) {
        List<OrderItemAsFlow> flowList = orderItemAsFlowService.list(Conds.get().in("order_id", orderIdList).ne("del_flag", BoolType.T.name()).order("start_time desc"));
        Map<String, Map<String, List<OrderItemAsFlowVo>>> map = Maps.newHashMap();
        for (OrderItemAsFlow flow : flowList) {
            Map<String, List<OrderItemAsFlowVo>> skuFlowMap = map.get(flow.getOrderId()) == null ? Maps.newHashMap() : map.get(flow.getOrderId());
            List<OrderItemAsFlowVo> flowVoList = skuFlowMap.get(flow.getGoodSkuId()) == null ? Lists.newArrayList() : skuFlowMap.get(flow.getGoodSkuId());
            OrderItemAsFlowVo vo = new OrderItemAsFlowVo();
            BeanUtils.copyProperties(flow, vo);
            flowVoList.add(vo);
            skuFlowMap.put(flow.getGoodSkuId(), flowVoList);
            map.put(flow.getOrderId(), skuFlowMap);
        }
        return map;
    }

    @Override
    public OrderItemAsFlowVo getAfterSaleOrderItemFlow(String orderId, String skuId, OrderBizType orderBizType) {

        List<OrderItemAsFlow> orderItemAsFlows = orderItemAsFlowService.list(Conds.get().eq("order_id", orderId).eq("good_sku_id", skuId).eq("order_type", orderBizType.name()));
        if (orderItemAsFlows.isEmpty()) {
            return null;
        }
        OrderItemAsFlowVo orderItemAsFlowVo = new OrderItemAsFlowVo();
        BeanUtils.copyProperties(orderItemAsFlows.get(orderItemAsFlows.size() - 1), orderItemAsFlowVo);
        List<OrderItemReplyVo> orderItemReplyVos = orderItemReplyQueryService.getItemReplyVoList(orderId, skuId, orderBizType);
        orderItemAsFlowVo.setOrderItemReplyVos(orderItemReplyVos);
        return orderItemAsFlowVo;
    }
}
