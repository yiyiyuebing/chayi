package pub.makers.shop.store.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.store.service.BankCardService;
import pub.makers.shop.store.dao.BankCardDao;
import pub.makers.shop.store.entity.BankCard;

@Service
public class BankCardServiceImpl extends BaseCRUDServiceImpl<BankCard, String, BankCardDao>
										implements BankCardService{

	@Override
	public BankCard getByStoreId(String storeId) {
		
		return get(Conds.get().eq("connect_id", storeId));
	}
	
}
