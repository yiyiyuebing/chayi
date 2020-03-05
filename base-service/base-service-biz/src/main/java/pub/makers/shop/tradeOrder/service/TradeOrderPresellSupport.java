package pub.makers.shop.tradeOrder.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dev.base.utils.DateUtil;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import org.apache.commons.lang3.StringUtils;
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
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.baseOrder.entity.OrderListPayment;
import pub.makers.shop.baseOrder.entity.OrderPresellExtra;
import pub.makers.shop.baseOrder.enums.*;
import pub.makers.shop.baseOrder.pojo.*;
import pub.makers.shop.baseOrder.service.BaseOrderSupportImpl;
import pub.makers.shop.baseOrder.service.OrderPaymentBizService;
import pub.makers.shop.baseOrder.service.OrderPromotionBizService;
import pub.makers.shop.bill.service.OrderBillMqTask;
import pub.makers.shop.cargo.service.CargoOutboundBizService;
import pub.makers.shop.logistics.service.FreightTplBizService;
import pub.makers.shop.message.enums.MessageStatus;
import pub.makers.shop.message.service.MessageBizService;
import pub.makers.shop.promotion.enums.PresellType;
import pub.makers.shop.promotion.service.PresellBizService;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.service.SubbranchAccountBizService;
import pub.makers.shop.store.service.SubbranchBizService;
import pub.makers.shop.thirdpart.service.U8SyncService;
import pub.makers.shop.tradeOrder.entity.Indent;
import pub.makers.shop.tradeOrder.entity.IndentList;
import pub.makers.shop.tradeOrder.enums.IndentDealStatus;
import pub.makers.shop.tradeOrder.enums.IndentListStatus;
import pub.makers.shop.tradeOrder.enums.IndentShopType;
import pub.makers.shop.tradeOrder.vo.IndentVo;
import pub.makers.shop.tradeOrder.vo.OrderPayInfo;
import pub.makers.shop.tradeOrder.vo.ShippingInfo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class TradeOrderPresellSupport extends BaseOrderSupportImpl {

    @Autowired
    private IndentService indentService;
    @Reference(version = "1.0.0")
    private SubbranchBizService subbranchBizService;
    @Reference(version = "1.0.0")
    private SubbranchAccountBizService subbranchAccountBizService;
    @Autowired
    private TransactionTemplate transcationTemplate;
    @Autowired
    private FreightTplBizService freightTplBizService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private CargoOutboundBizService cargoOutboundBizService;
    //    @Autowired
//    private AJpushUtils aJpushUtils;
    @Reference(version = "1.0.0")
    private MessageBizService messageBizService;
    @Autowired
    private PresellBizService presellBizService;
    @Autowired
    private OrderPaymentBizService paymentBizService;
    @Autowired
    private U8SyncService u8Service;
    @Autowired
    private IndentListService indentListService;
    @Autowired
    private TradeOrderHelper orderHelper;
    @Autowired
	private TransactionTemplate transationTemplate;
    @Reference(version = "1.0.0")
    private OrderBillMqTask orderBillMqTask;
    @Autowired
    private OrderPromotionBizService promotionBizService;
    
    protected void fixOrderAmount(BaseOrder order){
        presellBizService.processBaseorderAndCalcpayment(order, getOrderBizType(), false);
    }

    @Override
	protected void doCreateOrder(final BaseOrder bo) {
    	
    	final IndentVo indentVo = (IndentVo)bo;
    	indentVo.setBuyerCarriage(bo.getFreight().setScale(2).toString());
        indentVo.setIndentShopType(subbranchAccountBizService.isSubAccount(indentVo.getSubbranchId()) ? IndentShopType.sub.name() : IndentShopType.main.name());
    	
        transationTemplate.execute(new TransactionCallbackWithoutResult() {

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
		        presellBizService.lockStocks(bo.getItemList(), getOrderBizType());
		    	
		    	indentVo.setCreateTime(new Date());
		    	if (StringUtils.isBlank(indentVo.getSubbranchId())){
		    		indentVo.setSubbranchId(indentVo.getBuyerId());
		    	}
		        
				// 保存订单信息
		    	indentService.saveOrder(indentVo);
				// 新增出库单
				cargoOutboundBizService.addOutbound(indentVo.getId(), getOrderBizType(), Long.valueOf(indentVo.getBuyerId()), "销售出库");
			}
		});
        
	}

	@Override
	protected void doCancelOrder(BaseOrder bo, OrderCancelType cancelType) {
		
		Indent order = (Indent) bo;
		// 更新订单状态
		indentService.updateOrderToCancel(order, cancelType);
		List<IndentList> indentList = indentListService.listByOrderId(bo.getOrderId());
		// 更新库存
        presellBizService.releaseLockStocks(indentList, getOrderBizType());
		// 取消出货单
		cargoOutboundBizService.cancelOutbound(order.getId().toString(), "销售出库");
	}
    
    @Override
    public void doPayOrder(final OrderPayInfo payInfo, BaseOrder bo) {

    	// TODO 根据预售类型和支付的阶段来判断订单支付完成需要处于什么状态
    	OrderPresellExtra extra = presellBizService.getExtra(getOrderBizType(), bo.getId().toString());
    	OrderListPayment payment = paymentBizService.getById(payInfo.getPaymentId());
    	List<IndentList> indentList = indentListService.listByOrderId(bo.getOrderId());
    	final Indent order = (Indent) bo;
        // 前置条件检查
    	// 只有一期,状态直接为已支付
    	if (1 == payment.getStageNum() && PresellType.one.name().equals(extra.getPresellType())){
    		indentService.updateOrderToPayed(order, payInfo);
			// 同步U8库存
			u8Service.account(order.getId() + "", getOrderBizType(), getOrderType(), 2);
			// 确认出货单
			cargoOutboundBizService.confirmOutbound(order.getId().toString(), "销售出库");
			messageBizService.addNoticeMessage(order.getBuyerId().toString(), order.getSubbranchId().toString(), MessageStatus.ship.getDbData(),
	                "[订单]" + order.getName() + " 客户已下单，请及时发货！");
    	}
    	// 二期
    	else if (PresellType.second.name().equals(extra.getPresellType())){
    		//  第一期
    		if (1 == payment.getStageNum()){
    			indentService.updateOrderToPayed(order, payInfo, OrderStatus.payend.getDbData());
    		}
    		//  第二期
    		else if (2 == payment.getStageNum()){
    			indentService.updateOrderToPayed(order, payInfo);
    			// 同步U8库存
    			u8Service.account(order.getId() + "", getOrderBizType(), getOrderType(), 2);
				// 确认出货单
				cargoOutboundBizService.confirmOutbound(order.getId().toString(), "销售出库");
    			messageBizService.addNoticeMessage(order.getBuyerId().toString(), order.getSubbranchId().toString(), MessageStatus.ship.getDbData(),
    	                "[订单]" + order.getName() + " 客户已下单，请及时发货！");
    		}
    	} 
    	else {
    		// 无法预期的错误
    	}

    }

    @Override
    public void shipmentOrder(final ShippingInfo si) {

        final Indent order = indentService.getById(si.getOrderId());
        ValidateUtils.notNull(order, "订单不存在");
        ValidateUtils.isTrue(order.getStatus().equals(OrderStatus.ship.getDbData()), "订单无法发货");

        order.setReceiveTimeout(getReceiveTimeout(new Date()));
        transcationTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                // 更新订单状态
            	indentService.updateOrderToShipment(order, si);
            	
            }
        });
        orderHelper.sendShipSms(order, si);

    }

    @Override
    public void confirmReceipt(String userId, String orderId, final OrderConfirmType confirmType) {

        final Indent indent = indentService.getById(orderId);
        ValidateUtils.notNull(indent, "订单不存在");
        ValidateUtils.isTrue(userId.equals(indent.getBuyerId().toString()), "只能确认自己的订单");
        ValidateUtils.isTrue(indent.getStatus().equals(OrderStatus.receive.getDbData()), "订单无法发货");

        List<IndentList> orderLists = indentListService.listByOrderId(indent.getId().toString());
        List<String> statusList = Lists.newArrayList(IndentListStatus.receive.name(), IndentListStatus.returned1.name(), IndentListStatus.returned2.name(), IndentListStatus.returned3.name());
        for (IndentList list : orderLists) {
            ValidateUtils.isTrue(statusList.contains(list.getStatus()), "该订单有商品处于售后中，还不能确认收货。");
        }

        transcationTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                // 更新订单
            	indentService.updateOrderToReceipt(indent, confirmType);
            	orderHelper.recordIndentBill(indent.getOrderId());
                orderBillMqTask.addOrderBillRecord(indent.getOrderId());
            }
        });

        // TODO 记录运营商账务
    }

    @Override
    public void freeShipping(String orderId) {
    	orderHelper.freeShipping(orderId);
    }

    @Override
    public void deleteOrder(OrderDeleteInfo info) {
        Indent order = indentService.getById(info.getOrderId());
        ValidateUtils.notNull(order, "订单不存在");
        ValidateUtils.isTrue(order.getBuyerId().equals(info.getUserId()) || order.getSubbranchId().equals(Long.valueOf(info.getUserId())), "只能删除自己的订单");
        ValidateUtils.isTrue(OrderStatus.received.getDbData() == order.getStatus() || OrderStatus.evaluate.getDbData() == order.getStatus()
                        || OrderStatus.done.getDbData() == order.getStatus() || OrderStatus.refunded.getDbData() == order.getStatus()
                        || OrderStatus.returned.getDbData() == order.getStatus() || IndentDealStatus.deal_close.getDbData() == order.getDealStatus()
                        || IndentDealStatus.deal_fail.getDbData() == order.getDealStatus() || IndentDealStatus.deal_success.getDbData() == order.getDealStatus(),
                "订单无法删除");

        // 删除订单
        indentService.deleteOrder(order, info.getDeleteType());
    }

    @Override
    public void shipNotice(ShipNoticeInfo info) {
        Indent order = indentService.getById(info.getOrderId());
        ValidateUtils.notNull(order, "订单不存在");
        ValidateUtils.isTrue(order.getBuyerId().equals(info.getUserId()), "只能操作自己的订单");
        ValidateUtils.isTrue(OrderStatus.ship.getDbData() == order.getStatus(), "订单无法提醒发货");
        ValidateUtils.isTrue(!new Integer(1).equals(order.getShipNotice()), "订单已提交提醒发货");

        // 提醒发货
        indentService.shipNotice(order);

        messageBizService.addNoticeMessage(order.getBuyerId(), order.getSubbranchId().toString(), MessageStatus.noticeShip1.getDbData(),
                "[订单]" + order.getName() + " 客户提醒发货，请及时发货！");
    }


    private Date getTimeout(Date createTime) {
        SysDict dict = sysDictService.get(Conds.get().eq("dict_type", "indent_timeout").eq("code", "trade"));
        Integer minute = dict == null ? 30 : new BigDecimal(dict.getValue()).multiply(new BigDecimal(60)).intValue();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createTime);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }


	@Override
	protected OrderBizType getOrderBizType() {
		return OrderBizType.trade;
	}

	@Override
	protected OrderType getOrderType() {
		
		return OrderType.presell;
	}

	@Override
	protected void checkOrder(BaseOrder t) {
		IndentVo indentVo = (IndentVo)t;
		
		ValidateUtils.notNull(indentVo, "订单生成失败！");
        ValidateUtils.notNull(indentVo.getProvince(), "请填写收货地址！");
        ValidateUtils.notNull(indentVo.getCity(), "请填写收货地址！");
        ValidateUtils.notNull(indentVo.getAddress(), "请填写收货地址！");
        ValidateUtils.notNull(indentVo.getReceiver(), "请填写收货人！");
        ValidateUtils.notNull(indentVo.getReceiverPhone(), "请填写收货人联系方式！");
        ValidateUtils.notNull(indentVo.getIndentList(), "请选择商品！");
        ValidateUtils.notNull(indentVo.getBuyerId(), "添加订单失败！");
        ValidateUtils.notNull(indentVo.getSubbranchId(), "很抱歉，您在的店铺出现异常，请重新进入！");

        Subbranch shop = subbranchBizService.getById(indentVo.getSubbranchId());
        ValidateUtils.notNull(shop, "很抱歉，您在的店铺出现异常，请重新进入！");
	}

	@Override
	protected String getOrderCode() {
		
		return "ycl-s" + DateUtil.format(Constants.INDENT_NAME_RULE, new Date());
	}

	@Override
	protected BigDecimal calcFreight(BaseOrder order) {
		IndentVo indentVo = (IndentVo)order;
		BigDecimal buyerCarriage = freightTplBizService.calcTradeOrderFreight(indentVo, indentVo.getTradeContext(), indentVo.getExpressId());
		return buyerCarriage;
	}

	@Override
	protected void initItemProperty(BaseOrder order) {
		
		orderHelper.initItemProperty(order);
		
	}

	@Override
	protected BaseOrder getOrderById(String orderId) {
		
		return indentService.getById(orderId);
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
