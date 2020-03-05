package pub.makers.shop.tradeorder.controller;

import com.dev.base.json.JsonUtils;
import com.lantu.base.constant.CfgConstants;
import com.lantu.base.util.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;
import pub.makers.shop.baseOrder.pojo.BaseOrder;
import pub.makers.shop.invoice.vo.InvoiceVo;
import pub.makers.shop.tradeGoods.vo.GoodEvaluationVo;
import pub.makers.shop.tradeOrder.pojo.TradeOrderQuery;
import pub.makers.shop.tradeOrder.vo.IndentVo;
import pub.makers.shop.tradeorder.service.TradeOrderAppService;

import java.util.List;
import java.util.Map;


/**
 * 瀹㈡埛璁㈠崟鎺у埗鍣�
 * @author apple
 *
 */
@Controller
@RequestMapping("trade/corder")
public class CustomerTradeOrderController {

	@Autowired
	private TradeOrderAppService tradeOrderService;
	
	/**
	 * 创建普通订单
	 * @param modelJson
	 * @return
	 */
	@RequestMapping("createNormalOrder")
	@ResponseBody
	public ResultData createNormalOrder(String modelJson, String cartIds){
		
		IndentVo indentVo = JsonUtils.toObject(modelJson, IndentVo.class);
		if(indentVo != null && indentVo.getBuyType() == null){
			indentVo.setBuyType("z");
		}
		
		IndentVo order = tradeOrderService.createNormalOrder(indentVo);

		if (StringUtils.isNotEmpty(cartIds)) {
			tradeOrderService.delCart(cartIds, indentVo.getBuyerId());
		}
		return ResultData.createSuccess(order);
	}

	/**
	 * 创建预售订单
	 * @param modelJson
	 * @return
	 */
	@RequestMapping("createPresellOrder")
	@ResponseBody
	public ResultData createPresellOrder(String modelJson){

		IndentVo indentVo = JsonUtils.toObject(modelJson, IndentVo.class);
		if(indentVo != null && indentVo.getBuyType() == null){
			indentVo.setBuyType("z");
		}
		ValidateUtils.notNull(indentVo, "参数错误");
		ValidateUtils.notNull(indentVo.getBuyerId(), "买家ID不能为空");
		ValidateUtils.notNull(indentVo.getSubbranchId(), "店铺ID不能为空");

		IndentVo order = tradeOrderService.createPresellOrder(indentVo);
		return ResultData.createSuccess(order);
	}
	
	
	/**
	 * 创建微信支付信息
	 * @param orderId
	 * @return
	 */
	@RequestMapping("toPay")
	@ResponseBody
	public ResultData toPay(String orderId, String userId){
		
		ValidateUtils.notNull(orderId, "orderId不能为空");
		ValidateUtils.notNull(userId, "userId不能为空");
		Map<String, Object> payResult = tradeOrderService.toPay(orderId, userId);
		
		return ResultData.createSuccess(payResult);
	}
	
	/**
	 * 取消订单
	 * @param userId
	 * @param orderId
	 * @return
	 */
	@RequestMapping("cancelOrder")
	@ResponseBody
	public ResultData cancelOrder(String userId, String orderId){
		tradeOrderService.cancelOrder(userId, orderId);
		
		return ResultData.createSuccess();
	}
	
	/**
	 * 测试订单已支付
	 * @return
	 */
	@RequestMapping("testPayed")
	@ResponseBody
	public ResultData testPayed(){
		
		tradeOrderService.testPayed("388643383352508416");
		
		return ResultData.createSuccess();
	}

	/**
	 * 测试订单确认收货
	 * @return
	 */
	@RequestMapping("testConfirmReceipt")
	@ResponseBody
	public ResultData testConfirmReceipt(String orderNo){
		String orderBug = CfgConstants.getProperties().get("order.debug");
		com.dev.base.utils.ValidateUtils.isTrue("true".equals(orderBug), "非法请求");
		tradeOrderService.testConfirmReceipt(orderNo);

		return ResultData.createSuccess();
	}

	/**
	 * 订单列表
	 */
	@RequestMapping("getOrderList")
	@ResponseBody
	public ResultData getOrderList(String modelJson) {
		TradeOrderQuery query = JsonUtils.toObject(modelJson, TradeOrderQuery.class);
		List<IndentVo> indentVoList = tradeOrderService.getOrderList(query);
		return ResultData.createSuccess("orderList", indentVoList);
	}

