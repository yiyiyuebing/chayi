package pub.makers.shop.purchseOrder.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Cond;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.afterSale.enums.OrderAsType;
import pub.makers.shop.afterSale.enums.OrderFlowStatus;
import pub.makers.shop.afterSale.service.AfterSaleTkBizService;
import pub.makers.shop.afterSale.service.OrderItemAsFlowQueryService;
import pub.makers.shop.afterSale.vo.AfterSaleApply;
import pub.makers.shop.afterSale.vo.AfterSaleTkResult;
import pub.makers.shop.afterSale.vo.OrderItemAsFlowVo;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.util.SerializeUtils;
import pub.makers.shop.baseOrder.entity.OrderListPayment;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderStatus;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.service.OrderListPaymentService;
import pub.makers.shop.baseOrder.service.OrderListPresellExtraQueryService;
import pub.makers.shop.baseOrder.service.OrderPaymentQueryService;
import pub.makers.shop.baseOrder.service.OrderPresellExtraQueryService;
import pub.makers.shop.baseOrder.vo.OrderListPaymentVo;
import pub.makers.shop.baseOrder.vo.OrderListPresellExtraVo;
import pub.makers.shop.baseOrder.vo.OrderPresellExtraVo;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrder;
import pub.makers.shop.purchaseOrder.pojo.PurchaseOrderQuery;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderQueryService;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderService;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderCountVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by kok on 2017/6/17.
 */
@Service(version = "1.0.0")
public class PurchaseOrderQueryServiceImpl implements PurchaseOrderQueryService {
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private OrderPaymentQueryService orderPaymentQueryService;
    @Autowired
    private OrderItemAsFlowQueryService orderItemAsFlowQueryService;
    @Autowired
    private OrderPresellExtraQueryService orderPresellExtraQueryService;
    @Autowired
    private OrderListPresellExtraQueryService orderListPresellExtraQueryService;
    @Resource(name = "purchaseOrderListPaymentServiceImpl")
    private OrderListPaymentService paymentService;
    @Autowired
    private AfterSaleTkBizService afterSaleTkBizService;

    @Override
    public PurchaseOrder get(String id) {
        return purchaseOrderService.getById(id);
    }

    @Override
    public List<PurchaseOrderVo> getOrderList(PurchaseOrderQuery query) {
        // 前置验证
        check(query);

        // 查询条件
        Conds conds = Conds.get();
        if (StringUtils.isNotEmpty(query.getStatus())) {
            List<String> statusList = Arrays.asList(StringUtils.split(query.getStatus(), ","));
            List<Integer> dbDataList = Lists.transform(statusList, new Function<String, Integer>() {
                @Override
                public Integer apply(String s) {
                    return OrderStatus.valueOf(s).getDbData();
                }
            });
            conds.in("status", dbDataList);
            if (dbDataList.contains(OrderStatus.ship.getDbData()) || dbDataList.contains(OrderStatus.receive.getDbData())) {
                conds.addAll(Lists.newArrayList(Cond.isNull("deal_status")));
            }
        }
        if (StringUtils.isNotEmpty(query.getName())) {
            conds.like("order_no", query.getName());
        }
        if (StringUtils.isNotEmpty(query.getOrderType())) {
            conds.eq("order_type", query.getOrderType());
        }
        if (StringUtils.isNotEmpty(query.getOrderClientType())) {
            conds.eq("order_client_type", query.getOrderClientType());
        }
        List<PurchaseOrder> orderList = purchaseOrderService.list(conds.eq("buyer_id", query.getUserId()).ne("buyer_del_flag", BoolType.T.name()).ne("status", OrderStatus.confirm.getDbData())
                .order("create_time desc").page((query.getPageNo() - 1) * query.getPageSize(), query.getPageSize()));
        List<PurchaseOrderVo> orderVoList = Lists.newArrayList();
        if (orderList.isEmpty()) {
            return orderVoList;
        }
        List<String> orderIdList = Lists.newArrayList(ListUtils.getIdSet(orderList, "id"));
        // 订单明细
        ListMultimap<String, PurchaseOrderListVo> listMultimap = getOrderListList(orderIdList);
        // 分期信息
        Map<String, List<OrderListPaymentVo>> paymentMap = orderPaymentQueryService.getPaymentListByOrderList(orderIdList, OrderBizType.purchase);
        // 预售信息
        Map<String, OrderPresellExtraVo> extraVoMap = orderPresellExtraQueryService.getPresellExtraMap(orderIdList, OrderBizType.purchase);
        for (PurchaseOrder order : orderList) {
            PurchaseOrderVo vo = PurchaseOrderVo.fromPurchaseOrder(order);
            // 订单明细
            List<PurchaseOrderListVo> purchaseOrderListVoList = listMultimap.get(vo.getId());
            Integer number = 0;
            for (PurchaseOrderListVo purchaseOrderListVo : purchaseOrderListVoList) {
                number += purchaseOrderListVo.getNumber();
            }
            vo.setNumber(number);
            vo.setOrderListVos(purchaseOrderListVoList);
            if (OrderType.presell.name().equals(vo.getOrderType())) {
                vo.setPresellExtra(extraVoMap.get(vo.getId()));
            }
            // 分期信息
            vo.setPaymentList(paymentMap.get(vo.getId()));
            orderVoList.add(vo);
        }
        return orderVoList;
    }

