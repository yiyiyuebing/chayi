package pub.makers.shop.tradeOrder.service;


import java.util.List;

import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.tradeOrder.entity.IndentList;
import pub.makers.shop.tradeOrder.vo.IndentListVo;

public interface IndentListService extends BaseCRUDService<IndentList>{
	
	/**
	 * 保存订单列表
	 * @param voList
	 */
	void saveIndentListVo(List<IndentListVo> voList);
	
	/**
	 * 查询非赠品商品
	 * @return
	 */
	List<IndentList> getIndentListNotGift(List<Object> ids);
	
	List<IndentList> listByOrderId(String orderId);
}
