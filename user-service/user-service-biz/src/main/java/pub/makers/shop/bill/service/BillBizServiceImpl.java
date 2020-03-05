package pub.makers.shop.bill.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import com.lantu.base.common.entity.BoolType;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.account.entity.AccStoreBillChangeRecord;
import pub.makers.shop.account.enums.BillResourceType;
import pub.makers.shop.account.pojo.WithdrawApplyParam;
import pub.makers.shop.account.service.TotalStoreBillBizService;
import pub.makers.shop.base.util.IdGenerator;
import pub.makers.shop.base.utils.DateParseUtil;
import pub.makers.shop.baseOrder.enums.OrderListGoodType;
import pub.makers.shop.baseOrder.enums.OrderStatus;
import pub.makers.shop.bill.entity.IndentBill;
import pub.makers.shop.bill.entity.OrderBillRecord;
import pub.makers.shop.bill.entity.TimeCycle;
import pub.makers.shop.bill.enums.OrderBillStatus;
import pub.makers.shop.bill.enums.TimeCycleType;
import pub.makers.shop.bill.vo.BillVo;
import pub.makers.shop.store.entity.StoreLevel;
import pub.makers.shop.store.service.StoreLevelBizService;
import pub.makers.shop.store.service.StoreLevelMgrBizService;
import pub.makers.shop.tradeOrder.service.IndentMgrBizService;
import pub.makers.shop.tradeOrder.service.TradeOrderQueryService;
import pub.makers.shop.tradeOrder.vo.IndentListVo;
import pub.makers.shop.tradeOrder.vo.IndentVo;

import java.math.BigDecimal;
import java.util.*;


/**
 * 常规的分润算法实现
 * 供应商拿走成本价
 * 各级参与者按照比例或者固定金额依次拿走部分利润
 * 卖家拿走剩余的部分
 * @author apple
 *
 */
@Service(version = "1.0.0")
public class BillBizServiceImpl implements BillBizService{

	private Logger logger = Logger.getLogger(OrderBillRecordBizServiceImpl.class);

	@Autowired
	private IndentBillService billService;

	@Reference(version = "1.0.0")
	private TradeOrderQueryService tradeOrderQueryService;

	@Reference(version = "1.0.0")
	private StoreLevelBizService storeLevelBizService;

	@Autowired
	private TimeCycleService timeCycleService;

	@Reference(version = "1.0.0")
	private IndentMgrBizService indentMgrBizService;
	@Reference(version = "1.0.0")
	private StoreLevelMgrBizService storeLevelMgrBizService;
	@Autowired
	private OrderBillRecordService orderBillRecordService;
	@Autowired
	private IndentBillService indentBillService;

	@Autowired
	private TotalStoreBillBizService totalStoreBillBizService;

	public BillBizServiceImpl() {
	}


	/**
	 * 按照原有逻辑简单实现的记账
	 */
	@Override
	public void recordBill(BillVo billVo) {
		
		IndentBill bill = new IndentBill();
		bill.setId(IdGenerator.getDefault().nextId());
		bill.setIndentId(Long.valueOf(billVo.getOrderId()));
		bill.setStatus(1);
		bill.setCreateTime(new Date());
		
		billService.insert(bill);
	}

	@Override
	public IndentBill getBill(String indentId) {
		return billService.get(Conds.get().eq("indent_id", indentId));
	}

	@Override
	public Map<String, IndentBill> getBill(List<String> indentIdList) {
		List<IndentBill> indentBillList = billService.list(Conds.get().in("indent_id", indentIdList));
		Map<String, IndentBill> indentBillMap = Maps.newHashMap();
		for (IndentBill indentBill : indentBillList) {
			indentBillMap.put(indentBill.getIndentId().toString(), indentBill);
		}
		return indentBillMap;
	}

