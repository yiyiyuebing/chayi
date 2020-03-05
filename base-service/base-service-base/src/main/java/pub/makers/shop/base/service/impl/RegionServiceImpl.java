package pub.makers.shop.base.service.impl;

import java.util.List;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lantu.base.util.ListUtils;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.base.dao.RegionDao;
import pub.makers.shop.base.entity.Region;
import pub.makers.shop.base.service.RegionService;

@Service
public class RegionServiceImpl extends BaseCRUDServiceImpl<Region, String, RegionDao>
										implements RegionService{

	@Override
	public Region getByCityAndRegionName(String cityName, String regionName) {

		List<Region> regionList = Lists.newArrayList();

		if (StringUtils.isNoneBlank(regionName)) {
			regionList = dao.findBySql("select * from region where name = ? and pid = (select id from region where name = ?)", regionName, cityName);
		}
		if (regionList.isEmpty()) {
			regionList = dao.findBySql("select * from region where name = ?", cityName);
		}

		return ListUtils.getSingle(regionList);
	}
	
}
