package pub.makers.shop.logistics.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pub.makers.shop.logistics.enums.FreightTplPriceType;

@Service
public class FreightServiceManager {

	@Resource(name="pieceFreightPricingServiceImpl")
	private FreightPricingService pieceFreightPricingService;
	
	@Resource(name="freightPinkageAmountRuleServiceImpl")
	private FreightPinkageRuleService amountFreightPinkageRuleService;
	
	public FreightPricingService getPricingService(String type){
		
		FreightPricingService service = null;
		if (FreightTplPriceType.piece.name().equals(type)){
			service = pieceFreightPricingService;
		}
		
		return service;
	}
	
	public FreightPinkageRuleService getPinkageRuleService(String type){
		
		FreightPinkageRuleService service = null;
		
		if (FreightTplPriceType.amount.name().equals(type)){
			service = amountFreightPinkageRuleService;
		}
		return service;
	}
}
