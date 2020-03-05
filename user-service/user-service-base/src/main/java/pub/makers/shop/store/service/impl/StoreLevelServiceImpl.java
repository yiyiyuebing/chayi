package pub.makers.shop.store.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.store.dao.StoreLevelDao;
import pub.makers.shop.store.entity.StoreLevel;
import pub.makers.shop.store.service.StoreLevelService;

/**
 * Created by dy on 2017/4/14.
 */
@Service
public class StoreLevelServiceImpl extends BaseCRUDServiceImpl<StoreLevel, String, StoreLevelDao> implements StoreLevelService {

}
