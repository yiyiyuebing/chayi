package pub.makers.shop.tradeorder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.tradeOrder.vo.IndentExtendVo;
import pub.makers.shop.tradeOrder.vo.IndentVo;
import pub.makers.shop.tradeorder.service.BusinessTradeOrderService;
import pub.makers.shop.user.utils.AccountUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 店铺订单控制器
 * @author apple
 *
 */
@Controller
@RequestMapping("trade/order")
public class BusinessTradeOrderController {

	@Autowired
	private BusinessTradeOrderService tradeOrderService;
	private static final Map<String, String> statusMapping = Maps.newHashMap();
	static {
		statusMapping.put("confirm", "0");
		statusMapping.put("pay", "1");
		statusMapping.put("ship", "2");
		statusMapping.put("refund", "3");
		statusMapping.put("receive", "5");
		statusMapping.put("return", "6");
		statusMapping.put("returning", "7");
		statusMapping.put("refunded", "8");
		statusMapping.put("returned", "9");
		statusMapping.put("received", "10");
		statusMapping.put("cancel", "11");
		statusMapping.put("evaluate", "12");
		statusMapping.put("done", "13");
		statusMapping.put("refunding", "14");
	}
	
	
	/**
	 * 查询店铺订单列表
	 * @param request
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public ResultData orderList(HttpServletRequest request, String status){

		String shopId = AccountUtils.getCurrShopId();
		String newStatus = statusMapping.get(status);
		if (StringUtils.isNotBlank(newStatus)){
			status = newStatus;
		}
		List<IndentExtendVo> orderList = tradeOrderService.listShopOrder(shopId, null, status, Paging.build2(request));
		
		return ResultData.createSuccess("orderList", orderList);
	}

	@RequestMapping("detail")
	@ResponseBody
	public ResultData orderDetail(String orderId) {
		IndentVo indentVo = tradeOrderService.orderDetail(orderId);
		return ResultData.createSuccess(indentVo);
	}

	@RequestMapping("createOrder")
	@ResponseBody
	public ResultData createOrder(@RequestParam(value = "modelJson", required = true) String modelJson){
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			IndentVo indentVo = objectMapper.readValue(modelJson, IndentVo.class);
			tradeOrderService.createOrder(indentVo);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ResultData.createSuccess();
	}

	@RequestMapping("cancelOrder")
	@ResponseBody
	public ResultData cancelOrder(String userId, String orderId, OrderType orderType){
		ValidateUtils.notNull(userId, "用户id为空");
		ValidateUtils.notNull(orderId, "订单id为空");
		ValidateUtils.notNull(orderType, "订单类型为空");

		tradeOrderService.cancelOrder(userId, orderId, orderType);

		return ResultData.createSuccess();
	}

	@RequestMapping("confirmReceipt")
	@ResponseBody
	public ResultData confirmReceipt(String userId, String orderId, OrderType orderType){
		ValidateUtils.notNull(userId, "用户id为空");
		ValidateUtils.notNull(orderId, "订单id为空");
		ValidateUtils.notNull(orderType, "订单类型为空");

		tradeOrderService.confirmReceipt(userId, orderId, orderType);

		return ResultData.createSuccess();
	}
}
