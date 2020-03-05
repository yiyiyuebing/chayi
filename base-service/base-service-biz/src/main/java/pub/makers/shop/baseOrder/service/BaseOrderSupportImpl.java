package pub.makers.shop.baseOrder.service;

import com.dev.base.utils.SpringContextUtils;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.pay.PayService;
import com.lantu.base.pay.PayServicePool;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.service.SysDictService;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.baseOrder.entity.OrderListPayment;
import pub.makers.shop.baseOrder.enums.*;
import pub.makers.shop.baseOrder.pojo.*;
import pub.makers.shop.baseOrder.utils.BaseOrderHelps;
import pub.makers.shop.invoice.service.InvoiceBizService;
import pub.makers.shop.invoice.vo.InvoiceVo;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrder;
import pub.makers.shop.tradeOrder.entity.Indent;
import pub.makers.shop.tradeOrder.vo.OrderPayInfo;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class BaseOrderSupportImpl implements BaseOrderSupport{

	protected abstract OrderBizType getOrderBizType();
	
	protected abstract OrderType getOrderType();
	
	/**
	 * 处理订单并保存库存
	 * @param order
	 */
	protected abstract void doCreateOrder(BaseOrder order);
	
	/**
	 * 处理取消订单的自定义操作
	 * @param order
	 * @param cancelType
	 */
	protected abstract void doCancelOrder(BaseOrder order, OrderCancelType cancelType);
	
	protected abstract void checkOrder(BaseOrder t);
	
	protected abstract String getOrderCode();
	
	protected abstract BigDecimal calcFreight(BaseOrder order);
	
	protected abstract void initItemProperty(BaseOrder order);
	
	protected abstract BaseOrder getOrderById(String orderId);
	
	protected abstract void doPayOrder(OrderPayInfo info, BaseOrder bo);
	
	@Autowired
	private PayServicePool payServicePool;
	
	private OrderPaymentBizService orderPaymentService;
	@Autowired
    private SysDictService sysDictService;
	@Autowired
	private TransactionTemplate transationTemplate;
	@Autowired
    private OrderPreVerifyBizService orderPreverifyService;
	@Autowired
    private OrderPromotionBizService promotionBizService;
	@Autowired
	private InvoiceBizService invoiceBizService;
	
	@PostConstruct
	public void init(){
		payServicePool = SpringContextUtils.getBean(PayServicePool.class);
		orderPaymentService = SpringContextUtils.getBean(OrderPaymentBizService.class);
	}
	
	
	public BaseOrder preCreate(BaseOrder t){
		
		TradeContext tc = t.getTradeContext();

		// 检查前置验证是否通过
		OrderVerificationResult cr = orderPreverifyService.validate(t.getItemList(), getOrderBizType(), getOrderType(), tc);
		ValidateUtils.isTrue(cr.isSuccess(), cr.getMessage());
		// 构建促销查询条件
		PromotionOrderQuery query = new PromotionOrderQuery();
    	query.setOrderInfo(t);
    	query.setOrderType(getOrderType());
    	query.setOrderBizType(getOrderBizType());
    	final BaseOrder order = promotionBizService.applyGiftRule(query, tc);

		// 去掉赠品
		List<BaseOrderItem> itemList = Lists.newArrayList();
		for (BaseOrderItem baseOrderItem : order.getItemList()) {
			if (!BoolType.T.name().equals(baseOrderItem.getGiftFlag())) {
				itemList.add(baseOrderItem);
			}
		}
		order.setItemList(itemList);

    	// 初始化订单的其他属性
    	initItemProperty(order);

		// 应用订单价格插件
		query.setOrderInfo(order);
		promotionBizService.applyPriceRule(query, tc);
    	
    	// 统计订单的商品数量，商品总额，待支付金额，总优惠金额
    	BigDecimal totalWaitPay = BigDecimal.ZERO;
    	BigDecimal totalDiscount = BigDecimal.ZERO;
    	int totalNum = 0;
    	for (BaseOrderItem item : order.getItemList()){
    		
    		// 商品总额 = 订单商品的原价之和
    		totalWaitPay = totalWaitPay.add(item.getWaitPayAmont());
    		totalDiscount = totalDiscount.add(item.getDiscountAmount());
    		totalNum = totalNum + item.getBuyNum();
    	}
    	
    	order.setNumber(totalNum);
    	order.setStatus(OrderStatus.pay.getDbData());

    	// 计算订单金额
    	order.setTotalPrice(totalWaitPay.add(totalDiscount));
    	order.setDiscountAmount(totalDiscount);
    	order.setWaitpayAmount(totalWaitPay);
    	order.setPaymentAmount(BigDecimal.ZERO);

    	order.setCreateTime(new Date());
    	order.setTimeout(getTimeout(new Date()));    	
    	order.setBuyerDelFlag(BoolType.F.name());
    	order.setSellerDelFlag(BoolType.F.name());
    	order.setOrderType(getOrderType().name());
    	
    	// TODO 不同种类订单的订单计算金额不同
    	fixOrderAmount(order);

		// 应用订单总金额插件
		promotionBizService.applyTotalPriceRule(query, tc);
		// 计算订单运费
		if (!OrderType.presell.equals(getOrderType())) {
			BigDecimal freight = BigDecimal.ZERO;
			TradeContext tradeContext = order.getTradeContext();
			if (StringUtils.isNotEmpty(tradeContext.getCity())) {
				freight = calcFreight(order);
			}
			order.setFreight(freight);
			order.setWaitpayAmount(order.getWaitpayAmount().add(freight));
		}

		order.setItemList(BaseOrderHelps.groupItems(order.getItemList()));
    	
    	return order;
	}
	
	/**
	 * 修正订单的金额
	 * @param order
	 */
	protected void fixOrderAmount(BaseOrder order){
		
	}
	
	@Override
	public BaseOrder createOrder(BaseOrder t) {
		
		// 订单前置条件检查
		checkOrder(t);
		
		TradeContext tc = t.getTradeContext();
		
		// 检查前置验证是否通过
		OrderVerificationResult cr = orderPreverifyService.validate(t.getItemList(), getOrderBizType(), getOrderType(), tc);
		ValidateUtils.isTrue(cr.isSuccess(), cr.getMessage());
		
		// 构建促销查询条件
		PromotionOrderQuery query = new PromotionOrderQuery();
    	query.setOrderInfo(t);
    	query.setOrderType(getOrderType());
    	query.setOrderBizType(getOrderBizType());
    	final BaseOrder order = promotionBizService.applyGiftRule(query, tc);
    	
    	// 订单中的商品分离
    	
    	final String orderId = IdGenerator.getDefault().nextId() + "";
    	order.setOrderId(orderId);
    	order.setOrderCode(getOrderCode());

		// 订单商品分离
		List<? extends BaseOrderItem> itemList = BaseOrderHelps.splitItems(order.getItemList());
		order.setItemList(itemList);

    	// 初始化订单的其他属性
    	initItemProperty(order);
    	
    	// TODO 应用订单价格插件
    	query.setOrderInfo(order);
    	promotionBizService.applyPriceRule(query, tc);
    	
    	// 统计订单的商品数量，商品总额，待支付金额，总优惠金额
    	BigDecimal totalWaitPay = BigDecimal.ZERO;
    	BigDecimal totalDiscount = BigDecimal.ZERO;
    	int totalNum = 0;
    	for (BaseOrderItem item : order.getItemList()){
    		
    		// 商品总额 = 订单商品的原价之和
    		totalWaitPay = totalWaitPay.add(item.getWaitPayAmont());
    		totalDiscount = totalDiscount.add(item.getDiscountAmount());
    		totalNum = totalNum + item.getBuyNum();
    	}
    	
    	order.setNumber(totalNum);
    	order.setStatus(OrderStatus.pay.getDbData());

    	// 计算订单金额
    	order.setTotalPrice(totalWaitPay.add(totalDiscount));
    	order.setDiscountAmount(totalDiscount);
    	order.setWaitpayAmount(totalWaitPay);
    	order.setPaymentAmount(BigDecimal.ZERO);

    	order.setCreateTime(new Date());
    	order.setTimeout(getTimeout(new Date()));
    	
    	order.setBuyerDelFlag(BoolType.F.name());
    	order.setSellerDelFlag(BoolType.F.name());
    	order.setOrderType(getOrderType().name());

		doCreateOrder(order);
		InvoiceVo invoiceVo = order.getInvoiceVo();
		if (invoiceVo != null) {
			invoiceBizService.saveInvoice(invoiceVo);
		}
//    	// 保存订单
//    	transationTemplate.execute(new TransactionCallback<Object>() {
//
//			@Override
//			public Object doInTransaction(TransactionStatus arg0) {
//				
//				
//				return null;
//			}
//		});

		order.setItemList(BaseOrderHelps.groupItems(order.getItemList()));
		return order;
	}
	
	public void cancelOrder(final String userId, String orderId, final OrderCancelType cancelType){
		
		final BaseOrder order = getOrderById(orderId);
		ValidateUtils.notNull(order, "订单不存在");
		if (cancelType != OrderCancelType.manager) {
			ValidateUtils.isTrue(userId.equals(order.getBuyerId()), "只能取消自己的订单");
		}
		ValidateUtils.isTrue(order.getStatus().equals(OrderStatus.pay.getDbData()) || order.getStatus().equals(OrderStatus.payend.getDbData()), "只能取消未付款的订单");
		
		transationTemplate.execute(new TransactionCallback<Object>() {

			@Override
			public Object doInTransaction(TransactionStatus arg0) {
				
				doCancelOrder(order, cancelType);
				return null;
			}
		});
		
	}

	public String toPay(PayParam param){
		
		Validate.notNull(param.getClientIp(), "客户端IP不能为空");
		ValidateUtils.notNull(param.getTradeType(), "交易类型不能为空");
		
		BaseOrder order = getOrderById(param.getOrderId());
		ValidateUtils.notNull(order, "订单不存在");
		ValidateUtils.isTrue(order.getStatus() == OrderStatus.pay.getDbData() || order.getStatus() == OrderStatus.payend.getDbData() , "该订单已成功购买，请到订单列表查看");
		
		PaymentQuery query = new PaymentQuery();
		query.setOrderBizType(getOrderBizType());
		query.setOrderType(getOrderType());
		query.setOrder(order);
		// 获取支付单
		OrderListPayment payInfo = orderPaymentService.getByBaseOrder(query);
		
		PayService payService = payServicePool.getPayService(param.getPayWay());
		
		com.lantu.base.pay.entity.BaseOrder payOrder = new com.lantu.base.pay.entity.BaseOrder();
		payOrder.setOrderNo(payInfo.getId());
		if (order instanceof Indent){
			payOrder.setOrderName("优茶联订单-" + ((Indent)order).getName());
		}
		else if (order instanceof PurchaseOrder){
			payOrder.setOrderName("优茶联订单-" + ((PurchaseOrder)order).getOrderNo());
		}
		
		payOrder.setAmount(payInfo.getWaitpayAmount());
		payOrder.setClientIp(param.getClientIp());
		payOrder.setOpenId(param.getOpenId());
		
		String channel = "";
		// 如果是微信支付，需要处理支付渠道的问题
		if ("wxpay".equals(param.getPayWay())){
			// 商城只有一个账号
			if (OrderBizType.trade.equals(getOrderBizType())){
				channel = "";
			}
			// 采购分APP和普通
			else {
				if ("APP".equals(param.getTradeType())){
					channel = "appPay";
				}
				else {
					channel = "pur";
				}
			}
		}
		
		return payService.pay(payOrder, param.getTradeType(), channel);
	}
	
	
	
	@Override
	public void payOrder(final OrderPayInfo payInfo) {

		ValidateUtils.notNull(payInfo.getOrderType(), "订单类型不能为空");
		ValidateUtils.notNull(payInfo.getOrderBizType(), "订单业务类型不能为空");
		ValidateUtils.notNull(payInfo.getPayAccount(), "支付账号不能为空");
		ValidateUtils.notNull(payInfo.getPaymentId(), "支付单号不能为空");
		ValidateUtils.notNull(payInfo.getPayWay(), "支付方式不能为空");
		ValidateUtils.notNull(payInfo.getPayAccount(), "支付金额不能为空");
		
		final BaseOrder order = getOrderById(payInfo.getOrderId());
        ValidateUtils.notNull(order, "订单不存在");
        ValidateUtils.isTrue(order.getStatus().equals(OrderStatus.pay.getDbData()) || order.getStatus().equals(OrderStatus.payend.getDbData()), "订单无法支付");
        
        transationTemplate.execute(new TransactionCallback<Object>() {

			@Override
			public Object doInTransaction(TransactionStatus arg0) {
				
				doPayOrder(payInfo, order);
				return null;
			}
		});
        
	}

	private Date getTimeout(Date createTime) {
        SysDict dict = sysDictService.get(Conds.get().eq("dict_type", "order_timeout"));
        Integer minute = dict == null ? 30 : new BigDecimal(dict.getValue()).multiply(new BigDecimal(60)).intValue();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createTime);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

}
