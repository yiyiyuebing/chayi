package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.GoodPageTplApplyDao;
import pub.makers.shop.cargo.dao.GoodPageTplModelDao;
import pub.makers.shop.cargo.entity.GoodPageTplModel;
import pub.makers.shop.cargo.service.GoodPageTplModelService;

/**
 * Created by daiwenfa on 2017/6/7.
 */
@Service
public class GoodPageTplModelServiceImpl extends BaseCRUDServiceImpl<GoodPageTplModel, String, GoodPageTplModelDao> implements GoodPageTplModelService {
}
