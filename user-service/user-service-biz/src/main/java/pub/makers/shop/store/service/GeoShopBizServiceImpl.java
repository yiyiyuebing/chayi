package pub.makers.shop.store.service;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoResult;
import org.springframework.data.mongodb.core.geo.GeoResults;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.store.entity.GeoShopInfo;

@Service(version="1.0.0")
public class GeoShopBizServiceImpl implements GeoShopBizService{

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public GeoShopInfo addShop(GeoShopInfo shopInfo) {
		
		mongoTemplate.insert(shopInfo, "geo_shop_info");
		return shopInfo;
	}

	@Override
	public GeoShopInfo editShop(GeoShopInfo shopInfo) {
		
		mongoTemplate.save(shopInfo, "geo_shop_info");
		return shopInfo;
	}

	@Override
	public List<GeoShopInfo> findNearBy(Double lat, Double lng, Integer distance) {
		
		Map<String, Object> dataModel = Maps.newHashMap();
		dataModel.put("lat", lat);
		dataModel.put("lng", lng);
		dataModel.put("distance", distance);
		
		String shopInfo = FreeMarkerHelper.getValueFromTpl("sql/subbranch/queryOthersAroundShop.sql", dataModel);
		
		List<GeoShopInfo> shopInfoList = jdbcTemplate.query(shopInfo, new BeanPropertyRowMapper<GeoShopInfo>(GeoShopInfo.class));
		
		return shopInfoList;
	}
	
	/*@Override
	public List<GeoShopInfo> searchFromMongoBy(Double lng, Double lat, Integer distance) {

		NearQuery query = NearQuery.near(lat, lng);
		
		query.distanceMultiplier(6378137);
		query.maxDistance(distance/6378137.0);
//		query.maxDistance(6378137);
		query.num(10);
		query.spherical(true);
		
		GeoResults<GeoShopInfo> result = mongoTemplate.geoNear(query, GeoShopInfo.class, "geo_shop_info");
		
		List<GeoShopInfo> resultList = Lists.newArrayList();
		for (GeoResult<GeoShopInfo> info : result){
			GeoShopInfo shopInfo = info.getContent();
			shopInfo.setDistance(info.getDistance().getValue());
			resultList.add(shopInfo);
		}
		
		return resultList;
	}	*/
	
	public List<GeoShopInfo> searchFromMongoBy(Double lng, Double lat, Double distance,Integer limitNum) {
		
		Point point = new Point(lng, lat);
		Query query = new Query(Criteria.where("loc").near(point).maxDistance(distance));
		query.limit(limitNum);
		//query.spherical(true);

		List<GeoShopInfo> resultList = mongoTemplate.find(query, GeoShopInfo.class, "geo_shop_info");
		       
		       
		return resultList;
	}
	
	
	/*public void setDiscussStatusAndTime(String status, String id) throws Exception {

		Mongo mongo = new Mongo("123.207.216.234", 27017);
		DB db = mongo.getDB("youchalian");
		DBCollection collection = db.getCollection("geo_shop_info_copy");

		Date now = new Date();
		DateFormat tim = DateFormat.getInstance();
		String time = tim.format(now);

		Query query = new Query((Criteria.where("_id").gt(id)));

		query.addCriteria(where("_id").gt(id));
		Criteria criteria = where("age").gt(22);
		query.addCriteria(criteria);

		Update update = new Update();
		update.set("discussStatus", "status");
		update.set("firstDisTime", "time");
	}*/
	 
	
	public GeoShopInfo setDiscussingAndTimeBy(String id,String mainUser) {
			
		Criteria criteria = new Criteria() ;
		criteria.and("_id").is(id) ;
		Query query = new Query();
		query.addCriteria(criteria);
		
		Update update = new Update();
		update.set("discussStatus", "洽谈中");
		update.set("firstDisTime", new Date());
		update.set("mainUser", mainUser);
		
		GeoShopInfo result = mongoTemplate.findAndModify(query,update,GeoShopInfo.class, "geo_shop_info");
		return result;
	}
	
	public GeoShopInfo setDiscussedStatusAndTimeBy(String id,String storeId) {
		
		Criteria criteria = new Criteria() ;
		criteria.and("_id").is(id) ;
		Query query = new Query();
		query.addCriteria(criteria);
		
		Update update = new Update();
		update.set("discussStatus", "已洽谈");
		update.set("cooperationTime", new Date());
		update.set("storeId",storeId);
		
		GeoShopInfo result = mongoTemplate.findAndModify(query,update,GeoShopInfo.class, "geo_shop_info");
		return result;
	}

}
	
