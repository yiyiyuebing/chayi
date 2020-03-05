package pub.makers.shop.purchaseOrder.service.impl;

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
import pub.makers.shop.purchaseGoods.service.PurchaseOrderListService;
import pub.makers.shop.purchaseOrder.dao.PurchaseOrderDao;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrder;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrderList;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderService;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.tradeOrder.enums.IndentDealStatus;
import pub.makers.shop.tradeOrder.enums.IndentListStatus;
import pub.makers.shop.tradeOrder.vo.OrderPayInfo;
import pub.makers.shop.tradeOrder.vo.ShippingInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2017/4/10.
 */
@Service
public class PurchaseOrderServiceImpl
        extends BaseCRUDServiceImpl<PurchaseOrder, String, PurchaseOrderDao>
        implements PurchaseOrderService {

		@Autowired
	    private PurchaseOrderListService purchaseOrderListService;
	
        @Override
        public PurchaseOrder getPurchaseOrderById(Long id) {
                return getById(id);
        }

		@Override
		public void updateOrderToPayed(PurchaseOrder order, OrderPayInfo payInfo) {
			
			updateOrderToPayed(order, payInfo, OrderStatus.ship.getDbData());
		}
		
		@Override
		public void updateOrderToPayed(PurchaseOrder order, OrderPayInfo payInfo, int status) {
			
			BigDecimal allreadyPay = order.getPaymentAmount();
			if (allreadyPay == null){ allreadyPay = BigDecimal.ZERO; }
			
			String paytype = "wxpay".equals(payInfo.getPayWay()) ? "1" : "2";
			// 更新订单状态
            this.update(Update.byId(payInfo.getOrderId()).set("status", status).set("pay_type", paytype)
                    .set("pay_account", payInfo.getPayAccount()).set("payment_amount", allreadyPay.add(payInfo.getPaymentAmount())).set("pay_time", new Date()));

            List<PurchaseOrderList> purchaseOrderLists = purchaseOrderListService.list(Conds.get().eq("order_id", payInfo.getOrderId()));
            
            for (PurchaseOrderList purchaseOrderList : purchaseOrderLists) {
                // 更新订单商品商品状态
                purchaseOrderListService.update(Update.byId(purchaseOrderList.getId()).set("status", IndentListStatus.ship.name()).set("last_updated", new Date()));
            }
		}

		@Override
		public void updateOrderToCancel(PurchaseOrder order, OrderCancelType cancelType) {
			
			update(Update.byId(order.getId()).set("status", OrderStatus.cancel.getDbData()).set("cancel_type", cancelType.name()).set("finish_time", new Date())
	                .set("deal_status", IndentDealStatus.deal_close.getDbData()));
			
			List<PurchaseOrderList> purchaseOrderLists = purchaseOrderListService.list(Conds.get().eq("order_id", order.getId()));
	        for (PurchaseOrderList purchaseOrderList : purchaseOrderLists) {
	            // 更新订单商品
	            purchaseOrderListService.update(Update.byId(purchaseOrderList.getId()).set("status", IndentListStatus.canceled.name()).set("last_updated", new Date()));
	        }
		}

		@Override
		public void updateOrderToReceipt(PurchaseOrder order, OrderConfirmType confirmType) {
			
			// 更新订单
            update(Update.byId(order.getId()).set("status", OrderStatus.evaluate.getDbData()).set("confirm_type", confirmType.name()).set("finish_time", new Date())
                    .set("deal_status", IndentDealStatus.deal_success.getDbData()));
            // 更新订单商品
            List<PurchaseOrderList> purchaseOrderLists = purchaseOrderListService.list(Conds.get().eq("order_id", order.getId()).eq("status", IndentListStatus.receive.name()));
            for (PurchaseOrderList purchaseOrderList : purchaseOrderLists) {
                purchaseOrderListService.update(Update.byId(purchaseOrderList.getId()).set("status", IndentListStatus.received.name()).set("last_updated", new Date()));
            }
		}

		@Override
		public void updateOrderToShipment(PurchaseOrder order, ShippingInfo si) {
			
			// 更新订单状态
            update(Update.byId(order.getId()).set("status", OrderStatus.receive.getDbData()).set("shipper", si.getStaffName())
                    .set("ship_time", new Date()).set("ship_notice", 0).set("express_number", si.getExpressNumber()).set("express_company", si.getExpressCompany())
                    .set("express_id", si.getExpressId()).set("receive_timeout", order.getReceiveTimeout()));
            
            List<PurchaseOrderList> purchaseOrderLists = purchaseOrderListService.list(Conds.get().eq("order_id", si.getOrderId()));
            for (PurchaseOrderList purchaseOrderList : purchaseOrderLists) {
                // 更新订单商品商品状态
                purchaseOrderListService.update(Update.byId(purchaseOrderList.getId()).set("status", IndentListStatus.receive.name()).set("last_updated", new Date()));
            }
		}

		@Override
		public void saveOrder(PurchaseOrderVo order) {
			insert(PurchaseOrder.fromPurchaseOrderVo(order));
			purchaseOrderListService.savePurchaseOrderListVo(order.getOrderListVos());
		}

	@Override
	public void deleteOrder(PurchaseOrder order, OrderDeleteType deleteType) {
		if (OrderDeleteType.buyer.equals(deleteType)) {
			update(Update.byId(order.getId()).set("buyer_del_flag", BoolType.T.name()));
		} else if (OrderDeleteType.seller.equals(deleteType)) {
			update(Update.byId(order.getId()).set("seller_del_flag", BoolType.T.name()));
		}
	}

	@Override
	public void shipNotick(PurchaseOrder order) {
		update(Update.byId(order.getId()).set("ship_notice", "1"));
	}
}
