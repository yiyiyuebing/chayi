package pub.makers.shop.tradeOrder.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.dev.base.utils.DateUtil;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.transaction.support.TransactionTemplate;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.afterSale.entity.OrderItemAsFlow;
import pub.makers.shop.afterSale.entity.OrderItemReply;
import pub.makers.shop.afterSale.entity.OrderItemReplyImg;
import pub.makers.shop.afterSale.service.OrderItemAsFlowDetailService;
import pub.makers.shop.afterSale.service.OrderItemAsFlowService;
import pub.makers.shop.afterSale.service.OrderItemReplyImgService;
import pub.makers.shop.afterSale.service.OrderItemReplyService;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.util.SqlHelper;
import pub.makers.shop.base.utils.DateParseUtil;
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
import pub.makers.shop.message.service.MessageBizService;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;
import pub.makers.shop.stock.pojo.StockQuery;
import pub.makers.shop.stock.service.StockBizService;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.entity.VtwoStoreRole;
import pub.makers.shop.store.service.SubbranchAccountBizService;
import pub.makers.shop.store.service.VtwoStoreRoleMgrBizService;
import pub.makers.shop.tradeGoods.service.TradeGoodSkuService;
import pub.makers.shop.tradeOrder.entity.Indent;
import pub.makers.shop.tradeOrder.entity.IndentList;
import pub.makers.shop.tradeOrder.enums.IndentListStatus;
import pub.makers.shop.tradeOrder.vo.*;
import pub.makers.shop.user.entity.WeixinUserInfo;
import pub.makers.shop.user.service.WeixinUserInfoBizService;

import java.math.BigDecimal;
import java.util.*;

@Service(version="1.0.0")
public class IndentAdminServiceImpl implements IndentMgrBizService {

	private Logger logger = Logger.getLogger(IndentAdminServiceImpl.class);

	private final static String countUnDealOrderNumStmt = "select count(*) from indent_list il LEFT JOIN indent i ON il.indent_id=i.id where 1=1 and EXISTS (select 1 from order_item_as_flow oiaf  where oiaf.order_id = i.id and oiaf.order_type = 'trade')";
	private final static String getIndentListVoByIndentIDStmt = "select * from indent_list where indent_id=?";
	private final static String getRoleInfoByStaffId = "select role_id as roleId from _security_role_user where user_id =?";
	private final static String getOrderItemFolwInfoStmt = "select id, order_id, good_sku_id, order_type, as_type, flow_status, start_time, end_time, return_reason, return_amount as returnAmount, last_updated as lastUpdated, timeout as timeout from order_item_as_flow where order_id = ? and good_sku_id = ? order by start_time desc limit 0,1";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Reference(version="1.0.0")
	private SubbranchAccountBizService subbranchAccountService;
	@Autowired
	private IndentListService indentListService;
	@Autowired
	private IndentService indentService;
	@Reference(version="1.0.0")
	private VtwoStoreRoleMgrBizService vtwoStoreRoleMgrBizService;
	@Reference(version="1.0.0")
	private WeixinUserInfoBizService weixinUserInfoBizService;
	@Autowired
	private OrderItemAsFlowService orderItemAsFlowService;
	@Autowired
	private OrderItemAsFlowDetailService orderItemAsFlowDetailService;
	@Autowired
	private OrderItemReplyImgService orderItemReplyImgService;
	@Autowired
	private OrderItemReplyService orderItemReplyService;
	@Reference(version="1.0.0")
	private StockBizService stockBizService;
	@Autowired
	private TradeGoodSkuService tradeGoodSkuService;
	@Reference(version = "1.0.0")
	private TradeOrderManager tradeOrderManager;
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Reference(version = "1.0.0")
	private MessageBizService messageBizService;
	@Reference(version = "1.0.0")
	private OrderPaymentBizService orderPaymentBizService;

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
//		String paramBuyerId = StringUtils.isBlank(buyerId) ? "%" : buyerId;
//		String paramStatus = StringUtils.isBlank(status) ? "%" : status;
		
