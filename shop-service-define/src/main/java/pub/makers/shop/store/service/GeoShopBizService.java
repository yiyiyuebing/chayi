package pub.makers.shop.store.service;

import java.util.List;

import pub.makers.shop.store.entity.GeoShopInfo;

public interface GeoShopBizService {

	/**
	 * 添加店铺信息
	 * @param shopInfo
	 * @return
	 */
	GeoShopInfo addShop(GeoShopInfo shopInfo);
	
	
	/**
	 * 编辑店铺信息
	 * @param shopInfo
	 * @return
	 */
	GeoShopInfo editShop(GeoShopInfo shopInfo);
	
	
	/**
	 * 查找附近的店铺
	 * @param lat
	 * @param lng
	 * @return
	 */ 
	List<GeoShopInfo> findNearBy(Double lat, Double lng, Integer distance);


	List<GeoShopInfo> searchFromMongoBy(Double lng, Double lat, Double distance,Integer limitNum);
	
	/**
	 * 标记店铺洽谈中状态以及时间
	 * @param discussStatus
	 * @param id,mainUser
	 * @return 
	 */
	
	GeoShopInfo setDiscussingAndTimeBy(String id,String mainUser);
	
	/**
	 * 标记店铺已洽谈状态以及时间
	 * @param discussStatus
	 * @param id,storeId
	 * @return 
	 */
	
	GeoShopInfo setDiscussedStatusAndTimeBy(String id,String storeId);
}
