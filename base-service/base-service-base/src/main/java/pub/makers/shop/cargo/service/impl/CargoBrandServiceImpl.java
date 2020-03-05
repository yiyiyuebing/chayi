package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.CargoBrandDao;
import pub.makers.shop.cargo.entity.CargoBrand;
import pub.makers.shop.cargo.service.CargoBrandService;

/**
 * Created by dy on 2017/5/21.
 */
@Service
public class CargoBrandServiceImpl extends BaseCRUDServiceImpl<CargoBrand, String, CargoBrandDao> implements CargoBrandService {
}
