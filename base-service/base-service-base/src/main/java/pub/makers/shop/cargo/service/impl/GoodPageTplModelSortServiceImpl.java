package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.GoodPageTplModelSortDao;
import pub.makers.shop.cargo.entity.GoodPageTplModelSort;
import pub.makers.shop.cargo.service.GoodPageTplModelSortService;

/**
 * Created by daiwenfa on 2017/7/28.
 */
@Service
public class GoodPageTplModelSortServiceImpl extends BaseCRUDServiceImpl<GoodPageTplModelSort, String, GoodPageTplModelSortDao> implements GoodPageTplModelSortService {
}
