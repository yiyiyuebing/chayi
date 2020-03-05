package pub.makers.shop.cargo.service;

import pub.makers.shop.cargo.entity.GoodPutawaySchedule;

/**
 * Created by daiwenfa on 2017/6/1.
 */
public interface GoodPutawayScheduleMgrBizService {

    //定时上架
    void goodsShelfTime(GoodPutawaySchedule goodPutawaySchedule);

}
