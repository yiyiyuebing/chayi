package pub.makers.shop.store.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;

import pub.makers.shop.store.entity.SalesReturnReason;
import pub.makers.shop.store.service.SalesReturnReasonService;
import pub.makers.shop.store.service.dao.SalesReturnReasonDao;

/**
 * Created by dy on 2017/4/15.
 */
@Service
public class SalesReturnReasonServiceImpl extends BaseCRUDServiceImpl<SalesReturnReason, String, SalesReturnReasonDao> implements SalesReturnReasonService {
}
