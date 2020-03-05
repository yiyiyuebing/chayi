package pub.makers.shop.logistics.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.logistics.service.FreightShippingItemService;
import pub.makers.shop.logistics.dao.FreightShippingItemDao;
import pub.makers.shop.logistics.entity.FreightShippingItem;

@Service
public class FreightShippingItemServiceImpl extends BaseCRUDServiceImpl<FreightShippingItem, String, FreightShippingItemDao>
										implements FreightShippingItemService{

    @Override
    public void delShippingItemByShippingId(String shippingId) {
        delete(Conds.get().eq("shippingId", shippingId));
    }
}
