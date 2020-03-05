package pub.makers.shop.logistics.service;

import java.math.BigDecimal;

import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.logistics.entity.FreightTpl;
import pub.makers.shop.logistics.vo.FreightResultVo;
import pub.makers.shop.logistics.vo.FreightTplQuery;
import pub.makers.shop.logistics.vo.FreightTplVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.tradeOrder.vo.IndentVo;

public interface FreightTplBizService {
	
	/**
	 * 查询运费可选项
	 * @return
	 */
	FreightResultVo queryOptions(FreightTplQuery query);
	
	/**
	 * 查询运费模板的配置详情
	 * @param tplId
	 * @return
	 */
	FreightTpl getDetailById(String tplId);
	
	
	/**
	 * 显示商城订单可用的配送方式选项
	 * @param indent 订单信息
	 * @param context 当前交易的上下文
	 */
	FreightResultVo queryTradeOrderOptions(IndentVo indent, TradeContext context);
	
	
	/**
	 * 查询商城订单的运费
	 * @param indent
	 * @param context
	 * @param servicerId
	 * @return
	 */
	BigDecimal calcTradeOrderFreight(IndentVo indent, TradeContext context, String servicerId);
	
	
	/**
	 * 查询订单运费
	 * @param query
	 * @return
	 */
	BigDecimal calcOrderFreight(FreightTplQuery query);
	
	
	/**
	 * 查询进货订单可用的配送方式选项
	 * @param order
	 * @param context
	 * @return
	 */
	FreightResultVo queryPurchaseOrderOptions(PurchaseOrderVo order, TradeContext context);
	
	
	/**
	 * 查询商品的首要配送模板
	 * @param skuId
	 * @param context
	 * @param relType
	 * @return
	 */
	FreightTpl queryPrimaryTpl(String skuId, TradeContext context, String relType);
	
	
	/**
	 * 查询进货订单商城的运费
	 * @param order
	 * @param context
	 * @param servicerId
	 * @return
	 */
	BigDecimal calcPurchaseOrderFreight(PurchaseOrderVo order, TradeContext context, String servicerId);
	


	
	
	/**
	 * 根据商品SKU信息查询匹配的运费模板
	 * @param goodSkuId
	 * @param context
	 * @return
	 */
	FreightTplVo queryFreightTplByGoodSku(String goodSkuId, TradeContext context);
	
	
	/**
	 * 查询商品SKU是否已经配置运费模板
	 * @param skuId
	 * @param orderType
	 * @return
	 */
	Boolean hasConfigure(String skuId, String orderType, TradeContext context);
}
