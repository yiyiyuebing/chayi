package pub.makers.shop.tradeOrder.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.base.Joiner;
import com.google.common.collect.*;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Cond;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.afterSale.service.OrderItemAsFlowQueryService;
import pub.makers.shop.afterSale.vo.OrderItemAsFlowVo;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.util.SerializeUtils;
import pub.makers.shop.base.vo.Paging;
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
import pub.makers.shop.bill.entity.IndentBill;
import pub.makers.shop.bill.service.BillBizService;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.service.SubbranchAccountBizService;
import pub.makers.shop.tradeOrder.entity.Indent;
import pub.makers.shop.tradeOrder.entity.IndentList;
import pub.makers.shop.tradeOrder.pojo.TradeOrderQuery;
import pub.makers.shop.tradeOrder.vo.IndentExtendVo;
import pub.makers.shop.tradeOrder.vo.IndentListVo;
import pub.makers.shop.tradeOrder.vo.IndentVo;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by kok on 2017/6/16.
 */
@Service(version = "1.0.0")
public class TradeOrderQueryServiceImpl implements TradeOrderQueryService {
    @Autowired
    private IndentService indentService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private OrderItemAsFlowQueryService orderItemAsFlowQueryService;
    @Autowired
    private OrderPaymentQueryService orderPaymentQueryService;
    @Autowired
    private OrderPresellExtraQueryService orderPresellExtraQueryService;
    @Autowired
    private OrderListPresellExtraQueryService orderListPresellExtraQueryService;
    @Resource(name = "tradeOrderListPaymentServiceImpl")
    private OrderListPaymentService paymentService;
    @Reference(version = "1.0.0")
    private SubbranchAccountBizService subbranchAccountService;
    @Autowired
    private IndentListService indentListService;
    @Reference(version = "1.0.0")
    private BillBizService billBizService;

    @Override
    public Indent get(String id) {
        return indentService.getById(id);
    }

    @Override
    public List<IndentVo> getOrderList(TradeOrderQuery query) {
        // 前置验证
        ValidateUtils.notNull(query, "查询条件不能为空");
        ValidateUtils.notNull(query.getUserId(), "用户不能为空");

        Conds conds = Conds.get();
        if (StringUtils.isNotEmpty(query.getStatus())) {
            if (OrderStatus.pay.name().equals(query.getStatus())) {
                conds.in("status", Lists.newArrayList(OrderStatus.pay.getDbData(), OrderStatus.payend.getDbData()));
            } else {
                conds.eq("status", OrderStatus.valueOf(query.getStatus()).getDbData());
            }
            if (OrderStatus.ship.name().equals(query.getStatus()) || OrderStatus.receive.name().equals(query.getStatus())) {
                conds.addAll(Lists.newArrayList(Cond.isNull("deal_status")));
            }
        }
        if (StringUtils.isNotEmpty(query.getShopId())) {
            // 查询店铺ID列表
            Set<Long> shopIds = subbranchAccountService.queryChildrens(query.getShopId());
            conds.in("subbranch_id", shopIds);
        }
        // 订单列表
        List<Indent> indentList = indentService.list(conds.eq("buyer_id", query.getUserId()).ne("buyer_del_flag", BoolType.T.name()).ne("status", OrderStatus.confirm.getDbData())
                .order("create_time desc").page((query.getPageNo() - 1) * query.getPageSize(), query.getPageSize()));
        List<IndentVo> indentVoList = Lists.newArrayList();
        if (indentList.isEmpty()) {
            return indentVoList;
        }
        List<String> orderIdList = Lists.newArrayList(ListUtils.getIdSet(indentList, "id"));
        // 订单明细
        ListMultimap<String, IndentListVo> listMultimap = getOrderListList(orderIdList);
        // 预售信息
        Map<String, OrderPresellExtraVo> extraVoMap = orderPresellExtraQueryService.getPresellExtraMap(orderIdList, OrderBizType.trade);
        // 分期信息
        Map<String, List<OrderListPaymentVo>> paymentMap = orderPaymentQueryService.getPaymentListByOrderList(orderIdList, OrderBizType.trade);
        // 结算信息
        Map<String, IndentBill> indentBillMap = billBizService.getBill(orderIdList);
        // 售后信息
        for (Indent indent : indentList) {
            IndentVo vo = IndentVo.fromIndent(indent);
            List<IndentListVo> indentListVoList = listMultimap.get(vo.getId());
            Integer number = 0;
            for (IndentListVo indentListVo : indentListVoList) {
                number += indentListVo.getNumber();
            }
            vo.setNumber(number);
            vo.setIndentList(indentListVoList);
            if (OrderType.presell.name().equals(vo.getOrderType())) {
                vo.setPresellExtra(extraVoMap.get(vo.getId()));
            }
            vo.setBillStatus(indentBillMap.get(vo.getId()) == null ? null : indentBillMap.get(vo.getId()).getStatus());
            vo.setPaymentList(paymentMap.get(vo.getId()));
            indentVoList.add(vo);
        }
        return indentVoList;
    }

