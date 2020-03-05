package pub.makers.shop.purchseOrder.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.service.SmsService;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderListGoodType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.baseOrder.service.OrderPaymentBizService;
import pub.makers.shop.cargo.service.CargoImageBizService;
import pub.makers.shop.promotion.service.PresellBizService;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassify;
import pub.makers.shop.purchaseGoods.entity.PurchaseGoodsSku;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuBizService;
import pub.makers.shop.purchaseGoods.service.PurchaseGoodsSkuService;
import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsVo;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrder;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderService;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.service.SubbranchAccountBizService;
import pub.makers.shop.store.service.SubbranchBizService;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.tradeOrder.enums.IndentListStatus;
import pub.makers.shop.tradeOrder.vo.ShippingInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PurchaseOrderHelper {

	@Autowired
    private PurchaseGoodsSkuService purchaseGoodsSkuService;
	@Reference(version = "1.0.0")
    private SubbranchAccountBizService subbranchAccountBizService;
	@Reference(version = "1.0.0")
    private SubbranchBizService subbranchBizService;
	@Autowired
    private PurchaseGoodsService purchaseGoodsService;
	@Autowired
	private CargoImageBizService cargoImageBizService;
	@Autowired
	private PurchaseGoodsSkuBizService skuBizService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Reference(version = "1.0.0")
    private SmsService smsService;
	@Autowired
	private PurchaseOrderService orderService;
	@Autowired
	private OrderPaymentBizService paymentService;
	@Autowired
	private PresellBizService presellService;
	
	public void initItemProperty(BaseOrder order) {
		
		PurchaseOrderVo orderVo = (PurchaseOrderVo) order;
		List<PurchaseOrderListVo> orderListVoList =  orderVo.getOrderListVos();
		Subbranch subbranch = subbranchAccountBizService.getMainSubbranch(orderVo.getSubbranchId() + ""); //子账号处理，如果为子账号，则取出主账号。
		Set<String> skuIds = ListUtils.getIdSet(orderListVoList, "purGoodsSkuId");
		Map<String, BigDecimal> priceMap = skuBizService.getSkuPrice(Lists.newArrayList(skuIds), subbranch.getLevelId() + "");
		Map<String, PurchaseGoodsSku> skuMap = ListUtils.toKeyMap(purchaseGoodsSkuService.list(Conds.get().in("id", skuIds)), "id");
		List<PurchaseClassify> scClassifys = jdbcTemplate.query("select id from purchase_classify where parent_id = (select id from purchase_classify where name = '散茶')", new BeanPropertyRowMapper<PurchaseClassify>(PurchaseClassify.class));
		Set<String> scIds = ListUtils.getIdSet(scClassifys, "id");
		Map<String, PurchaseGoodsVo> goodMap = purchaseGoodsService.listBySkuId(skuIds);
		Set<String> cargoIdSet = ListUtils.getIdSet(Lists.newArrayList(goodMap.values()), "cargoId");
		Map<String, ImageVo> imageMap = cargoImageBizService.getCargoImage(Lists.newArrayList(cargoIdSet), ClientType.valueOf(orderVo.getClientType()));
		
		for (PurchaseOrderListVo orderListVo : orderListVoList){
			
			String skuId = orderListVo.getPurGoodsSkuId();
			PurchaseGoodsSku sku = skuMap.get(skuId);
			
			orderListVo.setId(IdGenerator.getDefault().nextId() + "");
	        orderListVo.setOrderId(orderVo.getId());
	        orderListVo.setCargoSkuId(sku.getCargoSkuId() + "");
	        orderListVo.setStatus(IndentListStatus.waitpay.name());
	        orderListVo.setCreateTime(new Date());
	        orderListVo.setPurGoodsId(sku.getPurGoodsId());
	        orderListVo.setIsValid(BoolType.T.name());
	        orderListVo.setDelFlag(BoolType.F.name());
			orderListVo.setShipCancelAfter(BoolType.F.name());
			orderListVo.setReceiveCancelAfter(BoolType.F.name());
			orderListVo.setGiftFlag(orderListVo.getGiftFlag() == null ? BoolType.F.name() : orderListVo.getGiftFlag());
			orderListVo.setIsSample(orderListVo.getIsSample() == null ? BoolType.F.name() : orderListVo.getIsSample());
	        orderListVo.setShipReturnTime(0);
	        orderListVo.setReturnTime(0);
			orderListVo.setIsEvalution(0);
			orderListVo.setGoodType(OrderListGoodType.normal.name());

			BigDecimal supplyPrice = priceMap.get(skuId);
			
			PurchaseGoodsVo good = goodMap.get(sku.getId());

	        // 赠品不计算金额,样品和散茶的金额延后计算
	        if (BoolType.T.name().equals(orderListVo.getGiftFlag()) || BoolType.T.name().equals(orderListVo.getIsSample()) || isSc(good.getGroupId(), scIds)) {
	        	if (supplyPrice != null){
	        		orderListVo.setSupplyPrice(supplyPrice.doubleValue());    //通过主账号取出供货价
	        	}
	            orderListVo.setPurGoodsAmount(BigDecimal.ZERO);
	            orderListVo.setOriginalAmount(BigDecimal.ZERO);
	            orderListVo.setDiscountAmount(BigDecimal.ZERO);
	            orderListVo.setFinalAmount(BigDecimal.ZERO);
	        } else {
	        	ValidateUtils.notNull(supplyPrice, String.format("商品[%s]未设置供货价,清联系管理员", skuId));
				orderListVo.setSupplyPrice(supplyPrice.doubleValue());    //通过主账号取出供货价
	            orderListVo.setPurGoodsAmount(supplyPrice);
	            orderListVo.setOriginalAmount(supplyPrice);
	            orderListVo.setDiscountAmount(BigDecimal.ZERO);
				orderListVo.setFinalAmount(supplyPrice.multiply(new BigDecimal(orderListVo.getBuyNum())));
				orderListVo.setSumAmount(supplyPrice.multiply(new BigDecimal(orderListVo.getBuyNum())));
	        }
			orderListVo.setCargoSkuId(sku.getCargoSkuId() + "");
	        orderListVo.setPurGoodsName(sku.getMaterialSkuName());
			orderListVo.setPurGoodsType(sku.getCargoSkuName());

	        if (good != null) {
	            orderListVo.setPurGoodsName(good.getPurGoodsName());
	            orderListVo.setPurGoodsImgUrl(imageMap.get(good.getCargoId()).getUrl());
	        }
		}
		
	}
	
	private boolean isSc(String groupId, Set<String> scIds){
		
		Set<String> goodGroups = Sets.newHashSet(groupId.split(","));
		goodGroups.retainAll(scIds);
		
		return goodGroups.size() > 0;
		
	}
	
	public void sendShipSms(PurchaseOrder order, ShippingInfo si){
		
		Map<String, Object> dataMap = Maps.newHashMap();
		dataMap.put("receiver", order.getReceiver());
		dataMap.put("orderNo", order.getOrderNo());
		dataMap.put("expressNumber", si.getExpressNumber());
		dataMap.put("expressCompany", si.getExpressCompany());
		
		smsService.sendMsgByTpl(order.getReceiverPhone(), "sms/purchase_order_ship", dataMap);
	}
	
	
	@Transactional
	public void freeShipping(String orderId){
		
		PurchaseOrder order = orderService.getById(orderId);
		jdbcTemplate.update("update purchase_order set final_amount = final_amount - carriage, buyer_carriage = 0, carriage = 0 where id = ?", orderId);
		paymentService.fixPaymentAmount(orderId, order.getFreight(), OrderBizType.purchase);
		if (OrderType.presell.name().equals(order.getOrderType())){
			presellService.freeShippint(OrderBizType.purchase, orderId, order.getFreight());
		}
	}
}
