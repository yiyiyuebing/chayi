package pub.makers.shop.afterSale.service.impl;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import com.qiniu.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.afterSale.entity.OrderItemAsFlow;
import pub.makers.shop.afterSale.service.AfterSaleHandler;
import pub.makers.shop.afterSale.vo.AfterSaleApply;
import pub.makers.shop.afterSale.vo.AfterSaleTkResult;
import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.service.SysDictService;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.baseOrder.enums.OrderStatus;
import pub.makers.shop.tradeOrder.entity.Indent;
import pub.makers.shop.tradeOrder.entity.IndentList;
import pub.makers.shop.tradeOrder.enums.IndentListStatus;
import pub.makers.shop.tradeOrder.service.IndentListService;
import pub.makers.shop.tradeOrder.service.IndentService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class TradeNormalAfterSaleHandler implements AfterSaleHandler {

	@Autowired
	private IndentService indentService;
	@Autowired
	private IndentListService indentListService;
	@Autowired
	private SysDictService sysDictService;
	
	@Override
	public AfterSaleTkResult calcMaxOrderItemTkAmount(AfterSaleApply apply) {
		
		List<IndentList> indentList = indentListService.list(Conds.get().eq("indentId", apply.getOrderId()).eq("gift_flag", BoolType.F.name()).in("id", apply.getOrderListIdList()));
		
		// 根据商品列表中的待支付金额累加
		// TODO 后续需要考虑整个订单打折的影响
		BigDecimal totalTkAmount = indentList.stream().map(m -> m.getWaitPayAmont().multiply(new BigDecimal(m.getBuyNum()))).reduce((sum, item) -> sum.add(item)).get();
		BigDecimal freight = BigDecimal.ZERO;

		// 未发货且是退款最后的商品可以退运费
		Indent indent = indentService.getById(apply.getOrderId());
		List<String> canReturnStatus = Lists.newArrayList(IndentListStatus.ship.name(), IndentListStatus.receive.name());
		List<IndentList> allList = indentListService.list(Conds.get().eq("indentId", apply.getOrderId()).eq("gift_flag", BoolType.F.name()).in("status", canReturnStatus));
		Set<String> listIds = ListUtils.getIdSet(indentList, "id");
		Set<String> allListIds = ListUtils.getIdSet(allList, "id");
		allListIds.removeAll(listIds);
		if (OrderStatus.ship.getDbData() == indent.getStatus() && !BoolType.T.name().equals(indent.getIsReturnCarriage()) && allListIds.isEmpty()){
			totalTkAmount = totalTkAmount.add(indent.getFreight());
			freight = indent.getFreight();
		}
		return new AfterSaleTkResult(totalTkAmount, freight);
	}

	@Override
	public AfterSaleTkResult calcMaxOrderTkAmount(AfterSaleApply apply) {
		
		// 查询订单信息
		Indent indent = indentService.getById(apply.getOrderId());
		BigDecimal totalTkAmount = null;
		BigDecimal freight = BigDecimal.ZERO;
		
		// 未发货前可以退运费
		if (OrderStatus.ship.getDbData() == indent.getStatus()){
			totalTkAmount = indent.getPaymentAmount();
			freight = indent.getFreight();
		}
		else {
			totalTkAmount = indent.getPaymentAmount().subtract(indent.getFreight());
		}
		
		return new AfterSaleTkResult(totalTkAmount, freight);
	}

	@Override
	public List<OrderItemAsFlow> createOrderFlow(OrderItemAsFlow flow, AfterSaleApply apply) {
		//订单商品列表
		Indent indent = indentService.getById(apply.getOrderId());
		ValidateUtils.notNull(indent, "订单不存在");
		ValidateUtils.isTrue(indent.getBuyerId().equals(apply.getOperManId()), "只能操作自己的订单");
		final List<IndentList> indentListList = Lists.newArrayList();
		List<IndentList> list = indentListService.list(Conds.get().eq("indent_id", apply.getOrderId()));
		ValidateUtils.notNull(list, "订单不存在");
		ValidateUtils.isTrue(list.size() > 0, "订单不存在");

		//查询最多售后次数
		SysDict dict = sysDictService.get(Conds.get().eq("dict_type", "return_time").eq("code", "trade"));
		Integer maxReturnTime = Integer.valueOf(dict.getValue());

		LinkedListMultimap<String, IndentList> indentListArrayListMultimap = LinkedListMultimap.create();
		for (IndentList indentList : list) {
			if (BoolType.T.name().equals(indentList.getGiftFlag())) {
				continue;
			}
			//订单状态为待发货和待收获的商品可以申请售后
			ValidateUtils.isTrue(indentList.getStatus().equals(IndentListStatus.ship.name()) || indentList.getStatus().equals(IndentListStatus.receive.name()), "订单状态错误");
			Integer shipReturnTime = indentList.getShipReturnTime() == null ? 0 : indentList.getShipReturnTime();
			Integer returnTime = indentList.getReturnTime() == null ? 0 : indentList.getReturnTime();
			if (IndentListStatus.ship.name().equals(indentList.getStatus())) {
				ValidateUtils.isTrue(!BoolType.T.name().equals(indentList.getShipCancelAfter()), "已撤销申请，无法再提交申请");
				//售后次数验证
				ValidateUtils.isTrue(shipReturnTime < maxReturnTime, "申请售后次数已达上限");
				shipReturnTime++;
			} else {
				ValidateUtils.isTrue(!BoolType.T.name().equals(indentList.getReceiveCancelAfter()), "已撤销申请，无法再提交申请");
				//售后次数验证
				ValidateUtils.isTrue(returnTime < maxReturnTime, "申请售后次数已达上限");
				returnTime++;
			}
			indentList.setShipReturnTime(shipReturnTime);
			indentList.setReturnTime(returnTime);
			indentListList.add(indentList);
			indentListArrayListMultimap.put(indentList.getGoodSkuId(), indentList);
		}

		for (IndentList indentList : indentListList) {
			indentListService.update(Update.byId(indentList.getId()).set("return_time", indentList.getReturnTime())
					.set("ship_return_time", indentList.getShipReturnTime()).set("status", apply.getListStatus().name()).set("last_updated", new Date()));
		}

		flow.setOrderId(apply.getOrderId());
		List<OrderItemAsFlow> flowList = Lists.newArrayList();
		for (String skuId : indentListArrayListMultimap.keySet()) {
			OrderItemAsFlow orderItemAsFlow = new OrderItemAsFlow();
			BeanUtils.copyProperties(flow, orderItemAsFlow);
			orderItemAsFlow.setId(IdGenerator.getDefault().nextStringId());
			orderItemAsFlow.setGoodSkuId(skuId);
			List<IndentList> indentLists = indentListArrayListMultimap.get(skuId);
			orderItemAsFlow.setOrderListIds(org.apache.commons.lang.StringUtils.join(ListUtils.getIdSet(indentLists, "id"), ","));
			orderItemAsFlow.setOrderListStatus(indentLists.get(0).getStatus());
			orderItemAsFlow.setGoodNum(indentLists.size());
			flowList.add(orderItemAsFlow);
		}
		return flowList;
	}

	@Override
	public OrderItemAsFlow createListFlow(OrderItemAsFlow flow, AfterSaleApply apply) {
		//订单商品列表
		List<String> listIdList = Lists.newArrayList();
		for (String ids : apply.getOrderListIdList()) {
			listIdList.addAll(Arrays.asList(org.apache.commons.lang.StringUtils.split(ids, ",")));
		}
		final List<IndentList> indentListList = indentListService.list(Conds.get().in("id", listIdList));
		ValidateUtils.notNull(indentListList, "订单不存在");
		ValidateUtils.isTrue(indentListList.size() == listIdList.size(), "订单不存在");
		Indent indent = indentService.getById(indentListList.get(0).getIndentId());
		ValidateUtils.notNull(indent, "订单不存在");
		ValidateUtils.isTrue(indent.getBuyerId().equals(apply.getOperManId()), "只能操作自己的订单");

		//查询最多售后次数
		SysDict dict = sysDictService.get(Conds.get().eq("dict_type", "return_time").eq("code", "trade"));
		Integer maxReturnTime = Integer.valueOf(dict.getValue());

		String skuId = indentListList.get(0).getCargoSkuId();
		for (IndentList indentList : indentListList) {
			ValidateUtils.isTrue(!BoolType.T.name().equals(indentList.getGiftFlag()), "赠品不能售后");
			ValidateUtils.isTrue(skuId.equals(indentList.getCargoSkuId()), "订单商品不是同一个商品");
			//订单状态为待发货和待收获的商品可以申请售后
			ValidateUtils.isTrue(indentList.getStatus().equals(IndentListStatus.ship.name()) || indentList.getStatus().equals(IndentListStatus.receive.name()), "订单状态错误");
			Integer shipReturnTime = indentList.getShipReturnTime() == null ? 0 : indentList.getShipReturnTime();
			Integer returnTime = indentList.getReturnTime() == null ? 0 : indentList.getReturnTime();
			if (IndentListStatus.ship.name().equals(indentList.getStatus())) {
				ValidateUtils.isTrue(!BoolType.T.name().equals(indentList.getShipCancelAfter()), "已撤销申请，无法再提交申请");
				//售后次数验证
				ValidateUtils.isTrue(shipReturnTime < maxReturnTime, "申请售后次数已达上限");
				shipReturnTime++;
			} else {
				ValidateUtils.isTrue(!BoolType.T.name().equals(indentList.getReceiveCancelAfter()), "已撤销申请，无法再提交申请");
				//售后次数验证
				ValidateUtils.isTrue(returnTime < maxReturnTime, "申请售后次数已达上限");
				returnTime++;
			}
			indentList.setShipReturnTime(shipReturnTime);
			indentList.setReturnTime(returnTime);
		}

		flow.setId(IdGenerator.getDefault().nextStringId());
		flow.setOrderId(indentListList.get(0).getIndentId().toString());
		flow.setOrderListIds(StringUtils.join(listIdList, ","));
		flow.setGoodSkuId(indentListList.get(0).getTradeGoodSkuId().toString());
		flow.setOrderListStatus(indentListList.get(0).getStatus());
		flow.setGoodNum(indentListList.size());

		for (IndentList indentList : indentListList) {
			indentListService.update(Update.byId(indentList.getId()).set("return_time", indentList.getReturnTime())
					.set("ship_return_time", indentList.getShipReturnTime()).set("status", apply.getListStatus().name()).set("last_updated", new Date()));
		}
		return flow;
	}

}
