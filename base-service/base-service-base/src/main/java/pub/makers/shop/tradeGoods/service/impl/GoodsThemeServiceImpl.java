package pub.makers.shop.tradeGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.tradeGoods.dao.GoodsThemeDao;
import pub.makers.shop.tradeGoods.service.GoodsThemeService;
import pub.makers.shop.tradeGoods.entity.GoodsTheme;

/**
 * Created by kok on 2017/5/27.
 */
@Service
public class GoodsThemeServiceImpl extends BaseCRUDServiceImpl<GoodsTheme, String, GoodsThemeDao> implements GoodsThemeService {
}