		List<IndentExtendVo> resultList = jdbcTemplate.query(stmt, new BeanPropertyRowMapper<IndentExtendVo>(IndentExtendVo.class), pi.getPs(), pi.getPn());
		
		// 查询子账号的信息
		List<Subbranch> shopList = subbranchAccountService.listSubAccountByParent(shopId);
		ListUtils.join(resultList, shopList, "shopId", "id", "subAccount");
		
		// 处理封面图片
		List<IndentList> indentList = indentListService.getIndentListNotGift(Arrays.asList(ListUtils.getIdSet(resultList, "id").toArray()));
		Map<Long, IndentList> indentMap = Maps.newHashMap();
		for (IndentList il : indentList){
			indentMap.put(il.getIndentId(), il);
		}
		
		for (IndentExtendVo vo : resultList){
			IndentList il = indentMap.get(Long.valueOf(vo.getId()));
			if (il != null){
				vo.setPicUrl(il.getTradeGoodImgUrl());
			}
		}
		
		return resultList;
	}

	@Override
	public Long countTodayOrderNum() {
		Date startDate = DateUtil.getDayStart(new Date());
		Date endDate = DateUtil.getDayEnd(new Date());
		Long count = indentService.count(Conds.get().gte("createTime", DateParseUtil.formatDate(startDate, "yyyy-MM-dd HH:mm:ss")).lte("createTime", DateParseUtil.formatDate(endDate, "yyyy-MM-dd HH:mm:ss")));
		return count;
	}

	@Override
	public Long countUnpayOrderNum() {
		return indentService.count(Conds.get().eq("status", 1));
	}

	@Override
	public Long countUnpostOrderNum() {
		return indentService.count(Conds.get().eq("status", 2));
	}

	@Override
	public BigDecimal indentTotalAmount() {
		String goodsTotalAmountStmt = "select IFNULL(SUM(total_amount), 0) from indent";
		return jdbcTemplate.queryForObject(goodsTotalAmountStmt, BigDecimal.class);
	}

	@Override
	public BigDecimal indentPaymentAmount() {
		String indentPaymentAmountStmt = "select IFNULL(SUM(payment_amount), 0) from indent where status != 1 and status != 8 and status != 9 and status != 11";
		return jdbcTemplate.queryForObject(indentPaymentAmountStmt, BigDecimal.class);
	}

	@Override
	public BigDecimal indentPaymentAmountByDateRange(Date dateStart, Date dateEnd) {
		String indentPaymentAmountStmt = "select IFNULL(SUM(payment_amount), 0) from indent where create_time >= ? and create_time <= ?";
		return jdbcTemplate.queryForObject(indentPaymentAmountStmt, BigDecimal.class, dateStart, dateEnd);
	}

	@Override
	public Long countUnDealOrderNum() {
		return jdbcTemplate.queryForLong(countUnDealOrderNumStmt);
	}


	@Override
	public ResultList<IndentVo> indentList(IndentParams indentParams, Paging pg) {

		String getIndentVoListStmt = FreeMarkerHelper.getValueFromTpl("sql/indent/getIndenVotList.sql", indentParams);

		String countgetIndentVoListStmt = "select count(*) from (" + getIndentVoListStmt + ") a";
		RowMapper<IndentVo> indentVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(IndentVo.class);
		List<IndentVo> indentVos = jdbcTemplate.query(getIndentVoListStmt + " limit ?, ?", indentVoRowMapper, pg.getPs(), pg.getPn());
		List<String> subbranchIdList = new ArrayList<>();
		Set<String> orderIdList = new HashSet<String>();
		for (IndentVo indentVo : indentVos) {
			if (StringUtils.isNotBlank(indentVo.getSubbranchId())) {
				subbranchIdList.add(indentVo.getSubbranchId());
			}
		}
		orderIdList = ListUtils.getIdSet(indentVos, "id");
		Map<String,VtwoStoreRole> vtwoStoreRoleMap = vtwoStoreRoleMgrBizService.getVtwoStoreRoleByGroup(subbranchIdList);
		indentParams.setCargoNo("");
		Map<String, List<IndentListVo>> orderListVoMap = getOrderListVoList(orderIdList, indentParams);
		for (IndentVo indentVo : indentVos) {
			VtwoStoreRole vtwoStoreRole = vtwoStoreRoleMap.get(indentVo.getSubbranchId());
			if(vtwoStoreRole!=null) {
				if (StringUtils.isNotBlank(vtwoStoreRole.getConcatName())) {
					indentVo.setConcatName(vtwoStoreRole.getConcatName());
				}
			}
			//获取订单明细列表
			indentVo.setIndentList(orderListVoMap.get(indentVo.getId()));
			if(indentVo.getWaitpayAmount()==null){
				indentVo.setWaitpayAmount(new BigDecimal("0,00"));
			}
		}
		Number totalRecords = jdbcTemplate.queryForObject(countgetIndentVoListStmt, Integer.class);
		ResultList<IndentVo> resultList = new ResultList<IndentVo>();
		resultList.setResultList(indentVos);
		resultList.setTotalRecords(totalRecords != null ? totalRecords.intValue() : 0);
		return resultList;
	}


	/**
	 * 获取订单商品明细
	 * @return
	 */
	private Map<String, List<IndentListVo>> getOrderListVoList(Set<String> orderIdList, IndentParams indentParams) {
		if (orderIdList.isEmpty()) {
			return null;
		}
		indentParams.setIds(StringUtils.join(orderIdList, ","));
		RowMapper<IndentListVo> indentListVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(IndentListVo.class);
		String getIndentListVoByIndentIdStmt = FreeMarkerHelper.getValueFromTpl("sql/indent/getOrderListVoByOrderId.sql", indentParams);
		List<IndentListVo> indentListVos = jdbcTemplate.query(getIndentListVoByIndentIdStmt, indentListVoRowMapper);

		Map<String, List<IndentListVo>> orderListVoMap = new HashMap<String, List<IndentListVo>>();

		for (IndentListVo indentListVo : indentListVos) {
			if (checkIsAfterSale(indentListVo)) {
				Map<String, Object> orderItemFlowMap  = new HashMap<String, Object>();
				orderItemFlowMap.put("goodSkuId", indentListVo.getTradeGoodSkuId());
				orderItemFlowMap.put("orderId", indentListVo.getIndentId());
				orderItemFlowMap.put("orderType", OrderBizType.trade.name());
				String getOrderItemFlowByGoodInfoStmt = FreeMarkerHelper.getValueFromTpl("sql/orderItemFlow/getOrderItemFlowByGoodInfo.sql", orderItemFlowMap);
				List<OrderItemAsFlow> orderItemAsFlows = jdbcTemplate.query(getOrderItemFlowByGoodInfoStmt, ParameterizedBeanPropertyRowMapper.newInstance(OrderItemAsFlow.class));
				if (!orderItemAsFlows.isEmpty()) {
					OrderItemAsFlow orderItemAsFlow = orderItemAsFlows.get(0);
					indentListVo.setGoodNum(orderItemAsFlow.getGoodNum());
					indentListVo.setFlowStatus(orderItemAsFlow.getFlowStatus());
					indentListVo.setFlowType(orderItemAsFlow.getAsType());
				}
//				indentListMap.put(indentListVo.getCargoSkuId(), indentListVo);
			}
			if (orderListVoMap.get(indentListVo.getIndentId()) == null) {
				List<IndentListVo> orderListVo = new ArrayList<IndentListVo>();
				orderListVo.add(indentListVo);
				orderListVoMap.put(indentListVo.getIndentId(), orderListVo);
			} else {
				orderListVoMap.get(indentListVo.getIndentId()).add(indentListVo);
			}

		}
		/*List<IndentListVo> indentListVoResults = new ArrayList<IndentListVo>();
		for (Map.Entry<String, IndentListVo> indentListVoEntry : indentListMap.entrySet()) {
			IndentListVo indentListVo = indentListVoEntry.getValue();
			Long count = indentListService.count(Conds.get().eq("indent_id", indentVo.getId()).eq("cargo_sku_id", indentListVo.getCargoSkuId()));
			indentListVo.setNumber(count.intValue());
			indentListVoResults.add(indentListVo);
		}*/
		return orderListVoMap;
	}



	/**
	 * 获取订单商品明细
	 * @param indentVo
	 * @return
	 */
	private List<IndentListVo> getIndentListVoList(IndentVo indentVo, IndentParams indentParams) {
		RowMapper<IndentListVo> indentListVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(IndentListVo.class);
		String getIndentListVoByIndentIdStmt = FreeMarkerHelper.getValueFromTpl("sql/indent/getIndentListVoByIndentId.sql", indentParams);
		List<IndentListVo> indentListVos = jdbcTemplate.query(getIndentListVoByIndentIdStmt, indentListVoRowMapper, indentVo.getId());
		for (IndentListVo indentListVo : indentListVos) {
			if (checkIsAfterSale(indentListVo)) {
				Map<String, Object> orderItemFlowMap  = new HashMap<String, Object>();
				orderItemFlowMap.put("goodSkuId", indentListVo.getTradeGoodSkuId());
				orderItemFlowMap.put("orderId", indentVo.getId());
				orderItemFlowMap.put("orderType", OrderBizType.trade.name());
				String getOrderItemFlowByGoodInfoStmt = FreeMarkerHelper.getValueFromTpl("sql/orderItemFlow/getOrderItemFlowByGoodInfo.sql", orderItemFlowMap);
				List<OrderItemAsFlow> orderItemAsFlows = jdbcTemplate.query(getOrderItemFlowByGoodInfoStmt, ParameterizedBeanPropertyRowMapper.newInstance(OrderItemAsFlow.class));
				if (!orderItemAsFlows.isEmpty()) {
					OrderItemAsFlow orderItemAsFlow = orderItemAsFlows.get(0);
					indentListVo.setGoodNum(orderItemAsFlow.getGoodNum());
					indentListVo.setFlowStatus(orderItemAsFlow.getFlowStatus());
					indentListVo.setFlowType(orderItemAsFlow.getAsType());
				}
//				indentListMap.put(indentListVo.getCargoSkuId(), indentListVo);
			}
		}
		/*List<IndentListVo> indentListVoResults = new ArrayList<IndentListVo>();
		for (Map.Entry<String, IndentListVo> indentListVoEntry : indentListMap.entrySet()) {
			IndentListVo indentListVo = indentListVoEntry.getValue();
			Long count = indentListService.count(Conds.get().eq("indent_id", indentVo.getId()).eq("cargo_sku_id", indentListVo.getCargoSkuId()));
			indentListVo.setNumber(count.intValue());
			indentListVoResults.add(indentListVo);
		}*/
		return indentListVos;
	}


	private boolean checkIsAfterSale(IndentListVo indentListVo) {
		if (IndentListStatus.return1.toString().equals(indentListVo.getStatus())) {
			return true;
		}
		if (IndentListStatus.return2.toString().equals(indentListVo.getStatus())) {
			return true;
		}
		if (IndentListStatus.return3.toString().equals(indentListVo.getStatus())) {
			return true;
		}

		if (IndentListStatus.returning1.toString().equals(indentListVo.getStatus())) {
			return true;
		}
		if (IndentListStatus.returning2.toString().equals(indentListVo.getStatus())) {
			return true;
		}
		if (IndentListStatus.returning3.toString().equals(indentListVo.getStatus())) {
			return true;
		}

		if (IndentListStatus.returned1.toString().equals(indentListVo.getStatus())) {
			return true;
		}
		if (IndentListStatus.returned2.toString().equals(indentListVo.getStatus())) {
			return true;
		}
		if (IndentListStatus.returned3.toString().equals(indentListVo.getStatus())) {
			return true;
		}

		return false;
	}

	@Override
	public List<IndentExport> exportExcel(IndentParams params) {
		return exportExcelIndentList(params);
	}


	@Override
	public List<IndentExport> exportExcelIndentList(IndentParams params) {
		String getIndentVoListStmt = FreeMarkerHelper.getValueFromTpl("sql/indent/queryIndentVoExport.sql", params);
		List<IndentVo> indentVoList = new ArrayList<IndentVo>();
		RowMapper<IndentVo> indentVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(IndentVo.class);
		indentVoList = jdbcTemplate.query(getIndentVoListStmt, indentVoRowMapper);
		List<IndentExport> indentExportList = new ArrayList<IndentExport>();
		Set<String> subbranchIdSet = ListUtils.getIdSet(indentVoList, "subbranchId");
		List<String> subbranchIdList = new ArrayList<String>();
		subbranchIdList.addAll(subbranchIdSet);

		Map<String,VtwoStoreRole> vtwoStoreRoleMap = vtwoStoreRoleMgrBizService.getVtwoStoreRoleByGroup(subbranchIdList);//查询用户名称

		List<String> orderIdList = Lists.newArrayList();
		for (IndentVo indentVo : indentVoList) {
			if (OrderType.presell.name().equals(indentVo.getOrderType())) {
				orderIdList.add(indentVo.getId());
			}
		}
		Map<String, List<OrderListPayment>> orderListPaymentMap = orderPaymentBizService.getPresellOrderListPaymentByOrderIds(orderIdList, OrderBizType.trade, OrderType.presell);//查询支付订单

		orderIdList.clear();
		orderIdList.addAll(ListUtils.getIdSet(indentVoList, "id"));
		Map<String, List<IndentListVo>> orderListVoExportsMap = getOrderListVoExportList(orderIdList, params); //订单明细
		for (IndentVo indentVo : indentVoList) {

			VtwoStoreRole vtwoStoreRole = vtwoStoreRoleMap.get(indentVo.getSubbranchId());
			if(vtwoStoreRole!=null) {
				if (StringUtils.isNotBlank(vtwoStoreRole.getConcatName())) {
					indentVo.setConcatName(vtwoStoreRole.getConcatName());
				}
			}

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
			List<IndentListVo> indentListVos = orderListVoExportsMap.get(indentVo.getId());

//			VtwoStoreRole vtwoStoreRole = vtwoStoreRoleMgrBizService.getVtwoStoreRoleBySubbranchId(indentVo.getSubbranchId() + "");
//			if (vtwoStoreRole == null) {
//				vtwoStoreRole = new VtwoStoreRole();
//			}
//			indentVo.setConcatName(vtwoStoreRole.getConcatName());
			IndentExport indentExport = new IndentExport();
			BeanUtils.copyProperties(indentVo, indentExport);
			indentExport.setPaymentAmount(indentVo.getPaymentAmount().toString());
			List<IndentListExport> indentListExports = new ArrayList<IndentListExport>();
			for (IndentListVo indentListVo : indentListVos) {
				IndentListExport indentListExport = new IndentListExport();
				BeanUtils.copyProperties(indentListVo, indentListExport);
				if ("T".equals(indentListVo.getGiftFlag())) {
					indentListExport.setFinalAmount("0.00");
					indentListExport.setGiftFlag("T");
				}
				indentListExports.add(indentListExport);
			}
			indentExport.setIndentListExports(indentListExports);
			indentExportList.add(indentExport);
		}
		return indentExportList;
	}

	private Map<String, List<IndentListVo>> getOrderListVoExportList(List<String> orderIdList, IndentParams orderParams) {

		Map<String, List<IndentListVo>> map = new HashMap<String, List<IndentListVo>>();
		if (orderIdList.isEmpty()) {
			return map;
		}
		orderParams.setIds(StringUtils.join(orderIdList, ","));
		String getIndentListVoByIndentIdStmt = FreeMarkerHelper.getValueFromTpl("sql/indent/getOrderListVoByOrderId.sql", orderParams);
		List<IndentListVo> indentListVos = jdbcTemplate.query(getIndentListVoByIndentIdStmt, ParameterizedBeanPropertyRowMapper.newInstance(IndentListVo.class));
		for (IndentListVo indentListVo : indentListVos) {
			if (map.get(indentListVo.getIndentId()) == null) {
				map.put(indentListVo.getIndentId(), new ArrayList<IndentListVo>());
			}
			map.get(indentListVo.getIndentId()).add(indentListVo);
		}
		return map;
	}

	@Override
	public IndentVo getDetailInfo(Long id) {

		String getIndentVoInfoStmt = SqlHelper.getSql("sql/indent/getIndentVoDetail.sql");

		RowMapper<IndentVo> indentVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(IndentVo.class);
		IndentVo indentVo = jdbcTemplate.queryForObject(getIndentVoInfoStmt, indentVoRowMapper, id);
		ValidateUtils.notNull(indentVo, "订单不存在");

		WeixinUserInfo weixinUserInfo = weixinUserInfoBizService.getWxUserById(Long.parseLong(indentVo.getBuyerId()));
		ValidateUtils.notNull(weixinUserInfo, "订单用户不存在");
		indentVo.setBuyerName(weixinUserInfo.getNickname());

		if (StringUtils.isNotBlank(indentVo.getSubbranchId())) {
			VtwoStoreRole vtwoStoreRole = vtwoStoreRoleMgrBizService.getVtwoStoreRoleBySubbranchId(indentVo.getSubbranchId());
			if (vtwoStoreRole != null && StringUtils.isNotBlank(vtwoStoreRole.getConcatName())) {
				indentVo.setConcatName(vtwoStoreRole.getConcatName());
			}
		}

		RowMapper<IndentListVo> indentListVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(IndentListVo.class);
		IndentParams params = new IndentParams();
//		List<IndentListVo> indentListVos = jdbcTemplate.query(getIndentListVoByIndentIDStmt, indentListVoRowMapper, id);
//		ValidateUtils.notNull(indentListVos, "订单明细不存在");
		Set<String> indentIds = new HashSet<String>();
		indentIds.add(indentVo.getId());
		Map<String, List<IndentListVo>> orderListVoMap = getOrderListVoList(indentIds, params);
		indentVo.setIndentList(orderListVoMap.get(indentVo.getId()));
		return indentVo;
	}



	@Override
	public Map<String, Object> updateIndentCarriage(IndentParams indentParams) {
		String[] idArr = indentParams.getIds().split(",");
		List<Indent> indents = indentService.list(Conds.get().in("id", Arrays.asList(idArr)));
		for (Indent indent : indents) {

			ShippingInfo shippingInfo = new ShippingInfo();
			shippingInfo.setOrderId(indent.getId() + "");
			shippingInfo.setOrderBizType(OrderBizType.trade);
			shippingInfo.setOrderType(OrderType.valueOf(indent.getOrderType()));
			tradeOrderManager.freeShipping(shippingInfo);

//			BigDecimal buyerCarriage = indent.getBuyerCarriage();
//			BigDecimal payAmount = indent.getFinalAmount();
//			payAmount = payAmount.subtract(buyerCarriage);
//			indentService.update(Update.byId(indent.getId()).set("buyer_carriage", 0).set("final_amount", payAmount));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", true);
		return result;
	}

	@Override
	public Map<String, Object> modifyIndentReceiverInfo(IndentParams indentParams) {
		ValidateUtils.notNull(indentParams.getId(), "ID不能为null");
		Indent indent = indentService.getById(indentParams.getId());
		ValidateUtils.notNull(indent, "当前订单不存在");
		indent.setReceiver(indentParams.getReceiver());
		indent.setReceiverPhone(indentParams.getReceiverPhone());
		indent.setAddress(indentParams.getAddress());
		indentService.update(indent);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", true);
		return result;
	}

	@Override
	public Map<String, Object> afterSaleIndentDetail(String orderNo, String indentListId, String goodSkuId, String flowStatus, String afterSaleService) {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();

		//获取订单信息
		String getIndentVoInfoStmt = SqlHelper.getSql("sql/indent/getIndentVoDetail.sql");
		RowMapper<IndentVo> indentVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(IndentVo.class);
		IndentVo indentVo = jdbcTemplate.queryForObject(getIndentVoInfoStmt, indentVoRowMapper, orderNo);
		ValidateUtils.notNull(indentVo, "订单不存在");

		//获取买家信息
		WeixinUserInfo weixinUserInfo = weixinUserInfoBizService.getWxUserById(Long.parseLong(indentVo.getBuyerId()));
		ValidateUtils.notNull(weixinUserInfo, "订单用户不存在");
		indentVo.setBuyerName(weixinUserInfo.getNickname());

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




		IndentParams params = new IndentParams();
//		params.setAfterSaleService(afterSaleService);
		params.setGoodSku(goodSkuId);
//		param.put("afterSaleService", afterSaleService);
//		param.put("cargoSkuId", goodSkuId);
//		String getIndentListVoByIndentIdStmt = FreeMarkerHelper.getValueFromTpl("sql/indent/getIndentListVoByIndentId.sql", param);
//		RowMapper<IndentListVo> indentListVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(IndentListVo.class);
//		List<IndentListVo> indentListVos = jdbcTemplate.query(getIndentListVoByIndentIdStmt, indentListVoRowMapper, indentVo.getId());
		indentVo.setIndentList(getIndentListVoList(indentVo, params));

		//获取订单流程信息getOrderItemFolwInfo
//		RowMapper<OrderItemAsFlow> orderItemAsFlowRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(OrderItemAsFlow.class);
//		TradeGoodSku tradeGoodSku = tradeGoodSkuService.get(Conds.get().eq("cargo_sku_id",goodSkuId));
//		OrderItemAsFlow orderItemAsFlow = jdbcTemplate.queryForObject(getOrderItemFolwInfoStmt, orderItemAsFlowRowMapper, indentVo.getId(), goodSkuId);
		Map<String, Object> orderItemFlowMap  = new HashMap<String, Object>();
		orderItemFlowMap.put("goodSkuId", goodSkuId);
		orderItemFlowMap.put("orderId", indentVo.getId());
		orderItemFlowMap.put("orderType", OrderBizType.trade.name());
		String getOrderItemFlowByGoodInfoStmt = FreeMarkerHelper.getValueFromTpl("sql/orderItemFlow/getOrderItemFlowByGoodInfo.sql", orderItemFlowMap);
		OrderItemAsFlow orderItemAsFlow = new OrderItemAsFlow();
		List<OrderItemAsFlow> orderItemAsFlows = jdbcTemplate.query(getOrderItemFlowByGoodInfoStmt, ParameterizedBeanPropertyRowMapper.newInstance(OrderItemAsFlow.class));
		if (!orderItemAsFlows.isEmpty()) {
			orderItemAsFlow = orderItemAsFlows.get(0);
		}

		result.put("indent", indentVo);
		result.put("orderItemAsFlow", orderItemAsFlow);

		Map<String, Object> replyMap = new HashMap<String, Object>();
		replyMap.put("orderType", OrderBizType.trade.name());
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
	public Map<String, Object> getRoleInfoByStaffId(String staffId) {
		return jdbcTemplate.queryForMap(getRoleInfoByStaffId, staffId);
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
	public List<IndentInvoiceVo> getIndentInvoiceList(IndentParams indentParams) {
		String queryIndentInvoiceExportStmt = FreeMarkerHelper.getValueFromTpl("sql/indent/queryIndentInvoiceExport.sql", indentParams);
		RowMapper<IndentInvoiceVo> indentInvoiceVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(IndentInvoiceVo.class);
		List<IndentInvoiceVo> indentInvoiceVos = jdbcTemplate.query(queryIndentInvoiceExportStmt, indentInvoiceVoRowMapper);
		return indentInvoiceVos;
	}

	@Override
	public ResultData shipIndent(ShippingInfo shippingInfo) {
		Indent indent = indentService.getById(shippingInfo.getOrderId());
		updateStock(indent, shippingInfo.getUserId()); //更新库存
		shippingInfo.setExpressId(indent.getExpressId());
		tradeOrderManager.shipmentOrder(shippingInfo); //订单发货
		return ResultData.createSuccess();
	}

	@Override
	public void updateRemark(Long id, String remark, String remarkLevel) {
		indentService.update(Update.byId(id).set("remark_info", remark).set("remark_level", remarkLevel));
	}

	@Override
	public ResultData batchCancelOrder(List<IndentParams> indentParams, OrderBizType orderBizType, String userId) {
		ValidateUtils.notNull(indentParams, "订单信息不存在");
		ValidateUtils.notNull(orderBizType, "业务类型不正确");
		ValidateUtils.notNull(userId, "用户ID存在");
		for (IndentParams indentParam : indentParams) {
			OrderCancelInfo cancelInfo = new OrderCancelInfo();
			cancelInfo.setOrderBizType(orderBizType);
			cancelInfo.setCancelType(OrderCancelType.manager);
			cancelInfo.setUserId(userId);
			cancelInfo.setOrderType(OrderType.valueOf(indentParam.getOrderType()));
			cancelInfo.setOrderId(indentParam.getId());
			tradeOrderManager.cancelOrder(cancelInfo);
		}
		return ResultData.createSuccess();
	}

	@Override
	public Map<String, List<IndentListVo>> getIndentListMapByName(Set<String> indentNameSet) {

		if (indentNameSet.isEmpty()) {
			return new HashMap<>();
		}
		Map<String, Object> param = new HashMap<>();
		param.put("indentNameList", StringUtils.join(indentNameSet, ","));
		String getIndentListByIndentNameStmt = FreeMarkerHelper.getValueFromTpl("sql/indent/getIndentListByIndentName.sql", param);
		List<IndentListVo> indentListList = jdbcTemplate.query(getIndentListByIndentNameStmt, ParameterizedBeanPropertyRowMapper.newInstance(IndentListVo.class));
		Map<String, List<IndentListVo>> indentListMap = new HashMap<>();

		for (IndentListVo list : indentListList) {
			if (indentListMap.get(list.getIndentName()) == null) {
				indentListMap.put(list.getIndentName(), new ArrayList<>());
			}
			indentListMap.get(list.getIndentName()).add(list);
		}
		return indentListMap;
	}

	/**
	 * 更新库存
	 * @param indent
	 */
	private void updateStock(Indent indent, Long userId) {
		switch (indent.getStatus()) {
			case 5:
				List<IndentList> indentLists = indentListService.list(Conds.get().eq("indent_id", indent.getId()));
				for (IndentList indentList : indentLists) {
					StockQuery stockQuery = new StockQuery();
					stockQuery.setUserId(userId);
					stockQuery.setNum(indentList.getNumber());
					stockQuery.setOrderType(OrderBizType.trade);
					stockQuery.setSkuId(indentList.getTradeGoodSkuId());
					indentList.setStatus(IndentListStatus.ship.toString());
					indentList.setLastUpdated(new Date());
					indentListService.update(indentList);
					stockBizService.useStock(stockQuery);
				}
				break;
		}
	}
}
