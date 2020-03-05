package pub.makers.shop.tradeGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.tradeGoods.dao.GoodLabelsDao;
import pub.makers.shop.tradeGoods.service.GoodLabelsService;
import pub.makers.shop.tradeGoods.entity.GoodLabels;

/**
 * Created by dy on 2017/5/25.
 */
@Service
public class GoodLabelsServiceImpl extends BaseCRUDServiceImpl<GoodLabels, String, GoodLabelsDao> implements GoodLabelsService {
}
