package pub.makers.shop.appPager.service;

import pub.makers.shop.appPager.entity.AppPager;
import pub.makers.shop.appPager.pojo.AppPagerPram;
import pub.makers.shop.appPager.vo.AppPagerDetialVo;
import pub.makers.shop.appPager.vo.AppPagerModuleVo;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;

import java.util.Map;

/**
 * Created by devpc on 2017/9/1.
 */
public interface AppPagerMgrBizService {

    ResultList<AppPagerModuleVo> getAppPagerList (AppPagerPram appPagerPram,Paging paging) ;

    boolean updateIsValid (AppPagerPram appPagerPram);

    Map<String,Object> updateOrAddAppPager (AppPager appPager);

    boolean deleteAppPager (AppPagerPram appPagerPram) ;

    AppPagerDetialVo selectAppPagerDetiale (String appId);

}
