package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.GoodPageTplDao;
import pub.makers.shop.cargo.entity.GoodPageTpl;
import pub.makers.shop.cargo.service.GoodPageTplService;

/**
 * Created by daiwenfa on 2017/6/7.
 */
@Service
public class GoodPageTplServiceImpl extends BaseCRUDServiceImpl<GoodPageTpl, String, GoodPageTplDao> implements GoodPageTplService  {
}
