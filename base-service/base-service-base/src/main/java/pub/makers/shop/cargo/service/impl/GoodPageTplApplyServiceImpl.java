package pub.makers.shop.cargo.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cargo.dao.GoodPageTplApplyDao;
import pub.makers.shop.cargo.entity.GoodPageTplApply;
import pub.makers.shop.cargo.service.GoodPageTplApplyService;

/**
 * Created by daiwenfa on 2017/6/7.
 */
@Service
public class GoodPageTplApplyServiceImpl extends BaseCRUDServiceImpl<GoodPageTplApply, String, GoodPageTplApplyDao> implements GoodPageTplApplyService {
}
