package pub.makers.shop.store.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.store.dao.WeixinUserStoreLabelDao;
import pub.makers.shop.store.entity.WeixinUserStoreLabel;
import pub.makers.shop.store.service.WeixinUserStoreLabelService;

/**
 * Created by dy on 2017/10/10.
 */
@Service
public class WeixinUserStoreLabelServiceImpl extends BaseCRUDServiceImpl<WeixinUserStoreLabel, String, WeixinUserStoreLabelDao> implements WeixinUserStoreLabelService {
}
