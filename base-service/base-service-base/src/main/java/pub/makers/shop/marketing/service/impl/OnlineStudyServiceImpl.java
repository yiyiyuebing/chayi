package pub.makers.shop.marketing.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.marketing.dao.OnlineStudyDao;
import pub.makers.shop.marketing.entity.OnlineStudy;
import pub.makers.shop.marketing.service.OnlineStudyService;

@Service
public class OnlineStudyServiceImpl extends BaseCRUDServiceImpl<OnlineStudy, String, OnlineStudyDao>
										implements OnlineStudyService{
	
}
