package pub.makers.shop.cargo.service;

import pub.makers.shop.cargo.vo.CargoBasePropertysVo;

import java.util.List;
import java.util.Map;

/**
 * Created by kok on 2017/7/6.
 */
public interface CargoBasePropertysBizService {
    /**
     * 查询货品基础属性 key是cargoId
     */
    Map<String, List<CargoBasePropertysVo>> getPropertysList(List<String> cargoIdList);
}
