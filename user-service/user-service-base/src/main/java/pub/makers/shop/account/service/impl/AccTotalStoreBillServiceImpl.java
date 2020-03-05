package pub.makers.shop.account.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.account.dao.AccTotalStoreBillDao;
import pub.makers.shop.account.service.AccTotalStoreBillService;
import pub.makers.shop.account.entity.AccTotalStoreBill;

/**
 * Created by kok on 2017/6/2.
 */
@Service
public class AccTotalStoreBillServiceImpl extends BaseCRUDServiceImpl<AccTotalStoreBill, String, AccTotalStoreBillDao> implements AccTotalStoreBillService {
}