	/**
	 * 新逻辑记录账单
	 * @param orderId
	 */
	@Override
	public void addOrderBillRecord(String orderId) {
		logger.info("订单号："  + orderId + "，生成结算单开始");

		if (orderId == null) {
			logger.error("订单号：" + orderId + "，生成结算单失败，当前订单不存在");
			return;
		}

		IndentVo indentVo = tradeOrderQueryService.getOrderDetail(orderId); //订单信息
		if (indentVo == null) {
			logger.error("订单号：" + orderId + "，生成结算单失败，当前订单不存在");
			return;
		}
		if (indentVo.getStatus() == null || OrderStatus.evaluate.getDbData() != indentVo.getStatus().intValue()) {
			logger.error("订单号：" + orderId + "，生成结算单失败，当前订单状态为" + OrderStatus.getTextByDbData(indentVo.getStatus()));
			return;
		}
		List<IndentListVo> indentListVos = indentVo.getIndentList();    //订单明细
		if (indentListVos == null || indentListVos.isEmpty()) {
			logger.error("订单号：" + orderId + "，生成结算单失败，获取当前订单明细失败或是当前订单明细不存在");
			return;
		}

		TimeCycle timeCycle = timeCycleService.get(Conds.get().eq("type", TimeCycleType.settleTime.getDbData()));
		if (timeCycle == null || timeCycle.getDuration() == null) {
			timeCycle = new TimeCycle();
			timeCycle.setDuration(0);
		}

		OrderBillRecord orderBillRecord = new OrderBillRecord();
		orderBillRecord.setId(IdGenerator.getDefault().nextId());
		orderBillRecord.setTimeCycle(timeCycle.getDuration().toString());
		orderBillRecord.setOrderId(Long.parseLong(indentVo.getId()));
		orderBillRecord.setOrderType(indentVo.getOrderType());
		orderBillRecord.setOrderNo(indentVo.getName());
		orderBillRecord.setIsSubAccount(StringUtils.isBlank(indentVo.getIsSubAccount()) ? BoolType.F.name() : BoolType.T.name());
		orderBillRecord.setPayAmount(indentVo.getPaymentAmount());
		orderBillRecord.setStatus(OrderBillStatus.wait.getDbData()); //待结算
		orderBillRecord.setBuyerId(Long.parseLong(indentVo.getBuyerId()));
		orderBillRecord.setSubbranchId(Long.parseLong(indentVo.getSubbranchId()));
		orderBillRecord.setSettlementDate(DateParseUtil.getLastDate(indentVo.getFinishTime(), timeCycle.getDuration()));

		BigDecimal buyerCarriage = indentVo.getBuyerCarriage() == null ?
				BigDecimal.ZERO : new BigDecimal(indentVo.getBuyerCarriage()); //运费
		BigDecimal refundAmount = StringUtils.isBlank(indentVo.getRefundAmount()) ? BigDecimal.ZERO : new BigDecimal(indentVo.getRefundAmount()); //退款金额

		BigDecimal orderProfit = indentVo.getPaymentAmount().subtract(buyerCarriage); //订单利润  实际支付金额-运费
		BigDecimal supplyPrice = indentVo.getPaymentAmount().subtract(buyerCarriage); //订单实际支付金额-运费

		BigDecimal orderProfitMargin = BigDecimal.ZERO; //订单利润率	订单利润/(实收款-运费）=利润率
		BigDecimal orderRefundProfit = BigDecimal.ZERO; //退款利润 	退款金额*利润率 =退款利润
		BigDecimal settleAmountProfit = BigDecimal.ZERO; //结算利润 	（订单利润-退款利润）*0.93 = 结算利润

		String orderProfitStr = "(" + indentVo.getPaymentAmount();
		String supplyPriceStr = "(" + indentVo.getPaymentAmount();

		StringBuffer orderProfitRemark = new StringBuffer();

//        (支付总金额-供货价-运费)*0.93
		for (IndentListVo indentListVo : indentListVos) {
			if (!BoolType.T.name().equals(indentListVo.getGiftFlag()) && !OrderListGoodType.zengpin.name().equals(indentListVo.getGoodType())) {
				BigDecimal number =new BigDecimal(Integer.toString(indentListVo.getNumber()));
				BigDecimal matchPrice = new BigDecimal(indentListVo.getSupplyPrice()).multiply(number); //单价*件数
				orderProfit = orderProfit.subtract(matchPrice); //减供货价
				orderProfitStr += " - (" + matchPrice + " * " + number + ")";  //单价*件数
			}
		}


		Set<String> subbranchIdSet = new HashSet<String>();
		subbranchIdSet.add(indentVo.getSubbranchId());
		Map<String, StoreLevel> storeLevelMap = storeLevelBizService.getStoreLevelMap(subbranchIdSet);//店铺等级

		StoreLevel storeLevel = storeLevelMap.get(indentVo.getSubbranchId());
		if (storeLevel == null || !(storeLevel.getName().indexOf("员工") >= 0) && !(storeLevel.getName().indexOf("直营") >= 0)) {
//            （实收款-运费-供货价总额）=订单利润
//            订单利润/(实收款-运费）=利润率
//            退款金额*利润率 =退款利润
//            （订单利润-退款利润）*0.93 = 结算利润

			if (BigDecimal.ZERO.equals(supplyPrice)) {
				orderProfitMargin = orderProfit.divide(supplyPrice, 2, BigDecimal.ROUND_HALF_UP); //利润率
			}

			orderRefundProfit = refundAmount.multiply(orderProfitMargin); //退款金额*利润率 =退款利润
			settleAmountProfit = orderProfit.subtract(orderRefundProfit).multiply(new BigDecimal("0.93"));	//（订单利润-退款利润）*0.93 = 结算利润
			settleAmountProfit = settleAmountProfit.setScale(2,   BigDecimal.ROUND_HALF_UP); //结算金额

			orderProfitStr += " - " + buyerCarriage + ")"; //减运费
			supplyPriceStr += " - " + buyerCarriage + ")"; //减运费
			orderProfitRemark.append("(" + orderProfitStr + " - " + "((" + orderProfitStr + "/" + supplyPriceStr + ") * " + refundAmount + ")) * 0.93");
			logger.info("订单号："  + orderId + "，生成结算单公式" + orderProfitRemark.toString());
		} else {
			orderProfit = BigDecimal.ZERO;
			orderProfitRemark = new StringBuffer();
		}
		orderBillRecord.setSettlementAmount(settleAmountProfit);
		orderBillRecord.setDateCreated(new Date());
		orderBillRecord.setRemark(orderProfitRemark.toString());
		orderBillRecord.setLastUpdated(new Date());
		logger.info("订单号："  + orderId + "，生成结算单信息：" + orderBillRecord.toString());
		orderBillRecordService.insert(orderBillRecord); //插入记录
		logger.info("订单号："  + orderId + "，生成结算单金额：" + orderProfit.setScale(2, BigDecimal.ROUND_HALF_UP));
		logger.info("订单号："  + orderId + "，生成结算单成功，结算单号：" + orderBillRecord.getId());

		// 更新用户总账
		AccStoreBillChangeRecord cr = getToSettlementBillChangeRecord(orderBillRecord);
		totalStoreBillBizService.updateStoreBill(cr);
	}

