package pub.makers.shop.baseOrder.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.baseOrder.entity.OrderListPayment;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.baseOrder.pojo.PaymentQuery;
import pub.makers.shop.promotion.entity.PresellActivity;
import pub.makers.shop.tradeOrder.vo.OrderPayInfo;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service(version="1.0.0")
public class OrderPaymentBizServiceImpl implements OrderPaymentBizService{

	@Resource(name="purchaseOrderListPaymentServiceImpl")
	private OrderListPaymentService purchasePaymentService;
	@Resource(name="tradeOrderListPaymentServiceImpl")
	private OrderListPaymentService tradePaymentService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public OrderListPayment getByBaseOrder(PaymentQuery query) {
		
		BaseOrder bo = query.getOrder();
		OrderListPayment payment = null;
		// 查询最近的一个未付款的支付记录
		OrderListPaymentService paymentService = getPaymentService(query.getOrderBizType());
		List<OrderListPayment> paymentList = paymentService.list(Conds.get().eq("orderId", query.getOrder().getId()).order("stage_num asc"));
		
		Date now = new Date();
		// 没有就创建一个
		if (paymentList.size() == 0){
			// 付款方式为一次性付款
			// 支付金额取订单的实际支付金额
			payment = new OrderListPayment();
			payment.setId(IdGenerator.getDefault().nextStringId());
			payment.setPayStart(now);
			payment.setPayEnd(DateUtils.addHours(now, 1));
			payment.setOrderId(bo.getId().toString());
			payment.setOrderBizType(query.getOrderBizType().name());
			payment.setOrderType(query.getOrderType().name());
			payment.setStageNum(1);
			payment.setWaitpayAmount(bo.getWaitpayAmount());
			payment.setDelFlag(BoolType.F.name());
			payment.setPayStatus(BoolType.F.name());
			
			paymentService.insert(payment);
		}
		// 有记录,查找一个最近未付款的记录
		else {
			for (OrderListPayment p : paymentList){
				if (BoolType.F.name().equals(p.getPayStatus())){
					payment = p;
					break;
				}
			}
		}
		
		ValidateUtils.isTrue(payment.getPayStart().compareTo(now) <= 0, "订单支付时间还未开始");
		ValidateUtils.isTrue(payment.getPayEnd().compareTo(now) > 0, "订单支付时间已经结束");
		ValidateUtils.notNull(payment, "订单已经付过款了");
		
		return payment;
	}

	private OrderListPaymentService getPaymentService(OrderBizType orderBizType){
		
		if (OrderBizType.purchase.equals(orderBizType)){
			return purchasePaymentService;
		}
		return tradePaymentService;
	}

	@Override
	public OrderListPayment getById(String id) {
		OrderListPayment payment = purchasePaymentService.getById(id);
		if (payment == null){
			payment = tradePaymentService.getById(id);
		}
		
		return payment;
	}

	@Override
	public void updateToPayed(OrderPayInfo info) {
		
		OrderListPaymentService paymentService = getPaymentService(info.getOrderBizType());
		OrderListPayment payment = paymentService.getById(info.getPaymentId());
		ValidateUtils.notNull(payment, "支付信息不存在");
		ValidateUtils.isTrue(BoolType.F.name().equals(payment.getPayStatus()), "订单已经支付了");
		
		payment.setPayStatus(BoolType.T.name());
		payment.setPayAmount(info.getPaymentAmount());
		payment.setPayWay(info.getPayWay());
		payment.setPayAccount(info.getPayAccount());
		payment.setPayTime(new Date());
		
		paymentService.update(payment);
		
	}

	@Override
	public OrderListPayment getOrderListPaymentByOrderId(PaymentQuery paymentQuery) {

		ValidateUtils.notNull(paymentQuery, "预售信息不能为空");
		ValidateUtils.notNull(paymentQuery.getOrder(), "订单不能为空");
		ValidateUtils.notNull(paymentQuery.getStageNum(), "期数不能为空");
		ValidateUtils.notNull(paymentQuery.getOrderType(), "订单类型不能为空");
		OrderListPaymentService paymentService = getPaymentService(paymentQuery.getOrderBizType());

		OrderListPayment orderListPayment = paymentService.get(Conds.get().eq("orderId", paymentQuery.getOrder().getId())
				.eq("order_type", paymentQuery.getOrderType().name())
				.eq("stage_num", paymentQuery.getStageNum()).eq("pay_status", BoolType.T.name()));
		return orderListPayment;
	}

	@Override
	public OrderListPayment getFinalOrderListByBaseOrder(PaymentQuery paymentQuery) {
		OrderListPaymentService paymentService = getPaymentService(paymentQuery.getOrderBizType());
		List<OrderListPayment> orderListPayments = paymentService.list(Conds.get().eq("orderId", paymentQuery.getOrder().getId())
				.eq("order_type", paymentQuery.getOrderType().name())
				.eq("pay_status", BoolType.T.name()));
		if (orderListPayments.isEmpty()) {
			return null;
		}
		return orderListPayments.get(orderListPayments.size() - 1);
	}

