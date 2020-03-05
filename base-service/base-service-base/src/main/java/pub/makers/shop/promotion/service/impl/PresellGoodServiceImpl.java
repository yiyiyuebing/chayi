package pub.makers.shop.promotion.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.promotion.service.PresellGoodService;
import pub.makers.shop.promotion.dao.PresellGoodDao;
import pub.makers.shop.promotion.entity.PresellGood;

@Service
public class PresellGoodServiceImpl extends BaseCRUDServiceImpl<PresellGood, String, PresellGoodDao>
										implements PresellGoodService{
	
}
