package pub.makers.shop.base.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;

import pub.makers.shop.base.dao.WeixinUserInfoExtDao;
import pub.makers.shop.base.service.SysDictService;
import pub.makers.shop.base.service.WeixinUserInfoExtAdminService;
import pub.makers.shop.base.service.WeixinUserInfoExtService;
import pub.makers.shop.user.entity.WeixinUserInfoExt;

/**
 * Created by devpc on 2017/7/25.
 */
@Service
public class WeixinUserInfoExtServiceImpl extends BaseCRUDServiceImpl<WeixinUserInfoExt,Long,WeixinUserInfoExtDao>
        implements WeixinUserInfoExtService {

}
