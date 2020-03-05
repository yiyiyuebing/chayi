package pub.makers.shop.marketing.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.marketing.dao.MktShowcaseDao;
import pub.makers.shop.marketing.entity.MktShowcase;
import pub.makers.shop.marketing.service.MktShowcaseService;

/**
 * Created by dy on 2017/5/29.
 */
@Service
public class MktShowcaseServiceImpl extends BaseCRUDServiceImpl<MktShowcase, String, MktShowcaseDao> implements MktShowcaseService {
}
