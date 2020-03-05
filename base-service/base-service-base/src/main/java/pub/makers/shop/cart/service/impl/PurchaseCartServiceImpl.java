package pub.makers.shop.cart.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cart.dao.PurchaseCartDao;
import pub.makers.shop.cart.entity.Cart;
import pub.makers.shop.cart.service.CartService;

/**
 * Created by kok on 2017/6/12.
 */
@Service
public class PurchaseCartServiceImpl extends BaseCRUDServiceImpl<Cart, String, PurchaseCartDao> implements CartService {
}
