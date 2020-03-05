package pub.makers.shop.cart.service.impl;

import org.springframework.stereotype.Service;

import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.cart.service.CartService;
import pub.makers.shop.cart.dao.CartDao;
import pub.makers.shop.cart.entity.Cart;

@Service
public class CartServiceImpl extends BaseCRUDServiceImpl<Cart, String, CartDao>
										implements CartService{
	
}
