package pub.makers.shop.marketing.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.marketing.service.ToutiaoUserRelService;
import pub.makers.shop.marketing.dao.ToutiaoUserRelDao;
import pub.makers.shop.marketing.entity.ToutiaoUserRel;

@Service
public class ToutiaoUserRelServiceImpl extends BaseCRUDServiceImpl<ToutiaoUserRel, String, ToutiaoUserRelDao>
										implements ToutiaoUserRelService{
	
}
