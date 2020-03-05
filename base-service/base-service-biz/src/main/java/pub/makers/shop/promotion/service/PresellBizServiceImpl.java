package pub.makers.shop.promotion.service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.common.entity.BoolType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.util.SerializeUtils;
import pub.makers.shop.base.util.SqlHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.baseOrder.entity.OrderListPayment;
import pub.makers.shop.baseOrder.entity.OrderListPresellExtra;
import pub.makers.shop.baseOrder.entity.OrderPresellExtra;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderListGoodType;
import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.baseOrder.pojo.BaseOrderItem;
import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.baseOrder.service.OrderListPresellExtraService;
import pub.makers.shop.baseOrder.service.OrderPaymentBizService;
import pub.makers.shop.baseOrder.service.OrderPresellExtraService;
import pub.makers.shop.baseOrder.utils.BaseOrderHelps;
import pub.makers.shop.baseOrder.vo.OrderListPaymentVo;
import pub.makers.shop.baseOrder.vo.OrderPresellExtraVo;
import pub.makers.shop.logistics.service.FreightTplBizService;
import pub.makers.shop.logistics.vo.FreightTplQuery;
import pub.makers.shop.logistics.vo.FreightTplQueryItem;
import pub.makers.shop.promotion.entity.PresellActivity;
import pub.makers.shop.promotion.entity.PresellGood;
import pub.makers.shop.promotion.enums.PresellType;
import pub.makers.shop.promotion.vo.PresellCreateResult;
import pub.makers.shop.promotion.vo.PresellPromotionActivityVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.tradeOrder.vo.IndentVo;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service(version="1.0.0")
public class PresellBizServiceImpl implements PresellBizService{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Resource(name="purchaseOrderPresellExtraServiceImpl")
	private OrderPresellExtraService purchasePresellService;
	@Resource(name="tradeOrderPresellExtraServiceImpl")
	private OrderPresellExtraService tradePresellService;
	@Resource(name="tradeOrderListPresellExtraServiceImpl")
	private OrderListPresellExtraService tradeOrderListPresellExtraService;
	@Resource(name="purchaseOrderListPresellExtraServiceImpl")
	private OrderListPresellExtraService purchaseOrderListPresellExtraService;
	@Autowired
	private PresellActivityService activityService;
	@Autowired
    private OrderPaymentBizService paymentBizService;
	@Autowired
	private PresellGoodService presellGoodService;
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private FreightTplBizService freightService;
	
	
	@Override
	public PresellActivity getValidActivityBySkuid(String skuId) {
		
		return activityService.getValidActivityBySkuid(skuId);
	}

	@Override
	public PresellGood getGoodIfExists(String skuId) {
		return activityService.getValidGoodBySkuid(skuId);
	}

	@Override
	public List<PresellPromotionActivityVo> listForGoodsSku(Set<String> goodSkuIdSet) {
		
		String stmt = SqlHelper.getSql("sql/promotion/presell/listForGoods.sql");
		String inStr = SqlHelper.getInStr(goodSkuIdSet);
		
		List<PresellPromotionActivityVo> resultList = jdbcTemplate.query(String.format(stmt, inStr), new BeanPropertyRowMapper<PresellPromotionActivityVo>(PresellPromotionActivityVo.class));
		// 对于商品列表展示,如果是一阶段预售，价格和sku价格一直,如果是二阶段的预售,价格则为首付款
		for (PresellPromotionActivityVo pvo : resultList){
			if (PresellType.one.equals(pvo.getPresellType())){
				pvo.setDiscountAmount(BigDecimal.ZERO);
			}
			
		}
		
		return resultList;
	}

	@Override
	public void addExtra(OrderBizType orderBizType, OrderPresellExtra extra) {
		
		OrderPresellExtraService presellService;
		if (OrderBizType.purchase.equals(orderBizType)){
			presellService = purchasePresellService;
		}
		else {
			presellService = tradePresellService;
		}
		
		extra.setId(IdGenerator.getDefault().nextStringId());
		extra.setDelFlag(BoolType.F.name());
		
		presellService.insert(extra);
	}

