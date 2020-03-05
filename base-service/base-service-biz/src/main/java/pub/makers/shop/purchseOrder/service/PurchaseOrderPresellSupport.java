package pub.makers.shop.purchseOrder.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.constants.Constants;
import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.service.SysDictService;
import pub.makers.shop.base.utils.DateParseUtil;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.baseOrder.entity.OrderListPayment;
import pub.makers.shop.baseOrder.entity.OrderPresellExtra;
import pub.makers.shop.baseOrder.enums.*;
import pub.makers.shop.baseOrder.pojo.*;
import pub.makers.shop.baseOrder.service.BaseOrderSupportImpl;
import pub.makers.shop.baseOrder.service.OrderPaymentBizService;
import pub.makers.shop.baseOrder.service.OrderPromotionBizService;
import pub.makers.shop.cargo.service.CargoOutboundBizService;
import pub.makers.shop.logistics.service.FreightTplBizService;
import pub.makers.shop.message.enums.MessageStatus;
import pub.makers.shop.message.service.MessageBizService;
import pub.makers.shop.promotion.entity.PresellActivity;
import pub.makers.shop.promotion.enums.PresellType;
import pub.makers.shop.promotion.service.PresellBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseOrderListService;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrder;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrderList;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderService;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.store.service.SubbranchAccountBizService;
import pub.makers.shop.store.service.SubbranchBizService;
import pub.makers.shop.thirdpart.service.U8SyncService;
import pub.makers.shop.tradeOrder.enums.IndentDealStatus;
import pub.makers.shop.tradeOrder.enums.IndentListStatus;
import pub.makers.shop.tradeOrder.vo.OrderPayInfo;
import pub.makers.shop.tradeOrder.vo.ShippingInfo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 采购预售订单支持
 */
@Service
public class PurchaseOrderPresellSupport extends BaseOrderSupportImpl {

    @Autowired
    private PurchaseOrderService purchaseOrderService;
    
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private CargoOutboundBizService cargoOutboundBizService;
    @Reference(version = "1.0.0")
    private SubbranchBizService subbranchBizService;
    @Reference(version = "1.0.0")
    private SubbranchAccountBizService subbranchAccountBizService;
    @Reference(version = "1.0.0")
	private MessageBizService messageBizService;
    @Autowired
    private FreightTplBizService freightTplBizService;
    @Autowired
    private PresellBizService presellBizService;
    @Autowired
    private OrderPaymentBizService paymentBizService;
    @Autowired
    PurchaseOrderHelper purchaseOrderHelper;
    @Autowired
    private U8SyncService u8Service;
    @Autowired
    private PurchaseOrderListService orderListService;
    @Autowired
	private TransactionTemplate transationTemplate;
	@Autowired
	private SysDictService sysDictService;
	@Autowired
	private OrderPromotionBizService promotionBizService;
    
	protected void fixOrderAmount(BaseOrder bo){
		presellBizService.processBaseorderAndCalcpayment(bo, getOrderBizType(), false);
	}

