package pub.makers.shop.finance.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.finance.dao.FinanceAccountspayItemDao;
import pub.makers.shop.finance.entity.FinanceAccountspayItem;
import pub.makers.shop.finance.service.FinanceAccountspayItemService;

/**
 * Created by dy on 2017/4/14.
 */
@Service
public class FinanceAccountspayItemServiceImpl extends BaseCRUDServiceImpl<FinanceAccountspayItem, String, FinanceAccountspayItemDao> implements FinanceAccountspayItemService {
}