	@Override
	public OrderPresellExtra getExtra(OrderBizType orderBizType, String orderId) {
		
		OrderPresellExtraService presellService;
		if (OrderBizType.purchase.equals(orderBizType)){
			presellService = purchasePresellService;
		}
		else {
			presellService = tradePresellService;
		}
		
		return presellService.get(Conds.get().eq("orderId", orderId));
	}
	
	public PresellCreateResult processBaseorderAndCalcpayment(BaseOrder bo, OrderBizType orderBizType){
		
		return processBaseorderAndCalcpayment(bo, orderBizType, true);
		
	}

	@Override
	public PresellCreateResult processBaseorderAndCalcpayment(BaseOrder bo, OrderBizType orderBizType, boolean createData) {
		
		// 购买数量不能大于最大限购数量
		
		List<? extends BaseOrderItem> itemList = BaseOrderHelps.groupItems(bo.getItemList());
		PresellActivity activity = null;
		BigDecimal totalWaitpay = BigDecimal.ZERO;
		
		List<OrderListPresellExtra> presellItemList = Lists.newArrayList();
		Map<String, BigDecimal> itemPresellMap = Maps.newHashMap();
		// 预售商品的总待支付金额=商品的预售金额
		for (BaseOrderItem item : itemList){
			// 赠品不做预售规则处理
			if (BoolType.T.name().equals(item.getGiftFlag()) || OrderListGoodType.zengpin.name().equals(item.getGoodType())){
				continue;
			}
			
			PresellGood pg = getGoodIfExists(item.getGoodSkuId());
			ValidateUtils.notNull(pg, String.format("%s未参与预售", item.getGoodName()));
			ValidateUtils.notNull(pg.getPresellAmount(), String.format("预售活动未设置预售价格,请联系管理员", item.getGoodSkuId()));
			activity = pg.getActivity();
			if (BoolType.T.name().equals(activity.getLimitFlg())){
				ValidateUtils.isTrue(activity.getLimitNum() >= item.getBuyNum(), "商品购买数量不能超过限购数量");
			}
			
			OrderListPresellExtra presellItem = new OrderListPresellExtra();
			presellItem.setOrderId(bo.getOrderId());
			presellItem.setGoodSkuId(item.getGoodSkuId());
			presellItem.setBuyNumber(item.getBuyNum());
//			presellItem.setOrderListId(item.get);
			
			// 对于一期，首付款为预售价，尾款为0
			if (PresellType.one.name().equals(activity.getPresellType())){
				
				presellItem.setPresellFirst(pg.getPresellAmount().multiply(new BigDecimal(item.getBuyNum())));
				presellItem.setPresellEnd(BigDecimal.ZERO);
				presellItem.setPresellAmount(pg.getPresellAmount().multiply(new BigDecimal(item.getBuyNum())));
			}
			// 对于二期，首付款是首款，尾款是尾款
			else {
				
				ValidateUtils.notNull(pg.getFirstAmount(), String.format("预售活动未设置首付款价格,请联系管理员", item.getGoodSkuId()));
				ValidateUtils.notNull(pg.getRemainingAmount(), String.format("预售活动未设置尾款价格,请联系管理员", item.getGoodSkuId()));
				
				BigDecimal endAmount = pg.getRemainingAmount().multiply(new BigDecimal(item.getBuyNum()));
				BigDecimal firstAmount = pg.getFirstAmount().multiply(new BigDecimal(item.getBuyNum()));
				BigDecimal presellAmount = pg.getPresellAmount().multiply(new BigDecimal(item.getBuyNum()));
				
				presellItem.setPresellFirst(firstAmount);
				presellItem.setPresellEnd(endAmount);
				presellItem.setPresellAmount(presellAmount);
			}
			
			presellItemList.add(presellItem);
			
			itemPresellMap.put(item.getGoodSkuId(), pg.getPresellAmount());
			totalWaitpay = totalWaitpay.add(pg.getPresellAmount().multiply(new BigDecimal(item.getBuyNum())));
			
		}
		// 重新计算预售订单的待支付金额
		bo.setTotalPrice(totalWaitpay);

		// 计算订单运费
		BigDecimal freight = BigDecimal.ZERO;
		TradeContext tradeContext = bo.getTradeContext();
		if (StringUtils.isNotEmpty(tradeContext.getCity())) {
			freight = calcFreight(presellItemList, orderBizType, bo);
		}
//
		bo.setWaitpayAmount(totalWaitpay.add(freight));
		bo.setFreight(freight);
		if (bo instanceof IndentVo){
			((IndentVo) bo).setBuyerCarriage(freight.setScale(2).toString());
		}
		else if (bo instanceof PurchaseOrderVo){
			((PurchaseOrderVo) bo).setBuyerCarriage(freight.setScale(2).toString());
		}
		
		for (BaseOrderItem item : bo.getItemList()){
			BigDecimal presellAmount = itemPresellMap.get(item.getGoodSkuId());
			if (presellAmount == null) {
				continue;
			}
			item.setGoodPrice(presellAmount);
			item.setSumAmount(presellAmount.multiply(new BigDecimal(item.getBuyNum())));
			item.setWaitPayAmont(presellAmount.multiply(new BigDecimal(item.getBuyNum())));
		}
		
		List<BigDecimal> paymentAmounts = Lists.newArrayList();
		
		
		BigDecimal totalFirstAmount = presellItemList.stream().map(i -> i.getPresellFirst()).reduce((sum, item) -> sum.add(item)).get();
		BigDecimal totalEndAmount = presellItemList.stream().map(i -> i.getPresellEnd()).reduce((sum, item) -> sum.add(item)).get();
		BigDecimal totalPresellAmount = presellItemList.stream().map(i -> i.getPresellAmount()).reduce((sum, item) -> sum.add(item)).get();

		// 如果预售订单是二期付款，那么运费在第二期进行计算
		if (totalEndAmount.compareTo(BigDecimal.ZERO) > 0){
			paymentAmounts.add(totalFirstAmount);
			paymentAmounts.add(totalEndAmount.add(bo.getFreight()));
		}
		// 如果是一期付款，那么运费在第一期进行计算
		else {
			paymentAmounts.add(totalFirstAmount.add(bo.getFreight()));
		}

		// 预售扩展信息
		OrderPresellExtra extra = new OrderPresellExtra();
		extra.setPresellFirst(paymentAmounts.get(0));
		extra.setPresellEnd(paymentAmounts.size() > 1 ? paymentAmounts.get(1) : BigDecimal.ZERO);
		extra.setPresellAmount(totalPresellAmount);
		extra.setOrderId(bo.getOrderId());
		extra.setPresellActivityId(activity.getId());
		extra.setPresellType(activity.getPresellType());
		if (createData){
			// 保存预售扩展信息
			addExtra(orderBizType, extra);
			addOrderListExtra(orderBizType, presellItemList);
			
			List<OrderListPayment> paymentList = paymentBizService.createPresellPayment(activity, bo.getOrderId(), paymentAmounts, orderBizType);
			List<OrderListPaymentVo> paymentListVo = paymentList.stream().map(m -> SerializeUtils.toJsonVo(OrderListPaymentVo.class, m)).collect(Collectors.toList());
			if (bo instanceof PurchaseOrderVo){
				((PurchaseOrderVo) bo).setPaymentList(paymentListVo);
			}
			else if (bo instanceof IndentVo){
				((IndentVo) bo).setPaymentList(paymentListVo);
			}
		}
		OrderPresellExtraVo extraVo = new OrderPresellExtraVo();
		BeanUtils.copyProperties(extra, extraVo);
		bo.setPresellExtra(extraVo);

		
		return null;
	}

