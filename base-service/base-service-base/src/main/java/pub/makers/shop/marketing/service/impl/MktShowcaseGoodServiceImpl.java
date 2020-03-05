package pub.makers.shop.marketing.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.marketing.dao.MktShowcaseGoodDao;
import pub.makers.shop.marketing.entity.MktShowcaseGood;
import pub.makers.shop.marketing.service.MktShowcaseGoodService;

/**
 * Created by dy on 2017/5/29.
 */
@Service
public class MktShowcaseGoodServiceImpl extends BaseCRUDServiceImpl<MktShowcaseGood, String, MktShowcaseGoodDao> implements MktShowcaseGoodService {
}
