package pub.makers.shop.store.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.store.dao.GoodReceiptAddrDao;
import pub.makers.shop.store.entity.GoodReceiptAddr;
import pub.makers.shop.store.service.GoodReceiptAddrService;

/**
 * Created by kok on 2017/6/8.
 */
@Service
public class GoodReceiptAddrServiceImpl extends BaseCRUDServiceImpl<GoodReceiptAddr, String, GoodReceiptAddrDao> implements GoodReceiptAddrService {
}
