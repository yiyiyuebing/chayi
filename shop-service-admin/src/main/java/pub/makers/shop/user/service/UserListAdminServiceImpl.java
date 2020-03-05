package pub.makers.shop.user.service;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.service.SysDictService;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.store.entity.GoodReceiptAddr;
import pub.makers.shop.store.entity.StoreLevel;
import pub.makers.shop.store.entity.StoreSubbranchExt;
import pub.makers.shop.store.entity.VtwoStoreRole;
import pub.makers.shop.store.service.SubbranchExtService;
import pub.makers.shop.store.vo.StoreSubbranceExtVo;
import pub.makers.shop.store.vo.SubbranchVo;
import pub.makers.shop.store.vo.TjStoreSubbranchVo;
import pub.makers.shop.user.vo.UserHomeStoreVo;
import pub.makers.shop.user.vo.UserListPurchaseParam;
import pub.makers.shop.user.vo.UserListTradeParam;
import pub.makers.shop.user.vo.UserListTradeVo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by daiwenfa on 2017/7/19.
 */
@Service(version="1.0.0")
public class UserListAdminServiceImpl implements UserListAdminService{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private GoodReceiptAddrService goodReceiptAddrService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private SubbranchExtService subbranchExtService;
    @Override
    public ResultList<UserListTradeVo> getUserListTradePageList(UserListTradeParam param, Paging pg) {
        param.setProvince((param.getProvince()==null?"":param.getProvince())+(param.getCity()==null?"":param.getCity())+(param.getArea()==null?"":param.getArea()));
        String sql = FreeMarkerHelper.getValueFromTpl("sql/user/queryUserListTradeList.sql", param);
        RowMapper<UserListTradeVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(UserListTradeVo.class);
        List<UserListTradeVo> list = jdbcTemplate.query(sql + " limit ?,? ", rowMapper, pg.getPs(), pg.getPn());
        Number total = jdbcTemplate.queryForObject("select count(0) from (" + sql +") nums ",null,Integer.class);
        ResultList<UserListTradeVo> resultList = new ResultList<UserListTradeVo>();
        resultList.setResultList(list);
        resultList.setTotalRecords(total != null ? total.intValue() : 0);
        return resultList;
    }

    @Override
    public UserListTradeVo getUserData(String id) {
        if(StringUtils.isNotBlank(id)) {
            String sql = FreeMarkerHelper.getValueFromTpl("sql/user/getTradeUserData.sql", null);
            RowMapper<UserListTradeVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(UserListTradeVo.class);
            List<UserListTradeVo> list = jdbcTemplate.query(sql, rowMapper, id);
            UserListTradeVo userData = list.get(0);
            List<GoodReceiptAddr> goodReceiptAddrList = goodReceiptAddrService.list(Conds.get().eq("user_id", id));
            userData.setGoodReceiptAddrList(goodReceiptAddrList);
            String sqls = FreeMarkerHelper.getValueFromTpl("sql/user/getUserHomeStoreList.sql", null);
            RowMapper<UserHomeStoreVo> rowMappers = ParameterizedBeanPropertyRowMapper.newInstance(UserHomeStoreVo.class);
            List<UserHomeStoreVo> userHomeStoreList = jdbcTemplate.query(sqls, rowMappers, id);
            userData.setUserHomeStoreList(userHomeStoreList);
            if(StringUtils.isNotBlank(userData.getLabel())) {
                String[] labelIds = userData.getLabel().split(",");
                List<String> list1 = Arrays.asList(labelIds);
                List<SysDict> sysDictsList = sysDictService.list(Conds.get().eq("dict_type", "label").in("dict_id", list1));
                userData.setSysDictsList(sysDictsList);
            }
            return userData;
        }
        return null;
    }

    @Override
    public ResultList<TjStoreSubbranchVo> getUserListPurchasePageList(UserListPurchaseParam param, Paging pg) {
        param.setProvince((param.getProvince()==null?"":param.getProvince())+(param.getCity()==null?"":param.getCity())+(param.getArea()==null?"":param.getArea()));
        String sql = FreeMarkerHelper.getValueFromTpl("sql/user/queryUserListPurchaseList.sql", param);
        RowMapper<TjStoreSubbranchVo> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(TjStoreSubbranchVo.class);
        List<TjStoreSubbranchVo> list = jdbcTemplate.query(sql + " limit ?,? ", rowMapper, pg.getPs(), pg.getPn());
        Number total = jdbcTemplate.queryForObject("select count(0) from (" + sql +") nums ",null,Integer.class);
        ResultList<TjStoreSubbranchVo> resultList = new ResultList<TjStoreSubbranchVo>();
        resultList.setResultList(list);
        resultList.setTotalRecords(total!=null?total.intValue():0);
        return resultList;
    }

    @Override
    public StoreSubbranceExtVo getStoreSubbranchExt(String id) {
        StoreSubbranchExt storeSubbranchExt = subbranchExtService.get(Conds.get().eq("store_id",id));
        StoreSubbranceExtVo storeSubbranceExtVo = new StoreSubbranceExtVo();
        if(storeSubbranchExt!=null) {
            storeSubbranceExtVo.setRemark(storeSubbranchExt.getRemark());
            if (StringUtils.isNotBlank(storeSubbranchExt.getLabel())) {
                String[] labelIds = storeSubbranchExt.getLabel().split(",");
                List<String> list1 = Arrays.asList(labelIds);
                List<SysDict> sysDictsList = sysDictService.list(Conds.get().eq("dict_type", "label").in("dict_id", list1));
                storeSubbranceExtVo.setSysDictsList(sysDictsList);
            }
        }
        return storeSubbranceExtVo;
    }

    @Override
    public void addOrUpdateStoreSubbranchExt(StoreSubbranchExt storeSubbranchExt, long userId) {
        StoreSubbranchExt sse = subbranchExtService.get(Conds.get().eq("store_id",storeSubbranchExt.getStoreId()));
        if(sse==null){
            storeSubbranchExt.setId(IdGenerator.getDefault().nextId());
            storeSubbranchExt.setDateCreated(new Date());
            storeSubbranchExt.setLastUpdated(new Date());
            storeSubbranchExt.setIsValid("T");
            storeSubbranchExt.setDelFlag("F");
            subbranchExtService.insert(storeSubbranchExt);
        }else{
            storeSubbranchExt.setId(sse.getId());
            storeSubbranchExt.setLastUpdated(new Date());
            subbranchExtService.update(storeSubbranchExt);
        }
    }

    @Override
    public SubbranchVo getData(SubbranchVo sv) {
        VtwoStoreRole vsr = null;
        StoreLevel sl = null;
        try {
            vsr = jdbcTemplate.queryForObject("select * from vtwo_store_role vsr where vsr.id = ? ", ParameterizedBeanPropertyRowMapper.newInstance(VtwoStoreRole.class), sv.getId());
        }catch (Exception e){

        }
        try {
            sl = jdbcTemplate.queryForObject("select * from store_level sl where sl.level_id = ? ", ParameterizedBeanPropertyRowMapper.newInstance(StoreLevel.class), sv.getLevelId());
        }catch (Exception e){

        }
        if(vsr!=null) {
            sv.setConcatName(vsr.getConcatName());
            sv.setConcatPhone(vsr.getConcatPhone());
        }
        if(sl!=null) {
            sv.setLevelName(sl.getName());
        }
        return sv;
    }
}