	/**
	 * 订单详情
	 */
	@RequestMapping("getOrderDetail")
	@ResponseBody
	public ResultData getOrderDetail(String orderId, String userId) {
		IndentVo indentVo = tradeOrderService.getOrderDetail(orderId, userId);
		return ResultData.createSuccess(indentVo);
	}

	/**
	 * 确认收货
	 */
	@RequestMapping("confirmReceipt")
	@ResponseBody
	public ResultData confirmReceipt(String orderId, String userId, OrderType orderType) {
		ValidateUtils.notNull(orderId, "orderId不能为空");
		ValidateUtils.notNull(userId, "userId不能为空");
		ValidateUtils.notNull(orderType, "orderType为空或类型错误");
		tradeOrderService.confirmReceipt(userId, orderId, orderType);
		return ResultData.createSuccess();
	}

	/**
	 * 删除订单
	 */
	@RequestMapping("deleteOrder")
	@ResponseBody
	public ResultData deleteOrder(String orderId, String userId, OrderType orderType) {
		ValidateUtils.notNull(orderId, "orderId不能为空");
		ValidateUtils.notNull(userId, "userId不能为空");
		ValidateUtils.notNull(orderType, "orderType为空或类型错误");
		tradeOrderService.deleteOrder(orderId, userId, orderType);
		return ResultData.createSuccess();
	}

	/**
	 * 发货提醒
	 */
	@RequestMapping("shipNotice")
	@ResponseBody
	public ResultData shipNotice(String orderId, String userId, OrderType orderType) {
		ValidateUtils.notNull(orderId, "orderId不能为空");
		ValidateUtils.notNull(userId, "userId不能为空");
		ValidateUtils.notNull(orderType, "orderType为空或类型错误");
		tradeOrderService.shipNotice(orderId, userId, orderType);
		return ResultData.createSuccess();
	}
	
	/**
     * 查询订单的支付信息
     * @param id
     * @return
     */
    @RequestMapping("getPayInfo")
    @ResponseBody
    public ResultData getPayInfo(String id){
    	
    	ValidateUtils.isTrue(StringUtils.isNotBlank(id), "订单id为空");
    	IndentVo ivo = tradeOrderService.queryPayInfo(id);
		return ResultData.createSuccess("purchaseOrderVo", ivo);
		
    }

	/**
     * 评论
     */
    @RequestMapping("addEvaluation")
    @ResponseBody
    public ResultData addEvaluation(String modelJson){
		List<GoodEvaluationVo> goodEvaluationVoList = JsonUtils.toObject(modelJson, ListUtils.getCollectionType(List.class, GoodEvaluationVo.class));
		tradeOrderService.addEvaluation(goodEvaluationVoList);
		return ResultData.createSuccess();
    }

	/**
	 * 预览订单概况
	 * @return
	 */
	@RequestMapping("preview")
	@ResponseBody
	public ResultData preview(String modelJson){

		ValidateUtils.notNull(modelJson, "订单信息不能为空");
		IndentVo order = JsonUtils.toObject(modelJson, IndentVo.class);
		ValidateUtils.notNull(order, "订单信息不能为空");
		ValidateUtils.notNull(order.getOrderType(), "订单类型不能为空");
		BaseOrder result = tradeOrderService.preview(order);

		return ResultData.createSuccess(result);
	}

	@RequestMapping("saveInvoice")
	@ResponseBody
	public ResultData saveInvoice(String modelJson) {
		ValidateUtils.notNull(modelJson, "发票信息不能为空");
		InvoiceVo invoiceVo = JsonUtils.toObject(modelJson, InvoiceVo.class);
		tradeOrderService.saveInvoice(invoiceVo);
		return ResultData.createSuccess();
	}

	/**
	 * 获取发票信息
	 */
	@RequestMapping("getInvoice")
	@ResponseBody
	public ResultData getInvoice(String userId) {
		ValidateUtils.notNull(userId, "userId不能为空");
		InvoiceVo invoiceVo = tradeOrderService.getInvoiceInfo(OrderBizType.trade, userId);
		return ResultData.createSuccess(invoiceVo);
	}
}
