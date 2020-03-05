package pub.makers.shop.favorite.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cart.dao.PurchaseCartDao;
import pub.makers.shop.cart.entity.Cart;
import pub.makers.shop.cart.service.CartService;
import pub.makers.shop.favorite.dao.PurchaseFavoriteDao;
import pub.makers.shop.favorite.entity.Favorite;
import pub.makers.shop.favorite.service.FavoriteService;

/**
 * Created by dy on 2017/6/19.
 */
@Service
public class PurchaseFavoriteServiceImpl extends BaseCRUDServiceImpl<Favorite, String, PurchaseFavoriteDao> implements FavoriteService {
}
