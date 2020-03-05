package pub.makers.shop.promotion.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.dao.impl.BaseCRUDDaoImpl;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.promotion.dao.ManzengActivityApplyDao;
import pub.makers.shop.promotion.entity.ManzengActivityApply;
import pub.makers.shop.promotion.service.ManzengActivityApplyService;

/**
* Created by dy on 2017/8/17.
*/
@Service
public class ManzengActivityApplyServiceImpl extends BaseCRUDServiceImpl<ManzengActivityApply, String, ManzengActivityApplyDao> implements ManzengActivityApplyService {
}
