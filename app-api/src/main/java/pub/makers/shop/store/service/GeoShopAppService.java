package pub.makers.shop.store.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.entity.GeoShopInfo;

@Service
public class GeoShopAppService {

	
	@Reference(version="1.0.0")
	private GeoShopBizService geoShopBizService;
	
	public GeoShopInfo addShop(GeoShopInfo shopInfo){
		
		return geoShopBizService.addShop(shopInfo);
	}
	
	public List<GeoShopInfo> findNearBy(Double lng, Double lat, Integer distance){
		
		return geoShopBizService.findNearBy(lng, lat, distance);
	}
	
	public List<GeoShopInfo> searchFromMongoBy(Double lng, Double lat, Double distance,Integer limitNum){
		
		return geoShopBizService.searchFromMongoBy(lng, lat, distance,limitNum);
	}
	
	public GeoShopInfo setDiscussingAndTimeBy(String id,String mainUser){
		
		return geoShopBizService.setDiscussingAndTimeBy(id,mainUser);
	}
	
	public GeoShopInfo setDiscussedStatusAndTimeBy(String id,String storeId){
		
		return geoShopBizService.setDiscussedStatusAndTimeBy(id,storeId);
	}
}
