package pub.makers.shop.base.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.base.dao.SysDictDao;
import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.service.SysDictService;

@Service
public class SysDictServiceImpl extends BaseCRUDServiceImpl<SysDict,Long,SysDictDao>
										implements SysDictService{
	
}

