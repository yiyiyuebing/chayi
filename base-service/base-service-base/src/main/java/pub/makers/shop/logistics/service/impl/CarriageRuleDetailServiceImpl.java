package pub.makers.shop.logistics.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.logistics.service.CarriageRuleDetailService;
import pub.makers.shop.logistics.dao.CarriageRuleDetailDao;
import pub.makers.shop.logistics.entity.CarriageRuleDetail;

@Service
public class CarriageRuleDetailServiceImpl extends BaseCRUDServiceImpl<CarriageRuleDetail, String, CarriageRuleDetailDao>
										implements CarriageRuleDetailService{
	
}
