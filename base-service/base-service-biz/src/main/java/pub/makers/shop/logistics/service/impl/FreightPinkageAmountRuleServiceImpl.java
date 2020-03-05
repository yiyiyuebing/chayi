package pub.makers.shop.logistics.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.logistics.entity.FreightPinkage;
import pub.makers.shop.logistics.service.FreightPinkageRuleService;
import pub.makers.shop.logistics.utils.RegionUtils;
import pub.makers.shop.logistics.vo.FreightVo;
import pub.makers.shop.tradeOrder.vo.IndentListVo;


/**
 * 按照金额计算的实现
 * @author jlr_6
 *
 */
@Service
public class FreightPinkageAmountRuleServiceImpl implements FreightPinkageRuleService{

	public FreightVo calcFreight(FreightPinkage rule, List<IndentListVo> ivoList, TradeContext tc) {
		
		// 匹配地区规则
		if (!RegionUtils.regionMatch(rule.getAreaIds(), tc.getRegionId())){
			return null;
		}
		
		// 匹配订单总金额
		BigDecimal totalAmount = BigDecimal.ZERO;
		for (IndentListVo ivo : ivoList){
			totalAmount = totalAmount.add(new BigDecimal(ivo.getTradeGoodAmount()));
		}
		
		// 匹配运费规则
		if (totalAmount.compareTo(new BigDecimal(rule.getFreeCondVal())) >= 0){
			
			FreightVo fvo = new FreightVo();
			fvo.setIndentList(ivoList);
			fvo.setFreeFalg(true);
			fvo.setMethodName(rule.getMethodId());
			fvo.setMethodId(rule.getMethodId());
			fvo.setServicerName(rule.getServicerName());
			fvo.setServicerId(rule.getServicerId());
			fvo.setFirstFreight(BigDecimal.ZERO);
			fvo.setIncrFreight(BigDecimal.ZERO);
			fvo.setTotalFreight(BigDecimal.ZERO);
			
			return fvo;
		}
		
		return null;
	}

}
