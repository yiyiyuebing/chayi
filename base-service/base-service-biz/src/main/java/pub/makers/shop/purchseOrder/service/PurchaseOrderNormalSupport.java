package pub.makers.shop.purchseOrder.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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
import pub.makers.shop.baseOrder.enums.*;
import pub.makers.shop.baseOrder.pojo.*;
import pub.makers.shop.baseOrder.service.BaseOrderSupportImpl;
import pub.makers.shop.baseOrder.service.OrderPromotionBizService;
import pub.makers.shop.cargo.entity.CargoSkuSupplyPrice;
import pub.makers.shop.cargo.service.CargoOutboundBizService;
import pub.makers.shop.logistics.service.FreightTplBizService;
import pub.makers.shop.message.enums.MessageStatus;
import pub.makers.shop.message.service.MessageBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSampleBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseOrderListService;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsSampleVo;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrder;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrderList;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderService;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.stock.service.StockBizService;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.service.SubbranchAccountBizService;
import pub.makers.shop.store.service.SubbranchBizService;
import pub.makers.shop.thirdpart.service.U8SyncService;
import pub.makers.shop.tradeOrder.enums.IndentDealStatus;
import pub.makers.shop.tradeOrder.enums.IndentListStatus;
import pub.makers.shop.tradeOrder.vo.OrderPayInfo;
import pub.makers.shop.tradeOrder.vo.ShippingInfo;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dy on 2017/4/10.
 */
@Service
public class PurchaseOrderNormalSupport extends BaseOrderSupportImpl {

    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private StockBizService stockBizService;
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
    PurchaseOrderHelper purchaseOrderHelper;
    @Autowired
    private U8SyncService u8Service;
    @Autowired
    private PurchaseGoodsSkuBizService skuService;
    @Autowired
    private PurchaseGoodsSampleBizService sampleBizService;
    @Autowired
    private PurchaseOrderListService orderListService;
    @Autowired
	private TransactionTemplate transationTemplate;
	@Autowired
	private SysDictService sysDictService;
	@Autowired
	private OrderPromotionBizService promotionBizService;
    
