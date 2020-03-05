package pub.makers.shop.base.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.base.dao.CommonTextDao;
import pub.makers.shop.base.entity.CommonText;
import pub.makers.shop.base.service.CommonTextService;

/**
 * Created by dy on 2017/7/28.
 */
@Service
public class CommonTextServiceImpl extends BaseCRUDServiceImpl<CommonText, String, CommonTextDao> implements CommonTextService {
}
