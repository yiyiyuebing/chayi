package pub.makers.shop.purchseOrder.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.*;
import pub.makers.shop.baseOrder.service.BaseOrderSupport;
import pub.makers.shop.baseOrder.service.OrderPaymentBizService;
import pub.makers.shop.purchaseOrder.service.PurchaseOrderManager;
import pub.makers.shop.tradeOrder.vo.OrderPayInfo;
import pub.makers.shop.tradeOrder.vo.ShippingInfo;

@Service(version="1.0.0")
public class PurchaseOrderManagerImpl implements PurchaseOrderManager{

	@Autowired
	private PurchaseOrderSupportManager supportManager;
	@Autowired
	private OrderPaymentBizService paymentService;
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Override
	public BaseOrder createOrder(OrderSubmitInfo info) {
		
		BaseOrderSupport support = supportManager.getOrderSupport(info.getOrderBizType(), info.getOrderType());
		return support.createOrder(info.getBaseOrder());
	}

	@Override
	public void cancelOrder(OrderCancelInfo info) {
		
		BaseOrderSupport support = supportManager.getOrderSupport(info.getOrderBizType(), info.getOrderType());
		support.cancelOrder(info.getUserId(), info.getOrderId(), info.getCancelType());
	}

	@Override
	public void payOrder(final OrderPayInfo info) {
		final BaseOrderSupport support = supportManager.getOrderSupport(info.getOrderBizType(), info.getOrderType());
		// 将支付单号设置成已支付
		transactionTemplate.execute(new TransactionCallback<Object>() {

			@Override
			public Object doInTransaction(TransactionStatus status) {
				paymentService.updateToPayed(info);
				support.payOrder(info);
				return null;
			}
		});
		
	}

	@Override
	public void shipmentOrder(ShippingInfo info) {
		BaseOrderSupport support = supportManager.getOrderSupport(info.getOrderBizType(), info.getOrderType());
		support.shipmentOrder(info);
	}

	@Override
	public void confirmReceipt(OrderConfirmInfo info) {
		
		BaseOrderSupport support = supportManager.getOrderSupport(info.getOrderBizType(), info.getOrderType());
		support.confirmReceipt(info.getUserId(), info.getOrderId(), info.getConfirmType());
	}

	@Override
	public void freeShipping(ShippingInfo info) {
		BaseOrderSupport support = supportManager.getOrderSupport(info.getOrderBizType(), info.getOrderType());
		support.freeShipping(info.getOrderId());
	}

	@Override
	public String toPay(PayParam param) {
		BaseOrderSupport support = supportManager.getOrderSupport(param.getOrderBizType(), param.getOrderType());
		return support.toPay(param);
	}

	@Override
	public void deleteOrder(OrderDeleteInfo info) {
		BaseOrderSupport support = supportManager.getOrderSupport(info.getOrderBizType(), info.getOrderType());
		support.deleteOrder(info);
	}

	@Override
	public void shipNotice(ShipNoticeInfo info) {
		BaseOrderSupport support = supportManager.getOrderSupport(info.getOrderBizType(), info.getOrderType());
		support.shipNotice(info);
	}

	@Override
	public BaseOrder preview(BaseOrder order) {
		
		BaseOrderSupport support = supportManager.getOrderSupport(OrderBizType.purchase, OrderType.fromName(order.getOrderType()));
		return support.preCreate(order);
	}

}
