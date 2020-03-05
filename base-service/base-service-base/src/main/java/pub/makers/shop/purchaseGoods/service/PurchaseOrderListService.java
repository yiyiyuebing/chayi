package pub.makers.shop.purchaseGoods.service;

import java.util.List;

import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrderList;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;

/**
 * Created by dy on 2017/4/14.
 */
public interface PurchaseOrderListService extends BaseCRUDService<PurchaseOrderList> {

    /**
     * 保存订单商品列表
     * @param purchaseOrderListVos
     */
    void savePurchaseOrderListVo(List<PurchaseOrderListVo> purchaseOrderListVos);
    
    List<PurchaseOrderList> listByOrderId(String orderId);
}
