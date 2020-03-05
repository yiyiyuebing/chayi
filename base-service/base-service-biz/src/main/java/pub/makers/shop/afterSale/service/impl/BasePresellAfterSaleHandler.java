package pub.makers.shop.afterSale.service.impl;

import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.afterSale.service.AfterSaleHandler;
import pub.makers.shop.afterSale.vo.AfterSaleApply;
import pub.makers.shop.afterSale.vo.AfterSaleTkResult;
import pub.makers.shop.baseOrder.entity.OrderListPresellExtra;
import pub.makers.shop.baseOrder.entity.OrderPresellExtra;
import pub.makers.shop.baseOrder.enums.OrderStatus;
import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.baseOrder.pojo.BaseOrderItem;
import pub.makers.shop.baseOrder.service.OrderListPresellExtraService;
import pub.makers.shop.baseOrder.service.OrderPresellExtraService;
import pub.makers.shop.promotion.enums.PresellType;
import pub.makers.shop.tradeOrder.enums.IndentListStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public abstract class BasePresellAfterSaleHandler implements AfterSaleHandler{

	protected abstract BaseOrder getOrder(String orderId);
	protected abstract List<? extends BaseOrderItem> getOrderList(String orderId, List<String> orderListId, List<String> statusList);
	protected abstract OrderPresellExtraService getOrderPresellExtraService();
	protected abstract OrderListPresellExtraService getOrderListPresellExtraService();
	
	@Override
	public AfterSaleTkResult calcMaxOrderItemTkAmount(AfterSaleApply apply) {
		
		List<? extends BaseOrderItem> orderList = getOrderList(apply.getOrderId(), apply.getOrderListIdList(), null);
		Set<String> skuIdSet = ListUtils.getIdSet(orderList, "goodSkuId");
		
		List<OrderListPresellExtra> extList = getOrderListPresellExtraService().list(Conds.get().eq("orderId", apply.getOrderId()).in("goodSkuId", skuIdSet));
		// 如果是一次性付款
		OrderPresellExtra extra = getOrderPresellExtraService().get(Conds.get().eq("orderId", apply.getOrderId()));
		
		BigDecimal totalTkAmount = null;
		if (PresellType.one.name().equals(extra.getPresellType())){
			totalTkAmount = extList.stream().map(m -> m.getPresellFirst()).reduce((sum, item) -> sum.add(item)).get();
		}
		else {
			totalTkAmount = extList.stream().map(m -> m.getPresellEnd()).reduce((sum, item) -> sum.add(item)).get();
		}
		BigDecimal freight = BigDecimal.ZERO;

		// 未发货且是退款最后的商品可以退运费
		BaseOrder order = getOrder(apply.getOrderId());
		List<String> canReturnStatus = Lists.newArrayList(IndentListStatus.ship.name(), IndentListStatus.receive.name());
		List<? extends BaseOrderItem> allList = getOrderList(apply.getOrderId(), null, canReturnStatus);
		Set<String> listIds = ListUtils.getIdSet(orderList, "id");
		Set<String> allListIds = ListUtils.getIdSet(allList, "id");
		allListIds.removeAll(listIds);
		if (OrderStatus.ship.getDbData() == order.getStatus() && !BoolType.T.name().equals(order.getIsReturnCarriage()) && allListIds.isEmpty()){
			totalTkAmount = totalTkAmount.add(order.getFreight());
			freight = order.getFreight();
		}
		return new AfterSaleTkResult(totalTkAmount, freight);
	}

	@Override
	public AfterSaleTkResult calcMaxOrderTkAmount(AfterSaleApply apply) {
		
		BaseOrder order = getOrder(apply.getOrderId());
		OrderPresellExtra extra = getOrderPresellExtraService().get(Conds.get().eq("orderId", apply.getOrderId()));
		BigDecimal totalTkAmount = null;
		BigDecimal freight = BigDecimal.ZERO;
		
		// 未发货前可以退运费
		if (OrderStatus.ship.getDbData() == order.getStatus()){
			totalTkAmount = getPresellTkAmount(extra);
			freight = order.getFreight();
		}
		else {
			totalTkAmount = getPresellTkAmount(extra).subtract(order.getFreight());
		}
		
		return new AfterSaleTkResult(totalTkAmount, freight);
	}
	
	private BigDecimal getPresellTkAmount(OrderPresellExtra extra){
		// 一次性的退款金额
		if (PresellType.one.name().equals(extra.getPresellType())){
			return extra.getPresellFirst();
		}
		return extra.getPresellEnd();
	}
}
