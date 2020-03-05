package pub.makers.shop.account.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.account.dao.AccStoreBillChangeRecordDao;
import pub.makers.shop.account.entity.AccStoreBillChangeRecord;
import pub.makers.shop.account.service.AccStoreBillChangeRecordService;

/**
 * Created by kok on 2017/6/2.
 */
@Service
public class AccStoreBillChangeRecordServiceImpl extends BaseCRUDServiceImpl<AccStoreBillChangeRecord, String, AccStoreBillChangeRecordDao> implements AccStoreBillChangeRecordService {
}
