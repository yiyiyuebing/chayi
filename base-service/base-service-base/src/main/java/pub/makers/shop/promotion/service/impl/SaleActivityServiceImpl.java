package pub.makers.shop.promotion.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.common.entity.BoolType;
import com.lantu.base.util.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.promotion.dao.SaleActivityDao;
import pub.makers.shop.promotion.dao.SaleActivityGoodDao;
import pub.makers.shop.promotion.entity.SaleActivity;
import pub.makers.shop.promotion.entity.SaleActivityGood;
import pub.makers.shop.promotion.service.SaleActivityService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SaleActivityServiceImpl extends BaseCRUDServiceImpl<SaleActivity, String, SaleActivityDao>
										implements SaleActivityService{

	@Autowired
	private SaleActivityGoodDao goodDao;
	
	@Override
	public List<SaleActivityGood> filterAvailableSaleGood(Set<String> goodSkuIdSet) {
		
		Date now = new Date();
		/**
		 *  查询出生效的活动
		 *  时间在有效期内
		 *  活动范围为指定商品
		 */
		List<SaleActivity> activityList = list(Conds.get().eq("is_valid", BoolType.T.name()).eq("del_flag", BoolType.F.name()).lt("start_time", now).gt("end_time", now));
		
		// 查询这些活动的商品列表
		List<SaleActivityGood> goodList = goodDao.list(Conds.get().in("activity_id", ListUtils.getIdSet(activityList, "id")).in("good_sku_id", goodSkuIdSet).order("date_created asc"));
		
		// 过滤重复数据
		Map<String, SaleActivityGood> goodMap = Maps.newLinkedHashMap();
		for (SaleActivityGood good : goodList){
			goodMap.put(good.getGoodSkuId(), good);
		}
		goodList = Lists.newArrayList(goodMap.values());
		ListUtils.join(goodList, activityList, "activityId", "id", "activity");
		
		return goodList;
	}
	
}
