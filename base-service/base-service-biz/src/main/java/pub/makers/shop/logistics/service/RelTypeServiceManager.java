package pub.makers.shop.logistics.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pub.makers.shop.logistics.enums.FreightTplRelType;

@Service
public class RelTypeServiceManager {

	@Resource(name="classifyRelTypeServiceImpl")
	private FreightTplRelTypeService classifyRelTypeServiceImpl;
	
	public FreightTplRelTypeService getServiceByType(String type){
		
		FreightTplRelTypeService service = null;
		
		if (FreightTplRelType.category.name().equals(type)){
			service = classifyRelTypeServiceImpl;
		}
		
		return service;
	}
}
