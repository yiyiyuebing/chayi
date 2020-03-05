package pub.makers.shop.tongji.service;

import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.tongji.pojo.TjShopTotalKpiParam;

import java.util.Map;

/**
 * Created by devpc on 2017/8/15.
 */
public interface TjShopTotalKpiMgrBizService {

    Map<String,Object> selectTodayTotalKpi ( OrderBizType orderBizType) ;

    Map<String,Object> selectSumTotalKpiByTime ( OrderBizType orderBizType,TjShopTotalKpiParam tjShopTotalKpiParam) ;

    Map<String,Object> selectTopTenTotalKpi ( OrderBizType orderBizType) ;
}
