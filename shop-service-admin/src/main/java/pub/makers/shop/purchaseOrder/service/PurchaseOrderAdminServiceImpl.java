package pub.makers.shop.purchaseOrder.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.dev.base.utils.DateUtil;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.afterSale.entity.OrderItemAsFlow;
import pub.makers.shop.afterSale.entity.OrderItemReply;
import pub.makers.shop.afterSale.entity.OrderItemReplyImg;
import pub.makers.shop.afterSale.service.OrderItemAsFlowService;
import pub.makers.shop.afterSale.service.OrderItemReplyImgService;
import pub.makers.shop.afterSale.service.OrderItemReplyService;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.util.SqlHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.baseOrder.entity.OrderListPayment;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderCancelType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.OrderCancelInfo;
import pub.makers.shop.baseOrder.service.OrderPaymentBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseOrderListService;
import pub.makers.shop.purchaseOrder.dao.PurchaseOrderListPaymentDao;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrder;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrderExport;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrderList;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrderListExport;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderParams;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.stock.pojo.StockQuery;
import pub.makers.shop.stock.service.StockBizService;
import pub.makers.shop.store.entity.VtwoStoreRole;
import pub.makers.shop.store.service.VtwoStoreRoleMgrBizService;
import pub.makers.shop.tradeOrder.enums.IndentListStatus;
import pub.makers.shop.tradeOrder.service.TradeOrderManager;
import pub.makers.shop.tradeOrder.vo.IndentInvoiceVo;
import pub.makers.shop.tradeOrder.vo.IndentParams;
import pub.makers.shop.tradeOrder.vo.IndentVo;
import pub.makers.shop.tradeOrder.vo.ShippingInfo;
import pub.makers.shop.user.service.WeixinUserInfoBizService;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dy on 2017/5/17.
 */
@Service(version = "1.0.0")
public class PurchaseOrderAdminServiceImpl implements PurchaseOrderMgrBizService {

    private final static String countAfterSaleOrderNumStmt = "select count(*) from purchase_order i where 1=1 and EXISTS (select 1 from order_item_as_flow oiaf  where oiaf.order_id = i.id and oiaf.order_type = 'purchase')";

    private final static String getOrderItemFolwInfoStmt = "select id, order_id, good_sku_id, order_type, as_type, flow_status, start_time, end_time, return_reason, sum(return_amount) as return_amount, last_updated as lastUpdated, timeout as timeout from order_item_as_flow where order_id = ? and good_sku_id = ? GROUP by good_sku_id;";

    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @Autowired
    private OrderItemAsFlowService orderItemAsFlowService;
    @Autowired
    private PurchaseOrderListService purchaseOrderListService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Reference(version="1.0.0")
    private VtwoStoreRoleMgrBizService vtwoStoreRoleMgrBizService;
    @Reference(version="1.0.0")
    private WeixinUserInfoBizService weixinUserInfoBizService;

    @Autowired
    private OrderItemReplyImgService orderItemReplyImgService;
    @Autowired
    private OrderItemReplyService orderItemReplyService;
    @Reference(version="1.0.0")
    private StockBizService stockBizService;

    @Reference(version = "1.0.0")
    private PurchaseOrderManager purchaseOrderManager;

    @Reference(version = "1.0.0")
    private OrderPaymentBizService orderPaymentBizService;

    @Override
    public Long countTodayOrderNum() {
        Date startDate = DateUtil.getDayStart(new Date());
        Date endDate = DateUtil.getDayEnd(new Date());
        return purchaseOrderService.count(Conds.get().gte("create_time", startDate).lte("create_time", endDate));
    }

    @Override
    public Long countUnpayOrderNum() {
        return purchaseOrderService.count(Conds.get().eq("status", 1));
    }

    @Override
    public Long countUnpostOrderNum() {
        return purchaseOrderService.count(Conds.get().eq("status", 2));
    }

    @Override
    public Long countUnDealOrderNum() {
        return jdbcTemplate.queryForLong(countAfterSaleOrderNumStmt);
    }

    @Override
    public BigDecimal indentTotalAmount() {
        String goodsTotalAmountStmt = "select IFNULL(SUM(total_amount), 0) from purchase_order";
        return jdbcTemplate.queryForObject(goodsTotalAmountStmt, BigDecimal.class);
    }

