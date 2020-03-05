package pub.makers.shop.tradeGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.tradeGoods.dao.GoodsColumnDao;
import pub.makers.shop.tradeGoods.service.GoodsColumnService;
import pub.makers.shop.tradeGoods.entity.GoodsColumn;

/**
 * Created by kok on 2017/5/26.
 */
@Service
public class GoodsColumnServiceImpl extends BaseCRUDServiceImpl<GoodsColumn, String, GoodsColumnDao> implements GoodsColumnService {
}
