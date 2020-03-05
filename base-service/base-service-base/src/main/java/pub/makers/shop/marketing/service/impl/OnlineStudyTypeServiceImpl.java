package pub.makers.shop.marketing.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.marketing.dao.OnlineStudyTypeDao;
import pub.makers.shop.marketing.entity.OnlineStudyType;
import pub.makers.shop.marketing.service.OnlineStudyTypeService;

/**
 * Created by dy on 2017/5/3.
 */
@Service
public class OnlineStudyTypeServiceImpl extends BaseCRUDServiceImpl<OnlineStudyType, String, OnlineStudyTypeDao> implements OnlineStudyTypeService {
}
