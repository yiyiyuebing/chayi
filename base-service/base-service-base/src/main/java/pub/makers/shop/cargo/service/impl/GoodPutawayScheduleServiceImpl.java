package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.GoodPutawayScheduleDao;
import pub.makers.shop.cargo.entity.GoodPutawaySchedule;
import pub.makers.shop.cargo.service.GoodPutawayScheduleService;

/**
 * Created by daiwenfa on 2017/6/1.
 */
@Service
public class GoodPutawayScheduleServiceImpl extends BaseCRUDServiceImpl<GoodPutawaySchedule, String, GoodPutawayScheduleDao> implements GoodPutawayScheduleService {
}
