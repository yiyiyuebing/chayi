package pub.makers.shop.cart.controller;

import com.dev.base.exception.errorcode.SysErrorCode;
import com.dev.base.json.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseGood.pojo.ChangeGoodNumQuery;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.cart.pojo.CartQuery;
import pub.makers.shop.cart.service.CartAppService;
import pub.makers.shop.cart.vo.CartVo;
import pub.makers.shop.cart.vo.ChangeGoodNumVo;
import pub.makers.shop.user.utils.AccountUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("cart")
public class CartAppController {

	@Autowired
	private CartAppService cartAppService;
	
	/**
	 * {cart/addToCart}
	 *  添加购物车商品 
	 * @return
	 */
	@RequestMapping("addToCart")
	@ResponseBody
	public ResultData addToCart(String queryJson){
		ValidateUtils.isTrue(StringUtils.isNotBlank(queryJson), SysErrorCode.SYS_001, "传入JSON数据为空");
		CartQuery query = JsonUtils.toObject(queryJson, CartQuery.class);
		String userId = AccountUtils.getCurrShopId();
		query.setUserId(userId);
		query.setClientType(ClientType.mobile);
		query.setOrderBizType(OrderBizType.purchase);
		cartAppService.addToCart(query);
		return ResultData.createSuccess();
	}
	
	/**
	 * {cart/delFromCart}
	 * 删除购物车商品
	 * @return
	 */
	@RequestMapping("delFromCart")
	@ResponseBody
	public ResultData delFromCart(String idList) {
		List<String> ids = new ArrayList<>();
		OrderBizType orderBizType = OrderBizType.purchase;
		if (idList != null) {
			String[] arrayId = idList.split(",");
			for (String id : arrayId) {
				ids.add(id);
			}
		}

		String userId = AccountUtils.getCurrShopId();
		cartAppService.delFromCart(ids, userId, orderBizType);
		return ResultData.createSuccess();
	}
	
	/**
	 * {cart/clearCart}
	 * 清空购物车
	 * @return
	 */
	@RequestMapping("clearCart")
	@ResponseBody
	public ResultData clearCart(){
		CartQuery query = new CartQuery();
		String userId = AccountUtils.getCurrShopId();
		query.setUserId(userId);
		query.setOrderBizType(OrderBizType.purchase);
		try {
			cartAppService.clearCart(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultData.createSuccess();
	}
	
	/**
	 *  {cart/getCartList}
	 *  获取购物车列表
	 * @return
	 */
	@RequestMapping("getCartList")
	@ResponseBody
	public ResultData getCartList(){
		CartQuery query = new CartQuery();
		String userId = AccountUtils.getCurrShopId();
		String storeLevelId = AccountUtils.getCurrStoreLevelId();
		query.setUserId(userId);
		query.setOrderBizType(OrderBizType.purchase);
		query.setStoreLevelId(storeLevelId);
		query.setClientType(ClientType.mobile);

		List<CartVo> cartVos = cartAppService.getCartList(query);
		
		return ResultData.createSuccess("cartVos",cartVos);
		
	}

	/**
	 *  {cart/countCartList}
	 *  获取购物车数量
	 * @return
	 */
	@RequestMapping("countCartList")
	@ResponseBody
	public ResultData countCartList(){
		CartQuery query = new CartQuery();
		String userId = AccountUtils.getCurrShopId();
		query.setUserId(userId);
		query.setOrderBizType(OrderBizType.purchase);

		Long num = cartAppService.countCartList(query);

		return ResultData.createSuccess("num",num);

	}

	@RequestMapping("changeGoodNum")
	@ResponseBody
	public ResultData changeGoodNum(String modelJson) {
		ValidateUtils.notNull(modelJson, "参数不能为空");

		ChangeGoodNumQuery query = JsonUtils.toObject(modelJson, ChangeGoodNumQuery.class);
		String storeLevelId = AccountUtils.getCurrStoreLevelId();
		query.setStoreLevelId(storeLevelId);
		ChangeGoodNumVo changeGoodNumVo = cartAppService.changeGoodNum(query);
		return ResultData.createSuccess(changeGoodNumVo);
	}
}