	@Override
	public void doSettlementMoneyBill(String recordIds) {
		//TODO 结算后，操作账户余额

	}


	@Override
	public void applyWithDraw(WithdrawApplyParam apply) {

	}

	@Override
	public void agreeWithDrawApply(WithdrawApplyParam apply) {

	}

	@Override
	public void refuseWithDrawApply(WithdrawApplyParam apply) {

	}

	@Override
	public void transferWithDraw(WithdrawApplyParam apply) {

	}

	@Override
	public void refuseWithDraw(WithdrawApplyParam apply) {

	}

	/**
	 * 待结算账单记录
	 * @param orderBillRecord
	 * @return
	 */
	private AccStoreBillChangeRecord getToSettlementBillChangeRecord(OrderBillRecord orderBillRecord) {
		AccStoreBillChangeRecord cr = createStoreBillChangeRecord();
		cr.setStoreId(orderBillRecord.getSubbranchId() + "");
		cr.setRelBillId(orderBillRecord.getId() + "");
		cr.setOperationType(BillResourceType.bill.name());
		cr.setToSettlementMoney(orderBillRecord.getSettlementAmount());
		return cr;
	}


	private static AccStoreBillChangeRecord createStoreBillChangeRecord(){

		AccStoreBillChangeRecord cr = new AccStoreBillChangeRecord();
		cr.setId(IdGenerator.getDefault().nextId() + "");
		cr.setSettlementedMoney(BigDecimal.ZERO); 	//已结算金额
		cr.setToSettlementMoney(BigDecimal.ZERO);	//待结算金额
		cr.setWithdrawLockMoney(BigDecimal.ZERO);	//提现锁定金额
		cr.setBalance(BigDecimal.ZERO);
		cr.setTotalIncomeMoney(BigDecimal.ZERO);
		cr.setTransferableMoney(BigDecimal.ZERO);
		return cr;
	}
//	private ActorService actorService;
//	private BillHandler billHandler;
//	
//	@Override
//	public void recordBill(BillVo billVo) {
//		
//		// 查找此交易的分润的参与者
//		ActorGroup ag = actorService.findActor(billVo);
//		// 交易的成本支付给供应商
//		
//		
//		// 各级参与者依次拿走利润的各个部分	
//		for (Actor actor : ag.getActors()){
//			
//		}
//	}


}