    @Override
    public Long countOrderList(PurchaseOrderQuery query) {
        // 前置验证
        check(query);

        // 查询条件
        Conds conds = Conds.get();
        if (StringUtils.isNotEmpty(query.getStatus())) {
            conds.eq("status", OrderStatus.valueOf(query.getStatus()).getDbData());
            if (OrderStatus.ship.name().equals(query.getStatus()) || OrderStatus.receive.name().equals(query.getStatus())) {
                conds.addAll(Lists.newArrayList(Cond.isNull("deal_status")));
            }
        }
        if (StringUtils.isNotEmpty(query.getName())) {
            conds.like("order_no", query.getName());
        }
        if (StringUtils.isNotEmpty(query.getOrderType())) {
            conds.eq("order_type", query.getOrderType());
        }
        if (StringUtils.isNotEmpty(query.getOrderClientType())) {
            conds.eq("order_client_type", query.getOrderClientType());
        }
        Long count = purchaseOrderService.count(conds.eq("buyer_id", query.getUserId()).ne("buyer_del_flag", BoolType.T.name()).ne("status", OrderStatus.confirm.getDbData()));
        return count;
    }

    private void check(PurchaseOrderQuery query) {
        ValidateUtils.notNull(query, "查询条件不能为空");
        ValidateUtils.notNull(query.getUserId(), "用户不能为空");
    }

    @Override
    public PurchaseOrderVo getOrderDetail(String id) {
        PurchaseOrder order = purchaseOrderService.getById(id);
        PurchaseOrderVo vo = PurchaseOrderVo.fromPurchaseOrder(order);

        // 订单明细
        ListMultimap<String, PurchaseOrderListVo> listMultimap = getOrderListList(Lists.newArrayList(vo.getId()));
        vo.setOrderListVos(listMultimap.get(vo.getId()));
        // 分期信息
        vo.setPaymentList(orderPaymentQueryService.getPaymentListByOrderList(vo.getId(), OrderBizType.purchase));
        if (OrderType.presell.name().equals(vo.getOrderType())) {
            vo.setPresellExtra(orderPresellExtraQueryService.getPresellExtra(vo.getId(), OrderBizType.purchase));
        }
        return vo;
    }

