package pub.makers.shop.tongji.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.tongji.service.ShopAccessRecordService;
import pub.makers.shop.tongji.dao.PurchaseshopAccessRecordDao;
import pub.makers.shop.tongji.entity.ShopAccessRecord;

@Service
public class PurchaseshopAccessRecordServiceImpl extends BaseCRUDServiceImpl<ShopAccessRecord, String, PurchaseshopAccessRecordDao>
										implements ShopAccessRecordService{
	
}
