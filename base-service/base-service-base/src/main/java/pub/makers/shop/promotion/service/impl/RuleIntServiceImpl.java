package pub.makers.shop.promotion.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.promotion.dao.RuleIntDao;
import pub.makers.shop.promotion.entity.RuleInt;
import pub.makers.shop.promotion.service.RuleIntService;

@Service
public class RuleIntServiceImpl extends BaseCRUDServiceImpl<RuleInt, String, RuleIntDao>
										implements RuleIntService{
	
}
