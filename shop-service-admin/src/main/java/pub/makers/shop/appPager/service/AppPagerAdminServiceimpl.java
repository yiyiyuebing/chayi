package pub.makers.shop.appPager.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lantu.base.util.ListUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.appPager.entity.AppModuleExtra;
import pub.makers.shop.appPager.entity.AppPager;
import pub.makers.shop.appPager.entity.AppPagerModule;
import pub.makers.shop.appPager.entity.AppPagerModuleGood;
import pub.makers.shop.appPager.pojo.AppPagerPram;
import pub.makers.shop.appPager.vo.*;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.cargo.service.CargoImageBizService;
import pub.makers.shop.store.vo.ImageVo;


import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * Created by devpc on 2017/9/1.
 */
@Service(version = "1.0.0")
public class AppPagerAdminServiceimpl implements AppPagerMgrBizService {

    private Logger logger = LoggerFactory.getLogger(AppPagerAdminServiceimpl.class);

    @Autowired
    private AppPagerService appPagerService;

    @Autowired
    private AppPagerModuleService appPagerModuleService;

    @Autowired
    private AppPagerModuleGoodService appPagerModuleGoodService;

    @Autowired
    private AppModuleExtraService appModuleExtraService;

    @Reference(version = "1.0.0")
    private CargoImageBizService cargoImageBizService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询所有pager
     * @param appPagerPram
     * @param paging
     * @return
     */
    @Override
    public ResultList<AppPagerModuleVo> getAppPagerList(AppPagerPram appPagerPram,Paging paging) {

        String sql = FreeMarkerHelper.getValueFromTpl("sql/appPager/getAppPagerList.sql", appPagerPram);
        RowMapper<AppPagerModuleVo> appPagerModuleVoRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(AppPagerModuleVo.class);
        List<AppPagerModuleVo> appPagerModuleVoList = jdbcTemplate.query(sql+"LIMIT ?,?", appPagerModuleVoRowMapper,paging.getPs(), paging.getPn());

        Integer countApp = jdbcTemplate.queryForObject("SELECT COUNT(0) FROM (" +sql +") s" ,Integer.class);

        Set<String> groupIdList = ListUtils.getIdSet(appPagerModuleVoList, "imageUrl");
        List<String> iamgeIdList = Lists.newArrayList();
        iamgeIdList.addAll(groupIdList);
        if (iamgeIdList != null && !iamgeIdList.isEmpty()) {
            Map<String, ImageVo> imageVoMap = cargoImageBizService.getImageById(iamgeIdList);//获取图片
            for (AppPagerModuleVo appPagerModuleVo : appPagerModuleVoList){
                if (appPagerModuleVo.getImageUrl() != null && !"".equals(appPagerModuleVo.getImageUrl()) && imageVoMap.get(appPagerModuleVo.getImageUrl()) != null) {
                    appPagerModuleVo.setImageUrl(imageVoMap.get(appPagerModuleVo.getImageUrl()).getUrl());
                }
            }
        }

        ResultList<AppPagerModuleVo> resultList = new ResultList<AppPagerModuleVo>();
        resultList.setResultList(appPagerModuleVoList);
        resultList.setTotalRecords(countApp);
        return resultList;
    }

    @Override
    public boolean updateIsValid(AppPagerPram appPagerPram) {

        String[] ids = appPagerPram.getIds().split(",");
        for (int i = 0; i < ids.length ; i ++ ) {
            appPagerService.update(Update.byId(ids[i]).set("is_valid",appPagerPram.getIsValid()));
        }
        return true;
    }

    /**
     * 获得单个页面详细信息
     * @param appId
     * @return
     */
    @Override
    public AppPagerDetialVo selectAppPagerDetiale (String appId) {

        AppPager appPager = appPagerService.getById(appId);

        AppPagerDetialVo appPagerDetialVo = new AppPagerDetialVo(appPager);

        try {
            List<AppPagerModule> appPagerModuleList = appPagerModuleService.list(Conds.get().eq("page_id",appId).order("sort asc"));

            Set<String> groupIdList = ListUtils.getIdSet(appPagerModuleList, "id");

            List<AppModuleExtra> appModuleExtraList = appModuleExtraService.list(Conds.get().in("module_id", groupIdList));

            Set<String> imageExtrIdList = ListUtils.getIdSet(appModuleExtraList, "imageUrl");

            List<AppPagerModuleGood> appPagerModuleGoodList = appPagerModuleGoodService.list(Conds.get().in("module_id", groupIdList));

            Set<String> imageGoodsIdList = ListUtils.getIdSet(appPagerModuleGoodList, "goodsImage");//获取图片

            List<String> iamgeIdList = Lists.newArrayList();

            iamgeIdList.addAll(imageExtrIdList);
            iamgeIdList.addAll(imageGoodsIdList);
            Map<String, ImageVo> imageVoMap = Maps.newHashMap();
            if ( iamgeIdList != null && !iamgeIdList.isEmpty()){
                imageVoMap = cargoImageBizService.getImageById(iamgeIdList);
            }
            //便利集合
            appPagerModuleList = getAppPagerModuleList(appPagerModuleList, appModuleExtraList, appPagerModuleGoodList ,imageVoMap);

            if (appPagerModuleList != null && !appPagerModuleList.isEmpty()){

                appPagerDetialVo.setAppPagerModuleList(appPagerModuleList);

            }

        }catch (Exception e) {
            logger.info(e.getMessage());
        }
        return appPagerDetialVo;
    }

