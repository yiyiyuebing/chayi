package pub.makers.shop.favorite.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.favorite.dao.TradeFavoriteDao;
import pub.makers.shop.favorite.entity.Favorite;
import pub.makers.shop.favorite.service.FavoriteService;

/**
 * Created by dy on 2017/6/19.
 */
@Service
public class TradeFavoriteServiceImpl extends BaseCRUDServiceImpl<Favorite, String, TradeFavoriteDao> implements FavoriteService {
}