    @Override
    public BigDecimal indentPaymentAmount() {
        String indentPaymentAmountStmt = "select IFNULL(SUM(payment_amount), 0) from purchase_order where status != 1 and status != 8 and status != 9 and status != 11";
        return jdbcTemplate.queryForObject(indentPaymentAmountStmt, BigDecimal.class);
    }

    @Override
    public BigDecimal indentPaymentAmountByDateRange(Date dateStart, Date dateEnd) {
        String indentPaymentAmountStmt = "select IFNULL(SUM(payment_amount), 0) from purchase_order where create_time >= ? and create_time <= ?";
        return jdbcTemplate.queryForObject(indentPaymentAmountStmt, BigDecimal.class, dateStart, dateEnd);
    }

    @Override
    public ResultList<PurchaseOrderVo> purIndentList(PurchaseOrderParams orderParams, Paging pg) {

        if (StringUtils.isBlank(orderParams.getAfterSaleService())) {
            orderParams.setAfterSaleService(null);
        }
        String getPurchaseVoListStmt = FreeMarkerHelper.getValueFromTpl("sql/purIndent/getPurchaseOrderVoList.sql", orderParams);
        String countGetPurchaseVoListStmt = "select count(*) from (" + getPurchaseVoListStmt + ") a";
        RowMapper<PurchaseOrderVo> indentVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PurchaseOrderVo.class);
        List<PurchaseOrderVo> indentVos = jdbcTemplate.query(getPurchaseVoListStmt + " limit ?, ?", indentVoRowMapper, pg.getPs(), pg.getPn());

        Set<String> subbranchIdSet = ListUtils.getIdSet(indentVos, "buyerId");
        Set<String> orderIdList = ListUtils.getIdSet(indentVos, "id");

        Map<String,VtwoStoreRole> vtwoStoreRoleMap = vtwoStoreRoleMgrBizService.getVtwoStoreRoleByGroup(Lists.newArrayList(subbranchIdSet));
        orderParams.setCargoNo("");
        Map<String, List<PurchaseOrderListVo>> puchaseOrderListMap = getPurOrderListVoList(Lists.newArrayList(orderIdList), orderParams);

