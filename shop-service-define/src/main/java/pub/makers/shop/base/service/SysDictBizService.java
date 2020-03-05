package pub.makers.shop.base.service;

import pub.makers.shop.base.entity.SysDict;

import java.util.List;

public interface SysDictBizService {
	
	//列表查询
	List<SysDict> list(String conditions,int start,int limit);

	/**
	 * 字典列表
	 */
	List<SysDict> list(String dictTyp, String code);
	
	void deleteById(String id);
}
