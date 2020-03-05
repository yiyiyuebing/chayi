package pub.makers.shop.logistics.service;


import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.logistics.entity.FreightShippingItem;

public interface FreightShippingItemService extends BaseCRUDService<FreightShippingItem>{

    /**
     * 通过运送方式shippingId删除运送详情
     * @param shippingId
     */
    void delShippingItemByShippingId(String shippingId);
}