        for (PurchaseOrderVo indentVo : indentVos) {
            if (vtwoStoreRoleMap.get(indentVo.getBuyerId()) != null) {
                indentVo.setConcatName(vtwoStoreRoleMap.get(indentVo.getBuyerId()).getConcatName());
            }

            indentVo.setOrderListVos(puchaseOrderListMap.get(indentVo.getId()));
        }
        Number totalRecords = jdbcTemplate.queryForObject(countGetPurchaseVoListStmt, Integer.class);
        ResultList<PurchaseOrderVo> resultList = new ResultList<PurchaseOrderVo>();
        resultList.setResultList(indentVos);
        resultList.setTotalRecords(totalRecords != null ? totalRecords.intValue() : 0);
        return resultList;
    }

    /**
     * 获取订单明细
     * @param orderIdList
     * @param orderParams
     * @return
     */
    private Map<String, List<PurchaseOrderListVo>> getPurOrderListVoList(ArrayList<String> orderIdList, PurchaseOrderParams orderParams) {
        if (orderIdList.isEmpty()) {
            return null;
        }
        orderParams.setIds(StringUtils.join(orderIdList, ","));
        String getIndentListVoByIndentIdStmt = FreeMarkerHelper.getValueFromTpl("sql/purIndent/getPurchaseOrderListVoByOrderId.sql", orderParams);
        List<PurchaseOrderListVo> purchaseOrderListVos = jdbcTemplate.query(getIndentListVoByIndentIdStmt, ParameterizedBeanPropertyRowMapper.newInstance(PurchaseOrderListVo.class));
        Map<String, List<PurchaseOrderListVo>> indentListMap = new HashMap<String, List<PurchaseOrderListVo>>();
        for (PurchaseOrderListVo purchaseOrderListVo : purchaseOrderListVos) {
            if (checkIsAfterSale(purchaseOrderListVo)) { //查询订单售后
                Map<String, Object> orderItemFlowMap  = new HashMap<String, Object>();
                orderItemFlowMap.put("goodSkuId", purchaseOrderListVo.getPurGoodsSkuId());
                orderItemFlowMap.put("orderId", purchaseOrderListVo.getOrderId());
                orderItemFlowMap.put("orderType", OrderBizType.purchase.name());
                String getOrderItemFlowByGoodInfoStmt = FreeMarkerHelper.getValueFromTpl("sql/orderItemFlow/getOrderItemFlowByGoodInfo.sql", orderItemFlowMap);
                List<OrderItemAsFlow> orderItemAsFlows = jdbcTemplate.query(getOrderItemFlowByGoodInfoStmt, ParameterizedBeanPropertyRowMapper.newInstance(OrderItemAsFlow.class));
                if (!orderItemAsFlows.isEmpty()) {
                    OrderItemAsFlow orderItemAsFlow = orderItemAsFlows.get(0);
                    purchaseOrderListVo.setGoodNum(orderItemAsFlow.getGoodNum());
                    purchaseOrderListVo.setFlowStatus(orderItemAsFlow.getFlowStatus());
                    purchaseOrderListVo.setFlowType(orderItemAsFlow.getAsType());
                }
            }
            if (indentListMap.get(purchaseOrderListVo.getOrderId()) == null) {
                indentListMap.put(purchaseOrderListVo.getOrderId(), new ArrayList<PurchaseOrderListVo>());
            }
            indentListMap.get(purchaseOrderListVo.getOrderId()).add(purchaseOrderListVo);
        }

        return indentListMap;

    }

    @Override
    public List<PurchaseOrderExport> exportExcel(PurchaseOrderParams params) {
        return exportExcelPruchaseOrder(params);
    }

    @Override
    public Map<String, Object> afterSaleIndentDetail(String orderNo, String goodSkuId, String flowStatus, String afterSaleService) {

        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();

        //获取订单信息
        String getIndentVoInfoStmt = SqlHelper.getSql("sql/purIndent/getPurOrderVoDetail.sql");
        RowMapper<PurchaseOrderVo> indentVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PurchaseOrderVo.class);
        PurchaseOrderVo indentVo = jdbcTemplate.queryForObject(getIndentVoInfoStmt, indentVoRowMapper, orderNo);
        ValidateUtils.notNull(indentVo, "订单不存在");

        //获取客户名称信息
        if (StringUtils.isNotBlank(indentVo.getSubbranchId())) {
            VtwoStoreRole vtwoStoreRole = vtwoStoreRoleMgrBizService.getVtwoStoreRoleBySubbranchId(indentVo.getSubbranchId());
            if (vtwoStoreRole != null && StringUtils.isNotBlank(vtwoStoreRole.getConcatName())) {
                indentVo.setConcatName(vtwoStoreRole.getConcatName());
            }
        }

        //获取订单明细
        /*if (StringUtils.isNotBlank(afterSaleService)) {
            if ("refund".equals(afterSaleService)) {
                afterSaleService = IndentListStatus.return1.toString();
            }
            if ("exchange".equals(afterSaleService)) {
                afterSaleService = IndentListStatus.return3.toString();
            }
            if ("refund_return".equals(afterSaleService)) {
                afterSaleService = IndentListStatus.return2.toString();
            }
        }

        if (StringUtils.isNotBlank(flowStatus)) {
            if ("success_refund".equals(flowStatus)) {
                afterSaleService = IndentListStatus.returned1.toString();
            }
            if ("success_exchange".equals(flowStatus)) {
                afterSaleService = IndentListStatus.returned3.toString();
            }
            if ("success_return".equals(flowStatus)) {
                afterSaleService = IndentListStatus.returned2.toString();
            }
        }*/

        PurchaseOrderParams params = new PurchaseOrderParams();
//        params.setAfterSaleService(afterSaleService);
        params.setGoodSku(goodSkuId);
        indentVo.setOrderListVos(getPurIndentListVoList(indentVo, params));

        //获取订单流程信息getOrderItemFolwInfo
