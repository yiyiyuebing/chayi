package pub.makers.shop.promotion.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.promotion.service.SaleActivityGoodService;
import pub.makers.shop.promotion.dao.SaleActivityGoodDao;
import pub.makers.shop.promotion.entity.SaleActivityGood;

@Service
public class SaleActivityGoodServiceImpl extends BaseCRUDServiceImpl<SaleActivityGood, String, SaleActivityGoodDao>
										implements SaleActivityGoodService{
	
}
