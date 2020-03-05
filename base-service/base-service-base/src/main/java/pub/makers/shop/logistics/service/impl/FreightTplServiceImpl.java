package pub.makers.shop.logistics.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.logistics.service.FreightTplService;
import pub.makers.shop.logistics.dao.FreightTplDao;
import pub.makers.shop.logistics.entity.FreightTpl;

@Service
public class FreightTplServiceImpl extends BaseCRUDServiceImpl<FreightTpl, String, FreightTplDao>
										implements FreightTplService{
	
}