//        RowMapper<OrderItemAsFlow> orderItemAsFlowRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(OrderItemAsFlow.class);
//        OrderItemAsFlow orderItemAsFlow = jdbcTemplate.queryForObject(getOrderItemFolwInfoStmt, orderItemAsFlowRowMapper, indentVo.getId(), goodSkuId);

        Map<String, Object> orderItemFlowMap  = new HashMap<String, Object>();
        orderItemFlowMap.put("goodSkuId", goodSkuId);
        orderItemFlowMap.put("orderId", indentVo.getId());
        orderItemFlowMap.put("orderType", OrderBizType.purchase.name());
        String getOrderItemFlowByGoodInfoStmt = FreeMarkerHelper.getValueFromTpl("sql/orderItemFlow/getOrderItemFlowByGoodInfo.sql", orderItemFlowMap);
        List<OrderItemAsFlow> orderItemAsFlows = jdbcTemplate.query(getOrderItemFlowByGoodInfoStmt, ParameterizedBeanPropertyRowMapper.newInstance(OrderItemAsFlow.class));
        OrderItemAsFlow orderItemAsFlow = new OrderItemAsFlow();
        if (!orderItemAsFlows.isEmpty()) {
            orderItemAsFlow = orderItemAsFlows.get(0);
        }

        result.put("indent", indentVo);
        result.put("orderItemAsFlow", orderItemAsFlow);


        Map<String, Object> replyMap = new HashMap<String, Object>();
        replyMap.put("orderType", OrderBizType.purchase.name());
        replyMap.put("orderNo", orderNo);
        replyMap.put("goodSkuId", goodSkuId);

        //获取售后协商回复信息
        String getOrderItemReplyListStmt = FreeMarkerHelper.getValueFromTpl("sql/indent/getOrderItemReplyList.sql", replyMap);
        RowMapper<OrderItemReply> orderItemReplyRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(OrderItemReply.class);
        List<OrderItemReply> orderItemReplyList = jdbcTemplate.query(getOrderItemReplyListStmt, orderItemReplyRowMapper);
        for (OrderItemReply orderItemReply : orderItemReplyList) {
            List<OrderItemReplyImg> orderItemReplyImgs = orderItemReplyImgService.list(Conds.get().eq("order_item_reply_id", orderItemReply.getId()));
            orderItemReply.setOrderItemReplyImgList(orderItemReplyImgs);
        }
        result.put("orderItemReplyList", orderItemReplyList);
        return result;
    }

    @Override
    public void updateRemark(long id, String remark, String remarkLevel) {
        purchaseOrderService.update(Update.byId(id).set("remark", remark).set("remark_level", remarkLevel));
    }

    @Override
    public void addOrderFlowReply(Map<String, Object> orderFlowInfo) {
        String orderId = orderFlowInfo.get("orderId").toString();
        String goodSkuId = orderFlowInfo.get("goodSkuId").toString();
        String orderType = orderFlowInfo.get("orderType").toString();
        String remarkInfo = orderFlowInfo.get("remark").toString();
        String operMan = orderFlowInfo.get("operMan").toString();
        String orderFlowType = orderFlowInfo.get("orderFlowType").toString();
        String imgstr = orderFlowInfo.get("imgs").toString();
        String[] imgArr = {};
        if (StringUtils.isNotBlank(imgstr)) {
            imgArr = imgstr.split(",");
        }
        OrderItemReply orderItemReply = new OrderItemReply();
        orderItemReply.setId(IdGenerator.getDefault().nextId() + "");
        orderItemReply.setOrderId(orderId);
        orderItemReply.setGoodSkuId(goodSkuId);
        orderItemReply.setOperMan(operMan);
        orderItemReply.setRemark(remarkInfo);
        orderItemReply.setOperManType("manager");
        orderItemReply.setOrderType(orderType);
        orderItemReply.setOrderFlowType(orderFlowType);
        orderItemReplyService.insert(orderItemReply);	//保存回复留言信息

        for (String imgUrl : imgArr) {
            OrderItemReplyImg orderItemReplyImg = new OrderItemReplyImg();
            orderItemReplyImg.setId(IdGenerator.getDefault().nextId() + "");
            orderItemReplyImg.setImgUrl(imgUrl);
            orderItemReplyImg.setOrderItemReplyId(orderItemReply.getId());
            orderItemReplyImgService.insert(orderItemReplyImg); //保存相应图片
        }

    }

    @Override
    public Map<String, Object> updateIndentCarriage(PurchaseOrderParams indentParams) {
        String[] idArr = indentParams.getIds().split(",");
        List<PurchaseOrder> indents = purchaseOrderService.list(Conds.get().in("id", Arrays.asList(idArr)));
        for (PurchaseOrder indent : indents) {
            ShippingInfo shippingInfo = new ShippingInfo();
            shippingInfo.setOrderId(indent.getId());
            shippingInfo.setOrderBizType(OrderBizType.purchase);
            shippingInfo.setOrderType(OrderType.valueOf(indent.getOrderType()));
            purchaseOrderManager.freeShipping(shippingInfo);

//            BigDecimal buyerCarriage = indent.getBuyerCarriage();
//            BigDecimal payAmount = indent.getFinalAmount();
//            payAmount = payAmount.subtract(buyerCarriage);
//            purchaseOrderService.update(Update.byId(indent.getId()).set("buyer_carriage", 0).set("final_amount", payAmount));
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", true);
        return result;
    }

    @Override
    public List<IndentInvoiceVo> getPurIndentInvoiceList(PurchaseOrderParams indentParams) {
        String queryPurIndentInvoiceExportStmt = FreeMarkerHelper.getValueFromTpl("sql/purIndent/queryPruIndentInvoiceExport.sql", indentParams);
        RowMapper<IndentInvoiceVo> indentInvoiceVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(IndentInvoiceVo.class);
        List<IndentInvoiceVo> indentInvoiceVos = jdbcTemplate.query(queryPurIndentInvoiceExportStmt, indentInvoiceVoRowMapper);
        return indentInvoiceVos;
    }

    @Override
    public ResultData shipIndent(String ids, ShippingInfo shippingInfo) {
        PurchaseOrder indent = purchaseOrderService.getById(shippingInfo.getOrderId());
        updateStock(indent, shippingInfo.getUserId()); //更新库存
        shippingInfo.setExpressId(indent.getExpressId());
        purchaseOrderManager.shipmentOrder(shippingInfo); //订单发货
        return ResultData.createSuccess();

    }

    @Override
    public PurchaseOrder findPurchaseOrderById(long id) {
        return purchaseOrderService.getById(id);
    }

    @Override
    public ResultData batchCancelOrder(List<PurchaseOrderParams> paramsList, OrderBizType orderBizType, String userId) {
        ValidateUtils.notNull(paramsList, "订单信息不存在");
        ValidateUtils.notNull(orderBizType, "业务类型不正确");
        ValidateUtils.notNull(userId, "用户ID存在");
        for (PurchaseOrderParams indentParam : paramsList) {
            OrderCancelInfo cancelInfo = new OrderCancelInfo();
            cancelInfo.setOrderBizType(orderBizType);
            cancelInfo.setCancelType(OrderCancelType.manager);
            cancelInfo.setUserId(userId);
            cancelInfo.setOrderType(OrderType.valueOf(indentParam.getOrderType()));
            cancelInfo.setOrderId(indentParam.getId());
            purchaseOrderManager.cancelOrder(cancelInfo);
        }
        return ResultData.createSuccess();
    }

    /**
     * 更新库存
     * @param indent
     */
    private void updateStock(PurchaseOrder indent, Long userId) {
        //发货更新库存
        if ("5".equals(indent.getStatus())) {
            List<PurchaseOrderList> indentLists = purchaseOrderListService.list(Conds.get().eq("order_id", indent.getId()));
            for (PurchaseOrderList indentList : indentLists) {
                StockQuery stockQuery = new StockQuery();
                stockQuery.setUserId(userId);
                stockQuery.setNum(indentList.getNumber());
                stockQuery.setOrderType(OrderBizType.purchase);
                stockQuery.setSkuId(Long.parseLong(indentList.getCargoSkuId()));
                indentList.setStatus(IndentListStatus.ship.toString());
                indentList.setLastUpdated(new Date());
                purchaseOrderListService.update(indentList);
                stockBizService.useStock(stockQuery);
            }
        }

    }

    private List<PurchaseOrderExport> exportExcelPruchaseOrder(PurchaseOrderParams params) {
        String getPurIndentVoListStmt = FreeMarkerHelper.getValueFromTpl("sql/purIndent/queryPurchaseOrderVoExport.sql", params);
        List<PurchaseOrderVo> indentVoList = new ArrayList<PurchaseOrderVo>();
        RowMapper<PurchaseOrderVo> purIndentVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PurchaseOrderVo.class);
        indentVoList = jdbcTemplate.query(getPurIndentVoListStmt, purIndentVoRowMapper);
        List<PurchaseOrderExport> indentExportList = new ArrayList<PurchaseOrderExport>();
        List<String> orderIdList = Lists.newArrayList();

       /* List<String> buyIdList = Lists.newArrayList();
        buyIdList.addAll(ListUtils.getIdSet(indentVoList,"buyerId"));*/
        Set<String> subbranchIdSet = ListUtils.getIdSet(indentVoList, "buyerId");
        Map<String,VtwoStoreRole> vtwoStoreRoleMap = vtwoStoreRoleMgrBizService.getVtwoStoreRoleByGroup(Lists.newArrayList(subbranchIdSet));//得到客户名称

        for (PurchaseOrderVo purchaseOrderVo : indentVoList) {
            if (OrderType.presell.name().equals(purchaseOrderVo.getOrderType())) {
                orderIdList.add(purchaseOrderVo.getId());
            }
        }
        Map<String, List<OrderListPayment>> orderListPaymentMap = orderPaymentBizService.getPresellOrderListPaymentByOrderIds(orderIdList, OrderBizType.purchase, OrderType.presell);

        orderIdList.clear();
        orderIdList.addAll(ListUtils.getIdSet(indentVoList, "id"));
        Map<String, List<PurchaseOrderListVo>> orderListVoExportsMap = getPurOrderListVoExportList(orderIdList, params);


        for (PurchaseOrderVo indentVo : indentVoList) {
            List<PurchaseOrderListVo> indentListVos = orderListVoExportsMap.get(indentVo.getId());
//            List<PurchaseOrderListVo> indentListVos = getPurIndentListVoList(indentVo, params);
            if (OrderType.presell.name().equals(indentVo.getOrderType())) {
                List<OrderListPayment> orderListPayments = orderListPaymentMap.get(indentVo.getOrderId());
                if (orderListPayments.size() > 0 && orderListPayments.size() < 2) {
                    indentVo.setPresellAmount(orderListPayments.get(0).getWaitpayAmount());
                    indentVo.setPresellEnd(BigDecimal.ZERO);
                    indentVo.setPresellFirst(BigDecimal.ZERO);
                } else if (orderListPayments.size() > 1) {
                    BigDecimal presellFirst = BigDecimal.ZERO;
                    BigDecimal presellEnd = BigDecimal.ZERO;
                    for (OrderListPayment orderListPayment : orderListPayments) {
                        if (orderListPayment.getStageNum() == 1) {
                            presellFirst = orderListPayment.getWaitpayAmount();
                        }
                        if (orderListPayment.getStageNum() == 2) {
                            presellEnd = orderListPayment.getWaitpayAmount();
                        }
                    }
                    indentVo.setPresellFirst(presellFirst);
                    indentVo.setPresellEnd(presellEnd);
                    indentVo.setPresellAmount(BigDecimal.ZERO);
                }
            } else {
                indentVo.setPresellEnd(BigDecimal.ZERO);
                indentVo.setPresellFirst(BigDecimal.ZERO);
                indentVo.setPresellAmount(BigDecimal.ZERO);
            }

//            VtwoStoreRole vtwoStoreRole = vtwoStoreRoleMgrBizService.getVtwoStoreRoleBySubbranchId(indentVo.getSubbranchId() + "");
//            if (vtwoStoreRole == null) {
//                vtwoStoreRole = new VtwoStoreRole();
//            }
//            indentVo.setConcatName(vtwoStoreRole.getConcatName());
            PurchaseOrderExport indentExport = new PurchaseOrderExport();
            BeanUtils.copyProperties(indentVo, indentExport);
            indentExport.setPaymentAmount(indentVo.getPaymentAmount() != null ? indentVo.getPaymentAmount().toString() : "0.00");
            List<PurchaseOrderListExport> indentListExports = new ArrayList<PurchaseOrderListExport>();
            if (indentListVos == null) {
                continue;
            }
            for (PurchaseOrderListVo indentListVo : indentListVos) {
                PurchaseOrderListExport indentListExport = new PurchaseOrderListExport();
                BeanUtils.copyProperties(indentListVo, indentListExport);
                indentListExport.setFinalAmount(indentListVo.getFinalAmount() != null ? indentListVo.getFinalAmount().doubleValue() : 0.00D);
                if ("T".equals(indentListVo.getGiftFlag())){
                    indentListExport.setFinalAmount(0.00D);
                    indentListExport.setGiftFlag("T");
                }
                indentListExports.add(indentListExport);
            }
            if (vtwoStoreRoleMap.get(indentVo.getBuyerId()) != null) {
                indentExport.setConcatName(vtwoStoreRoleMap.get(indentVo.getBuyerId()).getConcatName());
            }
            indentExport.setOrderListExportList(indentListExports);
            indentExportList.add(indentExport);
        }
        return indentExportList;
    }

    private Map<String, List<PurchaseOrderListVo>> getPurOrderListVoExportList(List<String> ids, PurchaseOrderParams orderParams) {
        Map<String, List<PurchaseOrderListVo>> map = new HashMap<String, List<PurchaseOrderListVo>>();
        if (ids.isEmpty()) {
            return map;
        }
        orderParams.setIds(StringUtils.join(ids, ","));
        String getIndentListVoByIndentIdStmt = FreeMarkerHelper.getValueFromTpl("sql/purIndent/getPurOrderListVoExport.sql", orderParams);
        List<PurchaseOrderListVo> purchaseOrderListVos = jdbcTemplate.query(getIndentListVoByIndentIdStmt, ParameterizedBeanPropertyRowMapper.newInstance(PurchaseOrderListVo.class));

        for (PurchaseOrderListVo purchaseOrderListVo : purchaseOrderListVos) {
            if (map.get(purchaseOrderListVo.getOrderId()) == null) {
                map.put(purchaseOrderListVo.getOrderId(), new ArrayList<PurchaseOrderListVo>());
            }
            map.get(purchaseOrderListVo.getOrderId()).add(purchaseOrderListVo);
        }
        return map;
    }

    /**
     * 获取订单商品明细
     * @param purchaseOrderVo
     * @param orderParams
     * @return
     */
    private List<PurchaseOrderListVo> getPurIndentListVoList(PurchaseOrderVo purchaseOrderVo, PurchaseOrderParams orderParams) {
        RowMapper<PurchaseOrderListVo> purchaseOrderListVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PurchaseOrderListVo.class);
        String getIndentListVoByIndentIdStmt = FreeMarkerHelper.getValueFromTpl("sql/purIndent/getPurchaseOrderListVoByOrderId.sql", orderParams);
        List<PurchaseOrderListVo> purchaseOrderListVos = jdbcTemplate.query(getIndentListVoByIndentIdStmt, purchaseOrderListVoRowMapper, purchaseOrderVo.getId());

        Map<String, PurchaseOrderListVo> indentListMap = new HashMap<String, PurchaseOrderListVo>();
        for (PurchaseOrderListVo purchaseOrderListVo : purchaseOrderListVos) {
//            if (indentListMap.get(purchaseOrderListVo.getPurGoodsSkuId()) != null && !"T".equals(purchaseOrderListVo.getIsSample())) {
//                continue;
//            } else {
//                if (!"T".equals(purchaseOrderListVo.getIsSample())) {
                    if (checkIsAfterSale(purchaseOrderListVo)) {
                        Map<String, Object> orderItemFlowMap  = new HashMap<String, Object>();
                        orderItemFlowMap.put("goodSkuId", purchaseOrderListVo.getPurGoodsSkuId());
                        orderItemFlowMap.put("orderId", purchaseOrderVo.getId());
                        orderItemFlowMap.put("orderType", OrderBizType.purchase.name());
                        String getOrderItemFlowByGoodInfoStmt = FreeMarkerHelper.getValueFromTpl("sql/orderItemFlow/getOrderItemFlowByGoodInfo.sql", orderItemFlowMap);
                        List<OrderItemAsFlow> orderItemAsFlows = jdbcTemplate.query(getOrderItemFlowByGoodInfoStmt, ParameterizedBeanPropertyRowMapper.newInstance(OrderItemAsFlow.class));
                        if (!orderItemAsFlows.isEmpty()) {
                            OrderItemAsFlow orderItemAsFlow = orderItemAsFlows.get(0);
                            purchaseOrderListVo.setGoodNum(orderItemAsFlow.getGoodNum());
                            purchaseOrderListVo.setFlowStatus(orderItemAsFlow.getFlowStatus());
                            purchaseOrderListVo.setFlowType(orderItemAsFlow.getAsType());
                        }
                    }
//                    indentListMap.put(purchaseOrderListVo.getPurGoodsSkuId(), purchaseOrderListVo);
//                } else {
//                    if (indentListMap.get(purchaseOrderListVo.getPurGoodsId()) == null) {
//                        indentListMap.put(purchaseOrderListVo.getPurGoodsId(), purchaseOrderListVo);
//                    }
//                }

//            }
        }
        /*List<PurchaseOrderListVo> indentListVoResults = new ArrayList<PurchaseOrderListVo>();
        for (Map.Entry<String, PurchaseOrderListVo> indentListVoEntry : indentListMap.entrySet()) {
            PurchaseOrderListVo purchaseOrderListVo = indentListVoEntry.getValue();
            if (StringUtils.isNotBlank(purchaseOrderListVo.getPurGoodsId())) {
                if (!"T".equals(purchaseOrderListVo.getIsSample())) {
                    //样品没有SKUID
                    Long count = 0L;
                    if (StringUtils.isNotBlank(purchaseOrderListVo.getPurGoodsSkuId())) {
                        count = purchaseOrderListService.count(Conds.get().eq("order_id", purchaseOrderVo.getId()).eq("pur_goods_sku_id", purchaseOrderListVo.getPurGoodsSkuId()));
                    }
                    purchaseOrderListVo.setNumber(count.intValue());
                }*//* else {
                    Long count = purchaseOrderListService.count(Conds.get().eq("order_id", purchaseOrderVo.getId()).eq("pur_goods_id", purchaseOrderListVo.getPurGoodsId()).eq("is_sample", BoolType.T.name()));
                    purchaseOrderListVo.setNumber(count.intValue());
                }*//*
            }
            indentListVoResults.add(purchaseOrderListVo);
        }*/

        return purchaseOrderListVos;
    }

    private boolean checkIsAfterSale(PurchaseOrderListVo purchaseOrderListVo) {
        if (IndentListStatus.return1.toString().equals(purchaseOrderListVo.getStatus())) {
            return true;
        }
        if (IndentListStatus.return2.toString().equals(purchaseOrderListVo.getStatus())) {
            return true;
        }
        if (IndentListStatus.return3.toString().equals(purchaseOrderListVo.getStatus())) {
            return true;
        }

        if (IndentListStatus.returning1.toString().equals(purchaseOrderListVo.getStatus())) {
            return true;
        }
        if (IndentListStatus.returning2.toString().equals(purchaseOrderListVo.getStatus())) {
            return true;
        }
        if (IndentListStatus.returning3.toString().equals(purchaseOrderListVo.getStatus())) {
            return true;
        }

        if (IndentListStatus.returned1.toString().equals(purchaseOrderListVo.getStatus())) {
            return true;
        }
        if (IndentListStatus.returned2.toString().equals(purchaseOrderListVo.getStatus())) {
            return true;
        }
        if (IndentListStatus.returned3.toString().equals(purchaseOrderListVo.getStatus())) {
            return true;
        }

        return false;
    }

    public PurchaseOrderVo getDetailInfo(Long id) {

        String getIndentVoInfoStmt = SqlHelper.getSql("sql/purIndent/getPurchaseOrderVoDetail.sql");

        RowMapper<PurchaseOrderVo> indentVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PurchaseOrderVo.class);
        PurchaseOrderVo indentVo = jdbcTemplate.queryForObject(getIndentVoInfoStmt, indentVoRowMapper, id.toString());
        ValidateUtils.notNull(indentVo, "订单不存在");

