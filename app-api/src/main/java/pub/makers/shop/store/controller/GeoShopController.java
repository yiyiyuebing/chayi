package pub.makers.shop.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.base.json.JsonUtils;

import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.entity.GeoShopInfo;
import pub.makers.shop.store.service.GeoShopAppService;

@Controller
@RequestMapping("geoshop")
public class GeoShopController {

	@Autowired
	private GeoShopAppService shopAppService;
	
	@RequestMapping("addShop")
	public ResultData addShop(String shopJson){
		
		GeoShopInfo shopInfo = JsonUtils.toObject(shopJson, GeoShopInfo.class);
		GeoShopInfo result = shopAppService.addShop(shopInfo);
		
		return ResultData.createSuccess(result);
	}
	
	
	@RequestMapping("findNearBy")
	@ResponseBody
	public ResultData findNearBy(double lng, double lat, Integer distance){
		
		List<GeoShopInfo> shopList = shopAppService.findNearBy(lng, lat, distance);
		
		return ResultData.createSuccess(shopList);
	}
	
	@RequestMapping("searchFromMongoBy")
	@ResponseBody
	public ResultData searchFromMongoBy(Double lng, Double lat, Double distance,Integer limitNum){
		
		List<GeoShopInfo> resultList = shopAppService.searchFromMongoBy(lng, lat, distance,limitNum);
		
		return ResultData.createSuccess(resultList);
	}
	
	@RequestMapping("updateDiscussing")
	@ResponseBody
	public ResultData setDiscussingAndTimeBy(String id,String mainUser){
		
		GeoShopInfo result = shopAppService.setDiscussingAndTimeBy(id,mainUser);
		
		return ResultData.createSuccess(result);
		
	}
	
	@RequestMapping("updateDiscussed")
	@ResponseBody
	public ResultData setDiscussedStatusAndTimeBy(String id,String storeId){
		
		GeoShopInfo result = shopAppService.setDiscussedStatusAndTimeBy(id,storeId);
		
		return ResultData.createSuccess(result);
		
	}
}
