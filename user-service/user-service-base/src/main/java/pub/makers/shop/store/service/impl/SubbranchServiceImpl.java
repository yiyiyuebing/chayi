package pub.makers.shop.store.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lantu.base.util.ListUtils;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.store.dao.SubbranchDao;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.service.SubbranchService;

@Service
public class SubbranchServiceImpl extends BaseCRUDServiceImpl<Subbranch, String, SubbranchDao>
										implements SubbranchService{

	public Subbranch getByMobile(String mobile) {
		
		List<Subbranch> shopList = dao.findBySql("select * from store_subbranch where mobile = ? and (del_flag <> 'T' or del_flag is null)", mobile);
		
		return ListUtils.getSingle(shopList);
	}

	public Subbranch getByPhone(String phone) {
		
		List<Subbranch> shopList = dao.findBySql("select * from store_subbranch where phone = ? and (del_flag <> 'T' or del_flag is null)", phone);
		
		return ListUtils.getSingle(shopList);
	}
	
}