    protected void fixOrderAmount(BaseOrder bo){
    	
    	PurchaseOrderVo order = (PurchaseOrderVo) bo;
    	
    	List<PurchaseOrderListVo> newItemList = Lists.newArrayList();
    	
    	Set<String> goodSkuSet = Sets.newHashSet();
		for (BaseOrderItem baseOrderItem : order.getOrderListVos()) {
			if (BoolType.T.name().equals(baseOrderItem.getGiftFlag()) || OrderListGoodType.zengpin.name().equals(baseOrderItem.getGoodType())) {
				continue;
			}
			goodSkuSet.add(baseOrderItem.getGoodSkuId());
		}
		// 查询商品中散茶的价格信息
    	Map<String, List<CargoSkuSupplyPrice>> priceMap = skuService.querySanchaPrice(goodSkuSet);
    	// 查询散茶样品金额
    	Map<String, PurchaseGoodsSampleVo> sampleMap = sampleBizService.findBySkus(goodSkuSet);
    	
    	for (String goodSkuId : priceMap.keySet()){
    		List<PurchaseOrderListVo> samplePvos = order.getOrderListVos().stream().filter(p -> p.getPurGoodsSkuId().equals(goodSkuId) && BoolType.T.name().equals(p.getIsSample())).collect(Collectors.toList());
    		List<PurchaseOrderListVo> normalPvos = order.getOrderListVos().stream().filter(p -> p.getPurGoodsSkuId().equals(goodSkuId) && !BoolType.T.name().equals(p.getIsSample())).collect(Collectors.toList());
    		
    		if (samplePvos.size() > 0){
				PurchaseOrderListVo pvo = null;
				try {
					pvo = (PurchaseOrderListVo) samplePvos.get(0).clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
    			int totalBuyNum = samplePvos.stream().mapToInt(s -> s.getNumber()).reduce((sum, s) -> sum + s).getAsInt();
    			pvo.setBuyNum(totalBuyNum);
    			Double samplePriceD = new BigDecimal(sampleMap.get(pvo.getGoodSkuId()).getSamplePrice()).doubleValue();
    			BigDecimal samplePrice = new BigDecimal(samplePriceD);
    			// 查询样品金额
    			pvo.setWaitPayAmont(samplePrice.multiply(new BigDecimal(totalBuyNum)));
				pvo.setSupplyPrice(samplePrice.multiply(new BigDecimal(totalBuyNum)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    			newItemList.add((PurchaseOrderListVo)pvo);
    		}
    		
    		if (normalPvos.size() > 0){
				PurchaseOrderListVo pvo = null;
				try {
					pvo = (PurchaseOrderListVo) normalPvos.get(0).clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
    			int totalBuyNum = normalPvos.stream().mapToInt(s -> s.getNumber()).reduce((sum, s) -> sum + s).getAsInt();
    			pvo.setBuyNum(totalBuyNum);
    			List<CargoSkuSupplyPrice> priceList = priceMap.get(pvo.getGoodSkuId());
    			// 查询散茶价格
    			CargoSkuSupplyPrice price = priceList.stream().filter(p -> p.getSectionStart() <= totalBuyNum && (p.getSectionEnd() == null || p.getSectionEnd() >= totalBuyNum)).findFirst().get();
    			ValidateUtils.notNull(price, String.format("散茶[%s]未设置供货价", ((PurchaseOrderListVo)pvo).getPurGoodsName()));
    			BigDecimal scPrice = price.getSupplyPrice();
    			pvo.setWaitPayAmont(scPrice.multiply(new BigDecimal(totalBuyNum)));
				pvo.setSupplyPrice(scPrice.multiply(new BigDecimal(totalBuyNum)).setScale(2).doubleValue());
    			newItemList.add((PurchaseOrderListVo)pvo);
    		}
    	}
    	
    	// 商品清单中存在散茶或者样品，需要将清单中的散茶和样品分开合并
    	if (newItemList.size() > 0){
    		List<PurchaseOrderListVo> normalList = order.getOrderListVos().stream().filter(p -> !priceMap.containsKey(p.getGoodSkuId())).collect(Collectors.toList());
    		newItemList.addAll(normalList);
    		
    		// 更新订单总待支付金额
    		BigDecimal totalWaitPay = BigDecimal.ZERO;
    		BigDecimal totalDiscount = BigDecimal.ZERO;
    		for (PurchaseOrderListVo pvo : newItemList){
    			totalWaitPay = totalWaitPay.add(pvo.getWaitPayAmont());
    			totalDiscount = totalDiscount.add(pvo.getDiscountAmount());
    		}
    		
    		bo.setTotalPrice(totalWaitPay.add(totalDiscount));
    		bo.setDiscountAmount(totalDiscount);
    		bo.setWaitpayAmount(totalWaitPay.add(bo.getFreight()));
    		
    		// 更新商品列表
    		bo.setItemList(newItemList);
    	}
	}
    
    @Override
	protected void doCreateOrder(final BaseOrder bo) {
    	
    	fixOrderAmount(bo);
		// 应用订单总金额变化插件
		PromotionOrderQuery query = new PromotionOrderQuery();
		query.setOrderInfo(bo);
		query.setOrderType(getOrderType());
		query.setOrderBizType(getOrderBizType());
		TradeContext tc = bo.getTradeContext();
		promotionBizService.applyTotalPriceRule(query, tc);

		// 计算订单运费
		BigDecimal freight = calcFreight(bo);
		bo.setFreight(freight);
		bo.setWaitpayAmount(bo.getWaitpayAmount().add(freight));
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
    	final PurchaseOrderVo order = (PurchaseOrderVo) bo;
    	order.setBuyerCarriage(bo.getFreight().setScale(2).toString());
    	
    	transationTemplate.execute(new TransactionCallbackWithoutResult(){

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				
				// 锁定库存
		    	stockBizService.lockStocks(bo.getItemList(), OrderBizType.purchase);
				
				// TODO 设置订单超时时间
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
		
		PurchaseOrder order = (PurchaseOrder) bo;
		// 更新订单状态
		purchaseOrderService.updateOrderToCancel(order, cancelType);
		List<PurchaseOrderList> orderList = orderListService.listByOrderId(order.getId());
		// 更新库存
        stockBizService.releaseLockStocks(orderList, OrderBizType.purchase);
		// 取消出货单
		cargoOutboundBizService.cancelOutbound(order.getId(), "进货出库");
	}


    @Override
    public void doPayOrder(final OrderPayInfo payInfo, BaseOrder bo) {
        // 前置条件检查
        final PurchaseOrder order = (PurchaseOrder)bo;
        purchaseOrderService.updateOrderToPayed(order, payInfo);
        
        // 查询订单商品列表
        List<PurchaseOrderList> orderList = orderListService.listByOrderId(order.getId());
    	stockBizService.useLockStocks(orderList, OrderBizType.purchase);
    	
        // 确认出货单
        cargoOutboundBizService.confirmOutbound(order.getId(), "进货出库");
        // 同步U8库存
        u8Service.account(order.getId(), getOrderBizType(), getOrderType(), 1);

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
                
                stockBizService.useStocks(order.getItemList(), OrderBizType.purchase);
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
			ValidateUtils.isTrue(statusList.contains(list.getStatus()), " 该订单有商品处于售后中，还不能确认收货。");
		}

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                // 更新订单
            	purchaseOrderService.updateOrderToReceipt(order, confirmType);
            }
        });

    }

	@Override
	public void freeShipping(String orderId) {
		
		purchaseOrderHelper.freeShipping(orderId);
		
	}

    @Override
    public void deleteOrder(OrderDeleteInfo info) {
        final PurchaseOrder order = purchaseOrderService.getById(info.getOrderId());
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

    @Override
	protected OrderBizType getOrderBizType() {
		
		return OrderBizType.purchase;
	}

	@Override
	protected OrderType getOrderType() {
		
		return OrderType.normal;
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

        Subbranch shop = subbranchBizService.getById(orderVo.getBuyerId());
        ValidateUtils.notNull(shop, "很抱歉，您在的店铺出现异常，请重新进入！");
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

	private Date getReceiveTimeout(Date shippingTime) {
		SysDict dict = sysDictService.get(Conds.get().eq("dict_type", "receive_timeout"));
		Integer day = dict == null ? 10 : new BigDecimal(dict.getValue()).intValue();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(shippingTime);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}
}
