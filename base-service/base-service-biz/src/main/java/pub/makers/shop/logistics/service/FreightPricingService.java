package pub.makers.shop.logistics.service;

import java.util.List;

import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.logistics.entity.FreightShipping;
import pub.makers.shop.logistics.vo.FreightVo;
import pub.makers.shop.tradeOrder.vo.IndentListVo;

/**	
 * 运费计价服务
 * @author apple
 *
 */
public interface FreightPricingService {
	
	/**
	 * 计算运费金额
	 * @param ivo
	 * @param tc
	 * @return
	 */
	List<FreightVo> calcFreight(List<FreightShipping> shippingList, IndentListVo ivo, TradeContext tc);
}
