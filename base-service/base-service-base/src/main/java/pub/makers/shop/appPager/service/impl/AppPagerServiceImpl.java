package pub.makers.shop.appPager.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.appPager.service.AppPagerService;
import pub.makers.shop.appPager.dao.AppPagerDao;
import pub.makers.shop.appPager.entity.AppPager;

/**
 * Created by kok on 2017/6/2.
 */
@Service
public class AppPagerServiceImpl extends BaseCRUDServiceImpl<AppPager, String, AppPagerDao> implements AppPagerService {
}
