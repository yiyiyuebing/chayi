package pub.makers.shop.marketing.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.marketing.dao.VtwoStudyGoodsDao;
import pub.makers.shop.marketing.entity.VtwoStudyGoods;
import pub.makers.shop.marketing.service.VtwoStudyGoodsService;

/**
 * Created by dy on 2017/5/3.
 */
@Service
public class VtwoStudyGoodsServiceImpl extends BaseCRUDServiceImpl<VtwoStudyGoods, String, VtwoStudyGoodsDao> implements VtwoStudyGoodsService {
}
