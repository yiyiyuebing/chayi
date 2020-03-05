package pub.makers.shop.tradeGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.tradeGoods.dao.TradeGoodsClassifyDao;
import pub.makers.shop.tradeGoods.service.TradeGoodsClassifyService;
import pub.makers.shop.tradeGoods.entity.TradeGoodsClassify;

/**
 * Created by dy on 2017/5/25.
 */
@Service
public class TradeGoodsClassifyServiceImpl extends BaseCRUDServiceImpl<TradeGoodsClassify, String, TradeGoodsClassifyDao> implements TradeGoodsClassifyService {
}
