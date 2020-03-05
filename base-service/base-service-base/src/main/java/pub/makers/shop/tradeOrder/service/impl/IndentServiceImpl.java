package pub.makers.shop.tradeOrder.service.impl;

import com.lantu.base.common.entity.BoolType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.baseOrder.enums.OrderCancelType;
import pub.makers.shop.baseOrder.enums.OrderConfirmType;
import pub.makers.shop.baseOrder.enums.OrderDeleteType;
import pub.makers.shop.baseOrder.enums.OrderStatus;
import pub.makers.shop.tradeOrder.dao.IndentDao;
import pub.makers.shop.tradeOrder.entity.Indent;
import pub.makers.shop.tradeOrder.entity.IndentList;
import pub.makers.shop.tradeOrder.enums.IndentDealStatus;
import pub.makers.shop.tradeOrder.enums.IndentListStatus;
import pub.makers.shop.tradeOrder.service.IndentListService;
import pub.makers.shop.tradeOrder.service.IndentService;
import pub.makers.shop.tradeOrder.vo.IndentVo;
import pub.makers.shop.tradeOrder.vo.OrderPayInfo;
import pub.makers.shop.tradeOrder.vo.ShippingInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class IndentServiceImpl extends BaseCRUDServiceImpl<Indent, String, IndentDao>
										implements IndentService{
	@Autowired
	private IndentListService indentListService;
	
	@Override
	public void updateOrderToPayed(Indent order, OrderPayInfo payInfo, int status) {
		
		String paytype = "wxpay".equals(payInfo.getPayWay()) ? "1" : "2";
		BigDecimal allreadyPay = order.getPaymentAmount();
		if (allreadyPay == null){ allreadyPay = BigDecimal.ZERO; }
		
		// 更新订单状态
        this.update(Update.byId(payInfo.getOrderId()).set("status", status).set("pay_type", paytype)
                .set("pay_account", payInfo.getPayAccount()).set("payment_amount", allreadyPay.add(payInfo.getPaymentAmount())).set("pay_time", new Date()));

        List<IndentList> indentLists = indentListService.list(Conds.get().eq("indent_id", payInfo.getOrderId()));
        
        for (IndentList item : indentLists) {
            // 更新订单商品商品状态
        	indentListService.update(Update.byId(item.getId()).set("status", IndentListStatus.ship.name()).set("last_updated", new Date()));
        }
		
	}
	
	@Override
	public void updateOrderToPayed(Indent order, OrderPayInfo payInfo) {
		
		updateOrderToPayed(order, payInfo, OrderStatus.ship.getDbData());
		
	}

	@Override
	public void updateOrderToCancel(Indent order, OrderCancelType cancelType) {
		
		update(Update.byId(order.getId()).set("status", OrderStatus.cancel.getDbData()).set("cancel_type", cancelType.name()).set("finish_time", new Date())
                .set("deal_status", IndentDealStatus.deal_close.getDbData()));
		
		List<IndentList> indentLists = indentListService.list(Conds.get().eq("indent_id", order.getId()));
        for (IndentList item : indentLists) {
            // 更新订单商品
        	indentListService.update(Update.byId(item.getId()).set("status", IndentListStatus.canceled.name()).set("last_updated", new Date()));
        }
		
	}

	@Override
	public void updateOrderToReceipt(Indent order, OrderConfirmType confirmType) {
		
		// 更新订单
        update(Update.byId(order.getId()).set("status", OrderStatus.evaluate.getDbData()).set("confirm_type", confirmType.name()).set("finish_time", new Date())
                .set("deal_status", IndentDealStatus.deal_success.getDbData()));
        // 更新订单商品
        List<IndentList> indentLists = indentListService.list(Conds.get().eq("indent_id", order.getId()).eq("status", IndentListStatus.receive.name()));
        for (IndentList item : indentLists) {
        	indentListService.update(Update.byId(item.getId()).set("status", IndentListStatus.received.name()).set("last_updated", new Date()));
        }
		
	}

	@Override
	public void updateOrderToShipment(Indent order, ShippingInfo si) {
		
		// 更新订单状态
        update(Update.byId(order.getId()).set("status", OrderStatus.receive.getDbData()).set("shipper", si.getStaffName())
                .set("ship_time", new Date()).set("ship_notice", 0).set("express_number", si.getExpressNumber()).set("express_company", si.getExpressCompany())
                .set("express_id", si.getExpressId()).set("receive_timeout", order.getReceiveTimeout()));
        
        List<IndentList> indentLists = indentListService.list(Conds.get().eq("indent_id", si.getOrderId()));
        for (IndentList item : indentLists) {
            // 更新订单商品商品状态
        	indentListService.update(Update.byId(item.getId()).set("status", IndentListStatus.receive.name()).set("last_updated", new Date()));
        }
		
	}

	@Override
	public void saveOrder(IndentVo order) {
		
		insert(Indent.fromIndentVo(order));
		indentListService.saveIndentListVo(order.getIndentList());
		
	}

	@Override
	public void deleteOrder(Indent order, OrderDeleteType deleteType) {
		if (OrderDeleteType.buyer.equals(deleteType)) {
			update(Update.byId(order.getId()).set("buyer_del_flag", BoolType.T.name()));
		} else if (OrderDeleteType.seller.equals(deleteType)) {
			update(Update.byId(order.getId()).set("seller_del_flag", BoolType.T.name()));
		}
	}

	@Override
	public void shipNotice(Indent order) {
		update(Update.byId(order.getId()).set("ship_notice", 1));
	}
}
