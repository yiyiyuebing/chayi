package pub.makers.shop.tongji.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.tongji.dao.TradeshopAccessRecordDao;
import pub.makers.shop.tongji.entity.ShopAccessRecord;
import pub.makers.shop.tongji.service.ShopAccessRecordService;

@Service
public class TradeshopAccessRecordServiceImpl extends BaseCRUDServiceImpl<ShopAccessRecord, String, TradeshopAccessRecordDao>
										implements ShopAccessRecordService{
	
}