    private ListMultimap<String, PurchaseOrderListVo> getOrderListList(List<String> orderIdList) {
        Map<String, Object> data = Maps.newHashMap();
        String orderIds = StringUtils.join(orderIdList, ",");
        if (StringUtils.isBlank(orderIds)) {
            orderIds = "-1";
        }
        data.put("orderIds", orderIds);
        String sql = FreeMarkerHelper.getValueFromTpl("sql/purchaseOrder/getPurchaseOrderListList.sql", data);
        List<PurchaseOrderListVo> orderListVoList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(PurchaseOrderListVo.class));
        ListMultimap<String, PurchaseOrderListVo> listMultimap = ArrayListMultimap.create();
        // 售后信息
        Map<String, Map<String, List<OrderItemAsFlowVo>>> flowMap = orderItemAsFlowQueryService.getListFlowList(orderIdList);
        // 预售信息
        Map<String, Map<String, OrderListPresellExtraVo>> extraMap = orderListPresellExtraQueryService.getListPresellExtra(orderIdList, OrderBizType.purchase);
        for (PurchaseOrderListVo listVo : orderListVoList) {
            if (flowMap.get(listVo.getOrderId()) != null) {
                listVo.setFlowList(flowMap.get(listVo.getOrderId()).get(listVo.getPurGoodsSkuId()));
            }
            if (extraMap.get(listVo.getOrderId()) != null) {
                listVo.setPresellExtra(extraMap.get(listVo.getOrderId()).get(listVo.getPurGoodsSkuId()));
            }
            listMultimap.put(listVo.getOrderId(), listVo);
        }
        return listMultimap;
    }

    @Override
    public PurchaseOrderCountVo getOrderCount(PurchaseOrderQuery query) {
        // 前置验证
        ValidateUtils.notNull(query, "用户不能为空");

        PurchaseOrderCountVo vo = new PurchaseOrderCountVo();

        Conds conds = Conds.get().eq("buyer_id", query.getUserId()).ne("buyer_del_flag", BoolType.T.name()).ne("status", OrderStatus.confirm.getDbData());
        if (StringUtils.isNotBlank(query.getOrderType())) {
            conds.eq("order_type", query.getOrderType());
        }
        if (StringUtils.isNotEmpty(query.getOrderClientType())) {
            conds.eq("order_client_type", query.getOrderClientType());
        }
        // 所有订单数
        Long allCount = purchaseOrderService.count(conds);
        vo.setAllCount(allCount);
        // 待评价订单数
        Long evaluateCount = purchaseOrderService.count(Conds.get().addAll(conds.getCondList()).eq("status", OrderStatus.evaluate.getDbData()));
        vo.setEvaluateCount(evaluateCount);
        // 已完成订单数
        Long doneCount = purchaseOrderService.count(Conds.get().addAll(conds.getCondList()).eq("status", OrderStatus.done.getDbData()));

        vo.setDoneCount(doneCount);
        conds.addAll(Lists.newArrayList(Cond.isNull("deal_status")));
        // 待支付订单数
        Long payCount = purchaseOrderService.count(Conds.get().addAll(conds.getCondList()).eq("status", OrderStatus.pay.getDbData()));
        vo.setPayCount(payCount);
        // 代发货订单数
        Long shipCount = purchaseOrderService.count(Conds.get().addAll(conds.getCondList()).eq("status", OrderStatus.ship.getDbData()));
        vo.setShipCount(shipCount);
        // 待收获订单数
        Long receiveCount = purchaseOrderService.count(Conds.get().addAll(conds.getCondList()).eq("status", OrderStatus.receive.getDbData()));
        vo.setReceiveCount(receiveCount);
        return vo;
    }

    @Override
    public PurchaseOrderVo queryPayInfo(String orderId) {

        PurchaseOrder order = purchaseOrderService.getById(orderId);
        PurchaseOrderVo pvo = PurchaseOrderVo.fromPurchaseOrder(order);
        // 查询付款记录列表
        List<OrderListPayment> paymentList = paymentService.list(Conds.get().eq("orderId", orderId));
        List<OrderListPaymentVo> pvoList = paymentList.stream().map(m -> SerializeUtils.toJsonVo(OrderListPaymentVo.class, m)).collect(Collectors.toList());
        pvo.setPaymentList(pvoList);

        return pvo;
    }

    /**
     * 获取订单中的商品信息,数据信息主要提供给售后服务选择页面
     */
    @Override
    public PurchaseOrderListVo getGoodInOrderMsg(String orderId, String skuId, String shopId) {
        Map<String, Object> dataParameter = Maps.newHashMap();
        dataParameter.put("orderId", orderId);
        dataParameter.put("skuId", skuId);
        dataParameter.put("shopId", shopId);
        String sqlStr = FreeMarkerHelper.getValueFromTpl("sql/afterSale/afterSaleSelectGoodMsgList.sql", dataParameter);
        List<PurchaseOrderListVo> goodMsgList = jdbcTemplate.query(sqlStr, ParameterizedBeanPropertyRowMapper.newInstance(PurchaseOrderListVo.class));

        if (goodMsgList.isEmpty()) {
            return null;
        }
        PurchaseOrderListVo purchaseOrderListVo = goodMsgList.get(0);
        AfterSaleApply afterSaleApply = new AfterSaleApply();

        String[] idArr = purchaseOrderListVo.getId().split(",");
        afterSaleApply.setOrderId(purchaseOrderListVo.getOrderId());
        afterSaleApply.setOrderListIdList(Arrays.asList(idArr));
        afterSaleApply.setOrderBizType(OrderBizType.purchase);
        afterSaleApply.setOrderType(OrderType.valueOf(purchaseOrderListVo.getOrderType()));
        AfterSaleTkResult afterSaleTkResult = afterSaleTkBizService.queryTkResult(afterSaleApply);
        purchaseOrderListVo.setRefundAmount(afterSaleTkResult.getMaxAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
        purchaseOrderListVo.setFreight(afterSaleTkResult.getFreight().setScale(2, BigDecimal.ROUND_HALF_UP));
        return goodMsgList.get(0);
    }

    @Override
    public List<PurchaseOrderListVo> getRefundOrderList(PurchaseOrderQuery query) {
        ValidateUtils.notNull(query, "参数错误");
        ValidateUtils.notNull(query.getUserId(), "用户id为空");
        Map<String, Object> data = Maps.newHashMap();
        if (query.getFlowStatusNum() != null) {
            List<String> flowStatusList = Lists.newArrayList();
            if (StringUtils.isNotEmpty(query.getFlowAsType())) {
                OrderFlowStatus status = OrderFlowStatus.getStatus(OrderAsType.valueOf(query.getFlowAsType()), query.getFlowStatusNum());
                if (status != null) {
                    flowStatusList.add(status.name());
                }
            } else {
                OrderFlowStatus returnStatus = OrderFlowStatus.getStatus(OrderAsType.refund_return, query.getFlowStatusNum());
                OrderFlowStatus refundStatus = OrderFlowStatus.getStatus(OrderAsType.refund, query.getFlowStatusNum());
                if (returnStatus != null) {
                    flowStatusList.add(returnStatus.name());
                }
                if (refundStatus != null) {
                    flowStatusList.add(refundStatus.name());
                }
            }
            query.setFlowStatusList(flowStatusList);
        }
        data.put("query", query);
        String sql = FreeMarkerHelper.getValueFromTpl("sql/purchaseOrder/getRefundPurchaseOrderList.sql", data);
        List<PurchaseOrderListVo> orderListVoList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(PurchaseOrderListVo.class));
        List<String> orderIdList = Lists.newArrayList(ListUtils.getIdSet(orderListVoList, "orderId"));
        // 售后信息
        Map<String, Map<String, List<OrderItemAsFlowVo>>> flowMap = orderItemAsFlowQueryService.getListFlowList(orderIdList);
        for (PurchaseOrderListVo listVo : orderListVoList) {
            if (flowMap.get(listVo.getOrderId()) != null) {
                listVo.setFlowList(flowMap.get(listVo.getOrderId()).get(listVo.getPurGoodsSkuId()));
            }
        }
        return orderListVoList;
    }

    @Override
    public Long countRefundOrderList(PurchaseOrderQuery query) {
        ValidateUtils.notNull(query, "参数错误");
        ValidateUtils.notNull(query.getUserId(), "用户id为空");
        Map<String, Object> data = Maps.newHashMap();
        if (query.getFlowStatusNum() != null) {
            List<String> flowStatusList = Lists.newArrayList();
            if (StringUtils.isNotEmpty(query.getFlowAsType())) {
                OrderFlowStatus status = OrderFlowStatus.getStatus(OrderAsType.valueOf(query.getFlowAsType()), query.getFlowStatusNum());
                if (status != null) {
                    flowStatusList.add(status.name());
                }
            } else {
                OrderFlowStatus returnStatus = OrderFlowStatus.getStatus(OrderAsType.refund_return, query.getFlowStatusNum());
                OrderFlowStatus refundStatus = OrderFlowStatus.getStatus(OrderAsType.refund, query.getFlowStatusNum());
                if (returnStatus != null) {
                    flowStatusList.add(returnStatus.name());
                }
                if (refundStatus != null) {
                    flowStatusList.add(refundStatus.name());
                }
            }
            query.setFlowStatusList(flowStatusList);
        }
        data.put("query", query);
        String sql = FreeMarkerHelper.getValueFromTpl("sql/purchaseOrder/countRefundPurchaseOrderList.sql", data);
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count;
    }
}
