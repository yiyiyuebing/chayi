package pub.makers.shop.logistics.service;

import java.util.List;

import pub.makers.shop.logistics.entity.FreightTplRel;
import pub.makers.shop.tradeOrder.vo.IndentListVo;

/**
 * 订单商品选择器
 * @author jlr_6
 *
 */
public interface FreightTplRelTypeService {

	/**
	 * 选择订单商品
	 * @return
	 */
	List<IndentListVo> selectGood(List<IndentListVo> ivList, FreightTplRel rel);
	
	
	/**
	 * 选择采购商品
	 * @param ivList
	 * @param rel
	 * @return
	 */
	List<IndentListVo> selectPurchaseGoods(List<IndentListVo> ivList, FreightTplRel rel);
}
