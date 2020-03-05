package pub.makers.shop.index.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.index.service.IndentMobileModuleService;
import pub.makers.shop.index.dao.IndentMobileModuleDao;
import pub.makers.shop.index.entity.IndentMobileModule;

@Service
public class IndentMobileModuleServiceImpl extends BaseCRUDServiceImpl<IndentMobileModule, String, IndentMobileModuleDao>
										implements IndentMobileModuleService{
	
}
