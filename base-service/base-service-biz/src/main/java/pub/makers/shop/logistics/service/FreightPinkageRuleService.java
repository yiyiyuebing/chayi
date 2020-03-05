package pub.makers.shop.logistics.service;

import java.util.List;

import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.logistics.entity.FreightPinkage;
import pub.makers.shop.logistics.vo.FreightVo;
import pub.makers.shop.tradeOrder.vo.IndentListVo;

/**
 * 包邮规则服务
 * @author jlr_6
 *
 */
public interface FreightPinkageRuleService {

	FreightVo calcFreight(FreightPinkage rule, List<IndentListVo> ivoList, TradeContext tc);
}