    @Override
    public List<IndentExtendVo> listShopOrder(String shopId, String buyerId, String status, Paging pi) {

        // 查询店铺ID列表
        Set<Long> shopIds = subbranchAccountService.queryChildrens(shopId);
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("shopIds", Joiner.on(",").join(shopIds));
        if (StringUtils.isNotBlank(buyerId)) {
            dataMap.put("buyerId", buyerId);
        }
        if (StringUtils.isNotBlank(status)) {
            dataMap.put("status", status);
        }

        String stmt = FreeMarkerHelper.getValueFromTpl("sql/indent/getShopOrderList.sql", dataMap);

        List<IndentExtendVo> resultList = jdbcTemplate.query(stmt, new BeanPropertyRowMapper<IndentExtendVo>(IndentExtendVo.class), pi.getPs(), pi.getPn());

        // 查询子账号的信息
        List<Subbranch> shopList = subbranchAccountService.listSubAccountByParent(shopId);
        ListUtils.join(resultList, shopList, "shopId", "id", "subAccount");

        // 处理封面图片
        List<IndentList> indentList = indentListService.getIndentListNotGift(Arrays.asList(ListUtils.getIdSet(resultList, "id").toArray()));
        Map<Long, IndentList> indentMap = Maps.newHashMap();
        for (IndentList il : indentList) {
            if (indentMap.get(il.getIndentId()) == null) {
                indentMap.put(il.getIndentId(), il);
            }

        }

        for (IndentExtendVo vo : resultList) {
            IndentList il = indentMap.get(Long.valueOf(vo.getId()));
            if (il != null && StringUtils.isBlank(vo.getPicUrl())) {
                vo.setPicUrl(il.getTradeGoodImgUrl());
            }
            vo.setStatus(OrderStatus.getTextByDbData(Integer.valueOf(vo.getStatus())));
            if (StringUtils.isNotEmpty(vo.getFlowStatus())) {
                if (OrderStatus.ship.name().equals(vo.getStatus())) {
                    vo.setFlowStatusStr("退款中");
                } else {
                    vo.setFlowStatusStr("售后中");
                }
            }
        }

        return resultList;
    }

    @Override
    public IndentVo getOrderDetail(String id) {
        Indent indent = indentService.getById(id);
        ValidateUtils.notNull(indent, "订单不存在");
        IndentVo vo = IndentVo.fromIndent(indent);
        // 订单明细
        ListMultimap<String, IndentListVo> listMultimap = getOrderListList(Lists.newArrayList(vo.getId()));
        vo.setIndentList(listMultimap.get(vo.getId()));
        vo.setPaymentList(orderPaymentQueryService.getPaymentListByOrderList(vo.getId(), OrderBizType.trade));
        if (OrderType.presell.name().equals(vo.getOrderType())) {
            vo.setPresellExtra(orderPresellExtraQueryService.getPresellExtra(vo.getId(), OrderBizType.trade));
        }
        IndentBill indentBill = billBizService.getBill(vo.getId());
        vo.setBillStatus(indentBill == null ? null : indentBill.getStatus());
        return vo;
    }

    private ListMultimap<String, IndentListVo> getOrderListList(List<String> orderIdList) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("indentIds", StringUtils.join(orderIdList, ","));
        String sql = FreeMarkerHelper.getValueFromTpl("sql/tradeOrder/getIndentListList.sql", data);
        List<IndentListVo> indentListVoList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(IndentListVo.class));
        // 售后信息
        Map<String, Map<String, List<OrderItemAsFlowVo>>> flowMap = orderItemAsFlowQueryService.getListFlowList(orderIdList);
        // 预售信息
        Map<String, Map<String, OrderListPresellExtraVo>> extraMap = orderListPresellExtraQueryService.getListPresellExtra(orderIdList, OrderBizType.trade);
        ListMultimap<String, IndentListVo> listMultimap = ArrayListMultimap.create();
        for (IndentListVo listVo : indentListVoList) {
            if (flowMap.get(listVo.getIndentId()) != null) {
                listVo.setFlowList(flowMap.get(listVo.getIndentId()).get(listVo.getTradeGoodSkuId()));
            }
            if (extraMap.get(listVo.getIndentId()) != null) {
                listVo.setPresellExtra(extraMap.get(listVo.getIndentId()).get(listVo.getTradeGoodSkuId()));
            }
            listMultimap.put(listVo.getIndentId(), listVo);
        }
        return listMultimap;
    }

    @Override
    public IndentVo queryPayInfo(String orderId) {

        Indent indent = indentService.getById(orderId);
        IndentVo indentVo = IndentVo.fromIndent(indent);
        List<OrderListPayment> paymentList = paymentService.list(Conds.get().eq("orderId", orderId));
        List<OrderListPaymentVo> pvoList = paymentList.stream().map(m -> SerializeUtils.toVo(new OrderListPaymentVo(), m)).collect(Collectors.toList());
        indentVo.setPaymentList(pvoList);

        return indentVo;
    }

    @Override
    public IndentVo getOrderDetailByOrderNo(String orderNo) {
        Indent indent = indentService.get(Conds.get().eq("name", orderNo));
        IndentVo indentVo = IndentVo.fromIndent(indent);
        return indentVo;
    }
}