    @Override
	protected void doCreateOrder(final BaseOrder bo) {
		
    	// 预售的订单金额需要重新计算
    	    	
		final PurchaseOrderVo order = (PurchaseOrderVo) bo;
		order.setBuyerCarriage(bo.getFreight().setScale(2).toString());
		
		transationTemplate.execute(new TransactionCallbackWithoutResult(){

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				// 处理订单的价格以及生成支付单
				presellBizService.processBaseorderAndCalcpayment(bo, getOrderBizType());

				PromotionOrderQuery query = new PromotionOrderQuery();
				query.setOrderInfo(bo);
				query.setOrderType(getOrderType());
				query.setOrderBizType(getOrderBizType());
				TradeContext tc = bo.getTradeContext();
				// 应用订单总金额变化插件
				promotionBizService.applyTotalPriceRule(query, tc);

				// 按顺序保存订单明细
				Integer num = 0;
				for (BaseOrderItem baseOrderItem : bo.getItemList()) {
					baseOrderItem.setId(IdGenerator.getDefault().nextStringId());
					// 配件不计入商品数量
					if (!BoolType.T.name().equals(baseOrderItem.getGiftFlag())) {
						num += baseOrderItem.getBuyNum();
					}
				}
				bo.setNumber(num);
				
				// 锁定库存
				presellBizService.lockStocks(bo.getItemList(), OrderBizType.purchase);
				
				order.setCreateTime(new Date());
		        order.setSubbranchId(order.getBuyerId());
		        
		        purchaseOrderService.saveOrder(order);
				// 新增出库单
				cargoOutboundBizService.addOutbound(order.getId(), getOrderBizType(), Long.valueOf(order.getBuyerId()), "进货出库");
			}
			
			
		});
		
	}
    
    @Override
	protected void doCancelOrder(BaseOrder bo, OrderCancelType cancelType) {
		
    	PurchaseOrder order = (PurchaseOrder)bo;
    	// 更新订单状态
    	purchaseOrderService.updateOrderToCancel(order, cancelType);
    	List<PurchaseOrderList> orderList = orderListService.listByOrderId(order.getId());
    	
        // 更新库存
		presellBizService.releaseLockStocks(orderList, OrderBizType.purchase);
		// 取消出货单
		cargoOutboundBizService.cancelOutbound(order.getId(), "进货出库");
	}


    @Override
    public void doPayOrder(final OrderPayInfo payInfo, BaseOrder bo) {
    	
    	// TODO 根据预售类型和支付的阶段来判断订单支付完成需要处于什么状态
    	OrderPresellExtra extra = presellBizService.getExtra(OrderBizType.purchase, bo.getId().toString());
    	OrderListPayment payment = paymentBizService.getById(payInfo.getPaymentId());
    	List<PurchaseOrderList> orderList = orderListService.listByOrderId(bo.getOrderId());
    	final PurchaseOrder order = (PurchaseOrder) bo;
    	// 只有一期,状态直接为已支付
    	if (1 == payment.getStageNum() && PresellType.one.name().equals(extra.getPresellType())){
    		purchaseOrderService.updateOrderToPayed(order, payInfo);
	        // 确认出货单
	        cargoOutboundBizService.confirmOutbound(order.getId(), "进货出库");
	        // 同步U8库存
	        u8Service.account(order.getId(), getOrderBizType(), getOrderType(), 1);
    	}
    	// 二期
    	else if (PresellType.second.name().equals(extra.getPresellType())){
    		//  第一期
    		if (1 == payment.getStageNum()){
    			purchaseOrderService.updateOrderToPayed(order, payInfo, OrderStatus.payend.getDbData());
    			
    		}
    		//  第二期
    		else if (2 == payment.getStageNum()){
    			purchaseOrderService.updateOrderToPayed(order, payInfo);
				// 确认出货单
				cargoOutboundBizService.confirmOutbound(order.getId(), "进货出库");
    	        // 同步U8库存
    	        u8Service.account(order.getId(), getOrderBizType(), getOrderType(), 1);
    		}
    	}
    	else {
    		// 无法预期的错误
    	}
    	
    }

    @Override
    public void shipmentOrder(final ShippingInfo si) {
        final PurchaseOrder order = purchaseOrderService.getById(si.getOrderId());
        ValidateUtils.notNull(order, "订单不存在");
        ValidateUtils.isTrue(order.getStatus().equals(OrderStatus.ship.getDbData()), "订单无法发货");

		order.setReceiveTimeout(getReceiveTimeout(new Date()));
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                // 更新订单状态
            	purchaseOrderService.updateOrderToShipment(order, si);
            }
        });
        
        purchaseOrderHelper.sendShipSms(order, si);
    }

    @Override
    public void confirmReceipt(String userId, String orderId, final OrderConfirmType confirmType) {
        final PurchaseOrder order = purchaseOrderService.getById(orderId);
        ValidateUtils.notNull(order, "订单不存在");
        ValidateUtils.isTrue(userId.equals(order.getBuyerId()), "只能确认自己的订单");
        ValidateUtils.isTrue(order.getStatus().equals(OrderStatus.receive.getDbData()), "订单无法确认收货");

		List<PurchaseOrderList> orderLists = orderListService.listByOrderId(order.getId());
		List<String> statusList = Lists.newArrayList(IndentListStatus.receive.name(), IndentListStatus.returned1.name(), IndentListStatus.returned2.name(), IndentListStatus.returned3.name());
		for (PurchaseOrderList list : orderLists) {
			ValidateUtils.isTrue(statusList.contains(list.getStatus()), "该订单有商品处于售后中，还不能确认收货。");
		}

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            	purchaseOrderService.updateOrderToReceipt(order, confirmType);
            }
        });

    }

	@Override
	public void freeShipping(String orderId) {
		purchaseOrderHelper.freeShipping(orderId);
	}

	@Override
	protected OrderBizType getOrderBizType() {
		
		return OrderBizType.purchase;
	}

	@Override
	protected OrderType getOrderType() {
		
		return OrderType.presell;
	}

	@Override
	protected void checkOrder(BaseOrder t) {
		
		PurchaseOrderVo orderVo = (PurchaseOrderVo) t;
		
		ValidateUtils.notNull(orderVo, "订单生成失败！");
        ValidateUtils.notNull(orderVo.getProvince(), "请填写收货地址！");
        ValidateUtils.notNull(orderVo.getCity(), "请填写收货地址！");
        ValidateUtils.notNull(orderVo.getAddress(), "请填写收货地址！");
        ValidateUtils.notNull(orderVo.getReceiver(), "请填写收货人！");
        ValidateUtils.notNull(orderVo.getReceiverPhone(), "请填写收货人联系方式！");
        ValidateUtils.notNull(orderVo.getOrderListVos(), "请选择商品！");
        ValidateUtils.notNull(orderVo.getBuyerId(), "添加订单失败！");
        
        // 预售订单只能有一种商品
        
        // 检查预售活动是否有效
        for (BaseOrderItem item : t.getItemList()){
        	
        	PresellActivity activity = presellBizService.getValidActivityBySkuid(item.getGoodSkuId());
        	ValidateUtils.notNull(activity, "预售活动已经结束");
        }
        
	}

	@Override
	protected String getOrderCode() {
		return "ycl-c" + DateParseUtil.formatDate(new Date(), Constants.INDENT_NAME_RULE);
	}

	@Override
	protected BigDecimal calcFreight(BaseOrder order) {
		
		PurchaseOrderVo orderVo = (PurchaseOrderVo) order;
		// TODO SERVICE_ID?
		return freightTplBizService.calcPurchaseOrderFreight(orderVo, orderVo.getTradeContext(), "");
	}

	@Override
	protected void initItemProperty(BaseOrder order) {
		
		purchaseOrderHelper.initItemProperty(order);
	}


	@Override
	protected BaseOrder getOrderById(String orderId) {
		return purchaseOrderService.getById(orderId);
	}

	@Override
	public void deleteOrder(OrderDeleteInfo info) {
		PurchaseOrder order = purchaseOrderService.getById(info.getOrderId());
		ValidateUtils.notNull(order, "订单不存在");
		ValidateUtils.isTrue(order.getBuyerId().equals(info.getUserId()) || order.getSubbranchId().equals(info.getUserId()), "只能删除自己的订单");
		ValidateUtils.isTrue(OrderStatus.received.getDbData() == order.getStatus() || OrderStatus.evaluate.getDbData() == order.getStatus()
						|| OrderStatus.done.getDbData() == order.getStatus() || OrderStatus.refunded.getDbData() == order.getStatus()
						|| OrderStatus.returned.getDbData() == order.getStatus() || (IndentDealStatus.deal_close.getDbData() + "").equals(order.getDealStatus())
						|| (IndentDealStatus.deal_fail.getDbData() + "").equals(order.getDealStatus()) || (IndentDealStatus.deal_success.getDbData() + "").equals(order.getDealStatus()),
				"订单无法删除");

		// 删除订单
		purchaseOrderService.deleteOrder(order, info.getDeleteType());
	}

	@Override
	public void shipNotice(ShipNoticeInfo info) {
		PurchaseOrder order = purchaseOrderService.getById(info.getOrderId());
		ValidateUtils.notNull(order, "订单不存在");
		ValidateUtils.isTrue(order.getBuyerId().equals(info.getUserId()), "只能操作自己的订单");
		ValidateUtils.isTrue(OrderStatus.ship.getDbData() == order.getStatus(), "订单无法提醒发货");
		ValidateUtils.isTrue(!"1".equals(order.getShipNotice()), "订单已提交提醒发货");

		// 提醒发货
		purchaseOrderService.shipNotick(order);

		messageBizService.addNoticeMessage(order.getBuyerId(), order.getBuyerId(), MessageStatus.noticeShip1.getDbData(),
				"[订单]" + order.getOrderNo() + " 客户提醒发货，请及时发货！");
	}

	private Date getReceiveTimeout(Date shippingTime) {
		SysDict dict = sysDictService.get(Conds.get().eq("dict_type", "receive_timeout"));
		Integer day = dict == null ? 10 : new BigDecimal(dict.getValue()).intValue();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(shippingTime);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}
}
