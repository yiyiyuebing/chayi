package pub.makers.shop.appPager.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.appPager.dao.AppPagerDao;
import pub.makers.shop.appPager.dao.AppPagerModuleDao;
import pub.makers.shop.appPager.entity.AppPager;
import pub.makers.shop.appPager.entity.AppPagerModule;
import pub.makers.shop.appPager.service.AppPagerModuleService;
import pub.makers.shop.appPager.service.AppPagerService;

/**
 * Created by kok on 2017/6/2.
 */
@Service
public class AppPagerModuleServiceImpl extends BaseCRUDServiceImpl<AppPagerModule, String, AppPagerModuleDao> implements AppPagerModuleService {
}
