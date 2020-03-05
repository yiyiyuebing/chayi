package pub.makers.shop.promotion.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.promotion.dao.ManzengActivityDao;
import pub.makers.shop.promotion.entity.ManzengActivity;
import pub.makers.shop.promotion.service.ManzengActivityService;

/**
 * Created by dy on 2017/8/17.
 */
@Service
public class ManzengActivityServiceImpl extends BaseCRUDServiceImpl<ManzengActivity, String, ManzengActivityDao> implements ManzengActivityService {
}
