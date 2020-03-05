package pub.makers.shop.bill.service;

import pub.makers.shop.bill.entity.TimeCycle;
import pub.makers.shop.bill.enums.TimeCycleType;
import pub.makers.shop.bill.vo.TimeCycleVo;

import java.util.Map;

/**
 * Created by dy on 2017/9/7.
 */
public interface TimeCycleBizService {

    /**
     * 获取结算周期
     * @param timeCycleType
     * @return
     */
    TimeCycle getTimeCycleInfo(TimeCycleType timeCycleType);

    /**
     * 新增或更新
     * @param timeCycle
     * @return
     */
    Map<String,Object> saveOrUpdate(TimeCycleVo timeCycle);

    /**
     * 获取详情
     * @param dbDataByName
     * @return
     */
    TimeCycleVo detail(int dbDataByName);
}
