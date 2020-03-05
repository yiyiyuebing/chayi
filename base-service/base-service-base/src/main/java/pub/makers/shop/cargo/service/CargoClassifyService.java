package pub.makers.shop.cargo.service;

import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.cargo.entity.CargoClassify;

import java.util.List;

/**
 * Created by dy on 2017/4/21.
 */
public interface CargoClassifyService extends BaseCRUDService<CargoClassify> {

    /**
     * 通过id集合获取数据
     * @param idList
     * @return
     */
    List<CargoClassify> listByIds(List<String> idList);
}
