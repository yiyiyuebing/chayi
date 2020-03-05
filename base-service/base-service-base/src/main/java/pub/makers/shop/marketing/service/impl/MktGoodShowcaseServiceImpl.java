package pub.makers.shop.marketing.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.marketing.dao.MktGoodShowcaseDao;
import pub.makers.shop.marketing.entity.MktGoodShowcase;
import pub.makers.shop.marketing.service.MktGoodShowcaseService;

/**
 * Created by dy on 2017/5/29.
 */
@Service
public class MktGoodShowcaseServiceImpl extends BaseCRUDServiceImpl<MktGoodShowcase, String, MktGoodShowcaseDao> implements MktGoodShowcaseService {
}
