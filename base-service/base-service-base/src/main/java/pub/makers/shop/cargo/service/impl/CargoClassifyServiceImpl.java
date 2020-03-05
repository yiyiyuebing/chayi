package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.cargo.dao.CargoClassifyDao;
import pub.makers.shop.cargo.entity.CargoClassify;
import pub.makers.shop.cargo.service.CargoClassifyService;

import java.util.List;

/**
 * Created by dy on 2017/4/21.
 */
@Service
public class CargoClassifyServiceImpl extends BaseCRUDServiceImpl<CargoClassify, String, CargoClassifyDao> implements CargoClassifyService {
    @Override
    public List<CargoClassify> listByIds(List<String> idList) {
        return list(Conds.get().in("id", idList));
    }
}
