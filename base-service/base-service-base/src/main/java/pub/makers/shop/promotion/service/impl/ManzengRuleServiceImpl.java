package pub.makers.shop.promotion.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.promotion.dao.ManzengRuleDao;
import pub.makers.shop.promotion.entity.ManzengRule;
import pub.makers.shop.promotion.service.ManzengRuleService;

/**
* Created by dy on 2017/8/17.
*/
@Service
public class ManzengRuleServiceImpl extends BaseCRUDServiceImpl<ManzengRule, String, ManzengRuleDao> implements ManzengRuleService {
}
