package pub.makers.shop.purchaseGoods.service.impl;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.purchaseGoods.dao.PurchaseOrderListDao;
import pub.makers.shop.purchaseGoods.service.PurchaseOrderListService;
import pub.makers.shop.purchaseOrder.entity.PurchaseOrderList;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;

import java.util.List;

/**
 * Created by dy on 2017/4/14.
 */
@Service
public class PurchaseOrderListServiceImpl extends BaseCRUDServiceImpl<PurchaseOrderList, String, PurchaseOrderListDao> implements PurchaseOrderListService {
    @Override
    public void savePurchaseOrderListVo(List<PurchaseOrderListVo> purchaseOrderListVos) {
        List<PurchaseOrderList> purchaseOrderLists = Lists.newArrayList();
        for (PurchaseOrderListVo purchaseOrderListVo : purchaseOrderListVos) {
            purchaseOrderLists.add(PurchaseOrderList.fromPurchaseOrderListVo(purchaseOrderListVo));
        }
        dao.batchInsert(purchaseOrderLists);
    }

	@Override
	public List<PurchaseOrderList> listByOrderId(String orderId) {
		
		return list(Conds.get().eq("orderId", orderId));
	}
}
