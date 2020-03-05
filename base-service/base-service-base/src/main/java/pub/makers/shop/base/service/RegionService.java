package pub.makers.shop.base.service;


import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.base.entity.Region;

public interface RegionService extends BaseCRUDService<Region>{
	
	Region getByCityAndRegionName(String cityName, String regionName);
}
