package pub.makers.shop.promotion.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.promotion.dao.ManzengGoodDao;
import pub.makers.shop.promotion.entity.ManzengGood;
import pub.makers.shop.promotion.service.ManzengGoodService;

/**
* Created by dy on 2017/8/17.
*/
@Service
public class ManzengGoodServiceImpl extends BaseCRUDServiceImpl<ManzengGood, String, ManzengGoodDao> implements ManzengGoodService {
}
