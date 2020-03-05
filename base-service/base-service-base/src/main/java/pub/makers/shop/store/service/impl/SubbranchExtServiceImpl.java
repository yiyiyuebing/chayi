package pub.makers.shop.store.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.store.entity.StoreSubbranchExt;
import pub.makers.shop.store.service.SubbranchExtService;
import pub.makers.shop.store.service.dao.SubbranchExtDao;

/**
 * Created by daiwenfa on 2017/7/27.
 */
@Service
public class SubbranchExtServiceImpl extends BaseCRUDServiceImpl<StoreSubbranchExt, String, SubbranchExtDao>
        implements SubbranchExtService {
}