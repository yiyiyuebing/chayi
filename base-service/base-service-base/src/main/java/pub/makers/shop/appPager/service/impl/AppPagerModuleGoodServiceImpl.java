package pub.makers.shop.appPager.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.appPager.dao.AppPagerModuleDao;
import pub.makers.shop.appPager.dao.AppPagerModuleGoodDao;
import pub.makers.shop.appPager.entity.AppPagerModule;
import pub.makers.shop.appPager.entity.AppPagerModuleGood;
import pub.makers.shop.appPager.service.AppPagerModuleGoodService;
import pub.makers.shop.appPager.service.AppPagerModuleService;

/**
 * Created by kok on 2017/6/2.
 */
@Service
public class AppPagerModuleGoodServiceImpl extends BaseCRUDServiceImpl<AppPagerModuleGood, String, AppPagerModuleGoodDao> implements AppPagerModuleGoodService {
}
