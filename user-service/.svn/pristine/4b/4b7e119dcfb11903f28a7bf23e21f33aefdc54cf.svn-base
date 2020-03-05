package pub.makers.shop.bill.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.util.IdGenerator;
import pub.makers.shop.bill.entity.TimeCycle;
import pub.makers.shop.bill.enums.TimeCycleType;
import pub.makers.shop.bill.vo.TimeCycleVo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/9/7.
 */
@Service(version = "1.0.0")
public class TimeCycleBizServiceImpl implements TimeCycleBizService {

    @Autowired
    private TimeCycleService timeCycleService;

    @Override
    public TimeCycle getTimeCycleInfo(TimeCycleType timeCycleType) {
        return timeCycleService.get(Conds.get().eq("type", timeCycleType.name()));
    }

    @Override
    public Map<String, Object> saveOrUpdate(TimeCycleVo timeCycle) {
        Map<String, Object> result = new HashMap<String, Object>();
        TimeCycle newTimeCycle = new TimeCycle();
        if (StringUtils.isBlank(timeCycle.getId())) {
            newTimeCycle.setId(IdGenerator.getDefault().nextId());
            newTimeCycle.setDuration(Integer.parseInt(timeCycle.getDuration()));
            newTimeCycle.setType(Integer.parseInt(TimeCycleType.settleTime + ""));
        } else {
            timeCycleService.update(Update.byId(timeCycle.getId()).set("duration", timeCycle.getDuration()).set("update_time", new Date()));
            newTimeCycle = timeCycleService.getById(timeCycle.getId());
        }
        result.put("success", true);
        result.put("msg", newTimeCycle.getId());
        return result;
    }

    @Override
    public TimeCycleVo detail(int type) {
        TimeCycleVo timeCycleVo = new TimeCycleVo();
        List<TimeCycle> timeCycleList = timeCycleService.list(Conds.get().eq("type", type));
        if (timeCycleList.isEmpty()) {
            return null;
        }
        TimeCycle timeCycle = timeCycleList.get(0);
        timeCycleVo.setId(timeCycle.getId() + "");
        timeCycleVo.setType(timeCycle.getType());
        timeCycleVo.setDuration(timeCycle.getDuration() + "");
        timeCycleVo.setUpdateTime(timeCycle.getUpdateTime());
        return timeCycleVo;
    }
}
