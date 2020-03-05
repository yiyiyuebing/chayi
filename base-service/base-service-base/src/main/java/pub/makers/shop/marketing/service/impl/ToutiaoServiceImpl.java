package pub.makers.shop.marketing.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.marketing.dao.ToutiaoDao;
import pub.makers.shop.marketing.entity.Toutiao;
import pub.makers.shop.marketing.service.ToutiaoService;

@Service
public class ToutiaoServiceImpl extends BaseCRUDServiceImpl<Toutiao, String, ToutiaoDao>
										implements ToutiaoService{
	
}
