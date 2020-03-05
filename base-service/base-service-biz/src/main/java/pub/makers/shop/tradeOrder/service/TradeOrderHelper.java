package pub.makers.shop.tradeOrder.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.service.SmsService;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderListGoodType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.baseOrder.service.OrderPaymentBizService;
import pub.makers.shop.bill.service.BillBizService;
import pub.makers.shop.bill.vo.BillVo;
import pub.makers.shop.cargo.entity.CargoSku;
import pub.makers.shop.cargo.service.CargoSkuService;
import pub.makers.shop.promotion.service.PresellBizService;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.service.SubbranchAccountBizService;
import pub.makers.shop.store.service.SubbranchBizService;
import pub.makers.shop.tradeGoods.entity.TradeGood;
import pub.makers.shop.tradeGoods.entity.TradeGoodSku;
import pub.makers.shop.tradeGoods.service.TradeGoodService;
import pub.makers.shop.tradeGoods.service.TradeGoodSkuService;
import pub.makers.shop.tradeOrder.entity.Indent;
import pub.makers.shop.tradeOrder.enums.IndentListStatus;
import pub.makers.shop.tradeOrder.vo.IndentListVo;
import pub.makers.shop.tradeOrder.vo.IndentVo;
import pub.makers.shop.tradeOrder.vo.ShippingInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TradeOrderHelper {

	@Autowired
    private TradeGoodService goodService;
	@Autowired
    private TradeGoodSkuService goodSkuService;
	@Autowired
	private CargoSkuService cargoSkuService;
	@Reference(version = "1.0.0")
	private SubbranchBizService subbranchBizService;
	@Reference(version = "1.0.0")
    private SubbranchAccountBizService subbranchAccountBizService;
	@Reference(version = "1.0.0")
	private BillBizService billService;
	@Reference(version = "1.0.0")
    private SmsService smsService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private IndentService indentService;
	@Autowired
	private OrderPaymentBizService paymentService;
	@Autowired
	private PresellBizService presellService;
	
	public void initItemProperty(BaseOrder order){
		
		IndentVo indentVo = (IndentVo)order;
		List<IndentListVo> indentListVoList = indentVo.getIndentList();
		List<TradeGoodSku> skuList = goodSkuService.list(Conds.get().in("id", ListUtils.getIdSet(indentListVoList, "goodSkuId")));
		List<CargoSku> carSkuList = cargoSkuService.list(Conds.get().in("id", ListUtils.getIdSet(skuList, "cargoSkuId")));
		Map<String, CargoSku> cargoSkuMap = ListUtils.toKeyMap(carSkuList, "id");
		Map<String, TradeGoodSku> skuMap = Maps.newHashMap();
		for (TradeGoodSku goodSku: skuList){
			skuMap.put(goodSku.getId() + "", goodSku);
		}
		
		for (IndentListVo indentListVo : indentListVoList){
			
			TradeGoodSku sku = skuMap.get(indentListVo.getGoodSkuId());
			ValidateUtils.notNull(sku, String.format("商品数据[%s]不存在", indentListVo.getGoodSkuId()));
			CargoSku cu = cargoSkuMap.get(sku.getCargoSkuId() + "");
			
			BigDecimal salePrice = cu.getRetailPrice();
			ValidateUtils.notNull(salePrice, String.format("商品[%s]未设置售价", cu.getName()));
			
			indentListVo.setId(IdGenerator.getDefault().nextId() + "");
	        indentListVo.setIndentId(indentVo.getId());
	        indentListVo.setCargoSkuId(sku.getCargoSkuId() + "");
	        indentListVo.setTradeGoodId(sku.getGoodId() + "");
	        indentListVo.setStatus(IndentListStatus.waitpay.name());
	        indentListVo.setDateCreated(new Date());
	        indentListVo.setIsValid(BoolType.T.name());
	        indentListVo.setDelFlag(BoolType.F.name());
			indentListVo.setShipCancelAfter(BoolType.F.name());
			indentListVo.setReceiveCancelAfter(BoolType.F.name());
			indentListVo.setGiftFlag(indentListVo.getGiftFlag() == null ? BoolType.F.name() : indentListVo.getGiftFlag());
	        indentListVo.setShipReturnTime(0);
	        indentListVo.setReturnTime(0);
	        indentListVo.setIsEvalution(0);
			indentListVo.setGoodType(OrderListGoodType.normal.name());

	        // 赠品不计算金额
	        if (BoolType.T.name().equals(indentListVo.getGiftFlag())) {

	            indentListVo.setTradeGoodAmount("0.00");
				indentListVo.setOriginalAmount("0.00");
	            indentListVo.setDiscountAmount(BigDecimal.ZERO);
	            indentListVo.setFinalAmount("0.00");
	        } else {
	            indentListVo.setTradeGoodAmount(salePrice.toString());
	            indentListVo.setOriginalAmount(salePrice.toString());
	            indentListVo.setDiscountAmount(BigDecimal.ZERO);
	            indentListVo.setFinalAmount(salePrice.multiply(new BigDecimal(indentListVo.getBuyNum())).toString());
	        }
	        Subbranch subbranch = subbranchAccountBizService.getMainSubbranch(indentVo.getSubbranchId() + ""); //子账号处理，如果为子账号，则取出主账号。
	        indentListVo.setSupplyPrice(subbranchBizService.queryTradeSuppliersPrice(subbranch.getId() + "", sku.getId() + "").toString());    //通过主账号取出供货价
//	        indentListVo.setSupplyPrice(salePrice.setScale(2, RoundingMode.HALF_UP).toString());
	        indentListVo.setCargoSkuId(sku.getCargoSkuId() + "");
	        indentListVo.setTradeGoodType(sku.getCargoSkuName());

	        TradeGood good = goodService.getById(sku.getGoodId());
	        if (good != null) {
	            indentListVo.setTradeGoodName(good.getName());
	            indentListVo.setTradeGoodImgUrl(goodService.queryGoodImageByCargoId(good.getCargoId()));
	        }
		}
		
	}
	
	public void recordIndentBill(String indentId) {
		
		BillVo bvo = new BillVo();
		bvo.setOrderId(indentId);
		
		billService.recordBill(bvo);
	}
	
	public void sendShipSms(Indent order, ShippingInfo si){
		
		Subbranch s = subbranchBizService.getById(order.getSubbranchId() + "");
		Map<String, Object> smsData = Maps.newHashMap();
		smsData.put("shopName", s.getName());
		smsData.put("receiver", order.getReceiver());
		smsData.put("orderNo", order.getName());
		smsData.put("expressCompany", si.getExpressCompany());
		smsData.put("expressNumber", si.getExpressNumber());
		
		System.out.println("发送短信");
		smsService.sendMsgByTpl(order.getReceiverPhone(), "sms/trade_order_ship", smsData);
	}
	
	@Transactional
	public void freeShipping(String orderId){
		
		Indent inent = indentService.getById(orderId);
		jdbcTemplate.update("update indent set final_amount = final_amount - carriage, buyer_carriage = 0, carriage = 0 where id = ?", orderId);
		// 查询一下运费金额
		paymentService.fixPaymentAmount(orderId, inent.getFreight(), OrderBizType.trade);
		if (OrderType.presell.name().equals(inent.getOrderType())){
			presellService.freeShippint(OrderBizType.trade, orderId, inent.getFreight());
		}
	}
}
