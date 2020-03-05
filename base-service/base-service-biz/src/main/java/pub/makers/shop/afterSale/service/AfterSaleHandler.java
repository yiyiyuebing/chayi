package pub.makers.shop.afterSale.service;

import pub.makers.shop.afterSale.entity.OrderItemAsFlow;
import pub.makers.shop.afterSale.vo.AfterSaleApply;
import pub.makers.shop.afterSale.vo.AfterSaleTkResult;

import java.util.List;

public interface AfterSaleHandler {

	/**
	 * 计算订单商品的最大可退款金额
	 * @param apply
	 * @return
	 */
	AfterSaleTkResult calcMaxOrderItemTkAmount(AfterSaleApply apply);
	
	/**
	 * 计算订单的最大可退款金额
	 * @param apply
	 * @return
	 */
	AfterSaleTkResult calcMaxOrderTkAmount(AfterSaleApply apply);

	/**
	 * 创建订单售后
	 */
	List<OrderItemAsFlow> createOrderFlow(OrderItemAsFlow flow, AfterSaleApply apply);

	/**
	 * 创建订单商品售后
	 */
	OrderItemAsFlow createListFlow(OrderItemAsFlow flow, AfterSaleApply apply);
}
