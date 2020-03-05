package pub.makers.shop.base.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.base.dao.StoreSubbranchExtDao;
import pub.makers.shop.base.service.StoreSubbranchExtService;
import pub.makers.shop.store.entity.StoreSubbranchExt;


/**
 * Created by devpc on 2017/7/25.
 */
@Service
public class StoreSubbranchExtServiceImpl extends BaseCRUDServiceImpl<StoreSubbranchExt,Long,StoreSubbranchExtDao>
        implements StoreSubbranchExtService {

}
