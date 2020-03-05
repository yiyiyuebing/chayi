package pub.makers.shop.afterSale.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lantu.base.common.entity.BoolType;

import pub.makers.shop.afterSale.vo.AfterSaleApply;
import pub.makers.shop.afterSale.vo.AfterSaleTkResult;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.purchaseGoods.service.PurchaseOrderListService;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrderList;
import pub.makers.shop.tradeOrder.entity.IndentList;
import pub.makers.shop.tradeOrder.service.IndentListService;

/**
 * 售后退款业务服务
 * @author apple
 *
 */
@Service(version="1.0.0")
public class AfterSaleTkBizServiceImpl implements AfterSaleTkBizService {

	@Autowired
	private AfterSaleHandlerManager handlerManager;
	@Autowired
	private IndentListService indentListService;
	@Autowired
	private PurchaseOrderListService orderListService;
	
	@Override
	public AfterSaleTkResult queryTkResult(AfterSaleApply apply) {
		
		List<String> orderIdList = apply.getOrderListIdList();
		if (orderIdList != null && !orderIdList.isEmpty()){
			List<String> nList = Lists.newArrayList();
			for (String orderId : orderIdList){
				nList.addAll(Sets.newHashSet(orderId.split(",")));
			}
			apply.setOrderListIdList(nList);
		}
		
		boolean isOrderTk = false;
		if (apply.getOrderListIdList() == null || apply.getOrderListIdList().isEmpty()){
			isOrderTk = true;
		}
		else {
			Set<String> iptDetailIds = Sets.newHashSet(apply.getOrderListIdList());
			// 如果传递的订单明细ID是所有订单ID，则为整个订单退款
			if (OrderBizType.trade.equals(apply.getOrderBizType())){
				
				List<IndentList> indentList = indentListService.listByOrderId(apply.getOrderId());
				// 订单列表排除赠品
				Set<String> orderIdSet = indentList.stream().filter(m -> !BoolType.T.name().equals(m.getGiftFlag())).map(m -> m.getId() + "").collect(Collectors.toSet());
				orderIdSet.removeAll(iptDetailIds);
				isOrderTk = orderIdSet.isEmpty();
			}
			else if (OrderBizType.purchase.equals(apply.getOrderBizType())){
				
				List<PurchaseOrderList> orderList = orderListService.listByOrderId(apply.getOrderId());
				Set<String> orderIdSet = orderList.stream().filter(m -> !BoolType.T.name().equals(m.getGiftFlag())).map(m -> m.getId() + "").collect(Collectors.toSet());
				orderIdSet.removeAll(iptDetailIds);
				isOrderTk = orderIdSet.isEmpty();
			}
		}
		
		AfterSaleHandler handler = handlerManager.getHandler(apply.getOrderBizType(), apply.getOrderType());
		AfterSaleTkResult result = null;
		// 没有传递商品列表，为订单退款
		if (isOrderTk){
			result = handler.calcMaxOrderTkAmount(apply);
		}
		// 传递商品列表，退款方式为根据商品退款
		else {
			result = handler.calcMaxOrderItemTkAmount(apply);
		}
		
		return result;
	}

}
