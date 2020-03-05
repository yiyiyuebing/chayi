package pub.makers.shop.logistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lantu.base.util.ListUtils;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.logistics.dao.FreightShippingDao;
import pub.makers.shop.logistics.entity.FreightShipping;
import pub.makers.shop.logistics.entity.FreightShippingItem;
import pub.makers.shop.logistics.service.FreightShippingItemService;
import pub.makers.shop.logistics.service.FreightShippingService;

@Service
public class FreightShippingServiceImpl extends BaseCRUDServiceImpl<FreightShipping, String, FreightShippingDao>
										implements FreightShippingService{

	@Autowired
	private FreightShippingItemService itemService;
	
	public List<FreightShipping> listDetailByTplId(String tplId) {
		
		List<FreightShipping> shippingList = list(Conds.get().eq("tplId", tplId));
		List<FreightShippingItem> itemList = itemService.list(Conds.get().in("shippingId", ListUtils.getIdSet(shippingList, "shippingId")).order("sort"));
		
		ListUtils.joinMulit(shippingList, itemList, "shippingId", "shippingId", "itemList");
		
		return shippingList;
	}

	@Override
	public void delShippingByTplId(String tplId) {
		delete(Conds.get().eq("tplId", tplId));
	}


}