    /**
     * 便利方法  查询详情
     * @param appPagerModules
     * @param appModuleExtras
     * @param appPagerModuleGoods
     * @return
     */
    public List<AppPagerModule> getAppPagerModuleList ( List<AppPagerModule> appPagerModules, List<AppModuleExtra> appModuleExtras ,List<AppPagerModuleGood> appPagerModuleGoods, Map<String, ImageVo> imageVoMap) {

        if (appModuleExtras != null && !appModuleExtras.isEmpty()) {//先将图片放入集合  在便利
            for (AppModuleExtra appModuleExtra : appModuleExtras) {
                if (appModuleExtra.getImageUrl() != null && !"".equals(appModuleExtra.getImageUrl())) {
                    appModuleExtra.setImageUrl(imageVoMap.get(appModuleExtra.getImageUrl()).getUrl());
                }
            }
        }
        if (appPagerModuleGoods != null && !appPagerModuleGoods.isEmpty()) {
            for (AppPagerModuleGood appPagerModuleGood : appPagerModuleGoods) {
                if (appPagerModuleGood.getGoodsImage() != null && !"".equals(appPagerModuleGood.getGoodsImage())) {
                    appPagerModuleGood.setGoodsImage(imageVoMap.get(appPagerModuleGood.getGoodsImage()).getUrl());
                }
            }
        }

        if (appPagerModules != null && !appPagerModules.isEmpty()) {  //便利 放入集合
            for (AppPagerModule appPagerModule : appPagerModules){

                if (appModuleExtras != null && !appModuleExtras.isEmpty()) {
                    List<AppModuleExtra> appModuleExtraList = Lists.newArrayList();
                    for (AppModuleExtra appModuleExtra : appModuleExtras) {
                        if (appModuleExtra.getModuleId().equals(appPagerModule.getId())) {
                            appModuleExtraList.add(appModuleExtra);
                        }
                    }
                    appPagerModule.setAppModuleExtraList(appModuleExtraList);
                }

                if (appPagerModuleGoods != null && !appPagerModuleGoods.isEmpty()){
                    List<AppPagerModuleGood> appPagerModuleGoodList = Lists.newArrayList();
                    for (AppPagerModuleGood appPagerModuleGood : appPagerModuleGoods) {
                        if (appPagerModuleGood.getModuleId().equals(appPagerModule.getId())){
                            appPagerModuleGoodList.add(appPagerModuleGood);
                        }
                    }
                    appPagerModule.setAppPagerModuleGoodList(appPagerModuleGoodList);
                }

            }
        }
        return appPagerModules;
    }


