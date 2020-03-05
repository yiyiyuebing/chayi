package pub.makers.shop.base.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.base.dao.MsgTemplateDao;
import pub.makers.shop.base.dao.OpinionDao;
import pub.makers.shop.base.entity.MsgTemplate;
import pub.makers.shop.base.entity.Opinion;
import pub.makers.shop.base.service.MsgTemplateService;
import pub.makers.shop.base.service.OpinionService;

/**
 * Created by Think on 2017/8/25.
 */
@Service
public class OpinionServiceImpl extends BaseCRUDServiceImpl<Opinion, String, OpinionDao>
        implements OpinionService {
}
