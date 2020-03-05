package pub.makers.shop.index.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.index.dao.IndexModuleDao;
import pub.makers.shop.index.entity.IndexModule;
import pub.makers.shop.index.service.IndexModuleService;

/**
 * Created by dy on 2017/6/12.
 */
@Service
public class IndexModuleServiceImpl extends BaseCRUDServiceImpl<IndexModule, String, IndexModuleDao> implements IndexModuleService {
}
