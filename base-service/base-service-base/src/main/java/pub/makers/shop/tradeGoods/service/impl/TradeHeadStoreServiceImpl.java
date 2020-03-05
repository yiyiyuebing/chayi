package pub.makers.shop.tradeGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.tradeGoods.dao.TradeHeadStoreDao;
import pub.makers.shop.tradeGoods.service.TradeHeadStoreService;
import pub.makers.shop.tradeGoods.entity.TradeHeadStore;

/**
 * Created by dy on 2017/4/15.
 */
@Service
public class TradeHeadStoreServiceImpl extends BaseCRUDServiceImpl<TradeHeadStore, String, TradeHeadStoreDao> implements TradeHeadStoreService {
}