	@Override
	public void lockStocks(List<? extends BaseOrderItem> itemList, OrderBizType orderBizType) {
		final Map<String, Integer> numberMap = Maps.newHashMap();
		final Map<String, String> nameMap = Maps.newHashMap();
		for (BaseOrderItem item : itemList) {
			Integer number = numberMap.get(item.getGoodSkuId()) == null ? 0 : numberMap.get(item.getGoodSkuId());
			number += item.getBuyNum();
			numberMap.put(item.getGoodSkuId(), number);
			nameMap.put(item.getGoodSkuId(), item.getGoodName());
		}
		final List<PresellPromotionActivityVo> activityVoList = listForGoodsSku(numberMap.keySet());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				for (PresellPromotionActivityVo activityVo : activityVoList) {
					Integer number = numberMap.get(activityVo.getGoodSkuId());
					ValidateUtils.isTrue(activityVo.getPresellNum() > number, nameMap.get(activityVo.getGoodSkuId()) + "预售库存不足");
					int saleNum = activityVo.getSaleNum() == null ? 0 : activityVo.getSaleNum();
					presellGoodService.update(Update.byId(activityVo.getId()).set("presell_num", activityVo.getPresellNum() - number).set("sale_num", saleNum + number));
				}
			}
		});
	}

	@Override
	public void releaseLockStocks(List<? extends BaseOrderItem> itemList, OrderBizType orderBizType) {
		final Map<String, Integer> numberMap = Maps.newHashMap();
		for (BaseOrderItem item : itemList) {
			Integer number = numberMap.get(item.getGoodSkuId()) == null ? 0 : numberMap.get(item.getGoodSkuId());
			number += item.getBuyNum();
			numberMap.put(item.getGoodSkuId(), number);
		}
		final List<PresellPromotionActivityVo> activityVoList = listForGoodsSku(numberMap.keySet());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				for (PresellPromotionActivityVo activityVo : activityVoList) {
					Integer number = numberMap.get(activityVo.getGoodSkuId());
					presellGoodService.update(Update.byId(activityVo.getId()).set("presell_num", activityVo.getPresellNum() + number).set("sale_num", activityVo.getSaleNum() - number));
				}
			}
		});
	}

	private void addOrderListExtra(OrderBizType orderBizType, List<OrderListPresellExtra> extraList){
		
		OrderListPresellExtraService extraService = null;
		if (OrderBizType.purchase.equals(orderBizType)){
			extraService = purchaseOrderListPresellExtraService;
		}
		else {
			extraService = tradeOrderListPresellExtraService;
		}
		
		for (OrderListPresellExtra extra : extraList){
			
			extra.setId(IdGenerator.getDefault().nextStringId());
			extra.setDelFlag(BoolType.F.name());
			
			extraService.insert(extra);
		}
	}
	
	/**
	 * 计算预售运费
	 * @return
	 */
	private BigDecimal calcFreight(List<OrderListPresellExtra> extList, OrderBizType orderBizType, BaseOrder bo){
		
		FreightTplQuery query = new FreightTplQuery();
		query.setOrderBizType(orderBizType);
		query.setTradeContext(bo.getTradeContext());
		String serviceId = "";
		if (bo instanceof IndentVo){
			serviceId = ((IndentVo) bo).getExpressId();
		}
		else if (bo instanceof PurchaseOrderVo){
			serviceId = ((PurchaseOrderVo) bo).getExpressId();
		}
		query.setServiceId(serviceId);
		
		List<FreightTplQueryItem> itemList = Lists.newArrayList();
		
		for (OrderListPresellExtra ext : extList){
			
			FreightTplQueryItem item = new FreightTplQueryItem();
			item.setGoodSkuId(ext.getGoodSkuId());
			item.setBuyNum(ext.getBuyNumber());
			item.setTotalAmount(ext.getPresellAmount());
			
			itemList.add(item);
		}
		query.setItemList(itemList);
		
		return freightService.calcOrderFreight(query);
	}

	@Override
	public void freeShippint(OrderBizType orderBizType, String orderId, BigDecimal freight) {
		
		System.out.println("presell1");
		OrderPresellExtra extra = getExtra(orderBizType, orderId);
		if (extra == null){
			return;
		}
		System.out.println("presell2");
		
		String tableName = "";
		String fieldName = "";
		if (OrderBizType.purchase.equals(orderBizType)){
			tableName = "purchase_order_presell_extra";
		}
		else {
			tableName = "trade_order_presell_extra";
		}
		
		// 预售全款，首付款减掉运费
		if (PresellType.one.name().equals(extra.getPresellType())){
			fieldName = "presell_first";
		}
		// 否则尾款减掉运费
		else {
			fieldName = "presell_end";
		}
		
		String stmt = String.format("update %s set %s = %s - ? where id = ?", tableName, fieldName, fieldName);
		System.out.println(stmt);
		System.out.println(freight.toString());
		System.out.println(extra.getId());
		jdbcTemplate.update(stmt, freight, extra.getId());
	}

}
