package pub.makers.shop.index.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.index.dao.IndexModuleGoodDao;
import pub.makers.shop.index.entity.IndexModuleGood;
import pub.makers.shop.index.service.IndexModuleGoodService;

/**
 * Created by dy on 2017/6/12.
 */
@Service
public class IndexModuleGoodServiceImpl extends BaseCRUDServiceImpl<IndexModuleGood, String, IndexModuleGoodDao> implements IndexModuleGoodService  {
}
