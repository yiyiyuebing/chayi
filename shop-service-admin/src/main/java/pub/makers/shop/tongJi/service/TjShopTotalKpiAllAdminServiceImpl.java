package pub.makers.shop.tongJi.service;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.baseOrder.enums.OrderBizType;


import pub.makers.shop.cargo.entity.vo.CargoVo;
import pub.makers.shop.cargo.service.CargoImageBizService;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.tongji.entity.TjShopTotalKpi;
import pub.makers.shop.tongji.pojo.TjShopTotalKpiParam;
import pub.makers.shop.tongji.service.TjShopTotalKpiBizService;
import pub.makers.shop.tongji.service.TjShopTotalKpiMgrBizService;
import pub.makers.shop.tongji.vo.TopTenGoodsTotalKpi;
import pub.makers.shop.tongji.vo.TopTenShopTotalKpi;



import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by devpc on 2017/8/11.
 */
@Service(version = "1.0.0")
public class TjShopTotalKpiAllAdminServiceImpl implements TjShopTotalKpiMgrBizService {

    private Logger logger = LoggerFactory.getLogger(TjShopTotalKpiAllAdminServiceImpl.class);
    @Reference(version = "1.0.0")
    private TjShopTotalKpiBizService tjShopTotalKpiBizService;

    @Reference(version = "1.0.0")
    private CargoImageBizService cargoImageBizService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 实时指标
     * @param orderBizType
     * @return
     */
    @Override
    public Map<String, Object> selectTodayTotalKpi(OrderBizType orderBizType) {

        tjShopTotalKpiBizService.selectTodayTotalKpi(orderBizType);

        TjShopTotalKpiParam tjShopTotalKpiParam = new TjShopTotalKpiParam();  //传入参数

        tjShopTotalKpiParam.setPurchseOrTrade(getTjShopParam(orderBizType));                //传入判断是哪张表的参数
        Date date = new Date();
        SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd");
        String creatDate = matter1.format(date);
        tjShopTotalKpiParam.setStartDate(creatDate);
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            String totalKpiSql = FreeMarkerHelper.getValueFromTpl("sql/tjShop/queryTradeOrPurchaseTjKpi.sql", tjShopTotalKpiParam);
            RowMapper<TjShopTotalKpi> tjShopTotalKpiRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(TjShopTotalKpi.class);
            List<TjShopTotalKpi> tjShopTotalKpi = jdbcTemplate.query(totalKpiSql, tjShopTotalKpiRowMapper); //返回实时指标
            if (!tjShopTotalKpi.isEmpty()){
                map.put("result",tjShopTotalKpi.get(0));
            }else
                map.put("result","null");
        }catch (Exception e){
            logger.toString();
            map.put("error",e.getMessage());
        }
        return map;
    }

    /**
     * 核心指标
     * @param orderBizType
     * @return
     */
    @Override
    public Map<String, Object> selectSumTotalKpiByTime(OrderBizType orderBizType,TjShopTotalKpiParam tjShopTotalKpiParam) {

        tjShopTotalKpiParam.setPurchseOrTrade(getTjShopParam(orderBizType));                //传入判断是哪张表的参数
        Date date = new Date();
        SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd");
        String creatDate = matter1.format(date);

        Map<String,Object> map = new HashMap<String,Object>();
        try {
            Date pramDate = matter1.parse(tjShopTotalKpiParam.getStartDate());
            tjShopTotalKpiParam.setStartDate(matter1.format(pramDate));

            if (creatDate.equals(tjShopTotalKpiParam.getStartDate())) {
                map = selectTodayTotalKpi(orderBizType);
            } else {
                String totalKpiSql = FreeMarkerHelper.getValueFromTpl("sql/tjShop/querySumTjKpiDetial.sql", tjShopTotalKpiParam);
                RowMapper<TjShopTotalKpi> tjShopTotalKpiRowMapper = ParameterizedBeanPropertyRowMapper.newInstance(TjShopTotalKpi.class);
                List<TjShopTotalKpi> tjShopTotalKpi = jdbcTemplate.query(totalKpiSql, tjShopTotalKpiRowMapper); //返回实时指标
                if (!tjShopTotalKpi.isEmpty()){
                    map.put("result",tjShopTotalKpi.get(0));
                }else
                    map.put("result","null");
            }
        }catch (Exception e){
            logger.toString();
            map.put("error",e.getMessage());
        }
        return map;
    }

    /**
     * 商品，商家    Top10
     * @param orderBizType
     * @return
     */
    @Override
    public Map<String, Object> selectTopTenTotalKpi(OrderBizType orderBizType) {
        TjShopTotalKpiParam tjShopTotalKpiParam = new TjShopTotalKpiParam();  //传入参数              /**改改***/

        tjShopTotalKpiParam.setPurchseOrTrade(getTjShopParam(orderBizType));
        Map<String,Object> map = new HashMap<String,Object>();

        try {
            RowMapper<TopTenGoodsTotalKpi> getTopTen = ParameterizedBeanPropertyRowMapper.newInstance(TopTenGoodsTotalKpi.class);
            String getGoodsSql = FreeMarkerHelper.getValueFromTpl("sql/tjShop/queryTop10TotalKpiGood.sql",tjShopTotalKpiParam);
            List<TopTenGoodsTotalKpi> goodsList = jdbcTemplate.query(getGoodsSql,getTopTen);    //得到集合

            List<String> groupIdList = new ArrayList<>();
            for (TopTenGoodsTotalKpi topTenGoodsTotalKpi : goodsList) {
                groupIdList.add(topTenGoodsTotalKpi.getGroupId());
            }

            Map<String, ImageVo> mapImage = cargoImageBizService.getImageByGroup(groupIdList);//得到图片
            List<CargoVo> cargoVos = Lists.newArrayList();
            for (TopTenGoodsTotalKpi topTenGoodsTotalKpi : goodsList) {
                ImageVo imageVo = mapImage.get(topTenGoodsTotalKpi.getGroupId());
                if(imageVo != null) {
                    topTenGoodsTotalKpi.setGroupId(imageVo.getUrl());
                }
            }
            map.put("goods",goodsList);

            RowMapper<TopTenShopTotalKpi> getTopTenShop = ParameterizedBeanPropertyRowMapper.newInstance(TopTenShopTotalKpi.class);
            String getShopSql = FreeMarkerHelper.getValueFromTpl("sql/tjShop/queryTop10TotalKpiShop.sql",tjShopTotalKpiParam);
            List<TopTenShopTotalKpi> shopList = jdbcTemplate.query(getShopSql,getTopTenShop);    //得到商家集合

            map.put("shop",shopList);
        }catch (Exception e){
            logger.toString();
            map.put("error",e.getMessage());
        }

        return map;
    }

    private String getTjShopParam(OrderBizType orderBizType) {
        if (OrderBizType.purchase.equals(orderBizType)) {
            return "purchase";
        }else{
            return "trade";
        }
    }
}