	@Override
	public Map<String, List<OrderListPayment>> getPresellOrderListPaymentByOrderIds(List<String> orderIdList, OrderBizType orderBizType, OrderType orderType) {
		OrderListPaymentService orderListPaymentService = getPaymentService(orderBizType);
		List<OrderListPayment> orderListPayments = orderListPaymentService.list(Conds.get().in("order_id", orderIdList).eq("order_type", orderType.name()));
		Map<String, List<OrderListPayment>> resultMap = new HashMap<String, List<OrderListPayment>>();
		if (orderIdList.isEmpty() || orderListPayments.isEmpty()) {
			return resultMap;
		}
		for (OrderListPayment orderListPayment : orderListPayments) {
			if (resultMap.get(orderListPayment.getOrderId()) == null) {
				resultMap.put(orderListPayment.getOrderId(), new ArrayList<OrderListPayment>());
			}
			resultMap.get(orderListPayment.getOrderId()).add(orderListPayment);
		}
		return resultMap;
	}

	@Override
	public void createPayment(OrderBizType orderBizType, List<OrderListPayment> paymentList) {
		
		OrderListPaymentService paymentService = getPaymentService(orderBizType);
		for (OrderListPayment payment : paymentList){
			payment.setId(IdGenerator.getDefault().nextStringId());
			paymentService.insert(payment);
		}
	}

	@Override
	public List<OrderListPayment> createPresellPayment(PresellActivity act, String orderId, List<BigDecimal> paymentAmounts, OrderBizType orderBizType) {
    	List<OrderListPayment> paymentList = Lists.newArrayList();
    	
    	// 先创建首付款的支付信息
    	OrderListPayment payment = new OrderListPayment();
    	payment.setOrderId(orderId);
    	payment.setOrderBizType(orderBizType.name());
    	payment.setOrderType(OrderType.presell.name());
    	payment.setStageCode("stage1");
		if (paymentAmounts.size() > 1){
			payment.setStageDesc("预售定金");
		} else {
			payment.setStageDesc("预售全款");
		}

    	// 首款的支付开始时间为活动开始时间
    	payment.setPayStart(act.getPresellStart());
    	// 首款的支付结束时间为活动结束时间
    	payment.setPayEnd(act.getPresellEnd());
    	payment.setStageNum(1);
    	payment.setPayStatus(BoolType.F.name());
    	payment.setWaitpayAmount(paymentAmounts.get(0));
    	payment.setDelFlag(BoolType.F.name());
    	paymentList.add(payment);
    	
    	// 如果是二阶段付款，需要创建第二阶段的付款记录
    	if (paymentAmounts.size() > 1){
    		OrderListPayment payment2 = new OrderListPayment();
    		payment2.setOrderId(orderId);
    		payment2.setOrderBizType(orderBizType.name());
    		payment2.setOrderType(OrderType.presell.name());
    		payment2.setStageCode("stage2");
    		payment2.setStageDesc("预售尾款");
        	payment2.setStageNum(2);
        	payment2.setPayStart(act.getPaymentStart());
        	payment2.setPayEnd(act.getPaymentEnd());
        	payment2.setPayStatus(BoolType.F.name());
        	payment2.setDelFlag(BoolType.F.name());
        	BigDecimal amount2 = paymentAmounts.get(1);
        	payment2.setWaitpayAmount(amount2);
        	paymentList.add(payment2);
    	}
    	
    	createPayment(orderBizType, paymentList);
    	
    	return paymentList;
	}

	@Override
	public void fixPaymentAmount(String orderId, BigDecimal amount, OrderBizType orderBizType) {
		
		OrderListPaymentService paymentService = getPaymentService(orderBizType);
		// 查询最近一个未支付付款单
		OrderListPayment payment = paymentService.get(Conds.get().eq("orderId", orderId).eq("payStatus", BoolType.F.name()).eq("delFlag", BoolType.F.name()).order("date_created desc"));
		if (payment == null){
			return;
		}
		BigDecimal newAmount = payment.getWaitpayAmount().subtract(amount);
		ValidateUtils.isTrue(newAmount.compareTo(BigDecimal.ZERO) >= 0, "减免金额不能超过支付金额");
		
		// 重新生成一次支付单号
		paymentService.update(Update.byId(payment.getId()).set("waitpay_amount", newAmount).set("id", IdGenerator.getDefault().nextId()));
		
	}

	@Override
	public List<OrderListPayment> getPaymentList(String orderNo, OrderBizType orderBizType) {
		String sql = "";

		if (orderBizType ==  OrderBizType.purchase) {
			sql = "select polp.* from purchase_order_list_payment polp " +
					"left join purchase_order po on po.id = polp.order_id " +
					"where po.order_no = ? and po.status=1";
		} else {
			sql = "select tolp.* from trade_order_list_payment tolp " +
					"left join indent po on po.id = tolp.order_id " +
					"where po.name = ? and po.status=1";
		}

		List<OrderListPayment> orderListPayments = jdbcTemplate.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(OrderListPayment.class), orderNo);
		return orderListPayments;
	}


}
