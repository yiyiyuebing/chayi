package pub.makers.shop.tongji.service;


import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.tongji.pojo.TjShopTotalKpiParam;

import java.util.Map;

/**
 *
 * @author apple
 *
 */
public interface TjShopTotalKpiBizService {


	Map<String,Object> selectTodayTotalKpi ( OrderBizType orderBizType) ;

}
