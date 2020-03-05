package pub.makers.shop.base.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.base.service.VersionInfoService;
import pub.makers.shop.base.dao.VersionInfoDao;
import pub.makers.shop.base.entity.VersionInfo;

@Service
public class VersionInfoServiceImpl extends BaseCRUDServiceImpl<VersionInfo, String, VersionInfoDao>
										implements VersionInfoService{
	
}
