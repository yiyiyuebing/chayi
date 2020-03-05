package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.GoodPageTplPostDao;
import pub.makers.shop.cargo.entity.GoodPageTplPost;
import pub.makers.shop.cargo.service.GoodPageTplPostService;

/**
 * Created by daiwenfa on 2017/6/8.
 */
@Service
public class GoodPageTplPostServiceImpl extends BaseCRUDServiceImpl<GoodPageTplPost, String, GoodPageTplPostDao> implements GoodPageTplPostService {
}
