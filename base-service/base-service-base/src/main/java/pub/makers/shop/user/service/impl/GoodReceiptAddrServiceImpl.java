package pub.makers.shop.user.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.store.entity.GoodReceiptAddr;
import pub.makers.shop.user.dao.GoodReceiptAddrDao;
import pub.makers.shop.user.service.GoodReceiptAddrService;

/**
 * Created by daiwenfa on 2017/7/21.
 */
@Service
public class GoodReceiptAddrServiceImpl extends BaseCRUDServiceImpl<GoodReceiptAddr, String, GoodReceiptAddrDao>
        implements GoodReceiptAddrService {
}
