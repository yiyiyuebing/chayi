package pub.makers.shop.logistics.service;


import java.util.List;

import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.logistics.entity.FreightShipping;

public interface FreightShippingService extends BaseCRUDService<FreightShipping>{
	
	
	/**
	 * 根据模板ID查询配置详情
	 * @param tplId
	 * @return
	 */
	List<FreightShipping> listDetailByTplId(String tplId);


	/**
	 * 通过模版tplId删除运送方式
	 * @param tplId
	 */
	void delShippingByTplId(String tplId);


}
