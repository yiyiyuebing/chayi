package pub.makers.shop.logistics.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.logistics.service.CarriageRuleService;
import pub.makers.shop.logistics.dao.CarriageRuleDao;
import pub.makers.shop.logistics.entity.CarriageRule;

@Service
public class CarriageRuleServiceImpl extends BaseCRUDServiceImpl<CarriageRule, String, CarriageRuleDao>
										implements CarriageRuleService{
	
}
