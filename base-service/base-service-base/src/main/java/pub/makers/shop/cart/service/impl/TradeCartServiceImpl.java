package pub.makers.shop.cart.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cart.dao.TradeCartDao;
import pub.makers.shop.cart.entity.Cart;
import pub.makers.shop.cart.service.CartService;

/**
 * Created by kok on 2017/6/12.
 */
@Service
public class TradeCartServiceImpl extends BaseCRUDServiceImpl<Cart, String, TradeCartDao> implements CartService {
}
