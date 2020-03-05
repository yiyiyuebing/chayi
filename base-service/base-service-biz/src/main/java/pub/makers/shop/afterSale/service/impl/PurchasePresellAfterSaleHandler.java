package pub.makers.shop.afterSale.service.impl;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.afterSale.entity.OrderItemAsFlow;
import pub.makers.shop.afterSale.vo.AfterSaleApply;
import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.service.SysDictService;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.baseOrder.pojo.BaseOrderItem;
import pub.makers.shop.baseOrder.service.OrderListPresellExtraService;
import pub.makers.shop.baseOrder.service.OrderPresellExtraService;
import pub.makers.shop.purchaseGoods.service.PurchaseOrderListService;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrder;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrderList;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderService;
import pub.makers.shop.tradeOrder.enums.IndentListStatus;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PurchasePresellAfterSaleHandler extends BasePresellAfterSaleHandler {

	@Autowired
	private PurchaseOrderService orderService;
	@Autowired
	private PurchaseOrderListService orderListService;
	@Resource(name="purchaseOrderPresellExtraServiceImpl")
	private OrderPresellExtraService presellExtraService;
	@Resource(name="purchaseOrderListPresellExtraServiceImpl")
	private OrderListPresellExtraService orderListPresellExtraService;
	@Autowired
	private SysDictService sysDictService;
	
	@Override
	protected BaseOrder getOrder(String orderId) {
		return orderService.getById(orderId);
	}

	@Override
	protected List<? extends BaseOrderItem> getOrderList(String orderId, List<String> orderListId, List<String> statusList) {
		Conds conds = Conds.get().eq("orderId", orderId);
		if (orderListId != null) {
			conds.in("id", orderListId);
		}
		if (statusList != null) {
			conds.in("status", statusList);
		}
		return orderListService.list(conds);
	}

	@Override
	protected OrderPresellExtraService getOrderPresellExtraService() {
		return presellExtraService;
	}

	@Override
	protected OrderListPresellExtraService getOrderListPresellExtraService() {
		return orderListPresellExtraService;
	}

	@Override
	public List<OrderItemAsFlow> createOrderFlow(OrderItemAsFlow flow, AfterSaleApply apply) {
		//订单商品列表
		PurchaseOrder order = orderService.getById(apply.getOrderId());
		ValidateUtils.isTrue(order.getBuyerId().equals(apply.getOperManId()), "只能操作自己的订单");
		ValidateUtils.notNull(order, "订单不存在");
		final List<PurchaseOrderList> purchaseOrderLists = Lists.newArrayList();
		List<PurchaseOrderList> list = orderListService.list(Conds.get().eq("order_id", apply.getOrderId()));
		ValidateUtils.notNull(list, "订单不存在");
		ValidateUtils.isTrue(list.size() > 0, "订单不存在");

		//查询最多售后次数
		SysDict dict = sysDictService.get(Conds.get().eq("dict_type", "return_time").eq("code", "purchase"));
		Integer maxReturnTime = Integer.valueOf(dict.getValue());

		LinkedListMultimap<String, PurchaseOrderList> purchaseOrderListMultimap = LinkedListMultimap.create();
		for (PurchaseOrderList purchaseOrderList : list) {
			if (BoolType.T.name().equals(purchaseOrderList.getGiftFlag()) || (BoolType.T.name().equals(purchaseOrderList.getIsSample())) && !purchaseOrderList.getStatus().equals(IndentListStatus.ship.name())) {
				continue;
			}
			//订单状态为待发货和待收获的商品可以申请售后
			ValidateUtils.isTrue(purchaseOrderList.getStatus().equals(IndentListStatus.ship.name()) || purchaseOrderList.getStatus().equals(IndentListStatus.receive.name()), "订单状态错误");
			Integer shipReturnTime = purchaseOrderList.getShipReturnTime() == null ? 0 : purchaseOrderList.getShipReturnTime();
			Integer returnTime = purchaseOrderList.getReturnTime() == null ? 0 : purchaseOrderList.getReturnTime();
			if (IndentListStatus.ship.name().equals(purchaseOrderList.getStatus())) {
				ValidateUtils.isTrue(!BoolType.T.name().equals(purchaseOrderList.getShipCancelAfter()), "已撤销申请，无法再提交申请");
				//售后次数验证
				ValidateUtils.isTrue(shipReturnTime < maxReturnTime, "申请售后次数已达上限");
				shipReturnTime++;
			} else {
				ValidateUtils.isTrue(!BoolType.T.name().equals(purchaseOrderList.getReceiveCancelAfter()), "已撤销申请，无法再提交申请");
				//售后次数验证
				ValidateUtils.isTrue(returnTime < maxReturnTime, "申请售后次数已达上限");
				returnTime++;
			}
			purchaseOrderList.setShipReturnTime(shipReturnTime);
			purchaseOrderList.setReturnTime(returnTime);
			purchaseOrderLists.add(purchaseOrderList);
			purchaseOrderListMultimap.put(purchaseOrderList.getGoodSkuId(), purchaseOrderList);
		}
		ValidateUtils.isTrue(!purchaseOrderLists.isEmpty(), "订单无法售后");
		for (PurchaseOrderList purchaseOrderList : purchaseOrderLists) {
			orderListService.update(Update.byId(purchaseOrderList.getId()).set("return_time", purchaseOrderList.getReturnTime())
					.set("ship_return_time", purchaseOrderList.getShipReturnTime()).set("status", apply.getListStatus().name()).set("last_updated", new Date()));
		}

		flow.setOrderId(apply.getOrderId());
		List<OrderItemAsFlow> flowList = Lists.newArrayList();
		for (String skuId : purchaseOrderListMultimap.keySet()) {
			OrderItemAsFlow orderItemAsFlow = new OrderItemAsFlow();
			BeanUtils.copyProperties(flow, orderItemAsFlow);
			orderItemAsFlow.setId(IdGenerator.getDefault().nextStringId());
			orderItemAsFlow.setGoodSkuId(skuId);
			List<PurchaseOrderList> purchaseOrderListList = purchaseOrderListMultimap.get(skuId);
			orderItemAsFlow.setOrderListIds(StringUtils.join(ListUtils.getIdSet(purchaseOrderListList, "id"), ","));
			orderItemAsFlow.setOrderListStatus(purchaseOrderListList.get(0).getStatus());
			orderItemAsFlow.setGoodNum(purchaseOrderListList.size());
			flowList.add(orderItemAsFlow);
		}
		return flowList;
	}

	@Override
	public OrderItemAsFlow createListFlow(OrderItemAsFlow flow, AfterSaleApply apply) {
		//订单商品列表
		List<String> listIdList = Lists.newArrayList();
		for (String ids : apply.getOrderListIdList()) {
			listIdList.addAll(Arrays.asList(StringUtils.split(ids, ",")));
		}
		final List<PurchaseOrderList> purchaseOrderLists = orderListService.list(Conds.get().in("id", listIdList));
		ValidateUtils.notNull(purchaseOrderLists, "订单不存在");
		ValidateUtils.isTrue(purchaseOrderLists.size() == listIdList.size(), "订单不存在");
		PurchaseOrder order = orderService.getById(purchaseOrderLists.get(0).getOrderId());
		ValidateUtils.notNull(order, "订单不存在");
		ValidateUtils.isTrue(order.getBuyerId().equals(apply.getOperManId()), "只能操作自己的订单");

		//查询最多售后次数
		SysDict dict = sysDictService.get(Conds.get().eq("dict_type", "return_time").eq("code", "purchase"));
		Integer maxReturnTime = Integer.valueOf(dict.getValue());

		String skuId = purchaseOrderLists.get(0).getCargoSkuId();
		for (PurchaseOrderList purchaseOrderList : purchaseOrderLists) {
			ValidateUtils.isTrue(!BoolType.T.name().equals(purchaseOrderList.getGiftFlag()), "赠品不能售后");
			ValidateUtils.isTrue(!BoolType.T.name().equals(purchaseOrderList.getIsSample()) || purchaseOrderList.getStatus().equals(IndentListStatus.ship.name()), "样品不能售后");
			ValidateUtils.isTrue(skuId.equals(purchaseOrderList.getCargoSkuId()), "订单商品不是同一个商品");
			//订单状态为待发货和待收获的商品可以申请售后
			ValidateUtils.isTrue(purchaseOrderList.getStatus().equals(IndentListStatus.ship.name()) || purchaseOrderList.getStatus().equals(IndentListStatus.receive.name()), "订单状态错误");
			Integer shipReturnTime = purchaseOrderList.getShipReturnTime() == null ? 0 : purchaseOrderList.getShipReturnTime();
			Integer returnTime = purchaseOrderList.getReturnTime() == null ? 0 : purchaseOrderList.getReturnTime();
			if (IndentListStatus.ship.name().equals(purchaseOrderList.getStatus())) {
				ValidateUtils.isTrue(!BoolType.T.name().equals(purchaseOrderList.getShipCancelAfter()), "已撤销申请，无法再提交申请");
				//售后次数验证
				ValidateUtils.isTrue(shipReturnTime < maxReturnTime, "申请售后次数已达上限");
				shipReturnTime++;
			} else {
				ValidateUtils.isTrue(!BoolType.T.name().equals(purchaseOrderList.getReceiveCancelAfter()), "已撤销申请，无法再提交申请");
				//售后次数验证
				ValidateUtils.isTrue(returnTime < maxReturnTime, "申请售后次数已达上限");
				returnTime++;
			}
			purchaseOrderList.setShipReturnTime(shipReturnTime);
			purchaseOrderList.setReturnTime(returnTime);
		}

		flow.setId(IdGenerator.getDefault().nextStringId());
		flow.setOrderId(purchaseOrderLists.get(0).getOrderId());
		flow.setOrderListIds(StringUtils.join(listIdList, ","));
		flow.setGoodSkuId(purchaseOrderLists.get(0).getPurGoodsSkuId());
		flow.setOrderListStatus(purchaseOrderLists.get(0).getStatus());
		flow.setGoodNum(purchaseOrderLists.size());

		for (PurchaseOrderList purchaseOrderList : purchaseOrderLists) {
			orderListService.update(Update.byId(purchaseOrderList.getId()).set("return_time", purchaseOrderList.getReturnTime())
					.set("status", apply.getListStatus().name()).set("last_updated", new Date()));
		}
		return flow;
	}
}
