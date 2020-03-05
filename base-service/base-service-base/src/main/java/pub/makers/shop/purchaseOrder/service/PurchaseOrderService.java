package pub.makers.shop.purchaseOrder.service;

import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.baseOrder.enums.OrderCancelType;
import pub.makers.shop.baseOrder.enums.OrderConfirmType;
import pub.makers.shop.baseOrder.enums.OrderDeleteType;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrder;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.tradeOrder.vo.OrderPayInfo;
import pub.makers.shop.tradeOrder.vo.ShippingInfo;

/**
 * Created by dy on 2017/4/10.
 */
public interface PurchaseOrderService extends BaseCRUDService<PurchaseOrder> {

    /**
     * 通过id获取PurchaseOrder信息
     * @param id
     * @return
     */
    PurchaseOrder getPurchaseOrderById(Long id);
    
    /**
     * 将订单和订单商品状态更新为已支付
     * @param payInfo
     */
    void updateOrderToPayed(PurchaseOrder order, OrderPayInfo payInfo);
    
    void updateOrderToPayed(PurchaseOrder order, OrderPayInfo payInfo, int status);
    
    
    /**
     * 将订单和订单商品状态更新为已取消
     * @param bo
     */
    void updateOrderToCancel(PurchaseOrder order, OrderCancelType cancelType);
    
    
    /**
     * 将订单和订单商品更新为已确认收货
     * @param order
     * @param confirmType
     */
    void updateOrderToReceipt(PurchaseOrder order, OrderConfirmType confirmType);
    
    /**
     * 将商品和订单状态更新为已发货
     * @param order
     */
    void updateOrderToShipment(PurchaseOrder order, ShippingInfo si);
    
    /**
     * 
     * @param order
     */
    void saveOrder(PurchaseOrderVo order);

    /**
     * 删除订单
     */
    void deleteOrder(PurchaseOrder order, OrderDeleteType deleteType);

    /**
     * 发货提醒
     */
    void shipNotick(PurchaseOrder order);
}
