package pub.makers.shop.tradeGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.tradeGoods.dao.GoodsBaseLabelDao;
import pub.makers.shop.tradeGoods.service.GoodsBaseLabelService;
import pub.makers.shop.tradeGoods.entity.GoodsBaseLabel;

/**
 * Created by dy on 2017/5/25.
 */
@Service
public class GoodsBaseLabelServiceImpl extends BaseCRUDServiceImpl<GoodsBaseLabel, String, GoodsBaseLabelDao> implements GoodsBaseLabelService {
}