//        WeixinUserInfo weixinUserInfo = weixinUserInfoBizService.getWxUserById(Long.parseLong(indentVo.getBuyerId()));
//        ValidateUtils.notNull(weixinUserInfo, "订单用户不存在");
//        indentVo.setBuyerName(weixinUserInfo.getNickname());

        if (StringUtils.isNotBlank(indentVo.getSubbranchId())) {
            VtwoStoreRole vtwoStoreRole = vtwoStoreRoleMgrBizService.getVtwoStoreRoleBySubbranchId(indentVo.getSubbranchId());
            if (vtwoStoreRole != null && StringUtils.isNotBlank(vtwoStoreRole.getConcatName())) {
                indentVo.setConcatName(vtwoStoreRole.getConcatName());
            }
        }

        RowMapper<PurchaseOrderListVo> indentListVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(PurchaseOrderListVo.class);
        PurchaseOrderParams params = new PurchaseOrderParams();
//		List<IndentListVo> indentListVos = jdbcTemplate.query(getIndentListVoByIndentIDStmt, indentListVoRowMapper, id);
//		ValidateUtils.notNull(indentListVos, "订单明细不存在");
        indentVo.setOrderListVos(getPurIndentListVoList(indentVo, params));
        return indentVo;
    }

    public Map<String, Object> modifyIndentReceiverInfo(PurchaseOrderParams indentParams) {
        ValidateUtils.notNull(indentParams.getId(), "ID不能为null");
        PurchaseOrder indent = purchaseOrderService.getById(indentParams.getId());
        ValidateUtils.notNull(indent, "当前订单不存在");
        indent.setReceiver(indentParams.getReceiver());
        indent.setReceiverPhone(indentParams.getReceiverPhone());
        indent.setProvince(indentParams.getProvince());
        indent.setCity(indentParams.getCity());
        indent.setDistrict(indentParams.getCountry());
        indent.setAddress(indentParams.getAddress());
        purchaseOrderService.update(indent);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", true);
        return result;
    }

}
