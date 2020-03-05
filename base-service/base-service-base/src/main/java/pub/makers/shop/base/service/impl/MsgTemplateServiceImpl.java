package pub.makers.shop.base.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.base.dao.MsgTemplateDao;
import pub.makers.shop.base.entity.MsgTemplate;
import pub.makers.shop.base.service.MsgTemplateService;

/**
 * Created by dy on 2017/5/15.
 */
@Service
public class MsgTemplateServiceImpl extends BaseCRUDServiceImpl<MsgTemplate, String, MsgTemplateDao>
        implements MsgTemplateService {

}
