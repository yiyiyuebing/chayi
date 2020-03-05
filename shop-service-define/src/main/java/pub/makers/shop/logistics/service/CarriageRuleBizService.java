package pub.makers.shop.logistics.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import pub.makers.shop.tradeOrder.vo.IndentListVo;

public interface CarriageRuleBizService {

	
	/**
	 * 计算订单运费
	 * @param indentList
	 * @return
	 */
	BigDecimal calcCarriageByIndentList(Map<Long, BigDecimal> postStat, String regionId);
}
