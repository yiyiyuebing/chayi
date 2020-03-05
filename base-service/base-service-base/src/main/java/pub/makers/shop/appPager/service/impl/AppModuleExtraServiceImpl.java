package pub.makers.shop.appPager.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.appPager.dao.AppModuleExtraDao;
import pub.makers.shop.appPager.dao.AppPagerDao;
import pub.makers.shop.appPager.entity.AppModuleExtra;
import pub.makers.shop.appPager.entity.AppPager;
import pub.makers.shop.appPager.service.AppModuleExtraService;
import pub.makers.shop.appPager.service.AppPagerService;

/**
 * Created by kok on 2017/6/2.
 */
@Service
public class AppModuleExtraServiceImpl extends BaseCRUDServiceImpl<AppModuleExtra, String, AppModuleExtraDao> implements AppModuleExtraService {
}
