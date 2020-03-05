package pub.makers.shop.tradeGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.tradeGoods.dao.TradeGiftRuleDao;
import pub.makers.shop.tradeGoods.service.TradeGiftRuleService;
import pub.makers.shop.tradeGoods.entity.TradeGiftRule;

@Service
public class TradeGiftRuleServiceImpl extends BaseCRUDServiceImpl<TradeGiftRule, String, TradeGiftRuleDao>
										implements TradeGiftRuleService {
	
}
