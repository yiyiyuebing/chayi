package pub.makers.shop.cart.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import pub.makers.shop.baseGood.pojo.ChangeGoodNumQuery;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.cart.pojo.CartQuery;
import pub.makers.shop.cart.vo.CartVo;
import pub.makers.shop.cart.vo.ChangeGoodNumVo;
import pub.makers.shop.user.utils.AccountUtils;

import java.util.List;

@Service
public class CartAppService {
	@Reference(version = "1.0.0")
	private CartBizService cartBizService;
	
	public void addToCart(CartQuery query){
		cartBizService.addToCart(query);
	}
	
	public void delFromCart(List<String> idList, String userId, OrderBizType orderBizType){
		cartBizService.delFromCart(idList, userId, orderBizType);
	}
	
	public void clearCart(CartQuery query){
		cartBizService.clearCart(query);
	}
	
	public List<CartVo> getCartList(CartQuery query){
		return cartBizService.getCartList(query);
	}
	
	public Long countCartList(CartQuery query){
		return cartBizService.countCartList(query);
	}

	public ChangeGoodNumVo changeGoodNum(ChangeGoodNumQuery query) {
		query.setOrderBizType(OrderBizType.purchase);
		ChangeGoodNumVo changeGoodNumVo = cartBizService.changeGoodNum(query);
		// 更新购物车
		String userId = AccountUtils.getCurrShopId();
		CartQuery cartQuery = new CartQuery();
		cartQuery.setGoodsId(query.getSkuId());
		cartQuery.setUserId(userId);
		cartQuery.setGoodsCount(changeGoodNumVo.getNum());
		cartQuery.setOrderBizType(OrderBizType.purchase);
		cartBizService.updateCartNumByGoodsId(cartQuery);
		return changeGoodNumVo;
	}
}
