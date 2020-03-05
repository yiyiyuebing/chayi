package pub.makers.shop.tradeOrder.service;


import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.baseOrder.enums.OrderCancelType;
import pub.makers.shop.baseOrder.enums.OrderConfirmType;
import pub.makers.shop.baseOrder.enums.OrderDeleteType;
import pub.makers.shop.tradeOrder.entity.Indent;
import pub.makers.shop.tradeOrder.vo.IndentVo;
import pub.makers.shop.tradeOrder.vo.OrderPayInfo;
import pub.makers.shop.tradeOrder.vo.ShippingInfo;

public interface IndentService extends BaseCRUDService<Indent>{
	
	/**
     * 将订单和订单商品状态更新为已支付
     * @param payInfo
     */
    void updateOrderToPayed(Indent order, OrderPayInfo payInfo);
    
    /**
     * 将订单和订单商品状态更新为已支付
     * @param payInfo
     * @param status
     */
    public void updateOrderToPayed(Indent order, OrderPayInfo payInfo, int status);
    
    
    /**
     * 将订单和订单商品状态更新为已取消
     * @param bo
     */
    void updateOrderToCancel(Indent order, OrderCancelType cancelType);
    
    
    /**
     * 将订单和订单商品更新为已确认收货
     * @param order
     * @param confirmType
     */
    void updateOrderToReceipt(Indent order, OrderConfirmType confirmType);
    
    /**
     * 将商品和订单状态更新为已发货
     * @param order
     */
    void updateOrderToShipment(Indent order, ShippingInfo si);
    
    /**
     * 
     * @param order
     */
    void saveOrder(IndentVo order);

    /**
     * 删除订单
     */
    void deleteOrder(Indent order, OrderDeleteType deleteType);

    /**
     * 提醒发货
     */
    void shipNotice(Indent order);
}