    /**
     * 增加或修改
     * @param appPager
     * @return
     */
    @Override
    public Map<String, Object> updateOrAddAppPager(AppPager appPager) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if ( appPager.getId() != null && ! "".equals(appPager.getId())){
                appPager.setLastUpdated(new Date());
                appPagerService.update(appPager);

                if (appPager.getAppPagerModuleList() != null && !appPager.getAppPagerModuleList().isEmpty()) {
                    List<AppPagerModule> appPagerModuleList = appPagerModuleService.list(Conds.get().eq("page_id",appPager.getId()));
                    //获取ID
                    Set<String> groupIdList = ListUtils.getIdSet(appPagerModuleList, "id");
                    //先删除
                    appModuleExtraService.delete(Conds.get().in("module_id",groupIdList));
                    appPagerModuleGoodService.delete(Conds.get().in("module_id",groupIdList));
                    appPagerModuleService.delete(Conds.get().eq("page_id",appPager.getId()));
                    //在增加
                    addAppPagerSon(appPager);
                }
            }else {
                String id = IdGenerator.getDefault().nextId() + "";     //先增加主标
                Long appId = Long.parseLong(id);
                appPager.setId(appId);
                appPager.setDelFlag("F");
                appPager.setIsValid("T");
                appPager.setDateCreated(new Date());
                appPager.setLastUpdated(new Date());
                appPagerService.insert(appPager);
                /**
                 * 插入module
                 */
                 addAppPagerSon(appPager);
            }
            map.put("success",true);
        }catch (Exception e) {
            map.put("erro",e.getMessage());
        }

        return map;
    }
    /**
     * 插入module
     */
    public  Map<String, Object> addAppPagerSon (AppPager appPager) {

        Map<String, Object> map = new HashMap<String, Object>();
        Long appId = appPager.getId();

        if ( !appPager.getAppPagerModuleList().isEmpty()) {
            for (AppPagerModule appPagerModule : appPager.getAppPagerModuleList()) {
                Long modulId  = IdGenerator.getDefault().nextId() ;     //主键
                appPagerModule.setId(modulId);
                appPagerModule.setDelFlag("F");
                appPagerModule.setLastUpdated(new Date());
                appPagerModule.setDateCreated(new Date());
                appPagerModule.setPageId(appId);
                appPagerModule.setIsValid("T");
                appPagerModuleService.insert(appPagerModule);
                /**
                 * 插入详情
                 */
                if ( appPagerModule.getAppModuleExtraList() != null && !appPagerModule.getAppModuleExtraList().isEmpty()) {
                    for (AppModuleExtra appModuleExtra : appPagerModule.getAppModuleExtraList()) {
                        Long extraId = IdGenerator.getDefault().nextId() ;  //主键
                        appModuleExtra.setModuleId(modulId);
                        appModuleExtra.setDateCreated(new Date());
                        appModuleExtra.setLastUpdated(new Date());
                        appModuleExtra.setIsValid("T");
                        appModuleExtra.setDelFlag("F");
                        appModuleExtra.setId(extraId);
                        if (appModuleExtra.getImageUrl() != null && !"".equals(appModuleExtra.getImageUrl())){
                            ImageVo imageVo = new ImageVo();
                            imageVo.setPicUrl(appModuleExtra.getImageUrl());
                            String imageid = cargoImageBizService.saveImage(imageVo,"66666");
                            appModuleExtra.setImageUrl(imageid);
                        }
                        appModuleExtraService.insert(appModuleExtra);
                    }
                }
                /**
                 * 插入商品
                 */
                if ( appPagerModule.getAppPagerModuleGoodList() != null && !appPagerModule.getAppPagerModuleGoodList().isEmpty()) {
                    for (AppPagerModuleGood appPagerModuleGood : appPagerModule.getAppPagerModuleGoodList()){
                        Long goodsId =  IdGenerator.getDefault().nextId() ;  //主键
                        appPagerModuleGood.setId(goodsId);
                        appPagerModuleGood.setDelFlag("F");
                        appPagerModuleGood.setIsValid("T");
                        appPagerModuleGood.setModuleId(modulId);
                        if (appPagerModuleGood.getGoodsImage() != null && !"".equals(appPagerModuleGood.getGoodsImage())){
                            ImageVo imageVo = new ImageVo();
                            imageVo.setPicUrl(appPagerModuleGood.getGoodsImage());
                            String imageid = cargoImageBizService.saveImage(imageVo, "66666");
                            appPagerModuleGood.setGoodsImage(imageid);
                        }
                        appPagerModuleGoodService.insert(appPagerModuleGood);
                    }
                }
            }
        }
        map.put("success","success");
        return map;
    }

    /**
     * 删除主活动
     * @param appPagerPram
     * @return
     */
    @Override
    public boolean deleteAppPager(AppPagerPram appPagerPram) {
        String[] ids = appPagerPram.getIds().split(",");
        try {
            for (int i = 0; i < ids.length ; i ++ ) {
                appPagerService.update(Update.byId(ids[i]).set("del_flag","T"));

                List<AppPagerModule> appPagerModuleList = appPagerModuleService.list(Conds.get().eq("page_id",ids[i]));//删除组件
                //获取ID
                if (appPagerModuleList != null && !appPagerModuleList.isEmpty()) {
                    for (AppPagerModule appPagerModule : appPagerModuleList) {
                        appPagerModuleService.update(Update.byId(appPagerModule.getId()).set("del_flag","T"));

                        List<AppPagerModuleGood> appPagerModuleGoodList = appPagerModuleGoodService.list(Conds.get().eq("module_id",appPagerModule.getId()));//删除商品
                        if ( appPagerModuleGoodList != null &&!appPagerModuleGoodList.isEmpty()) {
                            for (AppPagerModuleGood appPagerModuleGood : appPagerModuleGoodList) {
                                appPagerModuleGoodService.update(Update.byId(appPagerModuleGood.getId()).set("del_flag","T"));
                            }
                        }

                        List<AppModuleExtra> appModuleExtraList = appModuleExtraService.list(Conds.get().eq("module_id",appPagerModule.getId()));//删除扩展组件
                        if (appModuleExtraList != null &&!appModuleExtraList.isEmpty()) {
                            for (AppModuleExtra appModuleExtra : appModuleExtraList) {
                                appModuleExtraService.update(Update.byId(appModuleExtra.getId()).set("del_flag","T"));
                            }
                        }

                    }
                }

            }

        }catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }
}
