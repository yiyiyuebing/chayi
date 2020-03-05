package pub.makers.shop.logistics.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.logistics.service.FreightTplRelService;
import pub.makers.shop.logistics.dao.FreightTplRelDao;
import pub.makers.shop.logistics.entity.FreightTplRel;

@Service
public class FreightTplRelServiceImpl extends BaseCRUDServiceImpl<FreightTplRel, String, FreightTplRelDao>
										implements FreightTplRelService{
	
}
