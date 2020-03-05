package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.cargo.entity.CargoBasePropertys;
import pub.makers.shop.cargo.vo.CargoBasePropertysVo;

import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/7/6.
 */
@Service(version = "1.0.0")
public class CargoBasePropertysBizServiceImpl implements CargoBasePropertysBizService {
    @Autowired
    private CargoBasePropertysService cargoBasePropertysService;

    @Override
    public Map<String, List<CargoBasePropertysVo>> getPropertysList(List<String> cargoIdList) {
        List<CargoBasePropertys> propertysList = cargoBasePropertysService.list(Conds.get().in("cargo_id", cargoIdList));
        Map<String, List<CargoBasePropertysVo>> map = Maps.newHashMap();
        for (CargoBasePropertys propertys : propertysList) {
            if (StringUtils.isEmpty(propertys.getPvalue())) {
                continue;
            }
            List<CargoBasePropertysVo> propertysVoList = map.get(propertys.getCargoId().toString()) == null ? Lists.newArrayList() : map.get(propertys.getCargoId().toString());
            propertysVoList.add(new CargoBasePropertysVo(propertys));
            map.put(propertys.getCargoId().toString(), propertysVoList);
        }
        return map;
    }
}
